import { locationLabel } from '../constants/locations'
import { facilityTypeLabels, labelOf } from './statusLabels'

// 자전거·시설 식별 표시. 서버가 주는 id 값 앞에 접두사만 붙여 보여준다.
// 백엔드 응답(BikeResponse / FacilityResponse)에는 bikeNo, facilityNo 가 없으므로 id 만 사용한다.
// 표시 방식이 바뀔 때는 이 함수들만 수정한다.
export const formatBikeId = (id) => (id == null ? '-' : `Bike-${id}`)
export const formatFacilityId = (id) => (id == null ? '-' : `FAC-${id}`)

// 와이어프레임의 "시설명"(예: 대전역 엘리베이터). 응답에 시설명 필드가 없어 "위치 + 유형"으로 만든 표시 전용 값이다.
export const facilityName = (f) => (f ? `${locationLabel(f.location)} ${labelOf(facilityTypeLabels, f.facilityType)}` : '-')

export const formatDistance = (v) =>
  v == null ? '-' : `${Number(v).toLocaleString('ko-KR', { maximumFractionDigits: 1 })} km`
