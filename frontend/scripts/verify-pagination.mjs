import assert from 'node:assert/strict'
import { readFile, writeFile, unlink } from 'node:fs/promises'
import { parse, compileScript } from '@vue/compiler-sfc'
import { createRenderer, h, reactive, nextTick } from 'vue'

const source = await readFile(new URL('../src/components/common/BasePagination.vue', import.meta.url), 'utf8')
const { descriptor } = parse(source)
const temporary = new URL(`./.pagination-component-${process.pid}.mjs`, import.meta.url)
await writeFile(temporary, compileScript(descriptor, { id: 'pagination-test', inlineTemplate: true }).content)
try {
  const { default: Pagination } = await import(temporary.href)
  const node = (tag, text = '') => ({ tag, text, props: {}, children: [], parent: null })
  const renderer = createRenderer({
    createElement: node,
    createText: text => node('#text', text),
    createComment: text => node('#comment', text),
    setText: (n, text) => { n.text = text },
    setElementText: (n, text) => { n.text = text; n.children = [] },
    patchProp: (n, key, _old, value) => { n.props[key] = value },
    insert(n, parent, anchor = null) {
      if (n.parent) n.parent.children.splice(n.parent.children.indexOf(n), 1)
      const at = anchor ? parent.children.indexOf(anchor) : -1
      parent.children.splice(at < 0 ? parent.children.length : at, 0, n)
      n.parent = parent
    },
    remove(n) { n.parent.children.splice(n.parent.children.indexOf(n), 1); n.parent = null },
    parentNode: n => n.parent,
    nextSibling: n => n.parent?.children[n.parent.children.indexOf(n) + 1] || null,
  })
  const root = node('root')
  const state = reactive({ page: 12, totalPages: 25, boundaryLinks: true, always: false })
  const app = renderer.createApp({ render: () => h(Pagination, {
    ...state, 'onUpdate:page': page => { state.page = page },
  }) })
  app.mount(root)
  const all = n => [n, ...n.children.flatMap(all)]
  const button = label => all(root).find(n => n.tag === 'button' && n.props['aria-label'] === label)
  const click = async label => {
    const b = button(label)
    assert.ok(b, label)
    assert.equal(b.props.disabled, false, label)
    b.props.onClick()
    await nextTick()
  }
  await click('맨 처음 페이지')
  assert.equal(state.page, 1)
  assert.equal(button('맨 처음 페이지').props.disabled, true)
  assert.equal(button('이전 페이지').props.disabled, true)
  await click('맨 마지막 페이지')
  assert.equal(state.page, 25)
  assert.equal(button('맨 마지막 페이지').props.disabled, true)
  assert.equal(button('다음 페이지').props.disabled, true)
  await click('이전 페이지')
  assert.equal(state.page, 24)
  await click('다음 페이지')
  assert.equal(state.page, 25)
  const numeric = all(root).find(n => n.tag === 'button' && n.text === '20')
  assert.ok(numeric)
  numeric.props.onClick()
  await nextTick()
  assert.equal(state.page, 20)
  state.totalPages = 3
  state.page = 1
  await nextTick()
  await click('맨 마지막 페이지')
  assert.equal(state.page, 3)
  state.page = 1
  state.totalPages = 1
  await nextTick()
  assert.equal(all(root).filter(n => n.tag === 'nav').length, 0)
  state.always = true
  await nextTick()
  for (const label of ['맨 처음 페이지', '이전 페이지', '다음 페이지', '맨 마지막 페이지']) {
    assert.equal(button(label).props.disabled, true)
  }
  state.boundaryLinks = false
  state.totalPages = 10
  await nextTick()
  assert.equal(button('맨 처음 페이지'), undefined)
  assert.equal(button('맨 마지막 페이지'), undefined)
  assert.ok(button('이전 페이지'))
  assert.ok(button('다음 페이지'))
  app.unmount()
  console.log('Pagination regression passed: first/last, previous/next, numbers, changed totals, single page, opt-in scope.')
} finally {
  await unlink(temporary)
}
