package com.example.hotel.enums;


public enum UserType {
    Client("1"),
    HotelManager("2"),
    Admin("3"),
    HotelReceptionist("4"),
    Visitor("5");
    private String value;
    UserType(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return value;
    }

}
