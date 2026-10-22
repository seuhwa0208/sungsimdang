<template>
  <nav class="flex gap-1 overflow-x-auto md:flex-col" aria-label="관리자 메뉴">
    <RouterLink
      v-for="m in visible"
      :key="m.to"
      :to="m.to"
      class="whitespace-nowrap rounded-md px-3 py-2 text-sm text-gray-700 hover:bg-gray-100"
      :exact-active-class="m.exact ? 'bg-brand-50! text-brand-700! font-semibold' : ''"
      :active-class="m.exact ? '' : 'bg-brand-50! text-brand-700! font-semibold'"
    >
      {{ m.label }}
    </RouterLink>
  </nav>
</template>

<script setup>
import { computed } from 'vue'
import { useAuthStore } from '../../stores/authStore'

const auth = useAuthStore()
const items = [
  { to: '/dashboard', label: '통합 운영 현황', roles: ['MANAGER', 'ADMIN'], exact: true },
  { to: '/dashboard/qna', label: 'Q&A 관리', roles: ['ADMIN'] },
]
const visible = computed(() => items.filter((i) => auth.hasRole(i.roles)))
</script>
