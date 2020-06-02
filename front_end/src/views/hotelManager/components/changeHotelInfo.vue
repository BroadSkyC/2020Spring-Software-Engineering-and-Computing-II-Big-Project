<template>
    <a-modal
            :visible="changeHotelInfoVisible"
            title="更改酒店详情"
            width="900px"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="saveModify"
    >
        <a-form-item v-bind="formItemLayout" label="新的酒店详情">
            <a-input
                    v-decorator="[
                        'description',
                        { rules: [{required: true, message: '请填写新的酒店详情', }] }
                    ]"
            />
        </a-form-item>
    </a-modal>
</template>

<script>
    import {mapActions, mapGetters, mapMutations} from "vuex";

    export default {
        name: "changeHotelInfo",
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
            this.form = this.$form.createForm(this, { name: 'changeHotelInfo' });
        },
        computed: {
            ...mapGetters([
                'changeHotelInfoVisible',
                'currentHotelId',
                'currentHotelInfo',
            ])
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_changeHotelInfoVisible',
            ]),
            ...mapActions([
                'changeHotelInfo',
            ]),
            cancel(){
                this.set_changeHotelInfoVisible(false)
            },
            saveModify() {
                this.form.validateFields((err, values) => {
                    if (!err) {
                        const data = {
                            description: this.form.getFieldValue('description'),
                            id:this.currentHotelId,
                        }
                      this.changeHotelInfo(data)
                        this.set_changeHotelInfoVisible(false)
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
                this.set_changeHotelInfoVisible(false)
            }
        }
    }
</script>

<style scoped>

</style>