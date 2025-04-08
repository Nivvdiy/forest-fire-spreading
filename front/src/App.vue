<script setup lang="ts">
import SimulationDisplay from './components/SimulationDisplay/SimulationDisplay.vue';
import SimulationControls from './components/SimulationControls/SimulationControls.vue';
</script>

<template>
  <header>
    <h1>Fire Spread Simulation</h1>
  </header>
  <img
    class="logo vue"
    alt="Vue logo"
    src="./assets/vue.svg"
    style="width: 100px"
  />
  <div id="app">
    <SimulationDisplay />
    <SimulationControls />
  </div>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
