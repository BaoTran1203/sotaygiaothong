package com.trangiabao.giaothong.tracuu.xuphat;

public class PhuongTien {

    private int id;
    private String phuongTien;
    private String vietTat;
    private String iconName;

    public PhuongTien(int id, String phuongTien, String vietTat, String iconName) {
        this.id = id;
        this.phuongTien = phuongTien;
        this.vietTat = vietTat;
        this.iconName = iconName;
    }

    public int getId() {
        return id;
    }

    public String getPhuongTien() {
        return phuongTien;
    }

    public String getVietTat() {
        return vietTat;
    }

    public String getIconName() {
        return iconName;
    }
}
