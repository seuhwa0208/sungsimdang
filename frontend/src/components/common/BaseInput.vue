<template>
  <div>
    <label v-if="label" :for="id" class="mb-1 block text-sm font-medium text-gray-700">
      {{ label }}<span v-if="required" class="ml-0.5 text-red-500" aria-hidden="true">*</span>
    </label>
    <div class="relative">
    <component
      :is="as"
      :id="id"
      :value="modelValue"
      :type="as === 'input' ? (type === 'password' && revealed ? 'text' : type) : undefined"
      :rows="as === 'textarea' ? rows : undefined"
      :aria-invalid="!!error"
      v-bind="$attrs"
      :class="[
        'dtc-input w-full rounded-md border bg-white px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-brand-500 disabled:bg-gray-100',
        error ? 'border-red-400' : 'border-gray-300',
        type === 'password' && as === 'input' ? 'pr-10' : '',
      ]"
      @input="$emit('update:modelValue', $event.target.value)"
    />
    <!-- 비밀번호 보기/숨기기 (와이어프레임의 눈 아이콘) -->
    <button
      v-if="type === 'password' && as === 'input'"
      type="button"
      class="absolute inset-y-0 right-0 flex w-10 items-center justify-center text-gray-400 hover:text-gray-700"
      :aria-label="revealed ? '비밀번호 숨기기' : '비밀번호 보기'"
      :aria-pressed="revealed"
      @click="revealed = !revealed"
    >
      <svg class="h-5 w-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round" aria-hidden="true">
        <path d="M2 12s3.5-6.5 10-6.5S22 12 22 12s-3.5 6.5-10 6.5S2 12 2 12Z" />
        <circle cx="12" cy="12" r="3" />
        <path v-if="!revealed" d="M4 4l16 16" />
      </svg>
    </button>
    </div>
    <FormFieldError :message="error" />
    <p v-if="hint && !error" class="mt-1 text-xs text-gray-500">{{ hint }}</p>
  </div>
</template>

<script setup>
import { ref, useId } from 'vue'
import FormFieldError from './FormFieldError.vue'

defineOptions({ inheritAttrs: false })
defineProps({
  modelValue: { type: [String, Number], default: '' },
  label: { type: String, default: '' },
  type: { type: String, default: 'text' },
  as: { type: String, default: 'input' }, // input | textarea
  rows: { type: Number, default: 8 },
  error: { type: String, default: '' },
  hint: { type: String, default: '' },
  required: Boolean,
})
defineEmits(['update:modelValue'])
const id = useId()
const revealed = ref(false)
</script>
