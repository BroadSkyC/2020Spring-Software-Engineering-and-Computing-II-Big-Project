package com.example.hotel.vo;

public class HotelTargetRoomNumCouponVO extends CouponVO{
    private Integer hotelId;
    private Double discountMoney;
    private Integer targetRoomNum;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Double getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Double discountMoney) {
        this.discountMoney = discountMoney;
    }

    public Integer getTargetRoomNum(){return targetRoomNum;}

    public void setTargetRoomNum(int targetRoomNum){
        this.targetRoomNum = targetRoomNum;
    }
}
