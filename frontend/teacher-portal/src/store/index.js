import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user:  null,
    accessToken: null,
    refreshToken: null,
    isAuthenticated: false,
  },
  getters: {
    isLoggedIn(state) {
      return state.isAuthenticated;
    },
  },
  mutations: {
    async refreshTokens(state) {
      var refreshToken = state.refreshToken;
      const formData = {
        refresh: refreshToken
      }
      await axios
        .post('/token/refresh/', formData, {

        })
        .then(response => {
          var accessToken = response.data.access
          state.accessToken = accessToken
          state.refreshToken = refreshToken
          state.isAuthenticated = true
        })
        .catch(error => {
          console.log(JSON.stringify(error))
        })
    },
    async verifyRefreshToken(state) {
      const formData = {
        token: state.refreshToken,
      }
      await axios
        .post('/token/verify/', formData, {
          
        })
        .then(response => {
          if (Object.keys(response.data).length === 0) { // Если размер возвращаемых данных равен нулю, то токен действителен, обновляем access токен
            this.commit('refreshTokens')
          } else { // Если в возвращаемых данных есть хоть что-то, значит токен не действителен, удаляем все и просим пользователя пройти на страничку авторизации
            state.accessToken = null;
            state.refreshToken = null;
            state.isAuthenticated = false;
          }
        })
    },
    setUser(state, user) {
      state.user = user
    },
    setTokens(state, tokens) {
      state.accessToken = tokens.access
      state.refreshToken = tokens.refresh
      state.isAuthenticated = true
    },
    removeTokens(state) {
      state.accessToken = null
      state.refreshToken = null
      state.isAuthenticated = false
    }
  },
  actions: {
  },
  modules: {
  },
  plugins: [
    createPersistedState(),
  ],
})
