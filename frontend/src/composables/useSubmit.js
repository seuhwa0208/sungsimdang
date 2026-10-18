import { ref } from 'vue'
import { parseError } from '../utils/errors'

/** 제출/실행 중복 방지 + 오류 파싱. run(fn) 이 성공하면 결과를, 실패하면 null 을 반환한다. */
export function useSubmit() {
  const submitting = ref(false)
  const error = ref(null) // { status, message, fieldErrors }

  async function run(fn) {
    if (submitting.value) return null
    submitting.value = true
    error.value = null
    try {
      const result = await fn()
      return result === undefined || result === null || result === '' ? true : result
    } catch (e) {
      error.value = parseError(e)
      return null
    } finally {
      submitting.value = false
    }
  }
  const clearError = () => {
    error.value = null
  }
  return { submitting, error, run, clearError }
}
