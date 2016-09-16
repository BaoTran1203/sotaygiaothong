package com.trangiabao.giaothong.sathach.cauhoi.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.HinhCauHoi;

import java.util.ArrayList;
import java.util.List;

public class HinhCauHoiDB extends AbstractDB {

    public HinhCauHoiDB(Context context) {
        super(context);
    }

    public List<HinhCauHoi> getByIdCauHoi(int idCauHoi) {
        List<HinhCauHoi> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from HinhCauHoi where idCauHoi = ?", new String[]{String.valueOf(idCauHoi)});
        while (c.moveToNext()) {
            HinhCauHoi temp = new HinhCauHoi(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
