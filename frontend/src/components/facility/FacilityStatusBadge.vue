<template><StatusBadge :label="labelOf(facilityStatusLabels, status)" :tone="statusTone[status]" /></template>

<script setup>
import StatusBadge from '../common/StatusBadge.vue'
import { facilityStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'

defineProps({ status: { type: String, default: '' } })
</script>
