package com.trangiabao.giaothong.sathach.model;

public class HinhCauHoi {

    private int id;
    private int idCauHoi;
    private String hinh;

    public HinhCauHoi(int id, int idCauHoi, String hinh) {
        this.id = id;
        this.idCauHoi = idCauHoi;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public int getIdCauHoi() {
        return idCauHoi;
    }

    public String getHinh() {
        return hinh;
    }
}
