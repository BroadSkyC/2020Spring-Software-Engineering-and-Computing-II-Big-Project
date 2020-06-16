package com.example.hotel.blImpl.order;

import com.example.hotel.bl.hotel.HotelService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.bl.user.AccountService;
import com.example.hotel.data.order.OrderMapper;
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

    @Override
    public ResponseVO addOrder(OrderVO orderVO) {
        int reserveRoomNum = orderVO.getRoomNum();
        try {
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
                orderMapper.addOrder(order);
                hotelService.updateRoomInfo(orderVO.getHotelId(),orderVO.getRoomType(),orderVO.getRoomNum());
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
        orderMapper.annulOrder(orderid);
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
        BeanUtils.copyProperties(orderVO,order);
        orderMapper.updateOrderState(order);
        return ResponseVO.buildSuccess(true);
    }

}
