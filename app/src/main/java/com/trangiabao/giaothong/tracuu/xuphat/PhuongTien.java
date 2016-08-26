package com.trangiabao.giaothong.tracuu.xuphat;

import android.graphics.Bitmap;

public class PhuongTien {

    private int id;
    private String phuongTien;
    private String vietTat;
    private byte[] icon;

    public PhuongTien(int id, String phuongTien, String vietTat, byte[] icon) {
        this.id = id;
        this.phuongTien = phuongTien;
        this.vietTat = vietTat;
        this.icon = icon;
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

    public byte[] getIcon() {
        return icon;
    }
}
