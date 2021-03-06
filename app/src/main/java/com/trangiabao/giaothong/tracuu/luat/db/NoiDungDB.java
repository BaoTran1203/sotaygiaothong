package com.trangiabao.giaothong.tracuu.luat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.luat.model.NoiDung;

import java.util.ArrayList;

public class NoiDungDB extends AbstractDB {

    public NoiDungDB(Context context) {
        super(context);
    }

    public ArrayList<NoiDung> getByIdChuong(String idChuong) {
        ArrayList<NoiDung> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NoiDung where idChuong = " + idChuong, null);
        while (c.moveToNext()) {
            NoiDung temp = new NoiDung(
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
