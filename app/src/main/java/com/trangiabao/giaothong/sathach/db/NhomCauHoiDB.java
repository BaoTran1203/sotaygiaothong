package com.trangiabao.giaothong.sathach.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.sathach.model.NhomCauHoi;

import java.util.ArrayList;
import java.util.List;

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

    public NhomCauHoi getItemById(int id) {
        NhomCauHoi data = null;
        Cursor c = database.rawQuery("select * from NhomCauHoi where id = ?", new String[]{String.valueOf(id)});
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
