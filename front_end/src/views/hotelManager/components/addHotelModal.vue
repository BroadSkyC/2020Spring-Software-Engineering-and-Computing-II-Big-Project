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
            <a-form-item label="酒店封面图片" v-bind="formItemLayout" class="abc">
                <p class="row" id="imgbox" v-if="this.imgLocalUrl">
                    <img id="image" v-bind:src=this.imgLocalUrl>
                </p>
                <div class="file-input">
                    <p class="input-container" >
                        <a-icon type="plus-square" style="font-size: 70px" class="plus" />
                        <input type="file" @change="Upload1" accept="image/*"/>
                    </p>
                </div>
            </a-form-item>
            <a-form-item label="酒店展示图片" v-bind="formItemLayout" class="abc">
                <p class="row" id="imgbox1" v-if="this.imgLocalUrl1">
                    <img id="image1" v-bind:src=this.imgLocalUrl1>
                </p>
                <a-divider v-if="this.imgLocalUrl2"/>
                <p class="row" id="imgbox2" v-if="this.imgLocalUrl2">
                    <img id="image2" v-bind:src=this.imgLocalUrl2>
                </p>
                <a-divider v-if="this.imgLocalUrl3"/>
                <p class="row" id="imgbox3" v-if="this.imgLocalUrl3">
                    <img id="image3" v-bind:src=this.imgLocalUrl3>
                </p>
                <div class="file-input">
                    <p class="input-container" >
                        <a-icon type="plus-square" style="font-size: 70px" class="plus" />
                        <input type="file" @change="Upload" accept="image/*"  multiple="multiple"/>
                    </p>
                </div>
            </a-form-item>
        </a-form>
    </a-modal>
