package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.DiaPhuong;

import java.util.ArrayList;
import java.util.List;

public class DiaPhuongDB extends AbstractDB {

    public DiaPhuongDB(Context context) {
        super(context);
    }

    public List<DiaPhuong> getByIdTinh(int idTinh) {
        List<DiaPhuong> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from DiaPhuong where IdTinh = ?", new String[]{String.valueOf(idTinh)});
        while (c.moveToNext()) {
            DiaPhuong temp = new DiaPhuong(
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
}
