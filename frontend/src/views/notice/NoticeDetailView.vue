<template><BoardDetail :id="id" label="공지사항" base-path="/notices" :service="noticeService" /></template>

<script setup>
import BoardDetail from '../../components/board/BoardDetail.vue'
import { noticeService } from '../../services/noticeService'

defineProps({ id: { type: String, required: true } })
</script>
