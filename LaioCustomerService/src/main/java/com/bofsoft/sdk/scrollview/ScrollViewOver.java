package com.bofsoft.sdk.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

public class ScrollViewOver extends ScrollView {
    private OnScrollViewToListener listener;

    public ScrollViewOver(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewOver(Context context) {
        super(context);
    }

    private final static int STATE_START = 1;
    private final static int STATE_MIDDLE = 2;
    private final static int STATE_END = 3;
    private int XState = 0;
    private int YState = 0;
    private boolean autoHeight = false;
    private boolean autoWidth = false;

    /**
     * 当内容小于ScrollView高度时Scrollview与内容等高
     */
    public void autoHeight() {
        autoHeight = true;
    }

    /**
     * 当内容小于ScrollView宽度时Scrollview与内容等宽
     */
    public void autoWidth() {
        autoWidth = true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View v = getChildAt(0);
        if (v != null) {
            ViewGroup.LayoutParams lp = null;
            if (this.getParent() instanceof RelativeLayout)
                lp = getLayoutParams();
            else if (this.getParent() instanceof LinearLayout)
                lp = getLayoutParams();
            if (autoWidth)
                lp.width = v.getMeasuredWidth();
            if (autoHeight)
                lp.height = v.getMeasuredHeight();
            setLayoutParams(lp);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (listener == null)
            return;
        // 左右滑动scrollview
        if (clampedX || scrollX != 0 && !clampedX) {
            // x左边
            if (scrollX == 0) {
                if (XState != STATE_START || XState == 0) {
                    XState = STATE_START;
                    listener.isRight(true);
                    listener.isLeft(false);
                }
            }
            // x右边
            else if (scrollX != 0 && clampedX) {
                if (XState != STATE_END || XState == 0) {
                    XState = STATE_END;
                    listener.isRight(false);
                    listener.isLeft(true);
                }
            } else {
                if (XState != STATE_MIDDLE || XState == 0) {
                    XState = STATE_MIDDLE;
                    listener.isRight(false);
                    listener.isLeft(false);
                }
            }
        }
        // 上下滑动scrollview
        else {
            // y上边
            if (scrollY == 0) {
                if (YState != STATE_START || YState == 0) {
                    YState = STATE_START;
                    listener.isTop(true);
                    listener.isBottom(false);
                }
            }
            // y下边
            else if (scrollY != 0 && clampedY) {
                if (YState != STATE_END || YState == 0) {
                    YState = STATE_END;
                    listener.isTop(false);
                    listener.isBottom(true);
                }
            } else {
                if (YState != STATE_MIDDLE || YState == 0) {
                    YState = STATE_MIDDLE;
                    listener.isTop(false);
                    listener.isBottom(false);
                }
            }
        }
    }

    public void setOnScrollViewToListener(OnScrollViewToListener listener) {
        this.listener = listener;
    }

    public interface OnScrollViewToListener {
        void isTop(boolean isTop);

        void isBottom(boolean isBottom);

        void isLeft(boolean isLeft);

        void isRight(boolean isRight);
    }
}
