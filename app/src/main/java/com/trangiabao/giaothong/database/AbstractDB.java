package com.trangiabao.giaothong.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AbstractDB {

    protected Context context;
    protected SQLiteDatabase database;

    public AbstractDB(Context context) {
        this.context = context;
        this.database = context.openOrCreateDatabase("giaothong.db", Context.MODE_PRIVATE, null);
    }
}
