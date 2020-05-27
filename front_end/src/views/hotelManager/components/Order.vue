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
                <AWeekPicker  :format="format"
                              showTime
                              onChange={onChange}
                              v-decorator="['checkInDate',{rules: [{required:true,message: '请修改入住时间'}]}]"
                              v-if="modify"
                />
                <span v-else>{{ currentOrder.checkInDate}}</span>
            </a-form-item>
            <a-form-item label="离店时间" v-bind="formItemLayout" >
                <AWeekPicker  :format="format"
                              showTime
                              onChange={onChange}
                              v-decorator="['checkOutDate',{rules: [{required:true,message: '请修改入住时间'}]}]"
                              v-if="modify"
                />
                <span v-else>{{ currentOrder.checkOutDate}}</span>
            </a-form-item>
            <a-form-item  label="入住人数" v-bind="formItemLayout" >
                <a-input
                        placeholder="请修改入住人数"
                        v-decorator="['peopleNum', { rules: [{ required: true, message: '请修改入住人数' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ currentOrder.peopleNum}}</span>
            </a-form-item>
            <a-form-item  label="房价" v-bind="formItemLayout" >
                <a-input
                        placeholder="请修改房价"
                        v-decorator="['price', { rules: [{ required: true, message: '请修改房价' }] }]"
                        v-if="modify"
                />
                <span v-else>{{ currentOrder.price}}</span>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-if="modify">
                <a-button type="primary" @click="saveModify">
                    保存
                </a-button>
                <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                    取消
                </a-button>
            </a-form-item>
            <a-form-item :wrapper-col="{ span: 8, offset: 4 }" v-else>
                <a-button type="primary" @click="modifyInfo">
                    修改信息
                </a-button>
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
                modify: false,
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
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            checkInDate: this.form.getFieldValue('checkInDate'),
                            checkOutDate: this.form.getFieldValue('checkOutDate'),
                            peopleNum: Number(this.form.getFieldValue('peopleNum')),
                            price: Number(this.form.getFieldValue('price')),
                            id: this.currentOrder.id,
                            hotelName: this.currentOrder.hotelName,
                            roomType: this.currentOrder.roomType
                        }
                        this.updateOrderInfo(data).then(()=>{
                            this.modify = false
                        })
                        location.reload()
                    }
                });
            },
            modifyInfo() {
                setTimeout(() => {
                    this.form.setFieldsValue({
                    })
                }, 0)
                this.modify = true
            },
            cancelModify() {
                this.modify = false
            }
        }
    }
</script>

<style scoped>

</style>