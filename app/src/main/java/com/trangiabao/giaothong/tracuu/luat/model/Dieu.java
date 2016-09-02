package com.trangiabao.giaothong.tracuu.luat.model;

import android.content.Context;

import com.trangiabao.giaothong.tracuu.luat.db.NoiDungDB;

import java.util.List;

public class Dieu {

    private int id;
    private int idChuong;
    private String tenDieu;
    private List<NoiDung> lstNoiDung;

    public Dieu(Context context, int id, int idChuong, String tenDieu) {
        this.id = id;
        this.idChuong = idChuong;
        this.tenDieu = tenDieu;
        this.lstNoiDung = new NoiDungDB(context).getByIdDieu(id+"");
    }

    public int getId() {
        return id;
    }

    public int getIdChuong() {
        return idChuong;
    }

    public String getTenDieu() {
        return tenDieu;
    }

    public List<NoiDung> getLstNoiDung() {
        return lstNoiDung;
    }
}
