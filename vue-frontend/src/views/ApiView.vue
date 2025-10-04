<script setup lang="ts">

import { ref } from 'vue'

const resp = ref<any>({});

function handleFetch() {
  fetch("/api/billing/v1/test").then(value => value.json())
    .then(value => resp.value = value)
}

</script>

<template>
  <div class="home">
    <h1>This is an api page</h1>
    <button @click="handleFetch()">fetch from backend</button>
    <pre>{{ JSON.stringify(resp) }}</pre>
  </div>
</template>
