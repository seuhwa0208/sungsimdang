import api from './api'

// API 명세서 1차 확정 — @RequestMapping("/api/facilities")
// 관리자용과 사용자용(/member) 엔드포인트가 분리되어 있다.
const BASE = '/facilities'
const unwrap = (r) => r.data
const rowFacilityId = (r) => r?.facilityId ?? r?.facility?.id

export const facilityService = {
  // ---- 관리자 ----
  list: (params) => api.get(BASE, { params }).then(unwrap), // 목록 - 최신순
  listByStatus: (params) => api.get(`${BASE}/status`, { params }).then(unwrap), // 목록 - 상태별 정렬
  search: (params) => api.get(`${BASE}/search`, { params }).then(unwrap), // 검색(위치로만)
  get: (id) => api.get(`${BASE}/${id}`).then(unwrap),
  create: (payload) => api.post(BASE, payload).then(unwrap), // FacilityRequest { location, facilityType }
  update: (id, payload) => api.put(`${BASE}/${id}`, payload).then(unwrap),
  remove: (id) => api.delete(`${BASE}/${id}`).then(unwrap),

  // 상태 변경은 명세서상 PATCH /{facilityId}/maintenance (Repairing -> Available). body 형식은 { facilityStatus } 로 가정.
  updateStatus: (id, facilityStatus) => api.patch(`${BASE}/${id}/maintenance`, { facilityStatus }).then(unwrap),
  // 시설 고장 접수 - 목록 (GET /maintenance). 시설별 조회 경로는 명세서에 없다.
  maintenanceAll: (params) => api.get(`${BASE}/maintenance`, { params }).then(unwrap),
  // 시설 한 곳의 이력이 필요한 화면용: 전체 목록에서 해당 시설 행만 거른다.
  // 응답 행에 facilityId(또는 facility.id)가 없으면 거를 수 없으므로 원본을 그대로 돌려준다. (백엔드 DTO 확정 후 확인 필요)
  maintenance: async (id) => {
    const data = await facilityService.maintenanceAll()
    const rows = Array.isArray(data) ? data : data?.content
    if (!Array.isArray(rows) || !rows.some((r) => rowFacilityId(r) != null)) return data
    return rows.filter((r) => String(rowFacilityId(r)) === String(id))
  },
  addMaintenance: (id, faultType) => api.post(`${BASE}/${id}/maintenance`, { faultType }).then(unwrap), // 시설 고장 접수 - 등록

  // ---- 사용자 ----
  memberList: (params) => api.get(`${BASE}/member`, { params }).then(unwrap), // 목록 - 최신순
  memberSearch: (params) => api.get(`${BASE}/member/search`, { params }).then(unwrap),
  memberGet: (id) => api.get(`${BASE}/member/${id}`).then(unwrap),
}
