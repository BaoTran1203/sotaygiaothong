package com.trangiabao.giaothong.tracuu.xuphat.model;

public class LoaiViPham {

    private int id;
    private String loai;

    public LoaiViPham(int id, String loai) {
        this.id = id;
        this.loai = loai;
    }

    public int getId() {
        return id;
    }

    public String getLoai() {
        return loai;
    }
}
