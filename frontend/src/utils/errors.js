// 서버 오류 응답을 화면용 { status, message, fieldErrors } 로 바꾼다.
//
// 백엔드 현황(확인된 사실):
//  - @RestControllerAdvice 가 없어서 서비스의 IllegalArgumentException / IllegalStateException 은 HTTP 500 으로 내려간다.
//  - Spring Boot 기본 설정(server.error.include-message=never)에서는 응답 본문에 한글 메시지가 포함되지 않는다.
//    본문은 { timestamp, status, error: "Internal Server Error", path } 형태라 error 값을 그대로 보여주면 영어 문구가 노출된다.
// 그래서 본문에 "한글이 들어 있는 message"가 있을 때만 그대로 쓰고, 아니면 상태 코드별 안내 문구를 쓴다.
// (백엔드가 나중에 예외 처리기를 추가해 message 를 내려주면 자동으로 그 문구가 표시된다.)
const hasKorean = (s) => /[가-힣]/.test(s)

export function parseError(error) {
  const status = error?.response?.status ?? null
  const data = error?.response?.data
  const fieldErrors = {}
  let message = ''

  if (typeof data === 'string' && data.trim() && !data.trim().startsWith('<') && hasKorean(data)) {
    message = data
  } else if (data && typeof data === 'object') {
    if (typeof data.message === 'string' && hasKorean(data.message)) message = data.message
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

  // 현재 Security의 /error/403 forward 대상이 없으면 본래 접근 거절이 404로 보인다.
  if (status === 404 && (data?.path === '/error/403' || /No static resource error\/403/.test(data?.message ?? data?.trace ?? ''))) {
    message = '요청을 처리할 수 없습니다. 관리자에게 문의해 주세요.'
  }

  if (!message) {
    if (!error?.response) message = error?.message && !error?.request && hasKorean(error.message) ? error.message : '서버에 연결할 수 없습니다. 네트워크 상태를 확인해 주세요.'
    else if (status === 400) message = '입력값이 올바르지 않습니다. 내용을 확인해 주세요.'
    else if (status === 401) message = '로그인이 필요하거나 세션이 만료되었습니다.'
    else if (status === 403) message = '접근 권한이 없습니다.'
    else if (status === 404) message = '대상을 찾을 수 없습니다.'
    else if (status === 409) message = '요청이 현재 상태와 충돌합니다. 최신 내용을 확인해 주세요.'
    else if (status === 500) message = '요청을 처리하지 못했습니다. 입력 내용이나 현재 상태(이미 처리된 요청 등)를 확인해 주세요.'
    else message = '서버 오류가 발생했습니다. 잠시 후 다시 시도해 주세요.'
  }
  return { status, message, fieldErrors }
}
