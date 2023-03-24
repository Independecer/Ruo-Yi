import App from "./App";

import evaluationTabBar from '@/components/evaluation/tabBar.vue'
import curriculumTabBar from '@/components/curriculum/tabBar.vue'
// #ifndef VUE3
import Vue from "vue";
Vue.config.productionTip = false;
App.mpType = "app";

var VConsole = require('vconsole')
const vConsole = new VConsole()

const app = new Vue({
  ...App,
});
app.$mount();
Vue.component('evaluationTabBar',evaluationTabBar);
Vue.component('curriculumTabBar',curriculumTabBar);
// #endif

// #ifdef VUE3
import { createSSRApp } from "vue";
export function createApp() {
  const app = createSSRApp(App);
  return {
    app,
  };
}
// #endif
