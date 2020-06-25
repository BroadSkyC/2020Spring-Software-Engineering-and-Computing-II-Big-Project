import { message } from 'ant-design-vue'
import store from '@/store'
import {
    getHotelsAPI,
    getHotelByIdAPI,
    getManagerHotelsAPI,
    updateCurrentHotelInfoAPI,
} from '@/api/hotel'
import {
    reserveHotelAPI
} from '@/api/order'
import {
    orderMatchCouponsAPI,
} from '@/api/coupon'

const hotel = {
    state: {
        hotelList: [
            
        ],
        hotelListParams: {
            pageNo: 0,
            pageSize: 12
        },
        hotelListLoading: true,
        currentHotelId: '',
        visitCount: 0,
        currentHotelInfo: {

        },
        orderModalVisible: false,
        chooseDateVisible: false,
        currentOrderRoom: {

        },
        orderMatchCouponList: [

        ],
        checkInDate:'',
        checkOutDate:'',
        allRooms:[],
        dateParams:{

        },
        roomNums: 1,
    },
    mutations: {
        set_hotelList: function(state, data) {
            state.hotelList = data
        },
        set_hotelListParams: function(state, data) {
            state.hotelListParams = {
                ...state.hotelListParams,
                ...data,
            }
        },
        set_hotelListLoading: function(state, data) {
            state.hotelListLoading = data
        },
        set_currentHotelId: function(state, data) {
            state.currentHotelId = data
        },
        set_currentHotelInfo: function(state, data) {
            state.currentHotelInfo = {
                ...state.currentHotelInfo,
                ...data,
            }
        },
        set_checkIndate: function(state, data) {
            state.checkInDate= data
        },
        set_allRooms:function(state, data) {
            state.allRooms= data
        },
        set_visitCount: function(state, data) {
            state.visitCount= data
        },
        set_checkOutdate: function(state, data) {
            state.checkOutDate= data
        },
        clear_rooms:function(state){
            console.log("clear all")
            state.currentHotelInfo.rooms=[];
        },
        set_orderModalVisible: function(state, data) {
            state.orderModalVisible = data
        },
        set_chooseDateVisible: function(state, data) {
            state.chooseDateVisible = data
        },
        set_currentOrderRoom: function(state, data) {
            state.currentOrderRoom = {
                ...state.currentOrderRoom,
                ...data,
            }
        },
        set_orderMatchCouponList: function(state, data) {
            state.orderMatchCouponList = data
        },
        set_currentHotelInfoRooms:function (state,data) {
            state.currentHotelInfo.rooms=data
        },
        set_dateParams: function(state, data) {
            state.dateParams = {
                ...state.dateParams,
                ...data,
            }
        },
        set_roomNums:function (state) {
            state.roomNums=1
        },
        add_roomNums:function (state){
            state.roomNums++
        },
        sub_roomNums:function (state){
            state.roomNums--
        }
    },

    actions: {
        getHotelList: async({commit, state}) => {
            const res = await getHotelsAPI()
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getManagerHotelList:async({commit, state},userId) => {
            console.log(userId)
            const res=await getManagerHotelsAPI(userId)
            if(res){
                commit('set_hotelList', res)
                commit('set_hotelListLoading', false)
            }
        },
        getHotelById: async({commit, state}) => {
            const res = await getHotelByIdAPI({
                hotelId: state.currentHotelId
            })
            if(res){
                commit('set_currentHotelInfo', res)
                if(state.visitCount===0) {
                       commit('set_allRooms',state.currentHotelInfo.rooms)
                       console.log(state.allRooms)
                       commit('clear_rooms')
                }
            }
        },
        addOrder: async({ state, commit,dispatch }, data) => {
            const res = await reserveHotelAPI(data)
            const res1 = await updateCurrentHotelInfoAPI(state.dateParams)
            console.log(res)
            if(res){
                message.success('预定成功')
                commit('set_orderModalVisible', false)
                //dispatch('updateCurrentHotelInfo',state.dateParams)
                commit('set_currentHotelInfo', res1)
                commit('set_roomNums')
            }
        },
        getOrderMatchCoupons: async({ state, commit }, data) => {
            const res = await orderMatchCouponsAPI(data)
            if(res){
                commit('set_orderMatchCouponList', res)
            }
        },
        updateCurrentHotelInfo: async ({state, commit}, data) => {
            const res = await updateCurrentHotelInfoAPI(data)
            if (res) {
                message.success('设置成功')
                commit("set_chooseDateVisible",false)
                commit('set_currentHotelInfo', res)
                commit('set_dateParams',data)
            } else {
                message.error('修改失败')
            }
        },

    }
}

export default hotel