</template>
<script>
import { mapGetters, mapMutations, mapActions } from 'vuex';
import { client, put, remove } from '../../../utils/client';
export default {
    name: 'addHotelModal',
    data() {
        return {
            imgUrl:'',
            imgUrl1: '',
            imgUrl2: '',
            imgUrl3: '',
            imgLocalUrl:'',
            imgLocalUrl1:'',
            imgLocalUrl2:'',
            imgLocalUrl3:'',
            filePath:'',
            filePath1:'',
            filePath2:'',
            filePath3:'',
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
            if(this.filePath)remove(this.filePath);
            if(this.filePath1)remove(this.filePath1);
            if(this.filePath2)remove(this.filePath2);
            if(this.filePath3)remove(this.filePath3);
            this.imgUrl=''
            this.imgUrl1=''
            this.imgUrl2=''
            this.imgUrl3=''
            this.imgLocalUrl=''
            this.imgLocalUrl1=''
            this.imgLocalUrl2=''
            this.imgLocalUrl3=''
            this.filePath=''
            this.filePath1=''
            this.filePath2=''
            this.filePath3=''
            this.set_addHotelModalVisible(false)
        },
        changeStar(v){

        },
        getObjectURL(file) {
            var url = null ;
            // 下面函数执行的效果是一样的，只是需要针对不同的浏览器执行不同的 js 函数而已
            if (window.createObjectURL!=undefined) { // basic
                url = window.createObjectURL(file) ;
            } else if (window.URL!=undefined) { // mozilla(firefox)
                url = window.URL.createObjectURL(file) ;
            } else if (window.webkitURL!=undefined) { // webkit or chrome
                url = window.webkitURL.createObjectURL(file) ;
            }
            return url ;
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
        Upload1:function(e) {
            var file=e.target.files[0];
            const reader = new FileReader();
            this.imgLocalUrl = this.getObjectURL(file);
            reader.readAsDataURL(file);
            var urlData = "";
            reader.onload = () => {
                var url = reader.result;
                urlData = url;
                const base64 = urlData.split(',').pop();
                const fileType = urlData.split(';').shift().split(':').pop();
                // base64转blob
                const blob = this.toBlob(base64, fileType);
                reader.readAsArrayBuffer(blob);
                reader.onload = (event) => {
                    const OSS = require('ali-oss');
                    // arrayBuffer转Buffer
                    const buffer = new OSS.Buffer(event.target.result);
                    // 上传
                    var fileName = `${Date.parse(new Date())}` + '.jpg';  //定义唯一的文件
                    this.filePath=fileName
                    put(fileName, buffer).then((result) => {
                        this.imgUrl = result.url;
                    }).catch(function (err) {
                        console.log(err);
                    });

                }
                reader.onerror = function (error) {
                    console.log('Error: ', error);
                };
            };
        },
        Upload:function(e) {
            if(e.target.files.length>3 ){
                alert('只能上传3张图片，不得多于3张！');
            }else if(e.target.files.length<3 ){
                alert('必须上传3张图片，不得少于3张！');
            }
            else {
                var file1 = e.target.files[0];
                var file2 = e.target.files[1];
                var file3 = e.target.files[2];
                const reader1 = new FileReader();
                const reader2 = new FileReader();
                const reader3 = new FileReader();
                this.imgLocalUrl1 = this.getObjectURL(file1);
                this.imgLocalUrl2 = this.getObjectURL(file2);
                this.imgLocalUrl3 = this.getObjectURL(file3);
                reader1.readAsDataURL(file1);
                var urlData = "";
                reader1.onload = () => {
                    var url = reader1.result;
                    urlData = url;
                    const base64 = urlData.split(',').pop();
                    const fileType = urlData.split(';').shift().split(':').pop();
                    // base64转blob
                    const blob = this.toBlob(base64, fileType);
                    reader1.readAsArrayBuffer(blob);
                    reader1.onload = (event) => {
                        const OSS = require('ali-oss');
                        // arrayBuffer转Buffer
                        const buffer = new OSS.Buffer(event.target.result);
                        // 上传
                        var fileName = `${Date.parse(new Date())}` + '1.jpg';  //定义唯一的文件
                        this.filePath1=fileName
                        put(fileName, buffer).then((result) => {
                            this.imgUrl1 = result.url;
                        }).catch(function (err) {
                            console.log(err);
                        });

                    }
                    reader1.onerror = function (error) {
                        console.log('Error: ', error);
                    };
                };
                reader2.readAsDataURL(file2);
                urlData = "";
                reader2.onload = () => {
                    var url = reader2.result;
                    urlData = url;
                    const base64 = urlData.split(',').pop();
                    const fileType = urlData.split(';').shift().split(':').pop();
                    // base64转blob
                    const blob = this.toBlob(base64, fileType);
                    reader2.readAsArrayBuffer(blob);
                    reader2.onload = (event) => {
                        const OSS = require('ali-oss');
                        // arrayBuffer转Buffer
                        const buffer = new OSS.Buffer(event.target.result);
                        // 上传
                        var fileName = `${Date.parse(new Date())}` + '2.jpg';  //定义唯一的文件
                        this.filePath2=fileName
                        put(fileName, buffer).then((result) => {
                            this.imgUrl2 = result.url;
                        }).catch(function (err) {
                            console.log(err);
                        });

                    }
                    reader2.onerror = function (error) {
                        console.log('Error: ', error);
                    };
                };
                reader3.readAsDataURL(file3);
                urlData = "";
                reader3.onload = () => {
                    var url = reader3.result;
                    urlData = url;
                    const base64 = urlData.split(',').pop();
                    const fileType = urlData.split(';').shift().split(':').pop();
                    // base64转blob
                    const blob = this.toBlob(base64, fileType);
                    reader3.readAsArrayBuffer(blob);
                    reader3.onload = (event) => {
                        const OSS = require('ali-oss');
                        // arrayBuffer转Buffer
                        const buffer = new OSS.Buffer(event.target.result);
                        // 上传
                        var fileName = `${Date.parse(new Date())}` + '3.jpg';  //定义唯一的文件
                        this.filePath3=fileName
                        put(fileName, buffer).then((result) => {
                            this.imgUrl3 = result.url;
                        }).catch(function (err) {
                            console.log(err);
                        });

                    }
                    reader3.onerror = function (error) {
                        console.log('Error: ', error);
                    };
                };
            }
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
                        imgUrl:this.imgUrl,
                        imgUrl1:this.imgUrl1,
                        imgUrl2:this.imgUrl2,
                        imgUrl3:this.imgUrl3,
                    }
                    this.imgUrl=''
                    this.imgUrl1=''
                    this.imgUrl2=''
                    this.imgUrl3=''
                    this.imgLocalUrl=''
                    this.imgLocalUrl1=''
                    this.imgLocalUrl2=''
                    this.imgLocalUrl3=''
                    this.filePath=''
                    this.filePath1=''
                    this.filePath2=''
                    this.filePath3=''
                    this.set_addHotelParams(data)
                    this.addHotel()
                }
            });
        },
    }
}
</script>
<style>
    .plus{
        margin-left: -10px;
        margin-top: -4px;
        width: 60px;
        height: 60px;
    }
    .abc{
        margin-top: 50px;
    }
    .abc2{
        margin-left: -58px;
        margin-top: -44px;
    }
    .file-input{
        text-align: center;
        line-height:60px;
        position:relative;
        left: 10px;
        margin-left:0px;
        margin-right:auto;
    }
    .file-input .input-container{
        width:60px;
        height:60px;
        text-align: center;
        background:gainsboro;
        color:black;
        border-radius:3px;
        font-size:30px;
        margin-left:100px;
        margin-right:auto;
    }
    .file-input input{
        height: 60px;
        width: 60px;
        position:absolute;
        text-align: center;
        margin-left:100px;
        margin-right:auto;
        left:0;
        top:0;
        opacity:0;
    }
    #imgbox{

        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #image{
        width: auto;/*图片长宽自适应*/
        height: auto;
        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #imgbox1{

        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #image1{
        width: auto;/*图片长宽自适应*/
        height: auto;
        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #imgbox2{

        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #image2{
        width: auto;/*图片长宽自适应*/
        height: auto;
        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #imgbox3{

        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
    #image3{
        width: auto;/*图片长宽自适应*/
        height: auto;
        max-width:100%;
        max-height: 400px;
        vertical-align: center;
    }
</style>