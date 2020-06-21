const getters = {
  //user
  token: state => state.user.token,
  userId: state => state.user.userId,
  userInfo: state => state.user.userInfo,
  userOrderList: state => state.user.userOrderList,
  //hotel
  hotelListLoading: state => state.hotel.hotelListLoading,
  hotelList: state => state.hotel.hotelList,
  currentHotelInfo: state => state.hotel.currentHotelInfo,
  currentHotelId: state => state.hotel.currentHotelId,
  orderModalVisible: state => state.hotel.orderModalVisible,
  chooseDateVisible:  state => state.hotel.chooseDateVisible,
  registerVipVisible: state=> state.user.registerVipVisible,
  viewOrderVisible: state=>state.user.viewOrderVisible,
  commentVisible: state=>state.user.commentVisible,
  filterVisible:state=>state.user.filterVisible,
  currentOrderRoom: state => state.hotel.currentOrderRoom,
  orderMatchCouponList: state => state.hotel.orderMatchCouponList,
  checkInDate:state=>state.hotel.checkInDate,
  checkOutDate:state=>state.hotel.checkOutDate,
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
  receptionistVisible:state => state.hotelManager.receptionistVisible,
  //currentRoom: state=> state.hotelManager.currentRoom,
  modifyRoomVisible:state => state.hotelManager.modifyRoomVisible,
  addCouponVisible: state => state.hotelManager.addCouponVisible,
  addReceptionistVisible: state => state.hotelManager.addReceptionistVisible,
  activeHotelId: state => state.hotelManager.activeHotelId,
  updateOrderStateVisible: state=> state.hotelManager.updateOrderStateVisible,
  couponList: state => state.hotelManager.couponList,
  receptionistList: state => state.hotelManager.receptionistList,
  //receptionist
  receptionistOrderList: state=>state.hotelReceptionist.receptionistOrderList,
  currentOrrder:state=> state.hotelReceptionist.currentOrrder,
  updateOrderStatteVisible: state=> state.hotelReceptionist.updateOrderStatteVisible,
}
  
  export default getters