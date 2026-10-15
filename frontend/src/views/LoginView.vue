<template><div class="mx-auto max-w-md rounded-xl bg-white p-8 shadow"><h1 class="mb-6 text-2xl font-bold">로그인</h1><form @submit.prevent="submit" class="space-y-4"><input v-model="userid" class="w-full rounded border p-3" placeholder="아이디"><input v-model="password" type="password" class="w-full rounded border p-3" placeholder="비밀번호"><p v-if="error" class="text-red-600">{{ error }}</p><button class="w-full rounded bg-slate-900 p-3 text-white">로그인</button></form></div></template>
<script setup>
import { ref } from 'vue'; import { useRouter } from 'vue-router'; import { useAuthStore } from '../stores/auth'
const userid=ref(''),password=ref(''),error=ref(''),auth=useAuthStore(),router=useRouter()
async function submit(){try{await auth.login(userid.value,password.value);router.push('/')}catch(e){error.value=e.response?.data?.message||'로그인에 실패했습니다.'}}
</script>