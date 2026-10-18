<template>
  <nav v-if="always || totalPages > 1" class="flex items-center justify-between gap-2 pt-5" aria-label="페이지 이동">
    <button
      type="button"
      aria-label="이전 페이지"
      :disabled="page <= 1"
      class="flex h-9 w-9 shrink-0 items-center justify-center rounded-full border border-gray-200 bg-white text-gray-500 transition hover:border-emerald-500 hover:text-emerald-600 disabled:cursor-not-allowed disabled:text-gray-300 disabled:hover:border-gray-200 disabled:hover:text-gray-300"
      @click="go(page - 1)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12.5 4.5 7 10l5.5 5.5" /></svg>
    </button>

    <div class="flex flex-wrap items-center justify-center gap-x-1 gap-y-1 sm:gap-x-3">
      <button
        v-for="p in pages"
        :key="p"
        type="button"
        :aria-current="p === page ? 'page' : undefined"
        :class="[
          'min-h-8 min-w-8 rounded-md px-2 text-sm transition',
          p === page ? 'font-bold text-emerald-600' : 'text-gray-500 hover:text-gray-900',
        ]"
        @click="go(p)"
      >
        {{ p }}
      </button>
    </div>

    <button
      type="button"
      aria-label="다음 페이지"
      :disabled="page >= totalPages"
      class="flex h-9 w-9 shrink-0 items-center justify-center rounded-full border border-gray-200 bg-white text-gray-500 transition hover:border-emerald-500 hover:text-emerald-600 disabled:cursor-not-allowed disabled:text-gray-300 disabled:hover:border-gray-200 disabled:hover:text-gray-300"
      @click="go(page + 1)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M7.5 4.5 13 10l-5.5 5.5" /></svg>
    </button>
  </nav>
</template>

<script setup>
import { computed } from 'vue'

// 와이어프레임/참고 이미지 스타일: 좌우 원형 화살표 + 숫자 목록, 현재 페이지는 초록색 굵은 글씨.
const props = defineProps({
  page: { type: Number, required: true }, // 1부터 시작
  totalPages: { type: Number, required: true },
  maxVisible: { type: Number, default: 10 }, // 한 번에 보여줄 페이지 번호 개수
  always: Boolean, // true 면 페이지가 1개뿐이어도 이동 표시를 그대로 보여준다 (대시보드 패널)
})
const emit = defineEmits(['update:page'])

const pages = computed(() => {
  const size = Math.max(1, props.maxVisible)
  const start = Math.max(1, Math.min(props.page - Math.floor(size / 2), props.totalPages - size + 1))
  const end = Math.min(props.totalPages, start + size - 1)
  return Array.from({ length: end - start + 1 }, (_, i) => start + i)
})
const go = (p) => emit('update:page', Math.min(Math.max(1, p), props.totalPages))
</script>
