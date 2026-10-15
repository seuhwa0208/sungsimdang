<template>
  <div class="flex min-h-screen flex-col">
    <MockBanner />
    <AppHeader />
    <main class="mx-auto w-full max-w-6xl flex-1 px-4 py-8 sm:px-6">
      <slot />
    </main>
    <footer class="border-t border-gray-200 bg-white py-5 text-center text-xs text-gray-500">대전교통공사 통합운영관리시스템</footer>
  </div>
</template>

<script setup>
import AppHeader from '../components/common/AppHeader.vue'
import MockBanner from '../components/common/MockBanner.vue'
</script>
