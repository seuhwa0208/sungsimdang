<template>
  <div>
    <PageHeader :title="`타슈 고장·정비 ${formatBikeId(id)}`" />

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <LoadingState v-if="bikeLoading" />
    <ErrorState v-else-if="bikeError" :error="bikeError" back-to="/dashboard" @retry="loadBike" />
    <template v-else-if="bike">
      <!-- 현재 상태 카드: 서버가 제공하는 값 + 이력에서 근거 있게 유도한 값만 표시 -->
      <section class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <h2 class="text-lg font-bold">고장 접수</h2>
        <form class="mt-4 space-y-4" @submit.prevent="askFault">
          <BaseSelect v-model="faultType" :options="faultOptions" placeholder="선택" label="고장 유형" :error="faultError" />
          <p v-if="bike.status !== 'UNAVAILABLE'" class="text-sm text-gray-500">대시보드에서 이용불가 상태로 변경한 후 접수할 수 있습니다.</p>
          <BaseButton type="submit" class="w-full" :disabled="bike.status !== 'UNAVAILABLE' || submitting">고장 접수하기</BaseButton>
        </form>
      </section>

      <section class="mt-5 grid gap-4 rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200 sm:grid-cols-4">
        <div><p class="text-xs font-medium text-gray-500">위치</p><p class="mt-1 text-sm">{{ locationLabel(bike.location) }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">현재 상태</p><div class="mt-1"><BikeStatusBadge :status="bike.status" /></div></div>
        <div><p class="text-xs font-medium text-gray-500">누적 거리</p><p class="mt-1 text-sm">{{ formatDistance(bike.distance) }}</p></div>
        <div><p class="text-xs font-medium text-gray-500">최근 정비 기록일</p><p class="mt-1 text-sm">{{ latestRecordAt ? `${formatDate(latestRecordAt)} (${daysAgoText(latestRecordAt)})` : histError ? '조회 불가' : '기록 없음' }}</p></div>
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
import { locationLabel } from '../../constants/locations'
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
import { bikeFaultLabels, labelOf, toOptions } from '../../utils/statusLabels'
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
async function doConfirm() {
  const msg = await run(() => confirm.action())
  if (!msg) return
  confirm.open = false
  flash(typeof msg === 'string' ? msg : '처리했습니다.')
  await Promise.all([loadBike(), loadHistory()]) // 현재 상태와 이력을 서버 기준으로 다시 조회
}
</script>

