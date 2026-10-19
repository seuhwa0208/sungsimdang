<template>
  <EmptyState v-if="!records.length" title="점검·수리 이력이 없습니다." />
  <BaseTable v-else>
    <template #head>
      <tr>
        <th class="px-4 py-3">기록 번호</th>
        <th class="px-4 py-3">일시</th>
        <th class="px-4 py-3">당시 상태</th>
        <th class="px-4 py-3">고장 유형</th>
        <th class="px-4 py-3">처리자</th>
      </tr>
    </template>
    <tr v-for="r in sorted" :key="r.id">
      <td class="px-4 py-3 text-gray-500">{{ r.id }}</td>
      <td class="px-4 py-3">{{ formatDateTime(r.createdAt) }}</td>
      <td class="px-4 py-3"><FacilityStatusBadge :status="r.facilityStatus" /></td>
      <td class="px-4 py-3">{{ labelOf(facilityFaultLabels, r.faultType) }}</td>
      <td class="px-4 py-3">{{ r.memberLoginId || '-' }}</td>
    </tr>
  </BaseTable>
</template>

<script setup>
import { computed } from 'vue'
import BaseTable from '../common/BaseTable.vue'
import EmptyState from '../common/EmptyState.vue'
import FacilityStatusBadge from './FacilityStatusBadge.vue'
import { facilityFaultLabels, labelOf } from '../../utils/statusLabels'
import { formatDateTime } from '../../utils/formatDate'

const props = defineProps({ records: { type: Array, default: () => [] } })
const sorted = computed(() => [...props.records].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)))
</script>
