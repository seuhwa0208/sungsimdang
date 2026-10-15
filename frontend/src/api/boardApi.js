import api from './axios'
export const boardApi = {
  list: (params) => api.get('/api/boards', { params }),
  get: (id) => api.get(`/api/boards/${id}`),
  create: (data) => api.post('/api/boards', data),
  update: (id, data) => api.put(`/api/boards/${id}`, data),
  remove: (id) => api.delete(`/api/boards/${id}`)
}
