package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.CauHoi;

import java.util.ArrayList;

public class CauHoiDB extends AbstractDB {

    public CauHoiDB(Context context) {
        super(context);
    }

    public ArrayList<CauHoi> getCauHoi(String query) {
        ArrayList<CauHoi> data = new ArrayList<>();
        Cursor c = database.rawQuery(query, null);
        while (c.moveToNext()) {
            CauHoi temp = new CauHoi(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    c.getString(3)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }
}
