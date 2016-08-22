package com.trangiabao.giaothong.model;

public class Chuong {

    private int id;
    private String tenChuong;

    public Chuong(int id, String tenChuong) {
        this.id = id;
        this.tenChuong = tenChuong;
    }

    public int getId() {
        return id;
    }

    public String getTenChuong() {
        return tenChuong;
    }
}
