import { AxiosError } from 'axios'
import { api } from '../services/api'
import { createSeed } from './mockData'
import { locationLabels } from '../constants/locations'

// axios 어댑터를 교체해 실제 네트워크 대신 메모리 데이터를 응답한다. (npm run dev:mock 에서만 사용)
// 서비스/화면 코드는 그대로이며, 실제 모드에는 이 파일이 로드되지 않는다.
// 현재 Controller/DTO/서비스를 따르는 UI 전용 모드. 실제 서버의 인증 검증은 별도 수행한다.

const db = createSeed()
const locationValues = Object.fromEntries(Object.entries(locationLabels).map(([value, label]) => [label, value]))
for (const asset of [...db.bikes, ...db.facilities]) {
  asset.location = locationValues[asset.location] ?? asset.location
  delete asset.facilityNo
}
const SESSION_KEY = 'mock-session-member-id'

class HttpError extends Error {
  constructor(status, message, extra) {
    super(message)
    this.status = status
    this.extra = extra
  }
}
const fail = (status, message, extra) => {
  throw new HttpError(status, message, extra)
}

const sleep = (ms) => new Promise((r) => setTimeout(r, ms))
const now = () => new Date().toISOString()
const getSessionId = () => Number(sessionStorage.getItem(SESSION_KEY)) || null
const currentMember = () => db.members.find((m) => m.id === getSessionId()) || null

