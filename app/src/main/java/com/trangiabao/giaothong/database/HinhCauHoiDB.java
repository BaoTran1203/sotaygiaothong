package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.HinhCauHoi;

import java.util.ArrayList;

public class HinhCauHoiDB extends AbstractDB {

    public HinhCauHoiDB(Context context) {
        super(context);
    }

    public ArrayList<HinhCauHoi> getByIdCauHoi(int idCauHoi) {
        ArrayList<HinhCauHoi> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from HinhCauHoi where idCauHoi = ?", new String[]{String.valueOf(idCauHoi)});
        while (c.moveToNext()) {
            HinhCauHoi temp = new HinhCauHoi(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }
}
