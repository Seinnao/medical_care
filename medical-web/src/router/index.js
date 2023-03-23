import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
    {
        path: '/login',
        name: 'login',
        component: () => import('@/views/sys/Login')
    },
    {
        path: '/register',
        name: 'register',
        component: () => import('@/views/sys/Register')
    },]

const router = new VueRouter({
    mode: 'history',
    routes
})


// 重置路由
export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        routes
    })
}

// 注意：刷新页面会导致页面路由重置
export const setRoutes = () => {
    const storeMenus = localStorage.getItem("menus");

    console.log(storeMenus)

    if (storeMenus) {
        // 拼装动态路由
        const manageRoute = {
            path: '/',
            name: 'Manage',
            component: () => import('../views/sys/Manage'),
            redirect: "/home",
            children: [{
                path: 'person',
                name: '个人信息',
                component: () => import('../views/user/Person')
            }, {
                path: 'password',
                name: '修改密码',
                component: () => import('../views/user/Password')
            }]
        }

        const menus = JSON.parse(storeMenus)//动态加截菜单，来自于数据库

        menus.forEach(item => {
            if (item.path) {  // 当且仅当path不为空的时候才去设置路由
                let itemMenu = {
                    path: item.path.replace("/", ""),
                    name: item.name,
                    component: () => import('../views/' + item.pagePath + '.vue')
                }
                manageRoute.children.push(itemMenu)
            } else if (item.children.length) {
                item.children.forEach(item => {
                    if (item.path) {
                        let itemMenu = {
                            path: item.path.replace("/", ""),
                            name: item.name,
                            component: () => import('../views/' + item.pagePath + '.vue')
                        }
                        manageRoute.children.push(itemMenu)
                    }
                })
            }
        })

        // console.log("menus",menus);
        // console.log("manageRoute",manageRoute);
        // 获取当前的路由对象名称数组
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if (!currentRouteNames.includes('Manage')) {
            // 动态添加到现在的路由对象中去
            router.addRoute(manageRoute)
        }
    }
}

// 每次刷新页面都要重新设置路由，否则路由就会被重置
setRoutes()

//全局钩子
router.beforeEach((to, from, next) => {
    localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称
    store.commit("setPath")
    if (!to.matched.length) {
        const menus = localStorage.getItem("menus")
        if (!menus) {
            next("/login")
        } else {
            next("/404")
        }
    } else {
        next()
    }
})

export default router