<template>
  <div class="mx-auto max-w-3xl">
    <PageHeader :title="isEdit ? '질문 수정' : '질문 등록'" />
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="loadError" :error="loadError" back-to="/qna" @retry="load" />
    <form v-else class="space-y-4 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200" novalidate @submit.prevent="submit">
      <BaseInput v-model="form.title" label="제목" required maxlength="100" :error="errors.title" />
      <BaseInput v-model="form.content" as="textarea" :rows="12" label="내용" required :error="errors.content" />
      <p v-if="error" class="text-sm text-red-600" role="alert">{{ error.message }}</p>
      <div class="flex justify-end gap-2">
        <BaseButton variant="secondary" :disabled="submitting" @click="$router.back()">취소</BaseButton>
        <BaseButton type="submit" :loading="submitting">{{ isEdit ? '수정' : '등록' }}</BaseButton>
      </div>
    </form>
  </div>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '../../components/common/PageHeader.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { qnaService } from '../../services/qnaService'
import { required } from '../../utils/validators'

const props = defineProps({ id: { type: String, default: null } })
const router = useRouter()
const isEdit = computed(() => props.id != null)
const form = reactive({ title: '', content: '' })
const errors = reactive({})
const { submitting, error, run } = useSubmit()

const { data, loading, error: loadError, load } = useAsyncData(() => qnaService.get(props.id), { immediate: isEdit.value })
watch(data, (d) => d && Object.assign(form, { title: d.title ?? '', content: d.content ?? '' }))

async function submit() {
  errors.title = required('제목')(form.title)
  errors.content = required('내용')(form.content)
  if (errors.title || errors.content) return
  const payload = { title: form.title.trim(), content: form.content }
  const result = await run(() => (isEdit.value ? qnaService.update(props.id, payload) : qnaService.create(payload)))
  if (!result) return Object.assign(errors, error.value?.fieldErrors || {})
  const savedId = (typeof result === 'object' && result.id) || props.id
  router.replace(savedId ? `/qna/${savedId}` : '/qna')
}
</script>
