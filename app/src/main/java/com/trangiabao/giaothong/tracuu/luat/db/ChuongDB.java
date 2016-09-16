package com.trangiabao.giaothong.tracuu.luat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.luat.model.Chuong;

import java.util.ArrayList;

public class ChuongDB extends AbstractDB {

    public ChuongDB(Context context) {
        super(context);
    }

    public ArrayList<Chuong> getByIdVanBan(String idVanBan) {
        ArrayList<Chuong> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from Chuong where idVanBan = ?", new String[]{idVanBan});
        while (c.moveToNext()) {
            Chuong temp = new Chuong(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public Chuong getById(String idChuong) {
        Chuong data = null;
        Cursor c = database.rawQuery("select * from Chuong where id = ?", new String[]{idChuong});
        while (c.moveToNext()) {
            data = new Chuong(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3)
            );
        }
        c.close();
        database.close();
        return data;
    }
}
