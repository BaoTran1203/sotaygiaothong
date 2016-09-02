package com.trangiabao.giaothong.sathach.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.AbstractDB;
import com.trangiabao.giaothong.sathach.model.CauTraLoi;

import java.util.ArrayList;
import java.util.List;

public class CauTraLoiDB extends AbstractDB {

    public CauTraLoiDB(Context context) {
        super(context);
    }

    public List<CauTraLoi> getListByIdCauHoi(int idCauHoi) {
        List<CauTraLoi> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from CauTraLoi where IdCauHoi = ?", new String[]{String.valueOf(idCauHoi)});
        while (c.moveToNext()) {
            CauTraLoi temp = new CauTraLoi(
                    c.getInt(0),
                    c.getInt(1),
                    c.getString(2),
                    (c.getInt(3) != 0)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
