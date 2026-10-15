<template>
  <form class="mb-4 flex flex-wrap items-end gap-2 rounded-lg bg-white p-3 shadow-sm ring-1 ring-gray-200" @submit.prevent="$emit('search')">
    <div class="min-w-48 flex-1">
      <label class="mb-1 block text-xs font-medium text-gray-500" for="list-keyword">검색</label>
      <input
        id="list-keyword"
        :value="modelValue"
        :placeholder="placeholder"
        class="w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-brand-500"
        @input="$emit('update:modelValue', $event.target.value)"
      />
    </div>
    <slot />
    <BaseButton type="submit">검색</BaseButton>
    <BaseButton variant="secondary" @click="$emit('reset')">초기화</BaseButton>
  </form>
</template>

<script setup>
import BaseButton from './BaseButton.vue'

defineProps({ modelValue: { type: String, default: '' }, placeholder: { type: String, default: '검색어를 입력하세요' } })
defineEmits(['update:modelValue', 'search', 'reset'])
</script>
