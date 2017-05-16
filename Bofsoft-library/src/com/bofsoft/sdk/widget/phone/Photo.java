package com.bofsoft.sdk.widget.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.bofsoft.sdk.widget.popupwindow.OptionPopupWindow;
import com.bofsoft.sdk.widget.popupwindow.OptionPopupWindow.onSetChangeItemListener;

import java.io.Serializable;

/**
 * 照相功能插件
 *
 * @author Administrator
 */
@SuppressLint("NewApi")
public class Photo implements Serializable {
    private static final long serialVersionUID = 7840900861071229237L;

    public static String SER_KEY = "SER_KEY";
    private static Photo photo;
    public Iphoto iphoto;
    private Activity sourceActivity;
    private int width = 250;
    private int height = 250;
    private int aspectX = 1;
    private int aspectY = 1;
    private boolean crop = true;

    /**
     * 获得图片的宽度
     *
     * @return
     */
    public int getWidth() {
        return width;
    }

    public Photo setCrop(boolean value) {
        this.crop = value;
        return this;
    }

    /**
     * 设置图片宽度
     *
     * @param width
     * @return
     */
    public Photo setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * 获取图片高度
     *
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * 设置图片高度
     *
     * @param height
     * @return
     */
    public Photo setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * 获取剪切x位置
     *
     * @return
     */
    public int getAspectX() {
        return aspectX;
    }

    /**
     * 设置剪切x位置
     *
     * @return
     */
    public Photo setAspectX(int aspectX) {
        this.aspectX = aspectX;
        return this;
    }

    /**
     * 获取剪切y位置
     *
     * @return
     */
    public int getAspectY() {
        return aspectY;
    }

    /**
     * 设置剪切y位置
     *
     * @return
     */
    public Photo setAspectY(int aspectY) {
        this.aspectY = aspectY;
        return this;
    }

    private Photo(Activity activity) {
        this.sourceActivity = activity;
    }

    /**
     * 设置完成时监听器
     *
     * @param iPhoto
     * @return
     */
    public Photo setCompleteListener(Iphoto iPhoto) {
        this.iphoto = iPhoto;
        return this;
    }

    /**
     * 获得一个实例
     *
     * @return
     */
    public static Photo getInstance() {
        return photo;
    }

    /**
     * 创建工具箱
     *
     * @param activity
     * @return
     */
    public static Photo create(Activity activity) {
        photo = new Photo(activity);

        OptionPopupWindow.show(activity, new String[]{"照相机", "相册", "取消"}, new onSetChangeItemListener() {

            @Override
            public void onClick(View v, int position) {
                switch (position) {
                    case 0:
                        photo.goHandlerActivity(1);
                        OptionPopupWindow.close();
                        break;
                    case 1:
                        photo.goHandlerActivity(2);
                        OptionPopupWindow.close();
                        break;
                }
            }
        });
        return photo;
    }

    /**
     * @param type 1 相机 2 相册
     */
    private void goHandlerActivity(int type) {
        Intent intent = new Intent(sourceActivity, PhotoHandler.class);
        intent.putExtra("actionType", type);
        intent.putExtra("width", width);
        intent.putExtra("height", height);
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("crop", crop);
        sourceActivity.startActivity(intent);
    }
}
