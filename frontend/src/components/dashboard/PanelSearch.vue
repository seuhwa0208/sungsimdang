<template>
  <!-- 와이어프레임의 패널 검색줄: [라벨 + 입력(회색 안내 글씨)] [상태 선택 또는 상태 버튼] [검색] -->
  <form class="mb-2 flex flex-wrap items-end gap-2" role="search" @submit.prevent="onSubmit">
    <div class="min-w-36 flex-1">
      <label :for="inputId" class="mb-1 block text-xs font-medium text-gray-700">{{ label }}</label>
      <div class="relative">
        <input
          :id="inputId"
          :value="modelValue"
          :placeholder="placeholder"
          :inputmode="numeric ? 'numeric' : undefined"
          :aria-invalid="notice ? 'true' : undefined"
          class="w-full rounded-md border px-3 py-1 text-sm placeholder:text-gray-400 focus:outline-none focus:ring-2"
          :class="notice ? 'border-red-300 focus:ring-red-300' : 'border-gray-300 focus:ring-brand-500'"
          @input="onInput"
        />
        <!-- 레이아웃을 밀지 않도록 입력창 위에 떠 있는 말풍선으로 표시하고 잠시 뒤 자동으로 사라진다. -->
        <span v-if="notice" role="alert" class="pointer-events-none absolute bottom-full left-0 z-20 mb-1.5 whitespace-nowrap rounded-md bg-red-50 px-2.5 py-1 text-xs text-red-700 shadow-lg ring-1 ring-red-200">
          {{ notice }}
          <span class="absolute left-3 top-full -mt-1 h-2 w-2 rotate-45 border-b border-r border-red-200 bg-red-50" aria-hidden="true" />
        </span>
      </div>
    </div>
    <div v-if="statusOptions.length" class="w-28 shrink-0">
      <label :for="selectId" class="mb-1 block text-xs font-medium text-gray-700">상태</label>
      <select
        :id="selectId"
        :value="status"
        class="w-full rounded-md border border-gray-300 bg-white px-2 py-1 text-sm focus:outline-none focus:ring-2 focus:ring-brand-500"
        @change="emit('update:status', $event.target.value)"
      >
        <option value="">전체</option>
        <option v-for="o in statusOptions" :key="o.value" :value="o.value">{{ o.label }}</option>
      </select>
    </div>
    <BaseButton v-if="statusButton" size="xs" type="button" :variant="statusActive ? 'primary' : 'secondary'" :aria-pressed="statusActive" @click="emit('status-click')">상태</BaseButton>
    <BaseButton size="xs" type="submit">검색</BaseButton>
  </form>
</template>

<script setup>
import { onBeforeUnmount, ref, useId } from 'vue'
import BaseButton from '../common/BaseButton.vue'

// 대시보드 패널 안에서 쓰는 검색줄. 한 화면에 여러 개가 있어도 id 가 겹치지 않도록 useId 를 쓴다.
// statusOptions 를 주면 "상태" 선택이 함께 표시된다. (선택하면 바로 목록에 적용)
const props = defineProps({
  modelValue: { type: String, default: '' },
  status: { type: String, default: '' },
  statusOptions: { type: Array, default: () => [] }, // [{ value, label }]
  placeholder: { type: String, default: '검색어를 입력하세요' },
  label: { type: String, default: '검색' },
  statusButton: { type: Boolean, default: false }, // true 면 "상태" 버튼을 보여준다. (눌러서 상태별 정렬 켜기/끄기)
  statusActive: { type: Boolean, default: false }, // 상태별 정렬이 켜져 있는지
  numeric: { type: Boolean, default: false }, // true 면 숫자만 입력할 수 있다. (숫자가 아닌 문자는 입력 즉시 제거하고 안내 문구를 보여준다)
})
const emit = defineEmits(['update:modelValue', 'update:status', 'search', 'status-click'])
const inputId = useId()
const selectId = useId()

const NUMERIC_MESSAGE = '숫자만 입력할 수 있습니다.'
const notice = ref('')
let noticeTimer = null
function showNotice() {
  notice.value = NUMERIC_MESSAGE
  clearTimeout(noticeTimer)
  noticeTimer = setTimeout(() => (notice.value = ''), 2000)
}
onBeforeUnmount(() => clearTimeout(noticeTimer))

// numeric 일 때: 타이핑·붙여넣기로 들어온 숫자 이외의 문자를 입력창에서 바로 지운다.
function onInput(event) {
  let value = event.target.value
  if (props.numeric) {
    const digits = value.replace(/\D/g, '')
    if (digits !== value) {
      event.target.value = digits
      showNotice()
    }
    value = digits
  }
  emit('update:modelValue', value)
}

// 검색 시 한 번 더 검증한다. 숫자 이외의 값이 남아 있으면 검색을 하지 않는다.
function onSubmit() {
  if (props.numeric && !/^\d*$/.test(props.modelValue)) {
    showNotice()
    return
  }
  emit('search')
}
</script>
