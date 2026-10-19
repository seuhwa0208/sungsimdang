<template>
  <div class="dtc-table overflow-x-auto rounded-lg bg-white shadow-sm ring-1 ring-gray-200">
    <table class="min-w-full divide-y divide-gray-200 text-sm">
      <thead class="bg-gray-50 text-left text-xs font-semibold uppercase tracking-wide text-gray-500"><slot name="head" /></thead>
      <tbody class="divide-y divide-gray-100"><slot /></tbody>
    </table>
  </div>
</template>
