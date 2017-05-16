package com.bofsoft.sdk.widget.browser;

import android.content.Context;
import android.widget.Toast;

import com.bofsoft.sdk.widget.base.BaseActivity;

public class NSInterface {

    private static NSInterface nif = null;

    private NSInterface() {

    }

    public static NSInterface getInstence() {
        if (nif == null)
            nif = new NSInterface();
        return nif;
    }

    /**
     * Toast
     *
     * @param con
     * @param toast
     */
    public void toast(Context con, String toast) {
        Toast.makeText(con, toast, Toast.LENGTH_SHORT).show();
    }


    public void setTitle(Context con, String title) {
        ((BaseActivity) con).setTitle(title);
    }
}
