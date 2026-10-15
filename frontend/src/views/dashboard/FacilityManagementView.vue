<template>
  <div>
    <PageHeader title="기반시설 관리" description="시설 정보와 현재 상태를 관리하고 점검·수리 이력 화면으로 이동합니다.">
      <BaseButton @click="openCreate">시설 등록</BaseButton>
    </PageHeader>

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <ListToolbar v-model="list.keyword.value" placeholder="시설 번호·위치 검색" @search="list.search" @reset="list.reset(resetFilters)">
      <div class="w-40"><BaseSelect v-model="type" :options="typeOptions" placeholder="전체 유형" label="유형" /></div>
      <div class="w-40"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체 상태" label="상태" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 시설이 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3">시설 번호</th><th class="px-4 py-3">유형</th><th class="px-4 py-3">위치</th><th class="px-4 py-3">상태</th><th class="px-4 py-3 text-right">관리</th></tr>
        </template>
        <tr v-for="f in list.pageItems.value" :key="f.id">
          <td class="px-4 py-3 font-medium">{{ f.facilityNo || '-' }}</td>
          <td class="px-4 py-3">{{ labelOf(facilityTypeLabels, f.facilityType) }}</td>
          <td class="px-4 py-3">{{ f.location }}</td>
          <td class="px-4 py-3"><FacilityStatusBadge :status="f.facilityStatus" /></td>
          <td class="px-4 py-3">
            <div class="flex justify-end gap-1.5">
              <BaseButton size="sm" variant="secondary" @click="openStatus(f)">상태 변경</BaseButton>
              <BaseButton size="sm" variant="secondary" @click="openEdit(f)">정보 수정</BaseButton>
              <BaseButton size="sm" @click="$router.push(`/dashboard/facilities/${f.id}/maintenance`)">점검·수리</BaseButton>
            </div>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>

    <BaseModal v-model="formOpen" :title="editing ? '시설 정보 수정' : '시설 등록'" :persistent="submitting">
      <div class="space-y-4">
        <!-- facilityNo 는 FacilityRequest 에 없으므로 입력받지 않는다. (서버 부여 여부는 최신 Entity 확인) -->
        <BaseSelect v-model="form.facilityType" :options="typeOptions" label="시설 유형" placeholder="유형을 선택하세요" required :error="errors.facilityType" />
        <BaseSelect v-model="form.location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="errors.location" />
      </div>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="formOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" @click="saveForm">{{ editing ? '수정' : '등록' }}</BaseButton>
      </template>
    </BaseModal>

    <BaseModal v-model="statusOpen" title="시설 상태 변경" :persistent="submitting">
      <p class="mb-3 text-sm text-gray-700">{{ labelOf(facilityTypeLabels, target?.facilityType) }} · {{ target?.location }}</p>
      <BaseSelect v-model="newStatus" :options="statusOptions" label="변경할 상태" />
      <p v-if="newStatus !== target?.facilityStatus" class="mt-3 text-sm text-gray-600">'{{ labelOf(facilityStatusLabels, target?.facilityStatus) }}' → '{{ labelOf(facilityStatusLabels, newStatus) }}' 로 변경합니다.</p>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="statusOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" :disabled="newStatus === target?.facilityStatus" @click="saveStatus">변경</BaseButton>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
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
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { useSubmit } from '../../composables/useSubmit'
import { facilityService } from '../../services/facilityService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { facilityStatusLabels, facilityTypeLabels, labelOf, toOptions } from '../../utils/statusLabels'

const type = ref('')
const status = ref('')
const typeOptions = toOptions(facilityTypeLabels)
const statusOptions = toOptions(facilityStatusLabels)
const resetFilters = () => {
  type.value = ''
  status.value = ''
}
const list = useListView((p) => facilityService.list(p), {
  searchFields: (f) => [f.facilityNo, f.location],
  predicate: (f) => (!type.value || f.facilityType === type.value) && (!status.value || f.facilityStatus === status.value),
  sort: (a, b) => (a.id ?? 0) - (b.id ?? 0),
  watchSources: [type, status],
})

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const flash = (m) => {
  notice.value = m
  setTimeout(() => (notice.value = ''), 4000)
}

const formOpen = ref(false)
const editing = ref(null)
const form = reactive({ facilityType: '', location: '' })
const errors = reactive({})
const locationOptions = computed(() => {
  const names = new Set(LOCATION_OPTIONS)
  if (editing.value?.location) names.add(editing.value.location)
  return [...names].map((v) => ({ value: v, label: v }))
})
function openCreate() {
  editing.value = null
  Object.assign(form, { facilityType: '', location: '' })
  Object.keys(errors).forEach((k) => delete errors[k])
  clearError()
  formOpen.value = true
}
function openEdit(f) {
  editing.value = f
  Object.assign(form, { facilityType: f.facilityType, location: f.location })
  Object.keys(errors).forEach((k) => delete errors[k])
  clearError()
  formOpen.value = true
}
async function saveForm() {
  errors.facilityType = form.facilityType ? '' : '시설 유형을 선택해 주세요.'
  errors.location = form.location ? '' : '위치를 선택해 주세요.'
  if (errors.facilityType || errors.location) return
  const payload = { facilityType: form.facilityType, location: form.location }
  const ok = await run(() => (editing.value ? facilityService.update(editing.value.id, payload) : facilityService.create(payload)))
  if (!ok) return
  formOpen.value = false
  flash(editing.value ? '시설 정보를 수정했습니다.' : '시설을 등록했습니다.')
  await list.load()
}

const statusOpen = ref(false)
const target = ref(null)
const newStatus = ref('')
function openStatus(f) {
  target.value = f
  newStatus.value = f.facilityStatus
  clearError()
  statusOpen.value = true
}
async function saveStatus() {
  const ok = await run(() => facilityService.updateStatus(target.value.id, newStatus.value))
  if (!ok) return
  statusOpen.value = false
  flash(`시설 상태를 '${labelOf(facilityStatusLabels, newStatus.value)}'(으)로 변경했습니다.`)
  await list.load()
}
</script>
