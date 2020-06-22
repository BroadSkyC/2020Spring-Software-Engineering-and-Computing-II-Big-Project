<template>
    <a-modal
            :visible="creditRecordVisible"
            title="信用记录"
            width="900px"
            @cancel="cancel"
            :footer="null"
    >
        <a-table
                :columns="columns"
                :dataSource="userCreditRecord"
                bordered
        ></a-table>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const columns = [
        {
            title: '订单号',
            dataIndex: 'hotelName',
        },
        {
            title: '操作',
            dataIndex: 'roomType',
            scopedSlots: { customRender: 'roomType' }
        },
        {
            title: '信用值变化',
            dataIndex: 'checkInDate',
        },
        {
            title: '剩余信用值',
            dataIndex: 'checkOutDate',
        },
    ];

    export default {
        name: "creditRecord",
        data(){
            return {
                columns
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'creditRecord' });
        },
        async mounted() {
            await this.getUserOrders()
            await this.getUserCreditRecord()
        },
        computed: {
            ...mapGetters([
                'userId',
                'creditRecordVisible',
                'userOrderList',
                'userCreditRecord',
            ])
        },
        methods:{
            ...mapMutations([
                'set_creditRecordVisible'
            ]),
            ...mapActions([
                'getUserCreditRecord'
            ]),
            cancel(){
                this.set_creditRecordVisible(false)
            },
        }
    }

</script>

<style scoped>

</style>