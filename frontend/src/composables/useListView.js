import { ref, computed, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { normalizeList } from '../utils/list'
import { parseError } from '../utils/errors'

/**
 * 목록 화면 공통 로직: 조회 / 검색 / 필터 / 페이징.
 * - API 가 배열을 반환하면 Vue 에서 검색 → 필터 → 정렬 → 페이지 슬라이스를 처리한다.
 * - API 가 Page(content/totalElements) 를 반환하면 page/size/keyword 를 서버로 보낸다. (이 경우 predicate 는 무시)
 * - 검색어·페이지는 URL query(q, page)에 보존한다.
 */
export function useListView(fetcher, options = {}) {
  // serverSearch: (keyword) => Promise. 지정하면 검색어 제출 시 목록 대신 이 함수로 서버 검색 결과를 받아 온다. (프론트 필터링은 생략)
  const { pageSize = 10, searchFields = () => [], predicate = null, sort = null, watchSources = [], serverSearch = null } = options
  const route = useRoute()
  const router = useRouter()

  const items = ref([])
  const loading = ref(false)
  const error = ref(null)
  const serverPaged = ref(false)
  const serverTotal = ref(0)

  const keyword = ref(String(route.query.q ?? ''))
  const submittedKeyword = ref(keyword.value)
  const page = ref(Math.max(1, Number(route.query.page) || 1))

  async function load() {
    loading.value = true
    error.value = null
    try {
      const params = serverPaged.value
        ? { page: page.value - 1, size: pageSize, keyword: submittedKeyword.value || undefined }
        : undefined
      const kw = submittedKeyword.value.trim()
      const res = normalizeList(serverSearch && kw ? await serverSearch(kw) : await fetcher(params))
      if (res.serverPaged && !serverPaged.value) {
        serverPaged.value = true
        return await load() // 서버 페이징 확인 → 파라미터를 붙여 다시 조회
      }
      items.value = res.items
      serverTotal.value = res.total
    } catch (e) {
      error.value = parseError(e)
    } finally {
      loading.value = false
    }
  }

  const filtered = computed(() => {
    if (serverPaged.value) return items.value
    const kw = submittedKeyword.value.trim().toLowerCase()
    let list = items.value
    if (kw && !serverSearch) {
      list = list.filter((it) => searchFields(it).some((v) => String(v ?? '').toLowerCase().includes(kw)))
    }
    if (predicate) list = list.filter(predicate)
    return sort ? [...list].sort(sort) : list
  })

  const total = computed(() => (serverPaged.value ? serverTotal.value : filtered.value.length))
  const totalPages = computed(() => Math.max(1, Math.ceil(total.value / pageSize)))
  const pageItems = computed(() => {
    if (serverPaged.value) return items.value
    const start = (page.value - 1) * pageSize
    return filtered.value.slice(start, start + pageSize)
  })

  function search() {
    submittedKeyword.value = keyword.value
    page.value = 1
  }
  function reset(extraReset) {
    keyword.value = ''
    submittedKeyword.value = ''
    page.value = 1
    if (extraReset) extraReset()
  }

  watch(watchSources, () => {
    page.value = 1
  })
  watch([submittedKeyword, page], () => {
    router.replace({
      query: { ...route.query, q: submittedKeyword.value || undefined, page: page.value > 1 ? String(page.value) : undefined },
    })
    if (serverPaged.value) load()
  })
  if (serverSearch) watch(submittedKeyword, () => load())
  watch(totalPages, (n) => {
    if (!serverPaged.value && page.value > n) page.value = n
  })

  load()

  return { items, loading, error, keyword, submittedKeyword, page, total, totalPages, pageItems, pageSize, load, search, reset, serverPaged }
}
