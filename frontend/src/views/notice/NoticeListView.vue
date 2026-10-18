<template>
  <BoardList title="공지사항" description="대전교통공사의 운영 안내와 소식입니다." base-path="/notices" :fetcher="noticeService.list" empty-text="등록된 공지사항이 없습니다." />
</template>

<script setup>
import BoardList from '../../components/board/BoardList.vue'
import { noticeService } from '../../services/noticeService'
</script>
