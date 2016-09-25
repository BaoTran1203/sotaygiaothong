package com.trangiabao.giaothong.tracuu.xuphat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.tracuu.xuphat.model.LoaiViPham;

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
        database.close();
        return data;
    }

    public List<String> getLstString(List<LoaiViPham> lstLoaiViPham) {
        List<String> data = new ArrayList<>();
        for (LoaiViPham loaiViPham : lstLoaiViPham) {
            data.add(loaiViPham.getLoai());
        }
        database.close();
        return data;
    }
}