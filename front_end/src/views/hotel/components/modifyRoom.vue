<template>
    <a-modal
            :visible="modifyRoomVisible"
            title="修改房间信息"
            cancelText="取消"
            okText="确认修改"
            @cancel="cancel"
            @ok="handleModify"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="房型" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'roomType',
                    { rules: [{ required: true, message: '请修改房型' }] }]"
                >
                    <a-select-option value="BigBed">大床房</a-select-option>
                    <a-select-option value="DoubleBed">双床房</a-select-option>
                    <a-select-option value="Family">家庭房</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="房间数量" v-bind="formItemLayout">
                <a-input
                        placeholder="请修改房间数量"
                        v-decorator="['roomNum', { rules: [{ required: true, message: '请修改房间数量' }] }]"
                />
            </a-form-item>
            <a-form-item label="原始价格" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写原始价格"
                        v-decorator="['price', { rules: [{ required: true, message: '请修改原始价格' }] }]"
                />
            </a-form-item>
        </a-form>
        </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "modifyRoom",
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
                'modifyRoomVisible',
                'activeHotelId',
                'currentOrderRoom',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyRoom' });
        },
        mounted() {

        },
        methods:{
            ...mapMutations([
                'set_modifyRoomVisible',
                'set_updateRoomParams'
            ]),
            ...mapActions([
                'updateRoomInfo',
            ]),
            cancel() {
                this.set_modifyRoomVisible(false)
            },
            handleModify(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            roomType: this.form.getFieldValue('roomType'),
                            price: Number(this.form.getFieldValue('price')),
                            total: Number(this.form.getFieldValue('roomNum')),
                            curNum: Number(this.form.getFieldValue('roomNum')),
                            hotelId: this.activeHotelId,
                            id:this.currentOrderRoom.id,
                        }
                        this.set_updateRoomParams(data)
                        this.updateRoomInfo()
                        this.set_modifyRoomVisible(false)
                        location.reload()
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>