package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.Dieu;

import java.util.ArrayList;

public class DieuDB extends AbstractDB {

    public DieuDB(Context context) {
        super(context);
    }

    public ArrayList<Dieu> getAll(int id) {
        ArrayList<Dieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Dieu where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            Dieu temp = new Dieu(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public Dieu getById(int id) {
        ArrayList<Dieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Dieu where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            Dieu temp = new Dieu(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        return data.get(0);
    }
}
