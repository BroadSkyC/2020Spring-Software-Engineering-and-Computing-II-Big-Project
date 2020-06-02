<template>
    <div class="manageHotel-wrapper">
        <a-tabs>
            <a-tab-pane tab="酒店管理" key="1">
                <div style="width: 100%; text-align: right; margin:20px 0">
                    <a-button type="primary" @click="addHotel"><a-icon type="plus" />添加酒店</a-button>
                </div>
                 <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
                >
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="addRoom(record)">录入房间</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="info" size="small" @click="showCoupon(record)">优惠策略</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" size="small" @click="changeHotelInfor(record)">修改酒店详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-popconfirm
                            title="确定想删除该酒店吗？"
                            @confirm="deleteHotel(record)"
                            okText="确定"
                            cancelText="取消"
                        >
                            <a-button type="danger" size="small">删除酒店</a-button>
                        </a-popconfirm>
                    </span>
                </a-table>
            </a-tab-pane>
            <a-tab-pane tab="订单管理" key="2">
                <a-table
                    :columns="columns2"
                    :dataSource="orderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showOrder(record)">订单详情</a-button>
                        <a-divider type="vertical"></a-divider>
                        <a-button type="primary" size="small" @click="showchangeState(record)">更改状态</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
            
        </a-tabs>
        <AddHotelModal></AddHotelModal>
        <AddRoomModal></AddRoomModal>
        <Coupon></Coupon>
        <Order></Order>
        <ChangeState></ChangeState>
        <ChangeHotelInfo></ChangeHotelInfo>
    </div>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import AddHotelModal from './components/addHotelModal'
import AddRoomModal from './components/addRoomModal'
import Order from './components/Order'
import Coupon from './components/coupon'
import ChangeState from './components/changeState'
import ChangeHotelInfo from './components/changeHotelInfo'
const moment = require('moment')
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
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
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
    name: 'manageHotel',
    data(){
        return {
            formLayout: 'horizontal',
            pagination: {},
            columns1,
            columns2,
            form: this.$form.createForm(this, { name: 'manageHotel' }),
        }
    },
    components: {
        AddHotelModal,
        AddRoomModal,
        Coupon,
        Order,
        ChangeState,
        ChangeHotelInfo,
    },
    computed: {
        ...mapGetters([
            'orderList',
            'hotelList',
            'addHotelModalVisible',
            'addRoomModalVisible',
            'activeHotelId',
            'couponVisible',
        ]),
    },
    async mounted() {
        await this.getHotelList()
        await this.getAllOrders()
    },
    methods: {
        ...mapMutations([
            'set_addHotelModalVisible',
            'set_addRoomModalVisible',
            'set_couponVisible',
            'set_orderVisible',
            'set_activeHotelId',
            'set_currentOrder',
            'set_updateOrderStateVisible',
            'set_changeHotelInfoVisible',
        ]),
        ...mapActions([
            'getHotelList',
            'getAllOrders',
            'getHotelCoupon',
            'delHotel',
            'delOrder',
        ]),
        addHotel() {
            this.set_addHotelModalVisible(true)
        },
        addRoom(record) {
            this.set_activeHotelId(record.id)
            this.set_addRoomModalVisible(true)
        },
        showCoupon(record) {
            this.set_activeHotelId(record.id)
            this.set_couponVisible(true)
            this.getHotelCoupon()
        },
        showOrder(record){
            this.set_currentOrder(record)
            this.set_orderVisible(true)
        },
        deleteHotel(record){
            this.delHotel(record)
        },
        deleteOrder(record){
            this.delOrder(record)
        },
        showchangeState(record){
            this.set_currentOrder(record)
            this.set_updateOrderStateVisible(true)
        },
        changeHotelInfor(record){
            this.set_changeHotelInfoVisible(true)
        },
    }
}
</script>
<style scoped lang="less">
    .manageHotel-wrapper {
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
    .manageHotel-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
    }
</style>
<style lang="less">
    
</style>