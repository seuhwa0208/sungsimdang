import { computed, ref, watch } from 'vue'
import { usePaging } from './usePaging'

/**
 * 이미 메모리에 있는 배열에 "검색 + 상태 선택 + 초기화 + n개씩 페이징"을 붙인다. (대시보드 요약 패널용)
 * - fields(row): 검색 대상 값 배열. 검색어는 공백 제거·대소문자 무시로 부분 일치.
 * - statusOf(row): 상태 값을 돌려주면 "상태" 선택(status)으로 거를 수 있다. 선택하면 바로 적용된다. (생략하면 상태 필터 없음)
 * - 검색/초기화/상태 변경하면 1페이지로 돌아간다.
 */
export function usePanelList(rows, fields, pageSize = 3, statusOf = null) {
  const keyword = ref('') // 입력창 값
  const applied = ref('') // 검색 버튼을 눌러 실제로 적용된 값
  const status = ref('') // 상태 선택 값 ('' = 전체)

  const filtered = computed(() => {
    const kw = applied.value.trim().toLowerCase()
    return rows.value.filter((r) => {
      if (status.value && statusOf && statusOf(r) !== status.value) return false
      return !kw || fields(r).some((v) => String(v ?? '').toLowerCase().includes(kw))
    })
  })
  const paging = usePaging(filtered, pageSize)

  const search = () => {
    applied.value = keyword.value.trim()
    paging.page.value = 1
  }
  const reset = () => {
    keyword.value = ''
    applied.value = ''
    status.value = ''
    paging.page.value = 1
  }
  watch(status, () => {
    paging.page.value = 1
  })
  return { keyword, applied, status, filtered, search, reset, ...paging }
}
