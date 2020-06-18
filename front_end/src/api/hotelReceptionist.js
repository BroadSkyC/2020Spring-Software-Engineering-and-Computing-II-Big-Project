import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function  hotelAllReceptionistAPI(hotelId) {
    return axios({
        url: `${api.hotelPre}/hotelAllReceptionist`,
        method: 'GET',
        params: {hotelId: hotelId},
    })
}
export function delReceptionistAPI(data) {
    return axios({
        url:`${api.hotelPre}/delReceptionist`,
        method:'POST',
        data,
    })
}
export function addReceptionistAPI(data) {
    return axios({
        url: `${api.hotelPre}/addReceptionist`,
        method: 'POST',
        data
    })
}