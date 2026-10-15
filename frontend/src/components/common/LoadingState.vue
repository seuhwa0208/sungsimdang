<template>
  <div class="flex items-center justify-center gap-3 py-16 text-sm text-gray-500" role="status">
    <span class="h-5 w-5 animate-spin rounded-full border-2 border-brand-600 border-t-transparent" aria-hidden="true" />
    {{ message }}
  </div>
</template>

<script setup>
defineProps({ message: { type: String, default: '불러오는 중입니다...' } })
</script>
