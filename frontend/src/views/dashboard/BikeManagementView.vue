<template>
  <div>
    <ManageToolbar
      v-model="list.keyword.value"
      title="자전거 관리"
      keyword-label="자전거 번호"
      keyword-placeholder="자전거 번호 입력"
      deletable
      register-label="자전거 등록"
      @search="list.search"
      @delete="askRemoveByNumber"
      @register="openCreate"
    >
      <template #icon><StatIcon name="bike" /></template>
      <div class="w-28"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체" label="상태" /></div>
    </ManageToolbar>

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>
    <p v-if="toolbarError" class="mb-3 rounded-md bg-red-50 p-3 text-sm text-red-700" role="alert">{{ toolbarError }}</p>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 자전거가 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3 text-center">자전거 번호</th><th class="px-4 py-3 text-center">위치</th><th class="px-4 py-3 text-center">상태</th><th class="px-4 py-3 text-center">작업</th></tr>
        </template>
        <tr v-for="b in list.pageItems.value" :key="b.id">
          <td class="px-4 py-3 text-center font-medium">{{ formatBikeId(b.id) }}</td>
          <td class="px-4 py-3 text-center">{{ locationLabel(b.location) }}</td>
          <td class="px-4 py-3 text-center"><BikeStatusBadge :status="b.status" /></td>
          <td class="px-4 py-3">
            <div class="flex justify-center gap-1.5">
              <BaseButton size="sm" variant="secondary" @click="openEdit(b)">정보 수정</BaseButton>
              <BaseButton size="sm" variant="secondary" @click="$router.push(`/dashboard/bikes/${b.id}/maintenance`)">고장 접수</BaseButton>
            </div>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>

    <!-- 등록 / 정보 수정 (수정 시 상태 변경·삭제도 여기서) -->
    <BaseModal v-model="formOpen" :title="editing ? `자전거 ${formatBikeId(editing.id)} 정보 수정` : '자전거 등록'" :persistent="submitting">
      <!-- 자전거 번호(bikeNo)는 입력받지 않는다. 식별자는 서버가 부여하는 id 이다. -->
      <BaseSelect v-model="location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="locationError" />
      <div v-if="editing" class="mt-3"><BaseSelect v-model="newStatus" :options="statusOptions.filter(option => option.value !== 'REPAIRING' || option.value === newStatus)" label="상태" /></div>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton v-if="editing" class="mr-auto" variant="danger" :disabled="submitting" @click="askRemoveFromForm">삭제</BaseButton>
        <BaseButton variant="secondary" :disabled="submitting" @click="formOpen = false">취소</BaseButton>
        <BaseButton :loading="submitting" @click="saveForm">{{ editing ? '수정' : '등록' }}</BaseButton>
      </template>
    </BaseModal>

    <!-- 삭제 확인 (DELETE /api/bike/{id}) -->
    <ConfirmDialog v-model="removeOpen" title="자전거 삭제" :message="`자전거 ${formatBikeId(removeTarget?.id)} (${removeTarget?.location ?? ''}) 을(를) 삭제합니다.\n삭제한 자전거는 복구할 수 없습니다.`" confirm-text="삭제" :loading="submitting" :error="error?.message" @confirm="doRemove" />
  </div>
</template>

<script setup>
import { locationLabel } from '../../constants/locations'
import { computed, ref } from 'vue'
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
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { useSubmit } from '../../composables/useSubmit'
import { bikeService } from '../../services/bikeService'
import { changeOperationStatus } from '../../services/operationService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { bikeStatusLabels, toOptions } from '../../utils/statusLabels'
import { formatBikeId } from '../../utils/format'

const status = ref('')
const statusOptions = toOptions(bikeStatusLabels)
const list = useListView((p) => bikeService.list(p), {
  searchFields: (b) => [b.id, formatBikeId(b.id), b.location, locationLabel(b.location)],
  predicate: (b) => !status.value || b.status === status.value,
  sort: (a, b) => (a.id ?? 0) - (b.id ?? 0),
  watchSources: [status],
})

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const toolbarError = ref('')
function flash(msg) {
  notice.value = msg
  setTimeout(() => (notice.value = ''), 4000)
}

// ---- 등록 / 정보 수정 ----
const formOpen = ref(false)
const editing = ref(null)
const location = ref('')
const locationError = ref('')
const newStatus = ref('')
// 기존 위치가 임시 목록에 없더라도 선택 가능하도록 옵션에 포함
const locationOptions = computed(() => {
  const options = [...LOCATION_OPTIONS]
  const current = editing.value?.location
  if (current && !options.some(option => option.value === current)) options.push({ value: current, label: current })
  return options
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
  newStatus.value = b.status
  locationError.value = ''
  clearError()
  formOpen.value = true
}
async function saveForm() {
  locationError.value = location.value ? '' : '위치를 선택해 주세요.'
  if (locationError.value) return
  const ok = await run(async () => {
    if (!editing.value) return bikeService.create(location.value)
    const b = editing.value
    if (location.value !== b.location) await bikeService.update(b.id, location.value)
    if (newStatus.value !== b.status) await changeOperationStatus(bikeService, b.id, b.status, newStatus.value)
    return true
  })
  if (!ok) return
  formOpen.value = false
  flash(editing.value ? '자전거 정보를 수정했습니다.' : '자전거를 등록했습니다.')
  await list.load() // 서버 최신 데이터로 재조회
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
// 상단 "삭제" 버튼: 입력한 자전거 번호와 일치하는 자전거를 삭제 확인창으로 보낸다.
function askRemoveByNumber() {
  toolbarError.value = ''
  const kw = list.keyword.value.trim().toLowerCase()
  const hit = kw && list.items.value.find((b) => String(b.id) === kw || formatBikeId(b.id).toLowerCase() === kw)
  if (!hit) {
    toolbarError.value = '삭제할 자전거 번호를 정확히 입력한 뒤 삭제를 눌러 주세요.'
    return
  }
  removeTarget.value = hit
  clearError()
  removeOpen.value = true
}
async function doRemove() {
  // 204(본문 없음) 응답은 빈 문자열이라 run() 이 실패로 오해하지 않도록 true 를 돌려준다.
  const ok = await run(async () => {
    await bikeService.remove(removeTarget.value.id)
    return true
  })
  if (!ok) return
  removeOpen.value = false
  flash(`자전거 ${formatBikeId(removeTarget.value.id)} 을(를) 삭제했습니다.`)
  await list.load()
}
</script>
