<template>
    <div>
    <a-modal
            :visible="receptionistVisible"
            title="酒店前台"
            width="900px"
            :footer="null"
            @cancel="cancel"
    >
        <div style="width: 100%; text-align: right; margin: 20px 0">
            <a-button type="primary" @click="addReceptionist">
                <a-icon type="plus" />添加酒店前台
            </a-button>
        </div>
        <a-table
                :columns="columns"
                :dataSource="receptionistList"
                bordered
        >
        <span slot="action" slot-scope="record">
                <a-popconfirm
                        title="确定想删除该酒店前台吗？"
                        @confirm="deleteReceptionist(record)"
                        okText="确定"
                        cancelText="取消"
                >
                    <a-button type="danger" size="small">删除</a-button>
                </a-popconfirm>
            </span>
        </a-table>
    </a-modal>
        <AddReceptionist></AddReceptionist>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import AddReceptionist from "./addReceptionist";
    const columns=[
        {
            title: '编号',
            dataIndex: 'id'
        },
        {
            title: '用户邮箱',
            dataIndex: 'email'
        },
        {
            title: '密码',
            dataIndex: 'password'
        },
        {
            title: '电话号码',
            dataIndex: 'phoneNumber'
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "Receptionist",
        data() {
            return {
                columns
            }
        },
        components: {
            AddReceptionist

        },
        computed: {
            ...mapGetters([
                'receptionistVisible',
                'receptionistList',
            ])
        },
        methods: {
            ...mapMutations([
                'set_receptionistVisible',
                'set_addReceptionistVisible'
            ]),
            // 从store里面获取数据
            ...mapActions([
                'getReceptionistList',
                'delReceptionist'
            ]),
            cancel() {
                this.set_receptionistVisible(false)
            },
            addReceptionist() {
                    this.set_addReceptionistVisible(true),
                    this.set_receptionistVisible(false)
            },
            deleteReceptionist(record){
                this.delReceptionist(record)
            },
        },
    }
</script>

<style scoped>

</style>