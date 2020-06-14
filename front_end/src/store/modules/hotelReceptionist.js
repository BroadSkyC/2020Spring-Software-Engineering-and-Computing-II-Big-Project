import {
    getHotelOrdersAPI,
    updateOrderStateAPI,
} from '@/api/order'
import { message } from 'ant-design-vue'
const hotelReceptionist = {
    state: {
        receptionistOrderList: [],
        updateOrderStatteVisible:false,
        currentOrrder:{

        },
    },
    mutations: {
        set_receptionistOrderList: function (state, data) {
            state.receptionistOrderList = data
        },
        set_currentOrrder:function(state,data){
            state.currentOrrder=data
        },
        set_updateOrderStatteVisible: function(state, data) {
            state.updateOrderStatteVisible = data
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
        updateOrderStatte: async ({state,dispatch,commit},data)=>{
            const params={
                id:state.id,
                ...data,
            }
            const res=await updateOrderStateAPI(params)
            if(res){
                message.success('修改成功')
                commit('set_updateOrderStatteVisible', false)
                window.location.reload();
                // dispatch('getHotelOrders',this.currentOrrder.hotelId)
                // window.location.reload();
            }else{
                message.error('修改失败')
            }
        },
    }
}
export default hotelReceptionist