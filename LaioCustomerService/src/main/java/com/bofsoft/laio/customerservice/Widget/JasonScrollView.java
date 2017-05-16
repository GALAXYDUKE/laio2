package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.bofsoft.laio.common.MyLog;

import java.util.Date;

public class JasonScrollView extends ScrollView {
    // public View mInshowView;
    MyLog mylog = new MyLog(this.getClass());
    public final static int TO_TOP = 1;
    public final static int TO_BOOM = 2;
    public final static int TO_NOT_MOVE = 0;
    public final static int MOVE_ING = 0;
    public final static int NOT_MOVE_ING = 1;

    public final static int IN_TOP = 1;
    public final static int IN_BOOM = 2;
    public final static int IN_MOVE = 3;

    public int onState = IN_TOP;
    private int mOldState;
    // private int ScrollState = NOT_MOVE_ING;

    public View view = null;
    private long lasttime;
    private int moldx;
    private int moldy;

    public interface ITopBoomBack {
        int onTopBoomBack(int state, View view, int top);
    }

    public ITopBoomBack iTopBoomBack = null;

    public JasonScrollView(Context context) {
        super(context);
    }

    public JasonScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JasonScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        lasttime = 0;
        // android:focusable="true"
        // android:focusableInTouchMode="true"
    }

    public interface OnScrollChangedListener {
        void onScrollChanged(int x, int y, int oldxX, int oldY);
    }

    private OnScrollChangedListener onScrollChangedListener;

    /**
     * @param onScrollChangedListener
     */
    public void setOnScrollListener(OnScrollChangedListener onScrollChangedListener) {
        this.onScrollChangedListener = onScrollChangedListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        // mylog.e("==》 X :" + x + "|| Y ==》" + y + "|| oldX ==》" + oldX +
        // "|| oldY ==》"+ oldY);
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(x, y, oldX, oldY);
        }
        if ((view != null) && (iTopBoomBack != null) && (onState != TO_NOT_MOVE)) {
            int top = getViewTop(view);
            int h = getScrollParentH(view);
            int backState =
                    iTopBoomBack.onTopBoomBack(scrollChanged(x, y, oldX, oldY, view), view, (top - h));
            if (backState != -1) {
                onState = backState;
            }
        }
    }

    /**
     * 获取当前控件到JasonScrollView高度
     *
     * @param view
     * @return
     */
    private int getViewTop(View view) {
        int top = view.getTop();
        if (!(view.getParent() instanceof JasonScrollView)) {
            top += getViewTop((View) view.getParent());
        }
        return top;
    }

    @Override
    public void computeScroll() {// 此功能可以让scrollview不自动滚动
        // mylog.e("--->" + (new Date().getTime() - lasttime));
        if (new Date().getTime() - lasttime > 3000)
            super.computeScroll();// 此行注释掉
    }

    /**
     * @param view
     * @return
     */
    public static int getScrollParentH(View view) {
        int top = 0;
        // mylog.e("=======>move:" + view.getClass());
        if (view == null) {
            return top;
        }
        if (!(view instanceof JasonScrollView)) {
            return getScrollParentH((View) view.getParent());
        } else {
            top = view.getHeight();
        }
        return top;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // if (ev.getAction() == MotionEvent.ACTION_UP) {
        // if ((view != null) && (iTopBoomBack != null))
        // iTopBoomBack.onTopBoomBack(onState);
        // }
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            moldx = this.getScrollX();
            moldy = this.getScrollY();
            mOldState = onState;
        }
        if ((ev.getAction() == MotionEvent.ACTION_UP) && (iTopBoomBack != null)) {
            int top = getViewTop(view);
            int h = getScrollParentH(view);
            int backState =
                    iTopBoomBack.onTopBoomBack(scrollChangedBack(this.getScrollX(), this.getScrollY(), view),
                            view, (top - h));
            if (backState != -1) {
                onState = backState;
            }
        }
        return super.onTouchEvent(ev);
    }

    private int scrollChangedBack(int x, int y, View view) {
        int top = getViewTop(view);
        int h = getScrollParentH(view);
        // int inTop = top - h;
        // mylog.e("===>" + (top) + "|" + (y) + "|" + h + "|" + onState);
        if ((top - y > h / 3 * 2) && (top - y < h)) {
            if (onState == IN_TOP) {
                onState = IN_MOVE;
                lasttime = new Date().getTime();
                return TO_TOP;
            }
        }

        if ((top - y < h / 3) && (top - y > 0)) {
            if (onState == IN_BOOM) {
                onState = IN_MOVE;
                lasttime = new Date().getTime();
                return TO_BOOM;
            }
        }
        return TO_NOT_MOVE;
    }

    private int scrollChanged(int x, int y, int oldX, int oldY, View view) {
        int top = getViewTop(view);
        int h = getScrollParentH(view);
        // int inTop = top - h;
        // mylog.e("===>" + (top) + "|" + (y) + "|" + (h / 3) + "|" + onState);
        if (top - y <= h / 3 * 2) {
            if (onState == IN_TOP) {
                onState = IN_MOVE;
                lasttime = new Date().getTime();
                return TO_BOOM;
            }
        }

        if (top - y > h / 3) {
            if (onState == IN_BOOM) {
                onState = IN_MOVE;
                lasttime = new Date().getTime();
                return TO_TOP;
            }
        }
        return TO_NOT_MOVE;
    }
}
