<template>
  <div class="flex flex-1 flex-col lg:min-h-0">
    <div class="mb-3 flex flex-wrap items-center justify-between gap-4">
      <div>
        <h1 class="text-2xl font-bold text-gray-900">관리자 통합 운영현황</h1>
        <p class="mt-1 text-sm text-gray-500">타슈 및 관련 시설의 운영 현황을 한눈에 확인할 수 있습니다.</p>
      </div>
      <!-- 요약 카드: 전용 통계 API 가 없으므로 기존 목록 API 응답으로만 집계 -->
      <div class="flex flex-wrap items-center gap-2">
        <RouterLink v-for="c in cards" :key="c.title" :to="c.to" class="flex items-center gap-2 rounded-md bg-white px-3 py-1.5 shadow-sm ring-1 ring-gray-200 transition hover:ring-brand-500">
          <StatIcon :name="c.icon" class="h-5! w-5!" />
          <div class="min-w-0">
            <p class="text-[11px] font-medium leading-tight text-gray-500">{{ c.title }}</p>
            <p class="text-base font-bold leading-tight text-gray-900">{{ c.loading ? '…' : c.value }}<span class="ml-0.5 text-xs font-normal text-gray-500">{{ c.unit }}</span></p>
          </div>
        </RouterLink>
      </div>
    </div>

    <p v-if="notice" class="fixed bottom-4 left-1/2 z-40 -translate-x-1/2 rounded-md bg-green-50 px-4 py-2 text-sm text-green-800 shadow-lg ring-1 ring-green-200" role="status">{{ notice }}</p>
    <p v-if="statusError" class="fixed bottom-4 left-1/2 z-40 -translate-x-1/2 rounded-md bg-red-50 px-4 py-2 text-sm text-red-700 shadow-lg ring-1 ring-red-200" role="alert">{{ statusError }}</p>



    <div :class="['grid gap-3 lg:min-h-0 lg:flex-1 lg:grid-cols-2', auth.isAdmin ? 'lg:grid-rows-2' : 'lg:grid-rows-1']">
      <!-- 자전거 관리 -->
      <section class="flex min-h-0 flex-col rounded-lg bg-white p-3 shadow-sm ring-1 ring-gray-200 lg:overflow-hidden [&_td]:py-1! [&_th]:py-1! [&_.py-16]:py-6!">
        <div class="mb-2 flex items-center justify-between gap-2"><h2 class="font-bold">자전거 관리</h2><BaseButton size="xs" @click="openBikeForm(null)">자전거 등록</BaseButton></div>
        <p v-if="bikes.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <template v-else>
          <PanelSearch v-model="bikeList.keyword.value" status-button :status-active="statusSort.bike" label="자전거 번호" placeholder="자전거 번호 입력" @search="bikeList.search" @status-click="toggleStatusSort('bike')" />
          <EmptyState v-if="!bikeList.filtered.value.length" :title="bikeList.applied.value ? '검색 결과가 없습니다.' : '등록된 자전거가 없습니다.'" />
          <template v-else>
            <BaseTable>
              <template #head><tr><th class="px-3 py-2">자전거</th><th class="px-3 py-2">위치</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
              <tr v-for="b in bikeList.pageItems.value" :key="b.id">
                <td class="px-3 py-2 font-medium">{{ formatBikeId(b.id) }}</td><td class="px-3 py-2">{{ b.location }}</td>
                <td class="px-3 py-2">
                  <RotatingStatusButton :status="b.status" :labels="bikeStatusLabels" :busy="pending.has(`bike-${b.id}`)" @rotate="rotate('bike', b)" />
                </td>
                <td class="whitespace-nowrap px-3 py-2">
                  <div class="flex justify-end gap-1.5">
                    <BaseButton size="xs" variant="secondary" @click="openBikeForm(b)">정보 수정</BaseButton>
                    <BaseButton size="xs" variant="secondary" @click="$router.push(`/dashboard/bikes/${b.id}/maintenance`)">고장 접수</BaseButton>
                  </div>
                </td>
              </tr>
            </BaseTable>
            <BasePagination v-model:page="bikeList.page.value" :total-pages="bikeList.totalPages.value" :max-visible="5" always compact class="mt-auto" />
          </template>
        </template>
      </section>

      <!-- 시설 관리 -->
      <section class="flex min-h-0 flex-col rounded-lg bg-white p-3 shadow-sm ring-1 ring-gray-200 lg:overflow-hidden [&_td]:py-1! [&_th]:py-1! [&_.py-16]:py-6!">
        <div class="mb-2 flex items-center justify-between gap-2"><h2 class="font-bold">기반시설 관리</h2><BaseButton size="xs" @click="openFacilityForm(null)">시설 등록</BaseButton></div>
        <p v-if="facilities.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <template v-else>
          <PanelSearch v-model="facilityList.keyword.value" status-button :status-active="statusSort.facility" label="시설 번호" placeholder="시설 검색" @search="facilityList.search" @status-click="toggleStatusSort('facility')" />
          <EmptyState v-if="!facilityList.filtered.value.length" :title="facilityList.applied.value ? '검색 결과가 없습니다.' : '등록된 시설이 없습니다.'" />
          <template v-else>
            <BaseTable>
              <template #head><tr><th class="px-3 py-2">시설</th><th class="px-3 py-2">유형</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
              <tr v-for="f in facilityList.pageItems.value" :key="f.id">
                <td class="px-3 py-2 font-medium">{{ formatFacilityId(f.id) }}</td><td class="px-3 py-2">{{ labelOf(facilityTypeLabels, f.facilityType) }}</td>
                <td class="px-3 py-2">
                  <RotatingStatusButton :status="f.facilityStatus" :labels="facilityStatusLabels" :busy="pending.has(`facility-${f.id}`)" @rotate="rotate('facility', f)" />
                </td>
                <td class="whitespace-nowrap px-3 py-2">
                  <div class="flex justify-end gap-1.5">
                    <BaseButton size="xs" variant="secondary" @click="openFacilityForm(f)">정보 수정</BaseButton>
                    <BaseButton size="xs" variant="secondary" @click="$router.push(`/dashboard/facilities/${f.id}/maintenance`)">점검 등록</BaseButton>
                  </div>
                </td>
              </tr>
            </BaseTable>
            <BasePagination v-model:page="facilityList.page.value" :total-pages="facilityList.totalPages.value" :max-visible="5" always compact class="mt-auto" />
          </template>
        </template>
      </section>

      <!-- 회원 관리 (ADMIN) -->
      <section v-if="auth.isAdmin" class="flex min-h-0 flex-col rounded-lg bg-white p-3 shadow-sm ring-1 ring-gray-200 lg:overflow-hidden [&_td]:py-1! [&_th]:py-1! [&_.py-16]:py-6!">
        <div class="mb-2 flex min-h-7 items-center justify-between"><h2 class="font-bold">회원 관리</h2></div>
        <p v-if="members.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <template v-else>
          <PanelSearch v-model="memberList.keyword.value" v-model:status="memberList.status.value" :status-options="memberStatusOptions" label="아이디" placeholder="아이디 검색" @search="memberList.search" />
          <EmptyState v-if="!memberList.filtered.value.length" :title="memberList.applied.value ? '검색 결과가 없습니다.' : '회원이 없습니다.'" />
          <template v-else>
            <BaseTable>
              <template #head><tr><th class="px-3 py-2">아이디</th><th class="px-3 py-2">이름</th><th class="px-3 py-2">권한</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
              <tr v-for="m in memberList.pageItems.value" :key="m.id">
                <td class="px-3 py-2 font-medium">{{ m.loginId }}</td><td class="px-3 py-2">{{ m.name }}</td>
                <td class="px-3 py-2">{{ labelOf(memberRoleLabels, m.role) }}</td>
                <td class="px-3 py-2">
                  <RotatingStatusButton :status="m.status" :labels="memberStatusLabels" :rotation="MEMBER_ROTATION" :busy="pending.has(`member-${m.id}`)" :disabled="isMe(m)" @rotate="rotate('member', m)" />
                </td>
                <td class="whitespace-nowrap px-3 py-2 text-right"><BaseButton size="xs" variant="secondary" :disabled="isMe(m)" @click="openRoleForm(m)">권한</BaseButton></td>
              </tr>
            </BaseTable>
            <BasePagination v-model:page="memberList.page.value" :total-pages="memberList.totalPages.value" :max-visible="5" always compact class="mt-auto" />
          </template>
        </template>
      </section>

      <!-- Q&A 고객지원 관리 (ADMIN) -->
      <section v-if="auth.isAdmin" class="flex min-h-0 flex-col rounded-lg bg-white p-3 shadow-sm ring-1 ring-gray-200 lg:overflow-hidden [&_td]:py-1! [&_th]:py-1! [&_.py-16]:py-6!">
        <div class="mb-2 flex min-h-7 items-center justify-between"><h2 class="font-bold">Q&amp;A</h2></div>
        <p v-if="qna.failed" class="py-6 text-center text-sm text-gray-500">조회하지 못했습니다.</p>
        <template v-else>
          <PanelSearch v-model="qnaList.keyword.value" label="게시글 번호" placeholder="게시글 검색" @search="qnaList.search" />
          <EmptyState v-if="!qnaList.filtered.value.length" :title="qnaList.applied.value ? '검색 결과가 없습니다.' : '답변 대기 중인 질문이 없습니다.'" />
          <template v-else>
            <BaseTable>
              <template #head><tr><th class="px-3 py-2">번호</th><th class="px-3 py-2">제목</th><th class="px-3 py-2">상태</th><th class="px-3 py-2 text-right">작업</th></tr></template>
              <tr v-for="q in qnaList.pageItems.value" :key="q.id">
                <td class="whitespace-nowrap px-3 py-2 text-gray-500">{{ q.id }}</td><td class="px-3 py-2">{{ q.title }}</td>
                <td class="whitespace-nowrap px-3 py-2"><StatusBadge :label="labelOf(qnaStatusLabels, q.status)" :tone="statusTone[q.status]" /></td>
                <td class="whitespace-nowrap px-3 py-2 text-right"><BaseButton size="xs" variant="secondary" @click="$router.push(`/qna/${q.id}`)">답변 작성</BaseButton></td>
              </tr>
            </BaseTable>
            <BasePagination v-model:page="qnaList.page.value" :total-pages="qnaList.totalPages.value" :max-visible="5" always compact class="mt-auto" />
          </template>
        </template>
      </section>
    </div>

    <BikeFormModal v-model="bikeForm.open" :bike="bikeForm.target" @saved="onSaved($event, loadBikes)" />
    <FacilityFormModal v-model="facilityForm.open" :facility="facilityForm.target" @saved="onSaved($event, loadFacilities)" />
    <MemberRoleModal v-model="roleForm.open" :member="roleForm.target" @saved="onSaved($event, loadMembers)" />

    <p class="mt-6 text-xs text-gray-400">최근 정비 이력 요약은 전체 이력 조회 API 확정 후 추가합니다. (현재는 자전거·시설별 이력 화면에서 확인)</p>
  </div>
