import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'

import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'

import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

import axios from 'axios'

axios.defaults.baseURL = 'https://83.221.202.194:80/'

// axios.defaults.xsrfHeaderName = "X_CSRFTOKEN"
// axios.defaults.xsrfCookieName = "csrftoken"
// axios.defaults.headers.common['Access-Control-Allow-Origin'] = '*';

import VueCookies from 'vue-cookies'

Vue.use(VueCookies, {expire: '7d'})

Vue.config.productionTip = false

new Vue({
  axios,
  router,
  store,
  render: h => h(App)
}).$mount('#app')
