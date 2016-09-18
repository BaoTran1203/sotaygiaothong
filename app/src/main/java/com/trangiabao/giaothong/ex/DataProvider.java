package com.trangiabao.giaothong.ex;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataProvider {

    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "giaothong.db";

    private Context context;

    public DataProvider(Context context) {
        this.context = context;
    }

    public void processCopy() {
        CopyDataBaseFromAsset();

//        File dbFile = this.context.getDatabasePath(getDatabaseName());
//        if (!dbFile.exists()) {
//            try {
//                CopyDataBaseFromAsset();
//                Toast.makeText(this, "Copying sucess from Assets folder", Toast.LENGTH_LONG).show();
//            } catch (Exception e) {
//                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
//            }
//        }
    }

    private String getDatabasePath() {
        return this.context.getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = this.context.getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(this.context.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
