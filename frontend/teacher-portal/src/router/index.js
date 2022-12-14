import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '@/store'

import ScheduleView from '../views/ScheduleView.vue'
import LoginView from '../views/LoginView.vue'
import QuizCreate from '../views/QuizCreateView.vue'
import QuizView from '../views/QuizView.vue'
import MarksView from '../views/MarksView.vue'
import AttendanceView from '../views/AttendanceView.vue'
import InvolvementView from '../views/InvolvementView.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'schedule',
        component: ScheduleView,
        meta: {
            requiresAuth: true
        }
    },
    {
      path: '/quizcreate',
      name: 'quizcreate',
      component: QuizCreate,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/quizview',
      name: 'quizview',
      component: QuizView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/involvement',
      name: 'involvement',
      component: InvolvementView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/grade',
      name: 'grade',
      component: MarksView,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: '/attendance',
      name: 'attendance',
      component: AttendanceView,
      meta: {
        requiresAuth: true
      }
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

// router.beforeEach(async (to, from, next) => {
//     if (store.state.isAuthenticated) {
//         store.commit("verifyRefreshToken")
//     } else {
//         next({name: 'login'});
//     }

//     next()
// })

router.beforeEach((to, from, next) => {
    // store.commit('verifyRefreshToken')
    if (store.state.isAuthenticated) {
        store.commit('verifyRefreshToken')
    }
    if (to.matched.some(record => record.meta.requiresAuth)) {
      // this route requires auth, check if logged in
      // if not, redirect to login page.
      if (!store.state.isAuthenticated) {
        next({ name: 'login' })
      } else {
        next() // go to wherever I'm going
      }
    } else {
      next() // does not require auth, make sure to always call next()!
    }
})

export default router