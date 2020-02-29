import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [{
    path: '/Login',
    name: 'Login',
    component: () => import('../views/login/index.vue')
}]

const router = new VueRouter({
    base: process.env.BASE_URL,
    routes
})

export default router
