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
        <div class="mt-5 grid gap-3 sm:grid-cols-3">
          <div v-for="s in bikeStats" :key="s.key" class="rounded-xl bg-sky-100 px-4 py-3">
            <p class="text-sm text-gray-800">{{ s.label }}</p>
            <div class="mt-1 flex items-center justify-between gap-2">
              <p class="text-xl font-bold tabular-nums text-gray-900">{{ bikes.loading.value ? '조회 중' : bikes.error.value ? '조회 불가' : s.count.toLocaleString('ko-KR') + '대' }}</p>
              <StatIcon :name="s.icon" />
            </div>
          </div>
        </div>

        <!-- 서비스 안내 / 이용안내 (타슈 운영 현황 카드 안쪽) -->
        <div class="mt-6 border-t border-gray-100 pt-5" role="group" aria-label="이용안내">

          <div class="mt-4 flex items-start gap-4">
            <svg class="h-14 w-14 shrink-0" viewBox="0 0 80 80" fill="none" aria-hidden="true">
              <defs>
                <linearGradient id="guide-clock-grad" x1="10" y1="8" x2="70" y2="72" gradientUnits="userSpaceOnUse">
                  <stop offset="0" stop-color="#4cc3ff" />
                  <stop offset="1" stop-color="#1a6fe0" />
                </linearGradient>
              </defs>
              <circle cx="40" cy="40" r="38" fill="url(#guide-clock-grad)" />
              <circle cx="40" cy="40" r="29" fill="#fff" />
              <g stroke="#1f2937" stroke-width="2.5" stroke-linecap="round">
                <path d="M40 15v5M40 60v5M15 40h5M60 40h5" />
                <path d="M40 40V26" />
                <path d="M40 40l10 8" />
              </g>
              <circle cx="40" cy="40" r="2.5" fill="#1f2937" />
            </svg>

            <div class="min-w-0">
              <h4 class="text-base font-bold text-brand-600">이용안내</h4>
              <dl class="mt-2 grid grid-cols-[auto_1fr] gap-x-4 gap-y-1.5 text-sm text-gray-700">
                <dt class="font-bold">이용대상</dt>
                <dd>만 15세 이상 자전거 주행이 가능한 자</dd>
                <dt class="font-bold">대여시간</dt>
                <dd>05시 ~ 24시</dd>
                <dt class="font-bold">반납시간</dt>
                <dd>24시간</dd>
              </dl>
              <p class="mt-2 text-sm leading-6 text-gray-600">※ 이용 성수기인 3월부터 11월까지는 24시간 운영합니다.</p>
            </div>
          </div>
        </div>
      </div>

      <div class="rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200">
        <div class="flex items-center justify-between"><h2 class="text-lg font-bold">기반시설 현황</h2><RouterLink to="/facilities" class="text-sm text-brand-600 hover:underline">더보기 〉</RouterLink></div>
        <div class="mt-5 grid gap-3 sm:grid-cols-3">
          <div v-for="s in facilityStats" :key="s.key" class="rounded-xl bg-sky-100 px-4 py-3">
            <p class="text-sm text-gray-800">{{ s.label }}</p>
            <div class="mt-1 flex items-center justify-between gap-2">
              <p class="text-xl font-bold tabular-nums text-gray-900">{{ facilities.loading.value ? '조회 중' : facilities.error.value ? '조회 불가' : s.count.toLocaleString('ko-KR') + '개' }}</p>
              <StatIcon :name="s.icon" />
            </div>
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
        <LoadingState v-if="notices.loading.value" />
        <p v-else-if="notices.error.value" class="py-8 text-center text-sm text-gray-500">공지사항을 불러오지 못했습니다.</p>
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
        <LoadingState v-if="qna.loading.value" />
        <p v-else-if="qna.error.value" class="py-8 text-center text-sm text-gray-500">Q&amp;A를 불러오지 못했습니다.</p>
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
import StatIcon from '../../components/common/StatIcon.vue'
import { useAsyncData } from '../../composables/useAsyncData'
import { bikeService } from '../../services/bikeService'
import { facilityService } from '../../services/facilityService'
import { noticeService } from '../../services/noticeService'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { formatDate } from '../../utils/formatDate'
import { facilityTypeLabels, labelOf } from '../../utils/statusLabels'

// 각 영역은 독립적으로 조회한다. 한 곳이 실패해도 나머지는 그대로 표시된다.
// 메인 화면은 사용자용 엔드포인트(/api/bike/member, /api/facilities/member)를 사용한다.
const bikes = useAsyncData(() => bikeService.memberList())
const facilities = useAsyncData(() => facilityService.memberList())
const notices = useAsyncData(() => noticeService.list())
const qna = useAsyncData(() => qnaService.list())

const itemsOf = (src) => normalizeList(src.data.value).items
const newest = (arr) => [...arr].sort((a, b) => (b.id ?? 0) - (a.id ?? 0)).slice(0, 3)

// 와이어프레임 메인 화면 카드 순서·문구·아이콘을 따른다.
// 상태 문구는 실제 백엔드 Enum 의미에 맞춘다.
const BIKE_CARDS = [
  { key: 'AVAILABLE', label: '이용가능 자전거', icon: 'bike' },
  { key: 'REPAIRING', label: '정비중', icon: 'wrench' },
  { key: 'UNAVAILABLE', label: '이용불가', icon: 'ban' },
]
const FACILITY_CARDS = [
  { key: 'AVAILABLE', label: '이용가능 시설', icon: 'building' },
  { key: 'REPAIRING', label: '점검중', icon: 'wrench' },
  { key: 'UNAVAILABLE', label: '이용불가', icon: 'ban' },
]
const bikeStats = computed(() => BIKE_CARDS.map((c) => ({ ...c, count: itemsOf(bikes).filter((b) => b.status === c.key).length })))
const facilityStats = computed(() => FACILITY_CARDS.map((c) => ({ ...c, count: itemsOf(facilities).filter((f) => f.facilityStatus === c.key).length })))
const typeStats = computed(() => {
  const counts = {}
  itemsOf(facilities).forEach((f) => (counts[f.facilityType] = (counts[f.facilityType] || 0) + 1))
  return Object.entries(counts).map(([type, count]) => ({ type, count, label: labelOf(facilityTypeLabels, type) }))
})
const recentNotices = computed(() => newest(itemsOf(notices)))
const recentQna = computed(() => newest(itemsOf(qna)))
</script>
