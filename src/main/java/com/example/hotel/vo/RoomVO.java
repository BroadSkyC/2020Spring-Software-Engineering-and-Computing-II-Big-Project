package com.example.hotel.vo;

import com.example.hotel.enums.RoomType;

public class RoomVO {
    private Integer id;
    private String roomType;
    private Double price;
    private Integer curNum;
    private Integer total;
    private String beginDate;
    private String endDate;
    private Integer alldays;

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

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

    public String getAvaliableRoom() {
        return avaliableRoom;
    }

    public void setAvaliableRoom(String avaliableRoom) {
        this.avaliableRoom = avaliableRoom;
    }

    private String avaliableRoom;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getCurNum() {
        return curNum;
    }

    public void setCurNum(Integer curNum) {
        this.curNum = curNum;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
