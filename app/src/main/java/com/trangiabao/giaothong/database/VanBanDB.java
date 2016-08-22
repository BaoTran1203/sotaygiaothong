package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.VanBan;

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
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getBlob(4)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public VanBan getByID(int id) {
        VanBan vanBan = new VanBan();
        Cursor c = database.rawQuery("select * from VanBan where Id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            vanBan = new VanBan(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getBlob(4)
            );
        }
        c.close();
        return vanBan;
    }
}
