<template>
  <div v-if="USE_MOCK" class="bg-amber-400 px-4 py-1 text-center text-xs font-semibold text-amber-950">
    MOCK 모드 — 실제 서버가 아닌 브라우저 메모리 데이터를 사용 중입니다. 새로고침하면 데이터가 초기화됩니다.
  </div>
</template>

<script setup>
import { USE_MOCK } from '../../services/api'
</script>
