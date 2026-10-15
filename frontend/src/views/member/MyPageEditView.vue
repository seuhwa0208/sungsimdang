<template>
  <div>
    <PageHeader title="마이페이지" />
    <MyPageNav />
    <LoadingState v-if="!auth.currentUser" />
    <form v-else class="max-w-lg space-y-4 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200" novalidate @submit.prevent="submit">
      <BaseInput :model-value="auth.currentUser.loginId" label="아이디" disabled />
      <BaseInput v-model="form.name" label="이름" required :error="errors.name" />
      <BaseInput v-model="form.email" label="이메일" type="email" required :error="errors.email" />
      <BaseInput v-model="form.tel" label="전화번호" type="tel" :error="errors.tel" />
      <p v-if="error" class="text-sm text-red-600" role="alert">{{ error.message }}</p>
      <p v-if="saved" class="text-sm text-green-700" role="status">정보가 수정되었습니다.</p>
      <BaseButton type="submit" :loading="submitting">저장</BaseButton>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref, watchEffect } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import MyPageNav from '../../components/common/MyPageNav.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import { useAuthStore } from '../../stores/authStore'
import { useSubmit } from '../../composables/useSubmit'
import { memberService } from '../../services/memberService'
import { validateEmail, required } from '../../utils/validators'

const auth = useAuthStore()
const { submitting, error, run } = useSubmit()
const form = reactive({ name: '', email: '', tel: '' })
const errors = reactive({})
const saved = ref(false)

let initialized = false
watchEffect(() => {
  const u = auth.currentUser
  if (u && !initialized) {
    initialized = true
    Object.assign(form, { name: u.name ?? '', email: u.email ?? '', tel: u.tel ?? '' })
  }
})

async function submit() {
  saved.value = false
  errors.name = required('이름')(form.name)
  errors.email = validateEmail(form.email.trim())
  if (errors.name || errors.email) return

  const ok = await run(() => memberService.updateMe({ name: form.name.trim(), email: form.email.trim(), tel: form.tel.trim() || null }))
  if (!ok) return Object.assign(errors, error.value?.fieldErrors || {})
  await auth.refreshMe() // 서버 최신 상태로 갱신
  saved.value = true
}
</script>
