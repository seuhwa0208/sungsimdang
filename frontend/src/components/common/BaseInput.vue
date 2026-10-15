<template>
  <div>
    <label v-if="label" :for="id" class="mb-1 block text-sm font-medium text-gray-700">
      {{ label }}<span v-if="required" class="ml-0.5 text-red-500" aria-hidden="true">*</span>
    </label>
    <component
      :is="as"
      :id="id"
      :value="modelValue"
      :type="as === 'input' ? type : undefined"
      :rows="as === 'textarea' ? rows : undefined"
      :aria-invalid="!!error"
      v-bind="$attrs"
      :class="[
        'w-full rounded-md border bg-white px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-brand-500 disabled:bg-gray-100',
        error ? 'border-red-400' : 'border-gray-300',
      ]"
      @input="$emit('update:modelValue', $event.target.value)"
    />
    <FormFieldError :message="error" />
    <p v-if="hint && !error" class="mt-1 text-xs text-gray-500">{{ hint }}</p>
  </div>
</template>

<script setup>
import { useId } from 'vue'
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
</script>
