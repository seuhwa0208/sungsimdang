<template>
  <div>
    <div class="mb-6 flex flex-wrap items-end justify-between gap-2">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">관리자 통합 운영현황</h1>
        <p class="mt-1 text-sm text-gray-500">타슈 및 관련 시설의 운영 현황을 한눈에 확인할 수 있습니다.</p>
      </div>
      <p class="text-xs text-gray-400">기준일시 {{ asOf }}</p>
    </div>

    <!-- 요약 카드: 전용 통계 API 가 없으므로 기존 목록 API 응답으로만 집계 -->
    <div class="grid grid-cols-2 gap-3 lg:grid-cols-5">
      <RouterLink v-for="c in cards" :key="c.title" :to="c.to" class="rounded-lg bg-white p-4 shadow-sm ring-1 ring-gray-200 transition hover:ring-brand-500">
        <p class="text-xs font-medium text-gray-500">{{ c.title }}</p>
        <p class="mt-2 text-2xl font-bold text-gray-900">{{ c.loading ? '…' : c.value }}<span class="ml-0.5 text-sm font-normal text-gray-500">{{ c.unit }}</span></p>
      </RouterLink>
    </div>

    <div class="mt-6 grid gap-4 xl:grid-cols-2">
      <!-- 자전거 관리 -->
      <section class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
        <div class="mb-1 flex items-center justify-between"><h2 class="font-bold">자전거 관리</h2><RouterLink to="/dashboard/bikes" class="text-sm text-brand-600 hover:underline">전체 보기 〉</RouterLink></div>
        <p class="mb-3 text-xs text-gray-500">자전거 정보를 조회하고 등록, 상태 변경 및 정비를 관리할 수 있습니다.</p>
        <p v-if="bikes.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <EmptyState v-else-if="!bikeRows.length" title="등록된 자전거가 없습니다." />
        <BaseTable v-else>
          <template #head><tr><th class="px-3 py-2">자전거</th><th class="px-3 py-2">위치</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
          <tr v-for="b in bikeRows" :key="b.id">
            <td class="px-3 py-2 font-medium">{{ formatBikeId(b.id) }}</td><td class="px-3 py-2">{{ b.location }}</td>
            <td class="px-3 py-2"><BikeStatusBadge :status="b.status" /></td>
            <td class="px-3 py-2 text-right"><BaseButton size="sm" variant="secondary" @click="$router.push(`/dashboard/bikes/${b.id}/maintenance`)">고장 접수</BaseButton></td>
          </tr>
        </BaseTable>
      </section>

      <!-- 시설 관리 -->
      <section class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
        <div class="mb-1 flex items-center justify-between"><h2 class="font-bold">기반시설 관리</h2><RouterLink to="/dashboard/facilities" class="text-sm text-brand-600 hover:underline">전체 보기 〉</RouterLink></div>
        <p class="mb-3 text-xs text-gray-500">시설 정보를 조회하고 등록, 점검 및 수리 처리를 관리할 수 있습니다.</p>
        <p v-if="facilities.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <EmptyState v-else-if="!facilityRows.length" title="등록된 시설이 없습니다." />
        <BaseTable v-else>
          <template #head><tr><th class="px-3 py-2">시설</th><th class="px-3 py-2">유형</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
          <tr v-for="f in facilityRows" :key="f.id">
            <td class="px-3 py-2 font-medium">{{ f.facilityNo || f.location }}</td><td class="px-3 py-2">{{ labelOf(facilityTypeLabels, f.facilityType) }}</td>
            <td class="px-3 py-2"><FacilityStatusBadge :status="f.facilityStatus" /></td>
            <td class="px-3 py-2 text-right"><BaseButton size="sm" variant="secondary" @click="$router.push(`/dashboard/facilities/${f.id}/maintenance`)">점검 등록</BaseButton></td>
          </tr>
        </BaseTable>
      </section>

      <!-- 회원 관리 (ADMIN) -->
      <section v-if="auth.isAdmin" class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
        <div class="mb-1 flex items-center justify-between"><h2 class="font-bold">회원 관리</h2><RouterLink to="/dashboard/members" class="text-sm text-brand-600 hover:underline">전체 보기 〉</RouterLink></div>
        <p class="mb-3 text-xs text-gray-500">회원의 권한과 상태를 관리할 수 있습니다.</p>
        <p v-if="members.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <EmptyState v-else-if="!memberRows.length" title="회원이 없습니다." />
        <BaseTable v-else>
          <template #head><tr><th class="px-3 py-2">아이디</th><th class="px-3 py-2">이름</th><th class="px-3 py-2">권한</th><th class="px-3 py-2">상태</th></tr></template>
          <tr v-for="m in memberRows" :key="m.id">
            <td class="px-3 py-2 font-medium">{{ m.loginId }}</td><td class="px-3 py-2">{{ m.name }}</td>
            <td class="px-3 py-2">{{ labelOf(memberRoleLabels, m.role) }}</td>
            <td class="px-3 py-2"><StatusBadge :label="labelOf(memberStatusLabels, m.status)" :tone="statusTone[m.status]" /></td>
          </tr>
        </BaseTable>
      </section>

      <!-- Q&A 고객지원 관리 (ADMIN) -->
      <section v-if="auth.isAdmin" class="rounded-lg bg-white p-5 shadow-sm ring-1 ring-gray-200">
        <div class="mb-1 flex items-center justify-between"><h2 class="font-bold">Q&amp;A 고객지원 관리</h2><RouterLink to="/dashboard/qna" class="text-sm text-brand-600 hover:underline">전체 보기 〉</RouterLink></div>
        <p class="mb-3 text-xs text-gray-500">고객 문의를 확인하고 답변 작성 및 상태 관리를 할 수 있습니다.</p>
        <p v-if="qna.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <EmptyState v-else-if="!qnaRows.length" title="답변 대기 중인 질문이 없습니다." />
        <BaseTable v-else>
          <template #head><tr><th class="px-3 py-2">번호</th><th class="px-3 py-2">제목</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
          <tr v-for="q in qnaRows" :key="q.id">
            <td class="px-3 py-2 text-gray-500">{{ q.id }}</td><td class="px-3 py-2">{{ q.title }}</td>
            <td class="px-3 py-2"><StatusBadge :label="labelOf(qnaStatusLabels, q.status)" :tone="statusTone[q.status]" /></td>
            <td class="px-3 py-2 text-right"><BaseButton size="sm" variant="secondary" @click="$router.push(`/qna/${q.id}`)">답변 작성</BaseButton></td>
          </tr>
        </BaseTable>
      </section>
    </div>

    <p class="mt-6 text-xs text-gray-400">최근 정비 이력 요약은 전체 이력 조회 API 확정 후 추가합니다. (현재는 자전거·시설별 이력 화면에서 확인)</p>
  </div>
