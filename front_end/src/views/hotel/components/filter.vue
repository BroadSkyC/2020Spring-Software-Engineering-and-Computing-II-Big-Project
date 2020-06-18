<template>
    <a-modal
            :visible="filterVisible"
            title="进阶筛选"
            width="900px"
            cancelText="取消"
            okText="确定"
            @cancel="cancel"
            @ok="handleSubmit"
    >
        <a-form :form="form" style="margin-top: 30px" v-bind="formItemLayout">
            <a-form-item label="最低价格" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写您预期房间的最低价格"
                        v-decorator="['exp_min', { rules: [{ required: false, message: '请填写您预期房间的最低价格' }]  }]"
                />
            </a-form-item>
            <a-form-item label="最高价格" v-bind="formItemLayout">
                <a-input
                        placeholder="请填写您预期房间的最高价格"
                        v-decorator="['exp_max', { rules: [{ required: false, message: '请填写您预期房间的最高价格' }] }]"
                />
            </a-form-item>
            <a-form-item label="排列依据" v-bind="formItemLayout">
                <a-select
                        v-decorator="[
                    'sortType',
                    { rules: [{ required: true, message: '请选择排列依据' }] }]"
                >
                    <a-select-option value="star_descent">酒店星级从高到低</a-select-option>
                    <a-select-option value="star_rise">酒店星级从低到高</a-select-option>
                    <a-select-option value="rate_descent">酒店评分从高到低</a-select-option>
                    <a-select-option value="rate_rise">酒店评分从低到高</a-select-option>
                </a-select>
            </a-form-item>
        </a-form>
    </a-modal>
</template>

<script>
    import { mapGetters, mapMutations, mapActions } from 'vuex'
    export default {
        name: "filters",
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
                sortType: '',
                expMin: 0,
                expMax: 0,
                count:0,
                searchData:'',
                originHotelList:'',
            }
        },
        beforeCreate() {
            this.form = this.$form.createForm(this, { name: 'filters' });
        },
        computed: {
            ...mapGetters([
                'filterVisible',
                'hotelList'
            ])
        },
        mounted() {
        },
        methods: {
            ...mapMutations([
                'set_filterVisible',
                'set_hotelList',
            ]),
            ...mapActions([
                'getHotelList'
            ]),
            cancel(){
                this.set_filterVisible(false)
            },
            handleSubmit(e){
                console.log(this.hotelList)
                e.preventDefault();
                if(isNaN(Number(this.form.getFieldValue('exp_max'))) || Number(this.form.getFieldValue('exp_max'))===0){
                    console.log("yes")
                    this.form.setFieldsValue({"exp_max":"10000"});
                }
                if(isNaN(Number(this.form.getFieldValue('exp_min')))){
                    console.log("yes")
                    this.form.setFieldsValue({"exp_min":"0"});
                }
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            exp_min:Number(this.form.getFieldValue('exp_min')),
                            exp_max:Number(this.form.getFieldValue('exp_max')),
                            sortType: this.form.getFieldValue('sortType'),
                        }
                        console.log(data)
                        this.sortHotel(data)
                        this.set_filterVisible(false)
                    }
                });
            },
            sortHotel(data){
                this.sortType=data.sortType
                this.expMax=data.exp_max
                this.expMin=data.exp_min
                this.searchData=[]
                for(let i=0;i<this.hotelList.length;i++){
                    if(this.hotelList[i].minPrice<=this.expMax && this.hotelList[i].maxPrice>=this.expMin){
                        this.searchData.push(this.hotelList[i]);
                    }
                }
                this.set_hotelList(this.searchData)
                this.count++
                switch(this.sortType){
                    case 'star_descent':
                        this.hotelList.sort(this.compareDscStar('hotelStar'))
                        break
                    case 'star_rise':
                        this.hotelList.sort(this.compareRsStar('hotelStar'))
                        break
                    case 'rate_descent':
                        this.hotelList.sort(this.compareDsc('rate'))
                        break
                    case 'rate_rise':
                        this.hotelList.sort(this.compareRs('rate'))
                }
            },
            compareDsc(attr){
                return function(a,b){
                    var val1 = a[attr];
                    var val2 = b[attr];
                    return val2 - val1;
                }
            },
            compareRs(attr){
                return function(a,b){
                    var val1 = a[attr];
                    var val2 = b[attr];
                    return val1 - val2;
                }
            },
            compareDscStar(attr){
                return function (a,b) {
                    if(a[attr]=='Five' ){
                        if(b[attr]=='Five'){
                            return 0
                        }
                        else return -1;
                    }
                    else if(a[attr]=='Four'){
                        if(b[attr]=='Four'){
                            return 0;
                        }
                        else if( b[attr]=='Five'){
                            return 1;
                        }
                        else return -1;
                    }
                    else{
                        if(b[attr]=='Three'){
                            return 0;
                        }
                        else return 1;
                    }
                }
            },
            compareRsStar(attr){
                return function (a,b) {
                    if(a[attr]=='Five' ){
                        if(b[attr]=='Five'){
                            return 0
                        }
                        else return 1;
                    }
                    else if(a[attr]=='Four'){
                        if(b[attr]=='Four'){
                            return 0;
                        }
                        else if( b[attr]=='Five'){
                            return -1;
                        }
                        else return 1;
                    }
                    else{
                        if(b[attr]=='Three'){
                            return 0;
                        }
                        else return -1;
                    }
                }
            }
        }
    }

</script>

<style scoped>

</style>