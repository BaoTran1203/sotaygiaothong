package com.trangiabao.giaothong.sathach.cauhoi.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.NhomCauHoi;

import java.util.ArrayList;

public class NhomCauHoiDB extends AbstractDB {

    public NhomCauHoiDB(Context context) {
        super(context);
    }

    public ArrayList<NhomCauHoi> getAll() {
        ArrayList<NhomCauHoi> lstNhomCauHoi = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NhomCauHoi", null);
        while (c.moveToNext()) {
            NhomCauHoi nhomCauHoi = new NhomCauHoi(
                    c.getInt(0),
                    c.getString(1)
            );
            lstNhomCauHoi.add(nhomCauHoi);
        }
        c.close();
        database.close();
        return lstNhomCauHoi;
    }

    public NhomCauHoi getItemById(String id) {
        NhomCauHoi data = null;
        Cursor c = database.rawQuery("select * from NhomCauHoi where id = ?", new String[]{id});
        if (c.moveToNext()) {
            data = new NhomCauHoi(
                    c.getInt(0),
                    c.getString(1)
            );
        }
        c.close();
        database.close();
        return data;
    }
}
