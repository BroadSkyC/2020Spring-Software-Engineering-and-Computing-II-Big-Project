import { axios } from '@/utils/request'
const api = {
    orderPre: '/api/order'
}
export function reserveHotelAPI(data) {
    return axios({
        url: `${api.orderPre}/addOrder`,
        method: 'POST',
        data,
    })
}
export function getAllOrdersAPI() {
    return axios({
        url: `${api.orderPre}/getAllOrders`,
        method: 'GET',
    })
}
export function getUserOrdersAPI(data) {
    return axios({
        url: `${api.orderPre}/${data.userId}/getUserOrders`,
        method: 'GET',
    })
}
export function cancelOrderAPI(orderId) {
    return axios({
        url: `${api.orderPre}/${orderId}/annulOrder`,
        method: 'GET',
    })
}
export function delOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/delOrder`,
        method: 'POST',
        data,
    })
}
export function updateOrderAPI(data) {
    return axios({
        url: `${api.orderPre}/updateOrder`,
        method: 'POST',
        data,
    })
}
export function updateCommentAPI(data) {
    return axios({
        url: `${api.orderPre}/updateComment`,
        method: 'POST',
        data,
    })
}
export function updateOrderStateAPI(data) {
    return axios({
        url: `${api.orderPre}/updateOrderState`,
        method: 'POST',
        data,
    })
}
export function getHotelOrdersAPI(hotelId) {
    return axios({
        url: `${api.orderPre}/${hotelId}/getHotelOrders`,
        method: 'GET',
    })
}
export function getManageOrdersAPI(userId) {
    return axios({
        url: `${api.orderPre}/${userId}/getManageOrders`,
        method: 'GET',
    })
}