</template>

<script setup>
import { computed, reactive } from 'vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import BikeStatusBadge from '../../components/bike/BikeStatusBadge.vue'
import FacilityStatusBadge from '../../components/facility/FacilityStatusBadge.vue'
import { useAuthStore } from '../../stores/authStore'
import { bikeService } from '../../services/bikeService'
import { facilityService } from '../../services/facilityService'
import { adminMemberService } from '../../services/memberService'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { formatBikeId } from '../../utils/format'
import { formatDateTime } from '../../utils/formatDate'
import { facilityTypeLabels, memberRoleLabels, memberStatusLabels, qnaStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'

const auth = useAuthStore()
const makeState = () => reactive({ loading: true, failed: false, items: [], total: 0 })
const bikes = makeState()
const facilities = makeState()
const members = makeState()
const qna = makeState()
const asOf = formatDateTime(new Date().toISOString()) // 데이터를 조회한 시각

// 한 목록이 실패해도 나머지는 표시한다.
async function fill(state, fetcher) {
  try {
    const res = normalizeList(await fetcher())
    state.items = res.items
    state.total = res.total
  } catch {
    state.failed = true
  } finally {
    state.loading = false
  }
}
fill(bikes, () => bikeService.list())
fill(facilities, () => facilityService.list())
if (auth.isAdmin) {
  fill(members, () => adminMemberService.list())
  fill(qna, () => qnaService.list())
} else {
  members.loading = false
  qna.loading = false
}

const count = (state, key, value) => state.items.filter((i) => i[key] === value).length
const show = (state, v) => (state.failed ? '-' : v)

const cards = computed(() => {
  const list = []
  if (auth.isAdmin) list.push({ title: '전체 회원 수', to: '/dashboard/members', loading: members.loading, value: show(members, members.total), unit: '명' })
  list.push(
    { title: '이용가능 자전거', to: '/dashboard/bikes', loading: bikes.loading, value: show(bikes, count(bikes, 'status', 'AVAILABLE')), unit: '대' },
    { title: '정비중 자전거', to: '/dashboard/bikes', loading: bikes.loading, value: show(bikes, count(bikes, 'status', 'REPAIRING')), unit: '대' },
    { title: '정상 시설', to: '/dashboard/facilities', loading: facilities.loading, value: show(facilities, count(facilities, 'facilityStatus', 'AVAILABLE')), unit: '개' },
    { title: '정비중 시설', to: '/dashboard/facilities', loading: facilities.loading, value: show(facilities, count(facilities, 'facilityStatus', 'REPAIRING')), unit: '개' },
  )
  return list
})

// 패널에는 확인이 필요한 항목(비정상 상태 우선)을 먼저 5건 보여준다.
const attention = (items, key) => [...items].sort((a, b) => Number(a[key] === 'AVAILABLE') - Number(b[key] === 'AVAILABLE') || a.id - b.id).slice(0, 5)
const bikeRows = computed(() => attention(bikes.items, 'status'))
const facilityRows = computed(() => attention(facilities.items, 'facilityStatus'))
const memberRows = computed(() => [...members.items].sort((a, b) => b.id - a.id).slice(0, 5))
const qnaRows = computed(() => qna.items.filter((q) => q.status === 'WAITING').sort((a, b) => b.id - a.id).slice(0, 5))
</script>
