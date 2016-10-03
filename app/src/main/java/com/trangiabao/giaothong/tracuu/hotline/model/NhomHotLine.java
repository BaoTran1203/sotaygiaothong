package com.trangiabao.giaothong.tracuu.hotline.model;

public class NhomHotLine {

    private int id;
    private String nhom;
    private String moTa;

    public NhomHotLine(int id, String nhom, String moTa) {
        this.id = id;
        this.nhom = nhom;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public String getNhom() {
        return nhom;
    }

    public String getMoTa() {
        return moTa;
    }
}
