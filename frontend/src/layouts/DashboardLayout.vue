<template>
  <!-- 대시보드 홈(/dashboard)은 큰 화면(lg 이상)에서 스크롤 없이 한 화면에 맞춘다. 다른 관리 화면은 기존처럼 길이에 따라 스크롤된다. -->
  <div :class="['dtc-shell dtc-dashboard flex flex-col', fitScreen ? 'min-h-screen lg:h-dvh lg:min-h-0' : 'min-h-screen']">
    <MockBanner />
    <AppHeader />
    <div :class="['mx-auto flex w-full max-w-7xl flex-1 flex-col gap-6 px-4 sm:px-6', fitScreen ? 'py-3 lg:min-h-0' : 'py-6']">
      <main :class="['min-w-0 flex-1', fitScreen ? 'flex flex-col lg:min-h-0' : '']"><slot /></main>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import AppHeader from '../components/common/AppHeader.vue'
import MockBanner from '../components/common/MockBanner.vue'

const route = useRoute()
const fitScreen = computed(() => route.path.replace(/\/+$/, '') === '/dashboard')
</script>
