package com.trangiabao.giaothong.tracuu.xuphat.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.io.IOException;
import java.io.InputStream;

public class PhuongTien {

    private int id;
    private String phuongTien;
    private Drawable icon;

    public PhuongTien(Context context, int id, String phuongTien, String icon) {
        this.id = id;
        this.phuongTien = phuongTien;
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

    public Drawable getIcon() {
        return icon;
    }
}
