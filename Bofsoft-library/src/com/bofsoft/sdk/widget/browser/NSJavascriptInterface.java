package com.bofsoft.sdk.widget.browser;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class NSJavascriptInterface {
    private Context con;

    public NSJavascriptInterface(Context c) {
        con = c;
    }

    /**
     * Toast
     *
     * @param toast
     */
    @JavascriptInterface
    public void toast(String toast) {
        NSInterface.getInstence().toast(con, toast);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @JavascriptInterface
    public void setTitle(String title) {
        NSInterface.getInstence().setTitle(con, title);
    }

    public Context getContext() {
        return this.con;
    }
}
