<template>
  <div>
    <PageHeader :title="`타슈 고장·정비 ${formatBikeId(id)}`" />

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <LoadingState v-if="bikeLoading" />
    <ErrorState v-else-if="bikeError" :error="bikeError" back-to="/dashboard/bikes" @retry="loadBike" />
    <template v-else-if="bike">
      <!-- 현재 상태 카드: 서버가 제공하는 값 + 이력에서 근거 있게 유도한 값만 표시 -->
      <section class="grid gap-4 rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200 sm:grid-cols-4">
        <div><p class="text-xs font-medium text-gray-500">위치</p><p class="mt-1 text-sm">{{ bike.location }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">현재 상태</p><div class="mt-1"><BikeStatusBadge :status="bike.status" /></div></div>
        <div><p class="text-xs font-medium text-gray-500">누적 거리</p><p class="mt-1 text-sm">{{ formatDistance(bike.distance) }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">최근 정비 기록일</p><p class="mt-1 text-sm">{{ latestRecordAt ? `${formatDate(latestRecordAt)} (${daysAgoText(latestRecordAt)})` : '데이터 없음' }}</p></div>
      </section>

      <section class="mt-4 grid gap-4 lg:grid-cols-2">
        <div class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
          <h2 class="font-bold">고장 접수</h2>
          <p class="mt-1 text-xs text-gray-500">접수하면 새 정비 이력이 추가됩니다. 기존 이력은 수정되지 않습니다.</p>
          <div class="mt-3 flex items-end gap-2">
            <div class="flex-1"><BaseSelect v-model="faultType" :options="faultOptions" placeholder="고장 유형 선택" label="고장 유형" :error="faultError" /></div>
            <BaseButton @click="askFault">접수</BaseButton>
          </div>
        </div>
        <div class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
          <h2 class="font-bold">현재 상태 변경</h2>
          <p class="mt-1 text-xs text-gray-500">자전거의 현재 상태만 바꿉니다. 과거 이력의 당시 상태는 그대로 유지됩니다.</p>
          <div class="mt-3 flex flex-wrap gap-2">
            <BaseButton v-for="s in statusActions" :key="s.value" variant="secondary" :disabled="bike.status === s.value" @click="askStatus(s)">{{ s.label }}</BaseButton>
          </div>
        </div>
      </section>

      <section class="mt-6">
        <h2 class="mb-3 font-bold">정비 이력</h2>
        <LoadingState v-if="histLoading" />
        <ErrorState v-else-if="histError" :error="histError" :retryable="true" @retry="loadHistory" />
        <BikeHistoryTable v-else :records="records" :bike-id="id" />
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
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import BikeHistoryTable from '../../components/bike/BikeHistoryTable.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { useSubmit } from '../../composables/useSubmit'
import { bikeService } from '../../services/bikeService'
import { bikeFaultLabels, bikeStatusLabels, labelOf, toOptions } from '../../utils/statusLabels'
import { formatBikeId, formatDistance } from '../../utils/format'
import { formatDate, daysAgoText } from '../../utils/formatDate'
import { normalizeList } from '../../utils/list'

const props = defineProps({ id: { type: String, required: true } }) // Bike.id

const { data: bike, loading: bikeLoading, error: bikeError, load: loadBike } = useAsyncData(() => bikeService.get(props.id))
const { data: hist, loading: histLoading, error: histError, load: loadHistory } = useAsyncData(() => bikeService.maintenance(props.id))
const records = computed(() => normalizeList(hist.value).items)
// "최근 점검일"은 모델에 직접 필드가 없으므로 최신 이력의 createdAt 에서 유도한다.
const latestRecordAt = computed(() => records.value.reduce((max, r) => (!max || new Date(r.createdAt) > new Date(max) ? r.createdAt : max), null))

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const flash = (m) => {
  notice.value = m
  setTimeout(() => (notice.value = ''), 4000)
}

const faultOptions = toOptions(bikeFaultLabels)
const faultType = ref('')
const faultError = ref('')
const statusActions = [
  { value: 'REPAIRING', label: '정비 시작 (정비중)' },
  { value: 'AVAILABLE', label: '정비 완료 (이용가능)' },
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
    message: `${formatBikeId(props.id)} 자전거에 '${labelOf(bikeFaultLabels, type)}' 고장을 접수합니다.\n새 정비 이력이 추가됩니다.`,
    confirmText: '접수',
    action: async () => {
      await bikeService.addMaintenance(props.id, type) // 서버로는 Enum 원문 전송
      faultType.value = ''
      return '고장을 접수했습니다.'
    },
  })
}
function askStatus(s) {
  openConfirm({
    title: '상태 변경',
    message: `현재 상태를 '${labelOf(bikeStatusLabels, bike.value.status)}' → '${labelOf(bikeStatusLabels, s.value)}' 로 변경합니다.`,
    confirmText: '변경',
    action: async () => {
      await bikeService.updateStatus(props.id, s.value)
      return '상태를 변경했습니다.'
    },
  })
}
async function doConfirm() {
  const msg = await run(() => confirm.action())
  if (!msg) return
  confirm.open = false
  flash(typeof msg === 'string' ? msg : '처리했습니다.')
  await Promise.all([loadBike(), loadHistory()]) // 현재 상태와 이력을 서버 기준으로 다시 조회
}
</script>
