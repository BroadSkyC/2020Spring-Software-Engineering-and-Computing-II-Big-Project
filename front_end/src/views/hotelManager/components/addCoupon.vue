<template>
    <a-modal
        :visible="addCouponVisible"
        title="添加优惠策略"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <!-- 这里是添加策略模态框区域，请编写表单 -->
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="优惠券类型" v-bind="formItemLayout">
                <a-select v-decorator="['type',{rules: [{required:true,message: '请选择类型'}]}]" @change="changeType">
                    <a-select-option value="1">生日特惠</a-select-option>
                    <a-select-option value="2">多间特惠</a-select-option>
                    <a-select-option value="3">满减特惠</a-select-option>
                    <a-select-option value="4">限时特惠</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="券名" v-bind="formItemLayout">
                <a-input placeholder="请填写优惠券名" v-decorator="['name',{rules: [{required:true,message:'请输入券名'}]}]" />
            </a-form-item>

            <!--targetMoney-->
            <a-form-item label="达标金额" v-if="this.form.getFieldValue('type')==='3'">
                <a-input placeholder="请填写达标金额" v-decorator="['targetMoney',{rules:[{required:true,message:'请填写达标金额'}]}]"/>
            </a-form-item>
            <a-form-item label="优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='3'">
                <a-input placeholder="请填写优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写优惠金额'}]}]"/>
            </a-form-item>

            <!--time-->
            <a-form-item label="折扣" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='4'">
                <a-input placeholder="请填写折扣" v-decorator="['discount',{rules:[{required:true,message:'请填写折扣'}]}]"/>
            </a-form-item>
            <a-form-item label="起始-失效时间" v-if="this.form.getFieldValue('type')==='4'">
                <ARangePicker
                        :format="format"
                        showTime
                        onChange={onChange}
                        v-decorator="['time',{rules: [{required:true,message: '请选择时间'}]}]"
                />
            </a-form-item>

            <!--birthday-->
            <a-form-item label="折扣" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='1'">
                <a-input placeholder="请填写折扣" v-decorator="['discount',{rules:[{required:true,message:'请填写折扣'}]}]"/>
            </a-form-item>

            <!--targetRoomNum-->
            <a-form-item label="目标间数" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='2'">
                <a-input placeholder="请填写目标间数" v-decorator="['targetRoomNum',{rules:[{required:true,message:'请填写目标间数'}]}]"/>
            </a-form-item>
            <a-form-item label="每间优惠金额" v-bind="formItemLayout" v-if="this.form.getFieldValue('type')==='2'">
                <a-input placeholder="请填写每间优惠金额" v-decorator="['discountMoney',{rules:[{required:true,message:'请填写每间优惠金额'}]}]"/>
            </a-form-item>


            <a-form-item label="优惠简介" v-bind="formItemLayout">
                <a-input type="textarea" :rows="4" placeholder="请填写优惠简介"
                         v-decorator="['description',{rules: [{required:true,message:'请填写优惠简介'}]}]" />
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
import DatePicker from "ant-design-vue/lib/date-picker";
const { RangePicker } = DatePicker
export default {
    name: 'addCouponModal',
    components:{
    },
    data() {
        const format="YYYY-MM-DD HH:mm:ss"
        return {
            // label 标签布局，同 <Col> 组件，设置 span offset 值，如 {span: 3, offset: 12} 或 sm: {span: 3, offset: 12}
            // wrapperCol，需要为输入控件设置布局样式时，使用该属性，用法同 labelCol
            // xs、sm是响应尺寸
            // sm：一般用于小屏设备
            // xs：超小型设备
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
            //form: this.$form.createForm(this,{name: 'addCouponModal'}),
            format
        }
    },
    computed: {
        ...mapGetters([
            'activeHotelId',
            'addCouponVisible',
        ])
    },
    beforeCreate() {
        // 表单名默认为“form”
        this.form = this.$form.createForm(this, { name: 'addCouponModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addCouponVisible'
        ]),
        ...mapActions([
            // addHotelCoupon：添加酒店策略接口
            'addHotelTargetMoneyCoupon',
            'addHotelTimeCoupon',
            'addHotelTargetRoomNumCoupon',
            'addBirthdayCoupon'
        ]),
        cancel() {
            this.set_addCouponVisible(false)
        },
        changeType(v){
            if( v == '3') {

            }else{
                //this.$message.warning('请实现')
            }
        },
        handleSubmit(e) {
            console.log('添加策略')
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    if (this.form.getFieldValue('type')==='3'){
                        const data = {
                            // 这里添加接口参数
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            targetMoney: Number(this.form.getFieldValue('targetMoney')),
                            discountMoney: Number(this.form.getFieldValue('discountMoney')),
                            hotelId: Number(this.activeHotelId),
                            status: 1,
                        }
                        this.addHotelTargetMoneyCoupon(data)
                        this.form.resetFields()
                    }
                    else if(this.form.getFieldValue('type')==='2'){
                        const data = {
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            discountMoney: Number(this.form.getFieldValue('discountMoney')),
                            targetRoomNum: Number(this.form.getFieldValue('targetRoomNum')),
                            hotelId: Number(this.activeHotelId),
                            status: 1
                        }
                        this.addHotelTargetRoomNumCoupon(data)
                        this.form.resetFields()

                    }
                    else if(this.form.getFieldValue('type')==='4'){
                        const data = {
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            startTime: this.form.getFieldValue('time')[0],
                            endTime: this.form.getFieldValue('time')[1],
                            discount: Number(this.form.getFieldValue('discount')),
                            hotelId: Number(this.activeHotelId),
                            status: 1
                        }
                        this.addHotelTimeCoupon(data)
                        this.form.resetFields()
                    }
                    else{
                        const data={
                            name: this.form.getFieldValue('name'),
                            description: this.form.getFieldValue('description'),
                            type: Number(this.form.getFieldValue('type')),
                            discount: Number(this.form.getFieldValue('discount')),
                            hotelId: Number(this.activeHotelId),
                            status: 1
                        }
                        this.addBirthdayCoupon(data)
                        this.form.resetFields()
                    }
                }
            });
        },
    }
}
</script>