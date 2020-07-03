import {
    getManagerListAPI,
    addManagerAPI,
    delManagerAPI,
    getClientListAPI
} from '@/api/admin'
import { message } from 'ant-design-vue'

const admin = {
    state: {
        managerList: [

        ],
        userList:[

        ],
        addManagerModalVisible: false,
        managerAllHotelsVisible:false,
        addManagerParams: {
            email:'',
            password:'',
            userName:'',
            phoneNumber:''
        }
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_userList: function(state, data) {
            state.userList = data
        },
        set_addManagerModalVisible: function(state, data) {
            state.addManagerModalVisible = data
        },
        set_addManagerParams: function(state, data) {
            state.addManagerParams = {
                ...state.addManagerParams,
                ...data,
            }
        },
        set_managerAllHotelsVisible: function(state, data) {
            state.managerAllHotelsVisible = data
        },
    },
    actions: {
        getManagerList: async ({commit}) => {
            const res = await getManagerListAPI()
            if (res) {
                commit('set_managerList', res)
            }
        },
        getUserList: async ({commit}) => {
            const res = await getClientListAPI()
            console.log(res)
            if (res) {
                commit('set_userList', res)
            }
        },
        addManager: async ({state, commit, dispatch}) => {
            const res = await addManagerAPI(state.addManagerParams)
            if (res) {
                commit('set_addManagerParams', {
                    email: '',
                    password: '',
                    userName:'',
                    phoneNumber:''
                })
                commit('set_addManagerModalVisible', false)
                message.success('添加成功')
                dispatch('getManagerList')
            } else {
                message.error('添加失败')
            }
        },
        delManager: async ({dispatch}, data) => {
            const res = await delManagerAPI(data)
            if (res) {
                //dispatch('getHotelList')
                message.success('删除成功')
                dispatch('getManagerList')
            } else {
                message.error('删除失败')
            }
        }
    },
}
export default admin