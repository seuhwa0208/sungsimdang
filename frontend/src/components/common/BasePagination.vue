<template>
  <nav v-if="totalPages > 1" class="flex flex-wrap items-center justify-center gap-1 pt-4" aria-label="페이지 이동">
    <BaseButton size="sm" variant="secondary" :disabled="page <= 1" @click="go(page - 1)">이전</BaseButton>
    <button
      v-for="p in pages"
      :key="p"
      type="button"
      :aria-current="p === page ? 'page' : undefined"
      :class="[
        'min-h-8 min-w-8 rounded-md px-2 text-sm',
        p === page ? 'bg-brand-600 font-bold text-white' : 'text-gray-600 hover:bg-gray-100',
      ]"
      @click="go(p)"
    >
      {{ p }}
    </button>
    <BaseButton size="sm" variant="secondary" :disabled="page >= totalPages" @click="go(page + 1)">다음</BaseButton>
  </nav>
</template>

<script setup>
import { computed } from 'vue'
import BaseButton from './BaseButton.vue'

const props = defineProps({
  page: { type: Number, required: true }, // 1부터 시작
  totalPages: { type: Number, required: true },
})
const emit = defineEmits(['update:page'])

const pages = computed(() => {
  const start = Math.max(1, Math.min(props.page - 2, props.totalPages - 4))
  const end = Math.min(props.totalPages, start + 4)
  return Array.from({ length: end - start + 1 }, (_, i) => start + i)
})
const go = (p) => emit('update:page', Math.min(Math.max(1, p), props.totalPages))
</script>
