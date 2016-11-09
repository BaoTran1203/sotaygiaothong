package com.trangiabao.giaothong.sathach.cauhoi.model;

public class QuyTacRaDe {

    private int id;
    private int idNhomCauHoi;
    private int soCauThi;

    public QuyTacRaDe(int id, int idNhomCauHoi, int soCauThi) {
        this.id = id;
        this.idNhomCauHoi = idNhomCauHoi;
        this.soCauThi = soCauThi;
    }

    public int getId() {
        return id;
    }

    public int getIdNhomCauHoi() {
        return idNhomCauHoi;
    }

    public int getSoCauThi() {
        return soCauThi;
    }
}
