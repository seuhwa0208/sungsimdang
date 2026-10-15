import api from './axios'
export const adminApi = {
  dashboard: () => api.get('/api/admin/dashboard'),
  members: (params) => api.get('/api/admin/members', { params }),
  toggleMember: (id) => api.patch(`/api/admin/members/${id}/enabled`),
  deleteMember: (id) => api.delete(`/api/admin/members/${id}`),
  boards: (params) => api.get('/api/admin/boards', { params }),
  board: (id) => api.get(`/api/admin/boards/${id}`),
  updateBoard: (id, data) => api.put(`/api/admin/boards/${id}`, data),
  deleteBoard: (id) => api.delete(`/api/admin/boards/${id}`)
}
