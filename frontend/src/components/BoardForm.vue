<template>
<form @submit.prevent="$emit('submit')" class="space-y-4">
  <input v-model="model.title" class="w-full rounded border p-3" placeholder="제목" required maxlength="200">
  <textarea v-model="model.content" class="min-h-64 w-full rounded border p-3" placeholder="내용" required></textarea>
  <div class="flex gap-2"><button class="rounded bg-slate-900 px-5 py-2 text-white">{{ submitText }}</button><button type="button" @click="$router.back()" class="rounded border px-5 py-2">취소</button></div>
</form>
</template>
<script setup>
defineProps({ model: Object, submitText: { type: String, default: '저장' } })
defineEmits(['submit'])
</script>
