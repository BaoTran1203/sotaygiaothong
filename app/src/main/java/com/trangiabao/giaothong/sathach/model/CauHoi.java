package com.trangiabao.giaothong.sathach.model;

import android.content.Context;

import com.trangiabao.giaothong.sathach.db.CauTraLoiDB;
import com.trangiabao.giaothong.sathach.db.HinhCauHoiDB;

import java.util.ArrayList;
import java.util.List;

public class CauHoi {

    private int id;
    private int nhomCauHoi;
    private String cauHoi;
    private String giaiThich;
    private List<HinhCauHoi> lstHinhCauHoi = new ArrayList<>();
    private List<CauTraLoi> lstCauTraLoi = new ArrayList<>();

    public CauHoi(Context context, int id, int nhomCauHoi, String cauHoi, String giaiThich) {
        this.id = id;
        this.nhomCauHoi = nhomCauHoi;
        this.cauHoi = cauHoi;
        this.giaiThich = giaiThich;
        lstCauTraLoi = new CauTraLoiDB(context).getListByIdCauHoi(id);
        lstHinhCauHoi = new HinhCauHoiDB(context).getByIdCauHoi(id);
    }

    public int getId() {
        return id;
    }

    public String getCauHoi() {
        return cauHoi;
    }

    public String getGiaiThich() {
        return giaiThich;
    }

    public List<CauTraLoi> getLstCauTraLoi() {
        return lstCauTraLoi;
    }

    public List<HinhCauHoi> getLstHinhCauHoi() {
        return lstHinhCauHoi;
    }

}
