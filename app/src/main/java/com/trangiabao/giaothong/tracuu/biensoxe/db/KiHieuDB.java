package com.trangiabao.giaothong.tracuu.biensoxe.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.biensoxe.model.KiHieu;
import com.trangiabao.giaothong.tracuu.biensoxe.model.Seri;

import java.util.ArrayList;
import java.util.List;

public class KiHieuDB extends AbstractDB {

    public KiHieuDB(Context context) {
        super(context);
    }

    public ArrayList<KiHieu> getByIdNhomBienSoXe(String idNhom) {
        ArrayList<KiHieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from KiHieu where IdNhomBienSoXe = " + idNhom, null);
        while (c.moveToNext()) {
            KiHieu temp = new KiHieu(
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

    public List<KiHieu> filter(String idNhom, String filter) {
        List<KiHieu> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from KiHieu where IdNhomBienSoXe = " + idNhom, null);
        while (c.moveToNext()) {
            KiHieu temp = new KiHieu(
                    context,
                    c.getInt(0),
                    c.getString(1),
                    c.getString(2),
                    c.getString(3)
            );
            filter = MyMethod.unAccent(filter).toLowerCase();
            String kiHieu = MyMethod.unAccent(temp.getKiHieu()).toLowerCase();
            String tenKiHieu = MyMethod.unAccent(temp.getTenKiHieu()).toLowerCase();
            List<Seri> lstSeri = temp.getLstSeri();
            boolean isContainsSeri = false;
            for (Seri seri : lstSeri) {
                String mSeri = MyMethod.unAccent(seri.getSeri()).toLowerCase();
                String moTa = MyMethod.unAccent(seri.getMoTa()).toLowerCase();
                if (mSeri.contains(filter) || moTa.contains(filter)) {
                    isContainsSeri = true;
                    break;
                }
            }
            if (kiHieu.contains(filter) || tenKiHieu.contains(filter) || isContainsSeri)
                data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
