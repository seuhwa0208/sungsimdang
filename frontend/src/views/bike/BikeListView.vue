<template>
  <div class="space-y-6">
    <section class="rounded-xl border border-gray-200 bg-white p-5 sm:p-6">
      <div class="mb-5 flex flex-wrap items-start justify-between gap-3">
        <div>
          <p class="text-xs font-semibold tracking-wide text-brand-600">교통서비스 · 타슈</p>
          <h1 class="mt-1 text-2xl font-bold tracking-tight text-gray-900">타슈 자전거 현황</h1>
          <p class="mt-2 text-sm text-gray-500">역을 선택해 이용할 수 있는 자전거를 확인하세요.</p>
        </div>
        <RouterLink to="/" class="text-sm text-gray-500 hover:text-brand-700">홈으로 〉</RouterLink>
      </div>
      <form class="flex flex-wrap items-end gap-3" @submit.prevent="search">
        <div class="w-full sm:max-w-sm"><BaseSelect v-model="location" :options="LOCATION_OPTIONS" placeholder="전체 역" label="위치" /></div>
        <BaseButton type="submit">검색</BaseButton>
        <BaseButton variant="secondary" @click="list.reset(resetFilters)">초기화</BaseButton>
      </form>
    </section>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <div v-else class="grid items-start gap-6 lg:grid-cols-[1.05fr_1fr]">
      <section class="min-w-0 overflow-hidden rounded-xl border border-gray-200 bg-white" aria-label="자전거 목록">
        <div class="border-b border-gray-200 px-5 py-5">
          <h2 class="font-bold text-gray-900">자전거 목록</h2>
        </div>
        <EmptyState v-if="!list.pageItems.value.length" :title="appliedLocation ? '선택한 역에 이용가능한 자전거가 없습니다.' : '이용가능한 자전거가 없습니다.'" />
        <template v-else>
          <div class="overflow-x-auto">
            <table class="w-full text-left text-sm">
              <thead class="bg-sky-50 text-xs text-gray-600">
                <tr><th class="px-4 py-3 font-medium">ID</th><th class="px-4 py-3 font-medium">현재 위치</th><th class="px-4 py-3 text-right font-medium">보기</th></tr>
              </thead>
              <tbody class="divide-y divide-gray-100">
                <tr v-for="bike in list.pageItems.value" :key="bike.id" :class="selectedId === bike.id ? 'bg-brand-50' : 'hover:bg-gray-50'">
                  <td class="whitespace-nowrap px-4 py-4 font-semibold text-gray-900">{{ formatBikeId(bike.id) }}</td>
                  <td class="whitespace-nowrap px-4 py-4 text-gray-600">{{ locationLabel(bike.location) }}</td>
                  <td class="whitespace-nowrap px-4 py-4 text-right"><BaseButton size="sm" :variant="selectedId === bike.id ? 'primary' : 'secondary'" :aria-pressed="selectedId === bike.id" :aria-label="`${formatBikeId(bike.id)} 상세 보기`" @click="selectedId = bike.id">상세</BaseButton></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="border-t border-gray-100 px-3 pb-4"><BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" /></div>
        </template>
      </section>

      <section class="min-w-0 overflow-hidden rounded-xl border border-gray-200 bg-white" aria-label="선택한 자전거 상세" aria-live="polite">
        <img src="../../assets/images/tashu-bicycle.png" alt="자전거 안내 일러스트" class="aspect-[16/9] w-full bg-[#404040] object-contain" />
        <template v-if="selected">
          <div class="flex items-center justify-between border-b border-gray-200 px-5 py-4">
            <div><p class="text-xs text-gray-500">선택한 자전거</p><h2 class="mt-1 text-lg font-bold">{{ formatBikeId(selected.id) }}</h2></div>
          </div>
          <dl class="divide-y divide-gray-100 text-sm">
            <div class="grid grid-cols-[8rem_1fr] px-5 py-4"><dt class="text-gray-500">자전거 ID</dt><dd class="font-medium">{{ selected.id }}</dd></div>
            <div class="grid grid-cols-[8rem_1fr] px-5 py-4"><dt class="text-gray-500">현재 위치</dt><dd class="font-medium">{{ locationLabel(selected.location) }}</dd></div>
            <div class="grid grid-cols-[8rem_1fr] px-5 py-4"><dt class="text-gray-500">총 주행 거리</dt><dd class="font-medium tabular-nums">{{ formatDistance(selected.distance) }}</dd></div>
          </dl>
        </template>
        <div v-else class="p-8 text-center text-sm text-gray-500">목록에서 자전거를 선택하면 상세 정보가 표시됩니다.</div>
      </section>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import { useListView } from '../../composables/useListView'
import { bikeService } from '../../services/bikeService'
import { LOCATION_OPTIONS, locationLabel } from '../../constants/locations'
import { formatBikeId, formatDistance } from '../../utils/format'

const location = ref('')
const appliedLocation = ref('')
const selectedId = ref(null)
const resetFilters = () => { location.value = ''; appliedLocation.value = '' }
function search() { appliedLocation.value = location.value; list.search() }
const list = useListView(() => bikeService.memberList(), {
  pageSize: 5,
  predicate: bike => bike.status === 'AVAILABLE' && (!appliedLocation.value || bike.location === appliedLocation.value),
  watchSources: [appliedLocation],
})
watch(list.pageItems, rows => {
  if (!rows.some(bike => bike.id === selectedId.value)) selectedId.value = rows[0]?.id ?? null
}, { immediate: true })
const selected = computed(() => list.pageItems.value.find(bike => bike.id === selectedId.value))
</script>
