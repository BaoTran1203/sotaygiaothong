package com.trangiabao.giaothong.tracuu.luat.model;

import android.content.Context;

import com.trangiabao.giaothong.tracuu.luat.db.NoiDungDB;

import java.util.List;

public class Dieu {

    private int id;
    private String tenDieu;
    private List<NoiDung> lstNoiDung;

    public Dieu(Context context, int id, String tenDieu) {
        this.id = id;
        this.tenDieu = tenDieu;
        this.lstNoiDung = new NoiDungDB(context).getByIdDieu(id + "");
    }

    public int getId() {
        return id;
    }

    public String getTenDieu() {
        return tenDieu;
    }

    public List<NoiDung> getLstNoiDung() {
        return lstNoiDung;
    }
}
