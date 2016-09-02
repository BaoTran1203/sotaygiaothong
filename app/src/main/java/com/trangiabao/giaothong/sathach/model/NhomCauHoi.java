package com.trangiabao.giaothong.sathach.model;

public class NhomCauHoi {

    private int id;
    private String tenNhom;

    public NhomCauHoi(int id, String tenNhom) {
        this.id = id;
        this.tenNhom = tenNhom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhom() {
        return tenNhom;
    }

}
