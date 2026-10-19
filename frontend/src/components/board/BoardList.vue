<template>
  <div>
    <PageHeader :title="title" :description="description">
      <BaseButton v-if="canWrite" @click="$router.push(`${basePath}/new`)">글쓰기</BaseButton>
    </PageHeader>
    <BoardTabs />

    <ListToolbar v-model="list.keyword.value" placeholder="키워드를 입력하세요" @search="list.search" @reset="list.reset(resetField)">
      <div class="w-32"><BaseSelect v-model="field" :options="fieldOptions" label="검색 항목" /></div>
    </ListToolbar>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" :title="list.keyword.value ? '검색 결과가 없습니다.' : emptyText" />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr>
            <th class="w-16 px-4 py-3">번호</th>
            <th class="px-4 py-3">제목</th>
            <th v-if="showAuthor" class="hidden px-4 py-3 sm:table-cell">작성자</th>
            <th class="px-4 py-3">작성일</th>
            <th v-if="showHits" class="hidden px-4 py-3 sm:table-cell">조회수</th>
          </tr>
        </template>
        <tr v-for="row in list.pageItems.value" :key="row.id" class="cursor-pointer hover:bg-gray-50" @click="$router.push(`${basePath}/${row.id}`)">
          <td class="px-4 py-3 text-gray-500">{{ row.id }}</td>
          <td class="px-4 py-3 font-medium text-gray-900">{{ row.title }}</td>
          <td v-if="showAuthor" class="hidden px-4 py-3 text-gray-600 sm:table-cell">{{ row.author || '-' }}</td>
          <td class="px-4 py-3 text-gray-500">{{ formatDate(row.createdAt) }}</td>
          <td v-if="showHits" class="hidden px-4 py-3 text-gray-500 sm:table-cell">{{ row.hits ?? '-' }}</td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" boundary-links />
    </template>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import PageHeader from '../common/PageHeader.vue'
import BaseButton from '../common/BaseButton.vue'
import BaseSelect from '../common/BaseSelect.vue'
import ListToolbar from '../common/ListToolbar.vue'
import LoadingState from '../common/LoadingState.vue'
import ErrorState from '../common/ErrorState.vue'
import EmptyState from '../common/EmptyState.vue'
import BaseTable from '../common/BaseTable.vue'
import BasePagination from '../common/BasePagination.vue'
import BoardTabs from './BoardTabs.vue'
import { useAuthStore } from '../../stores/authStore'
import { useListView } from '../../composables/useListView'
import { formatDate } from '../../utils/formatDate'

// 공지사항 / 자료실 목록 공용. 작성자·조회수는 응답에 실제로 있을 때만 컬럼을 표시한다.
const props = defineProps({
  title: String,
  description: { type: String, default: '' },
  basePath: String, // '/notices' | '/data-room'
  fetcher: Function,
  emptyText: { type: String, default: '등록된 게시글이 없습니다.' },
})

const auth = useAuthStore()
const canWrite = computed(() => auth.isAdmin)

const field = ref('all')
const fieldOptions = [
  { value: 'all', label: '전체' },
  { value: 'title', label: '제목' },
  { value: 'content', label: '내용' },
  { value: 'author', label: '작성자' },
]
const resetField = () => {
  field.value = 'all'
}

const list = useListView((params) => props.fetcher(params), {
  searchFields: (r) => (field.value === 'all' ? [r.title, r.content, r.author] : [r[field.value]]),
  sort: (a, b) => (b.id ?? 0) - (a.id ?? 0),
  watchSources: [field],
})
const showAuthor = computed(() => list.items.value.some((r) => r.author))
const showHits = computed(() => list.items.value.some((r) => r.hits != null))
</script>
