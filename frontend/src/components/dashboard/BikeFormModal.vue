<template>
  <BaseModal :model-value="modelValue" :title="deleteRequested ? '자전거 삭제' : bike ? `자전거 ${formatBikeId(bike.id)} 정보 수정` : '자전거 등록'" :persistent="submitting" @update:model-value="emit('update:modelValue', $event)">
    <p v-if="deleteRequested" class="text-sm text-gray-700">자전거 {{ formatBikeId(bike.id) }} 정보를 삭제하시겠습니까? 해당 자전거의 정비 이력도 함께 삭제됩니다.</p>
    <!-- 자전거 번호는 입력받지 않는다. 식별자는 서버가 부여하는 id 이다. -->
    <BaseSelect v-else v-model="location" :options="locationOptions" label="위치" placeholder="위치를 선택하세요" required :error="locationError" />
    <p v-if="error" class="mt-3 text-sm text-red-600" role="alert">{{ error.message }}</p>
    <template #footer>
      <template v-if="deleteRequested">
        <BaseButton variant="secondary" :disabled="submitting" @click="cancelDelete">취소</BaseButton>
        <BaseButton variant="danger" :loading="submitting" @click="remove">삭제</BaseButton>
      </template>
      <template v-else>
        <BaseButton v-if="bike" variant="danger" class="mr-auto" :disabled="submitting" @click="askDelete">삭제</BaseButton>
        <BaseButton variant="secondary" :disabled="submitting" @click="emit('update:modelValue', false)">취소</BaseButton>
        <BaseButton :loading="submitting" @click="save">{{ bike ? '수정' : '등록' }}</BaseButton>
      </template>
    </template>
  </BaseModal>
</template>

<script setup>
import { computed, ref, watch } from 'vue'
import BaseModal from '../common/BaseModal.vue'
import BaseSelect from '../common/BaseSelect.vue'
import BaseButton from '../common/BaseButton.vue'
import { useSubmit } from '../../composables/useSubmit'
import { bikeService } from '../../services/bikeService'
import { LOCATION_OPTIONS } from '../../constants/locations'
import { formatBikeId } from '../../utils/format'

// 자전거 등록(bike=null) / 위치 수정. 성공하면 saved 이벤트로 안내 문구를 올린다.
const props = defineProps({ modelValue: Boolean, bike: { type: Object, default: null } })
const emit = defineEmits(['update:modelValue', 'saved'])

const { submitting, error, run, clearError } = useSubmit()
const location = ref('')
const locationError = ref('')
const deleteRequested = ref(false)
// 기존 위치가 목록에 없더라도 선택할 수 있도록 옵션에 포함
const locationOptions = computed(() => {
  const options = [...LOCATION_OPTIONS]
  const current = props.bike?.location
  if (current && !options.some(option => option.value === current)) options.push({ value: current, label: current })
  return options
})

watch(
  () => props.modelValue,
  (open) => {
    if (!open) return
    location.value = props.bike?.location ?? ''
    locationError.value = ''
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
  if (!deleteRequested.value || !props.bike) return
  const ok = await run(() => bikeService.remove(props.bike.id))
  if (!ok) return
  emit('update:modelValue', false)
  emit('saved', '자전거를 삭제했습니다.')
}

async function save() {
  locationError.value = location.value ? '' : '위치를 선택해 주세요.'
  if (locationError.value) return
  const isEdit = !!props.bike
  const ok = await run(() => (isEdit ? bikeService.update(props.bike.id, location.value) : bikeService.create(location.value)))
  if (!ok) return
  emit('update:modelValue', false)
  emit('saved', isEdit ? '자전거 정보를 수정했습니다.' : '자전거를 등록했습니다.')
}
</script>
