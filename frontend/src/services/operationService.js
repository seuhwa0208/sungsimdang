export function changeOperationStatus(service, id, current, next) {
  if (current === 'REPAIRING' && next === 'AVAILABLE') return service.completeMaintenance(id)
  return service.updateStatus(id, next)
}
