package com.trangiabao.giaothong.sathach.db;

import android.content.Context;
import android.database.Cursor;


import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.sathach.model.QuyTacRaDe;

import java.util.ArrayList;

public class QuyTacRaDeDB extends AbstractDB {

    public QuyTacRaDeDB(Context context) {
        super(context);
    }

    public ArrayList<QuyTacRaDe> getByIdLoaiBang(int idLoaiBang) {
        ArrayList<QuyTacRaDe> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from QuyTacRaDe where IdLoaiBang = ? and SoCau != 0", new String[]{String.valueOf(idLoaiBang)});
        while (c.moveToNext()) {
            QuyTacRaDe temp = new QuyTacRaDe(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3)
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
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3)
            );
        }
        c.close();
        database.close();
        return temp.getSoCauThi();
    }
}
