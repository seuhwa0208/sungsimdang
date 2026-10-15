import api from './api'

// TODO(미확정): 공지사항 경로는 PRD 9.3 의 "제안" 경로이다.
export const noticeService = {
  list: (params) => api.get('/notices', { params }).then((r) => r.data),
  get: (id) => api.get(`/notices/${id}`).then((r) => r.data),
  create: (payload) => api.post('/notices', payload).then((r) => r.data), // { title, content }
  update: (id, payload) => api.put(`/notices/${id}`, payload).then((r) => r.data),
  remove: (id) => api.delete(`/notices/${id}`).then((r) => r.data),
}
