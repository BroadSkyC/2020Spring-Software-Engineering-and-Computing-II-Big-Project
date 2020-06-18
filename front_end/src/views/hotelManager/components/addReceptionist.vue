<template>
    <a-modal
            :visible="addReceptionistVisible"
            title="添加酒店前台"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form">
            <a-form-item v-bind="formItemLayout" label="用户邮箱">
                <a-input
                        v-decorator="[
                        'email',
                        { rules: [{required: true, message: '请输入用户邮箱', }] }
                    ]"
                />
            </a-form-item>
            <a-form-item v-bind="formItemLayout" label="密码">
                <a-input
                        v-decorator="[
                        'password',
                        { rules: [{required: true, message: '请输入密码', }] }
                    ]"
                />
            </a-form-item >
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "addReceptionist",
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
                'activeHotelId',
                'addReceptionistVisible',
            ])
        },
        beforeCreate() {
            // 表单名默认为“form”
            this.form = this.$form.createForm(this, { name: 'addReceptionistModal' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_addReceptionistVisible',
                'set_addReceptionistParams',
            ]),
            ...mapActions([
                'getReceptionistList',
                'addReceptionist',
            ]),
            cancel() {
                this.set_addReceptionistVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            email: this.form.getFieldValue('email'),
                            password: this.form.getFieldValue('password'),
                            hotelId:Number(this.activeHotelId),
                        }
                        this.set_addReceptionistParams(data)
                        this.addReceptionist()
                    }
                });
            },
        }
    }

</script>

<style scoped>

</style>