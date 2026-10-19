<template>
  <div class="dtc-auth dtc-auth-signup mx-auto max-w-lg rounded-xl bg-white p-8 shadow-sm ring-1 ring-gray-200">
    <div class="dtc-auth-emblem" aria-hidden="true"><svg viewBox="0 0 32 32" fill="none"><circle cx="13" cy="10" r="5" /><path d="M3 27v-3a10 10 0 0 1 16-8M25 17v12M19 23h12" /></svg></div>
    <h1 class="text-center text-2xl font-bold">회원가입</h1>
    <p class="mb-6 mt-2 text-center text-sm text-gray-600">회원 정보를 입력해 주세요.</p>

    <form class="space-y-4" novalidate @submit.prevent="submit">
      <BaseInput v-model="form.loginId" label="아이디" required autocomplete="username" hint="4~20자 · 가입 시 중복 여부를 확인합니다." :error="errors.loginId" />
      <BaseInput v-model="form.password" label="비밀번호" type="password" required autocomplete="new-password" :hint="PASSWORD_RULE_TEXT" :error="errors.password" />
      <BaseInput v-model="form.passwordCheck" label="비밀번호 확인" type="password" required autocomplete="new-password" :error="errors.passwordCheck" />
      <BaseInput v-model="form.name" label="이름" required :error="errors.name" />
      <BaseInput v-model="form.email" label="이메일" type="email" required autocomplete="email" :error="errors.email" />
      <BaseInput v-model="form.tel" label="전화번호 (선택)" type="tel" placeholder="010-0000-0000" :error="errors.tel" />

      <fieldset class="space-y-2 rounded-md bg-gray-50 p-4">
        <label class="flex items-center gap-2 text-sm"><input v-model="form.termsAgreed" type="checkbox" class="h-4 w-4" /> 이용약관에 동의합니다. (필수)</label>
        <FormFieldError :message="errors.termsAgreed" />
        <label class="flex items-center gap-2 text-sm"><input v-model="form.privacyAgreed" type="checkbox" class="h-4 w-4" /> 개인정보 수집 및 이용에 동의합니다. (필수)</label>
        <FormFieldError :message="errors.privacyAgreed" />
      </fieldset>

      <p v-if="error" class="text-sm text-red-600" role="alert">{{ error.message }}</p>
      <div class="flex justify-end gap-2">
        <BaseButton type="button" variant="secondary" :disabled="submitting" @click="router.push({ name: 'login' })">취소</BaseButton>
        <BaseButton type="submit" :loading="submitting">회원가입</BaseButton>
      </div>
    </form>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import FormFieldError from '../../components/common/FormFieldError.vue'
import { memberService } from '../../services/memberService'
import { useSubmit } from '../../composables/useSubmit'
import { PASSWORD_RULE_TEXT, validateEmail, validateLoginId, validatePassword, required } from '../../utils/validators'

const router = useRouter()
const { submitting, error, run } = useSubmit()

// role / status 는 요청에 포함하지 않는다. (서버가 USER / ACTIVE 로 지정)
const form = reactive({ loginId: '', password: '', passwordCheck: '', name: '', email: '', tel: '', termsAgreed: false, privacyAgreed: false })
const errors = reactive({})

function validate() {
  Object.keys(errors).forEach((k) => delete errors[k])
  errors.loginId = validateLoginId(form.loginId.trim())
  errors.password = validatePassword(form.password)
  errors.passwordCheck = !form.passwordCheck ? '비밀번호 확인을 입력해 주세요.' : form.password !== form.passwordCheck ? '비밀번호가 일치하지 않습니다.' : ''
  errors.name = required('이름')(form.name)
  errors.email = validateEmail(form.email.trim())
  errors.termsAgreed = form.termsAgreed ? '' : '이용약관에 동의해 주세요.'
  errors.privacyAgreed = form.privacyAgreed ? '' : '개인정보 수집 및 이용에 동의해 주세요.'
  return Object.values(errors).every((v) => !v)
}

async function submit() {
  if (!validate()) return
  const payload = {
    loginId: form.loginId.trim(),
    password: form.password,
    passwordCheck: form.passwordCheck,
    name: form.name.trim(),
    email: form.email.trim(),
    tel: form.tel.trim() || null,
    termsAgreed: form.termsAgreed,
    privacyAgreed: form.privacyAgreed,
  }
  const ok = await run(() => memberService.register(payload))
  if (ok) return router.replace({ name: 'login', query: { registered: '1' } })
  // 서버의 필드별 검증 오류가 있으면 해당 입력 아래에 표시
  Object.assign(errors, error.value?.fieldErrors || {})
}
</script>
