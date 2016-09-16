package com.trangiabao.giaothong.tracuu.luat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.luat.model.VanBan;

import java.util.ArrayList;
import java.util.List;

public class VanBanDB extends AbstractDB {

    public VanBanDB(Context context) {
        super(context);
    }

    public List<VanBan> getAll() {
        List<VanBan> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from VanBan", null);
        while (c.moveToNext()) {
            VanBan temp = new VanBan(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
