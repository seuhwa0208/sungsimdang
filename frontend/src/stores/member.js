import { defineStore } from 'pinia'
import { memberApi } from '../api/memberApi'
export const useMemberStore = defineStore('member', {
  state: () => ({ profile: null }),
  actions: {
    async load() { this.profile = (await memberApi.me()).data; return this.profile },
    async update(data) { this.profile = (await memberApi.update(data)).data },
    async remove() { await memberApi.remove(); this.profile = null }
  }
})
