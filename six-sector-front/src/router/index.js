import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const router = new Router({
  routes: [{
    path: '/',
    redirect: '/login'
  }, {
    path: '/home',
    component: (resolve) => require(['@/systemPage/homeIndex'], resolve)
  }, {
    path: '/login',
    component: (resolve) => require(['@/systemPage/Login'], resolve)
  }]
})

export default router
