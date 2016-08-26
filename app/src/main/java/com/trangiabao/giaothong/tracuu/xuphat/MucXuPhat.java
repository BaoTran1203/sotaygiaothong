package com.trangiabao.giaothong.tracuu.xuphat;

public class MucXuPhat {

    private int id;
    private int idChiTiet;
    private String hanhVi;
    private String mucPhat;
    private String xuPhatKhac;

    public MucXuPhat(int id, int idChiTiet, String hanhVi, String mucPhat, String xuPhatKhac) {
        this.id = id;
        this.idChiTiet = idChiTiet;
        this.hanhVi = hanhVi;
        this.mucPhat = mucPhat;
        this.xuPhatKhac = xuPhatKhac;
    }

    public int getId() {
        return id;
    }

    public int getIdChiTiet() {
        return idChiTiet;
    }

    public String getHanhVi() {
        return hanhVi;
    }

    public String getMucPhat() {
        return mucPhat;
    }

    public String getXuPhatKhac() {
        return xuPhatKhac;
    }
}
