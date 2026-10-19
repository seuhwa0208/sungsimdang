<template><BoardDetail :id="id" label="자료실" base-path="/data-room" :service="dataRoomService" /></template>

<script setup>
import BoardDetail from '../../components/board/BoardDetail.vue'
import { dataRoomService } from '../../services/dataRoomService'

defineProps({ id: { type: String, required: true } })
</script>