const memberDto = ({ password, ...m }) => m // 비밀번호는 응답에 포함하지 않는다
const requireLogin = () => currentMember() || fail(401, '로그인이 필요합니다.')
const requireRole = (...roles) => {
  const me = requireLogin()
  if (!roles.includes(me.role)) fail(403, '접근 권한이 없습니다.')
  return me
}
const byId = (list, id) => list.find((x) => x.id === Number(id)) || fail(404, '대상을 찾을 수 없습니다.')
const need = (cond, message, field) => {
  if (!cond) fail(400, message, field ? { errors: { [field]: message } } : undefined)
}
const isPassword = (v) => typeof v === 'string' && v.length >= 4 && v.length <= 30 && /^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[!@#$%^&*])[A-Za-z0-9!@#$%^&*]+$/.test(v)
const newestFirst = (list) => [...list].sort((a, b) => b.id - a.id)
const keywordOf = (query) => String(query.keyword ?? query.q ?? query.title ?? query.location ?? '').trim().toLowerCase()

// 두 정비 이벤트의 처리 결과 → 처리 직전 상태. 실제 Response DTO와 같은 계약이다.
const stateBeforeMaintenance = result => result === 'REPAIRING' ? 'UNAVAILABLE' : result === 'AVAILABLE' ? 'REPAIRING' : result
const bikeMtDto = (r) => ({ id: r.id, bikeId: r.bikeId, createdAt: r.createdAt, status: stateBeforeMaintenance(r.status), faultType: r.faultType, memberLoginId: r.memberLoginId })
const facilityMtDto = (r) => ({ id: r.id, facilityId: r.facilityId, createdAt: r.createdAt, facilityStatus: stateBeforeMaintenance(r.facilityStatus), faultType: r.faultType, memberLoginId: r.memberLoginId })
const qnaDto = ({ memberId, ...q }) => q

// 게시판(공지/자료실) 공통 CRUD + 검색
function boardRoutes(prefix, key, seqKey) {
  return [
    ['get', new RegExp(`^/${prefix}$`), () => newestFirst(db[key])],
    ['get', new RegExp(`^/${prefix}/search$`), ({ query }) => {
      const kw = keywordOf(query)
      return newestFirst(db[key]).filter((r) => {
        const value = query.type === 'TITLE' ? r.title : query.type === 'CONTENT' ? r.content : `${r.title} ${r.content}`
        return !kw || value.toLowerCase().includes(kw)
      })
    }],
    ['get', new RegExp(`^/${prefix}/(\\d+)$`), ({ p }) => byId(db[key], p[0])],
    ['post', new RegExp(`^/${prefix}$`), ({ body }) => {
      const me = requireRole('ADMIN')
      need(body.title?.trim(), '제목은 필수입니다.', 'title')
      need(body.content?.trim(), '내용은 필수입니다.', 'content')
      const row = { id: db.seq[seqKey]++, title: body.title, content: body.content, author: me.loginId, createdAt: now(), updatedAt: now() }
      db[key].unshift(row)
      return row
    }],
    ['put', new RegExp(`^/${prefix}/(\\d+)$`), ({ p, body }) => {
      requireRole('ADMIN')
      const row = byId(db[key], p[0])
      need(body.title?.trim(), '제목은 필수입니다.', 'title')
      need(body.content?.trim(), '내용은 필수입니다.', 'content')
      Object.assign(row, { title: body.title, content: body.content, updatedAt: now() })
      return row
    }],
    ['delete', new RegExp(`^/${prefix}/(\\d+)$`), ({ p }) => {
      requireRole('ADMIN')
      const row = byId(db[key], p[0])
      db[key].splice(db[key].indexOf(row), 1)
      return null
    }],
  ]
}

const BIKE_STATUSES = ['AVAILABLE', 'UNAVAILABLE', 'REPAIRING']
const statusRank = { UNAVAILABLE: 0, REPAIRING: 1, AVAILABLE: 2 }

const routes = [
  ...['bike', 'facilities'].map(kind => [
    'patch', new RegExp('^/' + kind + '/(\\d+)/status$'), ({ p }) => {
      const me = requireRole('MANAGER', 'ADMIN')
      const isBike = kind === 'bike'
      const asset = byId(isBike ? db.bikes : db.facilities, p[0])
      const state = isBike ? 'status' : 'facilityStatus'
      const key = isBike ? 'bikeId' : 'facilityId'
      const records = isBike ? db.bikeMaintenance : db.facilityMaintenance
      need(asset[state] === 'REPAIRING', '정비중 상태에서만 완료할 수 있습니다.')
      const previous = [...records].reverse().find(row => row[key] === asset.id && row[state] === 'REPAIRING')
      need(previous, '고장 접수 이력이 필요합니다.')
      const record = { id: db.seq[isBike ? 'bikeMt' : 'facilityMt']++, [key]: asset.id, [state]: 'AVAILABLE', faultType: previous.faultType, memberLoginId: me.loginId, createdAt: now() }
      records.push(record)
      asset[state] = 'AVAILABLE'
      return isBike ? bikeMtDto(record) : facilityMtDto(record)
    },
  ]),

  // ---- /api/members ----
  ['post', /^\/members\/signup$/, ({ body }) => {
    need(body.loginId?.length >= 4 && body.loginId.length <= 20, '최소 4자에서 최대 20자까지 입력해야 합니다.', 'loginId')
    need(isPassword(body.password), '4자~30자, 영문과 숫자, 1개 이상의 특수문자를 사용해야 합니다.', 'password')
    need(body.password === body.passwordCheck, '비밀번호가 일치하지 않습니다.', 'passwordCheck')
    need(body.name?.trim(), '이름은 필수입니다.', 'name')
    need(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(body.email || ''), '올바른 이메일 형식이 아닙니다.', 'email')
    need(body.termsAgreed === true, '이용약관에 동의해 주세요.', 'termsAgreed')
    need(body.privacyAgreed === true, '개인정보 수집 및 이용에 동의해 주세요.', 'privacyAgreed')
    if (db.members.some((m) => m.loginId === body.loginId)) fail(400, '이미 사용 중인 아이디입니다.')
    if (db.members.some((m) => m.email === body.email)) fail(400, '이미 사용 중인 이메일입니다.')
    const m = { id: db.seq.member++, loginId: body.loginId, password: body.password, name: body.name, email: body.email, tel: body.tel || null, role: 'USER', status: 'ACTIVE', createdAt: now() }
    db.members.push(m)
    return memberDto(m)
  }],
  ['post', /^\/members\/login$/, ({ body }) => {
    const m = db.members.find((x) => x.loginId === body.loginId)
    if (!m || m.password !== body.password) fail(401, '아이디 또는 비밀번호가 올바르지 않습니다.')
    if (m.status !== 'ACTIVE') fail(403, '이용이 제한된 계정입니다.')
    sessionStorage.setItem(SESSION_KEY, String(m.id))
    return memberDto(m)
  }],
  ['post', /^\/members\/logout$/, () => {
    sessionStorage.removeItem(SESSION_KEY)
    return null
  }],
  ['get', /^\/members\/me$/, () => memberDto(requireLogin())],
  ['put', /^\/members\/me$/, ({ body }) => {
    const me = requireLogin()
    need(body.name?.trim(), '이름은 필수입니다.', 'name')
    need(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(body.email || ''), '올바른 이메일 형식이 아닙니다.', 'email')
    Object.assign(me, { name: body.name, email: body.email, tel: body.tel ?? null })
    return memberDto(me)
  }],
  ['put', /^\/members\/password$/, ({ body }) => {
    const me = requireLogin()
    if (me.password !== body.password) fail(400, '현재 비밀번호가 올바르지 않습니다.', { errors: { password: '현재 비밀번호가 올바르지 않습니다.' } })
    need(isPassword(body.newPassword), '4자~30자, 영문과 숫자, 1개 이상의 특수문자를 사용해야 합니다.', 'newPassword')
    need(body.newPassword === body.passwordCheck, '새 비밀번호가 일치하지 않습니다.', 'passwordCheck')
    me.password = body.newPassword
    return null
  }],
  ['post', /^\/members\/inactive$/, () => {
    const me = requireLogin()
    me.status = 'INACTIVE' // 소프트 삭제
    sessionStorage.removeItem(SESSION_KEY)
    return null
  }],

  // ---- /api/admin (회원 관리) ----
  ['get', /^\/admin$/, () => (requireRole('ADMIN'), db.members.map(memberDto))],
  ['get', /^\/admin\/login-id\/([^/]+)$/, ({ p }) => {
    requireRole('ADMIN')
    const m = db.members.find((x) => x.loginId === decodeURIComponent(p[0])) || fail(404, '대상을 찾을 수 없습니다.')
    return memberDto(m)
  }],
  ['get', /^\/admin\/(\d+)$/, ({ p }) => (requireRole('ADMIN'), memberDto(byId(db.members, p[0])))],
  ['put', /^\/admin\/(\d+)$/, ({ p, body }) => {
    requireRole('ADMIN')
    const m = byId(db.members, p[0])
    need(body.name?.trim(), '이름은 필수입니다.', 'name')
    need(/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(body.email || ''), '올바른 이메일 형식이 아닙니다.', 'email')
    Object.assign(m, { name: body.name, email: body.email, tel: body.tel ?? m.tel })
    return memberDto(m)
  }],
  ['patch', /^\/admin\/(\d+)\/role$/, ({ p, body }) => {
    requireRole('ADMIN')
    need(['USER', 'MANAGER', 'ADMIN'].includes(body.role), '올바르지 않은 권한입니다.')
    const m = byId(db.members, p[0])
    m.role = body.role
    return null
  }],
  ...[
    ['activate', 'ACTIVE'],
    ['inactivate', 'INACTIVE'],
    ['kick', 'KICKED'],
  ].map(([path, status]) => [
    'patch',
    new RegExp(`^/admin/(\\d+)/${path}$`),
    ({ p }) => {
      requireRole('ADMIN')
      const m = byId(db.members, p[0])
      if (m.role === 'ADMIN' && status !== 'ACTIVE') fail(403, '관리자 계정은 정지 또는 강퇴할 수 없습니다.')
      m.status = status
      return null
    },
  ]),

  // ---- /api/bike ---- (사용자용 /member 경로는 관리자용보다 먼저 선언)
  ['get', /^\/bike\/member\/$/, () => newestFirst(db.bikes)],
  ['get', /^\/bike\/member\/search$/, ({ query }) => {
    const kw = keywordOf(query)
    return newestFirst(db.bikes).filter((b) => !kw || b.location.toLowerCase().includes(kw))
  }],
  ['get', /^\/bike\/member\/(\d+)$/, ({ p }) => byId(db.bikes, p[0])],
  ['get', /^\/bike$/, () => (requireRole('MANAGER', 'ADMIN'), newestFirst(db.bikes))],
  ['get', /^\/bike\/status$/, () => (requireRole('MANAGER', 'ADMIN'), [...db.bikes].sort((a, b) => statusRank[a.status] - statusRank[b.status] || b.id - a.id))],
  ['get', /^\/bike\/maintenance$/, () => (requireRole('MANAGER', 'ADMIN'), [...db.bikeMaintenance].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)).map(bikeMtDto))],
  ['get', /^\/bike\/(\d+)$/, ({ p }) => (requireRole('MANAGER', 'ADMIN'), byId(db.bikes, p[0]))],
  ['post', /^\/bike$/, ({ body }) => {
    requireRole('MANAGER', 'ADMIN')
    need(body.location?.trim(), '위치는 필수입니다.', 'location')
    const b = { id: db.seq.bike++, location: body.location, status: 'AVAILABLE', distance: 0 }
    db.bikes.push(b)
    return b
  }],
  ['put', /^\/bike\/(\d+)$/, ({ p, body }) => {
    requireRole('MANAGER', 'ADMIN')
    need(body.location?.trim(), '위치는 필수입니다.', 'location')
    const b = byId(db.bikes, p[0])
    b.location = body.location
    return b
  }],
  ['delete', /^\/bike\/(\d+)$/, ({ p }) => {
    requireRole('MANAGER', 'ADMIN')
    const b = byId(db.bikes, p[0])
    db.bikes.splice(db.bikes.indexOf(b), 1)
    db.bikeMaintenance = db.bikeMaintenance.filter((r) => r.bikeId !== b.id)
    return null
  }],
  ['post', /^\/bike\/(\d+)\/maintenance$/, ({ p, body }) => {
    const me = requireRole('MANAGER', 'ADMIN')
    const b = byId(db.bikes, p[0])
    need(['BRAKE', 'TIRE', 'CHAIN', 'HANDEL', 'SADDLE', 'LOCK'].includes(body.faultType), '올바르지 않은 고장 유형입니다.')
    // 현재 서비스 규칙: 운영중지에서 접수 시 REPAIRING 이력을 INSERT 한다.
    need(b.status === 'UNAVAILABLE', '운영중지 상태에서만 고장을 접수할 수 있습니다.')
    b.status = 'REPAIRING'
    const row = { id: db.seq.bikeMt++, bikeId: b.id, createdAt: now(), status: 'REPAIRING', faultType: body.faultType, memberLoginId: me.loginId }
    db.bikeMaintenance.push(row)
    return bikeMtDto(row)
  }],
  ['patch', /^\/bike\/(\d+)\/maintenance$/, ({ p, body }) => {
    requireRole('MANAGER', 'ADMIN')
    need(BIKE_STATUSES.includes(body.status), '올바르지 않은 상태입니다.')
    const b = byId(db.bikes, p[0])
    b.status = body.status
    return b
  }],

  // ---- /api/facilities ----
  ['get', /^\/facilities\/member$/, () => newestFirst(db.facilities)],
  ['get', /^\/facilities\/member\/search$/, ({ query }) => {
    const kw = keywordOf(query)
    return newestFirst(db.facilities).filter((f) => !kw || f.location.toLowerCase().includes(kw))
  }],
  ['get', /^\/facilities\/member\/(\d+)$/, ({ p }) => byId(db.facilities, p[0])],
  ['get', /^\/facilities$/, () => (requireRole('MANAGER', 'ADMIN'), newestFirst(db.facilities))],
  ['get', /^\/facilities\/status$/, () => (requireRole('MANAGER', 'ADMIN'), [...db.facilities].sort((a, b) => statusRank[a.facilityStatus] - statusRank[b.facilityStatus] || b.id - a.id))],
  ['get', /^\/facilities\/search$/, ({ query }) => {
    requireRole('MANAGER', 'ADMIN')
    const kw = keywordOf(query)
    return newestFirst(db.facilities).filter((f) => !kw || f.location.toLowerCase().includes(kw))
  }],
  ['get', /^\/facilities\/maintenance$/, () => (requireRole('MANAGER', 'ADMIN'), [...db.facilityMaintenance].sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt)).map(facilityMtDto))],
  ['get', /^\/facilities\/(\d+)$/, ({ p }) => (requireRole('MANAGER', 'ADMIN'), byId(db.facilities, p[0]))],
  ['post', /^\/facilities$/, ({ body }) => {
    requireRole('MANAGER', 'ADMIN')
    need(body.location?.trim(), '위치는 필수입니다.', 'location')
    need(['BIKE_STATION', 'BIKE_RACK', 'ELEVATOR', 'SCREEN_DOOR'].includes(body.facilityType), '시설 유형은 필수입니다.', 'facilityType')
    const id = db.seq.facility++
    const f = { id, facilityType: body.facilityType, location: body.location, facilityStatus: 'AVAILABLE' }
    db.facilities.push(f)
    return f
  }],
  ['put', /^\/facilities\/(\d+)$/, ({ p, body }) => {
    requireRole('MANAGER', 'ADMIN')
    const f = byId(db.facilities, p[0])
    need(body.location?.trim(), '위치는 필수입니다.', 'location')
    Object.assign(f, { location: body.location, facilityType: body.facilityType || f.facilityType })
    return f
  }],
  ['delete', /^\/facilities\/(\d+)$/, ({ p }) => {
    requireRole('MANAGER', 'ADMIN')
    const f = byId(db.facilities, p[0])
    db.facilities.splice(db.facilities.indexOf(f), 1)
    db.facilityMaintenance = db.facilityMaintenance.filter((r) => r.facilityId !== f.id)
    return null
  }],
  ['post', /^\/facilities\/(\d+)\/maintenance$/, ({ p, body }) => {
    const me = requireRole('MANAGER', 'ADMIN')
    const f = byId(db.facilities, p[0])
    need(['OPERATION_FAILURE', 'DOOR_FAILURE', 'CONTROL_FAILURE', 'DISPLAY_FAILURE', 'STRUCTURE_DAMAGE', 'NOISE_VIBRATION'].includes(body.faultType), '올바르지 않은 고장 유형입니다.')
    need(f.facilityStatus === 'UNAVAILABLE', '운영중지 상태에서만 고장을 접수할 수 있습니다.')
    f.facilityStatus = 'REPAIRING'
    const row = { id: db.seq.facilityMt++, facilityId: f.id, createdAt: now(), facilityStatus: 'REPAIRING', faultType: body.faultType, memberLoginId: me.loginId }
    db.facilityMaintenance.push(row)
    return facilityMtDto(row)
  }],
  ['patch', /^\/facilities\/(\d+)\/maintenance$/, ({ p, body }) => {
    requireRole('MANAGER', 'ADMIN')
    need(BIKE_STATUSES.includes(body.facilityStatus), '올바르지 않은 상태입니다.')
    const f = byId(db.facilities, p[0])
    f.facilityStatus = body.facilityStatus
    return f
  }],

  // ---- 게시판 ----
  ...boardRoutes('notices', 'notices', 'notice'),
  ...boardRoutes('datafiles', 'dataFiles', 'dataFile'),

  // ---- /api/qna ---- (/my, /admin 은 /{qnaId} 보다 먼저 선언)
  ['get', /^\/qna$/, () => newestFirst(db.qna).map(qnaDto)],
  ['get', /^\/qna\/my$/, () => {
    const me = requireLogin()
    return newestFirst(db.qna.filter((q) => q.memberId === me.id)).map(qnaDto)
  }],
  ['get', /^\/qna\/admin$/, () => {
    requireRole('ADMIN')
    return newestFirst(db.qna.filter((q) => q.status === 'WAITING')).map(qnaDto)
  }],
  ['get', /^\/qna\/(\d+)$/, ({ p }) => {
    const q = byId(db.qna, p[0])
    return qnaDto(q)
  }],
  ['post', /^\/qna$/, ({ body }) => {
    const me = requireLogin()
    need(body.title?.trim(), '제목은 필수입니다.', 'title')
    need(body.content?.trim(), '내용은 필수입니다.', 'content')
    const q = { id: db.seq.qna++, memberId: me.id, title: body.title, content: body.content, answer: null, status: 'WAITING', hits: 0, createdAt: now(), updatedAt: now(), answeredAt: null }
    db.qna.unshift(q)
    return qnaDto(q)
  }],
  ['put', /^\/qna\/(\d+)$/, ({ p, body }) => {
    const me = requireLogin()
    const q = byId(db.qna, p[0])
    if (q.memberId !== me.id && me.role !== 'ADMIN') fail(403, '본인의 질문만 수정할 수 있습니다.')
    need(body.title?.trim(), '제목은 필수입니다.', 'title')
    need(body.content?.trim(), '내용은 필수입니다.', 'content')
    Object.assign(q, { title: body.title, content: body.content, updatedAt: now() })
    return qnaDto(q)
  }],
  ['delete', /^\/qna\/(\d+)$/, ({ p }) => {
    const me = requireLogin()
    const q = byId(db.qna, p[0])
    if (q.memberId !== me.id && me.role !== 'ADMIN') fail(403, '본인의 질문만 삭제할 수 있습니다.')
    db.qna.splice(db.qna.indexOf(q), 1)
    return null
  }],
  ['post', /^\/qna\/admin\/(\d+)$/, ({ p, body }) => {
    need(byId(db.qna, p[0]).status === 'WAITING', '이미 등록된 답변이 존재합니다.')
    return answerQna(p[0], body)
  }],
  ['put', /^\/qna\/admin\/(\d+)$/, ({ p, body }) => {
    need(byId(db.qna, p[0]).status === 'ANSWERED', '등록된 답변이 없습니다.')
    return answerQna(p[0], body)
  }],
  ['delete', /^\/qna\/admin\/(\d+)$/, ({ p }) => {
    requireRole('ADMIN')
    const q = byId(db.qna, p[0])
    Object.assign(q, { answer: null, status: 'WAITING', answeredAt: null, updatedAt: now() })
    return null
  }],
]

