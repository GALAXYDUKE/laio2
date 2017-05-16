package com.bofsoft.laio.laiovehiclegps.Application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.bofsoft.laio.laiovehiclegps.Config.Config;
import com.bofsoft.laio.laiovehiclegps.Database.PublicDBManager;

/**
 * Created by szw on 2017/2/16.
 */

public class MyApplication extends Application{

    public static Context context = null;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        Config.getInstance().init(context);

        PublicDBManager.getInstance(this);
        SDKInitializer.initialize(getApplicationContext());
//        Func.startDataCenter(context);
    }
}
