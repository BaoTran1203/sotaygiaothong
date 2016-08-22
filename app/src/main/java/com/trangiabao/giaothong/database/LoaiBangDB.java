package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.LoaiBang;

import java.util.ArrayList;
import java.util.List;

public class LoaiBangDB extends AbstractDB {

    public LoaiBangDB(Context context) {
        super(context);
    }

    public ArrayList<LoaiBang> getAll() {
        ArrayList<LoaiBang> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from LoaiBang", null);
        while (c.moveToNext()) {
            LoaiBang temp = new LoaiBang(
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getInt(3),
                    c.getInt(4),
                    c.getInt(5),
                    c.getInt(6),
                    c.getString(7)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public List<String> getTenBang(ArrayList<LoaiBang> arr) {
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
                    c.getInt(5),
                    c.getInt(6),
                    c.getString(7)
            );
        }
        c.close();
        return loaiBang;
    }

}
