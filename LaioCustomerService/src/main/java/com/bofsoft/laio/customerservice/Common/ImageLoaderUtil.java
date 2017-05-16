package com.bofsoft.laio.customerservice.Common;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.widget.ImageView;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;

public class ImageLoaderUtil {
    static MyLog myLog = new MyLog(ImageLoaderUtil.class);
    private static String oldUrlString = ".jpg";
    private static String smallUrlString = "_240x180.jpg";
    private static String bigUrlString = "_640x480.jpg";
    private static int delayBeforeLoading = 300;
    private static boolean dedug = false;

    static DisplayImageOptions mDisplaySmallOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.icon_default_small) // resource or drawable
            .showImageForEmptyUri(R.mipmap.icon_default_small) // resource or drawable
            .showImageOnFail(R.mipmap.icon_default_small) // resource or drawable
            .resetViewBeforeLoading(false) // default
            .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
            .cacheOnDisk(true) // default
            .considerExifParams(false) // default
            // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
            // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
            .bitmapConfig(Bitmap.Config.RGB_565) // default
            // .decodingOptions(...)
            .displayer(new SimpleBitmapDisplayer()) // default
            .handler(new Handler()) // default
            .build();

    static DisplayImageOptions mDisplayBigOptions = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.icon_default_big) // resource or drawable
            .showImageForEmptyUri(R.mipmap.icon_default_big) // resource or drawable
            .showImageOnFail(R.mipmap.icon_default_big) // resource or drawable
            .resetViewBeforeLoading(false) // default
            .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
            .cacheOnDisk(true) // default
            .considerExifParams(false) // default
            // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
            // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
            .bitmapConfig(Bitmap.Config.RGB_565) // default
            .displayer(new SimpleBitmapDisplayer()) // default
            .handler(new Handler()) // default
            .build();

    public static DisplayImageOptions mDisplayAdvertisementOptions = new DisplayImageOptions.Builder()
            .resetViewBeforeLoading(false)
            .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true)
            .cacheOnDisk(true)
            .considerExifParams(false)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
            .bitmapConfig(Bitmap.Config.RGB_565)
            .displayer(new SimpleBitmapDisplayer())
            .handler(new Handler())
            .build();

    public static void displayImage(String imageUrl, ImageView imageView) {
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, mDisplayBigOptions);
    }

    public static void displayImage(String imageUrl, ImageView imageView, DisplayImageOptions options) {
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, options);
    }

    /**
     * @param imageUrl
     * @param imageView
     * @param defaultImgRes 默认显示的图片资源id
     */
    public static void displayImage(String imageUrl, ImageView imageView, int defaultImgRes) {

        DisplayImageOptions displaySmallOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImgRes) // resource or drawable
                        .showImageForEmptyUri(defaultImgRes) // resource or drawable
                        .showImageOnFail(defaultImgRes) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //
                        // default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    /**
     * @param imageUrl
     * @param imageView
     * @param defaultImgRes 默认显示的图片资源id
     */
    public static void displayImageforCertification(String imageUrl, ImageView imageView, int defaultImgRes) {

        DisplayImageOptions displaySmallOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImgRes) // resource or drawable
                        .showImageForEmptyUri(defaultImgRes) // resource or drawable
                        .showImageOnFail(defaultImgRes) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(false) // default
                        .cacheOnDisk(false) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //
                        // default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    public static void displayImage(String imageUrl, ImageView imageView, Drawable defaultImg) {

        DisplayImageOptions displaySmallOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImg) // resource or drawable
                        .showImageForEmptyUri(defaultImg) // resource or drawable
                        .showImageOnFail(defaultImg) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //
                        // default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    /**
     * 加载小尺寸图片
     *
     * @param imageUrl
     * @param imageView
     */
    public static void displaySmallSizeImage(String imageUrl, ImageView imageView) {

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, smallUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>SmallSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, mDisplaySmallOptions);
    }

    /**
     * 加载小尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param displaySmallOptions
     */
    public static void displaySmallSizeImage(String imageUrl, ImageView imageView,
                                             DisplayImageOptions displaySmallOptions) {

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, smallUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>SmallSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    /**
     * 加载小尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param defaultImgRes
     */
    public static void displaySmallSizeImage(String imageUrl, ImageView imageView, int defaultImgRes) {

        DisplayImageOptions displaySmallOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImgRes) // resource or drawable
                        .showImageForEmptyUri(defaultImgRes) // resource or drawable
                        .showImageOnFail(defaultImgRes) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, smallUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>SmallSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    /**
     * 加载小尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param defaultImg
     */
    public static void displaySmallSizeImage(String imageUrl, ImageView imageView, Drawable defaultImg) {

        DisplayImageOptions displaySmallOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImg) // resource or drawable
                        .showImageForEmptyUri(defaultImg) // resource or drawable
                        .showImageOnFail(defaultImg) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, smallUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>SmallSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displaySmallOptions);
    }

    /**
     * 加载大尺寸图片
     *
     * @param imageUrl
     * @param imageView
     */
    public static void displayBigSizeImage(String imageUrl, ImageView imageView) {
        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, bigUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>BigSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, mDisplayBigOptions);
    }

    /**
     * 加载大尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param mDisplaySmallOptions
     */
    public static void displayBigSizeImage(String imageUrl, ImageView imageView,
                                           DisplayImageOptions displayBigOptions) {
        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, bigUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>BigSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displayBigOptions);
    }

    /**
     * 加载大尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param defaultImgRes
     */
    public static void displayBigSizeImage(String imageUrl, ImageView imageView, int defaultImgRes) {

        DisplayImageOptions displayBigOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImgRes) // resource or drawable
                        .showImageForEmptyUri(defaultImgRes) // resource or drawable
                        .showImageOnFail(defaultImgRes) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, bigUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>BigSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displayBigOptions);
    }

    /**
     * 加载大尺寸图片
     *
     * @param imageUrl
     * @param imageView
     * @param defaultImg
     */
    public static void displayBigSizeImage(String imageUrl, ImageView imageView, Drawable defaultImg) {

        DisplayImageOptions displayBigOptions =
                new DisplayImageOptions.Builder().showImageOnLoading(defaultImg) // resource or drawable
                        .showImageForEmptyUri(defaultImg) // resource or drawable
                        .showImageOnFail(defaultImg) // resource or drawable
                        .resetViewBeforeLoading(false) // default
                        .delayBeforeLoading(delayBeforeLoading).cacheInMemory(true) // default
                        .cacheOnDisk(true) // default
                        .considerExifParams(false) // default
                        // .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) //default
                        // .bitmapConfig(Bitmap.Config.ARGB_8888) // default
                        .imageScaleType(ImageScaleType.IN_SAMPLE_INT) // default
                        .bitmapConfig(Bitmap.Config.RGB_565) // default
                        .displayer(new SimpleBitmapDisplayer()) // default
                        .handler(new Handler()) // default
                        .build();

        if (imageUrl != null) {
            imageUrl = imageUrl.replace(oldUrlString, bigUrlString);
        }
        if (dedug) {
            myLog.i("=========ImageLoaderUtil>>BigSize>>url=" + imageUrl);
        }
        ImageLoader.getInstance().displayImage(imageUrl, imageView, displayBigOptions);

    }

}
