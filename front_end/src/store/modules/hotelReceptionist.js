import {
    getHotelOrdersAPI,
    updateOrderStateAPI,
} from '@/api/order'
import { message } from 'ant-design-vue'
const hotelReceptionist = {
    state: {
        receptionistOrderList: [],
        managingHotel:'',
    },
    mutations: {
        set_receptionistOrderList: function (state, data) {
            state.receptionistOrderList = data
        },
        set_currentOrder:function(state,data){
            state.currentOrder=data
        },
        set_updateOrderStateVisible: function(state, data) {
            state.updateOrderStateVisible = data
        },
    },
    actions: {
        getHotelOrders: async({ state, commit },hotelId) => {
            const res = await getHotelOrdersAPI(hotelId)
            if(res){
                commit('set_receptionistOrderList', res)
                console.log(state.receptionistOrderList)
            }
        },
        set_managingHotel: (state, data) => {
            state.managingHotel= data
        },
        updateOrderState: async ({state,dispatch},data)=>{
            const params={
                id:state.id,
                ...data,
            }
            const res=await updateOrderStateAPI(params)
            if(res){
                message.success('修改成功')
                dispatch('getHotelOrders')
            }else{
                message.error('修改失败')
            }
        },
    }
}
export default hotelReceptionist