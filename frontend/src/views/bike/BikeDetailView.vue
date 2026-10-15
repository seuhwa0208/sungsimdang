<template>
  <div class="mx-auto max-w-3xl">
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="error" :error="error" back-to="/bikes" @retry="load" />
    <template v-else-if="bike">
      <PageHeader :title="`타슈 ${formatBikeId(bike.id)}`">
        <BaseButton variant="secondary" @click="$router.push('/bikes')">목록</BaseButton>
        <BaseButton v-if="auth.canManageOps" @click="$router.push(`/dashboard/bikes/${bike.id}/maintenance`)">고장·정비 관리</BaseButton>
      </PageHeader>
      <dl class="grid gap-6 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200 sm:grid-cols-3">
        <div><dt class="text-xs font-medium text-gray-500">현재 위치</dt><dd class="mt-1 text-sm">{{ bike.location }}</dd></div>
        <div><dt class="text-xs font-medium text-gray-500">상태</dt><dd class="mt-1"><BikeStatusBadge :status="bike.status" /></dd></div>
        <div><dt class="text-xs font-medium text-gray-500">누적 거리</dt><dd class="mt-1 text-sm">{{ formatDistance(bike.distance) }}</dd></div>
      </dl>
    </template>
  </div>
</template>

<script setup>
import PageHeader from '../../components/common/PageHeader.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import { useAuthStore } from '../../stores/authStore'
import { useAsyncData } from '../../composables/useAsyncData'
import { bikeService } from '../../services/bikeService'
import { formatBikeId, formatDistance } from '../../utils/format'

const props = defineProps({ id: { type: String, required: true } })
const auth = useAuthStore()
const { data: bike, loading, error, load } = useAsyncData(() => bikeService.get(props.id))
</script>
