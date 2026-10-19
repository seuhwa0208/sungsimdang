// 목록 API 가 List<Response>(배열)인지 Page<Response>인지 아직 확정되지 않았다.
// 두 형태를 모두 받아 { items, serverPaged, total } 로 정규화한다.
export function normalizeList(data) {
  if (Array.isArray(data)) return { items: data, serverPaged: false, total: data.length }
  if (data && Array.isArray(data.content)) {
    return { items: data.content, serverPaged: true, total: data.totalElements ?? data.content.length }
  }
  return { items: [], serverPaged: false, total: 0 }
}
