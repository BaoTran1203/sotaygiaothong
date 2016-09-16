package com.trangiabao.giaothong.tracuu.bienbao.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.bienbao.model.NhomBienBao;

import java.util.ArrayList;

public class NhomBienBaoDB extends AbstractDB {

    public NhomBienBaoDB(Context context) {
        super(context);
    }

    public ArrayList<NhomBienBao> getAll() {
        ArrayList<NhomBienBao> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NhomBienBao", null);
        while (c.moveToNext()) {
            NhomBienBao temp = new NhomBienBao(
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
