<template>
  <header class="dtc-header">
    <div class="dtc-utility" aria-hidden="true"><div class="dtc-header-inner"><span>성심당 통합운영관리시스템</span><span class="dtc-utility-accent"><i /> 시민과 함께하는 편리한 대중교통</span></div></div>
    <div class="dtc-header-inner dtc-header-main">
      <RouterLink to="/" class="dtc-brand" @click="open = false">
        <img src="../../assets/images/sungsimdang-logo.svg" alt="성심당" width="224" height="48" />
        <span class="dtc-brand-divider" aria-hidden="true" /><span class="dtc-brand-caption">통합운영관리</span>
      </RouterLink>

      <button type="button" class="dtc-menu-toggle" :aria-expanded="open" aria-label="메뉴 열기" @click="open = !open">
        <span /><span /><span />
      </button>

      <nav :class="['dtc-navigation', { 'is-open': open }]">
        <RouterLink v-for="m in menus" :key="m.to" :to="m.to" :class="[linkClass, 'dtc-menu-link']" active-class="is-active" @click="open = false">{{ m.label }}</RouterLink>
        <RouterLink v-if="auth.canManageOps" to="/dashboard" :class="[linkClass, 'dtc-menu-link']" active-class="is-active" @click="open = false">대시보드</RouterLink>

        <span class="dtc-nav-divider" aria-hidden="true" />

        <template v-if="auth.isAuthenticated">
          <RouterLink to="/mypage" :class="[linkClass, 'dtc-account-link']" @click="open = false">
            {{ auth.currentUser.name }}<span class="dtc-role-label">({{ roleLabel }})</span>
          </RouterLink>
          <button type="button" :class="[linkClass, 'dtc-account-link', 'dtc-account-action']" @click="onLogout">로그아웃</button>
        </template>
        <template v-else>
          <RouterLink to="/login" :class="[linkClass, 'dtc-account-link']" @click="open = false">로그인</RouterLink>
          <RouterLink to="/signup" :class="[linkClass, 'dtc-account-link', 'dtc-account-action']" @click="open = false">회원가입</RouterLink>
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
