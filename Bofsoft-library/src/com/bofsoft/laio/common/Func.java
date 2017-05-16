package com.bofsoft.laio.common;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;

import com.bofsoft.laio.service.DataCenter;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Pattern;

public class Func {
    public static String packageInfo(Context context, String name) {
        PackageInfo info;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            // 当前应用的版本名称
            String versionName = info.versionName;
            // 当前版本的版本号
            int versionCode = info.versionCode;
            // 当前版本的包名
            String packageName = info.packageName;
//      saveFile(versionName,"versionName.txt");
//      if (name == "versionName")
//        return versionName;
//      if (name == "versionCode")
//        return String.valueOf(versionCode);
//      if (name == "packageName")
//        return packageName;
            if (name.equals("versionName"))
                return versionName;
            if (name.equals("versionCode"))
                return String.valueOf(versionCode);
            if (name.equals("packageName"))
                return packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void saveFile(String str, String filename) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + filename;
        } else  // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + filename;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPackageName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getVersionCode(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode + "";
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    // Byte转换为整形
    public static int byteArrayToInt(byte[] b, int offset) {
        int value = 0;
        for (int i = 0; i < 4; i++) {
            int shift = (4 - 1 - i) * 8;
            value += (b[i + offset] & 0x000000FF) << shift;
        }
        return value;
    }

