package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Tinh;

import java.util.ArrayList;
import java.util.List;

public class TinhDB extends AbstractDB {

    public TinhDB(Context context) {
        super(context);
    }

    public List<Tinh> getAll() {
        List<Tinh> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Tinh", null);
        while (c.moveToNext()) {
            Tinh temp = new Tinh(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
