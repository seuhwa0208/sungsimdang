// 자전거·시설 식별 표시. 서버가 주는 id 값 앞에 접두사만 붙여 보여준다. (bikeNo 는 제거 결정되었으므로 Bike.id 만 사용)
// 표시 방식이 바뀔 때는 이 함수들만 수정한다.
export const formatBikeId = (id) => (id == null ? '-' : `Bike-${id}`)
export const formatFacilityId = (id) => (id == null ? '-' : `FAC-${id}`)

export const formatDistance = (v) =>
  v == null ? '-' : `${Number(v).toLocaleString('ko-KR', { maximumFractionDigits: 1 })} km`
