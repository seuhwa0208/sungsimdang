<template>
  <div>
    <ManageToolbar v-model="list.keyword.value" title="Q&A 관리" keyword-label="게시글 번호" keyword-placeholder="게시글 검색" @search="list.search">
      <div class="w-32"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체" label="상태" /></div>
    </ManageToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 질문이 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr><th class="w-16 px-4 py-3">번호</th><th class="px-4 py-3">제목</th><th class="px-4 py-3">상태</th><th class="hidden px-4 py-3 sm:table-cell">작성일</th><th class="px-4 py-3 text-right">관리</th></tr>
        </template>
        <tr v-for="q in list.pageItems.value" :key="q.id">
          <td class="px-4 py-3 text-gray-500">{{ q.id }}</td>
          <td class="px-4 py-3 font-medium">{{ q.title }}</td>
          <td class="px-4 py-3"><StatusBadge :label="labelOf(qnaStatusLabels, q.status)" :tone="statusTone[q.status]" /></td>
          <td class="hidden px-4 py-3 text-gray-500 sm:table-cell">{{ formatDate(q.createdAt) }}</td>
          <td class="px-4 py-3 text-right">
            <BaseButton size="sm" :variant="q.status === 'WAITING' ? 'primary' : 'secondary'" @click="$router.push(`/qna/${q.id}`)">{{ q.status === 'WAITING' ? '답변하기' : '답변 보기' }}</BaseButton>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" boundary-links />
    </template>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import ManageToolbar from '../../components/common/ManageToolbar.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import { useListView } from '../../composables/useListView'
import { qnaService } from '../../services/qnaService'
import { qnaStatusLabels, labelOf, statusTone, toOptions } from '../../utils/statusLabels'
import { formatDate } from '../../utils/formatDate'

const status = ref('WAITING') // 관리자는 기본적으로 답변 대기 건을 먼저 본다
const statusOptions = toOptions(qnaStatusLabels)
const list = useListView((p) => qnaService.list(p), {
  searchFields: (q) => [q.id, q.title, q.content],
  predicate: (q) => !status.value || q.status === status.value,
  sort: (a, b) => (b.id ?? 0) - (a.id ?? 0),
  watchSources: [status],
})
</script>
