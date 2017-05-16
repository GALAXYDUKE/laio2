package com.bofsoft.sdk.widget.listview.pulllistview;

import android.os.Build;

public class APIUtil {
    public static boolean isSupport(int apiNo) {
        return Build.VERSION.SDK_INT >= apiNo;
    }
}
