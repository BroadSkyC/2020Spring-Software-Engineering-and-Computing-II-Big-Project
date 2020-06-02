import {
    addRoomAPI,
    addHotelAPI,
    delHotelAPI,
    changeHotelInfoAPI,
} from '@/api/hotelManager'
import {
    getAllOrdersAPI,
    updateOrderAPI,
    updateOrderStateAPI,
    delOrderAPI
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
        addHotelParams: {
            name: '',
            address: '',
            bizRegion:'',
            hotelStar:'',
            rate: '',
            description:'',
            phoneNum:'',
            managerId:'',
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
        },
        addRoomModalVisible: false,
        couponVisible: false,
        addCouponVisible: false,
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
        set_couponVisible: function(state, data) {
            state.couponVisible = data
        },
        set_orderVisible: function(state, data) {
            state.orderVisible = data
        },
        set_updateOrderStateVisible: function(state, data) {
            state.updateOrderStateVisible = data
        },
        set_changeHotelInfoVisible:function(state,data){
            state.changeHotelInfoVisible=data
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
        set_addCouponVisible: function(state, data) {
            state.addCouponVisible = data
        }
    },
    actions: {
        getAllOrders: async({ state, commit }) => {
            const res = await getAllOrdersAPI()
            if(res){
                commit('set_orderList', res)
            }
        },
        addHotel: async({ state, dispatch, commit }) => {
            const res = await addHotelAPI(state.addHotelParams)
            if(res){
                dispatch('getHotelList')
                commit('set_addHotelParams', {
                    name: '',
                    address: '',
                    bizRegion:'',
                    hotelStar:'',
                    rate: '',
                    description:'',
                    phoneNum:'',
                    managerId:'',
                })
                commit('set_addHotelModalVisible', false)
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        addRoom: async({ state, dispatch, commit }) => {
            const res = await addRoomAPI(state.addRoomParams)
            if(res){
                commit('set_addRoomModalVisible', false)
                commit('set_addRoomParams', {
                    roomType: '',
                    hotelId: '',
                    price: '',
                    total: 0,
                    curNum: 0,
                })
                message.success('添加成功')
            }else{
                message.error('添加失败')
            }
        },
        getHotelCoupon: async({ state, commit }) => {
            const res = await hotelAllCouponsAPI(state.activeHotelId)
            if(res) {
                // 获取到酒店策略之后的操作（将获取到的数组赋值给couponList）
                commit('set_couponList', res)
            }
        },
        addHotelCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTargetMoneyCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetMoneyAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTimeCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTimeAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addHotelTargetRoomNumCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelTargetRoomNumAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        addBirthdayCoupon: async({ commit, dispatch }, data) => {
            const res = await hotelBirthdayAPI(data)
            if(res){
                // 添加成功后的操作（提示文案、modal框显示与关闭，调用优惠列表策略等）
                dispatch('getHotelCoupon')
                commit('set_addCouponVisible', false)
                commit('set_couponVisible', false)
                message.success("添加策略成功")
            }else{
                // 添加失败后的操作
                message.error('添加失败')
            }
        },
        delHotel: async(dispatch ,data) => {
            const res = await delHotelAPI(data)
            if(res){
                //dispatch('getHotelList')
                message.success('删除成功')
                window.location.reload();
                dispatch('getHotelList')
            }else{
                message.error('删除失败')
            }
        },
        delOrder: async(dispatch ,data) => {
            const res = await delOrderAPI(data)
            if(res){
                //dispatch('getHotelList')
                message.success('删除成功')
                window.location.reload();
                dispatch('getAllOrders')
            }else{
                message.error('删除失败')
            }
        },
        delCoupon: async(dispatch ,data) => {
            const res = await delCouponAPI(data)
            if(res){
                message.success('删除成功')
                window.location.reload();
                dispatch('getHotelCoupon')
            }else{
                message.error('删除失败')
            }
        },
        updateOrderInfo: async({ state, dispatch }, data) => {
            const params = {
                id: state.id,
                ...data,
            }
            const res = await updateOrderAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getUserInfo')
            }
        },
         updateOrderState: async ({state,dispatch},data)=>{
            const params={
                id:state.id,
                ...data,
            }
            const res=await updateOrderStateAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getAllOrders')
            }else{
                message.error('修改失败')
            }
         },
        changeHotelInfo: async ({state,dispatch},data)=>{
            const params={
                id:state.id,
                ...data,
            }
            const res=await changeHotelInfoAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getHotelList')
            }else{
                message.error('修改失败')
            }
        },
    },

}
export default hotelManager