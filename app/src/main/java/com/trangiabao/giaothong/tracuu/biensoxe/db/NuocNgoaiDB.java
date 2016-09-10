package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.tracuu.biensoxe.model.NuocNgoai;

import java.util.ArrayList;
import java.util.List;

public class NuocNgoaiDB extends AbstractDB {

    public NuocNgoaiDB(Context context) {
        super(context);
    }

    public List<NuocNgoai> getAll() {
        List<NuocNgoai> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NuocNgoai", null);
        while (c.moveToNext()) {
            NuocNgoai temp = new NuocNgoai(
                    context,
                    c.getInt(0),
                    c.getString(1),
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
