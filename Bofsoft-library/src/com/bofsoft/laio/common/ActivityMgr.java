package com.bofsoft.laio.common;

import android.app.Activity;

import java.util.ArrayList;

public class ActivityMgr {
    static MyLog mylog = new MyLog(ActivityMgr.class);

    static public ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
        // mylog.i("addActivity:" + activities.size() + " " + activity.getClass().toString());
    }

    public static void deleteActivity(Activity activity) {
        activities.remove(activity);
        // mylog.i("deleteActivity:" + activities.size() + " " + activity.getClass().toString());
    }

    public static void finishActivity() {

        for (Activity activity : activities) {
            // mylog.i("finishActivity:" + activities.size() + " " + activity.getClass().toString());
            activity.finish();
        }
    }
}
