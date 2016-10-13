package com.trangiabao.giaothong.ex;

import android.app.ActivityManager;
import android.content.Context;

import java.text.DecimalFormat;

public class MyMemory {

    private Context context;
    private ActivityManager.MemoryInfo mi;
    private ActivityManager activityManager;

    public MyMemory(Context context) {
        this.context = context;
        this.mi = new ActivityManager.MemoryInfo();
        this.activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        this.activityManager.getMemoryInfo(mi);
    }

    public String toString() {
        double totalMemory = this.mi.totalMem / 1048576L * 1.0;

        double freeMemory = this.mi.availMem / 1048576L * 1.0;
        double freePercent = freeMemory / totalMemory * 100.0;

        double usedMemory = totalMemory - freeMemory;
        double usedPercent = usedMemory / totalMemory * 100.0;

        totalMemory = Double.parseDouble(new DecimalFormat("##.##").format(totalMemory));
        freeMemory = Double.parseDouble(new DecimalFormat("##.##").format(freeMemory));
        freePercent = Double.parseDouble(new DecimalFormat("##.##").format(freePercent));
        usedMemory = Double.parseDouble(new DecimalFormat("##.##").format(usedMemory));
        usedPercent = Double.parseDouble(new DecimalFormat("##.##").format(usedPercent));

        String activity = "Activity: " + this.context.getClass().getSimpleName() + "\n";
        String ram = "Total memory: " + String.valueOf(totalMemory) + "Mb - 100%\n";
        String free = "Free memory: " + String.valueOf(freeMemory) + "Mb - " + freePercent + "%\n";
        String used = "Used memory: " + String.valueOf(usedMemory) + "Mb - " + usedPercent + "%\n";
        return "\n" + activity + ram + free + used;
    }
}
