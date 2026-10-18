import axios from 'axios'

export const USE_MOCK = import.meta.env.VITE_USE_MOCK === 'true'

// 기본은 상대경로 /api (Vite 프록시 → http://localhost:8088). 포트 8080/8081 을 사용하지 않는다.
//
// CSRF: Spring Security의 csrf.spa()가 XSRF-TOKEN 쿠키를 발급한다.
// 라우터의 최초 세션 조회(GET /members/me)가 끝난 후 화면에 진입하고,
// Axios는 같은 출처의 변경 요청에 X-XSRF-TOKEN 헤더를 자동 첨부한다.
// 별도 /api/csrf 호출이나 쓰기 요청 자동 재시도는 하지 않는다.
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '/api',
  withCredentials: true, // JSESSIONID 쿠키 전달
  headers: { 'Content-Type': 'application/json' },
  xsrfCookieName: 'XSRF-TOKEN',
  xsrfHeaderName: 'X-XSRF-TOKEN',
})

// ---- 401 처리 ----------------------------------------------------------
// 요청 도중 세션이 만료되면(401) 라우터가 등록한 핸들러가 로그인 화면으로 보낸다.
// 권한 부족(403)은 그대로 호출한 쪽에서 메시지로 표시한다.
let unauthorizedHandler = null
export const setUnauthorizedHandler = (fn) => {
  unauthorizedHandler = fn
}

api.interceptors.response.use(
  (res) => res,
  (error) => {
    const { config, response } = error
    if (config && response?.status === 401 && !config.skipAuthRedirect && unauthorizedHandler) {
      unauthorizedHandler()
    }
    return Promise.reject(error)
  },
)

export default api
