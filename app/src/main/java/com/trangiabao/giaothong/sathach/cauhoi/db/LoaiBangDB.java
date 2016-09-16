package com.trangiabao.giaothong.sathach.cauhoi.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.LoaiBang;

import java.util.ArrayList;
import java.util.List;

public class LoaiBangDB extends AbstractDB {

    public LoaiBangDB(Context context) {
        super(context);
    }

    public List<LoaiBang> getAll() {
        List<LoaiBang> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from LoaiBang", null);
        while (c.moveToNext()) {
            LoaiBang temp = new LoaiBang(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getString(5)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public List<String> getTenBang(List<LoaiBang> arr) {
        List<String> data = new ArrayList<>();
        for (LoaiBang temp : arr) {
            data.add(temp.getTenBang());
        }
        return data;
    }

    public LoaiBang getById(String id) {
        LoaiBang loaiBang = null;
        Cursor c = database.rawQuery("select * from LoaiBang where id = ?", new String[]{id});
        while (c.moveToNext()) {
            loaiBang = new LoaiBang(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getString(5)
            );
        }
        c.close();
        database.close();
        return loaiBang;
    }

}
