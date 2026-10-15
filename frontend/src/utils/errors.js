// 서버 오류 응답의 정확한 JSON 구조는 아직 확인되지 않았다.
// message / error / errors(배열|객체) / 문자열 본문을 모두 방어적으로 처리한다.
export function parseError(error) {
  const status = error?.response?.status ?? null
  const data = error?.response?.data
  const fieldErrors = {}
  let message = ''

  if (typeof data === 'string' && data.trim() && !data.trim().startsWith('<')) {
    message = data
  } else if (data && typeof data === 'object') {
    message = data.message || data.error || ''
    const errs = data.errors || data.fieldErrors
    if (Array.isArray(errs)) {
      errs.forEach((e) => {
        const field = e.field || e.name
        if (field) fieldErrors[field] = e.defaultMessage || e.message || '올바르지 않은 값입니다.'
      })
    } else if (errs && typeof errs === 'object') {
      Object.assign(fieldErrors, errs)
    }
  }

  if (!message) {
    if (!error?.response) message = '서버에 연결할 수 없습니다. 네트워크 상태를 확인해 주세요.'
    else if (status === 400) message = '요청 값이 올바르지 않습니다.'
    else if (status === 401) message = '로그인이 필요하거나 세션이 만료되었습니다.'
    else if (status === 403) message = '접근 권한이 없습니다.'
    else if (status === 404) message = '대상을 찾을 수 없습니다.'
    else if (status === 409) message = '요청이 현재 상태와 충돌합니다. 최신 내용을 확인해 주세요.'
    else message = '서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.'
  }
  return { status, message, fieldErrors }
}
