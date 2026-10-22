import assert from 'node:assert/strict'
import { readFile, writeFile, unlink, access } from 'node:fs/promises'
import path from 'node:path'
import { fileURLToPath, pathToFileURL } from 'node:url'
import { parse, compileScript } from '@vue/compiler-sfc'
import { createRenderer, defineComponent, h, reactive, nextTick } from 'vue'
import { createServer } from 'vite'

// Exercise the actual modal template and handlers with inert visual primitives.
// Every HTTP request uses an in-memory Axios adapter. No database writes occur.
const server = await createServer({ server: { middlewareMode: true }, appType: 'custom', mode: 'development' })
const rootDir = fileURLToPath(new URL('../', import.meta.url))
const tempFiles = []
globalThis.__dashboardControlDeps = {}
let passed = 0
const pass = name => { passed++; console.log(`PASS ${name}`) }
const stubs = {
  BaseModal: defineComponent({ props: ['modelValue', 'title', 'persistent'], setup: (p, { slots }) => () => p.modelValue ? h('section', [h('h2', p.title), slots.default?.(), slots.footer?.()]) : null }),
  BaseSelect: defineComponent({ props: ['modelValue', 'label', 'options', 'error'], setup: p => () => h('label', p.label) }),
  BaseButton: defineComponent({ inheritAttrs: false, props: ['disabled', 'loading', 'variant'], setup: (p, { attrs, slots }) => () => h('button', { ...attrs, disabled: !!(p.disabled || p.loading) }, slots.default?.()) }),
}
const node = (tag, text = '') => ({ tag, text, props: {}, children: [], parent: null })
const renderer = createRenderer({
  createElement: node, createText: text => node('#text', text), createComment: text => node('#comment', text),
  setText: (n, text) => { n.text = text }, setElementText: (n, text) => { n.text = text; n.children = [] },
  patchProp: (n, key, _old, value) => { n.props[key] = value },
  insert(n, parent, anchor = null) {
    if (n.parent) n.parent.children.splice(n.parent.children.indexOf(n), 1)
    const at = anchor ? parent.children.indexOf(anchor) : -1
    parent.children.splice(at < 0 ? parent.children.length : at, 0, n); n.parent = parent
  },
  remove(n) { n.parent.children.splice(n.parent.children.indexOf(n), 1); n.parent = null },
  parentNode: n => n.parent, nextSibling: n => n.parent?.children[n.parent.children.indexOf(n) + 1] || null,
})
const all = n => [n, ...n.children.flatMap(all)]
const content = n => n.text + n.children.map(content).join('')

async function loadModal(kind) {
  const file = path.join(rootDir, `src/components/dashboard/${kind}FormModal.vue`)
  const { descriptor } = parse(await readFile(file, 'utf8'))
  let code = compileScript(descriptor, { id: `delete-${kind}`, inlineTemplate: true }).content
  const deps = globalThis.__dashboardControlDeps[kind] = {}
  for (const m of [...code.matchAll(/import\s+([\s\S]*?)\s+from\s+['"]([^'"]+)['"]/g)]) {
    const [, bindings, spec] = m
    if (spec === 'vue') continue
    const key = String(Object.keys(deps).length)
    const stub = stubs[path.basename(spec, '.vue')]
    deps[key] = stub ? { default: stub } : await server.ssrLoadModule(path.resolve(path.dirname(file), spec))
    const declaration = bindings.trim().startsWith('{') ? bindings.replace(/\bas\b/g, ':') : `{ default: ${bindings} }`
    code = code.replace(m[0], `const ${declaration} = globalThis.__dashboardControlDeps.${kind}[${JSON.stringify(key)}]`)
  }
  const temp = new URL(`./.dashboard-modal-${kind}-${process.pid}.mjs`, import.meta.url)
  tempFiles.push(temp); await writeFile(temp, code)
  return (await import(temp.href)).default
}

