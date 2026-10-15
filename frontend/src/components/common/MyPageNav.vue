<template>
  <nav class="mb-6 flex gap-1 overflow-x-auto border-b border-gray-200" aria-label="마이페이지 메뉴">
    <RouterLink
      v-for="t in tabs"
      :key="t.to"
      :to="t.to"
      class="whitespace-nowrap border-b-2 border-transparent px-4 py-2 text-sm text-gray-600 hover:text-gray-900"
      exact-active-class="border-brand-600! text-brand-700! font-semibold"
    >
      {{ t.label }}
    </RouterLink>
  </nav>
</template>

<script setup>
const tabs = [
  { to: '/mypage', label: '내 정보' },
  { to: '/mypage/edit', label: '정보 수정' },
  { to: '/mypage/password', label: '비밀번호 변경' },
  { to: '/mypage/posts', label: '내가 쓴 글' },
]
</script>
