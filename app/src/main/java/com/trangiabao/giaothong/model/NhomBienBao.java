package com.trangiabao.giaothong.model;

public class NhomBienBao {

    private int id;
    private String tenNhomBienBao;
    private String moTa;

    public NhomBienBao(int id, String tenNhomBienBao, String moTa) {
        this.id = id;
        this.tenNhomBienBao = tenNhomBienBao;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public String getTenNhomBienBao() {
        return tenNhomBienBao;
    }

    public String getMoTa() {
        return moTa;
    }
}
