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
                sortType: ''
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
                'set_filterVisible'
            ]),
            ...mapActions([
                'getHotelList'
            ]),
            cancel(){
                this.set_filterVisible(false)
            },
            handleSubmit(e){
                console.log(this.hotelList)
                console.log(this.hotelList.hotelStar)
                e.preventDefault();
                this.form.validateFieldsAndScroll((err, values) => {
                    if (!err) {
                        const data = {
                            sortType: this.form.getFieldValue('sortType'),
                        }
                        this.sortHotel(data)
                        console.log(this.hotelList)
                        this.set_filterVisible(false)
                    }
                });
            },
            sortHotel(data){
                this.sortType=data.sortType
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