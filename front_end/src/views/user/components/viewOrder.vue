<template>
    <a-modal
            :visible="viewOrderVisible"
            title="订单详情"
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
                    <span >{{ currentOrder.checkOutDate}}</span>
                </a-form-item>
                <a-form-item  label="入住人数" v-bind="formItemLayout" >
                    <span >{{ currentOrder.peopleNum}}</span>
                </a-form-item>
                <a-form-item  label="房价" v-bind="formItemLayout" >
                    <span >{{ currentOrder.price}}</span>
                </a-form-item>
                <a-form-item  label="订单状态" v-bind="formItemLayout" >
                    <span >{{ currentOrder.orderState}}</span>
                </a-form-item>
<!--                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" >-->
<!--                    <a-button type="primary" @click="modifyInfo">-->
<!--                        评论-->
<!--                    </a-button>-->
<!--                </a-form-item>-->
            </a-form>
        </div>
    </a-modal>

</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "viewOrder",
        data() {
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
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'viewOrder' });
        },
        computed: {
            ...mapGetters([
                'viewOrderVisible',
                'currentOrder'
            ])
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_viewOrderVisible'
            ]),
            ...mapActions([
            ]),
            cancel(){
                this.set_viewOrderVisible(false)
            },
        }
    }

</script>

<style scoped>

</style>