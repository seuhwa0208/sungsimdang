<template>
  <nav class="dtc-board-tabs mb-5 flex border-b border-gray-200" aria-label="게시판 구분">
    <RouterLink
      v-for="t in tabs"
      :key="t.to"
      :to="t.to"
      class="border-b-2 border-transparent px-6 py-2.5 text-sm text-gray-600 hover:text-gray-900"
      active-class="dtc-tab-active"
    >
      {{ t.label }}
    </RouterLink>
  </nav>
</template>

<script setup>
// 와이어프레임: 공지사항 / 자료실 / Q&A 가 하나의 "게시판 목록" 화면의 탭으로 구성됨
const tabs = [
  { to: '/notices', label: '공지사항' },
  { to: '/data-room', label: '자료실' },
  { to: '/qna', label: 'Q&A' },
]
</script>
