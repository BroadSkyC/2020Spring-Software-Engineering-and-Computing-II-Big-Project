<template>
    <a-layout>
        <a-layout-content>
            <div class="hotelDetailCard">
                <h1>
                    {{ currentHotelInfo.title }}
                </h1>
                <div class="hotel-info">
                    <a-card style="width: 240px">
                        <img
                            alt="example"
                            v-bind:src= currentHotelInfo.imgUrl
                            slot="cover"
                            referrerPolicy="no-referrer"
                            />
                    </a-card>
                    <div class="info">
                        <div class="items" v-if="currentHotelInfo.name">
                            <span class="label">酒店名称：</span>
                            <span class="value">{{ currentHotelInfo.name }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.address">
                            <span class="label">地址</span>
                            <span class="value">{{ currentHotelInfo.address }}</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.hotelStar">
                            <span class="label">星级:</span>
                            <span class="value" v-if="currentHotelInfo.hotelStar=='Five'">五星级</span>
                            <span class="value" v-if="currentHotelInfo.hotelStar=='Four'">四星级</span>
                            <span class="value" v-if="currentHotelInfo.hotelStar=='Three'">三星级</span>
                        </div>
                        <div class="items" v-if="currentHotelInfo.rate">
                            <span class="label">评分:</span>
                            <span class="value">{{ currentHotelInfo.rate }}</span>
                            <a-rate style="font-size: 15px" :value="currentHotelInfo.rate" disabled allowHalf/>
                        </div>
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span> 
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </div>
                </div>
                <a-divider></a-divider>
                <a-tabs>
                    <a-tab-pane tab="房间信息" key="1">
                        <RoomList :rooms="currentHotelInfo.rooms"></RoomList>
                    </a-tab-pane>
                    <a-tab-pane tab="全部房间" key="3" v-if="userInfo.userType==='HotelManager' && currentHotelInfo.managerId===userInfo.id ">
                        <AllRoomInfo :rooms="allRooms"></AllRoomInfo>
                    </a-tab-pane>
                    <a-tab-pane tab="酒店详情" key="2">
                        <div class="items" v-if="currentHotelInfo.description">
                            <span class="label">酒店简介:</span>
                            <span class="value">{{ currentHotelInfo.description }}</span>
                        </div>
                    </a-tab-pane>
                </a-tabs>
            </div>
        </a-layout-content>
    </a-layout>
</template>
<script>
import { mapGetters, mapActions, mapMutations } from 'vuex'
import RoomList from './components/roomList'
import AllRoomInfo from "./components/allRoomInfo";
export default {
    name: 'hotelDetail',
    components: {
        AllRoomInfo,
        RoomList,
    },
    data() {
        return {
            count:0,
        }
    },
    computed: {
        ...mapGetters([
            'currentHotelInfo',
            'allRooms',
            'userInfo'
        ])
    },
    mounted() {
        this.set_visitCount(this.count)
        this.set_currentHotelId(Number(this.$route.params.hotelId))
        this.getHotelById()
        this.clear_rooms()
        this.count++;
    },
    beforeRouteUpdate(to, from, next) {
        this.set_currentHotelId(Number(to.params.hotelId))
        this.getHotelById()
        next()
    },
    methods: {
        ...mapMutations([
            'set_currentHotelId',
            'set_visitCount',
            'clear_rooms'
        ]),
        ...mapActions([
            'getHotelById'
        ])
    }
}
</script>
<style scoped lang="less">
    .hotelDetailCard {
        padding: 50px 50px;
        background-color: white;
    }
    .hotel-info {
        display: flex;
        align-items: stretch;
        justify-content: flex-start;
        .info{
            padding: 10px 0;
            display: flex;
            flex-direction: column;
            margin-left: 20px;
            .items {
                display: flex;
                align-items: center;
                margin-bottom: 10px;
                .label{
                    margin-right: 10px;
                    font-size: 18px;
                }
                .value {
                    margin-right: 15px
                }
            }
        }
    }
</style>