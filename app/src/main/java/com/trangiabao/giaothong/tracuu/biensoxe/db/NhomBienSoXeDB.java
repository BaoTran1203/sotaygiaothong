package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NhomBienSoXe;

import java.util.ArrayList;

public class NhomBienSoXeDB extends AbstractDB {

    public NhomBienSoXeDB(Context context) {
        super(context);
    }

    public ArrayList<NhomBienSoXe> getAll() {
        ArrayList<NhomBienSoXe> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NhomBienSoXe", null);
        while (c.moveToNext()) {
            NhomBienSoXe temp = new NhomBienSoXe(
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