function answerQna(id, body) {
  requireRole('ADMIN')
  const q = byId(db.qna, id)
  need(body.answer?.trim(), '답변 내용은 필수입니다.', 'answer')
  Object.assign(q, { answer: body.answer, status: 'ANSWERED', answeredAt: now(), updatedAt: now() })
  return qnaDto(q)
}

function parseBody(data) {
  if (!data) return {}
  if (typeof data === 'string') {
    try {
      return JSON.parse(data)
    } catch {
      return {}
    }
  }
  return data
}

async function mockAdapter(config) {
  await sleep(150)
  const method = (config.method || 'get').toLowerCase()
  const path = (config.url || '').split('?')[0].replace(/^\/api/, '')
  const body = parseBody(config.data)

  const response = (status, data) => ({ data, status, statusText: String(status), headers: {}, config, request: {} })
  const reject = (status, data) => {
    const res = response(status, data)
    throw new AxiosError(data.message, status >= 500 ? 'ERR_BAD_RESPONSE' : 'ERR_BAD_REQUEST', config, {}, res)
  }

  // UI 확인용 메모리 어댑터이며 Spring Security/CSRF 검증을 대신하지 않는다.

  for (const [m, re, handler] of routes) {
    if (m !== method) continue
    const match = path.match(re)
    if (!match) continue
    try {
      const data = handler({ p: match.slice(1), body, query: config.params || {} })
      return response(data === null ? 204 : 200, data === null ? '' : structuredClone(data))
    } catch (e) {
      if (e instanceof HttpError) reject(e.status, { message: e.message, ...(e.extra || {}) })
      throw e
    }
  }
  return reject(404, { message: '대상을 찾을 수 없습니다.' })
}

export function installMock() {
  api.defaults.adapter = mockAdapter
  console.info('[MOCK] 실제 API 대신 Mock 어댑터를 사용합니다.')
}
