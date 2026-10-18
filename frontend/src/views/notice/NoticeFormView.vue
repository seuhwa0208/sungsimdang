<template><BoardForm :id="id" label="공지사항" base-path="/notices" :service="noticeService" /></template>

<script setup>
import BoardForm from '../../components/board/BoardForm.vue'
import { noticeService } from '../../services/noticeService'

defineProps({ id: { type: String, default: null } })
</script>
