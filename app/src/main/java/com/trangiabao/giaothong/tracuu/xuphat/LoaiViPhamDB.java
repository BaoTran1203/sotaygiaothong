package com.trangiabao.giaothong.tracuu.xuphat;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.database.AbstractDB;

import java.util.ArrayList;
import java.util.List;

public class LoaiViPhamDB extends AbstractDB {

    public LoaiViPhamDB(Context context) {
        super(context);
    }

    public List<LoaiViPham> getAll() {
        List<LoaiViPham> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from LoaiViPham", null);
        while (c.moveToNext()) {
            LoaiViPham temp = new LoaiViPham(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public List<String> getAllAsString() {
        List<String> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from LoaiViPham", null);
        while (c.moveToNext()) {
            LoaiViPham temp = new LoaiViPham(
                    c.getInt(0),
                    c.getString(1)
            );
            data.add(temp.getLoai());
        }
        c.close();
        return data;
    }
}