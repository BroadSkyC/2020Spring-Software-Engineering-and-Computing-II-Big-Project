import { axios } from '@/utils/request'
const api = {
    adminPre: '/api/admin'
}
export function getManagerListAPI(){
    return axios({
        url: `${api.adminPre}/getManagerList`,
        method: 'GET'
    })
}
export function addManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/addManager`,
        method: 'POST',
        data
    })
}
export function delManagerAPI(data) {
    return axios({
        url: `${api.adminPre}/delManager`,
        method: 'POST',
        data
    })
}
export function  hotelAllReceptionistAPI(hotelId) {
    return axios({
        url: `${api.adminPre}/${hotelId}/hotelAllReceptionist`,
        method: 'GET',
    })
}
export function delReceptionistAPI(data) {
    return axios({
        url:`${api.adminPre}/delReceptionist`,
        method:'POST',
        data,
    })
}
export function addReceptionistAPI(data) {
    return axios({
        url: `${api.adminPre}/addReceptionist`,
        method: 'POST',
        data
    })
}