// Mock 모드 전용 시드 데이터. 실제 서버/DB 와 무관하며 새로고침하면 초기화된다.
// 자산은 id로 식별한다. 어댑터는 위치를 실제 Enum으로 변환하고 과거 facilityNo를 응답에서 제거한다.
const daysAgo = (n, h = 10) => {
  const d = new Date()
  d.setDate(d.getDate() - n)
  d.setHours(h, 0, 0, 0)
  return d.toISOString()
}

export function createSeed() {
  return {
    seq: { member: 4, bike: 9, facility: 9, notice: 4, dataFile: 4, qna: 5, bikeMt: 5, facilityMt: 4 },
    members: [
      { id: 1, loginId: 'admin', name: '관리자', email: 'admin@example.com', tel: '010-0000-0001', role: 'ADMIN', status: 'ACTIVE', createdAt: daysAgo(60), password: 'Test123!' },
      { id: 2, loginId: 'manager01', name: '김담당', email: 'manager@example.com', tel: '010-0000-0002', role: 'MANAGER', status: 'ACTIVE', createdAt: daysAgo(45), password: 'Test123!' },
      { id: 3, loginId: 'user01', name: '홍길동', email: 'user01@example.com', tel: '010-0000-0003', role: 'USER', status: 'ACTIVE', createdAt: daysAgo(30), password: 'Test123!' },
    ],
    bikes: [
      { id: 1, location: '대전역', status: 'AVAILABLE', distance: 128.4 },
      { id: 2, location: '대전역', status: 'REPAIRING', distance: 342.1 },
      { id: 3, location: '시청역', status: 'AVAILABLE', distance: 56.0 },
      { id: 4, location: '정부청사역', status: 'UNAVAILABLE', distance: 410.7 },
      { id: 5, location: '유성온천역', status: 'AVAILABLE', distance: 12.5 },
      { id: 6, location: '월평역', status: 'AVAILABLE', distance: 87.3 },
      { id: 7, location: '탄방역', status: 'REPAIRING', distance: 205.9 },
      { id: 8, location: '노은역', status: 'AVAILABLE', distance: 33.2 },
    ],
    bikeMaintenance: [
      { id: 1, bikeId: 2, createdAt: daysAgo(12), status: 'REPAIRING', faultType: 'BRAKE', memberLoginId: 'manager01' },
      { id: 2, bikeId: 2, createdAt: daysAgo(40), status: 'AVAILABLE', faultType: 'TIRE', memberLoginId: 'manager01' },
      { id: 3, bikeId: 4, createdAt: daysAgo(5), status: 'UNAVAILABLE', faultType: 'CHAIN', memberLoginId: 'admin' },
      { id: 4, bikeId: 7, createdAt: daysAgo(2), status: 'REPAIRING', faultType: 'HANDEL', memberLoginId: 'manager01' },
    ],
    facilities: [
      { id: 1, facilityNo: 'FAC-001', facilityType: 'ELEVATOR', location: '대전역', facilityStatus: 'AVAILABLE' },
      { id: 2, facilityNo: 'FAC-002', facilityType: 'ELEVATOR', location: '시청역', facilityStatus: 'REPAIRING' },
      { id: 3, facilityNo: 'FAC-003', facilityType: 'SCREEN_DOOR', location: '정부청사역', facilityStatus: 'AVAILABLE' },
      { id: 4, facilityNo: 'FAC-004', facilityType: 'SCREEN_DOOR', location: '유성온천역', facilityStatus: 'UNAVAILABLE' },
      { id: 5, facilityNo: 'FAC-005', facilityType: 'BIKE_STATION', location: '대전역', facilityStatus: 'AVAILABLE' },
      { id: 6, facilityNo: 'FAC-006', facilityType: 'BIKE_RACK', location: '월평역', facilityStatus: 'AVAILABLE' },
      { id: 7, facilityNo: 'FAC-007', facilityType: 'BIKE_STATION', location: '노은역', facilityStatus: 'AVAILABLE' },
      { id: 8, facilityNo: 'FAC-008', facilityType: 'ELEVATOR', location: '탄방역', facilityStatus: 'AVAILABLE' },
    ],
    facilityMaintenance: [
      { id: 1, facilityId: 2, createdAt: daysAgo(3), facilityStatus: 'REPAIRING', faultType: 'OPERATION_FAILURE', memberLoginId: 'manager01' },
      { id: 2, facilityId: 4, createdAt: daysAgo(6), facilityStatus: 'UNAVAILABLE', faultType: 'DOOR_FAILURE', memberLoginId: 'admin' },
      { id: 3, facilityId: 2, createdAt: daysAgo(50), facilityStatus: 'AVAILABLE', faultType: 'NOISE_VIBRATION', memberLoginId: 'manager01' },
    ],
    notices: [
      { id: 1, title: '시스템 오픈 안내', content: '대전교통공사 통합운영관리시스템이 오픈했습니다.\n많은 이용 바랍니다.', author: '관리자', createdAt: daysAgo(20), updatedAt: daysAgo(20) },
      { id: 2, title: '타슈 정기 점검 안내', content: '매월 첫째 주 수요일 타슈 정기 점검이 진행됩니다.', author: '관리자', createdAt: daysAgo(10), updatedAt: daysAgo(10) },
      { id: 3, title: '엘리베이터 점검 일정', content: '시청역 엘리베이터 점검으로 일부 시간대 이용이 제한됩니다.', author: '관리자', createdAt: daysAgo(3), updatedAt: daysAgo(2) },
    ],
    dataFiles: [
      { id: 1, title: '이용 안내 자료', content: '타슈 이용 방법 안내 자료입니다.', author: '관리자', createdAt: daysAgo(15), updatedAt: daysAgo(15) },
      { id: 2, title: '시설 이용 수칙', content: '엘리베이터 및 스크린도어 이용 수칙입니다.', author: '관리자', createdAt: daysAgo(8), updatedAt: daysAgo(8) },
      { id: 3, title: '자주 묻는 질문 모음', content: '문의가 많은 내용을 정리했습니다.', author: '관리자', createdAt: daysAgo(1), updatedAt: daysAgo(1) },
    ],
    qna: [
      { id: 1, memberId: 3, title: '타슈 이용 시간이 궁금합니다', content: '운영 시간을 알려주세요.', answer: '매일 05:00~24:00 이용 가능합니다.', status: 'ANSWERED', hits: 12, createdAt: daysAgo(9), updatedAt: daysAgo(8), answeredAt: daysAgo(8) },
      { id: 2, memberId: 3, title: '대전역 자전거가 부족해요', content: '출근 시간대에 자전거가 부족합니다.', answer: null, status: 'WAITING', hits: 4, createdAt: daysAgo(2), updatedAt: daysAgo(2), answeredAt: null },
      { id: 3, memberId: 2, title: '시설 고장 접수 방법', content: '고장 접수는 어디서 하나요?', answer: null, status: 'WAITING', hits: 1, createdAt: daysAgo(1), updatedAt: daysAgo(1), answeredAt: null },
      { id: 4, memberId: 3, title: '비밀번호 변경이 안 됩니다', content: '특수문자를 넣었는데 오류가 납니다.', answer: '!@#$%^&* 중 하나를 포함해 주세요.', status: 'ANSWERED', hits: 7, createdAt: daysAgo(14), updatedAt: daysAgo(13), answeredAt: daysAgo(13) },
    ],
  }
}
