package com.example.hotel.enums;

public enum VipType {
    Common("1"),
    Company("2");
    private String value;
    VipType(String value) {
            this.value = value;
        }
    @Override
    public String toString() {
        return value;
    }


}
