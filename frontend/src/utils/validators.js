// UX 용 클라이언트 검증이다. 최종 검증은 항상 백엔드(SignupRequest / PasswordChangeRequest)가 수행한다.
export const PASSWORD_PATTERN = /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$/
export const PASSWORD_RULE_TEXT = '4~30자, 영문·숫자·특수문자(!@#$%^&*)를 각각 1개 이상 사용해야 합니다.'
const EMAIL_PATTERN = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

export const validatePassword = (v) => {
  if (!v) return '비밀번호를 입력해 주세요.'
  if (v.length < 4 || v.length > 30 || !PASSWORD_PATTERN.test(v)) return PASSWORD_RULE_TEXT
  return ''
}
export const validateEmail = (v, required = true) => {
  if (!v) return required ? '이메일을 입력해 주세요.' : ''
  return EMAIL_PATTERN.test(v) ? '' : '올바른 이메일 형식이 아닙니다.'
}
export const validateLoginId = (v) => {
  if (!v) return '아이디를 입력해 주세요.'
  if (v.length < 4 || v.length > 20) return '최소 4자에서 최대 20자까지 입력해야 합니다.'
  return ''
}
export const required = (label) => (v) => (v && String(v).trim() ? '' : `${label}을(를) 입력해 주세요.`)
