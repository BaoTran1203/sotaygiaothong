package com.trangiabao.giaothong.model;

import java.util.ArrayList;

public class CauHoi {

    private int id;
    private int nhomCauHoi;
    private String cauHoi;
    private String giaiThich;
    private ArrayList<DanhMucHinh> lstHinh = new ArrayList<>();
    private ArrayList<CauTraLoi> lstCauTraLoi = new ArrayList<>();
    private ArrayList<NoiDung> lstNoiDung = new ArrayList<>();

    public CauHoi(int id, int nhomCauHoi, String cauHoi, String giaiThich) {
        this.id = id;
        this.nhomCauHoi = nhomCauHoi;
        this.cauHoi = cauHoi;
        this.giaiThich = giaiThich;
    }

    public int getId() {
        return id;
    }

    public int getNhomCauHoi() {
        return nhomCauHoi;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public String getGiaiThich() {
        return giaiThich;
    }

    public ArrayList<CauTraLoi> getLstCauTraLoi() {
        return lstCauTraLoi;
    }

    public CauHoi setLstCauTraLoi(ArrayList<CauTraLoi> lstCauTraLoi) {
        this.lstCauTraLoi = lstCauTraLoi;
        return this;
    }

    public ArrayList<DanhMucHinh> getLstHinh() {
        return lstHinh;
    }

    public void setLstHinh(ArrayList<DanhMucHinh> lstHinh) {
        this.lstHinh = lstHinh;
    }

    public ArrayList<NoiDung> getLstNoiDung() {
        return lstNoiDung;
    }

    public void setLstNoiDung(ArrayList<NoiDung> lstNoiDung) {
        this.lstNoiDung = lstNoiDung;
    }
}
