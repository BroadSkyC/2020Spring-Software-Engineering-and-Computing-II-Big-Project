package com.example.hotel.blImpl.order;

import com.example.hotel.data.hotel.RoomMapper;
import com.example.hotel.enums.RoomType;
import com.example.hotel.po.Order;
import com.example.hotel.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ReportAsSingleViolation;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class updateAvailable {
    @Autowired
    RoomMapper roomMapper;

    public void update(Order order, boolean flag){
        int f = 1;
        if (!flag){
            f = -1;
        }
        LocalDate beginDate = LocalDate.parse(roomMapper.getBeginDate(order.getHotelId(), order.getRoomType(),order.getRoomPrice()));
        LocalDate checkin = LocalDate.parse(order.getCheckInDate());
        LocalDate checkout = LocalDate.parse(order.getCheckOutDate());
        String availableRoom = roomMapper.getAvailableRoom(order.getHotelId(), order.getRoomType(),order.getRoomPrice());
        String rooms[] = availableRoom.split(",");
        int checkin_to_begin = (int) ChronoUnit.DAYS.between(beginDate,checkin);
        int checkout_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkout);
        String ordered_room[] = new String[checkout_to_begin-checkin_to_begin];
        availableRoom = "";
        for (int j=0;j<checkin_to_begin;j++){
            availableRoom += rooms[j] + ",";
        }
        for (int i=checkin_to_begin;i<checkout_to_begin;i++){
            String room = rooms[i];
            int num = Integer.parseInt(room.split("\\*")[1])-order.getRoomNum()*f;
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
        roomMapper.updateAvailableRoom(order.getHotelId(),order.getRoomType(),order.getRoomPrice(),availableRoom);
    }

    public void update(OrderVO order, boolean flag){
        int f = 1;
        if (!flag){
            f = -1;
        }
        LocalDate beginDate = LocalDate.parse(roomMapper.getBeginDate(order.getHotelId(), order.getRoomType(),order.getRoomPrice()));
        LocalDate checkin = LocalDate.parse(order.getCheckInDate());
        LocalDate checkout = LocalDate.parse(order.getCheckOutDate());
        String availableRoom = roomMapper.getAvailableRoom(order.getHotelId(), order.getRoomType(),order.getRoomPrice());
        String rooms[] = availableRoom.split(",");
        int checkin_to_begin = (int) ChronoUnit.DAYS.between(beginDate,checkin);
        int checkout_to_begin = (int)ChronoUnit.DAYS.between(beginDate,checkout);
        String ordered_room[] = new String[checkout_to_begin-checkin_to_begin];
        availableRoom = "";
        for (int j=0;j<checkin_to_begin;j++){
            availableRoom += rooms[j] + ",";
        }
        for (int i=checkin_to_begin;i<checkout_to_begin;i++){
            String room = rooms[i];
            int num = Integer.parseInt(room.split("\\*")[1])-order.getRoomNum();
            ordered_room[i-checkin_to_begin] = i + "*" + num*f;
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
        roomMapper.updateAvailableRoom(order.getHotelId(),order.getRoomType(),order.getRoomPrice(),availableRoom);
    }
}
