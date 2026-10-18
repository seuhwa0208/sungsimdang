<template>
  <nav class="mb-6 w-full shrink-0 md:mb-0 md:w-52" aria-label="마이페이지 메뉴">
    <ul class="flex gap-1 overflow-x-auto md:flex-col">
      <li v-for="t in tabs" :key="t.to">
        <RouterLink
          :to="t.to"
          :aria-current="isActive(t) ? 'page' : undefined"
          :class="['flex items-center gap-2 whitespace-nowrap rounded-md px-3 py-2.5 text-sm', isActive(t) ? 'bg-gray-100 font-semibold text-gray-900' : 'text-gray-600 hover:bg-gray-50 hover:text-gray-900']"
        >
          <svg class="h-5 w-5 shrink-0" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
            <template v-if="t.icon === 'person'"><circle cx="12" cy="8" r="4" /><path d="M4.5 20a7.5 7.5 0 0 1 15 0" /></template>
            <template v-else-if="t.icon === 'lock'"><rect x="5" y="11" width="14" height="9" rx="2" /><path d="M8 11V8a4 4 0 0 1 8 0v3" /></template>
            <template v-else><path d="M7 3h7l4 4v14H7z" /><path d="M14 3v4h4M10 12h5M10 16h5" /></template>
          </svg>
          {{ t.label }}
        </RouterLink>
      </li>
    </ul>
  </nav>
</template>

<script setup>
import { useRoute } from 'vue-router'

// 와이어프레임: 왼쪽 메뉴 3개 (내 정보 / 비밀번호 변경 / 내가 쓴 글). 정보 수정 화면은 "내 정보" 메뉴에 속한다.
const route = useRoute()
const tabs = [
  { to: '/mypage', label: '내 정보', icon: 'person' },
  { to: '/mypage/password', label: '비밀번호 변경', icon: 'lock' },
  { to: '/mypage/posts', label: '내가 쓴 글', icon: 'file' },
]
const isActive = (t) => route.path === t.to || (t.to === '/mypage' && route.path === '/mypage/edit')
</script>
