package com.bofsoft.laio.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * UncaughtException处理类,当程序发生Uncaught异常的时候,有该类来接管程序,并记录发送错误报告.
 *
 * @author user
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private MyLog myLog = new MyLog(getClass());

    private boolean isDebug = false;

    public final String TAG = "CrashHandler";
    public final String fileName = "log.txt";

    private static final int LOG_FILE_LENGTH = 10 * 1024; // 限制10K

    // 系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    // CrashHandler实例
    private static CrashHandler INSTANCE = new CrashHandler();
    // 程序的Context对象
    private static Context mContext;
    // 用来存储设备信息和异常信息
    private Map<String, String> infos = new HashMap<String, String>();

    // 用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA);

    /**
     * 保证只有一个CrashHandler实例
     */
    private CrashHandler() {
    }

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static CrashHandler getInstance() {
        return INSTANCE;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(Context context) {
        mContext = context;
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            reportError(ex);
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            }
            // 退出程序
            ActivityMgr.finishActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 使用Toast来显示异常信息
        try {
            new Thread() {
                @Override
                public void run() {
                    if (mContext != null) {
                        Looper.prepare();
                        Toast.makeText(mContext, "很抱歉,程序出现异常,即将退出.", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            }.start();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        ex.printStackTrace();
        myLog.e(ex.getStackTrace());

        // 处理异常信息
        handleCrashInfo(ex);
        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public void collectDeviceInfo(Context ctx) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 处理异常信息
     *
     * @param ex
     * @return
     */
    public void handleCrashInfo(Throwable ex) {
        String error = getError(ex);
        myLog.e(error);
        if (mContext != null && NetworkUtil.isNetworkAvailable(mContext)) {
            reportError(ex);
        } else {
            // 收集设备参数信息
            collectDeviceInfo(mContext);
            saveErrorToSdcard(error);
        }
    }

    public String getError(Throwable ex) {
        StringBuffer error = new StringBuffer();

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        error.append(result).append("\n\n");

        for (Map.Entry<String, String> entry : infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            error.append(key + " = " + value + "\n");
        }
        String time = formatter.format(new Date());
        error.append("Happen_Time = " + time).append("\n");
        return error.toString();
    }

    public void reportError(Throwable ex) {
        if (!isDebug) {
//       MobclickAgent.reportError(mContext, ex);
        }
    }

    public void reportError(String error) {
        if (!isDebug) {
//       MobclickAgent.reportError(mContext, error);
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param error 异常日志信息
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveErrorToSdcard(String error) {
        if (error == null) {
            return null;
        }
        if (!isDebug) {
            try {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    String path = ConfigAll.APP_CRASH_PATH;
                    File dir = new File(path);
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    FileOutputStream fos = new FileOutputStream(path + fileName, true);
                    fos.write(error.toString().getBytes());
                    fos.close();
                    return dir.getName();
                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String readErrorFromSdcard() {
        String error = null;
        String path = ConfigAll.APP_CRASH_PATH;
        File errorFile = new File(path + fileName);
        if (errorFile.exists() && errorFile.isFile() && errorFile.length() < LOG_FILE_LENGTH) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(errorFile);
                error = inputStream2String(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            errorFile.delete();
        }
        return error;
    }

    public static String inputStream2String(InputStream in_st) {
        BufferedReader in = new BufferedReader(new InputStreamReader(in_st));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = in.readLine()) != null && buffer.length() < LOG_FILE_LENGTH) {
                buffer.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 上传异常日志
     *
     * @return
     */
    public boolean uploadErrorLog() {
        boolean isUpload = false;
        if (mContext != null && NetworkUtil.isNetworkAvailable(mContext)) {
            String error = readErrorFromSdcard();
            if (!TextUtils.isEmpty(error)) { // 限制在10k以内
                if (NetworkUtil.isWifi(mContext)) {
                    reportError(error);
                    isUpload = true;
                } else {
                    if (error.length() < LOG_FILE_LENGTH) {
                        reportError(error);
                        isUpload = true;
                    }
                }
            }
        }
        return isUpload;
    }
}
