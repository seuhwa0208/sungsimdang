import api from './api'

// 자전거는 Bike.id 로 식별한다. (bikeNo 제거 결정)
export const bikeService = {
  list: (params) => api.get('/bikes', { params }).then((r) => r.data),
  get: (id) => api.get(`/bikes/${id}`).then((r) => r.data),
  create: (location) => api.post('/bikes', { location }).then((r) => r.data), // BikeRequest { location }
  update: (id, location) => api.put(`/bikes/${id}`, { location }).then((r) => r.data),
  updateStatus: (id, status) => api.patch(`/bikes/${id}/status`, { status }).then((r) => r.data), // BikeStatusRequest
  maintenance: (id) => api.get(`/bikes/${id}/maintenance`).then((r) => r.data),
  // 새 이력 INSERT. BikeFaultRequest { faultType }
  addMaintenance: (id, faultType) => api.post(`/bikes/${id}/maintenance`, { faultType }).then((r) => r.data),
}
