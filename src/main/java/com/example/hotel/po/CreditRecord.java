package com.example.hotel.po;

public class CreditRecord {
    private Integer id;
    private Integer orderId;
    private Integer userId;
    private String action;
    private Integer creditChange;
    private Integer currentCredit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getCreditChange() {
        return creditChange;
    }

    public void setCreditChange(Integer cc) {
        this.creditChange = cc;
    }

    public Integer getCurrentCredit() {
        return currentCredit;
    }

    public void setCurrentCredit(Integer credit) {
        this.currentCredit = credit;
    }
}
