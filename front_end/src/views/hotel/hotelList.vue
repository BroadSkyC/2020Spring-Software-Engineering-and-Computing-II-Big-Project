<template>
  <div class="hotelList">
    <a-tabs>
        <a-tab-pane tab="图标" key="1">
            <a-layout>
                <a-layout-content style="min-width: 800px">
                    <div class="search-bar">
                        <div class="search-cont"
                        >
                            <a-input style="margin-left: 30px" placeholder="输入酒店名或商圈关键字" v-model="searchStr" />
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
                    </div>
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
            <a-table
                    :columns="columns1"
                    :dataSource="hotelList"
                    bordered
            >
            </a-table>
        </a-tab-pane>
    </a-tabs>

  </div>
</template>
<script>
import HotelCard from './components/hotelCard'
import Filters from './components/filter'
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
        dataIndex: 'hotelStar'
    },
    {
        title: '评分',
        dataIndex: 'rate',
    },
    {
        title: '简介',
        dataIndex: 'description',
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
        searchStr: '',
        searchData: '',
        originHotelList:'',
        count:0,
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
  },
  computed: {
    ...mapGetters([
      'hotelList',
      'hotelListLoading',
        'filterVisible'
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
        for(let i=0;i<this.hotelList.length;i++){
            if(this.hotelList[i].name.indexOf(this.searchStr)>=0 || this.hotelList[i].bizRegion.indexOf(this.searchStr)>=0){
                this.searchData.push(this.hotelList[i]);
            }
        }
        this.set_hotelList(this.searchData);
        this.count++;
      },
      getFilters:function(){
        this.set_filterVisible(true)
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
      min-height: 800px
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
  }
</style>