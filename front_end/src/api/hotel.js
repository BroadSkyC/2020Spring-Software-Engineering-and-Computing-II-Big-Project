import { axios } from '@/utils/request'
const api = {
    hotelPre: '/api/hotel'
}
export function getHotelsAPI() {
    return axios({
        url: `${api.hotelPre}/all`,
        method: 'GET',
    })
}
export function getHotelByIdAPI(param) {
    return axios({
        url: `${api.hotelPre}/${param.hotelId}/detail`,
        method: 'GET',
    })
}
export function getManagerHotelsAPI(userId) {
    return axios({
        url: `${api.hotelPre}/${userId}/getManagerHotels`,
        method: 'GET',
    })
}
export function updateCurrentHotelInfoAPI(data) {
    return axios({
        url: `${api.hotelPre}/modifyRoom`,
        method: 'POST',
        data,
    })
}
