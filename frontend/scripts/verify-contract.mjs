import assert from 'node:assert/strict'
import { createServer } from 'vite'

// No database writes: intercept Axios requests, then exercise the explicit memory-only mock.
const server = await createServer({ server: { middlewareMode: true }, appType: 'custom', mode: 'development' })
let passed = 0
const check = (name, fn) => Promise.resolve().then(fn).then(() => { passed++; console.log(`PASS ${name}`) })
try {
  const load = p => server.ssrLoadModule(`/src/${p}`)
  const { api } = await load('services/api.js')
  const { bikeService: bike } = await load('services/bikeService.js')
  const { facilityService: facility } = await load('services/facilityService.js')
  const { memberService: member, adminMemberService: admin } = await load('services/memberService.js')
  const { qnaService: qna } = await load('services/qnaService.js')
  const { noticeService: notice } = await load('services/noticeService.js')
  const { dataRoomService: data } = await load('services/dataRoomService.js')
  const { changeOperationStatus } = await load('services/operationService.js')
  const { recordsForAsset } = await load('services/maintenanceContract.js')
  let captured, responseData = []
  api.defaults.adapter = async config => {
    captured = config
    return { data: responseData, status: 200, statusText: 'OK', headers: {}, config }
  }
  const request = async (name, call, method, url, body) => check(name, async () => {
    await call()
    assert.equal(captured.method, method)
    assert.equal(captured.url, url)
    assert.deepEqual(captured.data ? JSON.parse(captured.data) : undefined, body)
  })
  for (const [svc, base, statusKey] of [[bike, '/bike', 'status'], [facility, '/facilities', 'facilityStatus']]) {
    const key = base === '/bike' ? 'bike' : 'facility'
    await request(`${key} public list`, () => svc.memberList(), 'get', `${base}/member${key === 'bike' ? '/' : ''}`)
    await request(`${key} public detail`, () => svc.memberGet(3), 'get', `${base}/member/3`)
    await request(`${key} location search`, () => svc.memberSearch({ location: 'DAEJEON' }), 'get', `${base}/member/search`)
    assert.equal(captured.params.location, 'DAEJEON')
    await request(`${key} admin list`, () => svc.list(), 'get', base)
    await request(`${key} status order`, () => svc.listByStatus(), 'get', `${base}/status`)
    await request(`${key} status payload`, () => svc.updateStatus(3, 'UNAVAILABLE'), 'patch', `${base}/3/maintenance`, { [statusKey]: 'UNAVAILABLE' })
    await request(`${key} fault payload`, () => svc.addMaintenance(3, key === 'bike' ? 'TIRE' : 'DOOR_FAILURE'), 'post', `${base}/3/maintenance`, { faultType: key === 'bike' ? 'TIRE' : 'DOOR_FAILURE' })
    await request(`${key} completion event`, () => changeOperationStatus(svc, 3, 'REPAIRING', 'AVAILABLE'), 'patch', `${base}/3/status`)
    await request(`${key} plain status update`, () => changeOperationStatus(svc, 3, 'UNAVAILABLE', 'AVAILABLE'), 'patch', `${base}/3/maintenance`, { [statusKey]: 'AVAILABLE' })
  }
  await request('bike create enum', () => bike.create('DAEJEON'), 'post', '/bike', { location: 'DAEJEON' })
  await request('facility create DTO', () => facility.create({ location: 'CITY_HALL', facilityType: 'ELEVATOR' }), 'post', '/facilities', { location: 'CITY_HALL', facilityType: 'ELEVATOR' })
  for (const [svc, base] of [[notice, '/notices'], [data, '/datafiles']]) {
    await request(`${base} search`, () => svc.search({ type: 'TITLE', keyword: '점검' }), 'get', `${base}/search`)
    await request(`${base} write DTO`, () => svc.create({ title: '제목', content: '내용' }), 'post', base, { title: '제목', content: '내용' })
    await request(`${base} delete`, () => svc.remove(2), 'delete', `${base}/2`)
  }
  await request('Q&A mine', () => qna.mine(), 'get', '/qna/my')
  await request('Q&A create DTO', () => qna.create({ title: '질문', content: '내용' }), 'post', '/qna', { title: '질문', content: '내용' })
  await request('answer create', () => qna.answer(2, '답변'), 'post', '/qna/admin/2', { answer: '답변' })
  await request('answer update', () => qna.updateAnswer(2, '수정'), 'put', '/qna/admin/2', { answer: '수정' })
  await request('answer delete', () => qna.removeAnswer(2), 'delete', '/qna/admin/2')
  await request('withdraw without unsupported password body', () => member.withdraw(), 'post', '/members/inactive')
  await request('password DTO', () => member.changePassword({ password: 'old', newPassword: 'New123!', passwordCheck: 'New123!' }), 'put', '/members/password', { password: 'old', newPassword: 'New123!', passwordCheck: 'New123!' })
  for (const [status, endpoint] of [['ACTIVE', 'activate'], ['INACTIVE', 'inactivate'], ['KICKED', 'kick']]) {
    await request(`member ${status}`, () => admin.updateStatus(3, status), 'patch', `/admin/3/${endpoint}`)
  }
  await request('member role DTO', () => admin.updateRole(3, 'MANAGER'), 'patch', '/admin/3/role', { role: 'MANAGER' })
  await check('history never leaks another asset record', () => {
    assert.deepEqual(recordsForAsset([{ id: 1, bikeId: 3 }, { id: 2, bikeId: 4 }], 'bikeId', 3), [{ id: 1, bikeId: 3 }])
    assert.throws(() => recordsForAsset([{ id: 1 }], 'bikeId', 3), /개별 정비 이력/)
    assert.throws(() => recordsForAsset([{ id: 1, bikeId: 3 }, { id: 2 }], 'bikeId', 3), /개별 정비 이력/)
    assert.deepEqual(recordsForAsset([], 'bikeId', 3), [])
  })
  await check('empty success bodies are successful submissions', async () => {
    const { useSubmit } = await load('composables/useSubmit.js')
    const submit = useSubmit()
    for (const value of ['', undefined, null]) assert.equal(await submit.run(async () => value), true)
    assert.equal(await submit.run(async () => { throw new Error('테스트 오류') }), null)
    assert.equal(submit.error.value.message, '테스트 오류')
  })
  await check('location choices carry actual backend enums', async () => {
    const { LOCATION_OPTIONS, locationLabel } = await load('constants/locations.js')
    assert.equal(LOCATION_OPTIONS.length, 22)
    assert.deepEqual(LOCATION_OPTIONS.find(option => option.value === 'DAEJEON'), { value: 'DAEJEON', label: '대전역' })
    assert.equal(locationLabel('CITY_HALL'), '시청역')
  })
  await check('missing backend 403 page does not look like a missing user', async () => {
    const { parseError } = await load('utils/errors.js')
    assert.equal(parseError({ response: { status: 404, data: { path: '/error/403' } } }).message, '요청을 처리할 수 없습니다. 관리자에게 문의해 주세요.')
  })
  for (const kind of ['Bike', 'Facility']) {
    await check(kind + ' registration modal renders valid options without an existing asset', async () => {
      const { createSSRApp } = await import('vue')
      const { renderToString } = await import('vue/server-renderer')
      const { default: Modal } = await load('components/dashboard/' + kind + 'FormModal.vue')
      const context = {}
      await renderToString(createSSRApp(Modal, { modelValue: true }), context)
      assert.ok(context.teleports.body.includes('value="DAEJEON"'))
      assert.ok(context.teleports.body.includes('대전역'))
      assert.ok(!context.teleports.body.includes('[object Object]'))
    })
  }
  await check('dashboard status filter selects only the requested state and resets pagination', async () => {
    const { ref, nextTick } = await import('vue')
    const { usePanelList } = await load('composables/usePanelList.js')
    const rows = ref([
      { id: 1, status: 'AVAILABLE' }, { id: 2, status: 'REPAIRING' },
      { id: 3, status: 'UNAVAILABLE' }, { id: 4, status: 'AVAILABLE' },
    ])
    const panel = usePanelList(rows, row => [row.id], 1, row => row.status)
    panel.page.value = 3
    for (const state of ['AVAILABLE', 'REPAIRING', 'UNAVAILABLE']) {
      panel.status.value = state
      await nextTick()
      assert.ok(panel.filtered.value.length > 0)
      assert.ok(panel.filtered.value.every(row => row.status === state))
      assert.equal(panel.page.value, 1)
    }
    panel.status.value = ''
    await nextTick()
    assert.equal(panel.filtered.value.length, 4)
  })
  await check('asset labels and dashboard status filters include all three backend enums', async () => {
    const { bikeStatusLabels, facilityStatusLabels } = await load('utils/statusLabels.js')
    assert.deepEqual(bikeStatusLabels, { AVAILABLE: '이용가능', REPAIRING: '정비중', UNAVAILABLE: '이용불가' })
    assert.deepEqual(facilityStatusLabels, { AVAILABLE: '이용가능', REPAIRING: '점검중', UNAVAILABLE: '이용불가' })
  })
  for (const role of ['USER', 'ADMIN']) {
    await check('my page account status visibility for ' + role, async () => {
      const { createSSRApp } = await import('vue')
      const { createPinia } = await import('pinia')
      const { createRouter, createMemoryHistory } = await import('vue-router')
      const { renderToString } = await import('vue/server-renderer')
      const { useAuthStore } = await load('stores/authStore.js')
      const { default: MyPage } = await load('views/member/MyPageView.vue')
      const pinia = createPinia()
      const auth = useAuthStore(pinia)
      auth.currentUser = { id: 3, loginId: 'visibility-test', name: '테스트', role, status: 'ACTIVE' }
      const router = createRouter({ history: createMemoryHistory(), routes: [{ path: '/:pathMatch(.*)*', component: MyPage }] })
      await router.push('/mypage')
      await router.isReady()
      const html = await renderToString(createSSRApp(MyPage).use(pinia).use(router))
      assert.equal(html.includes('계정 상태'), role === 'ADMIN')
      assert.ok(html.includes('visibility-test'))
    })
  }
  const session = new Map()
  globalThis.sessionStorage = { getItem: key => session.get(key) ?? null, setItem: (key, value) => session.set(key, String(value)), removeItem: key => session.delete(key) }
  const { installMock } = await load('mocks/mockAdapter.js')
  installMock()
  await check('mock matches actual response fields and public route', async () => {
    const rows = await bike.memberList()
    assert.equal(rows.length, 8)
    assert.equal(rows.filter(row => row.status === 'AVAILABLE').length, 5)
    assert.equal(rows.find(row => row.id === 1).location, 'DAEJEON')
    assert.ok((await facility.memberList()).every(row => !('facilityNo' in row)))
    assert.ok((await qna.list()).every(row => !('memberId' in row) && !('author' in row)))
  })
  await check('ordinary user can edit own answered question, not another user question', async () => {
    await member.login({ loginId: 'user01', password: 'Test123!' })
    assert.ok((await qna.mine()).some(row => row.id === 1))
    await qna.update(1, { title: '검증용 질문 수정', content: '메모리에서만 변경' })
    assert.equal((await qna.get(1)).title, '검증용 질문 수정')
    await assert.rejects(() => qna.update(3, { title: '다른 사람 글', content: '수정 불가' }), error => error.response.status === 403)
    await assert.rejects(() => qna.answer(2, '권한 없는 답변'), error => error.response.status === 403)
  })
  await check('mock facility creation never invents a facility number', async () => {
    await member.login({ loginId: 'admin', password: 'Test123!' })
    const created = await facility.create({ location: 'DAEJEON', facilityType: 'ELEVATOR' })
    assert.ok(!('facilityNo' in created))
    assert.equal(created.location, 'DAEJEON')
  })
  await check('mock void role response and forbidden admin suspension match Controller', async () => {
    assert.equal(await admin.updateRole(3, 'MANAGER'), '')
    await assert.rejects(() => admin.updateStatus(1, 'INACTIVE'), error => error.response.status === 403)
  })
  await check('admin fault completion appends history and returns to AVAILABLE', async () => {
    await member.login({ loginId: 'admin', password: 'Test123!' })
    await bike.updateStatus(1, 'UNAVAILABLE')
    const before = (await bike.maintenanceAll()).length
    const reported = await bike.addMaintenance(1, 'TIRE')
    assert.equal(reported.status, 'UNAVAILABLE')
    assert.equal((await bike.get(1)).status, 'REPAIRING')
    const completed = await bike.completeMaintenance(1)
    assert.equal(completed.status, 'REPAIRING')
    assert.equal((await bike.get(1)).status, 'AVAILABLE')
    assert.equal((await bike.maintenanceAll()).length, before + 2)
    const history = await bike.maintenance(1)
    assert.equal(history.length, 2)
    assert.ok(history.every(row => row.bikeId === 1))
    assert.deepEqual(new Set(history.map(row => row.status)), new Set(['UNAVAILABLE', 'REPAIRING']))
  })
  await check('facility report and completion preserve both records for the correct asset', async () => {
    await facility.updateStatus(1, 'UNAVAILABLE')
    const before = (await facility.maintenanceAll()).length
    const reported = await facility.addMaintenance(1, 'DOOR_FAILURE')
    assert.equal(reported.facilityStatus, 'UNAVAILABLE')
    assert.equal((await facility.get(1)).facilityStatus, 'REPAIRING')
    const completed = await facility.completeMaintenance(1)
    assert.equal(completed.facilityStatus, 'REPAIRING')
    assert.equal((await facility.get(1)).facilityStatus, 'AVAILABLE')
    assert.equal((await facility.maintenanceAll()).length, before + 2)
    const rows = await facility.maintenance(1)
    assert.equal(rows.length, 2)
    assert.ok(rows.every(row => row.facilityId === 1))
    assert.deepEqual(new Set(rows.map(row => row.facilityStatus)), new Set(['UNAVAILABLE', 'REPAIRING']))
  })
  await check('ordinary availability toggle does not invent a maintenance record', async () => {
    const before = (await bike.maintenanceAll()).length
    await bike.updateStatus(1, 'UNAVAILABLE')
    await bike.updateStatus(1, 'AVAILABLE')
    assert.equal((await bike.maintenanceAll()).length, before)
  })
  console.log(`\n${passed} contract checks passed. No real database writes were made.`)
} finally {
  await server.close()
  delete globalThis.sessionStorage
}
