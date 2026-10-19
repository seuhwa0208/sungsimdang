<template>
  <span :class="['inline-flex items-center gap-1.5 rounded-full px-2.5 py-0.5 text-xs font-semibold', tones[tone] || tones.gray]">
    <span class="h-1.5 w-1.5 rounded-full bg-current" aria-hidden="true" />
    {{ label }}
  </span>
</template>

<script setup>
// 색상 + 텍스트 레이블을 항상 함께 표시한다.
defineProps({
  label: { type: String, required: true },
  tone: { type: String, default: 'gray' }, // green | red | amber | blue | gray
})
const tones = {
  green: 'bg-green-100 text-green-800',
  red: 'bg-red-100 text-red-800',
  amber: 'bg-amber-100 text-amber-800',
  blue: 'bg-blue-100 text-blue-800',
  gray: 'bg-gray-100 text-gray-700',
}
</script>
