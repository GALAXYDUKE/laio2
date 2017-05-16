package com.bofsoft.sdk.widget.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

public class BaseBrowser extends WebView {

    private boolean autoScroll = true;

    public BaseBrowser(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public BaseBrowser(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (autoScroll) {
            return super.onTouchEvent(event);
        } else {
            return false;
        }
    }

    public void setAutoScroll(boolean autoScrool) {
        this.autoScroll = autoScrool;
    }
}
