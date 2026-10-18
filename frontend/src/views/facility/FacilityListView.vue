<template>
  <div>
    <PageHeader title="기반시설 현황" description="엘리베이터·스크린도어·자전거 대여소 등 시설의 현재 상태입니다." />

    <ListToolbar v-model="list.keyword.value" placeholder="시설 번호·위치 검색" @search="list.search" @reset="list.reset(resetFilters)">
      <div class="w-40"><BaseSelect v-model="type" :options="typeOptions" placeholder="전체 유형" label="유형" /></div>
      <div class="w-40"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체 상태" label="상태" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" :title="list.keyword.value || type || status ? '검색 결과가 없습니다.' : '등록된 시설이 없습니다.'" />
    <template v-else>
      <p class="mb-2 text-sm text-gray-600">
        총 {{ list.items.value.length }}개 <span v-for="s in summary" :key="s.key" class="ml-2">| {{ s.label }} {{ s.count }}개</span>
      </p>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3">시설 번호</th><th class="px-4 py-3">유형</th><th class="px-4 py-3">위치</th><th class="px-4 py-3">상태</th></tr>
        </template>
        <tr v-for="f in list.pageItems.value" :key="f.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`/facilities/${f.id}`)">
          <td class="px-4 py-3 font-medium">{{ f.facilityNo || '-' }}</td>
          <td class="px-4 py-3">{{ labelOf(facilityTypeLabels, f.facilityType) }}</td>
          <td class="px-4 py-3">{{ f.location }}</td>
          <td class="px-4 py-3"><FacilityStatusBadge :status="f.facilityStatus" /></td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import ListToolbar from '../../components/common/ListToolbar.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { facilityService } from '../../services/facilityService'
import { facilityStatusLabels, facilityTypeLabels, labelOf, toOptions } from '../../utils/statusLabels'

const type = ref('')
const status = ref('')
const typeOptions = toOptions(facilityTypeLabels)
const statusOptions = toOptions(facilityStatusLabels)
const resetFilters = () => {
  type.value = ''
  status.value = ''
}
const list = useListView((p) => facilityService.memberList(p), {
  searchFields: (f) => [f.facilityNo, f.location],
  predicate: (f) => (!type.value || f.facilityType === type.value) && (!status.value || f.facilityStatus === status.value),
  sort: (a, b) => (a.id ?? 0) - (b.id ?? 0),
  watchSources: [type, status],
})
const summary = computed(() =>
  Object.entries(facilityStatusLabels).map(([key, label]) => ({ key, label, count: list.items.value.filter((f) => f.facilityStatus === key).length })),
)
</script>
