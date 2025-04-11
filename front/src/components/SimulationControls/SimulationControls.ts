import { defineComponent, ref, toRefs, computed  } from 'vue';

export default defineComponent({
  name: 'SimulationControls',
  props: {
    simulationFinished: {
      type: Boolean,
      required: true
    },
    isPlaying: {
      type: Boolean,
      required: true
    }
  },
  emits: ['regenerate', 'simulateStep', 'toggleSimulation', 'speedChange'],
  setup(props, { emit }) {

    const { simulationFinished, isPlaying } = toRefs(props);

    const speed = ref<number>(1);

    const forceSize = ref(false);
    const height = ref(1);
    const width = ref(1);

    const regenerateGrid = () => {
    if(height.value < 1 ) {
      height.value = 1;
    } else if(height.value > 100) {
      height.value = 100;
    }
    if(width.value < 1 ) {
      width.value = 1;
    } else if(width.value > 100) {
      width.value = 100;
    }
      emit('regenerate', {
        forceSize: forceSize.value,
        height: height.value,
        width: width.value
      });
    };

    const toggleSimulation = () => {
      emit('toggleSimulation');
    };

    const nextStep = () => {
      if (!props.simulationFinished) {
        emit('simulateStep');
      }
    };

    const handleSpeedChange = () => {
      emit('speedChange', speed.value);
    };
    
    const handleLeft = computed(() => {
      return ((speed.value - 1) / 3) * 100 + '%';
    });

    const decreaseSpeed = () => {
      if (speed.value > 1) {
        speed.value--;
        handleSpeedChange();
      }
    };

    const increaseSpeed = () => {
      if (speed.value < 4) {
        speed.value++;
        handleSpeedChange();
      }
    };

    return {
      regenerateGrid,
      toggleSimulation,
      nextStep,
      speed,
      forceSize,
      height,
      width,
      handleLeft,
      decreaseSpeed,
      increaseSpeed,
      handleSpeedChange,
      simulationFinished,
      isPlaying
    };
  }
});
