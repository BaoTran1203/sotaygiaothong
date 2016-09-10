package com.trangiabao.giaothong.tracuu.biensoxe.model;

public class BoQuocPhong {

    private int id;
    private int idNhomBoQuocPhong;
    private String kiHieu;
    private String coQuan;

    public BoQuocPhong(int id, int idNhomBoQuocPhong, String kiHieu, String coQuan) {
        this.id = id;
        this.idNhomBoQuocPhong = idNhomBoQuocPhong;
        this.kiHieu = kiHieu;
        this.coQuan = coQuan;
    }

    public int getId() {
        return id;
    }

    public int getIdNhomBoQuocPhong() {
        return idNhomBoQuocPhong;
    }

    public String getKiHieu() {
        return kiHieu;
    }

    public String getCoQuan() {
        return coQuan;
    }
}
