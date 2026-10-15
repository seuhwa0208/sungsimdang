<template>
  <div>
    <PageHeader title="Q&A" description="궁금한 점을 질문하면 담당자가 답변해 드립니다.">
      <BaseButton v-if="auth.isAuthenticated" @click="$router.push('/qna/new')">질문하기</BaseButton>
      <BaseButton v-else variant="secondary" @click="$router.push({ name: 'login', query: { redirect: '/qna/new' } })">로그인 후 질문하기</BaseButton>
    </PageHeader>
    <BoardTabs />

    <ListToolbar v-model="list.keyword.value" placeholder="제목·내용 검색" @search="list.search" @reset="list.reset(resetFilters)">
      <div class="w-40"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체 상태" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" :title="list.keyword.value || status ? '검색 결과가 없습니다.' : '등록된 질문이 없습니다.'" />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr>
            <th class="w-16 px-4 py-3">번호</th>
            <th class="px-4 py-3">제목</th>
            <th v-if="showAuthor" class="hidden px-4 py-3 sm:table-cell">작성자</th>
            <th class="px-4 py-3">상태</th>
            <th class="hidden px-4 py-3 sm:table-cell">조회수</th>
            <th class="px-4 py-3">작성일</th>
            <th v-if="auth.isAdmin" class="px-4 py-3 text-right">답변</th>
          </tr>
        </template>
        <tr v-for="q in list.pageItems.value" :key="q.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`/qna/${q.id}`)">
          <td class="px-4 py-3 text-gray-500">{{ q.id }}</td>
          <td class="px-4 py-3 font-medium text-gray-900">{{ q.title }}</td>
          <td v-if="showAuthor" class="hidden px-4 py-3 text-gray-600 sm:table-cell">{{ q.author || '-' }}</td>
          <td class="px-4 py-3"><StatusBadge :label="labelOf(qnaStatusLabels, q.status)" :tone="statusTone[q.status]" /></td>
          <td class="hidden px-4 py-3 text-gray-500 sm:table-cell">{{ q.hits ?? 0 }}</td>
          <td class="px-4 py-3 text-gray-500">{{ formatDate(q.createdAt) }}</td>
          <td v-if="auth.isAdmin" class="px-4 py-3 text-right text-xs font-medium text-brand-600">{{ q.status === 'WAITING' ? '답변 등록' : '답변 수정' }}</td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import BoardTabs from '../../components/board/BoardTabs.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import ListToolbar from '../../components/common/ListToolbar.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import { useAuthStore } from '../../stores/authStore'
import { useListView } from '../../composables/useListView'
import { qnaService } from '../../services/qnaService'
import { qnaStatusLabels, labelOf, statusTone, toOptions } from '../../utils/statusLabels'
import { formatDate } from '../../utils/formatDate'

const auth = useAuthStore()
const status = ref('')
const statusOptions = toOptions(qnaStatusLabels)
const resetFilters = () => {
  status.value = ''
}
const list = useListView((p) => qnaService.list(p), {
  searchFields: (q) => [q.title, q.content, q.author],
  predicate: (q) => !status.value || q.status === status.value,
  sort: (a, b) => (b.id ?? 0) - (a.id ?? 0),
  watchSources: [status],
})
const showAuthor = computed(() => list.items.value.some((q) => q.author))
</script>
