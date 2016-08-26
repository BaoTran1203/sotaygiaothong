package com.trangiabao.giaothong.tracuu.xuphat;

public class ChiTietXuPhat {

    private int id;
    private int idPhuongTien;
    private int idLoaiViPham;

    public ChiTietXuPhat(int id, int idPhuongTien, int idLoaiViPham) {
        this.id = id;
        this.idPhuongTien = idPhuongTien;
        this.idLoaiViPham = idLoaiViPham;
    }

    public int getId() {
        return id;
    }

    public int getIdPhuongTien() {
        return idPhuongTien;
    }

    public int getIdLoaiViPham() {
        return idLoaiViPham;
    }
}
