package com.bofsoft.sdk.widget.table;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class TableColLayout extends RelativeLayout {

    private boolean autoHeight = true;
    private onDimensionBackListener dimenListener;

    private int w;

    public TableColLayout(Context context) {
        super(context, null);
    }

    public TableColLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        w = r - l;

        if (autoHeight)
            setMinimumHeight(w);
        else
            setMinimumHeight(0);

        if (changed) {
            if (w > 0 && dimenListener != null) {
                dimenListener.size(l, t, r, b);
            }
        }
    }

    public void setAutoHeight(boolean autoHeight) {
        this.autoHeight = autoHeight;
    }

    public interface onDimensionBackListener {
        void size(int l, int t, int r, int b);
    }

    public void setOnDimensionBackListener(onDimensionBackListener listener) {
        this.dimenListener = listener;
    }
}
