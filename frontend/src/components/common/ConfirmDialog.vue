<template>
  <BaseModal :model-value="modelValue" :title="title" :persistent="loading" @update:model-value="$emit('update:modelValue', $event)">
    <p class="whitespace-pre-line text-sm text-gray-700">{{ message }}</p>
    <FormFieldError :message="error" />
    <template #footer>
      <BaseButton variant="secondary" :disabled="loading" @click="$emit('update:modelValue', false)">취소</BaseButton>
      <BaseButton :variant="danger ? 'danger' : 'primary'" :loading="loading" @click="$emit('confirm')">{{ confirmText }}</BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import BaseModal from './BaseModal.vue'
import BaseButton from './BaseButton.vue'
import FormFieldError from './FormFieldError.vue'

defineProps({
  modelValue: Boolean,
  title: { type: String, default: '확인' },
  message: { type: String, default: '' },
  confirmText: { type: String, default: '확인' },
  danger: Boolean,
  loading: Boolean,
  error: { type: String, default: '' },
})
defineEmits(['update:modelValue', 'confirm'])
</script>
