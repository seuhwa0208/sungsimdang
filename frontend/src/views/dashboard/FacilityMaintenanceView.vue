<template>
  <div>
    <PageHeader title="시설 점검·수리" />

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <LoadingState v-if="facLoading" />
    <ErrorState v-else-if="facError" :error="facError" back-to="/dashboard/facilities" @retry="loadFacility" />
    <template v-else-if="facility">
      <section class="grid gap-4 rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200 sm:grid-cols-4">
        <div><p class="text-xs font-medium text-gray-500">시설 번호</p><p class="mt-1 text-sm">{{ facility.facilityNo || '-' }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">유형</p><p class="mt-1 text-sm">{{ labelOf(facilityTypeLabels, facility.facilityType) }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">위치</p><p class="mt-1 text-sm">{{ facility.location }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">현재 상태</p><div class="mt-1"><FacilityStatusBadge :status="facility.facilityStatus" /></div></div>
      </section>

      <section class="mt-4 grid gap-4 lg:grid-cols-2">
        <div class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
          <h2 class="font-bold">고장 접수</h2>
          <p class="mt-1 text-xs text-gray-500">접수하면 새 점검·수리 이력이 추가됩니다. 기존 이력은 수정되지 않습니다.</p>
          <div class="mt-3 flex items-end gap-2">
            <div class="flex-1"><BaseSelect v-model="faultType" :options="faultOptions" placeholder="고장 유형 선택" label="고장 유형" :error="faultError" /></div>
            <BaseButton @click="askFault">접수</BaseButton>
          </div>
        </div>
        <div class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
          <h2 class="font-bold">현재 상태 변경</h2>
          <p class="mt-1 text-xs text-gray-500">시설의 현재 상태만 바꿉니다. 과거 이력의 당시 상태는 그대로 유지됩니다.</p>
          <div class="mt-3 flex flex-wrap gap-2">
            <BaseButton v-for="s in statusActions" :key="s.value" variant="secondary" :disabled="facility.facilityStatus === s.value" @click="askStatus(s)">{{ s.label }}</BaseButton>
          </div>
        </div>
      </section>

      <section class="mt-6">
        <h2 class="mb-3 font-bold">점검·수리 이력</h2>
        <LoadingState v-if="histLoading" />
        <ErrorState v-else-if="histError" :error="histError" @retry="loadHistory" />
        <FacilityHistoryTable v-else :records="records" />
      </section>
    </template>

    <ConfirmDialog v-model="confirm.open" :title="confirm.title" :message="confirm.message" :confirm-text="confirm.confirmText" :loading="submitting" :error="error?.message" @confirm="doConfirm" />
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import ConfirmDialog from '../../components/common/ConfirmDialog.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import FacilityHistoryTable from '../../components/facility/FacilityHistoryTable.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { facilityService } from '../../services/facilityService'
import { facilityFaultLabels, facilityStatusLabels, facilityTypeLabels, labelOf, toOptions } from '../../utils/statusLabels'
import { normalizeList } from '../../utils/list'

const props = defineProps({ id: { type: String, required: true } })

const { data: facility, loading: facLoading, error: facError, load: loadFacility } = useAsyncData(() => facilityService.get(props.id))
const { data: hist, loading: histLoading, error: histError, load: loadHistory } = useAsyncData(() => facilityService.maintenance(props.id))
const records = computed(() => normalizeList(hist.value).items)

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const flash = (m) => {
  notice.value = m
  setTimeout(() => (notice.value = ''), 4000)
}

const faultOptions = toOptions(facilityFaultLabels)
const faultType = ref('')
const faultError = ref('')
const statusActions = [
  { value: 'REPAIRING', label: '수리 시작 (정비중)' },
  { value: 'AVAILABLE', label: '수리 완료 (정상)' },
  { value: 'UNAVAILABLE', label: '운영중지 처리' },
]

const confirm = reactive({ open: false, title: '', message: '', confirmText: '확인', action: null })
function openConfirm(cfg) {
  clearError()
  Object.assign(confirm, { open: true, ...cfg })
}
function askFault() {
  faultError.value = faultType.value ? '' : '고장 유형을 선택해 주세요.'
  if (faultError.value) return
  const type = faultType.value
  openConfirm({
    title: '고장 접수',
    message: `'${labelOf(facilityFaultLabels, type)}' 고장을 접수합니다.\n새 점검·수리 이력이 추가됩니다.`,
    confirmText: '접수',
    action: async () => {
      await facilityService.addMaintenance(props.id, type)
      faultType.value = ''
      return '고장을 접수했습니다.'
    },
  })
}
function askStatus(s) {
  openConfirm({
    title: '상태 변경',
    message: `현재 상태를 '${labelOf(facilityStatusLabels, facility.value.facilityStatus)}' → '${labelOf(facilityStatusLabels, s.value)}' 로 변경합니다.`,
    confirmText: '변경',
    action: async () => {
      await facilityService.updateStatus(props.id, s.value)
      return '상태를 변경했습니다.'
    },
  })
}
async function doConfirm() {
  const msg = await run(() => confirm.action())
  if (!msg) return
  confirm.open = false
  flash(typeof msg === 'string' ? msg : '처리했습니다.')
  await Promise.all([loadFacility(), loadHistory()])
}
</script>
