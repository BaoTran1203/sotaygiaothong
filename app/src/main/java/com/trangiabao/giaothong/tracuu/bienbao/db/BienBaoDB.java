package com.trangiabao.giaothong.tracuu.bienbao.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.bienbao.model.BienBao;

import java.util.ArrayList;
import java.util.List;

public class BienBaoDB extends AbstractDB {

    public BienBaoDB(Context context) {
        super(context);
    }

    public List<BienBao> getByIdNhomBienBao(String idNhomBienBao) {
        List<BienBao> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from BienBao where idNhomBienBao = ?", new String[]{idNhomBienBao});
        while (c.moveToNext()) {
            BienBao temp = new BienBao(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public List<BienBao> filter(String filter) {
        List<BienBao> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from BienBao", null);
        while (c.moveToNext()) {
            BienBao temp = new BienBao(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3),
                    c.getString(4)
            );
            filter = MyMethod.unAccent(filter).toLowerCase();
            String ma = MyMethod.unAccent(temp.getMaBienBao()).toLowerCase();
            String ten = MyMethod.unAccent(temp.getTenBienBao()).toLowerCase();
            String noiDung = MyMethod.unAccent(temp.getNoiDungBienBao()).toLowerCase();
            if (ma.contains(filter) || ten.contains(filter) || noiDung.contains(filter))
                data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
