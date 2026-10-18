<template>
  <div class="mb-5 flex flex-wrap items-end justify-between gap-x-4 gap-y-3">
    <div class="flex flex-wrap items-end gap-x-6 gap-y-3">
      <h1 class="flex items-center gap-2 pb-1 text-2xl font-bold text-gray-900"><slot name="icon" />{{ title }}</h1>

      <!-- 와이어프레임: 라벨이 입력창 위에 있고, 오른쪽에 조회(·삭제) 버튼 -->
      <form class="flex flex-wrap items-end gap-2" role="search" @submit.prevent="$emit('search')">
        <div class="w-48">
          <label :for="keywordId" class="mb-1 block text-sm font-medium text-gray-700">{{ keywordLabel }}</label>
          <input
            :id="keywordId"
            :value="modelValue"
            :placeholder="keywordPlaceholder"
            class="w-full rounded-md border border-gray-300 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-brand-500"
            @input="$emit('update:modelValue', $event.target.value)"
          />
        </div>
        <slot />
        <BaseButton type="submit" variant="secondary">조회</BaseButton>
        <BaseButton v-if="deletable" variant="secondary" @click="$emit('delete')">삭제</BaseButton>
      </form>
    </div>

    <BaseButton v-if="registerLabel" @click="$emit('register')">+ {{ registerLabel }}</BaseButton>
  </div>
</template>

<script setup>
import { useId } from 'vue'
import BaseButton from './BaseButton.vue'

defineProps({
  title: { type: String, required: true },
  modelValue: { type: String, default: '' },
  keywordLabel: { type: String, required: true },
  keywordPlaceholder: { type: String, default: '' },
  deletable: Boolean, // 삭제 버튼 표시
  registerLabel: { type: String, default: '' }, // 있으면 오른쪽에 "+ OO 등록" 버튼 표시
})
defineEmits(['update:modelValue', 'search', 'delete', 'register'])
const keywordId = useId()
</script>
