package com.example.hotel.vo;

public class HotelBirthdayCouponVO extends CouponVO{
    // TODO: 2020/5/25
    private Integer hotelId;

    private double discount;

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
