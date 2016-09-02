package com.trangiabao.giaothong.tracuu.luat.model;

public class NoiDung {

    private int id;
    private int idDieu;
    private String noiDung;

    public NoiDung(int id, int idDieu, String noiDung) {
        this.id = id;
        this.idDieu = idDieu;
        this.noiDung = noiDung;
    }

    public int getId() {
        return id;
    }

    public int getIdDieu() {
        return idDieu;
    }

    public String getNoiDung() {
        return noiDung;
    }
}
