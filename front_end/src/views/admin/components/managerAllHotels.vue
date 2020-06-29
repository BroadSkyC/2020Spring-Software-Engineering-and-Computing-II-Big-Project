<template>
    <a-modal
            :visible="managerAllHotelsVisible"
            title="旗下酒店"
            width="900px"
            @cancel="cancel"
            :footer="null"
    >
        <a-table
            :columns="columns1"
            :dataSource="hotelList"
            bordered
        >
        </a-table>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    const columns1 = [
        {
            title: '酒店名',
            dataIndex: 'name',
        },
        {
            title: '商圈',
            dataIndex: 'bizRegion',
        },
        {
            title: '地址',
            dataIndex: 'address',
        },
        {
            title: '酒店星级',
            filters: [{ text: '三星级', value: 'Three' }, { text: '四星级', value: 'Four' }, { text: '五星级', value: 'Five' }],
            onFilter: (value, record) => record.hotelStar.includes(value),
            dataIndex: 'hotelStar',
            scopedSlots: { customRender: 'hotelStar' }
        },
        {
            title: '评分',
            sorter:(a,b)=>a.rate-b.rate,
            dataIndex: 'rate',
        },
        {
            title: '联系电话',
            dataIndex: 'phoneNum',
        },
    ]
    export default {
        name: "managerAllHotels",
        data() {
            return {
                formItemLayout: {
                    labelCol: {
                        xs: { span: 12 },
                        sm: { span: 6 },
                    },
                    wrapperCol: {
                        xs: { span: 24 },
                        sm: { span: 16 },
                    },
                },
                columns1,
            }
        },
        computed: {
            ...mapGetters([
                'managerAllHotelsVisible',
                'hotelList',
            ])
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_managerAllHotelsVisible',
            ]),
            ...mapActions([
                'getManagerHotelList',
            ]),
            cancel(){
                this.set_managerAllHotelsVisible(false)
            },
    }}
</script>

<style scoped>

</style>