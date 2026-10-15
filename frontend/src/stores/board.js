import { defineStore } from 'pinia'
import { boardApi } from '../api/boardApi'
export const useBoardStore = defineStore('board', {
  state: () => ({ page: null, current: null }),
  actions: {
    async load(params = {}) { this.page = (await boardApi.list(params)).data },
    async loadOne(id) { this.current = (await boardApi.get(id)).data },
    async create(data) { return (await boardApi.create(data)).data },
    async update(id, data) { return (await boardApi.update(id, data)).data },
    async remove(id) { await boardApi.remove(id) }
  }
})
