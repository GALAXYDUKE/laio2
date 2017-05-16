package com.bofsoft.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquareLinearLayout extends LinearLayout {

    public SquareLinearLayout(Context context) {
        super(context, null);
        // TODO Auto-generated constructor stub
        setOrientation(LinearLayout.VERTICAL);
    }

    public SquareLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        setOrientation(LinearLayout.VERTICAL);
    }

    int mw = 0;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        mw = r - l;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mw != 0)
            setMeasuredDimension(resolveSize(mw, widthMeasureSpec), resolveSize(mw, heightMeasureSpec));
    }
}
