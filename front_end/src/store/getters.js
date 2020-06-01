const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  registerVipVisible: state=> state.user.registerVipVisible,
  viewOrderVisible: state=>state.user.viewOrderVisible,
  filterVisible:state=>state.user.filterVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  //admin
  managerList: state => state.admin.managerList,
  addManagerModalVisible: state => state.admin.addManagerModalVisible,
  addManagerParams: state => state.admin.addManagerParams,
  //hotelManager
  orderList: state => state.hotelManager.orderList,
  currentOrder:state=> state.hotelManager.currentOrder,
  orderVisible:state=> state.hotelManager.orderVisible,
  addHotelModalVisible: state => state.hotelManager.addHotelModalVisible,
  addRoomModalVisible: state => state.hotelManager.addRoomModalVisible,
  couponVisible: state => state.hotelManager.couponVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  updateOrderStateVisible: state=> state.hotelManager.updateOrderStateVisible,

  couponList: state => state.hotelManager.couponList,
  }
  
  export default getters