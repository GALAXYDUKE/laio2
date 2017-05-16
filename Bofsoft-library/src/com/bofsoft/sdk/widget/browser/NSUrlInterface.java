package com.bofsoft.sdk.widget.browser;

import android.content.Context;
import android.graphics.Bitmap;
import android.webkit.WebView;

import java.util.HashMap;
import java.util.Map;

public class NSUrlInterface {

    /**
     * 调用前缀
     */
    private static String headStr = "call:";

    private Context con;

    public NSUrlInterface(Context con) {
        this.con = con;
    }

    public Context getContext() {
        return this.con;
    }

    public boolean onLoadding(WebView view, String url) {
        if (isInnerInterface(url)) {
            String method = getMethod(url);
            if (method.equals("toast"))
                toast(url);
            else if (method.equals("setTitle"))
                setTitle(url);
            return true;
        }
        return false;
    }

    public void onStarted(WebView view, String url, Bitmap favicon) {

    }

    public void onFilished(WebView view, String url) {

    }

    /**
     * 是否内部接口 Call:method?param1=xx&param2=xx
     *
     * @param url
     * @return
     */
    public boolean isInnerInterface(String url) {
        if (url == null)
            return false;
        return url.startsWith(headStr);
    }

    /**
     * 获取调用方法
     *
     * @param url
     * @return
     */
    public String getMethod(String url) {
        String method = url.substring(headStr.length());
        if (method.indexOf("?") == -1)
            return method;
        return method.substring(0, method.indexOf("?"));
    }

    /**
     * 获取参数
     *
     * @param url
     * @return
     */
    public Map<String, Object> getParam(String url) {
        Map<String, Object> param = new HashMap<String, Object>();
        if (url.indexOf("?") == -1 || url.endsWith("?"))
            return param;
        String ps = url.substring(url.indexOf("?") + 1, url.length());
        String[] pss = ps.split("&");
        for (String p : pss) {
            String[] params = p.split("=");
            param.put(params[0], params[1]);
        }
        return param;
    }

    /**
     * Toast
     *
     * @param text
     */
    public void toast(String url) {
        Map<String, Object> param = getParam(url);
        NSInterface.getInstence().toast(con, (String) param.get("text"));
    }

    /**
     * 设置标题
     *
     * @param title
     */
    public void setTitle(String url) {
        Map<String, Object> param = getParam(url);
        NSInterface.getInstence().setTitle(con, (String) param.get("title"));
    }

}
