package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.data.order.OrderMapper;
import com.example.hotel.data.user.AccountMapper;
import com.example.hotel.blImpl.order.updateAvailable;
import com.example.hotel.po.Hotel;
import com.example.hotel.po.Order;
import com.example.hotel.po.User;
import com.example.hotel.vo.HotelVO;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
//import org.graalvm.compiler.core.common.type.ArithmeticOpTable;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
@Service
public class OrderServiceImpl implements OrderService {
    private final static String RESERVE_ERROR = "预订失败";
    private final static String ROOMNUM_LACK = "预订房间数量剩余不足";
    @Autowired
    OrderMapper orderMapper;
    @Autowired
    HotelService hotelService;
    @Autowired
    AccountService accountService;
    @Autowired
    OrderService orderService;
    @Autowired
    RoomMapper roomMapper;
    @Autowired
    AccountMapper accountMapper;

    updateAvailable updateAvailable;
    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();

        try {
/*            String availableRoom = roomMapper.getAvailableRoom(orderVO.getHotelId(), orderVO.getRoomType(),orderVO.getRoomPrice());
            LocalDate beginDate = LocalDate.parse(roomMapper.getBeginDate(orderVO.getHotelId(), orderVO.getRoomType(),orderVO.getRoomPrice()));
            String rooms[] = availableRoom.split(",");
            LocalDate checkin = LocalDate.parse(orderVO.getCheckInDate());
            LocalDate checkout = LocalDate.parse(orderVO.getCheckOutDate());

            */
            int curNum = hotelService.getRoomCurNum(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomPrice());
            if(reserveRoomNum>curNum){
                return ResponseVO.buildFailure(ROOMNUM_LACK);
            }
            try {
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = new Date(System.currentTimeMillis());
                String curdate = sf.format(date);
                orderVO.setCreateDate(curdate);
                orderVO.setOrderState("已预订");
                User user = accountService.getUserInfo(orderVO.getUserId());
                orderVO.setClientName(user.getUserName());
                orderVO.setPhoneNumber(user.getPhoneNumber());
                Order order = new Order();
                BeanUtils.copyProperties(orderVO,order);
                order.setRate(0.0);
                order.setFeedback("");
                //update availableRoom
                /*LocalDate beginDate = LocalDate.parse(roomMapper.getBeginDate(orderVO.getHotelId(), orderVO.getRoomType(),orderVO.getRoomPrice()));
                LocalDate checkin = LocalDate.parse(orderVO.getCheckInDate());
                LocalDate checkout = LocalDate.parse(orderVO.getCheckOutDate());
                String availableRoom = roomMapper.getAvailableRoom(orderVO.getHotelId(), orderVO.getRoomType(),orderVO.getRoomPrice());
                String rooms[] = availableRoom.split(",");
                int checkin_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkin);
                int checkout_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkout);
                String ordered_room[] = new String[checkout_to_begin-checkin_to_begin];
                availableRoom = "";
                for (int j=0;j<checkin_to_begin;j++){
                    availableRoom += rooms[j] + ",";
                }
                for (int i=checkin_to_begin;i<checkout_to_begin;i++){
                    String room = rooms[i];
                    int num = Integer.parseInt(room.split("\\*")[1])-orderVO.getRoomNum();
                    ordered_room[i-checkin_to_begin] = i + "*" + num;
                    if (i!=checkout_to_begin-1){
                        availableRoom += ordered_room[i-checkin_to_begin] + ",";
                    }else{
                        if (checkout_to_begin!=rooms.length-1){
                            availableRoom += ordered_room[i-checkin_to_begin] + ",";
                        }else {
                            availableRoom += ordered_room[i-checkin_to_begin];
                        }
                    }
                }
                if (checkout_to_begin!=rooms.length-1){
                    for (int k=checkout_to_begin;k<rooms.length;k++){
                        if (k!=rooms.length-1){
                            availableRoom += rooms[k] + ",";
                        }else {
                            availableRoom += rooms[k];
                        }
                    }
                }
                roomMapper.updateAvailableRoom(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomPrice(),availableRoom);*/
                updateAvailable.update(orderVO,true);
                orderMapper.addOrder(order);
                hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum(),orderVO.getRoomPrice());
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return ResponseVO.buildFailure(RESERVE_ERROR);
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
            return  ResponseVO.buildFailure(RESERVE_ERROR);
        }
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderMapper.getAllOrders();
    }

    @Override
    public List<Order> getUserOrders(int userid) {
        return orderMapper.getUserOrders(userid);
    }

    @Override
    public ResponseVO annulOrder(int orderid) {
        //取消订单逻辑的具体实现（注意可能有和别的业务类之间的交互）
        Order order = orderMapper.getOrderById(orderid);
/*        LocalDate beginDate = LocalDate.parse(roomMapper.getBeginDate(order.getHotelId(),order.getRoomType(),order.getRoomPrice()));
        LocalDate checkin = LocalDate.parse(order.getCheckInDate());
        LocalDate checkout = LocalDate.parse(order.getCheckOutDate());
        int checkin_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkin);
        int checkout_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkout);
        String availableRoom = roomMapper.getAvailableRoom(order.getHotelId(), order.getRoomType(),order.getRoomPrice());
        String rooms[] = availableRoom.split(",");
        String ordered_room[] = new String[checkout_to_begin-checkin_to_begin];
        availableRoom = "";
        for (int j=0;j<checkin_to_begin;j++){
            availableRoom += rooms[j] + ",";
        }
        for (int i=checkin_to_begin;i<checkout_to_begin;i++){
            String room = rooms[i];
            int num = Integer.parseInt(room.split("\\*")[1])+order.getRoomNum();
            ordered_room[i-checkin_to_begin] = i + "*" + num;
            if (i!=checkout_to_begin-1){
                availableRoom += ordered_room[i-checkin_to_begin] + ",";
            }else{
                if (checkout_to_begin!=rooms.length-1){
                    availableRoom += ordered_room[i-checkin_to_begin] + ",";
                }else {
                    availableRoom += ordered_room[i-checkin_to_begin];
                }
            }
        }
        if (checkout_to_begin!=rooms.length-1){
            for (int k=checkout_to_begin;k<rooms.length;k++){
                if (k!=rooms.length-1){
                    availableRoom += rooms[k] + ",";
                }else {
                    availableRoom += rooms[k];
                }
            }
        }*/
        LocalDate checkin = LocalDate.parse(order.getCheckInDate());
        LocalDate checkout = LocalDate.parse(order.getCheckOutDate());
        LocalDate today = LocalDate.now();
        int diff = (int)ChronoUnit.DAYS.between(today,checkin);
        accountMapper.updateUserCredit(order.getUserId(),-diff);
        //roomMapper.updateAvailableRoom(order.getHotelId(),order.getRoomType(),order.getRoomPrice(),availableRoom);
        orderMapper.annulOrder(orderid);
        updateAvailable.update(order,false);
        hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),order.getRoomNum(),order.getRoomPrice()*(-1));
        return ResponseVO.buildSuccess(true);
    }

    /**
     * @param hotelId
     * @return
     */
    @Override
    public List<Order> getHotelOrders(Integer hotelId) {
        List<Order> orders = orderService.getAllOrders();
        orders=orders.stream().filter(order -> order.getHotelId().equals(hotelId)).collect(Collectors.toList());
        //orders=orders.stream().filter(order -> !order.getOrderState().equals("已撤销")).collect(Collectors.toList());
        return orders;
    }
    
    @Override
    public List<Order> getManageOrders(Integer userId){
        List<HotelVO> Hotel=hotelService.retrieveManagerHotels(userId);
        List<Order> order;
        List<Order> all=new ArrayList<Order>();
        for(int i=0;i<Hotel.size();i++){
            order=orderService.getHotelOrders(Hotel.get(i).getId());
            for(int j=0;j<order.size();j++){
                all.add(order.get(j));
                
            }
        }
        return all;
    }

    @Override
    public ResponseVO delOrder(OrderVO orderVO){
        Order order=new Order();
        order.setId(orderVO.getId());
        orderMapper.deleteOrder(order);
        return  ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateOrder(OrderVO orderVO){
        Order order=new Order();
        BeanUtils.copyProperties(orderVO,order);
        order.setCheckInDate(order.getCheckInDate().substring(0,10));
        order.setCheckOutDate(order.getCheckOutDate().substring(0,10));
        orderMapper.updateOrder(order);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateComment(OrderVO orderVO){
        Order order=new Order();
        BeanUtils.copyProperties(orderVO,order);
        orderMapper.updateComment(order);
        hotelService.updateRate(order.getHotelId(),order.getRate());
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateOrderState(OrderVO orderVO){
        Order order=new Order();
        String state = orderVO.getOrderState();
        BeanUtils.copyProperties(orderVO,order);
        orderMapper.updateOrderState(order);
        if(state.equals("已完成")){
            hotelService.updateRoomInfo(order.getHotelId(),order.getRoomType(),order.getRoomNum(),order.getRoomPrice()*(-1));
            updateAvailable.update(order, false);
            accountMapper.updateUserCredit(order.getUserId(),Integer.parseInt(order.getPrice().toString()));
        }
        return ResponseVO.buildSuccess(true);
    }

}
