import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function addRoomAPI(data) {
    return axios({
        url: `${api.hotelPre}/roomInfo`,
        method: 'POST',
        data,
    })
}
export function addHotelAPI(data) {
    return axios({
        url: `${api.hotelPre}/addHotel`,
        method: 'POST',
        data,
    })
}
export function delHotelAPI(data) {
    return axios({
        url:`${api.hotelPre}/delHotel`,
        method:'POST',
        data,
    })
}
export function delOrderAPI(data) {
    return axios({
        url:`${api.hotelPre}/delOrder`,
        method:'POST',
        data,
    })
}
