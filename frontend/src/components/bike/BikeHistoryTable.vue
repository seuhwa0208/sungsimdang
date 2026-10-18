<template>
  <EmptyState v-if="!records.length" title="정비 이력이 없습니다." />
  <BaseTable v-else>
    <template #head>
      <tr>
        <th class="px-4 py-3">기록 번호</th>
        <th class="px-4 py-3">자전거</th>
        <th class="px-4 py-3">정비일</th>
        <th class="px-4 py-3">당시 상태</th>
        <th class="px-4 py-3">고장 유형</th>
        <th class="px-4 py-3">처리자</th>
      </tr>
    </template>
    <tr v-for="r in sorted" :key="r.id">
      <td class="px-4 py-3 text-gray-500">{{ r.id }}</td>
      <td class="px-4 py-3">{{ formatBikeId(bikeId) }}</td>
      <td class="px-4 py-3">{{ formatDateTime(r.createdAt) }}</td>
      <td class="px-4 py-3"><BikeStatusBadge :status="r.status" /></td>
      <td class="px-4 py-3">{{ labelOf(bikeFaultLabels, r.faultType) }}</td>
      <td class="px-4 py-3">{{ r.memberLoginId || '-' }}</td>
    </tr>
  </BaseTable>
</template>

<script setup>
import { computed } from 'vue'
import BaseTable from '../common/BaseTable.vue'
import EmptyState from '../common/EmptyState.vue'
import BikeStatusBadge from './BikeStatusBadge.vue'
import { bikeFaultLabels, labelOf } from '../../utils/statusLabels'
import { formatDateTime } from '../../utils/formatDate'
import { formatBikeId } from '../../utils/format'

// 각 행의 status 는 "기록 당시 상태"이다. 현재 자전거 상태로 덮어쓰지 않는다.
const props = defineProps({ records: { type: Array, default: () => [] }, bikeId: { type: [String, Number], default: null } })
const sorted = computed(() => [...props.records].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)))
</script>
