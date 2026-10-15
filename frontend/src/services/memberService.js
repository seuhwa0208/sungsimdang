import api from './api'

// 인증/회원. 경로는 PRD 9.2 문서 기준이며 실제 Controller 와 다르면 이 파일만 수정한다.
export const memberService = {
  register: (payload) => api.post('/members/register', payload).then((r) => r.data),
  // 401 은 "로그인 실패"이므로 전역 세션 만료 처리를 건너뛴다.
  // TODO(미확정): 와이어프레임의 아이디 "중복확인" 용. 응답 형식({ available }) 과 경로는 백엔드 확정 후 맞춘다.
  checkLoginId: (loginId) => api.get('/members/check-login-id', { params: { loginId }, skipAuthRedirect: true }).then((r) => r.data),
  login: (payload) => api.post('/members/login', payload, { skipAuthRedirect: true }).then((r) => r.data),
  logout: () => api.post('/members/logout', null, { skipAuthRedirect: true }).then((r) => r.data),
  me: () => api.get('/members/me', { skipAuthRedirect: true }).then((r) => r.data),
  updateMe: (payload) => api.put('/members/me', payload).then((r) => r.data), // { name, email, tel }
  changePassword: (payload) => api.patch('/members/me/password', payload).then((r) => r.data), // { password, newPassword, passwordCheck }
  // TODO(미확정): 공통 API 문서에만 기재. 실제 구현·비밀번호 재확인 요구 여부 확인 필요.
  withdraw: (payload) => api.delete('/members/me/delete', { data: payload }).then((r) => r.data),
}

export const adminMemberService = {
  list: (params) => api.get('/admin/members', { params }).then((r) => r.data),
  get: (id) => api.get(`/admin/members/${id}`).then((r) => r.data),
  updateRole: (id, role) => api.patch(`/admin/members/${id}/role`, { role }).then((r) => r.data),
  updateStatus: (id, status) => api.patch(`/admin/members/${id}/status`, { status }).then((r) => r.data),
}
