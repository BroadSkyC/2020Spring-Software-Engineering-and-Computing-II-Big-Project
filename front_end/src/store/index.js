import Vue from 'vue'
import Vuex from 'vuex'
import user from './modules/user'
import hotel from './modules/hotel'
import hotelManager from './modules/hotelManager'
import admin from './modules/admin'
import hotelReceptionist from './modules/hotelReceptionist'
import getters from './getters'
Vue.use(Vuex)

export default new Vuex.Store({
  modules: {
    hotel,
    user,
    hotelManager,
    admin,
    hotelReceptionist
  },
  state: {
  },
  mutations: {
  },
  actions: {
  },
  getters
})
