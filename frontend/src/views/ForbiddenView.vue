<template>
  <div class="mx-auto max-w-md rounded-lg bg-white p-10 text-center shadow-sm ring-1 ring-gray-200">
    <p class="text-4xl font-bold text-gray-300">403</p>
    <h1 class="mt-3 text-xl font-bold">접근 권한이 없습니다</h1>
    <p class="mt-2 text-sm text-gray-500">이 페이지를 볼 수 있는 권한이 없습니다. 필요하면 관리자에게 문의해 주세요.</p>
    <BaseButton class="mt-6" @click="$router.push('/')">메인으로</BaseButton>
  </div>
</template>

<script setup>
import BaseButton from '../components/common/BaseButton.vue'
</script>
