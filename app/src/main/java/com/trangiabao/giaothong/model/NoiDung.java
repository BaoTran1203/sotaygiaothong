package com.trangiabao.giaothong.model;

public class NoiDung {

    private int id;
    private int idVanBan;
    private int idChuong;
    private int idDieu;
    private String noiDung;

    public NoiDung(int id, int idVanBan, int idChuong, int idDieu, String noiDung) {
        this.id = id;
        this.idVanBan = idVanBan;
        this.idChuong = idChuong;
        this.idDieu = idDieu;
        this.noiDung = noiDung;
    }

    public int getId() {
        return id;
    }

    public int getIdVanBan() {
        return idVanBan;
    }

    public int getIdChuong() {
        return idChuong;
    }

    public int getIdDieu() {
        return idDieu;
    }

    public String getNoiDung() {
        return noiDung;
    }
}
