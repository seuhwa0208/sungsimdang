// 자전거 식별 표시. bikeNo 는 제거 결정되었으므로 Bike.id 만 사용한다.
// 와이어프레임의 표시 방식이 팀에서 확정되면 이 함수 한 곳만 수정한다.
export const formatBikeId = (id) => (id == null ? '-' : `#${id}`)

export const formatDistance = (v) =>
  v == null ? '-' : `${Number(v).toLocaleString('ko-KR', { maximumFractionDigits: 1 })} km`
