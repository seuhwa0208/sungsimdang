<template><div v-if="page" class="mt-6 flex justify-center gap-2">
<button v-for="n in page.totalPages" :key="n" @click="$emit('change', n-1)" class="rounded border px-3 py-1" :class="{ 'bg-slate-900 text-white': n-1 === page.number }">{{ n }}</button>
</div></template>
<script setup>defineProps({ page: Object }); defineEmits(['change'])</script>
