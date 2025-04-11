import { defineComponent, toRefs } from 'vue';

export default defineComponent({
  name: 'SimulationDisplay',
  props: {
    gridData: {
      type: Array,
      required: true
    }
  },
  setup(props) {

    const { gridData } = toRefs(props);

    return {
      gridData
    };
  }
});
