<template>
  <div>
    <PageHeader title="타슈 관리" description="자전거 위치·상태를 관리하고 고장 접수 화면으로 이동합니다.">
      <BaseButton @click="openCreate">자전거 등록</BaseButton>
    </PageHeader>

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <ListToolbar v-model="list.keyword.value" placeholder="번호·위치 검색" @search="list.search" @reset="list.reset(resetFilters)">
      <div class="w-40"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체 상태" label="상태" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 자전거가 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3">자전거</th><th class="px-4 py-3">위치</th><th class="px-4 py-3">상태</th><th class="px-4 py-3">누적 거리</th><th class="px-4 py-3 text-right">관리</th></tr>
        </template>
        <tr v-for="b in list.pageItems.value" :key="b.id">
          <td class="px-4 py-3 font-medium">{{ formatBikeId(b.id) }}</td>
          <td class="px-4 py-3">{{ b.location }}</td>
          <td class="px-4 py-3"><BikeStatusBadge :status="b.status" /></td>
          <td class="px-4 py-3 text-gray-600">{{ formatDistance(b.distance) }}</td>
          <td class="px-4 py-3">
            <div class="flex justify-end gap-1.5">
              <BaseButton size="sm" variant="secondary" @click="openStatus(b)">상태 변경</BaseButton>
              <BaseButton size="sm" variant="secondary" @click="openEdit(b)">위치 수정</BaseButton>
              <BaseButton size="sm" @click="$router.push(`/dashboard/bikes/${b.id}/maintenance`)">고장·정비</BaseButton>
            </div>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>

    <!-- 등록 / 위치 수정 -->
    <BaseModal v-model="formOpen" :title="editing ? `자전거 ${formatBikeId(editing.id)} 위치 수정` : '자전거 등록'" :persistent="submitting">
      <!-- 자전거 번호(bikeNo)는 입력받지 않는다. 식별자는 서버가 부여하는 id 이다. -->
      <BaseSelect v-model="location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="locationError" />
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="formOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" @click="saveForm">{{ editing ? '수정' : '등록' }}</BaseButton>
      </template>
    </BaseModal>

    <!-- 상태 변경 -->
    <BaseModal v-model="statusOpen" title="상태 변경" :persistent="submitting">
      <p class="mb-3 text-sm text-gray-700">자전거 {{ formatBikeId(target?.id) }} · {{ target?.location }}</p>
      <BaseSelect v-model="newStatus" :options="statusOptions" label="변경할 상태" />
      <p v-if="newStatus !== target?.status" class="mt-3 text-sm text-gray-600">'{{ labelOf(bikeStatusLabels, target?.status) }}' → '{{ labelOf(bikeStatusLabels, newStatus) }}' 로 변경합니다.</p>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="statusOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" :disabled="newStatus === target?.status" @click="saveStatus">변경</BaseButton>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BaseModal from '../../components/common/BaseModal.vue'
import ListToolbar from '../../components/common/ListToolbar.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { useSubmit } from '../../composables/useSubmit'
import { bikeService } from '../../services/bikeService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { bikeStatusLabels, labelOf, toOptions } from '../../utils/statusLabels'
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

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
function flash(msg) {
  notice.value = msg
  setTimeout(() => (notice.value = ''), 4000)
}

// ---- 등록 / 위치 수정 ----
const formOpen = ref(false)
const editing = ref(null)
const location = ref('')
const locationError = ref('')
// 기존 위치가 임시 목록에 없더라도 선택 가능하도록 옵션에 포함
const locationOptions = computed(() => {
  const names = new Set(LOCATION_OPTIONS)
  if (editing.value?.location) names.add(editing.value.location)
  return [...names].map((v) => ({ value: v, label: v }))
})
function openCreate() {
  editing.value = null
  location.value = ''
  locationError.value = ''
  clearError()
  formOpen.value = true
}
function openEdit(b) {
  editing.value = b
  location.value = b.location
  locationError.value = ''
  clearError()
  formOpen.value = true
}
async function saveForm() {
  locationError.value = location.value ? '' : '위치를 선택해 주세요.'
  if (locationError.value) return
  const ok = await run(() => (editing.value ? bikeService.update(editing.value.id, location.value) : bikeService.create(location.value)))
  if (!ok) return
  formOpen.value = false
  flash(editing.value ? '자전거 위치를 수정했습니다.' : '자전거를 등록했습니다.')
  await list.load() // 서버 최신 데이터로 재조회
}

// ---- 상태 변경 ----
const statusOpen = ref(false)
const target = ref(null)
const newStatus = ref('')
function openStatus(b) {
  target.value = b
  newStatus.value = b.status
  clearError()
  statusOpen.value = true
}
async function saveStatus() {
  const ok = await run(() => bikeService.updateStatus(target.value.id, newStatus.value))
  if (!ok) return
  statusOpen.value = false
  flash(`자전거 ${formatBikeId(target.value.id)} 상태를 '${labelOf(bikeStatusLabels, newStatus.value)}'(으)로 변경했습니다.`)
  await list.load()
}
</script>
