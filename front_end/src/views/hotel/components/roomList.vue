<template>
    <div class="room-list">
        <div class="filter">

        </div>
        <div class="list">
            <a-table
                :columns="columns"
                :dataSource="rooms"
            >

                <span slot="price" slot-scope="text">
                    <span>￥{{ text }}</span>
                </span>
                <span slot="action" slot-scope="record">
                    <a-button type="primary" @click="order(record)" v-if="userInfo.userType=='Client'">预定</a-button>
                    <a-button type="primary" @click="showModify(record)" v-if="userInfo.userType=='HotelManager'">修改房间信息</a-button>
                </span>
            </a-table>
        </div>
        <OrderModal></OrderModal>
        <ModifyRoom></ModifyRoom>
    </div>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import OrderModal from './orderModal'
import ModifyRoom from "./modifyRoom";
const columns = [
    {  
      title: '房型',
        filters: [{ text: '大床房', value: '大床房' }, { text: '双床房', value: '双床房' }, { text: '家庭房', value: '家庭房' }],
        onFilter: (value, record) => record.roomType.includes(value),
        sorter:(a,b)=>{return a.roomType.localeCompare(b.roomType)},
      dataIndex: 'roomType',
      key: 'roomType',
    },
    {
      title: '床型',
      dataIndex: 'bedType',
      key: 'bedType',
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
    components: {
        ModifyRoom,
        OrderModal
    },
    computed: {
        ...mapGetters([
            'orderModalVisible',
            'modifyRoomVisible',
            'userInfo'
        ])
    },
    monuted() {

    },
    methods: {
        ...mapMutations([
            'set_orderModalVisible',
            'set_currentOrderRoom',
            'set_modifyRoomVisible'
        ]),
        ...mapActions([

        ]),
        order(record) {
            this.set_currentOrderRoom(record)
            this.set_orderModalVisible(true)
        },
        showModify(record){
            this.set_modifyRoomVisible(true)
        }
    }
}
</script>