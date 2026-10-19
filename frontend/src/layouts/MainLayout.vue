<template>
  <div class="dtc-shell flex min-h-screen flex-col">
    <MockBanner />
    <AppHeader />
    <main class="dtc-main mx-auto w-full max-w-6xl flex-1 px-4 py-8 sm:px-6">
      <slot />
    </main>
    <footer class="dtc-footer"><div class="dtc-footer-inner"><img src="../assets/images/sungsimdang-logo.svg" alt="" width="224" height="48" /><span>성심당 통합운영관리시스템</span></div></footer>
  </div>
</template>

<script setup>
import AppHeader from '../components/common/AppHeader.vue'
import MockBanner from '../components/common/MockBanner.vue'
</script>
