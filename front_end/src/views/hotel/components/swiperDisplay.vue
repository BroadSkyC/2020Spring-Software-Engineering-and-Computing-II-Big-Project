<template>
    <a-carousel autoplay effect="fade">
        <div><img v-bind:src=currentHotelInfo.imgUrl1 /></div>
        <div><img v-bind:src=currentHotelInfo.imgUrl2 /></div>
        <div><img v-bind:src=currentHotelInfo.imgUrl3 /></div>
    </a-carousel>
</template>
<script>
    import {mapGetters} from "vuex";

    export default {
        mounted() {
            console.log("hotel cover is")
            console.log(this.currentHotelInfo)
        },
        computed: {
            ...mapGetters([
                'currentHotelInfo',
            ])
        },
    };
</script>
<style scoped>
    /* For demo */
    .ant-carousel >>> .slick-slide {
        text-align: center;
        height: 400px;
        width: 400px;
        line-height: 180px;
        background: #ffffff;
        overflow: hidden;
    }

    .ant-carousel >>> .slick-slide h3 {
        color: #fff;
    }
    .ant-carousel >>> .slick-slide img {
        border: 5px solid #fff;
        display: block;
        margin: auto;
        max-width: 75%;
        /*object-fit: cover;*/
    }
</style>
