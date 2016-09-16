package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.KiHieu;

import java.util.ArrayList;

public class KiHieuDB extends AbstractDB {

    public KiHieuDB(Context context) {
        super(context);
    }

    public ArrayList<KiHieu> getByIdNhom(String idNhom) {
        ArrayList<KiHieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from KiHieu where idNhomBienSoXe = ?", new String[]{idNhom});
        while (c.moveToNext()) {
            KiHieu temp = new KiHieu(
                    context,
                    c.getInt(0),
                    c.getInt(1),
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
