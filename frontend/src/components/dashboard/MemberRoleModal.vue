<template>
  <BaseModal :model-value="modelValue" title="권한 변경" :persistent="submitting" @update:model-value="emit('update:modelValue', $event)">
    <p class="mb-3 text-sm text-gray-700">{{ member?.name }} ({{ member?.loginId }})</p>
    <BaseSelect v-model="newRole" :options="roleOptions" label="변경할 권한" />
    <p v-if="changed" class="mt-3 text-sm text-gray-600">'{{ labelOf(memberRoleLabels, member?.role) }}' → '{{ labelOf(memberRoleLabels, newRole) }}' 로 변경합니다.</p>
    <p v-if="warn" class="mt-2 rounded-md bg-amber-50 p-3 text-sm text-amber-800">{{ warn }}</p>
    <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
    <template #footer>
      <BaseButton variant="secondary" :disabled="submitting" @click="emit('update:modelValue', false)">취소</BaseButton>
      <BaseButton :variant="warn ? 'danger' : 'primary'" :loading="submitting" :disabled="!changed" @click="save">변경</BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import BaseModal from '../common/BaseModal.vue'
import BaseSelect from '../common/BaseSelect.vue'
import BaseButton from '../common/BaseButton.vue'
import { useSubmit } from '../../composables/useSubmit'
import { adminMemberService } from '../../services/memberService'
import { memberRoleLabels, labelOf, toOptions } from '../../utils/statusLabels'

// 회원 권한 변경 (대시보드 회원 관리 패널의 "권한 변경" 버튼). 성공하면 saved 이벤트로 안내 문구를 올린다.
const props = defineProps({ modelValue: Boolean, member: { type: Object, default: null } })
const emit = defineEmits(['update:modelValue', 'saved'])

const { submitting, error, run, clearError } = useSubmit()
const roleOptions = toOptions(memberRoleLabels)
const newRole = ref('')
const changed = computed(() => !!newRole.value && newRole.value !== props.member?.role)
const warn = computed(() => (props.member?.role === 'ADMIN' && newRole.value !== 'ADMIN' ? '관리자 권한을 제거합니다. 신중하게 확인해 주세요.' : ''))

watch(
  () => props.modelValue,
  (open) => {
    if (!open) return
    newRole.value = props.member?.role ?? ''
    clearError()
  },
)

async function save() {
  const ok = await run(() => adminMemberService.updateRole(props.member.id, newRole.value))
  if (!ok) return
  emit('update:modelValue', false)
  emit('saved', `${props.member.loginId} 회원의 권한을 '${labelOf(memberRoleLabels, newRole.value)}'(으)로 변경했습니다.`)
}
</script>
