import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Home',
        component: () => import('@/views/sys/HomePage')
    },
    {
        path: '/hello',
        name: 'Hello',
        component: () => import('@/components/HelloWorld')
    },
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/sys/Login')
    },
]

const router = new VueRouter({
    mode: 'history',
    routes
})

export default router