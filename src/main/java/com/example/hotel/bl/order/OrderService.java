package com.example.hotel.bl.order;

import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;

import java.util.List;

/**
 * @Author: chenyizong
 * @Date: 2020-03-04
 */
public interface OrderService {

    /**
     * 预订酒店
     * @param orderVO
     * @return
     */
    ResponseVO addOrder(OrderVO orderVO);

    /**
     * 获得所有订单信息
     * @return
     */
    List<Order> getAllOrders();

    /**
     * 获得指定用户的所有订单信息
     * @param userid
     * @return
     */
    List<Order> getUserOrders(int userid);

    /**
     * 撤销订单
     * @param orderid
     * @return
     */
    ResponseVO annulOrder(int orderid);

    /**
     * 查看酒店的所有订单
     * @param hotelId
     * @return
     */
    List<Order> getHotelOrders(Integer hotelId);

    ResponseVO delOrder(OrderVO orderVO);

    /**
     *修改订单信息
     * @param  orderVO
     * @return
     */
    ResponseVO updateOrder(OrderVO orderVO);

    /**
     *修改订单评价
     * @param  orderVO
     * @return
     */
    ResponseVO updateComment(OrderVO orderVO);

    /**
     * 修改订单状态
     * @param
     * @return
     */
    ResponseVO updateOrderState(OrderVO orderVO);

}
