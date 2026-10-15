<template>
  <div class="py-16 text-center text-sm text-gray-500">
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
