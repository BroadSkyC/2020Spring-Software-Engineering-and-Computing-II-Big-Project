package com.example.hotel.vo;

public class HotelBirthdayCouponVO extends CouponVO{
    // TODO: 2020/5/25
    private Integer hotelId;

    private Integer discount;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
