package com.trangiabao.giaothong.model;

public class Dieu {

    private int id;
    private String tenDieu;

    public Dieu(int id, String tenDieu) {
        this.id = id;
        this.tenDieu = tenDieu;
    }

    public int getId() {
        return id;
    }

    public String getTenDieu() {
        return tenDieu;
    }
}
