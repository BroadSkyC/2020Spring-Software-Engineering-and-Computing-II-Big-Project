import {
    addRoomAPI,
    modifyRoomAPI,
    addHotelAPI,
    delHotelAPI,
    delRoomAPI,
} from '@/api/hotelManager'
import{
    hotelAllReceptionistAPI,
    delReceptionistAPI,
    addReceptionistAPI
} from '@/api/admin'
import {
    getAllOrdersAPI,
    updateOrderAPI,
    updateOrderStateAPI,
    delOrderAPI,
    getManageOrdersAPI
} from '@/api/order'
import {
    hotelAllCouponsAPI,
    hotelTargetMoneyAPI,
    hotelTimeAPI,
    hotelTargetRoomNumAPI,
    hotelBirthdayAPI,
    delCouponAPI
} from '@/api/coupon'
import { message } from 'ant-design-vue'


const hotelManager = {

    state: {
        orderList: [],
        receptionistList: [],
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'',
            hotelStar:'',
            rate: '',
            description:'',
            phoneNum:'',
            managerId:'',
            imgUrl: ''
        },
        currentOrder:{

        },
        addHotelModalVisible: false,
        addRoomParams: {
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
            beginDate:'',
            endDate:'',
        },
        updateRoomParams:{
            roomType: '',
            hotelId: '',
            price: '',
            total: 0,
            curNum: 0,
            id:'',
        },
        addReceptionistParams: {
            email:'',
            password:'',
            hotelId:'',
        },
        addRoomModalVisible: false,
        couponVisible: false,
        receptionistVisible:false,
        modifyRoomVisible:false,
        addCouponVisible: false,
        addReceptionistVisible:false,
        orderVisible:false,
        updateOrderStateVisible:false,
        changeHotelInfoVisible:false,
        activeHotelId: 0,
        couponList: [],
    },
    mutations: {
        set_orderList: function(state, data) {
            state.orderList = data
        },
        set_addHotelModalVisible: function(state, data) {
            state.addHotelModalVisible = data
        },
        set_addHotelParams: function(state, data) {
            state.addHotelParams = {
                ...state.addHotelParams,
                ...data,
            }
        },
        set_addRoomModalVisible: function(state, data) {
            state.addRoomModalVisible = data
        },
        set_addRoomParams: function(state, data) {
            state.addRoomParams = {
                ...state.addRoomParams,
                ...data,
            }
        },
        set_addReceptionistParams: function(state, data) {
            state.addReceptionistParams = {
                ...state.addReceptionistParams,
                ...data,
            }
        },
        set_updateRoomParams:function(state, data) {
            state.updateRoomParams = {
                ...state.updateRoomParams,
                ...data,
            }
        },
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_orderVisible: function(state, data) {
            state.orderVisible = data
        },
        set_receptionistVisible:function(state, data) {
            state.receptionistVisible = data
        },
        set_updateOrderStateVisible: function(state, data) {
            state.updateOrderStateVisible = data
        },
        set_changeHotelInfoVisible:function(state,data){
            state.changeHotelInfoVisible=data
        },
        set_modifyRoomVisible:function(state,data){
            state.modifyRoomVisible=data
        },
        set_currentOrder:function(state,data){
            state.currentOrder=data
        },
        set_activeHotelId: function(state, data) {
            state.activeHotelId = data
        },
        set_couponList: function(state, data) {
            state.couponList = data
        },
        set_receptionistList: function(state, data) {
            state.receptionistList = data
        },
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible = data
        },
        set_addReceptionistVisible: function(state, data) {
            state.addReceptionistVisible = data
        },
    },
    actions: {
        getAllOrders: async ({state, commit}) => {
            const res = await getAllOrdersAPI()
            if (res) {
                commit('set_orderList', res)
            }
        },
        getManageOrders:async ({state, commit},userId) => {
            const res = await getManageOrdersAPI(userId)
            if (res) {
                commit('set_orderList', res)
            }
        },
        addHotel: async ({state, dispatch, commit,getters}) => {
            const res = await addHotelAPI(state.addHotelParams)
            if (res) {
                console.log(getters.userId)
                dispatch('getManagerHotelList',getters.userId)
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion: '',
                    hotelStar: '',
                    rate: '',
                    description: '',
                    phoneNum: '',
                    managerId: '',
                    imgUrl: '',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        addReceptionist: async ({state, commit, dispatch}) => {
            const res = await addReceptionistAPI(state.addReceptionistParams)
            if (res) {
                commit('set_addReceptionistParams', {
                    email: '',
                    password: '',
                    hotelId: '',
                    phoneNum:''
                })
                commit('set_addReceptionistVisible', false)
                commit('set_receptionistVisible',true)
                message.success('添加成功')
                dispatch('getReceptionistList')
            } else {
                message.error('添加失败')
            }
        },
        addRoom: async ({state, dispatch, commit}) => {
            const res = await addRoomAPI(state.addRoomParams)
            if (res) {
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                    beginDate:'',
                    endDate:'',
                })
                message.success('添加成功')
            } else {
                message.error('添加失败')
            }
        },
        updateRoomInfo: async ({state, dispatch, commit}) => {
            const res = await modifyRoomAPI(state.updateRoomParams)
            if (res) {
                commit('set_modifyRoomVisible', false)
                commit('set_updateRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                    id:'',
                })
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
        },
        getHotelCoupon: async ({state, commit}) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if (res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }
        },
        getReceptionistList: async ({state, commit}) => {
            const res = await hotelAllReceptionistAPI(state.activeHotelId)
            if (res) {
                commit('set_receptionistList', res)
            }
        },
        addHotelCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTargetMoneyCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTimeCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTimeAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTargetRoomNumCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelTargetRoomNumAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addBirthdayCoupon: async ({commit, dispatch}, data) => {
            const res = await hotelBirthdayAPI(data)
            if (res) {
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            } else {
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        delHotel: async ({dispatch, getters},data) => {
            const res = await delHotelAPI(data)
            if (res) {
                dispatch('getManagerHotelList',getters.userId)
                message.success('删除成功')

            } else {
                message.error('删除失败')
            }
        },
        delOrder: async ({dispatch, getters},data) => {
            const res = await delOrderAPI(data)
            if (res) {
                //dispatch('getHotelList')
                message.success('删除成功')
                dispatch('getManageOrders',getters.userId)
            } else {
                message.error('删除失败')
            }
        },
        delCoupon: async ({commit, dispatch}, data) => {
            const res = await delCouponAPI(data)
            if (res) {
                message.success('删除成功')
                commit('set_couponVisible', false)
                // window.location.reload();
                dispatch('getHotelCoupon')
            } else {
                message.error('删除失败')
            }
        },
        delReceptionist: async ({commit, dispatch}, data) => {
            const res = await delReceptionistAPI(data)
            if (res) {
                message.success('删除成功')
                // window.location.reload();
                dispatch('getReceptionistList')
            } else {
                message.error('删除失败')
            }
        },
        updateOrderInfo: async ({state, dispatch}, data) => {
            const params = {
                id: state.id,
                ...data,
            }
            const res = await updateOrderAPI(params)
            if (res) {
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
        updateOrderState: async ({state, dispatch,commit,getters}, data) => {
            const params = {
                id: state.id,
                ...data,
            }
            const res = await updateOrderStateAPI(params)
            if (res) {
                message.success('修改成功')
                commit("set_updateOrderStateVisible",false)
                dispatch('getManageOrders',getters.userId)
            } else {
                message.error('修改失败')
            }
        },
        delRoom: async ({dispatch},data) => {
            const res = await delRoomAPI(data)
            if (res) {
                dispatch('getHotelById')
                message.success('删除成功')

            } else {
                message.error('删除失败')
            }
        },
    }
}
export default hotelManager