<template>
  <div>
    <label v-if="label" :for="id" class="mb-1 block text-sm font-medium text-gray-700">
      {{ label }}<span v-if="required" class="ml-0.5 text-red-500" aria-hidden="true">*</span>
    </label>
    <select
      :id="id"
      :value="modelValue"
      v-bind="$attrs"
      :class="[
        'w-full rounded-md border bg-white px-3 py-2 text-sm shadow-sm focus:outline-none focus:ring-2 focus:ring-brand-500 disabled:bg-gray-100',
        error ? 'border-red-400' : 'border-gray-300',
      ]"
      @change="$emit('update:modelValue', $event.target.value)"
    >
      <option v-if="placeholder" value="">{{ placeholder }}</option>
      <option v-for="o in options" :key="o.value" :value="o.value">{{ o.label }}</option>
    </select>
    <FormFieldError :message="error" />
  </div>
</template>

<script setup>
import { useId } from 'vue'
import FormFieldError from './FormFieldError.vue'

defineOptions({ inheritAttrs: false })
defineProps({
  modelValue: { type: [String, Number], default: '' },
  options: { type: Array, default: () => [] }, // [{ value, label }]
  label: { type: String, default: '' },
  placeholder: { type: String, default: '' },
  error: { type: String, default: '' },
  required: Boolean,
})
defineEmits(['update:modelValue'])
const id = useId()
</script>
