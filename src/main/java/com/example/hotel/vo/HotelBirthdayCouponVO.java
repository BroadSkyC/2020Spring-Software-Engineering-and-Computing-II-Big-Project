package com.example.hotel.vo;

public class HotelBirthdayCouponVO extends CouponVO{
    // TODO: 2020/5/25
    private Integer hotelId;

    private Integer discountMoney;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(Integer discountMoney) {
        this.discountMoney = discountMoney;
    }
}
