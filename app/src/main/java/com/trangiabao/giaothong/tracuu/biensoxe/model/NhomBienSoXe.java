package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class NhomBienSoXe {

    private int id;
    private String ten;
    private String mauSac;
    private String moTa;
    private String hinh;

    public NhomBienSoXe(int id, String ten, String mauSac, String moTa, String hinh) {
        this.id = id;
        this.ten = ten;
        this.mauSac = mauSac;
        this.moTa = moTa;
        this.hinh = hinh;
    }

    public int getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public String getMauSac() {
        return mauSac;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getHinh() {
        return hinh;
    }
}
