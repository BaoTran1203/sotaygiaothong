package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Seri;

import java.util.ArrayList;
import java.util.List;

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
                    c.getString(1),
                    c.getString(2)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public List<Seri> filter(String filter) {
        List<Seri> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Seri", null);
        while (c.moveToNext()) {
            Seri temp = new Seri(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2)
            );
            String seri = MyMethod.unAccent(temp.getSeri()).toLowerCase();
            String moTa = MyMethod.unAccent(temp.getMoTa()).toLowerCase();
            if (seri.contains(filter) || moTa.contains(filter))
                data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
