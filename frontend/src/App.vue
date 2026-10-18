<template>
  <component :is="layout">
    <RouterView />
  </component>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import MainLayout from './layouts/MainLayout.vue'
import DashboardLayout from './layouts/DashboardLayout.vue'

const route = useRoute()
const layout = computed(() => (route.meta.layout === 'dashboard' ? DashboardLayout : MainLayout))
</script>
