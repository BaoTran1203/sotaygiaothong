package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.Cursor;

import com.trangiabao.giaothong.model.NoiDung;

import java.util.ArrayList;

public class NoiDungDB extends AbstractDB {

    public NoiDungDB(Context context) {
        super(context);
    }

    public ArrayList<NoiDung> getByIdVanBan(int idVanBan) {
        ArrayList<NoiDung> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NoiDung where idVanBan = ?", new String[]{String.valueOf(idVanBan)});
        while (c.moveToNext()) {
            NoiDung temp = new NoiDung(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getString(4)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public ArrayList<NoiDung> getByIdDieu(int idDieu) {
        ArrayList<NoiDung> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NoiDung where idDieu = ?", new String[]{String.valueOf(idDieu)});
        while (c.moveToNext()) {
            NoiDung temp = new NoiDung(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getString(4)
            );
            data.add(temp);
        }
        c.close();
        return data;
    }

    public NoiDung getById(int id) {
        ArrayList<NoiDung> data = new ArrayList<>();
        Cursor c = database.rawQuery("select * from NoiDung where id = ?", new String[]{String.valueOf(id)});
        while (c.moveToNext()) {
            NoiDung temp = new NoiDung(
                    c.getInt(0),
                    c.getInt(1),
                    c.getInt(2),
                    c.getInt(3),
                    c.getString(4)
            );
            data.add(temp);
        }
        c.close();
        return data.get(0);
    }
}
