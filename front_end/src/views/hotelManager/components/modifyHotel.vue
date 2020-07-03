<template>
    <a-modal
            :visible="modifyHotelVisible"
            title="修改酒店信息"
            cancelText="取消"
            okText="确认修改"
            @cancel="cancel"
            @ok="handleModify"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名称" v-bind="formItemLayout">
                <a-input
                        v-decorator="['name', { rules: [{ required: true, message: currentHotelInfo.name}] }]"
                />
            </a-form-item>
            <a-form-item label="商圈" v-bind="formItemLayout">
                <a-input
                        v-decorator="['bizRegion', { rules: [{ required: true, message: currentHotelInfo.bizRegion }] }]"
                />
            </a-form-item>
            <a-form-item label="地址" v-bind="formItemLayout">
                <a-input
                        v-decorator="['address', { rules: [{ required: true, message: currentHotelInfo.address }] }]"
                />
            </a-form-item>
            <a-form-item label="电话" v-bind="formItemLayout">
                <a-input
                        v-decorator="['phoneNum', { rules: [{ required: true, message: currentHotelInfo.phoneNum }] }]"
                />
            </a-form-item>
            <a-form-item label="简介" v-bind="formItemLayout">
                <a-input
                        v-decorator="['description', { rules: [{ required: true, message: currentHotelInfo.description }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "modifyHotel",
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
                'modifyHotelVisible',
                'currentHotelInfo',
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyRoom' });
        },
        async mounted() {
            console.log(this.currentHotelInfo)
            await this.set_currentHotelId(this.currentHotelInfo.id)
            await this.getHotelById()
        },
        methods:{
            ...mapMutations([
                'set_modifyHotelVisible',
                'set_updateHotelParams',
                'set_currentHotelId'
            ]),
            ...mapActions([
                'updateHotelInfo',
                'getHotelById'
            ]),
            cancel() {
                this.set_modifyHotelVisible(false)
            },
            handleModify(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            name: this.form.getFieldValue('name'),
                            address: this.form.getFieldValue('address'),
                            description: this.form.getFieldValue('description'),
                            bizRegion: this.form.getFieldValue('bizRegion'),
                            phoneNum:this.form.getFieldValue('phoneNum'),
                            id:this.currentHotelInfo.id,
                        }
                        this.set_updateHotelParams(data)
                        this.updateHotelInfo()
                        this.set_modifyHotelVisible(false)
                        location.reload()
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>