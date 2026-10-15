<template>
  <div class="flex min-h-screen flex-col">
    <MockBanner />
    <AppHeader />
    <div class="mx-auto flex w-full max-w-7xl flex-1 flex-col gap-6 px-4 py-6 sm:px-6 md:flex-row">
      <aside class="md:w-52 md:shrink-0">
        <div class="rounded-lg bg-white p-2 shadow-sm ring-1 ring-gray-200 md:sticky md:top-4">
          <p class="hidden px-3 pb-2 pt-1 text-xs font-semibold uppercase tracking-wide text-gray-400 md:block">관리자</p>
          <AppSidebar />
        </div>
      </aside>
      <main class="min-w-0 flex-1"><slot /></main>
    </div>
  </div>
</template>

<script setup>
import AppHeader from '../components/common/AppHeader.vue'
import AppSidebar from '../components/common/AppSidebar.vue'
import MockBanner from '../components/common/MockBanner.vue'
</script>
