<template>
  <div class="dtc-empty py-16 text-center text-sm text-gray-500">
    <svg class="dtc-empty-icon" viewBox="0 0 56 56" fill="none" aria-hidden="true"><rect x="13" y="9" width="30" height="35" rx="7" /><path d="M13 31h30M20 17h16M21 44l-5 7M35 44l5 7" /><circle cx="21" cy="37" r="1.5" /><circle cx="35" cy="37" r="1.5" /></svg>
    <p class="font-medium text-gray-600">{{ title }}</p>
    <p v-if="description" class="mt-1">{{ description }}</p>
    <div v-if="$slots.default" class="mt-4"><slot /></div>
  </div>
</template>

<script setup>
defineProps({
  title: { type: String, default: '표시할 내용이 없습니다.' },
  description: { type: String, default: '' },
})
</script>
