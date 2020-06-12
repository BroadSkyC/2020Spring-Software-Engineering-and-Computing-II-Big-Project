package com.example.hotel.controller.order;

import com.example.hotel.bl.order.OrderService;
import com.example.hotel.vo.OrderVO;
import com.example.hotel.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: chenyizong
 * @Date: 2020-02-29
 */


@RestController()
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/addOrder")
    public ResponseVO reserveHotel(@RequestBody OrderVO orderVO){
        return orderService.addOrder(orderVO);
    }

    @GetMapping("/getAllOrders")
    public ResponseVO retrieveAllOrders(){
        return ResponseVO.buildSuccess(orderService.getAllOrders());
    }

    @GetMapping("/{userid}/getUserOrders")
    public  ResponseVO retrieveUserOrders(@PathVariable int userid){
        return ResponseVO.buildSuccess(orderService.getUserOrders(userid));
    }

    @GetMapping("/{orderid}/annulOrder")
    public ResponseVO annulOrder(@PathVariable int orderid){
        return orderService.annulOrder(orderid);
    }

    //@GetMapping("/{hotelId}/allOrders")
    //public ResponseVO retrieveHotelOrders(@PathVariable Integer hotelId) {
        //return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    //}

    @PostMapping("/delOrder")
    public ResponseVO deleOrder(@RequestBody OrderVO orderVO){
        orderService.delOrder(orderVO);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/updateOrder")
    public ResponseVO updateOrder(@RequestBody OrderVO orderVO){
        orderService.updateOrder(orderVO);
        return ResponseVO.buildSuccess(true);
    }

    @PostMapping("/updateOrderState")
    public ResponseVO updateOrderState(@RequestBody OrderVO orderVO){
        orderService.updateOrderState(orderVO);
        return ResponseVO.buildSuccess(true);
    }

    @GetMapping("/{hotelId}/getHotelOrders")
    public  ResponseVO retrieveHotelOrders(@PathVariable int hotelId){
        Integer id=hotelId;
        Integer idd=hotelId;
        return ResponseVO.buildSuccess(orderService.getHotelOrders(hotelId));
    }
}
