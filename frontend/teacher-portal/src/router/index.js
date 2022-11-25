import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import ScheduleView from '../views/ScheduleView.vue'
import LoginView from '../views/LoginView.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'schedule',
        component: ScheduleView
    },
    {
        path: '/login',
        name: 'login',
        component: LoginView
    },    
]

const router = new VueRouter({
    mode: "history",
    base: process.env.BASE_URL,
    routes
});

router.beforeEach(async (to, from, next) => {
    if (store.state.isAuthenticated) {
        store.commit("verifyRefreshToken")
    }

    next()
})

export default router