<template>
  <header class="bg-gray-800 text-white">
    <div class="mx-auto flex max-w-6xl items-center justify-between gap-4 px-4 py-3 sm:px-6">
      <RouterLink to="/" class="text-base font-bold tracking-tight sm:text-lg" @click="open = false">대전교통공사 통합운영관리</RouterLink>

      <button type="button" class="rounded p-2 hover:bg-gray-700 md:hidden" :aria-expanded="open" aria-label="메뉴 열기" @click="open = !open">
        <span class="block h-0.5 w-5 bg-white" /><span class="mt-1 block h-0.5 w-5 bg-white" /><span class="mt-1 block h-0.5 w-5 bg-white" />
      </button>

      <nav :class="[open ? 'flex' : 'hidden', 'absolute inset-x-0 top-[52px] z-30 flex-col gap-1 bg-gray-800 px-4 pb-4 md:static md:flex md:flex-row md:items-center md:gap-1 md:p-0']">
        <RouterLink v-for="m in menus" :key="m.to" :to="m.to" :class="linkClass" active-class="bg-gray-700! font-semibold" @click="open = false">{{ m.label }}</RouterLink>
        <RouterLink v-if="auth.canManageOps" to="/dashboard" :class="linkClass" active-class="bg-gray-700! font-semibold" @click="open = false">대시보드</RouterLink>

        <span class="mx-2 hidden h-5 w-px bg-gray-600 md:block" />

        <template v-if="auth.isAuthenticated">
          <RouterLink to="/mypage" :class="linkClass" @click="open = false">
            {{ auth.currentUser.name }}<span class="ml-1 text-xs text-gray-400">({{ roleLabel }})</span>
          </RouterLink>
          <button type="button" :class="linkClass" @click="onLogout">로그아웃</button>
        </template>
        <template v-else>
          <RouterLink to="/login" :class="linkClass" @click="open = false">로그인</RouterLink>
          <RouterLink to="/signup" :class="linkClass" @click="open = false">회원가입</RouterLink>
        </template>
      </nav>
    </div>
  </header>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../../stores/authStore'
import { memberRoleLabels, labelOf } from '../../utils/statusLabels'

const auth = useAuthStore()
const router = useRouter()
const open = ref(false)

const menus = [
  { to: '/notices', label: '공지사항' },
  { to: '/data-room', label: '자료실' },
  { to: '/qna', label: 'Q&A' },
]
const linkClass = 'rounded px-3 py-2 text-left text-sm hover:bg-gray-700'
const roleLabel = computed(() => labelOf(memberRoleLabels, auth.role))

async function onLogout() {
  open.value = false
  try {
    await auth.logout()
  } finally {
    router.push('/')
  }
}
</script>
