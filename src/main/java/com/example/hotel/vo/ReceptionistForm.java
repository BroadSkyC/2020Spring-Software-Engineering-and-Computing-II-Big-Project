package com.example.hotel.vo;

public class ReceptionistForm {
    private String email;
    /**
     * 用户密码
     */
    private String password;
    private Integer hotelId;
    private String phoneNum;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getHotelId(){return hotelId;}

    public void setHotelId(Integer hotelId){this.hotelId=hotelId;}

    public String getPhoneNum(){return phoneNum;}

    public void setPhoneNum(String phoneNum){this.phoneNum=phoneNum;}
}
