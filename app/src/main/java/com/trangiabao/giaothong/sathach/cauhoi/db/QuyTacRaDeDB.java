package com.trangiabao.giaothong.sathach.cauhoi.db;

import android.content.Context;
import android.database.Cursor;


import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.QuyTacRaDe;

import java.util.ArrayList;

public class QuyTacRaDeDB extends AbstractDB {

    public QuyTacRaDeDB(Context context) {
        super(context);
    }

    public ArrayList<QuyTacRaDe> getByIdLoaiBang(String idLoaiBang) {
        ArrayList<QuyTacRaDe> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from QuyTacRaDe where IdLoaiBang = ? and SoCau != 0", new String[]{idLoaiBang});
        while (c.moveToNext()) {
            QuyTacRaDe temp = new QuyTacRaDe(
                    c.getInt(0),
                    c.getInt(2),
                    c.getInt(1)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public ArrayList<QuyTacRaDe> getByIdLoaiBang2(String idLoaiBang) {
        ArrayList<QuyTacRaDe> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from QuyTacRaDe where IdLoaiBang = ?", new String[]{idLoaiBang});
        while (c.moveToNext()) {
            QuyTacRaDe temp = new QuyTacRaDe(
                    c.getInt(0),
                    c.getInt(2),
                    c.getInt(1)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
