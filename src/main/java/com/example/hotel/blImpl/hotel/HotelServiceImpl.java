package com.example.hotel.blImpl.hotel;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.hotel.RoomService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.coupon.CouponMapper;
import com.example.hotel.data.hotel.HotelMapper;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;
import com.example.hotel.enums.UserType;
import com.example.hotel.po.*;
import com.example.hotel.util.ServiceException;
import com.example.hotel.vo.CouponVO;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.RoomVO;
import com.example.hotel.vo.SearchRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelMapper hotelMapper;

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private OrderService orderService;

    public HotelServiceImpl() {
    }


    @Override
    public void addHotel(HotelVO hotelVO) throws ServiceException {
        User manager = accountService.getUserInfo(hotelVO.getManagerId());
        if(manager == null || !manager.getUserType().equals(UserType.HotelManager)){
            throw new ServiceException("管理员不存在或者无权限！创建酒店失败！");
        }
        Hotel hotel = new Hotel();
        hotel.setDescription(hotelVO.getDescription());
        hotel.setAddress(hotelVO.getAddress());
        hotel.setHotelName(hotelVO.getName());
        hotel.setPhoneNum(hotelVO.getPhoneNum());
        hotel.setManagerId(hotelVO.getManagerId());
        hotel.setRate(hotelVO.getRate());
        hotel.setBizRegion(hotelVO.getBizRegion());
        hotel.setMinPrice(0);
        hotel.setMaxPrice(0);
        hotel.setCommentTime(1);
        hotel.setImgUrl(hotelVO.getImgUrl());
        //hotel.setBizRegion(BizRegion.valueOf(hotelVO.getBizRegion()));
        hotel.setHotelStar(HotelStar.valueOf(hotelVO.getHotelStar()));
        hotelMapper.insertHotel(hotel);
    }

    @Override
    public void updateRoomInfo(Integer hotelId, String roomType, Integer rooms,Double price) {
        roomService.updateRoomInfo(hotelId,roomType,rooms,price);
    }

    @Override
    public int getRoomCurNum(Integer hotelId, String roomType,Double price) {
        return roomService.getRoomCurNum(hotelId,roomType,price);
    }

    @Override
    public List<HotelVO> retrieveHotels() {
        return hotelMapper.selectAllHotel();
    }

    @Override
    public List<HotelVO> retrieveManagerHotels(Integer userId){
        List<HotelVO> HotelO=hotelMapper.selectAllHotel();
        HotelO=HotelO.stream().filter(Hotel -> Hotel.getManagerId().equals(userId)).collect(Collectors.toList());
        return HotelO;
    }

    @Override
    public HotelVO retrieveHotelDetails(Integer hotelId) {
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        List<HotelRoom> rooms = roomService.retrieveHotelRoomInfo(hotelId);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            roomVO.setBeginDate(r.getBeginDate());
            roomVO.setEndDate(r.getEndDate());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public HotelVO retrieveHotelDetails_setDate(SearchRoom searchRoom) {
        HotelVO hotelVO = hotelMapper.selectById(searchRoom.getHotelId());
        List<HotelRoom> rooms = roomService.retrieveAvailableRoomInfo(searchRoom);
        List<RoomVO> roomVOS = rooms.stream().map(r -> {
            RoomVO roomVO = new RoomVO();
            roomVO.setId(r.getId());
            roomVO.setPrice(r.getPrice());
            roomVO.setRoomType(r.getRoomType().toString());
            roomVO.setCurNum(r.getCurNum());
            roomVO.setTotal(r.getTotal());
            return roomVO;
        }).collect(Collectors.toList());
        hotelVO.setRooms(roomVOS);

        return hotelVO;
    }

    @Override
    public void updateRate(Integer hotelId,Double rate){
        HotelVO hotelVO = hotelMapper.selectById(hotelId);
        double d=(hotelVO.getRate()*hotelVO.getCommentTime()+rate)/(hotelVO.getCommentTime()+1);
        d=(double) Math.round(d*10)/10;
        hotelVO.setRate(d);
        hotelVO.setCommentTime(hotelVO.getCommentTime()+1);
        hotelMapper.updateRate(hotelId,hotelVO.getRate(),hotelVO.getCommentTime());
    }

    @Override
    public void delHotel(HotelVO hotelVO) {
        Hotel hotel = new Hotel();
        hotel.setId(hotelVO.getId());
        List<HotelRoom> rooms=roomMapper.selectRoomsByHotelId(hotel.getId());
        List<Coupon> coupons= couponMapper.selectByHotelId(hotel.getId());
        hotelMapper.deleteHotel(hotel);
        for(int i=0;i<coupons.size();i++) couponMapper.deleteCoupon(coupons.get(i));
        for(int i=0;i<rooms.size();i++) roomMapper.deleRoom(rooms.get(i));
    }

    @Override
    public void delRoom(RoomVO roomVO) {
        HotelRoom hr = roomMapper.getRoomById(roomVO.getId());
        int hotel_id = hr.getHotelId();
        HotelRoom hotelRoom=new HotelRoom();
        hotelRoom.setId(roomVO.getId());
        roomMapper.deleRoom(hotelRoom);
        List<HotelRoom> hotelRooms = roomService.retrieveHotelRoomInfo(hotel_id);
        if (hotelRooms.size()==0){
            hotelMapper.updateMaxPrice(hotel_id, 0.0);
            hotelMapper.updateMinPrice(hotel_id,0.0);
        }else {
            double maxPrice = hotelRooms.get(0).getPrice();
            double minPrice = hotelRooms.get(0).getPrice();
            for (HotelRoom room : hotelRooms) {
                if (room.getPrice() <= minPrice) {
                    minPrice = room.getPrice();
                }
                if (room.getPrice() >= maxPrice) {
                    maxPrice = room.getPrice();
                }
            }
            hotelMapper.updateMaxPrice(hotel_id, maxPrice);
            hotelMapper.updateMinPrice(hotel_id, minPrice);
        }
    }

    @Override
    public List<Comment> retrieveHotelsComments(Integer hotelId){
        List<Comment> comments =new ArrayList<Comment>();
        List<Order> orders=orderService.getHotelOrders(hotelId);
        orders=orders.stream().filter(order -> !order.getFeedback().equals("")).collect(Collectors.toList());
        for (Order order : orders) {
            Comment comment = new Comment();
            comment.setFeedback(order.getFeedback());
            comment.setRate(order.getRate());
            comment.setCheckInDate(order.getCheckInDate());
            comment.setCheckOutDate(order.getCheckOutDate());
            User user = accountService.getUserInfo(order.getUserId());
            double k=user.getCredit();
            if(k<=100)
                comment.setCreditGrade("信用一般");
            else if(k<=500)
                comment.setCreditGrade("信用良好");
            else
                comment.setCreditGrade("信用极佳");
            comment.setImgUrl(user.getImgUrl());
            String name=user.getUserName();
            StringBuilder s= new StringBuilder(name.substring(0, 1));
            if(name.length()==2)
                s.append('*');
            else if(name.length()>2){
                for(int i=0;i<name.length()-2;i++){
                    s.append('*');
                }
                s.append(name.substring(name.length()-1));
            }
            comment.setUserName(s.toString());
            comments.add(comment);
        }
        return comments;
    }
}
