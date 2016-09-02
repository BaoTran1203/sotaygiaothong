package com.trangiabao.giaothong.tracuu.xuphat.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class PhuongTien {

    private int id;
    private String phuongTien;
    private String vietTat;
    private Drawable icon;

    public PhuongTien(Context context, int id, String phuongTien, String vietTat, String icon) {
        this.id = id;
        this.phuongTien = phuongTien;
        this.vietTat = vietTat;
        try {
            InputStream is = context.getAssets().open(icon);
            this.icon = Drawable.createFromStream(is, null);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public Drawable getIcon() {
        return icon;
    }
}
