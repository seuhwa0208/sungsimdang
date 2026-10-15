import api from './api'

// 자료실(DataFiles). 파일 업로드/다운로드가 아닌 게시글 기능이다.
// TODO(미확정): 경로는 PRD 9.3 의 "제안" 경로이다.
export const dataRoomService = {
  list: (params) => api.get('/datafiles', { params }).then((r) => r.data),
  get: (id) => api.get(`/datafiles/${id}`).then((r) => r.data),
  create: (payload) => api.post('/datafiles', payload).then((r) => r.data),
  update: (id, payload) => api.put(`/datafiles/${id}`, payload).then((r) => r.data),
  remove: (id) => api.delete(`/datafiles/${id}`).then((r) => r.data),
}
