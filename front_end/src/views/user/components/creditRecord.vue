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
            dataIndex: 'orderId',
        },
        {
            title: '操作',
            dataIndex: 'action',
            scopedSlots: { customRender: 'action' }
        },
        {
            title: '信用值变化',
            dataIndex: 'creditChange',
        },
        {
            title: '当前信用值',
            dataIndex: 'currentCredit',
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
        },
        computed: {
            ...mapGetters([
                'userId',
                'creditRecordVisible',
                'userCreditRecord',
            ])
        },
        methods:{
            ...mapMutations([
                'set_creditRecordVisible'
            ]),
            ...mapActions([
            ]),
            cancel(){
                this.set_creditRecordVisible(false)
            },
        }
    }

</script>

<style scoped>

</style>