<template>
    <div class="info-wrapper">
        <a-tabs>
            <a-tab-pane tab="我的信息" key="1">
                <a-form :form="form" style="margin-top: 30px">
                    <a-form-item label="头像" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <span v-if="modify">
                            <p v-if="this.imgLocalUrl" >
                                <a-avatar size="large"  v-bind:src=this.imgLocalUrl></a-avatar>
                            </p>
                            <div class="file-input">
                            <p class="input-container" >
                                <a-icon type="plus-circle" style="font-size: 50px" class="plus" />
                                 <input type="file" @change="Upload" accept="image/*"/>
                            </p>
                            </div>
                        </span>
                        <span v-else><a-avatar size="large"  v-bind:src=userInfo.imgUrl></a-avatar>
                        </span>
                    </a-form-item>
                    <a-form-item label="用户名" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1  }">
                        <a-input
                            placeholder="请填写用户名"
                            v-decorator="['userName', { rules: [{ required: true, message: userInfo.userName }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.userName }}</span>
                    </a-form-item>
                    <a-form-item label="邮箱" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <span>{{ userInfo.email }}</span>
                    </a-form-item>

                    <a-form-item label="手机号" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                        <a-input
                            placeholder="请填写手机号"
                            v-decorator="['phoneNumber', { rules: [{ required: true, message: userInfo.phoneNumber }] }]"
                            v-if="modify"
                        />
                        <span v-else>{{ userInfo.phoneNumber}}</span>
                    </a-form-item>
                    <a-form-item label="信用值" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }">
                    <span>{{userInfo.credit}} </span>
                        <a-button type="default" size="small" @click="showCreditRecord" v-if="userInfo.userType==='Client'">查看记录</a-button>
                </a-form-item>
                    <a-form-item label="密码" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="modify">
                        <a-input
                            placeholder="请输入新密码"
                            v-decorator="['password', { rules: [{ required: true, message: '请输入新密码' }] }]"
                        />
                    </a-form-item>
                    <a-form-item label="生日" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }"  v-if="userInfo.vipType==='Common'">
<!--                        <AWeekPicker  :format="format"-->
<!--                                      showTime-->
<!--                                      onChange={onChange}-->
<!--                                      v-decorator="['birthday',{rules: [{required:true,message: '请选择生日'}]}]"-->
<!--                                      v-if="modify"-->
<!--                                      />-->
                        <span>{{
                            userInfo.birthday==null? '': userInfo.birthday.substring(0,10)}}</span>
              </a-form-item>
                    <a-form-item label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="userInfo.vipType==='Common'">
                        <span>普通会员</span>
                    </a-form-item>
                    <a-form-item label="会员类型" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="userInfo.vipType==='Company'">
                        <span>企业会员</span>
                    </a-form-item>
                    <a-form-item label="企业名称" :label-col="{ span: 3 }" :wrapper-col="{ span: 8, offset: 1 }" v-if="userInfo.vipType==='Company'">
                        <span>{{ userInfo.company }}</span>
                    </a-form-item>
                    <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-if="modify">
                        <a-button type="primary" @click="saveModify">
                            保存
                        </a-button>
                        <a-button type="default" style="margin-left: 30px" @click="cancelModify">
                            取消
                        </a-button>
                    </a-form-item>
                     <a-form-item :wrapper-col="{ span: 12, offset: 5 }" v-else>
                        <a-button type="primary" @click="modifyInfo" v-if="userInfo.userType!='Visitor'">
                            修改信息
                        </a-button>
                         <a-button type="primary" style="margin-left: 30px" @click="showRegisterVip" v-if="userInfo.userType==='Client' && userInfo.vipType==null">
                             注册会员
                         </a-button>
                    </a-form-item>
                </a-form>
            </a-tab-pane>
            <a-tab-pane tab="我的订单" key="2" v-if="userInfo.userType!='Visitor'">
                <a-table
                    :columns="columns"
                    :dataSource="userOrderList"
                    bordered
                >
                    <span slot="price" slot-scope="text">
                        <span>￥{{ text }}</span>
                    </span>
                    <span slot="roomType" slot-scope="text">
                        <span v-if="text == 'BigBed'">大床房</span>
                        <span v-if="text == 'DoubleBed'">双床房</span>
                        <span v-if="text == 'Family'">家庭房</span>
                    </span>
                    <a-tag slot="orderState" color="blue" slot-scope="text">
                    {{ text }}
                </a-tag>
                    <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="showViewOrder(record)">查看</a-button>
                        <a-divider type="vertical" v-if="record.orderState == '已预订'"></a-divider>
                        <a-popconfirm
                            title="你确定撤销该笔订单吗？"
                            @confirm="confirmCancelOrder(record.id)"
                            @cancel="cancelCancelOrder"
                            okText="确定"
                            cancelText="取消"
                            v-if="record.orderState === '已预订'"
                        >
                            <a-button type="danger" size="small">撤销</a-button>
                        </a-popconfirm>
                        <a-divider type="vertical" v-if="record.orderState == '已完成' && !record.feedback"></a-divider>
                        <a-button type="primary" size="small" @click="showComment(record)" v-if="record.orderState === '已完成' && !record.feedback">评价</a-button>
                    </span>
                </a-table>
            </a-tab-pane>
        </a-tabs>
        <RegisterVip></RegisterVip>
        <ViewOrder></ViewOrder>
        <Comment></Comment>
        <CreditRecord></CreditRecord>
    </div>
