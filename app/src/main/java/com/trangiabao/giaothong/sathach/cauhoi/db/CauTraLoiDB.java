package com.trangiabao.giaothong.sathach.cauhoi.db;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.ex.AbstractDB;
import com.trangiabao.giaothong.sathach.cauhoi.model.CauTraLoi;

import java.util.ArrayList;
import java.util.List;

public class CauTraLoiDB extends AbstractDB {

    public CauTraLoiDB(Context context) {
        super(context);
    }

    public List<CauTraLoi> getListByIdCauHoi(String idCauHoi) {
        List<CauTraLoi> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from CauTraLoi where IdCauHoi = ?", new String[]{idCauHoi});
        while (c.moveToNext()) {
            CauTraLoi temp = new CauTraLoi(
                    c.getInt(0),
                    c.getString(1),
                    (c.getInt(2) != 0)
            );
            data.add(temp);
        }
        c.close();
        database.close();
        return data;
    }
}
