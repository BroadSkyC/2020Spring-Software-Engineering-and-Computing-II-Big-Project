package com.example.hotel.po;

import com.example.hotel.enums.RoomType;

public class HotelRoom {
    private Integer id;
    private RoomType roomType;
    private Integer hotelId;
    private double price;
    /**
     * 当前剩余可预定房间数
     */
    private int curNum;
    /**
     * 某类型房间总数
     */
    private int total;

    private String beginDate;
    private String endDate;
    private Integer alldays;
    private String availableRoom;


    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getAlldays() {
        return alldays;
    }

    public void setAlldays(Integer alldays) {
        this.alldays = alldays;
    }

    public String getAvailableRoom() {
        return availableRoom;
    }


    public void setAvailableRoom(String availableRoom) {
        this.availableRoom = availableRoom;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCurNum() {
        return curNum;
    }

    public void setCurNum(int curNum) {
        this.curNum = curNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
