<template>
  <div>
    <PageHeader title="타슈 현황" description="자전거의 현재 위치와 이용 상태를 확인합니다." />

    <ListToolbar v-model="list.keyword.value" placeholder="번호·위치 검색" @search="list.search" @reset="list.reset(resetFilters)">
      <div class="w-40"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체 상태" label="상태" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" :title="list.keyword.value || status ? '검색 결과가 없습니다.' : '등록된 자전거가 없습니다.'" />
    <template v-else>
      <p class="mb-2 text-sm text-gray-600">
        총 {{ list.items.value.length }}대 <span v-for="s in summary" :key="s.key" class="ml-2">| {{ s.label }} {{ s.count }}대</span>
      </p>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3">자전거</th><th class="px-4 py-3">현재 위치</th><th class="px-4 py-3">상태</th><th class="hidden px-4 py-3 sm:table-cell">누적 거리</th></tr>
        </template>
        <tr v-for="b in list.pageItems.value" :key="b.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`/bikes/${b.id}`)">
          <td class="px-4 py-3 font-medium">{{ formatBikeId(b.id) }}</td>
          <td class="px-4 py-3">{{ b.location }}</td>
          <td class="px-4 py-3"><BikeStatusBadge :status="b.status" /></td>
          <td class="hidden px-4 py-3 text-gray-600 sm:table-cell">{{ formatDistance(b.distance) }}</td>
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
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { bikeService } from '../../services/bikeService'
import { bikeStatusLabels, toOptions } from '../../utils/statusLabels'
import { formatBikeId, formatDistance } from '../../utils/format'

const status = ref('')
const statusOptions = toOptions(bikeStatusLabels)
const resetFilters = () => {
  status.value = ''
}
const list = useListView((p) => bikeService.list(p), {
  searchFields: (b) => [b.id, b.location],
  predicate: (b) => !status.value || b.status === status.value,
  sort: (a, b) => (a.id ?? 0) - (b.id ?? 0),
  watchSources: [status],
})
const summary = computed(() =>
  Object.entries(bikeStatusLabels).map(([key, label]) => ({ key, label, count: list.items.value.filter((b) => b.status === key).length })),
)
</script>
