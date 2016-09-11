package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Seri;

import java.util.ArrayList;

public class SeriDB extends AbstractDB {

    public SeriDB(Context context) {
        super(context);
    }

    public ArrayList<Seri> getByIdKiHieu(String idKiHieu) {
        ArrayList<Seri> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Seri where idKiHieu = ?", new String[]{idKiHieu});
        while (c.moveToNext()) {
            Seri temp = new Seri(
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
