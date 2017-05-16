package com.bofsoft.sdk.utils;

import android.annotation.SuppressLint;

import com.bofsoft.sdk.config.BaseLogConfig;

import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class Log {

    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");

    public static void e(String tag, String msg, Throwable tr) {
        if (isEmpty())
            return;
        if (BaseLogConfig.getInstence().isConsole())
            android.util.Log.e(tag, msgFormat(msg), tr);
        if (BaseLogConfig.getInstence().isFile())
            ;
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (isEmpty())
            return;
        if (BaseLogConfig.getInstence().isConsole())
            android.util.Log.i(tag, msgFormat(msg), tr);
        if (BaseLogConfig.getInstence().isFile())
            ;
    }

    public static void v(String tag, String msg, Throwable tr) {
        if (isEmpty())
            return;
        if (BaseLogConfig.getInstence().isConsole())
            android.util.Log.v(tag, msgFormat(msg), tr);
        if (BaseLogConfig.getInstence().isFile())
            ;
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (isEmpty())
            return;
        if (BaseLogConfig.getInstence().isConsole())
            android.util.Log.w(tag, msgFormat(msg), tr);
        if (BaseLogConfig.getInstence().isFile())
            ;
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (isEmpty())
            return;
        if (BaseLogConfig.getInstence().isConsole())
            android.util.Log.wtf(tag, msgFormat(msg), tr);
        if (BaseLogConfig.getInstence().isFile())
            ;
    }

    public static String msgFormat(String msg) {
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        String time = dateFormat.format(new Date());
        String format =
                "-----Path[" + className + ":" + methodName + "LineNum:" + lineNumber + "] Time:" + time
                        + "\n" + msg;
        return format;
    }

    private static boolean isEmpty() {
        return BaseLogConfig.getInstence() == null;
    }
}
