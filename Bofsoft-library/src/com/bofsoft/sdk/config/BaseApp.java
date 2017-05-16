package com.bofsoft.sdk.config;

import android.app.Application;
import android.content.Context;

public class BaseApp extends Application {

    public static Context context = null;

    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

}
