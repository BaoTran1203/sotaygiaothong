package com.trangiabao.giaothong.model;

import java.util.ArrayList;

public class NhomCauHoi {

    private int id;
    private String tenNhom;
    private ArrayList<CauHoi> lstCauHoi = new ArrayList<>();

    public NhomCauHoi(int id, String tenNhom) {
        this.id = id;
        this.tenNhom = tenNhom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenNhom() {
        return tenNhom;
    }

    public ArrayList<CauHoi> getLstCauHoi() {
        return lstCauHoi;
    }

}