</template>

<script setup>
import { computed, reactive, ref } from 'vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import StatIcon from '../../components/common/StatIcon.vue'
import PanelSearch from '../../components/dashboard/PanelSearch.vue'
import RotatingStatusButton from '../../components/dashboard/RotatingStatusButton.vue'
import BikeFormModal from '../../components/dashboard/BikeFormModal.vue'
import FacilityFormModal from '../../components/dashboard/FacilityFormModal.vue'
import MemberRoleModal from '../../components/dashboard/MemberRoleModal.vue'
import { useAuthStore } from '../../stores/authStore'
import { usePanelList } from '../../composables/usePanelList'
import { bikeService } from '../../services/bikeService'
import { facilityService } from '../../services/facilityService'
import { adminMemberService } from '../../services/memberService'
import { qnaService } from '../../services/qnaService'
import { normalizeList } from '../../utils/list'
import { parseError } from '../../utils/errors'
import { formatBikeId, formatFacilityId } from '../../utils/format'
import {
  bikeStatusLabels,
  facilityStatusLabels,
  facilityTypeLabels,
  memberRoleLabels,
  memberStatusLabels,
  qnaStatusLabels,
  labelOf,
  nextIn,
  nextStatus,
  statusTone,
  toOptions,
  MEMBER_ROTATION,
} from '../../utils/statusLabels'

