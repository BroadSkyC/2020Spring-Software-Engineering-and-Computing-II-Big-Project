<template>
    <a-modal
            :visible="commentVisible"
            title="评价"
            width="900px"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="评分">
            <span>
            <a-rate v-model="rate" :tooltips="tips"  allow-half >
                 <a-icon slot="character" type="heart"></a-icon>
            </a-rate>
            </span>
            </a-form-item>
            <a-form-item label="意见反馈" v-bind="formItemLayout">
                <a-input
                        type="textarea"
                        :rows="4"
                        placeholder="房间如何？服务如何？谈谈您对本次入住的感受吧~我们的工作人员将收到反馈，并努力为您提供更好的服务"
                        v-decorator="['feedback', { rules: [{ required: true, message: '请填写意见反馈' }] }]"
                />
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    import { message } from 'ant-design-vue'
    export default {
        name: "comment",
        data() {
            return {
                rate:3,
                tips: ['非常不满意', '不满意', '一般', '满意', '非常满意'],
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
                'commentVisible',
                'currentOrder'
            ])
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'comment' });
        },
        mounted() {

        },
        methods: {
            ...mapMutations([
                'set_commentVisible',
            ]),
            ...mapActions([
                'updateComment',
            ]),
            cancel() {
                this.set_commentVisible(false)
            },
            handleSubmit(e) {
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                           rate: this.rate,
                           feedback: this.form.getFieldValue('feedback'),
                           hotelId:this.currentOrder.hotelId,
                           id:this.currentOrder.id,
                        }
                        this.updateComment(data)
                        this.set_commentVisible(false)
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>