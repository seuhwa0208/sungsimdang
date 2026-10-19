import api from './api'
import { recordsForAsset } from './maintenanceContract'

// API 명세서 1차 확정 — @RequestMapping("/api/facilities")
// 관리자용과 사용자용(/member) 엔드포인트가 분리되어 있다.
const BASE = '/facilities'
const unwrap = (r) => r.data

export const facilityService = {
  // ---- 관리자 ----
  list: (params) => api.get(BASE, { params }).then(unwrap), // 목록 - 최신순
  listByStatus: (params) => api.get(`${BASE}/status`, { params }).then(unwrap), // 목록 - 상태별 정렬
  search: (params) => api.get(`${BASE}/search`, { params }).then(unwrap), // 검색(위치로만)
  get: (id) => api.get(`${BASE}/${id}`).then(unwrap),
  create: (payload) => api.post(BASE, payload).then(unwrap), // FacilityRequest { location, facilityType }
  update: (id, payload) => api.put(`${BASE}/${id}`, payload).then(unwrap),
  remove: (id) => api.delete(`${BASE}/${id}`).then(unwrap),

  // 현재 Controller/Request DTO 기준의 상태 변경 API.
  updateStatus: (id, facilityStatus) => api.patch(`${BASE}/${id}/maintenance`, { facilityStatus }).then(unwrap),
  // 시설 고장 접수 - 목록 (GET /maintenance). 시설별 조회 경로는 명세서에 없다.
  maintenanceAll: (params) => api.get(`${BASE}/maintenance`, { params }).then(unwrap),
  // 시설 한 곳의 이력이 필요한 화면용: 전체 목록에서 해당 시설 행만 거른다.
  maintenance: async (id) => recordsForAsset(await facilityService.maintenanceAll(), 'facilityId', id),
  addMaintenance: (id, faultType) => api.post(`${BASE}/${id}/maintenance`, { faultType }).then(unwrap), // 시설 고장 접수 - 등록

  // 정비 완료는 별도 API에서 새 AVAILABLE 이력을 생성한다 (body 없음).
  completeMaintenance: (id) => api.patch(`${BASE}/${id}/status`).then(unwrap),

  // ---- 사용자 ----
  memberList: (params) => api.get(`${BASE}/member`, { params }).then(unwrap), // 목록 - 최신순
  memberSearch: (params) => api.get(`${BASE}/member/search`, { params }).then(unwrap),
  memberGet: (id) => api.get(`${BASE}/member/${id}`).then(unwrap),
}
