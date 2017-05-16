package com.bofsoft.sdk.config;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;

import com.bofsoft.laio.common.SharedPreferencesHelper;
import com.bofsoft.sdk.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统配置信息
 */
public abstract class BaseSysConfig {

    private Context _con;
    private Resources _res;

    /**
     * --------------------------------------网络信息------------------------------ ----------------------
     */
    /**
     * 服务器IP
     */
    private static String IP = null;
    /**
     * 服务器端口
     */
    private static int PORT = 0;

    /**
     * --------------------------------------系统信息------------------------------ ----------------------
     */
    /**
     * 软件启动时间
     */
    public static Date launchTime = null;
    public static SharedPreferencesHelper spHelper = null;
    /**
     * 应用包名
     */
    public static String packageName = "";
    /**
     * 应用包路径
     */
    public static String packagePath = "";
    /**
     * SD卡路径
     */
    public static String SDPath = "";
    /**
     * 手机自带文件目录 /Android/data/app_package/files 卸载程序会自动清理这个目录
     */
    public static String filesPath = "";
    /**
     * SD卡文件目录 /Android/data/app_package/files 卸载程序会自动清理这个目录
     */
    public static String filesSDPath = "";
    /**
     * 手机自带缓存目录 /Android/data/app_package/cache 卸载程序会自动清理这个目录
     */
    public static String cachePath = "";
    /**
     * SD卡缓存目录 /Android/data/app_package/cache 卸载程序会自动清理这个目录
     */
    public static String cacheSDPath = "";
    /**
     * APP默认文件目录
     */
    public static String appFilesPath = "";
    /**
     * App默认缓存目录
     */
    public static String appCachePath = "";
    /**
     * 屏幕信息
     */
    public static DisplayMetrics dm = new DisplayMetrics();
    /**
     * SDK
     */
    public static int SDK = 0;

    /**
     * -------------------------------------ActionBar信息------------------------ ----------------------
     * -----
     */
    /**
     * 标题栏高度
     */
    public static int actionBarHeight = 0;
    /**
     * 标题栏背景视图
     */
    public static int actionBarDrawableResources = 0;
    /**
     * 标题栏背景颜色
     */
    public static int actionBarColor = 0;
    /**
     * 标题栏标题颜色
     */
    public static int actionBarTitleColor = 0;
    /**
     * 标题栏字体大小
     */
    public static int actionBarTitleSize = 0;
    /**
     * 标题栏文字按钮颜色
     */
    public static int actionBarTexButtonNormalColor = 0;
    /**
     * 标题栏文字按钮按下颜色
     */
    public static int actionBarTexButtonDownColor = 0;
    /**
     * 标题栏文字按钮大小
     */
    public static int actionBarTexButtonSize = 0;
    /**
     * 标题栏按钮背景颜色
     */
    public static int actionBarButtonNormalColor = 0;
    /**
     * 标题栏按钮按下背景颜色
     */
    public static int actionBarButtonDownColor = 0;

    /**
     * -------------------------------------activity----------------------------
     * ---------------------- ------
     */
    /**
     * Activity左入动画
     */
    public static int activityAnimLeftInResId = 0;
    /**
     * Activity右入动画
     */
    public static int activityAnimRightInResId = 0;
    /**
     * Activity左出动画
     */
    public static int activityAnimLeftOutResId = 0;
    /**
     * Activity右出动画
     */
    public static int activityAnimRightOutResId = 0;

    /**
     * -------------------------------------资源----------------------------------
     * ----------------------
     */
    public static int loadingResId = 0;

    /**
     * -------------------------------------资源信息--------------------------------
     * ---------------------- --
     */
    public static List<Drawable> defaultImage = new ArrayList<Drawable>();

    protected BaseSysConfig() {
        super();
    }

    protected void init(Context con) {
        this._con = con;
        this._res = con.getResources();

        // 初使网络信息
        if (getIP() != null)
            IP = getIP();
        if (getPORT() != 0)
            PORT = getPORT();

        // 系统信息
        launchTime = new Date();
        spHelper = new SharedPreferencesHelper(_con);
        SDPath = Environment.getExternalStorageDirectory().getPath();
        filesPath = _con.getFilesDir().toString();
        cachePath = _con.getCacheDir().toString();
        packageName = _con.getPackageName();
        packagePath = _con.getPackageResourcePath();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (_con.getExternalFilesDir("") == null) {
                filesSDPath = SDPath + File.separator + "Android" + filesPath.substring(5, filesPath.length());
            } else {
                filesSDPath = _con.getExternalFilesDir("").toString();
            }
            if (_con.getExternalCacheDir() == null) {
                cacheSDPath = SDPath + File.separator + "Android" + cachePath.substring(5, cachePath.length());
            } else {
                cacheSDPath = _con.getExternalCacheDir().toString();
            }
        }
        appFilesPath = (filesSDPath != null && filesSDPath.length() > 0) ? filesSDPath : filesPath;
        appCachePath = (cacheSDPath != null && cacheSDPath.length() > 0) ? cacheSDPath : cachePath;

