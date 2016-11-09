package com.trangiabao.giaothong.sathach.meo;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.meo.Meo;

import java.util.ArrayList;
import java.util.List;

public class MeoDB extends AbstractDB {

    public MeoDB(Context context) {
        super(context);
    }

    public List<Meo> getAll() {
        List<Meo> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Meo", null);
        while (c.moveToNext()) {
            Meo temp = new Meo(
                    c.getString(0),
                    c.getString(1),
                    c.getString(2)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public List<String> getListString(List<Meo> data) {
        List<String> lstString = new ArrayList<>();
        for (Meo meo : data) {
            lstString.add(meo.getPhan());
        }
        return lstString;
    }
}