const PAGE_SIZE = 3 // 패널마다 3건씩 보여주고, 그 이상은 다음 페이지로 넘긴다.

const auth = useAuthStore()
const makeState = () => reactive({ loading: true, failed: false, items: [], total: 0 })
const bikes = makeState()
const facilities = makeState()
const members = makeState()
const qna = makeState()
const statusSort = reactive({ bike: false, facility: false }) // "상태" 버튼으로 상태별 정렬(GET /bike/status, /facilities/status)을 켬 상태

// 한 목록이 실패해도 나머지는 표시한다.
async function fill(state, fetcher) {
  state.failed = false
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
// 등록·수정 후 다시 불러올 수 있도록 함수로 둔다.
const loadBikes = () => fill(bikes, () => (statusSort.bike ? bikeService.listByStatus() : bikeService.list()))
const loadFacilities = () => fill(facilities, () => (statusSort.facility ? facilityService.listByStatus() : facilityService.list()))
const loadMembers = () => fill(members, () => adminMemberService.list())
loadBikes()
loadFacilities()
if (auth.isAdmin) {
  loadMembers()
  fill(qna, () => qnaService.waiting()) // GET /api/qna/admin — 답변 대기 중인 Q&A 목록
} else {
  members.loading = false
  qna.loading = false
}

const count = (state, key, value) => state.items.filter((i) => i[key] === value).length
const show = (state, v) => (state.failed ? '-' : v)

const cards = computed(() => {
  const list = []
  if (auth.isAdmin) list.push({ icon: 'person', title: '전체 회원 수', to: '/dashboard/members', loading: members.loading, value: show(members, members.total), unit: '명' })
  list.push(
    { icon: 'bike', title: '이용가능 자전거', to: '/dashboard/bikes', loading: bikes.loading, value: show(bikes, count(bikes, 'status', 'AVAILABLE')), unit: '대' },
    { icon: 'wrench', title: '정비중 자전거', to: '/dashboard/bikes', loading: bikes.loading, value: show(bikes, count(bikes, 'status', 'REPAIRING')), unit: '대' },
    { icon: 'building', title: '정상 시설', to: '/dashboard/facilities', loading: facilities.loading, value: show(facilities, count(facilities, 'facilityStatus', 'AVAILABLE')), unit: '개' },
    { icon: 'wrench', title: '정비중 시설', to: '/dashboard/facilities', loading: facilities.loading, value: show(facilities, count(facilities, 'facilityStatus', 'REPAIRING')), unit: '개' },
  )
  return list
})

// 상태를 눌러 바꿔도 행 위치가 달라지지 않도록 상태가 아닌 id 기준으로 정렬한다. (회원·Q&A 는 최신순)
const bikeRows = computed(() => (statusSort.bike ? [...bikes.items] : [...bikes.items].sort((a, b) => a.id - b.id))) // 상태별 정렬 중에는 서버 순서 그대로
const facilityRows = computed(() => (statusSort.facility ? [...facilities.items] : [...facilities.items].sort((a, b) => a.id - b.id)))
const memberRows = computed(() => [...members.items].sort((a, b) => b.id - a.id))
const qnaRows = computed(() => qna.items.filter((q) => q.status == null || q.status === 'WAITING').sort((a, b) => b.id - a.id))

// 와이어프레임의 패널별 검색(자전거 번호 / 시설 / 회원 / 게시글) + 3건씩 페이징
const bikeList = usePanelList(bikeRows, (b) => [b.id, formatBikeId(b.id), b.location], PAGE_SIZE)
const facilityList = usePanelList(facilityRows, (f) => [f.id, formatFacilityId(f.id), f.location, labelOf(facilityTypeLabels, f.facilityType)], PAGE_SIZE)
const memberList = usePanelList(memberRows, (m) => [m.id, m.loginId, m.name, m.email], PAGE_SIZE, (m) => m.status)
const qnaList = usePanelList(qnaRows, (q) => [q.id, q.title], PAGE_SIZE)

// 등록·수정·권한 변경 팝업. 저장에 성공하면 해당 목록만 다시 불러온다.
const memberStatusOptions = toOptions(memberStatusLabels).filter((o) => o.value !== 'KICKED') // 상태 검색 목록에서 강퇴 제외
const bikeForm = reactive({ open: false, target: null })
const facilityForm = reactive({ open: false, target: null })
const roleForm = reactive({ open: false, target: null })
const openBikeForm = (b) => Object.assign(bikeForm, { target: b, open: true })
const openFacilityForm = (f) => Object.assign(facilityForm, { target: f, open: true })
const openRoleForm = (m) => Object.assign(roleForm, { target: m, open: true })
const isMe = (m) => m.id === auth.currentUser?.id // 본인 계정의 권한·상태는 관리자 잠금(lock-out) 방지를 위해 바꾸지 못하게 한다.
function onSaved(message, reload) {
  flash(message)
  reload()
}

// ---- 상태 배지 클릭 → 이용가능 → 운영중지 → 정비중 순환 ----
const pending = reactive(new Set()) // 요청 중인 행 ("bike-3"): 중복 클릭 방지
const notice = ref('')
const statusError = ref('')
let noticeTimer = null
function flash(msg) {
  notice.value = msg
  clearTimeout(noticeTimer)
  noticeTimer = setTimeout(() => (notice.value = ''), 4000)
}

// "상태" 버튼: 누르면 상태별 정렬 목록(GET /api/bike/status, /api/facilities/status)을 불러오고, 다시 누르면 기본 목록으로 돌아간다.
async function toggleStatusSort(kind) {
  const isBike = kind === 'bike'
  const state = isBike ? bikes : facilities
  const service = isBike ? bikeService : facilityService
  const panel = isBike ? bikeList : facilityList
  const next = !statusSort[kind]
  statusError.value = ''
  try {
    const res = normalizeList(await (next ? service.listByStatus() : service.list()))
    state.items = res.items
    state.total = res.total
    statusSort[kind] = next
    panel.page.value = 1
  } catch (e) {
    statusError.value = `목록을 정렬하지 못했습니다. ${parseError(e).message}`
  }
}

async function rotate(kind, item) {
  const key = `${kind}-${item.id}`
  if (pending.has(key)) return
  const isBike = kind === 'bike'
  const isMember = kind === 'member'
  const next = isMember ? nextIn(MEMBER_ROTATION, item.status) : nextStatus(isBike ? item.status : item.facilityStatus)
  pending.add(key)
  statusError.value = ''
  try {
    if (isMember) await adminMemberService.updateStatus(item.id, next)
    else if (isBike) await bikeService.updateStatus(item.id, next)
    else await facilityService.updateStatus(item.id, next)
    // 서버가 성공을 응답한 뒤에만 화면 값을 바꾼다. (실패하면 이전 상태 그대로)
    if (isBike || isMember) item.status = next
    else item.facilityStatus = next
    const name = isMember ? `회원 ${item.loginId}` : isBike ? `자전거 ${formatBikeId(item.id)}` : `시설 ${formatFacilityId(item.id)}`
    const labels = isMember ? memberStatusLabels : isBike ? bikeStatusLabels : facilityStatusLabels
    flash(`${name} 상태를 '${labelOf(labels, next)}'(으)로 변경했습니다.`)
  } catch (e) {
    statusError.value = `상태를 변경하지 못했습니다. ${parseError(e).message}`
  } finally {
    pending.delete(key)
  }
}
</script>
