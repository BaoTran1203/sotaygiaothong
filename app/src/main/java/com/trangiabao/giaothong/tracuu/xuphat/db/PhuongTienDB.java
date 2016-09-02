package com.trangiabao.giaothong.tracuu.xuphat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.PhuongTien;

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
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
