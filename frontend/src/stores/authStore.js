import { defineStore } from 'pinia'
import { memberService } from '../services/memberService'

// 권한은 브라우저에 저장한 문자열이 아니라 GET /api/members/me 의 서버 응답으로만 판단한다.
export const useAuthStore = defineStore('auth', {
  state: () => ({ currentUser: null, initialized: false, loading: false }),
  getters: {
    isAuthenticated: (s) => !!s.currentUser,
    role: (s) => s.currentUser?.role ?? null,
    isAdmin: (s) => s.currentUser?.role === 'ADMIN',
    isManager: (s) => s.currentUser?.role === 'MANAGER',
    canManageOps: (s) => ['MANAGER', 'ADMIN'].includes(s.currentUser?.role),
  },
  actions: {
    hasRole(roles) {
      return !roles?.length || roles.includes(this.role)
    },
    async refreshMe() {
      try {
        this.currentUser = await memberService.me()
      } catch {
        this.currentUser = null
      } finally {
        this.initialized = true
      }
      return this.currentUser
    },
    async initializeSession() {
      if (!this.initialized) await this.refreshMe()
    },
    async login(credentials) {
      this.loading = true
      try {
        await memberService.login(credentials)
        const me = await this.refreshMe()
        if (!me) throw new Error('로그인 후 사용자 정보를 확인하지 못했습니다.')
        return me
      } finally {
        this.loading = false
      }
    },
    async logout() {
      try {
        await memberService.logout()
      } finally {
        this.currentUser = null
      }
    },
    clearSession() {
      this.currentUser = null
    },
  },
})
