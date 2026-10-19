<template>
  <BaseModal :model-value="modelValue" :title="facility ? `${facility.id} 정보 수정` : '시설 등록'" :persistent="submitting" @update:model-value="emit('update:modelValue', $event)">
    <div class="space-y-4">
      <!-- 시설 번호(FACILITY-xxx)는 id 에서 파생되는 표시값이므로 입력받지 않는다. -->
      <BaseSelect v-model="values.facilityType" :options="typeOptions" label="시설 유형" placeholder="유형을 선택하세요" required :error="errors.facilityType" />
      <BaseSelect v-model="values.location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="errors.location" />
    </div>
    <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
    <template #footer>
      <BaseButton variant="secondary" :disabled="submitting" @click="emit('update:modelValue', false)">취소</BaseButton>
      <BaseButton :loading="submitting" @click="save">{{ facility ? '수정' : '등록' }}</BaseButton>
    </template>
  </BaseModal>
</template>

<script setup>
import { computed, reactive, watch } from 'vue'
import BaseModal from '../common/BaseModal.vue'
import BaseSelect from '../common/BaseSelect.vue'
import BaseButton from '../common/BaseButton.vue'
import { useSubmit } from '../../composables/useSubmit'
import { facilityService } from '../../services/facilityService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { facilityTypeLabels, toOptions } from '../../utils/statusLabels'

// 시설 등록(facility=null) / 정보 수정. 성공하면 saved 이벤트로 안내 문구를 올린다.
const props = defineProps({ modelValue: Boolean, facility: { type: Object, default: null } })
const emit = defineEmits(['update:modelValue', 'saved'])

const { submitting, error, run, clearError } = useSubmit()
const typeOptions = toOptions(facilityTypeLabels)
const values = reactive({ facilityType: '', location: '' })
const errors = reactive({ facilityType: '', location: '' })
const locationOptions = computed(() => {
  const options = [...LOCATION_OPTIONS]
  const current = props.facility?.location
  if (current && !options.some(option => option.value === current)) options.push({ value: current, label: current })
  return options
})

watch(
  () => props.modelValue,
  (open) => {
    if (!open) return
    Object.assign(values, { facilityType: props.facility?.facilityType ?? '', location: props.facility?.location ?? '' })
    errors.facilityType = ''
    errors.location = ''
    clearError()
  },
)

async function save() {
  errors.facilityType = values.facilityType ? '' : '시설 유형을 선택해 주세요.'
  errors.location = values.location ? '' : '위치를 선택해 주세요.'
  if (errors.facilityType || errors.location) return
  const isEdit = !!props.facility
  const payload = { facilityType: values.facilityType, location: values.location }
  const ok = await run(() => (isEdit ? facilityService.update(props.facility.id, payload) : facilityService.create(payload)))
  if (!ok) return
  emit('update:modelValue', false)
  emit('saved', isEdit ? '시설 정보를 수정했습니다.' : '시설을 등록했습니다.')
}
</script>
