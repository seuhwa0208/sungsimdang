import api from './api'

// TODO(미확정): 시설 REST 경로는 PRD 9.3 의 "제안" 경로이다. 실제 Controller 가 확정되면 이 파일만 수정한다.
export const facilityService = {
  list: (params) => api.get('/facilities', { params }).then((r) => r.data),
  get: (id) => api.get(`/facilities/${id}`).then((r) => r.data),
  create: (payload) => api.post('/facilities', payload).then((r) => r.data), // FacilityRequest { location, facilityType }
  update: (id, payload) => api.put(`/facilities/${id}`, payload).then((r) => r.data),
  updateStatus: (id, facilityStatus) => api.patch(`/facilities/${id}/status`, { facilityStatus }).then((r) => r.data),
  maintenance: (id) => api.get(`/facilities/${id}/maintenance`).then((r) => r.data),
  addMaintenance: (id, faultType) => api.post(`/facilities/${id}/maintenance`, { faultType }).then((r) => r.data), // FacilityFaultRequest
}
