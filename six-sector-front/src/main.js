import Vue from 'vue'
Vue.config.productionTip = false

// 引用antdesign
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
Vue.use(Antd)

// 全局引用axios
import axios from 'axios'
Vue.prototype.$axios = axios;

// 全局引用qs
import qs from 'qs'
Vue.prototype.$qs = qs;

/** 初始化Vue **/
import router from './router'
import App from './App.vue'

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
