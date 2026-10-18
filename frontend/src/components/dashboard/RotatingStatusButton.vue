<template>
  <button
    type="button"
    :disabled="busy || !rotatable"
    :title="rotatable ? `누르면 '${nextLabel}'(으)로 변경됩니다` : undefined"
    :aria-label="rotatable ? `현재 ${currentLabel}. 누르면 ${nextLabel}(으)로 변경` : `현재 ${currentLabel}`"
    :class="[
      'rounded-full transition focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-brand-600',
      busy ? 'cursor-wait opacity-60' : rotatable ? 'cursor-pointer hover:brightness-95' : 'cursor-default',
    ]"
    @click="emit('rotate')"
  >
    <StatusBadge :label="busy ? '변경 중…' : currentLabel" :tone="busy ? 'gray' : statusTone[status]" />
  </button>
</template>

<script setup>
import { computed } from 'vue'
import StatusBadge from '../common/StatusBadge.vue'
import { STATUS_NEXT, labelOf, nextIn, nextStatus, statusTone } from '../../utils/statusLabels'

// 상태 배지를 버튼으로 만든 것. 누를 때마다 rotation 순서대로 돌아가며 변경을 요청한다.
// - 자전거·시설: 이용가능 ↔ 운영중지, 정비중 → 정비 완료. 고장 접수는 별도 화면에서 처리한다.
// - 회원: MEMBER_ROTATION (활성 ↔ 비활성). 순환 목록에 없는 상태(예: 강퇴)나 disabled 면 눌러도 바뀌지 않는다.
// 실제 API 호출은 부모가 처리한다 (rotate 이벤트).
const props = defineProps({
  status: { type: String, default: '' },
  labels: { type: Object, required: true }, // bikeStatusLabels | facilityStatusLabels | memberStatusLabels
  rotation: { type: Array, default: null },
  busy: { type: Boolean, default: false },
  disabled: { type: Boolean, default: false },
})
const emit = defineEmits(['rotate'])

const rotatable = computed(() => !props.disabled && (props.rotation ? props.rotation.includes(props.status) : !!STATUS_NEXT[props.status]))
const currentLabel = computed(() => labelOf(props.labels, props.status))
const nextLabel = computed(() => labelOf(props.labels, props.rotation ? nextIn(props.rotation, props.status) : nextStatus(props.status)))
</script>
