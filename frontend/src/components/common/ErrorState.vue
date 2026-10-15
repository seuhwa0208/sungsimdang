<template>
  <div class="rounded-lg border border-red-200 bg-red-50 p-6 text-center" role="alert">
    <p class="text-sm font-medium text-red-800">{{ error?.message || '오류가 발생했습니다.' }}</p>
    <div class="mt-4 flex justify-center gap-2">
      <BaseButton v-if="error?.status === 401" size="sm" @click="$router.push({ name: 'login', query: { redirect: $route.fullPath } })">로그인</BaseButton>
      <BaseButton v-else-if="retryable" size="sm" variant="secondary" @click="$emit('retry')">다시 시도</BaseButton>
      <BaseButton v-if="backTo" size="sm" variant="secondary" @click="$router.push(backTo)">목록으로</BaseButton>
    </div>
  </div>
</template>

<script setup>
import BaseButton from './BaseButton.vue'

defineProps({
  error: { type: Object, default: null }, // parseError() 결과
  retryable: { type: Boolean, default: true },
  backTo: { type: [String, Object], default: null },
})
defineEmits(['retry'])
</script>
