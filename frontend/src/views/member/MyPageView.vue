<template>
  <div>
    <PageHeader title="마이페이지" />
    <MyPageNav />

    <LoadingState v-if="!user" />
    <template v-else>
      <dl class="grid gap-x-8 gap-y-4 rounded-lg bg-white p-6 shadow-sm ring-1 ring-gray-200 sm:grid-cols-2">
        <div v-for="row in rows" :key="row.label">
          <dt class="text-xs font-medium text-gray-500">{{ row.label }}</dt>
          <dd class="mt-1 text-sm text-gray-900">{{ row.value }}</dd>
        </div>
        <div>
          <dt class="text-xs font-medium text-gray-500">상태</dt>
          <dd class="mt-1"><StatusBadge :label="labelOf(memberStatusLabels, user.status)" :tone="statusTone[user.status]" /></dd>
        </div>
      </dl>

      <section class="mt-8 rounded-lg border border-red-200 bg-white p-6">
        <h2 class="font-bold text-red-700">회원 탈퇴</h2>
        <p class="mt-1 text-sm text-gray-600">탈퇴하면 계정이 비활성화되며 더 이상 로그인할 수 없습니다.</p>
        <BaseButton class="mt-4" variant="danger" @click="openWithdraw">회원 탈퇴</BaseButton>
      </section>
    </template>

    <BaseModal v-model="withdrawOpen" title="회원 탈퇴" :persistent="submitting">
      <p class="mb-4 text-sm text-gray-700">정말 탈퇴하시겠습니까? 본인 확인을 위해 비밀번호를 입력해 주세요.</p>
      <BaseInput v-model="withdrawPassword" label="비밀번호" type="password" autocomplete="current-password" :error="error?.fieldErrors?.password" />
      <p v-if="error && !error.fieldErrors?.password" class="mt-2 text-sm text-red-600" role="alert">{{ error.message }}</p>
      <template #footer>
        <BaseButton variant="secondary" :disabled="submitting" @click="withdrawOpen = false">취소</BaseButton>
        <BaseButton variant="danger" :loading="submitting" :disabled="!withdrawPassword" @click="withdraw">탈퇴하기</BaseButton>
      </template>
    </BaseModal>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '../../components/common/PageHeader.vue'
import MyPageNav from '../../components/common/MyPageNav.vue'
import LoadingState from '../../components/common/LoadingState.vue'
import StatusBadge from '../../components/common/StatusBadge.vue'
import BaseButton from '../../components/common/BaseButton.vue'
import BaseInput from '../../components/common/BaseInput.vue'
import BaseModal from '../../components/common/BaseModal.vue'
import { useAuthStore } from '../../stores/authStore'
import { useSubmit } from '../../composables/useSubmit'
import { memberService } from '../../services/memberService'
import { memberRoleLabels, memberStatusLabels, labelOf, statusTone } from '../../utils/statusLabels'
import { formatDateTime } from '../../utils/formatDate'

const auth = useAuthStore()
const router = useRouter()
const user = computed(() => auth.currentUser)
const rows = computed(() => [
  { label: '아이디', value: user.value.loginId },
  { label: '이름', value: user.value.name },
  { label: '이메일', value: user.value.email || '-' },
  { label: '전화번호', value: user.value.tel || '-' },
  { label: '권한', value: labelOf(memberRoleLabels, user.value.role) },
  { label: '가입일', value: formatDateTime(user.value.createdAt) },
])

const withdrawOpen = ref(false)
const withdrawPassword = ref('')
const { submitting, error, run, clearError } = useSubmit()

function openWithdraw() {
  withdrawPassword.value = ''
  clearError()
  withdrawOpen.value = true
}

async function withdraw() {
  // TODO(미확정): 탈퇴 API(DELETE /api/members/me/delete)와 비밀번호 재확인 방식은 실제 Controller 확인 후 맞춘다.
  const ok = await run(() => memberService.withdraw({ password: withdrawPassword.value }))
  if (!ok) return
  withdrawOpen.value = false
  auth.clearSession()
  router.replace('/')
}
</script>
