import {
    getManagerListAPI,
    addManagerAPI,
    delManagerAPI,
    getClientListAPI
} from '@/api/admin'
import {
    modifyPasswordAPI
} from '@/api/user'
import { message } from 'ant-design-vue'
import modifyPassword from "../../views/admin/components/modifyPassword";

const admin = {
    state: {
        managerList: [

        ],
        userList:[

        ],
        addManagerModalVisible: false,
        managerAllHotelsVisible:false,
        modifyPasswordVisible:false,
        addManagerParams: {
            email:'',
            password:'',
            userName:'',
            phoneNumber:''
        },
        activeUserId:'',
    },
    mutations: {
        set_managerList: function(state, data) {
            state.managerList = data
        },
        set_userList: function(state, data) {
            state.userList = data
        },
        set_activeUserId: function(state, data) {
            state.activeUserId = data
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
        set_modifyPasswordVisible: function(state, data) {
            state.modifyPasswordVisible = data
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
                dispatch('getUserList')
            } else {
                message.error('删除失败')
            }
        },
        updatePassword: async ({state, dispatch, commit},data) => {
            const res = await modifyPasswordAPI(data)
            if (res) {
                commit('set_modifyPasswordVisible', false)
                dispatch('getManagerList')
                dispatch('getUserList')
                message.success('修改成功')
            } else {
                message.error('修改失败')
            }
        },
    },
}
export default admin