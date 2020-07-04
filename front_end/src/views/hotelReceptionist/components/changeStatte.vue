<template >
    <a-modal
            :visible="updateOrderStatteVisible"
            title="更改订单状态"
            width="900px"
            @cancel="cancel"
            :footer="null"
    >
        <div class="changeOrderStatte">
            <a-form :form="form" >
                <a-form-item  label="当前状态"  v-bind="formItemLayout" >
                    <span >{{currentOrrder.orderState}}</span>
                </a-form-item>
                <a-form-item label="更改状态" v-bind="formItemLayout">
                    <a-select
                            v-decorator="[
                    'orderState',
                    { rules: [{ required: true, message: '请选择状态' }] }]"
                    >
                        <a-select-option value="已撤销" v-if="currentOrrder.orderState==='已预订'">撤销</a-select-option>
                        <a-select-option value="已入住" v-if="currentOrrder.orderState==='已预订'">入住</a-select-option>
                        <a-select-option value="已完成" v-if="currentOrrder.orderState==='已入住'">完成</a-select-option>
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
        name: "changeStatte",
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
            this.form = this.$form.createForm(this, { name: 'changeStatte' });
        },
        computed: {
            ...mapGetters([
                'updateOrderStatteVisible',
                'currentOrrder'
            ])
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_updateOrderStatteVisible'
            ]),
            ...mapActions([
                'getUserInfo',
                'getUserOrders',
                'updateOrderStatte',
            ]),
            cancel(){
                this.set_updateOrderStatteVisible(false)
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            checkInDate: this.currentOrrder.checkInDate,
                            checkOutDate: this.currentOrrder.checkOutDate,
                            peopleNum: this.currentOrrder.peopleNum,
                            price: this.currentOrrder.price,
                            id: this.currentOrrder.id,
                            hotelName: this.currentOrrder.hotelName,
                            roomType: this.currentOrrder.roomType,
                            orderState:this.form.getFieldValue('orderState')
                        }
                        console.log("curentorder is")
                        console.log(this.currentOrrder)
                        this.updateOrderStatte(data).then(()=>{
                            this.modify = false
                        })
                         // window.location.reload()
                        this.form.resetFields()
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
                this.set_updateOrderStatteVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>