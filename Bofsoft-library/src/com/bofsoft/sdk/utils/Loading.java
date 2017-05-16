package com.bofsoft.sdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

import com.bofsoft.sdk.widget.dialog.LoadingDialog;

public class Loading {

    public Context con;

    private LoadingDialog load = null;

    private static Loading self = null;

    private boolean cancel;

    private Loading(Context con) {
        this.con = con;
        load = new LoadingDialog(con);
    }

    private static Loading getInstence(Context con) {
        if (self == null || self.getCon() != con || ((Activity) self.getCon()).isFinishing()) {
            self = null;
        }
        if (self == null) {
            self = new Loading(con);
        }
        return self;
    }

    public static void show(Context con) {
        show(con, "正在加载");
    }

    public static void show(Context con, String msg) {
        // activity不存在
        if (((Activity) con).isFinishing())
            return;
        getInstence(con).show(con, msg, false);
    }

    public static void show(Context con, boolean cancelable) {
        // activity不存在
        if (((Activity) con).isFinishing())
            return;
        getInstence(con).show(con, "正在加载", cancelable);
    }

    public static void close() {
        // activity不存在
        if (self == null || ((Activity) self.con).isFinishing())
            return;
        self.cancel();
    }

    public void show(Context con, String msg, boolean cancelable) {
        load.setCancelable(true);
        load.setTitle(msg);
        load.show();
    }

    public static Loading setCancelListener(OnCancelListener listener) {
        if (self != null)
            self.setCancel(listener);
        return self;
    }

    public void cancel() {
        if (load.isShowing())
            load.cancel();
    }

    public Context getCon() {
        return con;
    }

    public void setCon(Context con) {
        this.con = con;
    }

    public LoadingDialog getLoad() {
        return load;
    }

    public void setLoad(LoadingDialog load) {
        this.load = load;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(OnCancelListener listener) {
        cancel = listener != null;
        self.load.setOnCancelListener(listener);
    }
}
