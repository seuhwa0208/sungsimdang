<template>
  <div class="mx-auto max-w-3xl">
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="error" :error="error" back-to="/facilities" @retry="load" />
    <template v-else-if="facility">
      <PageHeader :title="labelOf(facilityTypeLabels, facility.facilityType)" :description="facility.facilityNo ? `시설 번호 ${facility.facilityNo}` : ''">
        <BaseButton variant="secondary" @click="$router.push('/facilities')">목록</BaseButton>
        <BaseButton v-if="auth.canManageOps" @click="$router.push(`/dashboard/facilities/${facility.id}/maintenance`)">점검·수리 관리</BaseButton>
      </PageHeader>
      <dl class="grid gap-6 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200 sm:grid-cols-3">
        <div><dt class="text-xs font-medium text-gray-500">유형</dt><dd class="mt-1 text-sm">{{ labelOf(facilityTypeLabels, facility.facilityType) }}</dd></div>
        <div><dt class="text-xs font-medium text-gray-500">위치</dt><dd class="mt-1 text-sm">{{ facility.location }}</dd></div>
        <div><dt class="text-xs font-medium text-gray-500">현재 상태</dt><dd class="mt-1"><FacilityStatusBadge :status="facility.facilityStatus" /></dd></div>
      </dl>
    </template>
  </div>
</template>

<script setup>
import PageHeader from '../../components/common/PageHeader.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import { useAuthStore } from '../../stores/authStore'
import { useAsyncData } from '../../composables/useAsyncData'
import { facilityService } from '../../services/facilityService'
import { facilityTypeLabels, labelOf } from '../../utils/statusLabels'

const props = defineProps({ id: { type: String, required: true } })
const auth = useAuthStore()
const { data: facility, loading, error, load } = useAsyncData(() => facilityService.get(props.id))
</script>