    // 按钮变色
    public static OnTouchListener onTouchListener = new OnTouchListener() {
        public float[] BT_SELECTED = new float[]{1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0,
                0, 1, 0};
        public float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 1, 0};

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
                // v.performClick(); // 修改点击一次按钮出现两次效果的问题
            }
            return false;
        }
    };

    // 按钮变色
    public static OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {
        public float[] BT_SELECTED = new float[]{1, 0, 0, 0, 50, 0, 1, 0, 0, 50, 0, 0, 1, 0, 50, 0, 0,
                0, 1, 0};
        public float[] BT_NOT_SELECTED = new float[]{1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0,
                0, 0, 1, 0};

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus == true) {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_NOT_SELECTED));
            } else {
                v.getBackground().setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
            }
        }

    };

    /**
     * 获取wifi的ip地址
     *
     * @param inContext
     * @return String
     */
    public static String getWIFIIpAddress(Context inContext) {
        Context context = inContext.getApplicationContext();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        if (ipAddress == 0)
            return null;
        return ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "." + (ipAddress >> 16 & 0xff)
                + "." + (ipAddress >> 24 & 0xff)); // 移位运算
    }

    /**
     * 获取GPRS的ip地址
     *
     * @return String
     */
    public static String getGPRSIpAddress() {
        String ipaddress = null;
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en
                    .hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        ipaddress = inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ipaddress = null;
        }
        return ipaddress;
    }

    /**
     * 判断Wifi是否连接
     *
     * @param inContext
     * @return boolean
     */
    public static boolean isWiFiActive(Context inContext) {
        Context context = inContext.getApplicationContext();
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getTypeName().equals("WIFI") && info[i].isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断手机是否连接网络
     *
     * @param inContext
     * @return boolean 如果有，返回true,否则，返回false
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo =
                    mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                if (mMobileNetworkInfo.isAvailable()) {
                    if (mMobileNetworkInfo.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取本地Ip地址
     *
     * @param context
     * @return String ip地址
     */
    public static String getLocalIPAddress(Context context) {
        String ipaddress = null;
        if (isWiFiActive(context) == true) {
            ipaddress = getWIFIIpAddress(context);
        } else {
            ipaddress = getGPRSIpAddress();
        }
        return ipaddress;
    }

    /**
     * 获取两个日期之间的间隔秒
     *
     * @return
     */
    public static long getGapCount(String dateBegin, String dateEnd) {
        Date Begin = strToDate(dateBegin);
        Date End = strToDate(dateEnd);
        long gap = End.getTime() - Begin.getTime();
        return gap;
    }

    public static Date strToDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        try {
            Date date = sdf.parse(str);
            return date;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String formattime(Date date, Integer format) {
        String formatStr = "yyyy-MM-dd HH:mm:ss.SSS";
        switch (format) {
            case 0:
                formatStr = "yyyy-MM-dd";
                break;
            case 1:
                formatStr = "yyyy-MM-dd HH:mm";
                break;
            case 2:
                formatStr = "HH:mm";
                break;
            default:
                break;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(formatStr, Locale.CHINA);
        return formatter.format(date);
    }

    public static String getDateGap(String dateBegin, String dateEnd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(strToDate(dateBegin));
        int intbegin = calendar.get(Calendar.DATE);
        calendar.setTime(strToDate(dateEnd));
        int intend = calendar.get(Calendar.DATE);

        long gap = getGapCount(dateBegin, dateEnd);
        gap = gap / 1000;
        if (gap < 60)
            return "刚刚";
        gap = gap / 60;
        if (gap < 60)
            return gap + "分钟前";

        if (intbegin == intend) {
            return "今天 " + formattime(strToDate(dateBegin), 2);
        }
        if (intbegin == intend - 1) {
            return "昨天 " + formattime(strToDate(dateBegin), 2);
        }
        if (intbegin == intend - 1) {
            return "前天 " + formattime(strToDate(dateBegin), 2);
        }
        return dateBegin;
    }

    public static Bitmap getBitmapFromView(View view) {
        view.destroyDrawingCache();
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache(true);
        return bitmap;
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static ServiceConnection serviceConnection = null;

    public static void startDataCenter(Context context) {
        Intent intent = new Intent(context, DataCenter.class);
        context.startService(intent);
    }

    public static void stopDataCenter(Context context) {
        Intent intent = new Intent(context, DataCenter.class);
        context.stopService(intent);
    }

    public static void bindDataCenter(Context context, ServiceConnection conn) {
        Intent intent = new Intent(context, DataCenter.class);
        serviceConnection = conn;
        context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    public static void unbindDataCenter(Context context) {
        if (null != context && serviceConnection != null) {
            context.unbindService(serviceConnection);
        }
    }

    /**
     * 获取顶部状态栏高度
     *
     * @param context 传入当前activity
     * @return int
     */
    public static int getStatusBarHeight(Context context) {
        // 顶部状态栏高度
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 检查是否为手机号
     *
     * @param mobiles 手机号
     * @return boolean
     */
    public static boolean isMobileNO(String mobiles) {
        if (mobiles == null) {
            return false;
        }
        Pattern pattern = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        return pattern.matcher(mobiles).matches();
    }

    /**
     * 检查是否中文
     *
     * @param s
     * @return
     */
    public static boolean isChinese(String str) { // 姓名验证
        boolean flag = false;
        if (str == null) {
            return flag;
        }
        try {
            flag = str.matches("[\u4E00-\u9FA5]{1,5}([·.*]?[\u4E00-\u9FA5]{1,5})*");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 检查是否为邮箱地址
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        boolean flag = false;
        if (email == null) {
            return flag;
        }
        String check =
                "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        try {
            flag = email.matches(check);
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 检查是否为身份证号码
     *
     * @param idCardNum
     * @return
     */
    public static boolean isIDCardNum(String idCardNum) {
        if (idCardNum == null) {
            return false;
        }
        if (idCardNum.length() == 18) {
            final int[] weight = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
            final int[] checkDigit = new int[]{1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
            int sum = 0;
            idCardNum.matches("^[0-9]{17}[0-9]{1}$");
            int mod = 0;
            for (int i = 0; i < 17; i++) {
                int b = 0;
                b = Integer.parseInt(idCardNum.substring(i, i + 1));// 取出输入身份证一个一个的号码
                // 然后转化为Integer
                int a = weight[i]; // 取出加权因子的一个一个的数
                sum = a * b + sum; // 累加求和；
                mod = sum % 11;
            }
            if (mod == 2) {
                return "X".equals(idCardNum.substring(17, 18));
            } else {
                return checkDigit[mod] == Integer.parseInt(idCardNum);
            }
        }
        return false;
    }


    /**
     * 分享内容随机数
     *
     * @param shareListSize
     * @return
     */
    public static int getRandomPositon(int shareListSize) {
        return new Random().nextInt(shareListSize);
    }
}
