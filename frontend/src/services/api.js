import axios from 'axios'

export const USE_MOCK = import.meta.env.VITE_USE_MOCK === 'true'

// 기본은 상대경로 /api (Vite 프록시 → http://localhost:8088). 포트 8080/8081 을 사용하지 않는다.
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  withCredentials: true, // JSESSIONID 쿠키 전달
  headers: { 'Content-Type': 'application/json' },
})

const SAFE_METHODS = ['get', 'head', 'options']
const isUnsafe = (config) => !SAFE_METHODS.includes((config.method || 'get').toLowerCase())

// ---- CSRF ------------------------------------------------------------
// GET /api/csrf 응답을 { token, headerName } 으로 가정한다 (Spring CsrfToken 직렬화 형태).
// TODO: 실제 /api/csrf 응답 형식이 확정되면 이 파일만 조정한다.
let csrfInfo = null
let csrfPending = null

export function clearCsrf() {
  csrfInfo = null
}

export async function refreshCsrf() {
  if (!csrfPending) {
    csrfPending = api
      .get('/csrf', { skipCsrf: true, skipAuthRedirect: true })
      .then(({ data }) => {
        csrfInfo = data?.token ? { token: data.token, headerName: data.headerName || 'X-CSRF-TOKEN' } : null
        return csrfInfo
      })
      .catch(() => {
        csrfInfo = null // CSRF 엔드포인트가 없으면 헤더 없이 진행(서버 설정에 따라 403 가능)
        return null
      })
      .finally(() => {
        csrfPending = null
      })
  }
  return csrfPending
}

api.interceptors.request.use(async (config) => {
  if (isUnsafe(config) && !config.skipCsrf) {
    if (!csrfInfo) await refreshCsrf()
    if (csrfInfo) {
      config.headers[csrfInfo.headerName] = csrfInfo.token
      config._csrfSent = true
    }
  }
  return config
})

// ---- 401 / 403 처리 ----------------------------------------------------
let unauthorizedHandler = null
export const setUnauthorizedHandler = (fn) => {
  unauthorizedHandler = fn
}

api.interceptors.response.use(
  (res) => res,
  async (error) => {
    const { config, response } = error
    if (!config || !response) return Promise.reject(error)

    // 403 + CSRF 를 보낸 요청: 토큰이 만료됐을 수 있으므로 한 번만 재발급 후 재시도 (무한 루프 방지)
    if (response.status === 403 && config._csrfSent && !config._csrfRetried) {
      config._csrfRetried = true
      clearCsrf()
      await refreshCsrf()
      return api.request(config)
    }

    if (response.status === 401 && !config.skipAuthRedirect && unauthorizedHandler) {
      unauthorizedHandler()
    }
    return Promise.reject(error)
  },
)

export default api
