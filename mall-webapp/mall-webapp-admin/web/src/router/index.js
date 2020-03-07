import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

Vue.component('TopMenu', () => import('../components/top-menu/index.vue'));
Vue.component('FileUpload', () => import('../components/file-upload/index.vue'));
Vue.component('RichEditor', () => import('../components/rich-editor/index.vue'));
Vue.component('SelectTree', () => import('../components/select-tree/index.vue'));

const routes = [{
    path: '/',
    name: 'Index',
    component: () => import('../views/index/index.vue')
}, {
    path: '/Login',
    name: 'Login',
    component: () => import('../views/login/index.vue')
}, {
    path: '/Menu',
    name: 'Menu',
    component: () => import('../views/menu/index.vue')
}, {
    path: '/OperationUser',
    name: 'OperationUser',
    component: () => import('../views/operation-user/index.vue')
}, {
    path: '/OperationUserInfo',
    name: 'OperationUserInfo',
    component: () => import('../views/operation-user-info/index.vue')
}, {
    path: '/User',
    name: 'User',
    component: () => import('../views/user/index.vue')
}, {
    path: '/Shop',
    name: 'Shop',
    component: () => import('../views/shop/index.vue')
}, {
    path: '/ShopInfo',
    name: 'ShopInfo',
    component: () => import('../views/shop-info/index.vue')
}, {
    path: '/Category',
    name: 'Category',
    component: () => import('../views/category/index.vue')
}, {
    path: '/Brand',
    name: 'Brand',
    component: () => import('../views/brand/index.vue')
}, {
    path: '/AtomsGoods',
    name: 'AtomsGoods',
    component: () => import('../views/atoms-goods/index.vue')
}, {
    path: '/Goods',
    name: 'Goods',
    component: () => import('../views/goods/index.vue')
}, {
    path: '/Region',
    name: 'Region',
    component: () => import('../views/region/index.vue')
}, {
    path: '/Logistics',
    name: 'Logistics',
    component: () => import('../views/logistics/index.vue')
}, {
    path: '/Order',
    name: 'Order',
    component: () => import('../views/order/index.vue')
}]

const router = new VueRouter({
    base: process.env.BASE_URL,
    routes
})

export default router
