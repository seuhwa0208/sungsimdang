<template>
  <!-- 와이어프레임의 패널 검색줄: [라벨 + 입력(회색 안내 글씨)] [상태 선택] [검색] [초기화] -->
  <form class="mb-3 flex flex-wrap items-end gap-2" role="search" @submit.prevent="emit('search')">
    <div class="min-w-36 flex-1">
      <label :for="inputId" class="mb-1 block text-xs font-medium text-gray-700">{{ label }}</label>
      <input
        :id="inputId"
        :value="modelValue"
        :placeholder="placeholder"
        class="w-full rounded-md border border-gray-300 px-3 py-1.5 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-2 focus:ring-brand-500"
        @input="emit('update:modelValue', $event.target.value)"
      />
    </div>
    <div v-if="statusOptions.length" class="w-28 shrink-0">
      <label :for="selectId" class="mb-1 block text-xs font-medium text-gray-700">상태</label>
      <select
        :id="selectId"
        :value="status"
        class="w-full rounded-md border border-gray-300 bg-white px-2 py-1.5 text-sm focus:outline-none focus:ring-2 focus:ring-brand-500"
        @change="emit('update:status', $event.target.value)"
      >
        <option value="">전체</option>
        <option v-for="o in statusOptions" :key="o.value" :value="o.value">{{ o.label }}</option>
      </select>
    </div>
    <BaseButton size="sm" type="submit">검색</BaseButton>
    <BaseButton size="sm" variant="secondary" @click="emit('reset')">초기화</BaseButton>
  </form>
</template>

<script setup>
import { useId } from 'vue'
import BaseButton from '../common/BaseButton.vue'

// 대시보드 패널 안에서 쓰는 검색줄. 한 화면에 여러 개가 있어도 id 가 겹치지 않도록 useId 를 쓴다.
// statusOptions 를 주면 "상태" 선택이 함께 표시된다. (선택하면 바로 목록에 적용)
defineProps({
  modelValue: { type: String, default: '' },
  status: { type: String, default: '' },
  statusOptions: { type: Array, default: () => [] }, // [{ value, label }]
  placeholder: { type: String, default: '검색어를 입력하세요' },
  label: { type: String, default: '검색' },
})
const emit = defineEmits(['update:modelValue', 'update:status', 'search', 'reset'])
const inputId = useId()
const selectId = useId()
</script>