</template>
<script>
    //引入一个变量存储是否完成过评价order的属性，若完成过则不可重复评价
import { mapGetters, mapMutations, mapActions } from 'vuex'
import RegisterVip from './components/RegisterVip'
import ViewOrder from "./components/viewOrder";
import Comment from "./components/comment";
import CreditRecord from "./components/creditRecord";
import { client, put, remove } from '../../utils/client';
const columns = [
    {
        title: '订单号',
        sorter:(a,b)=>a.id-b.id,
        dataIndex: 'id',
    },
    {
        title: '酒店名',
        sorter:(a,b)=>{return a.hotelName.localeCompare(b.hotelName)},
        dataIndex: 'hotelName',
    },
    {
        title: '房型',
        filters: [{ text: '大床房', value: 'BigBed' }, { text: '双床房', value: 'DoubleBed' }, { text: '家庭房', value: 'Family' }],
        onFilter: (value, record) => record.roomType.includes(value),
        dataIndex: 'roomType',
        scopedSlots: { customRender: 'roomType' }
    },
    {
        title: '入住时间',
        sorter:(a,b)=>{return a.checkInDate.localeCompare(b.checkInDate)},
        dataIndex: 'checkInDate',
        scopedSlots: { customRender: 'checkInDate' }
    },
    {
        title: '离店时间',
        sorter:(a,b)=>{return a.checkOutDate.localeCompare(b.checkOutDate)},
        dataIndex: 'checkOutDate',
        scopedSlots: { customRender: 'checkOutDate' }
    },
    {
        title: '入住人数',
        sorter:(a,b)=>a.peopleNum-b.peopleNum,
        dataIndex: 'peopleNum',
    },
    {
        title: '房价',
        sorter:(a,b)=>a.price-b.price,
        dataIndex: 'price',
    },
    {
        title: '状态',
        filters: [{ text: '已预订', value: '已预订' }, { text: '已撤销', value: '已撤销' }, { text: '已入住', value: '已入住' }],
        onFilter: (value, record) => record.orderState.includes(value),
        dataIndex: 'orderState',
        scopedSlots: { customRender: 'orderState' }
    },
    {
      title: '操作',
      key: 'action',
      scopedSlots: { customRender: 'action' },
    },

  ];
