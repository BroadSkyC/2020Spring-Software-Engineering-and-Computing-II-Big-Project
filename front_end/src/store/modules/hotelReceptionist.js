import {
    getHotelOrdersAPI,
    updateOrderStateAPI,
} from '@/api/order'
import { message } from 'ant-design-vue'
const hotelReceptionist = {
    state: {
        receptionistOrderList: [],
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
        getHotelOrders: async({ state, commit }) => {
            const data = {
                hotelId: Number(state.managingHotel)
            }
            const res = await getHotelOrdersAPI(data)
            if(res){
                commit('set_receptionistOrderList', res)
                console.log(state.receptionistOrderList)
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
                dispatch('getHotelOrders')
            }else{
                message.error('修改失败')
            }
        },
    }
}
export default hotelReceptionist