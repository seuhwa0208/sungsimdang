// 한글 표시 레이블과 서버 Enum 값은 분리한다. 서버로는 항상 Enum 원문을 보낸다.
// 값 출처: backend BikeStatus / BikeFaultType / FacilityStatus / FacilityType /
//          FacilityFaultType / MemberRole / MemberStatus / QnaStatus

export const bikeStatusLabels = {
  AVAILABLE: '이용가능',
  REPAIRING: '정비중',
  UNAVAILABLE: '이용불가',
}

// 주의: 서버 Enum 철자가 HANDEL 이다. 프론트에서 HANDLE 로 고치면 서버와 불일치한다.
export const bikeFaultLabels = {
  BRAKE: '브레이크',
  TIRE: '타이어',
  CHAIN: '체인',
  HANDEL: '핸들',
  SADDLE: '안장',
  LOCK: '잠금장치',
}

export const facilityStatusLabels = {
  AVAILABLE: '이용가능',
  REPAIRING: '점검중',
  UNAVAILABLE: '이용불가',
}

export const facilityTypeLabels = {
  BIKE_STATION: '자전거 대여소',
  BIKE_RACK: '자전거 거치대',
  ELEVATOR: '엘리베이터',
  SCREEN_DOOR: '스크린도어',
}

export const facilityFaultLabels = {
  OPERATION_FAILURE: '작동 불량',
  DOOR_FAILURE: '문 개폐 불량',
  CONTROL_FAILURE: '조작부 고장',
  DISPLAY_FAILURE: '표시장치 고장',
  STRUCTURE_DAMAGE: '시설물 파손',
  NOISE_VIBRATION: '소음·진동 이상',
}

export const qnaStatusLabels = {
  WAITING: '답변 대기',
  ANSWERED: '답변 완료',
}

export const memberRoleLabels = {
  USER: '일반 회원',
  MANAGER: '업무 담당자',
  ADMIN: '관리자',
}

export const memberStatusLabels = {
  ACTIVE: '활성',
  INACTIVE: '비활성',
  KICKED: '강퇴',
}

// 배지 색상 톤. 색상만으로 의미를 전달하지 않도록 항상 텍스트 레이블과 함께 쓴다.
export const statusTone = {
  AVAILABLE: 'green',
  UNAVAILABLE: 'red',
  REPAIRING: 'amber',
  WAITING: 'amber',
  ANSWERED: 'green',
  ACTIVE: 'green',
  INACTIVE: 'gray',
  KICKED: 'red',
}

// 대시보드에서 자전거·시설 상태 배지를 누르면 바뀌는 다음 상태 (백엔드 서비스 규칙 기준)
//  - 이용가능 → 운영중지, 운영중지 → 이용가능 : PATCH /{id}/maintenance { status }
//  - 정비중 → 이용가능(정비 완료)             : PATCH /{id}/status  (정비 이력이 함께 남는다)
// '정비중'으로 바꾸는 방법은 "고장 접수"(POST /{id}/maintenance)뿐이다. 접수는 운영중지 상태에서만 가능하다.
export const STATUS_NEXT = { AVAILABLE: 'UNAVAILABLE', UNAVAILABLE: 'AVAILABLE', REPAIRING: 'AVAILABLE' }
export const nextStatus = (current) => STATUS_NEXT[current]
// 회원 상태 배지는 활성 ↔ 비활성만 오간다. (강퇴는 이 목록에 없어 배지를 눌러도 바뀌지 않는다)
export const MEMBER_ROTATION = ['ACTIVE', 'INACTIVE']
export const nextIn = (rotation, current) => rotation[(rotation.indexOf(current) + 1) % rotation.length]

export const toOptions = (labels) => Object.entries(labels).map(([value, label]) => ({ value, label }))
export const labelOf = (labels, value) => labels[value] ?? value ?? '-'
