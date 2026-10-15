import api from './axios'
export const memberApi = {
  register: (data) => api.post('/api/members', data),
  me: () => api.get('/api/members/me'),
  update: (data) => api.put('/api/members/me', data),
  remove: () => api.delete('/api/members/me')
}
