package com.example.hotelmanagementsystem.model;

public class hotel {
    public hotel(String name, String address, String room, String price, String uid) {
        this.name = name;
        this.address = address;
        this.room = room;
        this.price = price;
        this.uid = uid;
    }

    public hotel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    String name, address, room, price, uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
