<template><BoardForm :id="id" label="자료실" base-path="/data-room" :service="dataRoomService" /></template>

<script setup>
import BoardForm from '../../components/board/BoardForm.vue'
import { dataRoomService } from '../../services/dataRoomService'

defineProps({ id: { type: String, default: null } })
</script>
