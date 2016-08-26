package com.trangiabao.giaothong.tracuu.xuphat;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.database.AbstractDB;

import java.util.ArrayList;
import java.util.List;

public class PhuongTienDB extends AbstractDB {

    public PhuongTienDB(Context context) {
        super(context);
    }

    public List<PhuongTien> getAll() {
        List<PhuongTien> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from PhuongTien", null);
        while (c.moveToNext()) {
            PhuongTien temp = new PhuongTien(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getBlob(3)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }
}
