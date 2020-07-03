<template>
    <div class="manageUser-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店经理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addManagerModal"><a-icon type="plus" />添加经理</a-button>
                </div>
                <a-table
                    :columns="columns"
                    :dataSource="managerList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showManagerHotelList(record.id)">查看旗下酒店</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该经理吗？"
                                @confirm="deleteManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                        <a-button type="danger" size="small">删除经理</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="住房用户" key="2">
                <a-table
                        :columns="columns1"
                        :dataSource="userList"
                        bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showManagerHotelList(record.id)">查看旗下酒店</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                                title="确定想删除该经理吗？"
                                @confirm="deleteManager(record)"
                                okText="确定"
                                cancelText="取消"
                        >
                        <a-button type="danger" size="small">删除经理</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <AddManagerModal></AddManagerModal>
        <ManagerAllHotels></ManagerAllHotels>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddManagerModal from './components/addManagerModal'
import ManagerAllHotels from './components/managerAllHotels'
const columns = [
    {
        title: '用户ID',
        dataIndex: 'id',
    },
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {  
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户密码',
        dataIndex: 'password',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
const columns1 = [
    {
        title: '用户ID',
        dataIndex: 'id',
    },
    {
        title: '用户邮箱',
        dataIndex: 'email',
    },
    {
        title: '用户名',
        dataIndex: 'userName',
    },
    {
        title: '用户信用值',
        dataIndex: 'credit',
    },
    {
        title: '用户手机号',
        dataIndex: 'phoneNumber',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];
export default {
    name: 'manageUser',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns,
            columns1,
            data: [],
            form: this.$form.createForm(this, { name: 'manageUser' }),
        }
    },
    components: {
        AddManagerModal,
        ManagerAllHotels,
    },
    computed: {
        ...mapGetters([
            'addManagerModalVisible',
            'managerList',
            'hotelList',
            'userList'
        ])
    },
    async mounted() {
        await this.getManagerList()
        await this.getUserList()
        console.log(this.userList)
        console.log(this.managerList)
    },
    methods: {
        ...mapMutations([
            'set_addManagerModalVisible',
            'set_managerAllHotelsVisible'
        ]),
        ...mapActions([
            'getManagerList',
            'delManager',
            'getManagerHotelList',
            'getUserList'
        ]),
        addManagerModal(){
            this.set_addManagerModalVisible(true)

        },
        deleteManager(record){
            this.delManager(record)
        },
        showManagerHotelList(id){
            this.set_managerAllHotelsVisible(true)
            this.getManagerHotelList(id)
        }
    }
}
</script>
<style scoped lang="less">
    .manageUser-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .manageUser-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>