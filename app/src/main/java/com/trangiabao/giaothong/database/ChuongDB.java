package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.Chuong;

import java.util.ArrayList;

public class ChuongDB extends AbstractDB {

    public ChuongDB(Context context) {
        super(context);
    }

    public ArrayList<Chuong> getAll(int id) {
        ArrayList<Chuong> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Chuong where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            Chuong temp = new Chuong(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public Chuong getById(int id) {
        ArrayList<Chuong> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Chuong where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            Chuong temp = new Chuong(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        return data.get(0);
    }
}
