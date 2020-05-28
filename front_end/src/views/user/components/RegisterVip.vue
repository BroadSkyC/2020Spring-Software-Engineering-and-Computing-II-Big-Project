<template>
    <a-modal
            :visible="registerVipVisible"
            title="注册会员"
            width="900px"
            @cancel="cancel"
            cancelText="取消"
            okText="确定"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="用户ID" v-bind="formItemLayout" >
            <span>{{userInfo.id}}</span>
            </a-form-item>
            <a-form-item label="用户名" v-bind="formItemLayout" >
                <span>{{userInfo.userName}}</span>
            </a-form-item>
            <a-form-item label="会员类型" v-bind="formItemLayout">
                <a-select v-decorator="['type',{rules: [{required:true,message: '请选择注册会员的类型'}]}]"  @change="changeType">
                    <a-select-option value="1">普通会员</a-select-option>
                    <a-select-option value="2">企业会员</a-select-option>
                </a-select>
            </a-form-item>
                <a-form-item label="企业名称" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='2'">
                    <a-input placeholder="请填写企业名称" v-decorator="['company',{rules:[{required:true,message:'请填写企业名称'}]}]"/>
                </a-form-item>
        </a-form>
        </a-modal>
</template>
<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name:'registerVip',
        data() {
            const format = "YYYY-MM-DD"
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
                format
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'registerVip' });
        },
        computed: {
            ...mapGetters([
                'registerVipVisible',
                'userInfo'
            ])
        },
        methods:{
            ...mapMutations([
                'set_RegisterVipVisible'
            ]),
            ...mapActions([
               'addCommonVip',
                'addCompanyVip'
            ]),
            cancel(){
                this.set_RegisterVipVisible(false)
            },
            changeType(v){
                if( v == '2') {

                }else{
                    this.$message.info('请完善我的信息中的生日信息，否则无法享受生日特惠')
                }
            },
            handleSubmit(e){
                console.log('添加会员')
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        if (this.form.getFieldValue('type')==='1'){
                            const data = {
                                // 这里添加接口参数
                                id:this.userInfo.id
                            }
                            this.addCommonVip(data)
                            this.form.resetFields()
                            this.window.location.reload()
                        }
                        else{
                            const data={
                                id:this.userInfo.id,
                                company:this.form.getFieldValue('company')
                            }
                            this.addCompanyVip(data)
                            this.form.resetFields()
                            this.window.location.reload()
                        }
                    }
                });
            }
        }
    }
</script>