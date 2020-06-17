<template>
    <a-modal
        :visible="addHotelModalVisible"
        title="添加酒店"
        cancelText="取消"
        okText="确定"
        @cancel="cancel"
        @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="酒店名">
                <a-input
                    placeholder="请填写酒店名称"
                    v-decorator="['hotelName', { rules: [{ required: true, message: '请填写酒店名称' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店地址" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写酒店地址"
                    v-decorator="['address', { rules: [{ required: true, message: '请填写酒店地址' }] }]"
                />
            </a-form-item>
            <a-form-item label="所在商圈" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写酒店所在商圈"
                        v-decorator="['bizRegion', { rules: [{ required: true, message: '请填写酒店所在商圈' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店星级" v-bind="formItemLayout">
                <a-select
                    v-decorator="[
                    'hotelStar', 
                    { rules: [{ required: true, message: '请选择酒店星级' }] }]"
                    @change="changeStar"
                >
                  <a-select-option value="Three">三星级</a-select-option>
                  <a-select-option value="Four">四星级</a-select-option>
                  <a-select-option value="Five">五星级</a-select-option>
                </a-select>
            </a-form-item>
            <a-form-item label="评分" v-bind="formItemLayout">
                            <a-input
                                    placeholder="请填写酒店评分"
                                    v-decorator="['rate', { rules: [{ required: true, message: '请填写酒店评分' }] }]"
                            />
                        </a-form-item>
            <a-form-item label="手机号" v-bind="formItemLayout">
                <a-input
                    placeholder="请填写手机号"
                    v-decorator="['phoneNumber', { rules: [{ required: true, message: '请输入手机号' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店简介" v-bind="formItemLayout">
                <a-input
                    type="textarea"
                    :rows="4"
                    placeholder="请填写酒店简介"
                    v-decorator="['description', { rules: [{ required: true, message: '请填写酒店简介' }] }]"
                />
            </a-form-item>
            <a-form-item label="酒店图片" v-bind="formItemLayout">
                <input type="file" @change="Upload" accept="image/*"/>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script src="http://gosspublic.alicdn.com/aliyun-oss-sdk-4.4.4.min.js"></script>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex'
export default {
    name: 'addHotelModal',
    data() {
        return {
            imgUrl: '',
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
            'userId',
            'addHotelParams',
            'addHotelModalVisible'
        ])
    },
    beforeCreate() {
        this.form = this.$form.createForm(this, { name: 'addHotelModal' });
    },
    mounted() {

    },
    methods: {
        ...mapMutations([
            'set_addHotelParams',
            'set_addHotelModalVisible',
            'set_imageUrl'
        ]),
        ...mapActions([
            'addHotel'
        ]),
        cancel() {
            this.set_addHotelModalVisible(false)
        },
        changeStar(v){

        },
        toBlob(urlData,fileType) {
            let bytes = window.atob(urlData);
            let n = bytes.length;
            let u8arr = new Uint8Array(n);
            while (n--) {
                u8arr[n] = bytes.charCodeAt(n);
            }
            return new Blob([u8arr], { type: fileType });
        },
        Upload:function(e) {
            var fileName = 'seec' + `${Date.parse(new Date())}`+'.jpg';  //定义唯一的文件名
            var file = e.target.files[0];
            const reader = new FileReader();
            reader.readAsDataURL(file);
            var urlData="";
            reader.onload = () => {
                var url = reader.result;
                urlData = url;
                const base64 = urlData.split(',').pop();
                const fileType = urlData.split(';').shift().split(':').pop();
                // base64转blob
                const blob = this.toBlob(base64, fileType);
                reader.readAsArrayBuffer(blob);
                reader.onload =  (event) => {
                    const OSS = require('ali-oss');
                    const client = new OSS({
                        region: 'oss-cn-shanghai',
                        accessKeyId: 'LTAI4GDPCV3LpbpnQjCoyXNC',
                        accessKeySecret: 'DXdQfTosIkscieZ1zBn1F2RX3Q0jnT',
                        bucket: 'seec67'
                    });

                    // arrayBuffer转Buffer
                    const buffer = new OSS.Buffer(event.target.result);
                    // 上传
                    client.put(fileName, buffer).then((result)=> {
                        // console.log(result.url);
                       this.imgUrl=result.url;
                    }).catch(function (err) {
                        console.log(err);
                    });
                }
                reader.onerror = function (error) {
                    console.log('Error: ', error);
                };
            };
        },
        handleSubmit(e) {
            e.preventDefault();
            this.form.validateFieldsAndScroll((err, values) => {
                if (!err) {
                    const data = {
                        name: this.form.getFieldValue('hotelName'),
                        description: this.form.getFieldValue('description'),
                        address: this.form.getFieldValue('address'),
                        phoneNum: this.form.getFieldValue('phoneNumber'),
                        hotelStar: this.form.getFieldValue('hotelStar'),
                        rate: this.form.getFieldValue('rate'),
                        bizRegion:this.form.getFieldValue('bizRegion'),
                        managerId: Number(this.userId),
                        imgUrl:this.imgUrl
                    }
                    this.set_addHotelParams(data)
                    this.addHotel()
                }
            });
        },
    }
}
</script>