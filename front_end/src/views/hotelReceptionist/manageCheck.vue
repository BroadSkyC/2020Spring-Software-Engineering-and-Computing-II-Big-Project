<template>
    <div class="manageCheck-wrapper">
        <a-table
                :columns="columns2"
                :dataSource="receptionistOrderList"
                bordered
        >
            <span slot="price" slot-scope="text">
                <span>￥{{ text }}</span>
            </span>
            <a-tag slot="orderState" color="blue" slot-scope="text">
                {{ text }}
            </a-tag>
            <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
            <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showchangeStatte(record)">更改状态</a-button>
                    </span>
        </a-table>
        <ChangeStatte></ChangeStatte>
    </div>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import ChangeStatte from './components/changeStatte'
    const moment = require('moment')
    const columns2 = [
        {
            title: '订单号',
            sorter:(a,b)=>a.id-b.id,
            dataIndex: 'id',
        },
        {
            title: '酒店名',
            sorter:(a,b)=>{return a.hotelName.localeCompare(b.hotelName)},
            dataIndex: 'hotelName',
        },
        {
            title: '房型',
            filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' }],
            onFilter: (value, record) => record.roomType.includes(value),
            dataIndex: 'roomType',
            scopedSlots: { customRender: 'roomType' }
        },
        {
            title: '入住时间',
            sorter:(a,b)=>{return a.checkInDate.localeCompare(b.checkInDate)},
            dataIndex: 'checkInDate',
            scopedSlots: { customRender: 'checkInDate' }
        },
        {
            title: '离店时间',
            sorter:(a,b)=>{return a.checkOutDate.localeCompare(b.checkOutDate)},
            dataIndex: 'checkOutDate',
            scopedSlots: { customRender: 'checkOutDate' }
        },
        {
            title: '入住人数',
            sorter:(a,b)=>a.peopleNum-b.peopleNum,
            dataIndex: 'peopleNum',
        },
        {
            title: '房价',
            sorter:(a,b)=>a.price-b.price,
            dataIndex: 'price',
        },
        {
            title: '状态',
            filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' },{ text: '已完成', value: '已完成' }],
            onFilter: (value, record) => record.orderState.includes(value),
            dataIndex: 'orderState',
            scopedSlots: { customRender: 'orderState' }
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "manageCheck",
        // created() {
        //     if (sessionStorage.getItem('store')) {
        //         this.$store.replaceState(
        //             Object.assign(
        //                 {},
        //                 this.$store.state,
        //                 JSON.parse(sessionStorage.getItem('store'))
        //             )
        //         )
        //     }
        //     // 在页面刷新时将vuex里的信息保存到sessionStorage里
        //     // beforeunload事件在页面刷新时先触发
        //     window.addEventListener('beforeunload', () => {
        //         sessionStorage.setItem('store', JSON.stringify(this.$store.state))
        //     })
        // },
        data(){
            return {
                formLayout: 'horizontal',
                pagination: {},
                columns2,
                form: this.$form.createForm(this, { name: 'manageCheck' }),
                data: [],
            }
        },
        components: {
            ChangeStatte,
        },
        computed: {
            ...mapGetters([
                'userInfo',
                'receptionistOrderList',
            ]),
        },
        async mounted() {
           console.log(this.userInfo.hotelId)
            await this.getHotelOrders(this.userInfo.hotelId)
        },
        methods: {
            ...mapMutations([
                'set_currentOrrder',
                'set_updateOrderStatteVisible'
            ]),
            ...mapActions([
                'getHotelOrders',
            ]),
            showchangeStatte(record){
                this.set_currentOrrder(record)
                this.set_updateOrderStatteVisible(true)
                // window.location.reload();
            },
         }
    }
</script>

<style scoped lang="less">
    .manageCheck-wrapper {
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
    .manageCheck-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">

</style>