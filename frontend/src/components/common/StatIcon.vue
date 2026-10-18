<template>
  <svg class="h-7 w-7 shrink-0 text-gray-800" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
    <template v-if="name === 'person'">
      <circle cx="12" cy="8" r="4" />
      <path d="M4.5 20a7.5 7.5 0 0 1 15 0" />
    </template>
    <template v-else-if="name === 'bike'">
      <circle cx="5.5" cy="17" r="3.5" />
      <circle cx="18.5" cy="17" r="3.5" />
      <path d="M5.5 17 9 8h5l4.5 9M9 8 12 17h0M14 8l-2 9M8 5h3" />
    </template>
    <template v-else-if="name === 'wrench'">
      <path d="M14.7 6.3a4 4 0 0 0-5.4 5.2L3.5 17.3a1.8 1.8 0 0 0 2.5 2.5l5.8-5.8a4 4 0 0 0 5.2-5.4l-2.6 2.6-2.3-.5-.5-2.3 2.6-2.6Z" />
    </template>
    <template v-else-if="name === 'ban'">
      <circle cx="12" cy="12" r="9" />
      <path d="m5.6 5.6 12.8 12.8" />
    </template>
    <template v-else-if="name === 'building'">
      <rect x="5" y="3" width="14" height="18" rx="1.5" />
      <path d="M9 7.5h2M13 7.5h2M9 11.5h2M13 11.5h2M9 15.5h2M13 15.5h2" />
    </template>
  </svg>
</template>

<script setup>
// 메인 화면 현황 카드 오른쪽 아이콘 (와이어프레임: 자전거 / 렌치 / 금지 / 건물)
defineProps({ name: { type: String, required: true } })
</script>
