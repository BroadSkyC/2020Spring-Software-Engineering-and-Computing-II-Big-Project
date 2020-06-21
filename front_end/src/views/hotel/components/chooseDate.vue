<template>
    <a-modal
            :visible="chooseDateVisible"
            title="选择您要入住的时间"
            cancelText="取消"
            okText="确定"
            @cancel="exits"
            @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="入住日期">
                <a-range-picker
                        format="YYYY-MM-DD"
                        v-decorator="[
                        'date',
                        {
                            rules: [{ required: true, message: '请选择入住时间' }]
                        }
                    ]"
                        :placeholder="['入住日期','退房日期']"
                />
            </a-form-item>
        </a-form>
    </a-modal>


</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import { message } from 'ant-design-vue'
    const moment = require('moment')
    export default {
        name: "chooseDate",
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
        computed: {
            ...mapGetters([
                'chooseDateVisible',
                'currentHotelId',
                'currentHotelInfo',
                'userId',
            ]),
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'chooseDate' });
        },
        methods: {
            ...mapMutations([
                'set_chooseDateVisible',
                'clear_rooms'
            ]),
            ...mapActions([

            ]),
            exits(){
                // eslint-disable-next-line no-undef
                message.warn("请务必填写入住日期，否则无法查看房间列表")
                this.set_chooseDateVisible(false)
                this.clear_rooms()
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            hotelId: this.currentHotelId,
                            checkInDate: moment(this.form.getFieldValue('date')[0]).format('YYYY-MM-DD'),
                            checkOutDate: moment(this.form.getFieldValue('date')[1]).format('YYYY-MM-DD'),
                        }
                        this.updateCurrentHotelInfo(data)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>