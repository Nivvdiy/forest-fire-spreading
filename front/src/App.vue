<script setup lang="ts">
import { ref, onMounted } from 'vue';
import SimulationDisplay from './components/SimulationDisplay/SimulationDisplay.vue';
import SimulationControls from './components/SimulationControls/SimulationControls.vue';

interface Cell {
  id: number;
  x: number;
  y: number;
  state: string;
}
interface Row {
  id: number;
  y: number;
  cells: Cell[];
}

const gridData = ref<Row[]>([]);
const simulationFinished = ref(false);
const isPlaying = ref(false);
const simulationSpeed = ref(1);

const speedMapping: Record<number, number> = {
  1: 1000,
  2: 750,
  3: 500,
  4: 250
};

let simulationInterval: ReturnType<typeof setInterval> | null = null;

async function fetchInitialGrid() {
  try {
    const response = await fetch('http://localhost:8080/api/simulation/init');
    if (response.ok) {
      const newData = await response.json();
      gridData.value.splice(0, gridData.value.length, ...newData);
      simulationFinished.value = false;
      isPlaying.value = false;
      console.log('Grille initiale récupérée :', gridData.value);
    } else {
      console.error('Erreur lors de la récupération de la grille initiale.');
    }
  } catch (error) {
    console.error('Erreur fetchInitialGrid :', error);
  }
}

async function fetchInitialForcedGrid(height: number, width: number) {
  try {
    const response = await fetch('http://localhost:8080/api/simulation/initForce?'+ new URLSearchParams({
      height: height.toString(),
      width: width.toString(),
    }).toString());
    if (response.ok) {
      const newData = await response.json();
      gridData.value.splice(0, gridData.value.length, ...newData);
      simulationFinished.value = false;
      isPlaying.value = false;
      console.log('Grille initiale récupérée :', gridData.value);
    } else {
      console.error('Erreur lors de la récupération de la grille initiale.');
    }
  } catch (error) {
    console.error('Erreur fetchInitialGrid :', error);
  }
}

onMounted(() => {
  handleRegenerate();
});

function handleRegenerate(payload?: {forceSize: boolean,height: number,width: number}) {
  console.log('Regenerate grid with payload:', payload);
  simulationFinished.value = false;
  isPlaying.value = false;
  if (simulationInterval) {
    clearInterval(simulationInterval);
    simulationInterval = null;
  }
  if(payload && payload.forceSize) {
    fetchInitialForcedGrid(payload.height, payload.width);
  } else {
    fetchInitialGrid();
  }
}

async function handleSimulateStep() {
  try {
    const response = await fetch('http://localhost:8080/api/simulation/next', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(gridData.value)
    });
    if (response.ok) {
      const data = await response.json();
      simulationFinished.value = data.finished;
      const grid = data.grid;
      gridData.value.splice(0, gridData.value.length, ...grid);
      console.log('Nouvelle grille (étape suivante) :', gridData.value);
      if (simulationFinished.value && simulationInterval) {
        clearInterval(simulationInterval);
        simulationInterval = null;
        isPlaying.value = false;
      }
    } else {
      console.error('Erreur lors du calcul de l’étape suivante.');
    }
  } catch (error) {
    console.error('Erreur handleSimulateStep :', error);
  }
}

function startSimulationInterval() {
  simulationInterval = setInterval(handleSimulateStep, speedMapping[simulationSpeed.value]);
}

function handleToggleSimulation() {
  isPlaying.value = !isPlaying.value;
  console.log('Toggle simulation, isPlaying:', isPlaying.value);
  if (isPlaying.value && !simulationFinished.value) {
    startSimulationInterval();
  } else {
    if (simulationInterval) {
      clearInterval(simulationInterval);
      simulationInterval = null;
    }
  }
}

function handleSpeedChange(newSpeed: number) {
  console.log('Speed changed to X' + newSpeed);
  simulationSpeed.value = newSpeed;
  if (isPlaying.value && simulationInterval) {
    clearInterval(simulationInterval);
    startSimulationInterval();
  }
}
</script>

<template>
  <div id="app" class="app-container">
    <h1>Forest Fire Simulation</h1>
    <SimulationDisplay :gridData="gridData" />
    <SimulationControls 
      :simulationFinished="simulationFinished" 
      :isPlaying="isPlaying"
      @regenerate="handleRegenerate" 
      @simulateStep="handleSimulateStep" 
      @toggleSimulation="handleToggleSimulation"
      @speedChange="handleSpeedChange"
    />
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

.app-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  min-height: 100vh;
}
</style>