try {
  const { api } = await server.ssrLoadModule('/src/services/api.js')
  for (const [kind, prop, endpoint, label] of [['Bike', 'bike', '/bike', '자전거'], ['Facility', 'facility', '/facilities', '시설']]) {
    const Component = await loadModal(kind)
    const state = reactive({ modelValue: false, [prop]: null })
    const events = []
    const root = node('root')
    const app = renderer.createApp({ render: () => h(Component, { ...state,
      'onUpdate:modelValue': open => { events.push(['open', open]); state.modelValue = open },
      onSaved: message => events.push(['saved', message]),
    }) })
    app.mount(root)
    let requests = [], fail = false, release
    api.defaults.adapter = async config => {
      requests.push(config)
      if (fail) throw new Error('삭제 실패 검증')
      await new Promise(resolve => { release = resolve })
      return { data: '', status: 204, statusText: 'No Content', headers: {}, config }
    }
    const button = label => all(root).find(n => n.tag === 'button' && content(n) === label)
    const click = async label => {
      const b = button(label); assert.ok(b, label); assert.equal(b.props.disabled, false)
      b.props.onClick(); await nextTick()
    }
    state.modelValue = true; await nextTick()
    assert.equal(button('삭제'), undefined)
    pass(`${kind}: registration has no delete button`)
    state.modelValue = false; await nextTick()
    state[prop] = { id: 10001, location: '', facilityType: 'ELEVATOR' }
    state.modelValue = true; await nextTick()
    await click('삭제')
    assert.ok(content(root).includes('정비 이력도 함께 삭제됩니다.'))
    assert.equal(requests.length, 0)
    await click('취소')
    assert.ok(button('수정')); assert.equal(requests.length, 0)
    pass(`${kind}: confirmation and cancellation never send DELETE`)
    await click('삭제'); fail = true
    await button('삭제').props.onClick(); await nextTick()
    assert.ok(content(root).includes('삭제 실패 검증'))
    assert.equal(state.modelValue, true)
    assert.equal(events.filter(e => e[0] === 'saved').length, 0)
    pass(`${kind}: failed deletion keeps dialog open with error`)
    await click('취소')
    assert.ok(!content(root).includes('삭제 실패 검증'))
    await click('삭제'); fail = false
    const promise = button('삭제').props.onClick()
    await nextTick(); await new Promise(resolve => setImmediate(resolve))
    assert.equal(button('삭제').props.disabled, true)
    await button('삭제').props.onClick()
    assert.equal(requests.length, 2)
    release(); await promise; await nextTick()
    assert.equal(requests[1].method, 'delete')
    assert.equal(requests[1].url, `${endpoint}/10001`)
    assert.equal(requests[1].data, undefined)
    assert.equal(state.modelValue, false)
    assert.deepEqual(events.filter(e => e[0] === 'saved'), [['saved', kind === 'Bike' ? '자전거를 삭제했습니다.' : '시설을 삭제했습니다.']])
    pass(`${kind}: one existing DELETE API call, 204 success, one reload event`)
    state[prop] = { id: 10002, location: 'DAEJEON', facilityType: 'ELEVATOR' }
    state.modelValue = true; await nextTick()
    assert.ok(button('수정')); assert.ok(!content(root).includes('삭제하시겠습니까?'))
    pass(`${kind}: reopening restores edit form`)
    app.unmount()
  }
  const { ref } = await import('vue')
  const { usePanelList } = await server.ssrLoadModule('/src/composables/usePanelList.js')
  const rows = ref([{ id: 3 }, { id: 2 }, { id: 1 }])
  const list = usePanelList(rows, r => [r.id], 1)
  list.page.value = 3; rows.value = [{ id: 3 }, { id: 2 }]; await nextTick()
  assert.equal(list.page.value, 2); assert.equal(list.pageItems.value[0].id, 2)
  pass('deletion on last page clamps to remaining page')
  const router = await readFile(path.join(rootDir, 'src/router/index.js'), 'utf8')
  for (const kind of ['Bike', 'Facility', 'Member']) {
    await assert.rejects(access(path.join(rootDir, `src/views/dashboard/${kind}ManagementView.vue`)))
    assert.ok(!router.includes(`${kind}ManagementView.vue`))
  }
  for (const name of ['dashboard-members', 'dashboard-bikes', 'dashboard-facilities']) assert.ok(!router.includes(`name: '${name}'`))
  for (const name of ['dashboard-bike-maintenance', 'dashboard-facility-maintenance', 'bike-detail']) assert.ok(router.includes(`name: '${name}'`))
  const dashboard = await readFile(path.join(rootDir, 'src/views/dashboard/DashboardHomeView.vue'), 'utf8')
  assert.ok(!dashboard.includes('<RouterLink'))
  assert.ok(!dashboard.includes('c.to'))
  pass('removed management pages and links; maintenance and public detail remain')
  console.log(`\n${passed} dashboard checks passed. No real database writes were made.`)
} finally {
  await server.close()
  for (const file of tempFiles) await unlink(file)
  delete globalThis.__dashboardControlDeps
}

