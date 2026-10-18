<template>
  <div class="mx-auto max-w-3xl">
    <PageHeader :title="`${label} ${isEdit ? '수정' : '등록'}`" />
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="loadError" :error="loadError" :back-to="basePath" @retry="load" />
    <form v-else class="space-y-4 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200" novalidate @submit.prevent="submit">
      <!-- 작성자는 로그인 세션에서 서버가 결정하므로 입력받지 않는다. -->
      <BaseInput v-model="form.title" label="제목" required maxlength="100" :error="errors.title" />
      <BaseInput v-model="form.content" as="textarea" :rows="14" label="내용" required :error="errors.content" />
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
import PageHeader from '../common/PageHeader.vue'
import LoadingState from '../common/LoadingState.vue'
import ErrorState from '../common/ErrorState.vue'
import BaseInput from '../common/BaseInput.vue'
import BaseButton from '../common/BaseButton.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { required } from '../../utils/validators'

// 등록/수정 공용 폼 (공지사항·자료실). Request: { title, content }
const props = defineProps({ id: { type: [String, Number], default: null }, label: String, basePath: String, service: Object })
const router = useRouter()
const isEdit = computed(() => props.id != null)

const form = reactive({ title: '', content: '' })
const errors = reactive({})
const { submitting, error, run } = useSubmit()

const { data, loading, error: loadError, load } = useAsyncData(() => props.service.get(props.id), { immediate: isEdit.value })
watch(data, (d) => d && Object.assign(form, { title: d.title ?? '', content: d.content ?? '' }))

async function submit() {
  errors.title = required('제목')(form.title)
  errors.content = required('내용')(form.content)
  if (errors.title || errors.content) return

  const payload = { title: form.title.trim(), content: form.content }
  const result = await run(() => (isEdit.value ? props.service.update(props.id, payload) : props.service.create(payload)))
  if (!result) return Object.assign(errors, error.value?.fieldErrors || {})
  const savedId = (typeof result === 'object' && result.id) || props.id
  router.replace(savedId ? `${props.basePath}/${savedId}` : props.basePath)
}
</script>
