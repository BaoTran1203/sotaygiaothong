package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class Seri {

    private int id;
    private String seri;
    private String moTa;

    public Seri(int id, String seri, String moTa) {
        this.id = id;
        this.seri = seri;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public String getSeri() {
        return seri;
    }

    public String getMoTa() {
        return moTa;
    }
}
