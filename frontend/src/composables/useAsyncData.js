import { ref } from 'vue'
import { parseError } from '../utils/errors'

/** 단건/상세 조회용: loading / error / data 를 구분해서 관리한다. */
export function useAsyncData(fetcher, { immediate = true } = {}) {
  const data = ref(null)
  const loading = ref(false)
  const error = ref(null)

  async function load() {
    loading.value = true
    error.value = null
    try {
      data.value = await fetcher()
    } catch (e) {
      error.value = parseError(e)
    } finally {
      loading.value = false
    }
  }
  if (immediate) load()
  return { data, loading, error, load }
}
