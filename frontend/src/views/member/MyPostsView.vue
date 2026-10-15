<template>
  <div>
    <PageHeader title="마이페이지" />
    <MyPageNav />
    <LoadingState v-if="loading" />
    <ErrorState v-else-if="error" :error="unavailable ? { message: '내가 쓴 글 조회 API가 아직 연결되지 않았습니다.' } : error" :retryable="!unavailable" @retry="load" />
    <EmptyState v-else-if="!posts.length" title="작성한 글이 없습니다.">
      <BaseButton size="sm" @click="$router.push('/qna/new')">Q&amp;A 작성하기</BaseButton>
    </EmptyState>
    <template v-else>
      <p class="mb-2 text-xs text-gray-500">Q&amp;A 질문 기준</p>
      <BaseTable>
        <template #head>
          <tr><th class="px-4 py-3">제목</th><th class="px-4 py-3">상태</th><th class="px-4 py-3">작성일</th></tr>
        </template>
        <tr v-for="p in posts" :key="p.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`/qna/${p.id}`)">
          <td class="px-4 py-3 font-medium">{{ p.title }}</td>
          <td class="px-4 py-3"><StatusBadge :label="labelOf(qnaStatusLabels, p.status)" :tone="statusTone[p.status]" /></td>
          <td class="px-4 py-3 text-gray-500">{{ formatDate(p.createdAt) }}</td>
        </tr>
      </BaseTable>
    </template>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import MyPageNav from '../../components/common/MyPageNav.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { qnaStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'
import { formatDate } from '../../utils/formatDate'

const { data, loading, error, load } = useAsyncData(() => qnaService.mine())
const posts = computed(() => normalizeList(data.value).items)
const unavailable = computed(() => [404, 405].includes(error.value?.status))
</script>
