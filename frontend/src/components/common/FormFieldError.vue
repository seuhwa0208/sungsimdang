<template>
  <p v-if="message" class="mt-1 text-sm text-red-600" role="alert">{{ message }}</p>
</template>

<script setup>
defineProps({ message: { type: String, default: '' } })
</script>
