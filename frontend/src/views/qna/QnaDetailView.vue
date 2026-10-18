<template>
  <div>
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="error" :error="error" back-to="/qna" @retry="load" />
    <template v-else-if="qna">
      <article class="rounded-lg bg-white shadow-sm ring-1 ring-gray-200">
        <header class="border-b border-gray-200 p-6">
          <div class="flex items-center gap-2">
            <p class="text-xs font-semibold text-brand-600">Q&amp;A</p>
            <StatusBadge :label="labelOf(qnaStatusLabels, qna.status)" :tone="statusTone[qna.status]" />
          </div>
          <h1 class="mt-1 text-xl font-bold text-gray-900 sm:text-2xl">{{ qna.title }}</h1>
          <p class="mt-3 text-sm text-gray-500">
            <template v-if="qna.author">작성자 {{ qna.author }} · </template>작성일 {{ formatDateTime(qna.createdAt) }} · 조회수 {{ qna.hits ?? 0 }}
          </p>
        </header>
        <div class="whitespace-pre-wrap break-words p-6 text-sm leading-7 text-gray-800">{{ qna.content }}</div>
        <footer class="flex flex-wrap justify-between gap-2 border-t border-gray-200 p-4">
          <BaseButton variant="secondary" @click="$router.push('/qna')">목록</BaseButton>
          <!-- 수정 가능 여부(본인·미답변 등)는 서버 정책 확정 전까지 서버 응답(403/409)에 따른다. -->
          <BaseButton v-if="auth.isAuthenticated && qna.status === 'WAITING'" variant="secondary" @click="$router.push(`/qna/${id}/edit`)">질문 수정</BaseButton>
          <BaseButton v-if="auth.isAuthenticated" variant="danger" @click="ask('question')">질문 삭제</BaseButton>
        </footer>
      </article>

      <section class="mt-6 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <h2 class="font-bold text-gray-900">답변</h2>
        <template v-if="qna.status === 'ANSWERED' || qna.answer">
          <p class="mt-3 whitespace-pre-wrap break-words text-sm leading-7 text-gray-800">{{ qna.answer }}</p>
          <p class="mt-2 text-xs text-gray-400">답변(관리자) · 작성일 {{ formatDateTime(qna.answeredAt) }}</p>
        </template>
        <p v-else class="mt-3 text-sm text-gray-500">아직 답변이 등록되지 않았습니다.</p>

        <!-- 답변 작성/수정은 ADMIN 에게만 노출 (서버 권한 검증은 별도) -->
        <form v-if="auth.isAdmin" class="mt-6 space-y-3 border-t border-gray-100 pt-5" @submit.prevent="submitAnswer">
          <BaseInput v-model="answerText" as="textarea" :rows="5" :label="qna.answer ? '답변 수정' : '답변 작성'" :error="answerError" />
          <p v-if="error2" class="text-sm text-red-600" role="alert">{{ error2.message }}</p>
          <div class="flex flex-wrap gap-2">
            <BaseButton type="submit" :loading="submitting">{{ qna.answer ? '답변 수정' : '답변 등록' }}</BaseButton>
            <BaseButton v-if="qna.answer" type="button" variant="danger" @click="ask('answer')">답변 삭제</BaseButton>
          </div>
        </form>
      </section>
    </template>

    <!-- 삭제 확인: 질문 DELETE /api/qna/{id}, 답변 DELETE /api/qna/admin/{id} (권한은 서버가 최종 검증) -->
    <ConfirmDialog
      v-model="confirmOpen"
      :title="confirmKind === 'answer' ? '답변 삭제' : '질문 삭제'"
      :message="confirmKind === 'answer' ? '등록된 답변을 삭제합니다.' : '질문을 삭제합니다.\n삭제한 질문은 복구할 수 없습니다.'"
      confirm-text="삭제"
      :loading="deleting"
      :error="error3?.message"
      @confirm="doDelete"
    />
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router'
import ConfirmDialog from '../../components/common/ConfirmDialog.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseInput from '../../components/common/BaseInput.vue'
import { useAuthStore } from '../../stores/authStore'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { qnaService } from '../../services/qnaService'
import { qnaStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'
import { formatDateTime } from '../../utils/formatDate'

const props = defineProps({ id: { type: String, required: true } })
const auth = useAuthStore()
const { data: qna, loading, error, load } = useAsyncData(() => qnaService.get(props.id))

const answerText = ref('')
const answerError = ref('')
watch(qna, (q) => (answerText.value = q?.answer ?? ''))

const { submitting, error: error2, run } = useSubmit()
async function submitAnswer() {
  answerError.value = answerText.value.trim() ? '' : '답변 내용을 입력해 주세요.'
  if (answerError.value) return
  const hasAnswer = !!qna.value.answer
  const ok = await run(() => (hasAnswer ? qnaService.updateAnswer(props.id, answerText.value) : qnaService.answer(props.id, answerText.value)))
  if (ok) await load() // 서버의 최신 상태(status/answeredAt)로 다시 조회
}

// ---- 삭제 (질문 / 답변) ----
const router = useRouter()
const confirmOpen = ref(false)
const confirmKind = ref('question')
const { submitting: deleting, error: error3, run: runDelete, clearError: clearDeleteError } = useSubmit()
function ask(kind) {
  confirmKind.value = kind
  clearDeleteError()
  confirmOpen.value = true
}
async function doDelete() {
  // 204(본문 없음) 응답은 빈 문자열이라 run() 이 실패로 오해하지 않도록 true 를 돌려준다.
  const ok = await runDelete(async () => {
    if (confirmKind.value === 'answer') await qnaService.removeAnswer(props.id)
    else await qnaService.remove(props.id)
    return true
  })
  if (!ok) return
  confirmOpen.value = false
  if (confirmKind.value === 'answer') await load()
  else router.push('/qna')
}
</script>
