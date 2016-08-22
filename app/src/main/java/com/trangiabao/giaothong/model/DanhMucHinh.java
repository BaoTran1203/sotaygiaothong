package com.trangiabao.giaothong.model;


public class DanhMucHinh {

    private int id;
    private byte[] hinh;

    public DanhMucHinh(int id, byte[] hinh) {
        this.id = id;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public byte[] getHinh() {
        return hinh;
    }
}
