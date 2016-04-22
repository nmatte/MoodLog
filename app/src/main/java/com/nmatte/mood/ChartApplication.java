package com.nmatte.mood;


import android.app.Application;
import android.os.StrictMode;

import net.danlew.android.joda.JodaTimeAndroid;

public class ChartApplication extends Application {
    @Override
    public void onCreate() {
        JodaTimeAndroid.init(this);
        super.onCreate();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());


    }
}
