package com.trangiabao.giaothong.model;

public class HinhCauHoi {

    private int id;
    private int idCauHoi;
    private int idHinh;

    public HinhCauHoi(int id, int idCauHoi, int idHinh) {
        this.id = id;
        this.idCauHoi = idCauHoi;
        this.idHinh = idHinh;
    }

    public int getId() {
        return id;
    }

    public int getIdCauHoi() {
        return idCauHoi;
    }

    public int getIdHinh() {
        return idHinh;
    }
}
