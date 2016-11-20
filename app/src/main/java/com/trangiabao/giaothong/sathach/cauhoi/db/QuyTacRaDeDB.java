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

    public int getSoCau(String idLoaiBang, String idNhomCauHoi) {
        QuyTacRaDe temp = null;
        Cursor c = database.rawQuery("select * from QuyTacRaDe where IdLoaiBang = ? and IdNhomCauHoi = ?", new String[]{idLoaiBang, idNhomCauHoi});
        while (c.moveToNext()) {
            temp = new QuyTacRaDe(
                    c.getInt(0),
                    c.getInt(2),
                    c.getInt(1)
            );
        }
        c.close();
        database.close();
        return temp.getSoCauThi();
    }
}
