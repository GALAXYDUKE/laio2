package com.bofsoft.sdk.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.bofsoft.sdk.R;
import com.bofsoft.sdk.config.BaseSysConfig;

public abstract class BaseButton extends RelativeLayout implements Cloneable {

    private int id;

    protected Context context;

    private boolean autoAttr = false;

    public BaseButton(Context context, int id) {
        super(context, null);
        this.context = context;
        autoAttr = true;
        setId(id);
        initView();
    }

    public BaseButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        autoAttr = false;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initView() {
        this.setMinimumHeight(BaseSysConfig.actionBarHeight);
        this.setMinimumWidth(BaseSysConfig.actionBarHeight);
        int gap = getResources().getDimensionPixelOffset(R.dimen.DP_5);
        this.setPadding(gap, 0, gap, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        if (id != 0) {
            super.setId(id);
        }
    }

    public boolean isAutoAttr() {
        return autoAttr;
    }

    public void setAutoAttr(boolean autoAttr) {
        this.autoAttr = autoAttr;
    }
}
