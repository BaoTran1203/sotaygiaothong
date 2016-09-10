package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class NhomBoQuocPhong {

    private int id;
    private String kiTu;
    private String nhom;

    public NhomBoQuocPhong(int id, String kiTu, String nhom) {
        this.id = id;
        this.kiTu = kiTu;
        this.nhom = nhom;
    }

    public int getId() {
        return id;
    }

    public String getKiTu() {
        return kiTu;
    }

    public String getNhom() {
        return nhom;
    }
}
