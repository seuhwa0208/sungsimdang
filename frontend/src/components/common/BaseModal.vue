<template>
  <Teleport to="body">
    <div v-if="modelValue" class="fixed inset-0 z-50 flex items-end justify-center bg-black/40 p-0 sm:items-center sm:p-4" @mousedown.self="close">
      <div
        role="dialog"
        aria-modal="true"
        :aria-label="title"
        class="max-h-[90vh] w-full overflow-y-auto rounded-t-xl bg-white shadow-xl sm:max-w-lg sm:rounded-xl"
      >
        <div class="flex items-center justify-between border-b border-gray-200 px-5 py-4">
          <h2 class="text-base font-bold text-gray-900">{{ title }}</h2>
          <button type="button" class="rounded p-1 text-gray-400 hover:bg-gray-100 hover:text-gray-600" aria-label="닫기" @click="close">✕</button>
        </div>
        <div class="px-5 py-4"><slot /></div>
        <div v-if="$slots.footer" class="flex justify-end gap-2 border-t border-gray-200 px-5 py-3"><slot name="footer" /></div>
      </div>
    </div>
  </Teleport>
</template>

<script setup>
import { onBeforeUnmount, onMounted } from 'vue'

const props = defineProps({
  modelValue: Boolean,
  title: { type: String, default: '' },
  persistent: Boolean, // true 면 바깥 클릭/ESC 로 닫히지 않음 (제출 중 등)
})
const emit = defineEmits(['update:modelValue'])

function close() {
  if (!props.persistent) emit('update:modelValue', false)
}
function onKey(e) {
  if (e.key === 'Escape' && props.modelValue) close()
}
onMounted(() => window.addEventListener('keydown', onKey))
onBeforeUnmount(() => window.removeEventListener('keydown', onKey))
</script>
