<template>
  <div>
    <ManageToolbar v-model="list.keyword.value" title="회원 관리" keyword-label="회원 번호" keyword-placeholder="회원 검색" @search="list.search">
      <div class="w-32"><BaseSelect v-model="role" :options="roleOptions" placeholder="전체" label="권한" /></div>
      <div class="w-28"><BaseSelect v-model="status" :options="statusOptions" placeholder="전체" label="상태" /></div>
    </ManageToolbar>

    <p v-if="notice" class="mb-3 rounded-md bg-green-50 p-3 text-sm text-green-800" role="status">{{ notice }}</p>

    <LoadingState v-if="list.loading.value" />
    <ErrorState v-else-if="list.error.value" :error="list.error.value" @retry="list.load" />
    <EmptyState v-else-if="!list.pageItems.value.length" title="조건에 맞는 회원이 없습니다." />
    <template v-else>
      <BaseTable>
        <template #head>
          <tr>
            <th class="px-4 py-3">ID</th><th class="px-4 py-3">아이디</th><th class="px-4 py-3">이름</th>
            <th class="hidden px-4 py-3 md:table-cell">이메일</th><th class="px-4 py-3">권한</th><th class="px-4 py-3">상태</th><th class="px-4 py-3 text-right">관리</th>
          </tr>
        </template>
        <tr v-for="m in list.pageItems.value" :key="m.id">
          <td class="px-4 py-3 text-gray-500">{{ m.id }}</td>
          <td class="px-4 py-3 font-medium">{{ m.loginId }}<span v-if="isMe(m)" class="ml-1 text-xs text-gray-400">(나)</span></td>
          <td class="px-4 py-3">{{ m.name }}</td>
          <td class="hidden px-4 py-3 text-gray-600 md:table-cell">{{ m.email }}</td>
          <td class="px-4 py-3">{{ labelOf(memberRoleLabels, m.role) }}</td>
          <td class="px-4 py-3"><StatusBadge :label="labelOf(memberStatusLabels, m.status)" :tone="statusTone[m.status]" /></td>
          <td class="px-4 py-3">
            <div class="flex justify-end gap-1.5">
              <!-- 본인 계정의 권한/상태 변경은 관리자 잠금(lock-out) 방지를 위해 UI 에서 막는다. -->
              <BaseButton size="sm" variant="secondary" :disabled="isMe(m)" @click="openEdit(m, 'role')">권한 변경</BaseButton>
              <BaseButton size="sm" variant="secondary" :disabled="isMe(m) || m.role === 'ADMIN'" @click="openEdit(m, 'status')">상태 변경</BaseButton>
            </div>
          </td>
        </tr>
      </BaseTable>
      <BasePagination v-model:page="list.page.value" :total-pages="list.totalPages.value" />
    </template>

    <BaseModal v-model="open" :title="mode === 'role' ? '권한 변경' : '상태 변경'" :persistent="submitting">
      <p class="mb-3 text-sm text-gray-700">{{ target?.name }} ({{ target?.loginId }})</p>
      <BaseSelect v-if="mode === 'role'" v-model="value" :options="roleOptions" label="변경할 권한" />
      <BaseSelect v-else v-model="value" :options="statusOptions" label="변경할 상태" />
      <p v-if="changed" class="mt-3 text-sm text-gray-600">'{{ currentLabel }}' → '{{ nextLabel }}' 로 변경합니다.</p>
      <p v-if="warn" class="mt-2 rounded-md bg-amber-50 p-3 text-sm text-amber-800">{{ warn }}</p>
      <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="open = false">취소</BaseButton>
        <BaseButton :variant="warn ? 'danger' : 'primary'" :loading="submitting" :disabled="!changed" @click="save">변경</BaseButton>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import ManageToolbar from '../../components/common/ManageToolbar.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseSelect from '../../components/common/BaseSelect.vue'
import BaseModal from '../../components/common/BaseModal.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import ErrorState from '../../components/common/ErrorState.vue'
import EmptyState from '../../components/common/EmptyState.vue'
import BaseTable from '../../components/common/BaseTable.vue'
import BasePagination from '../../components/common/BasePagination.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import { useAuthStore } from '../../stores/authStore'
import { useListView } from '../../composables/useListView'
import { useSubmit } from '../../composables/useSubmit'
import { adminMemberService } from '../../services/memberService'
import { memberRoleLabels, memberStatusLabels, labelOf, statusTone, toOptions } from '../../utils/statusLabels'

const auth = useAuthStore()
const isMe = (m) => m.id === auth.currentUser?.id

const role = ref('')
const status = ref('')
const roleOptions = toOptions(memberRoleLabels)
const statusOptions = toOptions(memberStatusLabels)
// 비밀번호/해시는 DTO(MemberResponse)에 없으며 화면 어디에도 표시하지 않는다.
const list = useListView((p) => adminMemberService.list(p), {
  searchFields: (m) => [m.id, m.loginId, m.name, m.email],
  predicate: (m) => (!role.value || m.role === role.value) && (!status.value || m.status === status.value),
  sort: (a, b) => (b.id ?? 0) - (a.id ?? 0),
  watchSources: [role, status],
})

const { submitting, error, run, clearError } = useSubmit()
const notice = ref('')
const open = ref(false)
const mode = ref('role')
const target = ref(null)
const value = ref('')

const labels = computed(() => (mode.value === 'role' ? memberRoleLabels : memberStatusLabels))
const original = computed(() => (mode.value === 'role' ? target.value?.role : target.value?.status))
const changed = computed(() => !!value.value && value.value !== original.value)
const currentLabel = computed(() => labelOf(labels.value, original.value))
const nextLabel = computed(() => labelOf(labels.value, value.value))
const warn = computed(() => {
  if (mode.value === 'role' && target.value?.role === 'ADMIN' && value.value !== 'ADMIN') return '관리자 권한을 제거합니다. 신중하게 확인해 주세요.'
  if (mode.value === 'status' && value.value === 'INACTIVE') return '비활성화하면 해당 회원은 로그인할 수 없습니다. (삭제되지 않습니다)'
  if (mode.value === 'status' && value.value === 'KICKED') return '강퇴 처리하면 해당 회원은 이용이 제한됩니다.'
  return ''
})

function openEdit(m, m2) {
  target.value = m
  mode.value = m2
  value.value = m2 === 'role' ? m.role : m.status
  clearError()
  open.value = true
}
async function save() {
  const id = target.value.id
  const ok = await run(() => (mode.value === 'role' ? adminMemberService.updateRole(id, value.value) : adminMemberService.updateStatus(id, value.value)))
  if (!ok) return
  open.value = false
  notice.value = `${target.value.loginId} 회원의 ${mode.value === 'role' ? '권한' : '상태'}을 변경했습니다.`
  setTimeout(() => (notice.value = ''), 4000)
  await list.load()
}
</script>
