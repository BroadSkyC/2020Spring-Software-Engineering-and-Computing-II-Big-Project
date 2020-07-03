<template >
    <a-modal
            :visible="orderVisible"
            title="订单管理详情"
            width="900px"
            @cancel="cancel"
            :footer="null"
    >
    <div class="order-info">
        <a-form :form="form" >
        <a-form-item label="订单号" v-bind="formItemLayout">
            <span>{{currentOrder.id}}</span>
        </a-form-item>
        <a-form-item label="酒店名" v-bind="formItemLayout">
            <span>{{currentOrder.hotelName}}</span>
        </a-form-item>
        <a-form-item  label="房型" v-bind="formItemLayout" >
                <span v-if="currentOrder.roomType == 'BigBed'">大床房</span>
                        <span v-if="currentOrder.roomType == 'DoubleBed'">双床房</span>
                        <span v-if="currentOrder.roomType == 'Family'">家庭房</span>
        </a-form-item>
            <a-form-item label="入住时间" v-bind="formItemLayout" >
                <span >{{ currentOrder.checkInDate}}</span>
            </a-form-item>
            <a-form-item label="离店时间" v-bind="formItemLayout" >
                <span>{{ currentOrder.checkOutDate}}</span>
            </a-form-item>
            <a-form-item  label="入住人数" v-bind="formItemLayout" >
                <span>{{ currentOrder.peopleNum}}</span>
            </a-form-item>
            <a-form-item  label="房间数" v-bind="formItemLayout" >
                <span>{{ currentOrder.roomNum}}</span>
            </a-form-item>
            <a-form-item  label="房价" v-bind="formItemLayout" >
                <span>{{ currentOrder.price}}</span>
            </a-form-item>
            <a-form-item label="订单状态" v-bind="formItemLayout">
                <span>{{currentOrder.orderState}}</span>
            </a-form-item>
            <a-form-item label="订单备注" v-bind="formItemLayout">
                <span>{{currentOrder.tip}}</span>
            </a-form-item>
            <a-form-item label="用户评分" v-bind="formItemLayout" v-if="currentOrder.orderState==='已评价'">
                <span>{{currentOrder.rate}}</span>
            </a-form-item>
            <a-form-item label="用户反馈" v-bind="formItemLayout" v-if="currentOrder.orderState==='已评价'">
                <span>{{currentOrder.feedback}}</span>
            </a-form-item>
        </a-form>
    </div>
        </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "order",
        data() {
            const format = "YYYY-MM-DD"
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
                format
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'order' });
        },
        computed: {
            ...mapGetters([
                'orderVisible',
                'currentOrder'
            ])
        },
         mounted() {

        },
        methods: {
            ...mapMutations([
                'set_orderVisible'
            ]),
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateOrderInfo',
            ]),
            cancel(){
                this.set_orderVisible(false)
            },
        }
    }
</script>

<style scoped>

</style>