export default {
    name: 'info',
    data(){
        const format="YYYY-MM-DD "
        return {
            uploadClickTime:0,
            filePath:'',
            modify: false,
            formLayout: 'horizontal',
            pagination: {},
            columns,
            data: [],
            form: this.$form.createForm(this, { name: 'coordinated' }),
            format,
            loading: false,
            imgUrl: '',
            imgLocalUrl:'',
        }
    },
    components: {
        CreditRecord,
        Comment,
        ViewOrder,
        RegisterVip
    },
    computed: {
        ...mapGetters([
            'userId',
            'userInfo',
            'userOrderList',
            'userCreditRecord',
        ])
    },
    async mounted() {
        await this.getUserInfo()
        await this.getUserOrders()
        console.log(this.userId)
        await this.getUserCreditRecord(this.userId)
        console.log(this.userCreditRecord)
    },
    methods: {
        ...mapMutations([
            'set_RegisterVipVisible',
            'set_viewOrderVisible',
            'set_commentVisible',
            'set_currentOrder',
            'set_creditRecordVisible',
        ]),
        ...mapActions([
            'getUserInfo',
            'getUserOrders',
            'getUserCreditRecord',
            'updateUserInfo',
            'cancelOrder'
        ]),
        saveModify() {
           this.form.validateFields((err, values) => {
                if (!err) {
                    const data = {
                        userName: this.form.getFieldValue('userName'),
                        phoneNumber: this.form.getFieldValue('phoneNumber'),
                        password: this.form.getFieldValue('password'),
                        imgUrl:this.imgUrl,
                        // birthday: this.form.getFieldValue('birthday')
                    }
                    this.updateUserInfo(data).then(()=>{
                        this.filePath=''
                        this.uploadClickTime=0
                        this.imgUrl=''
                        this.modify = false
                        this.imgLocalUrl=''
                    })
                }
            });
        },
        modifyInfo() {
            setTimeout(() => {
                this.form.setFieldsValue({
                    'userName': this.userInfo.userName,
                    'phoneNumber': this.userInfo.phoneNumber,
                })
            }, 0)
            this.modify = true
        },
        showCreditRecord(){
            this.set_creditRecordVisible(true)
        },
        showRegisterVip(){
            if(this.userInfo.credit>=100) {
                this.set_RegisterVipVisible(true)
            }
            else{
                this.$message.error('必须信用值高于100才能注册成为会员')
            }
        },
        showComment(record){
            this.set_currentOrder(record)
            this.set_commentVisible(true)
        },
        showViewOrder(record){
            this.set_currentOrder(record)
            this.set_viewOrderVisible(true)
        },
        cancelModify() {
            if(this.filePath)remove(this.filePath);
            this.imgUrl=''
            this.modify = false
            this.filePath=''
            this.uploadClickTime=0
            this.imgLocalUrl=''
        },
        confirmCancelOrder(orderId){
            this.cancelOrder(orderId)
        },
        cancelCancelOrder() {

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
        Upload:function(e) {
            var file = e.target.files[0];
            const reader = new FileReader();
            this.imgLocalUrl=this.getObjectURL(file);
            reader.readAsDataURL(file);
            var urlData="";
            reader.onload = () => {
                this.uploadClickTime+=1;
                var url = reader.result;
                urlData = url;
                const base64 = urlData.split(',').pop();
                const fileType = urlData.split(';').shift().split(':').pop();
                // base64转blob
                const blob = this.toBlob(base64, fileType);
                reader.readAsArrayBuffer(blob);
                reader.onload =  (event) => {
                    const OSS = require('ali-oss');
                    // arrayBuffer转Buffer
                    const buffer = new OSS.Buffer(event.target.result);
                    // 上传
                    var fileName =`${Date.parse(new Date())}`+'.jpg';  //定义唯一的文件
                    put(fileName, buffer).then((result) => {
                        this.imgUrl = result.url;
                    }).catch(function (err) {
                        console.log(err);
                    });
                    if(this.uploadClickTime>1) {
                        remove(this.filePath);
                    }
                    this.filePath=fileName;
                }
                reader.onerror = function (error) {
                    console.log('Error: ', error);
                };
            };
        },
    }
}
</script>
<style>
    .avatar-uploader > .ant-upload {
        width: 64px;
        height: 64px;
    }
    .ant-upload-select-picture-card i {
        font-size: 32px;
        color: #999;
    }

    .ant-upload-select-picture-card .ant-upload-text {
        margin-top: 8px;
        color: #666;
    }
</style>
<style scoped lang="less">
    .info-wrapper {
        padding: 50px;
        .chart {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-top: 20px
        }
    }
</style>
<style lang="less">
    .info-wrapper {
        .ant-tabs-bar {
            padding-left: 30px
        }
        .items{
            margin-right: 10px;
        }
    }
</style>
<style lang="less">
    .file-input{
        text-align: center;
        line-height:50px;
        position:relative;
        left: 10px;
        border-radius: 100%;
        margin-left:-100px;
        margin-right:auto;
    }
    .file-input .input-container{
        width:50px;
        height:50px;
        text-align: center;
        border-radius: 100%;
        background-color: white;
        font-size:30px;
        margin-left:100px;
        margin-right:auto;
    }
    .file-input input{
        border-radius: 100%;
        height: 50px;
        width: 50px;
        position:absolute;
        text-align: center;
        margin-left:100px;
        margin-right:auto;
        left:0;
        top:0;
        opacity:0;
    }
    .abc2{
        margin-left: -257.5px;
        margin-top: -42px;
    }

</style>