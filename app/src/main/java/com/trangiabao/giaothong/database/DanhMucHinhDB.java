package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.DanhMucHinh;

import java.util.ArrayList;

public class DanhMucHinhDB extends AbstractDB {

    public DanhMucHinhDB(Context context) {
        super(context);
    }

    public ArrayList<DanhMucHinh> getAll() {
        ArrayList<DanhMucHinh> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from DanhMucHinh", null);
        while (c.moveToNext()) {
            DanhMucHinh temp = new DanhMucHinh(
                    c.getInt(0),
                    c.getBlob(1)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public DanhMucHinh getById(int id) {
        ArrayList<DanhMucHinh> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from DanhMucHinh where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            DanhMucHinh temp = new DanhMucHinh(
                    c.getInt(0),
                    c.getBlob(1)
            );
            data.add(temp);
        }
        c.close();
        return data.get(0);
    }
}
