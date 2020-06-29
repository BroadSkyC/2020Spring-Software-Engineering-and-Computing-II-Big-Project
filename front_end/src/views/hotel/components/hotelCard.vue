<template>
  <a-card hoverable class="hotelCard ant-col-xs-7 ant-col-lg-5 ant-col-xxl-3">
    <img
      alt="example"
      v-bind:src= hotel.imgUrl
      slot="cover"
      referrerPolicy="no-referrer"
    />
    <a-tooltip :title="hotel.title" placement="top">
      <a-card-meta :title="hotel.name">
      <template slot="description">
              <a style="font-size: 15px" /> {{hotel.address}}<br/>
              <a-rate style="font-size: 15px" :value="rateValue" disabled allowHalf /> {{hotel.rate}}分<br/>
              <a style="font-size: 15px" /> 价格区间:{{hotel.minPrice}} ~ {{hotel.maxPrice}}元
            </template>
    </a-card-meta>
    </a-tooltip>
  </a-card>
</template>
<script>
export default {
    name: '',
    props: {
        hotel: {}
    },
    data() {
        return {
            rateValue: 0,
        }
    },
    mounted() {
        this.set_rateValue(this.hotel.rate)
    },
    methods:{
        set_rateValue(data){
            if(data-parseInt(data)>=0.5){
                this.rateValue=parseInt(data)+0.5
            }
            else{
                this.rateValue=data
            }
        }
    }
}
</script>
<style scoped lang="less">
    .hotelCard {
        margin: 10px 10px;
        min-width: 160px;
        max-height: 380px;
        img {
          height: 250px;
        }
    }
</style>

<style lang="less">
.hotelCard{
  .ant-card-body{
    padding: 12px
  }
}

.ant-card-hoverable:hover {
  box-shadow: 0 2px 8px rgba(0,0,0,0.4)
}

</style>