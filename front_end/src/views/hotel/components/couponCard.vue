<template>
    <a-card hoverable class="couponCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
        <img
                alt="example"
                v-bind:src= coupon.imgUrl
                slot="cover"
        />
        <a-card-meta :title="coupon.couponName">
            <template slot="description">
                <a style="font-size: 15px" /> 优惠券介绍：{{coupon.description}}<br/>
            </template>
        </a-card-meta>
    </a-card>
</template>
<script>
    export default {
        name: 'couponcard',
        props: {
            coupon: {}
        },
        data() {
            return {
                rateValue: 0,
            }
        },
        mounted() {
        },
        methods:{
        }
    }
</script>
<style scoped lang="less">
    .couponCard {
        margin: 15px 15px;
        min-width: 160px;
        min-height: 360px;
        max-height: 400px;
        img {
            height: 240px;
            width: 100%;
        }
    }
</style>

<style lang="less">
    .couponCard{
        .ant-card-body{
            padding: 12px
        }
    }

    .ant-card-hoverable:hover {
        box-shadow: 0 2px 8px rgba(0,0,0,0.4)
    }

</style>