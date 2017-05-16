package com.bofsoft.sdk.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

import com.bofsoft.sdk.config.BaseSysConfig;

@SuppressLint("ClickableViewAccessibility")
public class IconButton extends BaseButton implements OnTouchListener {

    private int resId;

    private int bgNormalColor;

    private int bgDownColor;

    private ImageView imageView;

    public IconButton(Context context, int id, int resId) {
        super(context, id);
        this.resId = resId;
        this.bgNormalColor = BaseSysConfig.actionBarButtonNormalColor;
        this.bgDownColor = BaseSysConfig.actionBarButtonDownColor;
        drawView();
        this.setOnTouchListener(this);
    }

    public IconButton(Context context, int id, int resId, Integer bgNormalColor, Integer bgDownColor) {
        super(context, id);
        this.resId = resId;
        this.bgNormalColor =
                bgNormalColor == null ? BaseSysConfig.actionBarButtonNormalColor : bgNormalColor;
        this.bgDownColor = bgDownColor == null ? BaseSysConfig.actionBarButtonDownColor : bgDownColor;
        drawView();
        this.setOnTouchListener(this);
    }


    public IconButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void drawView() {
        if (imageView == null) {
            addView(imageView = new ImageView(getContext()), new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            setGravity(Gravity.CENTER);
        }
        imageView.setBackgroundDrawable(getResources().getDrawable(resId));
        this.setBackgroundColor(this.bgNormalColor);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isAutoAttr())
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(this.bgDownColor);
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(this.bgNormalColor);
                break;
        }
        return false;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
        drawView();
    }

    public int getBgNormalColor() {
        return bgNormalColor;
    }

    public void setBgNormalColor(int bgNormalColor) {
        this.bgNormalColor = bgNormalColor;
        this.setBackgroundColor(this.bgNormalColor);
    }

    public int getBgDownColor() {
        return bgDownColor;
    }

    public void setBgDownColor(int bgDownColor) {
        this.bgDownColor = bgDownColor;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public BaseButton clone() {
        return new IconButton(context, getId(), resId, bgNormalColor, bgDownColor);
    }
}
