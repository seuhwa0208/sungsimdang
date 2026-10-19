<template>
  <div>
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="error" :error="error" :back-to="basePath" @retry="load" />
    <article v-else-if="post" class="rounded-lg bg-white shadow-sm ring-1 ring-gray-200">
      <header class="border-b border-gray-200 p-6">
        <p class="text-xs font-semibold text-brand-600">{{ label }}</p>
        <h1 class="mt-1 text-xl font-bold text-gray-900 sm:text-2xl">{{ post.title }}</h1>
        <p class="mt-3 text-sm text-gray-500">
          <span v-if="post.author">작성자 {{ post.author }} · </span>작성일 {{ formatDateTime(post.createdAt) }}<span v-if="post.hits != null"> · 조회수 {{ post.hits }}</span>
          <span v-if="isEdited"> (수정 {{ formatDateTime(post.updatedAt) }})</span>
        </p>
      </header>
      <!-- 본문은 v-html 이 아닌 텍스트로 렌더링한다 (XSS 방지). 줄바꿈만 유지. -->
      <div class="whitespace-pre-wrap break-words p-6 text-sm leading-7 text-gray-800">{{ post.content }}</div>

      <footer class="flex flex-wrap justify-between gap-2 border-t border-gray-200 p-4">
        <BaseButton variant="secondary" @click="$router.push(basePath)">목록</BaseButton>
        <div v-if="auth.isAdmin" class="flex gap-2">
          <BaseButton variant="secondary" @click="$router.push(`${basePath}/${id}/edit`)">수정</BaseButton>
          <BaseButton variant="danger" @click="confirmOpen = true">삭제</BaseButton>
        </div>
      </footer>
    </article>

    <ConfirmDialog
      v-model="confirmOpen"
      title="게시글 삭제"
      message="이 게시글을 삭제하시겠습니까? 삭제 후에는 되돌릴 수 없습니다."
      confirm-text="삭제"
      danger
      :loading="submitting"
      :error="submitError?.message"
      @confirm="remove"
    />
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import LoadingState from '../common/LoadingState.vue'
import ErrorState from '../common/ErrorState.vue'
import BaseButton from '../common/BaseButton.vue'
import ConfirmDialog from '../common/ConfirmDialog.vue'
import { useAuthStore } from '../../stores/authStore'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { formatDateTime } from '../../utils/formatDate'

const props = defineProps({ id: [String, Number], label: String, basePath: String, service: Object })
const auth = useAuthStore()
const router = useRouter()

const { data: post, loading, error, load } = useAsyncData(() => props.service.get(props.id))
const isEdited = computed(() => post.value?.updatedAt && post.value.updatedAt !== post.value.createdAt)

const confirmOpen = ref(false)
const { submitting, error: submitError, run } = useSubmit()
async function remove() {
  const ok = await run(() => props.service.remove(props.id))
  if (ok) {
    confirmOpen.value = false
    router.replace(props.basePath)
  }
}
</script>
