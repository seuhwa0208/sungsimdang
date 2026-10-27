import api from './api'
import { recordsForAsset } from './maintenanceContract'

// API 명세서 1차 확정 — @RequestMapping("/api/bike")  (복수형 /bikes 가 아니라 단수형 /bike 이다)
// 자전거는 Bike.id 로 식별한다. 관리자(대시보드)용과 사용자용(/member) 엔드포인트가 분리되어 있다.
const BASE = '/bike'
const unwrap = (r) => r.data

export const bikeService = {
  // ---- 관리자 ----
  list: (params) => api.get(BASE, { params }).then(unwrap), // 목록 - 대시보드 / 최신순
  listByStatus: (params) => api.get(`${BASE}/status`, { params }).then(unwrap), // 목록 - 대시보드 / 상태별 정렬
  get: (id) => api.get(`${BASE}/${id}`).then(unwrap), // 자전거 ID 검색
  create: (location) => api.post(BASE, { location }).then(unwrap), // BikeRequest { location }
  update: (id, location) => api.put(`${BASE}/${id}`, { location }).then(unwrap),
  remove: (id) => api.delete(`${BASE}/${id}`).then(unwrap),

  // 현재 Controller/Request DTO 기준의 상태 변경 API.
  updateStatus: (id, status) => api.patch(`${BASE}/${id}/maintenance`, { status }).then(unwrap),
  // 정비 이력 전체 조회 (GET /maintenance). 자전거별 조회 경로는 명세서에 없다.
  maintenanceAll: (params) => api.get(`${BASE}/maintenance`, { params }).then(unwrap),
  // 자전거 한 대의 이력이 필요한 화면용: 전체 이력에서 해당 자전거 행만 거른다.
  maintenance: async (id) => recordsForAsset(await bikeService.maintenanceAll(), 'bikeId', id),
  // 고장 접수는 UNAVAILABLE 상태에서만 가능하며 새 REPAIRING 이력을 생성한다.
  addMaintenance: (id, faultType) => api.post(`${BASE}/${id}/maintenance`, { faultType }).then(unwrap),

  // 정비 완료는 별도 API에서 새 AVAILABLE 이력을 생성한다 (body 없음).
  completeMaintenance: (id) => api.patch(`${BASE}/${id}/status`).then(unwrap),

  // ---- 사용자 ----
  memberList: (params) => api.get(`${BASE}/member`, { params }).then(unwrap), // 목록 - 최신순
  memberSearch: (params) => api.get(`${BASE}/member/search`, { params }).then(unwrap), // 위치별 검색
  memberGet: (id) => api.get(`${BASE}/member/${id}`).then(unwrap),
}
