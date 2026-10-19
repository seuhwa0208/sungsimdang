<template>
  <div class="md:flex md:gap-8">
    <MyPageNav />
    <div class="min-w-0 flex-1">
      <PageHeader title="내가 쓴 글" />
      <LoadingState v-if="loading" />
      <ErrorState v-else-if="error" :error="unavailable ? { message: '내가 쓴 글 조회 API가 아직 연결되지 않았습니다.' } : error" :retryable="!unavailable" @retry="load" />
      <EmptyState v-else-if="!posts.length" title="작성한 글이 없습니다.">
        <BaseButton size="sm" @click="$router.push('/qna/new')">Q&amp;A 작성하기</BaseButton>
      </EmptyState>
      <template v-else>
        <!-- 와이어프레임: 상태 선택("전체") + 제목 검색 -->
        <form class="mb-4 flex items-center gap-2" role="search" @submit.prevent>
          <div class="w-36 shrink-0"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체" /></div>
          <label for="my-posts-keyword" class="sr-only">제목 검색</label>
          <input id="my-posts-keyword" v-model="keyword" placeholder="제목 검색" class="min-w-0 flex-1 rounded-md border border-gray-300 px-3 py-2 text-sm focus:outline-none focus:ring-2 focus:ring-brand-500" />
        </form>
        <EmptyState v-if="!filtered.length" title="검색 결과가 없습니다." />
        <template v-else>
          <BaseTable>
            <template #head>
              <tr><th class="w-16 px-4 py-3">번호</th><th class="px-4 py-3">제목</th><th class="px-4 py-3">작성일</th><th class="px-4 py-3">상태</th></tr>
            </template>
            <tr v-for="p in pageItems" :key="p.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`/qna/${p.id}`)">
              <td class="px-4 py-3 text-gray-500">{{ p.id }}</td>
              <td class="px-4 py-3 font-medium">{{ p.title }}</td>
              <td class="whitespace-nowrap px-4 py-3 text-gray-500">{{ formatDate(p.createdAt) }}</td>
              <td class="whitespace-nowrap px-4 py-3"><StatusBadge :label="labelOf(qnaStatusLabels, p.status)" :tone="statusTone[p.status]" /></td>
            </tr>
          </BaseTable>
          <BasePagination v-model:page="page" :total-pages="totalPages" always />
        </template>
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import PageHeader from '../../components/common/PageHeader.vue'
import MyPageNav from '../../components/common/MyPageNav.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { usePaging } from '../../composables/usePaging'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { qnaStatusLabels, labelOf, statusTone, toOptions } from '../../utils/statusLabels'
import { formatDate } from '../../utils/formatDate'

const { data, loading, error, load } = useAsyncData(() => qnaService.mine())
const posts = computed(() => normalizeList(data.value).items)
const unavailable = computed(() => [404, 405].includes(error.value?.status))

const status = ref('')
const keyword = ref('')
const statusOptions = toOptions(qnaStatusLabels)
// 최신 글이 위로 오도록 id 내림차순
const filtered = computed(() => {
  const kw = keyword.value.trim().toLowerCase()
  return [...posts.value]
    .filter((p) => (!status.value || p.status === status.value) && (!kw || String(p.title ?? '').toLowerCase().includes(kw)))
    .sort((a, b) => (b.id ?? 0) - (a.id ?? 0))
})
const { page, totalPages, pageItems } = usePaging(filtered, 10)
watch([status, keyword], () => {
  page.value = 1
})
</script>
