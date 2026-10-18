<template>
  <div>
    <ManageToolbar
      v-model="list.keyword.value"
      title="시설 관리"
      keyword-label="시설 번호"
      keyword-placeholder="시설 검색"
      deletable
      register-label="시설 등록"
      @search="list.search"
      @delete="askRemoveByNumber"
      @register="openCreate"
    >
      <template #icon><StatIcon name="building" /></template>
      <div class="w-28"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체" label="상태" /></div>
    </ManageToolbar>

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>
    <p v-if="toolbarError" class="mb-3 rounded-md bg-red-50 p-3 text-sm text-red-700" role="alert">{{ toolbarError }}</p>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 시설이 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3 text-center">시설번호</th><th class="px-4 py-3 text-center">유형</th><th class="px-4 py-3 text-center">상태</th><th class="px-4 py-3 text-center">작업</th></tr>
        </template>
        <tr v-for="f in list.pageItems.value" :key="f.id">
          <td class="px-4 py-3 text-center font-medium">{{ f.facilityNo || '-' }}</td>
          <td class="px-4 py-3 text-center">{{ labelOf(facilityTypeLabels, f.facilityType) }}</td>
          <td class="px-4 py-3 text-center"><FacilityStatusBadge :status="f.facilityStatus" /></td>
          <td class="px-4 py-3">
            <div class="flex justify-center gap-1.5">
              <BaseButton size="sm" variant="secondary" @click="openEdit(f)">정보 수정</BaseButton>
              <BaseButton size="sm" variant="secondary" @click="$router.push(`/dashboard/facilities/${f.id}/maintenance`)">점검 등록</BaseButton>
            </div>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>

    <!-- 등록 / 정보 수정 (수정 시 상태 변경·삭제도 여기서) -->
    <BaseModal v-model="formOpen" :title="editing ? '시설 정보 수정' : '시설 등록'" :persistent="submitting">
      <div class="space-y-4">
        <!-- facilityNo 는 FacilityRequest 에 없으므로 입력받지 않는다. (서버 부여 여부는 최신 Entity 확인) -->
        <BaseSelect v-model="form.facilityType" :options="typeOptions" label="시설 유형" placeholder="유형을 선택하세요" required :error="errors.facilityType" />
        <BaseSelect v-model="form.location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="errors.location" />
        <BaseSelect v-if="editing" v-model="newStatus" :options="statusOptions" label="상태" />
      </div>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton v-if="editing" class="mr-auto" variant="danger" :disabled="submitting" @click="askRemoveFromForm">삭제</BaseButton>
        <BaseButton variant="secondary" :disabled="submitting" @click="formOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" @click="saveForm">{{ editing ? '수정' : '등록' }}</BaseButton>
      </template>
    </BaseModal>

    <!-- 삭제 확인 (DELETE /api/facilities/{id}) -->
    <ConfirmDialog v-model="removeOpen" title="시설 삭제" :message="`${labelOf(facilityTypeLabels, removeTarget?.facilityType)} · ${removeTarget?.location ?? ''} 시설을 삭제합니다.\n삭제한 시설은 복구할 수 없습니다.`" confirm-text="삭제" :loading="submitting" :error="error?.message" @confirm="doRemove" />
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import ManageToolbar from '../../components/common/ManageToolbar.vue'
import StatIcon from '../../components/common/StatIcon.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BaseModal from '../../components/common/BaseModal.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import ConfirmDialog from '../../components/common/ConfirmDialog.vue'
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { useSubmit } from '../../composables/useSubmit'
import { facilityService } from '../../services/facilityService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { facilityStatusLabels, facilityTypeLabels, labelOf, toOptions } from '../../utils/statusLabels'

const status = ref('')
const typeOptions = toOptions(facilityTypeLabels)
const statusOptions = toOptions(facilityStatusLabels)
const list = useListView((p) => facilityService.list(p), {
  searchFields: (f) => [f.id, f.facilityNo, f.location, labelOf(facilityTypeLabels, f.facilityType)],
  predicate: (f) => !status.value || f.facilityStatus === status.value,
  sort: (a, b) => (a.id ?? 0) - (b.id ?? 0),
  watchSources: [status],
})

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const toolbarError = ref('')
const flash = (m) => {
  notice.value = m
  setTimeout(() => (notice.value = ''), 4000)
}

// ---- 등록 / 정보 수정 ----
const formOpen = ref(false)
const editing = ref(null)
const form = reactive({ facilityType: '', location: '' })
const newStatus = ref('')
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
  newStatus.value = f.facilityStatus
  Object.keys(errors).forEach((k) => delete errors[k])
  clearError()
  formOpen.value = true
}
async function saveForm() {
  errors.facilityType = form.facilityType ? '' : '시설 유형을 선택해 주세요.'
  errors.location = form.location ? '' : '위치를 선택해 주세요.'
  if (errors.facilityType || errors.location) return
  const payload = { facilityType: form.facilityType, location: form.location }
  const ok = await run(async () => {
    if (!editing.value) return facilityService.create(payload)
    const f = editing.value
    if (payload.facilityType !== f.facilityType || payload.location !== f.location) await facilityService.update(f.id, payload)
    if (newStatus.value !== f.facilityStatus) await facilityService.updateStatus(f.id, newStatus.value)
    return true
  })
  if (!ok) return
  formOpen.value = false
  flash(editing.value ? '시설 정보를 수정했습니다.' : '시설을 등록했습니다.')
  await list.load()
}

// ---- 삭제 ----
const removeOpen = ref(false)
const removeTarget = ref(null)
function askRemoveFromForm() {
  removeTarget.value = editing.value
  formOpen.value = false
  clearError()
  removeOpen.value = true
}
// 상단 "삭제" 버튼: 입력한 시설 번호와 일치하는 시설을 삭제 확인창으로 보낸다.
function askRemoveByNumber() {
  toolbarError.value = ''
  const kw = list.keyword.value.trim().toLowerCase()
  const hit = kw && list.items.value.find((f) => String(f.id) === kw || String(f.facilityNo ?? '').toLowerCase() === kw)
  if (!hit) {
    toolbarError.value = '삭제할 시설 번호를 정확히 입력한 뒤 삭제를 눌러 주세요.'
    return
  }
  removeTarget.value = hit
  clearError()
  removeOpen.value = true
}
async function doRemove() {
  // 204(본문 없음) 응답은 빈 문자열이라 run() 이 실패로 오해하지 않도록 true 를 돌려준다.
  const ok = await run(async () => {
    await facilityService.remove(removeTarget.value.id)
    return true
  })
  if (!ok) return
  removeOpen.value = false
  flash('시설을 삭제했습니다.')
  await list.load()
}
</script>
