<template>
    <div class="example-3d">
        <swiper class="swiper" :options="swiperOption">
            <swiper-slide>
                <div><img v-bind:src=currentHotelInfo.imgUrl1 /></div>
            </swiper-slide>
            <swiper-slide><div><img v-bind:src=currentHotelInfo.imgUrl2 /></div></swiper-slide>
            <swiper-slide><div><img v-bind:src=currentHotelInfo.imgUrl3 /></div></swiper-slide>
            <div class="swiper-pagination" slot="pagination"></div>
        </swiper>
    </div>
</template>

<script>
    import { Swiper, SwiperSlide } from 'vue-awesome-swiper'
    import 'swiper/css/swiper.css'
    import {mapGetters} from "vuex";
    export default {
        name: 'dynamicSwipe',
        title: '3D Coverflow effect',
        components: {
            Swiper,
            SwiperSlide
        },
        data() {
            return {
                swiperOption: {
                    effect: 'coverflow',
                    grabCursor: true,
                    centeredSlides: true,
                    slidesPerView: 'auto',
                    coverflowEffect: {
                        rotate: 50,
                        stretch: 0,
                        depth: 100,
                        modifier: 1,
                        slideShadows : true
                    },
                    pagination: {
                        el: '.swiper-pagination'
                    }
                }
            }
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
            ])
        },
    }
</script>

<style lang="scss" scoped>
    .example-3d {
        width: 100%;
        height: 400px;
        padding-top: 50px;
        padding-bottom: 50px;
    }
    .swiper {
        height: 100%;
        width: 100%;
        .swiper-slide {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 300px;
            height: 300px;
            text-align: center;
            font-weight: bold;
            font-size: 30px;
            background-color: white;
            background-position: center;
            background-size: cover;
            color: white;
        }
        .swiper-pagination {
            /deep/ .swiper-pagination-bullet.swiper-pagination-bullet-active {
                background-color: white;
            }
        }
    }
</style>