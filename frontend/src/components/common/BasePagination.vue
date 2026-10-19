<template>
  <nav v-if="always || totalPages > 1" :class="['flex items-center justify-between gap-2', compact ? 'pt-1.5' : 'pt-5']" aria-label="페이지 이동">
    <div class="flex shrink-0 items-center gap-1">
    <button
      v-if="boundaryLinks"
      type="button"
      aria-label="맨 처음 페이지"
      title="맨 처음 페이지"
      :disabled="page <= 1"
      :class="[arrowClass, compact ? 'h-7 w-7' : 'h-9 w-9']"
      @click="go(1)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="m9.5 5-5 5 5 5m6-10-5 5 5 5" /></svg>
    </button>
    <button
      type="button"
      aria-label="이전 페이지"
      :disabled="page <= 1"
      :class="[arrowClass, compact ? 'h-7 w-7' : 'h-9 w-9']"
      @click="go(page - 1)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M12.5 4.5 7 10l5.5 5.5" /></svg>
    </button>
    </div>

    <div class="flex min-w-0 flex-1 flex-wrap items-center justify-center gap-x-1 gap-y-1 sm:gap-x-3">
      <button
        v-for="p in pages"
        :key="p"
        type="button"
        :aria-current="p === page ? 'page' : undefined"
        :class="[
          'rounded-md px-2 text-sm transition',
          compact ? 'min-h-7 min-w-7' : 'min-h-8 min-w-8',
          p === page ? 'font-bold text-emerald-600' : 'text-gray-500 hover:text-gray-900',
        ]"
        @click="go(p)"
      >
        {{ p }}
      </button>
    </div>

    <div class="flex shrink-0 items-center gap-1">
    <button
      type="button"
      aria-label="다음 페이지"
      :disabled="page >= totalPages"
      :class="[arrowClass, compact ? 'h-7 w-7' : 'h-9 w-9']"
      @click="go(page + 1)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="M7.5 4.5 13 10l-5.5 5.5" /></svg>
    </button>
    <button
      v-if="boundaryLinks"
      type="button"
      aria-label="맨 마지막 페이지"
      title="맨 마지막 페이지"
      :disabled="page >= totalPages"
      :class="[arrowClass, compact ? 'h-7 w-7' : 'h-9 w-9']"
      @click="go(totalPages)"
    >
      <svg class="h-4 w-4" viewBox="0 0 20 20" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true"><path d="m4.5 5 5 5-5 5m6-10 5 5-5 5" /></svg>
    </button>
    </div>
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
  compact: Boolean, // true 면 위쪽 여백과 버튼 크기를 줄인다 (한 화면에 맞춰야 하는 대시보드 패널)
  boundaryLinks: Boolean, // 게시판에서 맨 처음·맨 마지막 페이지 이동 버튼을 표시한다.
})
const emit = defineEmits(['update:page'])

const arrowClass =
  'flex shrink-0 items-center justify-center rounded-full border border-gray-200 bg-white text-gray-500 transition hover:border-emerald-500 hover:text-emerald-600 disabled:cursor-not-allowed disabled:text-gray-300 disabled:hover:border-gray-200 disabled:hover:text-gray-300'

const pages = computed(() => {
  const size = Math.max(1, props.maxVisible)
  const start = Math.max(1, Math.min(props.page - Math.floor(size / 2), props.totalPages - size + 1))
  const end = Math.min(props.totalPages, start + size - 1)
  return Array.from({ length: end - start + 1 }, (_, i) => start + i)
})
const go = (p) => emit('update:page', Math.min(Math.max(1, p), props.totalPages))
</script>
