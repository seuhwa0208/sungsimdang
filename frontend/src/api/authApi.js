import api from './axios'
export const authApi = {
  me: () => api.get('/api/auth/me'),
  login: (data) => api.post('/api/auth/login', new URLSearchParams(data), { headers: { 'Content-Type': 'application/x-www-form-urlencoded' } }),
  logout: () => api.post('/api/auth/logout')
}
