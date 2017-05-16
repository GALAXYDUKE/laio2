package com.bofsoft.sdk.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

public class FloatDialog {

    private WindowManager wm;

    public LayoutParams lp;

    private View v;

    public void show(Context context, View v) {
        this.v = v;
        wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);

        lp = new LayoutParams();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        lp.type =
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
                        | WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        lp.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL | LayoutParams.FLAG_NOT_FOCUSABLE;

        lp.gravity = Gravity.CENTER;
        wm.addView(v, lp);
    }

    public boolean isShown() {
        if (v != null) return v.isShown();
        return false;
    }

    public void close() {
        if (wm != null && v != null && v.isShown()) {
            wm.removeView(v);
        }
    }
}
