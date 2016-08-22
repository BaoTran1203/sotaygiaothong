package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.NhomCauHoi;

import java.util.ArrayList;
import java.util.List;

public class NhomCauHoiDB extends AbstractDB {

    public NhomCauHoiDB(Context context) {
        super(context);
    }

    public ArrayList<NhomCauHoi> getAll() {
        ArrayList<NhomCauHoi> lstNhomCauHoi = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NhomCauHoi", null);
        while (c.moveToNext()) {
            NhomCauHoi nhomCauHoi = new NhomCauHoi(
                    c.getInt(0),
                    c.getString(1)
            );
            lstNhomCauHoi.add(nhomCauHoi);
        }
        c.close();
        return lstNhomCauHoi;
    }

    public List<String> getTenNhomCauHoi(ArrayList<NhomCauHoi> data) {
        List<String> result = new ArrayList<>();
        for (NhomCauHoi temp : data) {
            result.add(temp.getTenNhom());
        }
        return result;
    }

    public NhomCauHoi getItemById(int id) {
        NhomCauHoi data = null;
        Cursor c = database.rawQuery("select * from NhomCauHoi where id = ?", new String[]{String.valueOf(id)});
        if (c.moveToNext()) {
            data = new NhomCauHoi(
                    c.getInt(0),
                    c.getString(1)
            );
            //data.add(temp);
        }
        c.close();
        return data;
    }
}
