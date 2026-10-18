<template>
  <div class="flex min-h-screen flex-col">
    <MockBanner />
    <AppHeader />
    <div class="mx-auto flex w-full max-w-7xl flex-1 flex-col gap-6 px-4 py-6 sm:px-6">
      <main class="min-w-0 flex-1"><slot /></main>
    </div>
  </div>
</template>

<script setup>
import AppHeader from '../components/common/AppHeader.vue'
import MockBanner from '../components/common/MockBanner.vue'
</script>
