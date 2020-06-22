<template>
    <div class="room-list">
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
                <span slot="action" slot-scope="record">
                    <a-button type="primary" @click="showModify(record)" v-if="userInfo.userType==='HotelManager' && currentHotelInfo.managerId===userInfo.id">修改房间信息</a-button>
                </span>
            </a-table>
        </div>
        <ModifyRoom></ModifyRoom>
    </div>
</template>

<script>
    import { mapGetters, mapActions, mapMutations } from 'vuex'
    import { message } from 'ant-design-vue'
    import ModifyRoom from "./modifyRoom";
    const columns = [
        {
            title: '房间id',
            key: 'id',
            dataIndex: 'id',
        },
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
            title: '总数量',
            key: 'total',
            dataIndex: 'total',
        },
        {
            title: '开始时间',
            key: 'beginDate',
            dataIndex: 'beginDate',
        },
        {
            title: '结束时间',
            key: 'endDate',
            dataIndex: 'endDate',
        },
        {
            title: '操作',
            key: 'action',
            scopedSlots: { customRender: 'action' },
        },
    ];
    export default {
        name: "allRoomInfo",
        components: {ModifyRoom},
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
        computed: {
            ...mapGetters([
                'modifyRoomVisible',
                'userInfo',
                'currentHotelInfo',
            ])
        },
        methods: {
            ...mapMutations([
                'set_currentOrderRoom',
                'set_modifyRoomVisible',
            ]),
            ...mapActions([

            ]),
            showModify(record){
                this.set_currentOrderRoom(record)
                this.set_modifyRoomVisible(true)
            },
        }
    }
</script>

<style scoped>

</style>