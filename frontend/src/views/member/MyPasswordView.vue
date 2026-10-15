<template>
  <div>
    <PageHeader title="마이페이지" />
    <MyPageNav />
    <form class="max-w-lg space-y-4 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200" novalidate @submit.prevent="submit">
      <!-- 필드명은 현재 PasswordChangeRequest(password / newPassword / passwordCheck) 기준 -->
      <BaseInput v-model="form.password" label="현재 비밀번호" type="password" autocomplete="current-password" required :error="errors.password" />
      <BaseInput v-model="form.newPassword" label="새 비밀번호" type="password" autocomplete="new-password" required :hint="PASSWORD_RULE_TEXT" :error="errors.newPassword" />
      <BaseInput v-model="form.passwordCheck" label="새 비밀번호 확인" type="password" autocomplete="new-password" required :error="errors.passwordCheck" />
      <p v-if="error" class="text-sm text-red-600" role="alert">{{ error.message }}</p>
      <p v-if="saved" class="text-sm text-green-700" role="status">비밀번호가 변경되었습니다.</p>
      <BaseButton type="submit" :loading="submitting">비밀번호 변경</BaseButton>
    </form>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import MyPageNav from '../../components/common/MyPageNav.vue'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import { useSubmit } from '../../composables/useSubmit'
import { memberService } from '../../services/memberService'
import { PASSWORD_RULE_TEXT, validatePassword } from '../../utils/validators'

const { submitting, error, run } = useSubmit()
const form = reactive({ password: '', newPassword: '', passwordCheck: '' })
const errors = reactive({})
const saved = ref(false)

async function submit() {
  saved.value = false
  errors.password = form.password ? '' : '현재 비밀번호를 입력해 주세요.'
  errors.newPassword = validatePassword(form.newPassword)
  errors.passwordCheck = !form.passwordCheck ? '새 비밀번호 확인을 입력해 주세요.' : form.newPassword !== form.passwordCheck ? '새 비밀번호가 일치하지 않습니다.' : ''
  if (errors.password || errors.newPassword || errors.passwordCheck) return

  const ok = await run(() => memberService.changePassword({ ...form }))
  if (!ok) return Object.assign(errors, error.value?.fieldErrors || {})
  Object.assign(form, { password: '', newPassword: '', passwordCheck: '' })
  saved.value = true
}
</script>
