<template>
    <a-modal
            :visible="modifyPasswordVisible"
            title="修改密码"
            cancelText="取消"
            okText="确认修改"
            @cancel="cancel"
            @ok="handleModify"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="密码" v-bind="formItemLayout">
                <a-input
                        v-decorator="['password', { rules: [{ required: true, message: '密码不为空'}]}]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "modifyPassword",
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
                loading: false,
            }
        },
        computed: {
            ...mapGetters([
                'modifyPasswordVisible',
                'activeUserId'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'modifyPassword' });
        },
        async mounted() {
        },
        methods:{
            ...mapMutations([
                'set_modifyPasswordVisible',
            ]),
            ...mapActions([
                'updatePassword'
            ]),
            cancel() {
                this.set_modifyPasswordVisible(false)
            },
            handleModify(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            password: this.form.getFieldValue('password'),
                            id:this.activeUserId,
                        }
                        this.updatePassword(data)
                        this.set_modifyPasswordVisible(false)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>