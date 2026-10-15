import { defineStore } from 'pinia'
import { authApi } from '../api/authApi'

export const useAuthStore = defineStore('auth', {
  state: () => ({ member: null, initialized: false }),
  getters: {
    isLoggedIn: (s) => !!s.member,
    isAdmin: (s) => s.member?.role === 'ADMIN'
  },
  actions: {
    async fetchMe() {
      try {
        const { data } = await authApi.me()
        this.member = data.authenticated ? data.member : null
      } catch { this.member = null }
      finally { this.initialized = true }
    },
    async login(userid, password) {
      await authApi.login({ userid, password })
      await this.fetchMe()
    },
    async logout() {
      await authApi.logout()
      this.member = null
    }
  }
})
