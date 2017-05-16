package com.bofsoft.sdk.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class Tel {

    private static Tel self;

    private Tel() {

    }

    public static Tel getInstence() {
        if (self == null)
            self = new Tel();
        return self;
    }

    /**
     * 直接拨号
     *
     * @param act
     * @param telPhone
     */
    public void call(Activity act, String number) {
        call(act, number, Intent.ACTION_CALL);
    }

    /**
     * 调用拨号界面
     *
     * @param act
     * @param telPhone
     */
    public void dial(Activity act, String number) {
        call(act, number, Intent.ACTION_DIAL);
    }

    private void call(Activity act, String number, String action) {
        Uri uri = Uri.parse("tel:" + number);
        Intent intent = new Intent();
        intent.setAction(action);
        intent.setData(uri);
        act.startActivity(intent);
    }
}
