<template>
  <BaseModal :model-value="modelValue" :title="deleteRequested ? '시설 삭제' : facility ? `${facility.id} 정보 수정` : '시설 등록'" :persistent="submitting" @update:model-value="emit('update:modelValue', $event)">
    <p v-if="deleteRequested" class="text-sm text-gray-700">시설 {{ formatFacilityId(facility.id) }} 정보를 삭제하시겠습니까? 해당 시설의 정비 이력도 함께 삭제됩니다.</p>
    <div v-else class="space-y-4">
      <!-- 시설 번호(FACILITY-xxx)는 id 에서 파생되는 표시값이므로 입력받지 않는다. -->
      <BaseSelect v-model="values.facilityType" :options="typeOptions" label="시설 유형" placeholder="유형을 선택하세요" required :error="errors.facilityType" />
      <BaseSelect v-model="values.location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="errors.location" />
    </div>
    <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
    <template #footer>
      <template v-if="deleteRequested">
        <BaseButton variant="secondary" :disabled="submitting" @click="cancelDelete">취소</BaseButton>
        <BaseButton variant="danger" :loading="submitting" @click="remove">삭제</BaseButton>
      </template>
      <template v-else>
        <BaseButton v-if="facility" variant="danger" class="mr-auto" :disabled="submitting" @click="askDelete">삭제</BaseButton>
        <BaseButton variant="secondary" :disabled="submitting" @click="emit('update:modelValue', false)">취소</BaseButton>
        <BaseButton :loading="submitting" @click="save">{{ facility ? '수정' : '등록' }}</BaseButton>
      </template>
    </template>
  </BaseModal>
</template>

<script setup>
import { computed, reactive, ref, watch } from 'vue'
import BaseModal from '../common/BaseModal.vue'
import BaseSelect from '../common/BaseSelect.vue'
import BaseButton from '../common/BaseButton.vue'
import { useSubmit } from '../../composables/useSubmit'
import { facilityService } from '../../services/facilityService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { facilityTypeLabels, toOptions } from '../../utils/statusLabels'
import { formatFacilityId } from '../../utils/format'

// 시설 등록(facility=null) / 정보 수정. 성공하면 saved 이벤트로 안내 문구를 올린다.
const props = defineProps({ modelValue: Boolean, facility: { type: Object, default: null } })
const emit = defineEmits(['update:modelValue', 'saved'])

const { submitting, error, run, clearError } = useSubmit()
const typeOptions = toOptions(facilityTypeLabels)
const values = reactive({ facilityType: '', location: '' })
const errors = reactive({ facilityType: '', location: '' })
const deleteRequested = ref(false)
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
    deleteRequested.value = false
    clearError()
  },
)

function askDelete() {
  clearError()
  deleteRequested.value = true
}

function cancelDelete() {
  clearError()
  deleteRequested.value = false
}

async function remove() {
  if (!deleteRequested.value || !props.facility) return
  const ok = await run(() => facilityService.remove(props.facility.id))
  if (!ok) return
  emit('update:modelValue', false)
  emit('saved', '시설을 삭제했습니다.')
}

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
