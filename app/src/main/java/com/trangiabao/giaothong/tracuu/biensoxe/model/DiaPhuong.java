package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class DiaPhuong {

    private int id;
    private int idKiHieu;
    private String seri;
    private String diaPhuong;

    public DiaPhuong(int id, int idKiHieu, String seri, String diaPhuong) {
        this.id = id;
        this.idKiHieu = idKiHieu;
        this.seri = seri;
        this.diaPhuong = diaPhuong;
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

    public String getDiaPhuong() {
        return diaPhuong;
    }
}
