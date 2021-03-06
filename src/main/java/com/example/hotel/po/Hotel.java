package com.example.hotel.po;

import com.example.hotel.enums.BizRegion;
import com.example.hotel.enums.HotelStar;

public class Hotel {
    private Integer id;
    private String hotelName;
    private String address;
    /**
     * 商圈
     */
    private String bizRegion;
    private HotelStar hotelStar;
    private Double rate;
    private Integer commentTime;
    private String description;
    private String phoneNum;
    /**
     * 酒店管理员id
     */
    private Integer managerId;
    private double minPrice;
    private double maxPrice;
    private String imgUrl;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBizRegion() {
        return bizRegion;
    }

    public void setBizRegion(String bizRegion) {
        this.bizRegion = bizRegion;
    }

    public HotelStar getHotelStar() {
        return hotelStar;
    }

    public void setHotelStar(HotelStar hotelStar) {
        this.hotelStar = hotelStar;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Integer getManagerId() {
        return managerId;
    }

    public void setManagerId(Integer managerId) {
        this.managerId = managerId;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setCommentTime(Integer time) {
        this.commentTime = time;
    }

    public Integer getCommentTime() { return this.commentTime; }

    public String getImgUrl(){return this.imgUrl;}

    public void setImgUrl(String url){this.imgUrl=url;}

    public String getImgUrl1(){return this.imgUrl1;}

    public void setImgUrl1(String url){this.imgUrl1=url;}

    public String getImgUrl2(){return this.imgUrl2;}

    public void setImgUrl2(String url){this.imgUrl2=url;}

    public String getImgUrl3(){return this.imgUrl3;}

    public void setImgUrl3(String url){this.imgUrl3=url;}
}

