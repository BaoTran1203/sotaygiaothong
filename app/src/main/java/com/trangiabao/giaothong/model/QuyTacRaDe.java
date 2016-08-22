package com.trangiabao.giaothong.model;

public class QuyTacRaDe {

    private int id;
    private int idLoaiBang;
    private int idNhomCauHoi;
    private int soCauThi;

    public QuyTacRaDe(int id, int idLoaiBang, int idNhomCauHoi, int soCauThi) {
        this.id = id;
        this.idLoaiBang = idLoaiBang;
        this.idNhomCauHoi = idNhomCauHoi;
        this.soCauThi = soCauThi;
    }

    public int getId() {
        return id;
    }

    public int getIdLoaiBang() {
        return idLoaiBang;
    }

    public int getIdNhomCauHoi() {
        return idNhomCauHoi;
    }

    public int getSoCauThi() {
        return soCauThi;
    }
}
