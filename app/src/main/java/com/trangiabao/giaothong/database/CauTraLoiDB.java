package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.CauTraLoi;

import java.util.ArrayList;

public class CauTraLoiDB extends AbstractDB {

    public CauTraLoiDB(Context context) {
        super(context);
    }

    public ArrayList<CauTraLoi> getCauTraLoiByIdCauHoi(int idCauHoi) {
        ArrayList<CauTraLoi> data = new ArrayList<>();
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
        return data;
    }
}
