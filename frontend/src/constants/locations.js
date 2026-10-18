// 위치는 백엔드 enum(BikeLocation / FacilityLocation, 두 enum 의 상수는 동일)이다.
// 서버로는 항상 enum 원문(PANAM, DAEJEON ...)을 보내고, 한글 역 이름은 화면 표시용 레이블로만 쓴다.
// 값 ↔ 역 이름 대응은 backend BikeLocation.java 의 주석을 따랐다. (ORYONG = 오룡)
const STATIONS = [
  ['PANAM', '판암역'],
  ['SINHEUNG', '신흥역'],
  ['DAEDONG', '대동역'],
  ['DAEJEON', '대전역'],
  ['JUNGANGNO', '중앙로역'],
  ['JUNG_GU_OFFICE', '중구청역'],
  ['SEODAEJEON_NEGEORI', '서대전네거리역'],
  ['ORYONG', '오룡역'],
  ['YONGMUN', '용문역'],
  ['TANBANG', '탄방역'],
  ['CITY_HALL', '시청역'],
  ['GOVERNMENT_COMPLEX_DAEJEON', '정부청사역'],
  ['GALMA', '갈마역'],
  ['WOLPYEONG', '월평역'],
  ['GAPCHEON', '갑천역'],
  ['YUSEONG_SPA', '유성온천역'],
  ['GUAM', '구암역'],
  ['NATIONAL_CEMETERY', '현충원역'],
  ['WORLD_CUP_STADIUM', '월드컵경기장역'],
  ['NOEUN', '노은역'],
  ['JIJOK', '지족역'],
  ['BANSEOK', '반석역'],
]

export const locationLabels = Object.fromEntries(STATIONS)

// <BaseSelect :options> 용: [{ value: 'PANAM', label: '판암역' }, ...]
export const LOCATION_OPTIONS = STATIONS.map(([value, label]) => ({ value, label }))

export const locationLabel = (value) => locationLabels[value] ?? value ?? '-'

// 위치 검색(부분 일치): 한글 역 이름 또는 enum 원문에 검색어가 포함되면 true
export const locationMatches = (value, keyword) => {
  const kw = String(keyword ?? '').trim().toLowerCase()
  if (!kw) return true
  return locationLabel(value).toLowerCase().includes(kw) || String(value ?? '').toLowerCase().includes(kw)
}
