<template>
  <div class="space-y-6">
    <div class="flex items-start justify-between gap-3">
      <div><p class="text-xs font-semibold tracking-wide text-brand-600">교통 인프라</p><h1 class="mt-1 text-2xl font-bold tracking-tight text-gray-900">기반시설 현황</h1><p class="mt-2 text-sm text-gray-500">역을 선택해 이용할 수 있는 시설을 확인하세요.</p></div>
      <RouterLink to="/" class="text-sm text-gray-500 hover:text-brand-700">홈으로 〉</RouterLink>
    </div>

    <form class="flex flex-wrap items-end gap-3 rounded-xl border border-gray-200 bg-white p-5" @submit.prevent="search">
      <div class="min-w-0 flex-1 sm:max-w-sm"><BaseSelect v-model="location" :options="LOCATION_OPTIONS" placeholder="전체 역" label="위치" /></div>
      <BaseButton type="submit">검색</BaseButton>
    </form>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <section v-else class="overflow-hidden rounded-xl border border-gray-200 bg-white" aria-label="시설 목록">
      <div class="flex flex-wrap items-center justify-between gap-2 border-b border-gray-200 px-5 py-5">
        <h2 class="font-bold text-gray-900">시설 목록</h2>
      </div>
      <EmptyState v-if="!list.pageItems.value.length" :title="appliedLocation ? '선택한 조건에 이용가능한 시설이 없습니다.' : '이용가능한 시설이 없습니다.'" />
      <template v-else>
        <div class="overflow-x-auto">
          <table class="w-full text-left text-sm">
            <thead class="bg-sky-50 text-xs text-gray-600"><tr><th class="px-5 py-3 font-medium">ID</th><th class="px-5 py-3 font-medium">시설명</th><th class="px-5 py-3 font-medium">유형</th><th class="px-5 py-3 font-medium">위치</th></tr></thead>
            <tbody class="divide-y divide-gray-100">
              <tr v-for="facility in list.pageItems.value" :key="facility.id" class="hover:bg-gray-50">
                <td class="px-5 py-4 text-gray-500">{{ facility.id }}</td>
                <td class="whitespace-nowrap px-5 py-4 font-medium text-gray-900">{{ facilityName(facility) }}</td>
                <td class="whitespace-nowrap px-5 py-4 text-gray-600">{{ labelOf(facilityTypeLabels, facility.facilityType) }}</td>
                <td class="whitespace-nowrap px-5 py-4 text-gray-600">{{ locationLabel(facility.location) }}</td>
              </tr>
            </tbody>
          </table>
        </div>
        <div class="border-t border-gray-100 px-4 pb-5"><BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" /></div>
      </template>
    </section>
  </div>
</template>

<script setup>
import { ref, watch } from 'vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import { useListView } from '../../composables/useListView'
import { facilityService } from '../../services/facilityService'
import { LOCATION_OPTIONS, locationLabel } from '../../constants/locations'
import { facilityTypeLabels, labelOf } from '../../utils/statusLabels'
import { facilityName } from '../../utils/format'

const location = ref('')
const appliedLocation = ref('')
function search() { appliedLocation.value = location.value; list.search() }
// 위치를 선택해 검색하면 GET /api/facilities/member/search?location=..., 아니면 GET /api/facilities/member
const list = useListView(() => (appliedLocation.value ? facilityService.memberSearch({ location: appliedLocation.value }) : facilityService.memberList()), {
  predicate: facility => facility.facilityStatus === 'AVAILABLE',
  watchSources: [appliedLocation],
})
watch(appliedLocation, () => list.load())
</script>
