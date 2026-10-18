<template>
  <div class="md:flex md:gap-8">
    <MyPageNav />
    <div class="min-w-0 flex-1">
    <PageHeader title="내 정보">
      <BaseButton variant="secondary" @click="$router.push('/mypage/edit')">정보 수정</BaseButton>
    </PageHeader>

    <LoadingState v-if="!user" />
    <template v-else>
      <dl class="divide-y divide-gray-100 rounded-lg bg-white shadow-sm ring-1 ring-gray-200">
        <div v-for="row in rows" :key="row.label" class="flex items-center gap-4 px-5 py-3.5">
          <dt class="w-28 shrink-0 text-sm font-medium text-gray-600">{{ row.label }}</dt>
          <dd class="text-sm text-gray-900">
            <StatusBadge v-if="row.badge" :label="labelOf(memberStatusLabels, user.status)" :tone="statusTone[user.status]" />
            <template v-else>{{ row.value }}</template>
          </dd>
        </div>
      </dl>

      <div class="mt-6 flex justify-end">
        <BaseButton variant="danger" @click="openWithdraw">회원 탈퇴</BaseButton>
      </div>
    </template>
    </div>

    <BaseModal v-model="withdrawOpen" title="회원 탈퇴" :persistent="submitting">
      <p class="mb-4 text-sm text-gray-700">정말 탈퇴하시겠습니까? 탈퇴하면 계정이 비활성화되어 더 이상 로그인할 수 없습니다. 본인 확인을 위해 비밀번호를 입력해 주세요.</p>
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
  { label: '가입일', value: formatDateTime(user.value.createdAt) },
  { label: '계정 상태', badge: true },
  { label: '권한', value: labelOf(memberRoleLabels, user.value.role) },
  { label: '전화번호', value: user.value.tel || '-' },
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