        // 标题栏信息
        actionBarHeight =
                (getActionBarHeight() == null) ? _res.getDimensionPixelOffset(R.dimen.actionbar_height)
                        : getActionBarHeight();
        actionBarDrawableResources =
                (getActionBarDrawableResources() == null) ? 0 : getActionBarDrawableResources();
        actionBarColor =
                (getActionBarColor() == null) ? _res.getColor(R.color.actionbar_color)
                        : getActionBarColor();
        actionBarTitleColor =
                (getActionBarTitleColor() == null) ? _res.getColor(R.color.actionbar_title_color)
                        : getActionBarTitleColor();
        actionBarTitleSize =
                (getActionBarTitleSize() == null) ? _res
                        .getDimensionPixelOffset(R.dimen.actionbar_title_size) : getActionBarTitleSize();
        actionBarTexButtonNormalColor =
                (getActionBarTexButtonNormalColor() == null) ? _res
                        .getColor(R.color.actionbar_texbutton_normalcolor) : getActionBarTexButtonNormalColor();
        actionBarTexButtonDownColor =
                (getActionBarTexButtonDownColor() == null) ? _res
                        .getColor(R.color.actionbar_texbutton_downcolor) : getActionBarTexButtonDownColor();
        actionBarTexButtonSize =
                (getActionBarTexButtonSize() == null) ? _res
                        .getDimensionPixelOffset(R.dimen.actionbar_texbutton_size)
                        : getActionBarTexButtonSize();
        actionBarButtonNormalColor =
                (getActionBarButtonNormalColor() == null) ? _res
                        .getColor(R.color.actionbar_button_normalcolor) : getActionBarButtonNormalColor();
        actionBarButtonDownColor =
                (getActionBarButtonDownColor() == null) ? _res.getColor(R.color.actionbar_button_downcolor)
                        : getActionBarButtonDownColor();

        // activity
        activityAnimLeftInResId =
                getActivityAnimLeftInResId() == null ? R.anim.activity_left_in
                        : getActivityAnimLeftInResId();
        activityAnimLeftOutResId =
                getActivityAnimLeftOutResId() == null ? R.anim.activity_left_out
                        : getActivityAnimLeftOutResId();
        activityAnimRightInResId =
                getActivityAnimRightInResId() == null ? R.anim.activity_right_in
                        : getActivityAnimRightInResId();
        activityAnimRightOutResId =
                getActivityAnimRightOutResId() == null ? R.anim.activity_right_out
                        : getActivityAnimRightOutResId();

        loadingResId = getLoadingResId() == null ? R.drawable.ic_launcher : getLoadingResId();
    }

    /**
     * 设置服务器IP
     */
    protected abstract String getIP();

    /**
     * 服务器端口
     */
    protected abstract int getPORT();

    /**
     * 标题栏高度
     */
    protected abstract Integer getActionBarHeight();

    /**
     * 标题栏背景视图
     */
    protected abstract Integer getActionBarDrawableResources();

    /**
     * 标题栏背景颜色
     */
    protected abstract Integer getActionBarColor();

    /**
     * 标题栏标题颜色
     */
    protected abstract Integer getActionBarTitleColor();

    /**
     * 标题栏字体大小
     */
    protected abstract Integer getActionBarTitleSize();

    /**
     * 标题栏文字按钮颜色
     */
    protected abstract Integer getActionBarTexButtonNormalColor();

    /**
     * 标题栏文字按钮按下颜色
     */
    protected abstract Integer getActionBarTexButtonDownColor();

    /**
     * 标题栏文字按钮大小
     */
    protected abstract Integer getActionBarTexButtonSize();

    /**
     * 标题栏按钮背景颜色
     */
    protected abstract Integer getActionBarButtonNormalColor();

    /**
     * 标题栏按钮按下背景颜色
     */
    protected abstract Integer getActionBarButtonDownColor();

    /**
     * 默认图片 resId
     */
    protected abstract Integer[] getDefaultImage();

    /**
     * activity左入
     */
    protected abstract Integer getActivityAnimLeftInResId();

    /**
     * activity右入
     */
    protected abstract Integer getActivityAnimRightInResId();

    /**
     * activity左出
     */
    protected abstract Integer getActivityAnimLeftOutResId();

    /**
     * activity右出
     */
    protected abstract Integer getActivityAnimRightOutResId();

    /**
     * loading资源
     */
    protected abstract Integer getLoadingResId();

    public Context getContext() {
        return this._con;
    }

    public Resources getResources() {
        return this._res;
    }

    /**
     * 获取服务器URL
     */
    public static String getRUL() {
        return IP + ":" + PORT;
    }

    public static int getWidth(Activity act) {
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getHeight(Activity act) {
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int getDpi(Activity act) {
        act.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.densityDpi;
    }

    public static int getStatusBarHeight(Context con) {
        int result = 0;
        int resourceId = con.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = con.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
