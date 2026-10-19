// 전체 이력 응답의 자산 ID로 구분한다. 다른 자산의 기록을 해당 자산의 이력으로 표시하지 않는다.
export function recordsForAsset(data, key, id) {
  const rows = Array.isArray(data) ? data : data?.content
  if (!Array.isArray(rows)) throw new Error('정비 이력 응답 형식을 확인할 수 없습니다.')
  if (rows.some(row => row[key] == null)) {
    throw new Error('현재는 개별 정비 이력을 확인할 수 없습니다. 담당자에게 문의해 주세요.')
  }
  return rows.filter(row => String(row[key]) === String(id))
}
