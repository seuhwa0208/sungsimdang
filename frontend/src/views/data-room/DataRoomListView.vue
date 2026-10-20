<template>
  <BoardList title="자료실" description="안내 자료 게시글을 확인할 수 있습니다." base-path="/data-room" :fetcher="dataRoomService.list" :searcher="dataRoomService.search" empty-text="등록된 자료가 없습니다." />
</template>

<script setup>
import BoardList from '../../components/board/BoardList.vue'
import { dataRoomService } from '../../services/dataRoomService'
</script>
