package com.trangiabao.giaothong.tracuu.hotline.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.hotline.model.NhomHotLine;

import java.util.ArrayList;
import java.util.List;

public class NhomHotLineDB extends AbstractDB {

    public NhomHotLineDB(Context context) {
        super(context);
    }

    public List<NhomHotLine> getAll() {
        List<NhomHotLine> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NhomHotLine", null);
        while (c.moveToNext()) {
            NhomHotLine temp = new NhomHotLine(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    ""
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
