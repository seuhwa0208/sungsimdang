import api from './api'

// API 명세서 1차 확정 — @RequestMapping("/api/datafiles")
// 자료실은 파일 업로드/다운로드가 아닌 게시글 기능이다. 등록/수정/삭제는 로그인 + 관리자 권한이 필요하다.
export const dataRoomService = {
  list: (params) => api.get('/datafiles', { params }).then((r) => r.data),
  search: (params) => api.get('/datafiles/search', { params }).then((r) => r.data),
  get: (id) => api.get(`/datafiles/${id}`).then((r) => r.data),
  create: (payload) => api.post('/datafiles', payload).then((r) => r.data),
  update: (id, payload) => api.put(`/datafiles/${id}`, payload).then((r) => r.data),
  remove: (id) => api.delete(`/datafiles/${id}`).then((r) => r.data),
}
