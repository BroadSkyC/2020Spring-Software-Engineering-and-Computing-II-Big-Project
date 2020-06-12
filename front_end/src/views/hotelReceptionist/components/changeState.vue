<template >
    <a-modal
            :visible="updateOrderStateVisible"
            title="更改订单状态"
            width="900px"
            @cancel="cancel"
            :footer="null"
    >
        <div class="changeOrderState">
            <a-form :form="form" >
                <a-form-item  label="当前状态"  v-bind="formItemLayout" >
                    <span >{{currentOrder.orderState}}</span>
                </a-form-item>
                <a-form-item label="更改状态" v-bind="formItemLayout">
                    <a-select
                            v-decorator="[
                    'orderState',
                    { rules: [{ required: true, message: '请选择状态' }] }]"
                    >
                        <a-select-option value="已撤销">撤销</a-select-option>
                        <a-select-option value="已入住">入住</a-select-option>
                        <a-select-option value="已完成">完成</a-select-option>
                    </a-select>
                </a-form-item>
                <a-form-item :wrapper-col="{ span: 8, offset: 4 }" >
                    <a-button type="primary" @click="saveModify">
                        保存
                    </a-button>
                    <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                        取消
                    </a-button>
                </a-form-item>
            </a-form>
        </div>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "changeState",
        data() {
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
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'changeState' });
        },
        computed: {
            ...mapGetters([
                'updateOrderStateVisible',
                'currentOrder'
            ])
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_updateOrderStateVisible'
            ]),
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateOrderState',
            ]),
            cancel(){
                this.set_updateOrderStateVisible(false)
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            checkInDate: this.currentOrder.checkInDate,
                            checkOutDate: this.currentOrder.checkOutDate,
                            peopleNum: this.currentOrder.peopleNum,
                            price: this.currentOrder.price,
                            id: this.currentOrder.id,
                            hotelName: this.currentOrder.hotelName,
                            roomType: this.currentOrder.roomType,
                            orderState:this.form.getFieldValue('orderState')
                        }
                        this.updateOrderState(data).then(()=>{
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
                this.modify=false
                this.set_updateOrderStateVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>