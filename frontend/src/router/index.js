import { createRouter, createWebHistory } from 'vue-router'
import { useAuthStore } from '../stores/authStore'
import { setUnauthorizedHandler } from '../services/api'

const OPS = ['MANAGER', 'ADMIN']
const ADMIN = ['ADMIN']

// 아래 URL 은 PRD 5장의 "제안" 경로이다. :id 는 DB 기본키(숫자)이며 bikeNo 는 사용하지 않는다.
const routes = [
  { path: '/', name: 'home', component: () => import('../views/home/HomeView.vue') },
  { path: '/login', name: 'login', component: () => import('../views/auth/LoginView.vue'), meta: { guestOnly: true } },
  { path: '/signup', name: 'signup', component: () => import('../views/auth/SignupView.vue'), meta: { guestOnly: true } },

  { path: '/mypage', name: 'mypage', component: () => import('../views/member/MyPageView.vue'), meta: { auth: true } },
  { path: '/mypage/edit', name: 'mypage-edit', component: () => import('../views/member/MyPageEditView.vue'), meta: { auth: true } },
  { path: '/mypage/password', name: 'mypage-password', component: () => import('../views/member/MyPasswordView.vue'), meta: { auth: true } },
  { path: '/mypage/posts', name: 'mypage-posts', component: () => import('../views/member/MyPostsView.vue'), meta: { auth: true } },

  { path: '/notices', name: 'notices', component: () => import('../views/notice/NoticeListView.vue') },
  { path: '/notices/new', name: 'notice-new', component: () => import('../views/notice/NoticeFormView.vue'), meta: { auth: true, roles: ADMIN } },
  { path: '/notices/:id(\\d+)', name: 'notice-detail', component: () => import('../views/notice/NoticeDetailView.vue'), props: true },
  { path: '/notices/:id(\\d+)/edit', name: 'notice-edit', component: () => import('../views/notice/NoticeFormView.vue'), props: true, meta: { auth: true, roles: ADMIN } },

  { path: '/data-room', name: 'data-room', component: () => import('../views/data-room/DataRoomListView.vue') },
  { path: '/data-room/new', name: 'data-room-new', component: () => import('../views/data-room/DataRoomFormView.vue'), meta: { auth: true, roles: ADMIN } },
  { path: '/data-room/:id(\\d+)', name: 'data-room-detail', component: () => import('../views/data-room/DataRoomDetailView.vue'), props: true },
  { path: '/data-room/:id(\\d+)/edit', name: 'data-room-edit', component: () => import('../views/data-room/DataRoomFormView.vue'), props: true, meta: { auth: true, roles: ADMIN } },

  // TODO: Q&A 목록/상세 공개 범위가 확정되면 meta.auth 를 조정한다. (현재는 작성만 로그인 필요)
  { path: '/qna', name: 'qna', component: () => import('../views/qna/QnaListView.vue') },
  { path: '/qna/new', name: 'qna-new', component: () => import('../views/qna/QnaFormView.vue'), meta: { auth: true } },
  { path: '/qna/:id(\\d+)', name: 'qna-detail', component: () => import('../views/qna/QnaDetailView.vue'), props: true },
  { path: '/qna/:id(\\d+)/edit', name: 'qna-edit', component: () => import('../views/qna/QnaFormView.vue'), props: true, meta: { auth: true } },

  { path: '/bikes', name: 'bikes', component: () => import('../views/bike/BikeListView.vue') },
  { path: '/bikes/:id(\\d+)', name: 'bike-detail', component: () => import('../views/bike/BikeDetailView.vue'), props: true },
  { path: '/facilities', name: 'facilities', component: () => import('../views/facility/FacilityListView.vue') },
  { path: '/facilities/:id(\\d+)', name: 'facility-detail', component: () => import('../views/facility/FacilityDetailView.vue'), props: true },

  { path: '/dashboard', name: 'dashboard', component: () => import('../views/dashboard/DashboardHomeView.vue'), meta: { auth: true, roles: OPS, layout: 'dashboard' } },
  { path: '/dashboard/members', name: 'dashboard-members', component: () => import('../views/dashboard/MemberManagementView.vue'), meta: { auth: true, roles: ADMIN, layout: 'dashboard' } },
  { path: '/dashboard/bikes', name: 'dashboard-bikes', component: () => import('../views/dashboard/BikeManagementView.vue'), meta: { auth: true, roles: OPS, layout: 'dashboard' } },
  { path: '/dashboard/bikes/:id(\\d+)/maintenance', name: 'dashboard-bike-maintenance', component: () => import('../views/dashboard/BikeMaintenanceView.vue'), props: true, meta: { auth: true, roles: OPS, layout: 'dashboard' } },
  { path: '/dashboard/facilities', name: 'dashboard-facilities', component: () => import('../views/dashboard/FacilityManagementView.vue'), meta: { auth: true, roles: OPS, layout: 'dashboard' } },
  { path: '/dashboard/facilities/:id(\\d+)/maintenance', name: 'dashboard-facility-maintenance', component: () => import('../views/dashboard/FacilityMaintenanceView.vue'), props: true, meta: { auth: true, roles: OPS, layout: 'dashboard' } },
  { path: '/dashboard/qna', name: 'dashboard-qna', component: () => import('../views/dashboard/QnaManagementView.vue'), meta: { auth: true, roles: ADMIN, layout: 'dashboard' } },

  { path: '/forbidden', name: 'forbidden', component: () => import('../views/ForbiddenView.vue') },
  { path: '/:pathMatch(.*)*', name: 'not-found', component: () => import('../views/NotFoundView.vue') },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ top: 0 }),
})

// 화면 가드는 UX 용이다. 최종 권한 검증은 반드시 서버가 다시 수행한다.
router.beforeEach(async (to) => {
  const auth = useAuthStore()
  await auth.initializeSession()

  if (to.meta.guestOnly && auth.isAuthenticated) return { name: 'home' }
  if (to.meta.auth && !auth.isAuthenticated) {
    return { name: 'login', query: { redirect: to.fullPath } }
  }
  if (to.meta.roles && !auth.hasRole(to.meta.roles)) return { name: 'forbidden' }
})

// 요청 도중 401(세션 만료) 발생 시 로그인으로 보낸다.
setUnauthorizedHandler(() => {
  const auth = useAuthStore()
  const wasLoggedIn = auth.isAuthenticated
  auth.clearSession()
  const current = router.currentRoute.value
  if (current.name !== 'login' && (wasLoggedIn || current.meta.auth)) {
    router.push({ name: 'login', query: { redirect: current.fullPath, expired: '1' } })
  }
})

export default router
