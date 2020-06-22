<template>
    <div class="room-list">
                <div class="search-bar">
                    <div class="search-cont"
                    >
                        <span>入住时间：{{checkInDate}}</span>
                    </div>
                    <div class="search-cont"
                    >
                        <span>离店时间：{{checkOutDate}} </span>
                    </div>
                    <div class="search-cont">
                        <a-button
                                type="primary" style="margin-left: 10px"
                                @click="showChooseDate">设置</a-button>
                    </div>
        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
            >
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="record" v-if="userInfo.userType==='Client'">
                    <a-button type="primary" @click="order(record)" v-if="userInfo.userType==='Client'">预定</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
        <ModifyRoom></ModifyRoom>
        <ChooseDate></ChooseDate>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import { message } from 'ant-design-vue'
import OrderModal from './orderModal'
import ModifyRoom from "./modifyRoom";
import ChooseDate from "./chooseDate";
const columns = [
    {  
      title: '房型',
        filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' }],
        onFilter: (value, record) => record.roomType.includes(value),
        sorter:(a,b)=>{return a.roomType.localeCompare(b.roomType)},
      dataIndex: 'roomType',
      key: 'roomType',
    },
    {
      title: '房价',
        sorter:(a,b)=>a.price-b.price,
      key: 'price',
      dataIndex: 'price',
      scopedSlots: { customRender: 'price'}
    },
    {
       title: '剩余数量',
       key: 'curNum',
       dataIndex: 'curNum',
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },
  ];
export default {
    name:'roomList',
    props: {
        rooms: {
            type: Array
        }
    },
    data() {
        return {
            columns,
        }
    },
    mounted() {
            this.set_chooseDateVisible(true)
            this.set_checkIndate("")
            console.log("rooms are" )
            console.log(this.currentHotelInfo.rooms)
            this.set_checkOutdate("")
    },
    components: {
        ChooseDate,
        ModifyRoom,
        OrderModal
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'modifyRoomVisible',
            'chooseDateVisible',
            'userInfo',
            'currentHotelInfo',
            'checkInDate',
            'checkOutDate',
        ])
    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom',
            'set_modifyRoomVisible',
            'set_chooseDateVisible',
            'clear_rooms',
            'set_checkIndate',
            'set_checkOutdate'
        ]),
        ...mapActions([

        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        },
        showModify(record){
            this.set_currentOrderRoom(record)
            this.set_modifyRoomVisible(true)
        },
        showChooseDate(){
            this.set_chooseDateVisible(true)
        }
    }
}
</script>
<style scoped lang="less">
        .search-bar{
            display: flex;
            flex-wrap: wrap;
        }
        .search-cont{
            display: flex;
            align-items: center;
            margin-right: 30px;
            font-family: "Yuanti TC",serif;
            /*font-size: 17px;*/
        }
</style>