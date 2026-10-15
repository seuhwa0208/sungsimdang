<template>
  <div class="space-y-10">
    <!-- 소개 -->
    <section class="rounded-xl bg-white px-8 py-12 shadow-sm ring-1 ring-gray-200 sm:px-12">
      <h1 class="text-2xl font-bold leading-snug text-gray-900 sm:text-3xl">
        시민과 함께하는<br />편리한 대중교통 서비스,<br />대전교통공사가 만들어갑니다
      </h1>
      <p class="mt-4 max-w-xl text-sm text-gray-600 sm:text-base">타슈, 교통 인프라 등 대전의 대중교통 정보를 통합적으로 제공하는 대전교통공사 통합운영관리시스템입니다.</p>
    </section>

    <!-- 타슈 / 기반시설 현황 요약: 목록 API 응답으로 집계 가능한 값만 표시 -->
    <section class="grid gap-6 lg:grid-cols-2">
      <div class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <div class="flex items-center justify-between"><h2 class="text-lg font-bold">타슈 운영 현황</h2><RouterLink to="/bikes" class="text-sm text-brand-600 hover:underline">더보기 〉</RouterLink></div>
        <p class="mt-1 text-sm text-gray-500">대전 공공자전거 ‘타슈’의 이용 현황을 확인할 수 있습니다.</p>
        <p v-if="bikes.error" class="mt-6 text-center text-sm text-gray-500">현황을 불러오지 못했습니다.</p>
        <div v-else class="mt-5 grid grid-cols-3 gap-3 text-center">
          <div v-for="s in bikeStats" :key="s.key" class="rounded-md bg-gray-50 p-4">
            <p class="text-xs text-gray-500">{{ s.label }}</p>
            <p class="mt-1 text-2xl font-bold">{{ bikes.loading ? '…' : s.count }}<span class="ml-0.5 text-sm font-normal text-gray-500">대</span></p>
          </div>
        </div>
      </div>

      <div class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <div class="flex items-center justify-between"><h2 class="text-lg font-bold">기반시설 현황</h2><RouterLink to="/facilities" class="text-sm text-brand-600 hover:underline">더보기 〉</RouterLink></div>
        <p class="mt-1 text-sm text-gray-500">엘리베이터, 스크린도어 등 시설의 현황을 확인할 수 있습니다.</p>
        <p v-if="facilities.error" class="mt-6 text-center text-sm text-gray-500">현황을 불러오지 못했습니다.</p>
        <div v-else class="mt-5 grid grid-cols-3 gap-3 text-center">
          <div v-for="s in facilityStats" :key="s.key" class="rounded-md bg-gray-50 p-4">
            <p class="text-xs text-gray-500">{{ s.label }}</p>
            <p class="mt-1 text-2xl font-bold">{{ facilities.loading ? '…' : s.count }}<span class="ml-0.5 text-sm font-normal text-gray-500">개</span></p>
          </div>
        </div>
        <ul v-if="typeStats.length" class="mt-4 divide-y divide-gray-100 text-sm">
          <li v-for="t in typeStats" :key="t.type" class="flex justify-between py-2"><span>{{ t.label }}</span><span class="text-gray-600">{{ t.count }}개</span></li>
        </ul>
      </div>
    </section>

    <!-- 최근 공지사항 / Q&A -->
    <section class="grid gap-6 lg:grid-cols-2">
      <div class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <div class="flex items-center justify-between"><h2 class="text-lg font-bold">최근 공지사항</h2><RouterLink to="/notices" class="text-sm text-brand-600 hover:underline">더보기 〉</RouterLink></div>
        <p class="mt-1 text-sm text-gray-500">대전교통공사의 주요 소식을 확인할 수 있습니다.</p>
        <LoadingState v-if="notices.loading" />
        <p v-else-if="notices.error" class="py-8 text-center text-sm text-gray-500">공지사항을 불러오지 못했습니다.</p>
        <EmptyState v-else-if="!recentNotices.length" title="등록된 공지사항이 없습니다." />
        <ul v-else class="mt-3 divide-y divide-gray-100">
          <li v-for="n in recentNotices" :key="n.id">
            <RouterLink :to="`/notices/${n.id}`" class="flex items-center justify-between gap-4 py-3 text-sm hover:text-brand-700">
              <span class="truncate">{{ n.title }}</span><span class="shrink-0 text-xs text-gray-400">{{ formatDate(n.createdAt) }}</span>
            </RouterLink>
          </li>
        </ul>
      </div>

      <div class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <div class="flex items-center justify-between"><h2 class="text-lg font-bold">Q&amp;A</h2><RouterLink to="/qna" class="text-sm text-brand-600 hover:underline">더보기 〉</RouterLink></div>
        <p class="mt-1 text-sm text-gray-500">자주 찾는 질문</p>
        <LoadingState v-if="qna.loading" />
        <p v-else-if="qna.error" class="py-8 text-center text-sm text-gray-500">Q&amp;A를 불러오지 못했습니다.</p>
        <EmptyState v-else-if="!recentQna.length" title="등록된 질문이 없습니다." />
        <ul v-else class="mt-3 divide-y divide-gray-100">
          <li v-for="q in recentQna" :key="q.id">
            <RouterLink :to="`/qna/${q.id}`" class="flex items-center justify-between gap-4 py-3 text-sm hover:text-brand-700">
              <span class="truncate">{{ q.title }}</span><span class="shrink-0 text-xs text-gray-400">{{ formatDate(q.createdAt) }}</span>
            </RouterLink>
          </li>
        </ul>
      </div>
    </section>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import LoadingState from '../../components/common/LoadingState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { bikeService } from '../../services/bikeService'
import { facilityService } from '../../services/facilityService'
import { noticeService } from '../../services/noticeService'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { formatDate } from '../../utils/formatDate'
import { bikeStatusLabels, facilityStatusLabels, facilityTypeLabels, labelOf } from '../../utils/statusLabels'

// 각 영역은 독립적으로 조회한다. 한 곳이 실패해도 나머지는 그대로 표시된다.
const bikes = useAsyncData(() => bikeService.list())
const facilities = useAsyncData(() => facilityService.list())
const notices = useAsyncData(() => noticeService.list())
const qna = useAsyncData(() => qnaService.list())

const itemsOf = (src) => normalizeList(src.data.value).items
const newest = (arr) => [...arr].sort((a, b) => (b.id ?? 0) - (a.id ?? 0)).slice(0, 5)

const bikeStats = computed(() =>
  ['AVAILABLE', 'REPAIRING', 'UNAVAILABLE'].map((key) => ({ key, label: bikeStatusLabels[key], count: itemsOf(bikes).filter((b) => b.status === key).length })),
)
const facilityStats = computed(() =>
  ['AVAILABLE', 'REPAIRING', 'UNAVAILABLE'].map((key) => ({ key, label: facilityStatusLabels[key], count: itemsOf(facilities).filter((f) => f.facilityStatus === key).length })),
)
const typeStats = computed(() => {
  const counts = {}
  itemsOf(facilities).forEach((f) => (counts[f.facilityType] = (counts[f.facilityType] || 0) + 1))
  return Object.entries(counts).map(([type, count]) => ({ type, count, label: labelOf(facilityTypeLabels, type) }))
})
const recentNotices = computed(() => newest(itemsOf(notices)))
const recentQna = computed(() => newest(itemsOf(qna)))
</script>
