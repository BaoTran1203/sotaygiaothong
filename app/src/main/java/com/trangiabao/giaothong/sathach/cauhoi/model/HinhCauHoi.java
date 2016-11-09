package com.trangiabao.giaothong.sathach.cauhoi.model;

public class HinhCauHoi {

    private int id;
    private String hinh;

    public HinhCauHoi(int id, String hinh) {
        this.id = id;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getHinh() {
        return hinh;
    }
}
