<template>
  <div class="hotelList">
      <div class="search-bar">
          <div class="search-cont"
          >
              <a-input style="margin-left: 30px" placeholder="输入酒店相关关键字" v-model="searchStr" />
          </div>
          <div class="search-cont">
              <a-button
                      type="primary" style="margin-left: 10px"
                      @click="handleSearch">查询</a-button>
          </div>
          <div class="search-cont">
              <a-button
                      type="primary" style="margin-left: 10px"
                      @click="getFilters">进阶筛选</a-button>
          </div>
          <div class="search-cont">
              <a-button
                      type="primary" style="margin-left: 10px"
                      @click="turnBack">显示所有酒店</a-button>
          </div>
      </div>
      <a-tabs>
        <a-tab-pane tab="卡片" key="1">
            <a-layout>
                <a-layout-content style="min-width: 800px">

                    <a-spin :spinning="hotelListLoading">
                        <div class="card-wrapper">
                            <HotelCard :hotel="item" v-for="item in hotelList" :key="item.index" @click.native="jumpToDetails(item.id)"></HotelCard>
                            <div v-for="item in emptyBox" :key="item.name" class="emptyBox ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
                            </div>
                            <a-pagination showQuickJumper :total="hotelList.totalElements" :defaultCurrent="1" @change="pageChange"></a-pagination>
                        </div>
                    </a-spin>
                </a-layout-content>
                <Filters></Filters>
            </a-layout>
        </a-tab-pane>
        <a-tab-pane tab="列表" key="2">
            <a-layout>
                <a-layout-content style="min-width: 800px" >
            <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
                    class="list_table"
            >
             <span slot="hotelStar" slot-scope="text">
                        <span v-if="text == 'Three'">三星级</span>
                        <span v-if="text == 'Four'">四星级</span>
                        <span v-if="text == 'Five'">五星级</span>
                    </span>
                <span slot="action" slot-scope="record">
                        <a-button type="primary" size="small" @click="jumpToDetails(record.id)" v-if="userInfo.userType!='Visitor'">预定</a-button>
                    <a-button type="primary" size="small" @click="jumpToDetails(record.id)" v-if="userInfo.userType=='Visitor'">查看</a-button>
                    </span>
            </a-table>
                </a-layout-content>
            </a-layout>
        </a-tab-pane>
    </a-tabs>

  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import Filters from './components/filter'
import { message } from 'ant-design-vue'
import { mapGetters, mapActions, mapMutations } from 'vuex'
const columns1 = [
    {
        title: '酒店名',
        dataIndex: 'name',
    },
    {
        title: '商圈',
        dataIndex: 'bizRegion',
    },
    {
        title: '地址',
        dataIndex: 'address',
    },
    {
        title: '酒店星级',
        filters: [{ text: '三星级', value: 'Three' }, { text: '四星级', value: 'Four' }, { text: '五星级', value: 'Five' }],
        onFilter: (value, record) => record.hotelStar.includes(value),
        dataIndex: 'hotelStar',
        scopedSlots: { customRender: 'hotelStar' }
    },
    {
        title: '评分',
        sorter:(a,b)=>a.rate-b.rate,
        dataIndex: 'rate',
    },
    {
        title:'最低价格',
        sorter:(a,b)=>a.minPrice-b.minPrice,
        dataIndex:'minPrice'
    },
    {
        title:'最高价格',
        sorter:(a,b)=>a.maxPrice-b.maxPrice,
        dataIndex:'maxPrice'
    },
    {
        title: '联系电话',
        dataIndex: 'phoneNum',
    },
    {
        title: '操作',
        key: 'action',
        scopedSlots: { customRender: 'action' },
    },
];

export default {
  name: 'home',
  components: {
    HotelCard,
      Filters
  },
  data(){
    return{
        columns1,
        searchStr: '',
        searchData: '',
        originHotelList:'',
        count:0,
        count_filter:0,
      emptyBox: [{ name: 'box1' }, { name: 'box2'}, {name: 'box3'}],
        inputs: [
            {
                title: "酒店名",
                model: 'input1',
            },
            {
                title: "商圈名",
                model: 'input1',
                width: 100
            },
        ],
        selects: [

        ],
    }
  },
  async mounted() {
    await this.getHotelList()
      console.log("HotelList is")
      console.log(this.hotelList)
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
        'filterVisible',
        'userInfo'
    ])
  },
  methods: {
    ...mapMutations([
      'set_hotelListParams',
      'set_hotelListLoading',
        'set_hotelList',
        'set_filterVisible',
    ]),
    ...mapActions([
      'getHotelList'
    ]),

    pageChange(page, pageSize) {
      const data = {
        pageNo: page - 1
      }
      this.set_hotelListParams(data)
      this.set_hotelListLoading(true)
      this.getHotelList()
    },
      handleSearch: function(){
        console.log("进入")
        this.searchData=[];
        console.log(this.count)
        if(this.count<1){
            this.originHotelList=this.hotelList
            console.log(this.originHotelList)
        }
        this.set_hotelList(this.originHotelList)
          console.log(this.hotelList)
          console.log(this.hotelList.rooms)
        for(let i=0;i<this.hotelList.length;i++){
            if(this.hotelList[i].name.indexOf(this.searchStr)>=0 || this.hotelList[i].bizRegion.indexOf(this.searchStr)>=0 || this.hotelList[i].address.indexOf(this.searchStr)>=0){
                this.searchData.push(this.hotelList[i]);
            }
        }
        this.set_hotelList(this.searchData);
        this.count++;
      },
      getFilters:function(){
          if(this.count_filter<1) {
              this.originHotelList = this.hotelList
          }
          this.count_filter++
          this.set_filterVisible(true)
      },
      turnBack:function(){
        if(this.count<1 && this.count_filter<1){
            message.error('您未进行任何搜索或者筛选')
        }
        else{
            this.set_hotelList(this.originHotelList)
        }
      },
    jumpToDetails(id){
      this.$router.push({ name: 'hotelDetail', params: { hotelId: id }})
    }
  }
}
</script>
<style scoped lang="less">
  .hotelList {
    text-align: center;
    padding: 50px 0;
    .emptyBox {
      height: 0;
      margin: 10px 10px
    }
    .card-wrapper{
      display: flex;
      justify-content: space-around;
      flex-wrap: wrap;
      flex-grow: 3;
      min-height: 800px;
        background-color: white;

    }
    .card-wrapper .card-item {
      margin: 30px;
      position: relative;
      height: 188px;
    }
      .search-bar{
          display: flex;
          flex-wrap: wrap;
      }
      .search-cont{
          display: flex;
          align-items: center;
          margin: 0px 0px 0px 0px;
      }
      .list_table{
          background-color: white;
      }
  }
</style>