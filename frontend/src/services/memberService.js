import api from './api'

// API 명세서 1차 확정 — @RequestMapping("/api/members") 와 @RequestMapping("/api/admin")
export const memberService = {
  register: (payload) => api.post('/members/signup', payload).then((r) => r.data), // 회원 가입
  // 401 은 "로그인 실패"이므로 전역 세션 만료 처리를 건너뛴다.
  login: (payload) => api.post('/members/login', payload, { skipAuthRedirect: true }).then((r) => r.data),
  logout: () => api.post('/members/logout', null, { skipAuthRedirect: true }).then((r) => r.data),
  me: () => api.get('/members/me', { skipAuthRedirect: true }).then((r) => r.data), // 마이 페이지
  updateMe: (payload) => api.put('/members/me', payload).then((r) => r.data), // { name, email, tel }
  changePassword: (payload) => api.put('/members/password', payload).then((r) => r.data), // { password, newPassword, passwordCheck }
  // 회원 탈퇴 = POST /members/inactive (소프트 삭제). 비밀번호 재확인 요구 여부는 서버 확정 후 확인.
  withdraw: () => api.post('/members/inactive').then((r) => r.data),
}

// 관리자 회원 관리 (/api/admin). 화면 코드는 updateStatus(id, 'ACTIVE'|'INACTIVE'|'KICKED') 를 그대로 쓰고,
// 아래에서 명세서의 activate / inactivate / kick 경로로 나눠 호출한다.
const STATUS_PATH = { ACTIVE: 'activate', INACTIVE: 'inactivate', KICKED: 'kick' }

export const adminMemberService = {
  list: (params) => api.get('/admin', { params }).then((r) => r.data),
  get: (id) => api.get(`/admin/${id}`).then((r) => r.data),
  getByLoginId: (loginId) => api.get(`/admin/login-id/${encodeURIComponent(loginId)}`).then((r) => r.data),
  update: (id, payload) => api.put(`/admin/${id}`, payload).then((r) => r.data), // 회원 정보 수정
  updateRole: (id, role) => api.patch(`/admin/${id}/role`, { role }).then((r) => r.data),
  updateStatus: (id, status) => {
    const path = STATUS_PATH[status]
    if (!path) return Promise.reject(new Error(`지원하지 않는 회원 상태입니다: ${status}`))
    return api.patch(`/admin/${id}/${path}`).then((r) => r.data)
  },
}
