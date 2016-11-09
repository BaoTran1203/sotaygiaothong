package com.trangiabao.giaothong.tracuu.luat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.luat.model.Dieu;

import java.util.ArrayList;
import java.util.List;

public class DieuDB extends AbstractDB {

    public DieuDB(Context context) {
        super(context);
    }

    public List<Dieu> getByIdChuong(String idChuong) {
        List<Dieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Dieu where IdChuong = ?", new String[]{idChuong});
        while (c.moveToNext()) {
            Dieu temp = new Dieu(
                    context,
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
