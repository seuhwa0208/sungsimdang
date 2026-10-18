import api from './api'

// API 명세서 1차 확정 — @RequestMapping("/api/notices")
// 등록/수정/삭제는 로그인 + 관리자 권한이 필요하다.
export const noticeService = {
  list: (params) => api.get('/notices', { params }).then((r) => r.data),
  search: (params) => api.get('/notices/search', { params }).then((r) => r.data),
  get: (id) => api.get(`/notices/${id}`).then((r) => r.data),
  create: (payload) => api.post('/notices', payload).then((r) => r.data), // { title, content }
  update: (id, payload) => api.put(`/notices/${id}`, payload).then((r) => r.data),
  remove: (id) => api.delete(`/notices/${id}`).then((r) => r.data),
}
