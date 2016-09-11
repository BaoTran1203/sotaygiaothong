package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class Seri {

    private int id;
    private int idKiHieu;
    private String seri;
    private String moTa;

    public Seri(int id, int idKiHieu, String seri, String moTa) {
        this.id = id;
        this.idKiHieu = idKiHieu;
        this.seri = seri;
        this.moTa = moTa;
    }

    public int getId() {
        return id;
    }

    public int getIdKiHieu() {
        return idKiHieu;
    }

    public String getSeri() {
        return seri;
    }

    public String getMoTa() {
        return moTa;
    }
}
