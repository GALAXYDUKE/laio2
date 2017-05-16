package com.bofsoft.laio.common;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class MyLog {
    public static final int Log_Show_Limit_Lenght = 2048; // 调试日志的最大显示长度
    private Class<?> c = null;
//    private boolean is_w = true;
//    private boolean is_file = true;

    public MyLog(Class<?> c) {
        this.c = c;
    }

    public void e(Object msg) {
        if (ServerConfigall.MyLog_is_w) {
//            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
//            String date = sDateFormat.format(new java.util.Date());
//            Log.e("mylog:" + date + ":" + c.getName(), String.valueOf(msg));
            Log.e("mylog:" + c.getName(), String.valueOf(msg));
        }
        if (ServerConfigall.Mylog_is_file) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String date = sDateFormat.format(new java.util.Date());
            writeFileToSD(date + "|" + c.getName() + ":" + String.valueOf(msg));
        }
    }

    public void w(Object msg) {
        if (ServerConfigall.MyLog_is_w) {
//            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
//            String date = sDateFormat.format(new java.util.Date());
//            Log.w("mylog:" + date + ":" + c.getName(), String.valueOf(msg));
            Log.w("mylog:" + c.getName(), String.valueOf(msg));
        }
        if (ServerConfigall.Mylog_is_file) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String date = sDateFormat.format(new java.util.Date());
            writeFileToSD(date + "|" + c.getName() + ":" + String.valueOf(msg));
        }
    }

    public void i(Object msg) {
        if (ServerConfigall.MyLog_is_w) {
//            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
//            String date = sDateFormat.format(new java.util.Date());
//            Log.i("mylog:" + date + ":" + c.getName(), String.valueOf(msg));
            Log.i("mylog:" + c.getName(), String.valueOf(msg));
        }
        if (ServerConfigall.Mylog_is_file) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String date = sDateFormat.format(new java.util.Date());
            writeFileToSD(date + "|" + c.getName() + ":" + String.valueOf(msg));
        }
    }

    public void v(Object msg) {
        if (ServerConfigall.MyLog_is_w) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String date = sDateFormat.format(new java.util.Date());
            Log.v("mylog:" + date + ":" + c.getName(), String.valueOf(msg));
        }
        if (ServerConfigall.Mylog_is_file) {
            SimpleDateFormat sDateFormat = new SimpleDateFormat("MM-dd HH:mm:ss.SSS");
            String date = sDateFormat.format(new java.util.Date());
            writeFileToSD(date + "|" + c.getName() + ":" + String.valueOf(msg));
        }
    }

    public void e(StackTraceElement[] stackTraceElements) {
        String tmp = "";
        for (StackTraceElement s : stackTraceElements) {
            tmp += "!!!!!!!!--------:	" + s.toString() + "\n";
        }
        e(tmp);
    }

    private void writeFileToSD(String log) {
        // String sdStatus = Environment.getExternalStorageState();
        // if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) {
        // Log.d("TestFile", "SD card is not avaiable/writeable right now.");
        // return;
        // }
        try {
            String pathName = ConfigAll.fileLogPath;
            String fileName = "log.txt";
            File path = new File(pathName);
            File file = new File(pathName, fileName);
            if (!path.exists()) {
                Log.d("TestFile", "Create the path:" + pathName);
                path.mkdir();
            }
            if (!file.exists()) {
                Log.d("TestFile", "Create the file:" + fileName);
                file.createNewFile();
            }
            FileOutputStream stream = new FileOutputStream(file, true);
            String s = log;
            byte[] buf = s.getBytes();
            stream.write(buf);
            stream.write("\r\n".getBytes());
            stream.close();

        } catch (Exception e) {
            Log.e("TestFile", "Error on writeFilToSD.");
            e.printStackTrace();
        }
    }

}
