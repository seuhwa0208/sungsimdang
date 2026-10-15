<template><StatusBadge :label="labelOf(bikeStatusLabels, status)" :tone="statusTone[status]" /></template>

<script setup>
import StatusBadge from '../common/StatusBadge.vue'
import { bikeStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'

defineProps({ status: { type: String, default: '' } })
</script>
