package com.trangiabao.giaothong.tracuu.xuphat.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.ex.MyMethod;
import com.trangiabao.giaothong.tracuu.xuphat.model.MucXuPhat;

import java.util.ArrayList;
import java.util.List;

public class MucXuPhatDB extends AbstractDB {

    public MucXuPhatDB(Context context) {
        super(context);
    }

    public List<MucXuPhat> getList(int idPhuongTien, int idLoaiViPham) {
        List<MucXuPhat> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from MucXuPhat where idPhuongTien = ? and idLoaiViPham = ?", new String[]{idPhuongTien + "", idLoaiViPham + ""});
        while (c.moveToNext()) {
            MucXuPhat temp = new MucXuPhat(
                    c.getInt(0),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }

    public List<MucXuPhat> filter(String query, String filter) {
        List<MucXuPhat> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from MucXuPhat", null);
        while (c.moveToNext()) {
            MucXuPhat temp = new MucXuPhat(
                    c.getInt(0),
                    c.getString(3),
                    c.getString(4),
                    c.getString(5),
                    c.getString(6),
                    c.getString(7)
            );
            filter = MyMethod.unAccent(filter).toLowerCase();
            String doiTuong = MyMethod.unAccent(temp.getDoiTuong()).toLowerCase();
            String hanhVi = MyMethod.unAccent(temp.getHanhVi()).toLowerCase();
            String mucPhat = MyMethod.unAccent(temp.getMucPhat()).toLowerCase();
            String boSung = MyMethod.unAccent(temp.getBoSung()).toLowerCase();
            String khacPhuc = MyMethod.unAccent(temp.getKhacPhuc()).toLowerCase();
            if (hanhVi.contains(filter) || mucPhat.contains(filter) || boSung.contains(filter) ||
                    doiTuong.contains(filter) || khacPhuc.contains(filter))
                data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}