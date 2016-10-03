package com.trangiabao.giaothong.tracuu.hotline.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.hotline.model.HotLine;

import java.util.ArrayList;
import java.util.List;

public class HotLineDB extends AbstractDB {

    public HotLineDB(Context context) {
        super(context);
    }

    public List<HotLine> getByIdNhomHotline(String idNhomHotLine) {
        List<HotLine> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from HotLine where idNhomHotLine = ?", new String[]{idNhomHotLine});
        while (c.moveToNext()) {
            HotLine temp = new HotLine(
                    c.getInt(0),
                    c.getInt(1),
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
