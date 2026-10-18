import { computed, ref, watch } from 'vue'

/**
 * 이미 메모리에 있는 배열(computed/ref)을 n개씩 나눠 보여주는 클라이언트 페이징.
 * 대시보드 요약 패널처럼 서버 페이징이 필요 없는 곳에서 쓴다. (검색·URL query 연동이 필요하면 useListView 사용)
 */
export function usePaging(source, pageSize = 3) {
  const page = ref(1)
  const totalPages = computed(() => Math.max(1, Math.ceil(source.value.length / pageSize)))
  const pageItems = computed(() => source.value.slice((page.value - 1) * pageSize, page.value * pageSize))
  // 항목이 줄어 현재 페이지가 사라지면 마지막 페이지로 당긴다.
  watch(totalPages, (n) => {
    if (page.value > n) page.value = n
  })
  return { page, totalPages, pageItems, pageSize }
}
