<template>
  <div class="mx-auto max-w-md rounded-xl bg-white p-8 shadow-sm ring-1 ring-gray-200">
    <h1 class="text-center text-2xl font-bold">로그인</h1>
    <p class="mb-6 mt-2 text-center text-sm text-gray-500">대전교통공사 통합운영관리시스템에 오신 것을 환영합니다.</p>

    <p v-if="$route.query.expired" class="mb-4 rounded-md bg-amber-50 p-3 text-sm text-amber-800">세션이 만료되었습니다. 다시 로그인해 주세요.</p>
    <p v-else-if="$route.query.registered" class="mb-4 rounded-md bg-green-50 p-3 text-sm text-green-800">회원가입이 완료되었습니다. 로그인해 주세요.</p>
    <p v-else-if="$route.query.redirect" class="mb-4 rounded-md bg-blue-50 p-3 text-sm text-blue-800">로그인이 필요한 페이지입니다.</p>

    <form class="space-y-4" novalidate @submit.prevent="submit">
      <BaseInput v-model="form.loginId" label="아이디" placeholder="아이디를 입력하세요" autocomplete="username" required :error="errors.loginId" />
      <BaseInput v-model="form.password" label="비밀번호" type="password" placeholder="비밀번호를 입력하세요" autocomplete="current-password" required :error="errors.password" />
      <label class="flex items-center gap-2 text-sm text-gray-700"><input v-model="remember" type="checkbox" class="h-4 w-4" /> 아이디 저장</label>
      <p v-if="message" class="text-sm text-red-600" role="alert">{{ message }}</p>

      <!-- 로그인 버튼은 하나. 로그인 후 서버가 알려주는 권한에 따라 이동한다. -->
      <BaseButton type="submit" class="w-full" :loading="auth.loading" :disabled="auth.loading">로그인</BaseButton>
      <BaseButton type="button" variant="secondary" class="w-full" :disabled="auth.loading" @click="$router.push('/signup')">회원가입</BaseButton>
    </form>
    <p v-if="USE_MOCK" class="mt-4 rounded-md bg-gray-50 p-3 text-xs text-gray-500">
      Mock 계정: admin / manager01 / user01 (비밀번호 모두 <code>Test123!</code>)
    </p>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import { useAuthStore } from '../../stores/authStore'
import { USE_MOCK } from '../../services/api'
import { parseError } from '../../utils/errors'

const SAVED_ID_KEY = 'saved-login-id' // 아이디만 저장한다. 비밀번호는 절대 저장하지 않는다.

const auth = useAuthStore()
const route = useRoute()
const router = useRouter()

let savedId = ''
try {
  savedId = localStorage.getItem(SAVED_ID_KEY) || ''
} catch {
  /* 저장소 접근 불가 시 무시 */
}
const form = reactive({ loginId: savedId, password: '' })
const remember = ref(!!savedId)
const errors = reactive({ loginId: '', password: '' })
const message = ref('')

async function submit() {
  message.value = ''
  errors.loginId = form.loginId.trim() ? '' : '아이디를 입력해 주세요.'
  errors.password = form.password ? '' : '비밀번호를 입력해 주세요.'
  if (errors.loginId || errors.password) return

  try {
    const me = await auth.login({ loginId: form.loginId.trim(), password: form.password })
    try {
      if (remember.value) localStorage.setItem(SAVED_ID_KEY, form.loginId.trim())
      else localStorage.removeItem(SAVED_ID_KEY)
    } catch {
      /* ignore */
    }

    // 오픈 리다이렉트 방지: 같은 사이트 내부 경로만 허용
    const redirect = String(route.query.redirect || '')
    if (redirect.startsWith('/') && !redirect.startsWith('//')) return router.replace(redirect)
    router.replace(['MANAGER', 'ADMIN'].includes(me.role) ? '/dashboard' : '/')
  } catch (e) {
    message.value = e.response ? parseError(e).message : e.message || parseError(e).message
  }
}
</script>
