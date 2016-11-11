package com.trangiabao.giaothong.tracuu.bienbao.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.bienbao.model.BienBao;

import java.util.ArrayList;
import java.util.List;

public class BienBaoDB extends AbstractDB {

    public BienBaoDB(Context context) {
        super(context);
    }

    public List<BienBao> getByIdNhomBienBao(String idNhom) {
        List<BienBao> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from BienBao where IdNhomBienBao = " + idNhom, null);
        while (c.moveToNext()) {
            BienBao temp = new BienBao(
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
