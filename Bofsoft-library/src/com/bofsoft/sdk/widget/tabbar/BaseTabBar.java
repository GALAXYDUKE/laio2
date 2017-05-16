package com.bofsoft.sdk.widget.tabbar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class BaseTabBar extends HorizontalScrollView implements OnClickListener,
        View.OnTouchListener {

    private LinearLayout content;

    private List<View> tabViews = new ArrayList<View>();

    public enum tabBarState {
        DOWN, UP, SELECTED, NORMAL
    }

    private StateListener listener;

    private int position = 0;

    private View proView;

    private boolean autoWidth = false;

    private long curTime = 0;

    public BaseTabBar(Context context) {
        super(context, null);
        init();
    }

    public BaseTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public List<View> getTabViews() {
        return tabViews;
    }

    private void init() {
        setHorizontalScrollBarEnabled(false);
        content = new LinearLayout(getContext());
        addView(content);
        setFillViewport(true);
    }

//  @Override
//  protected void onLayout(boolean changed, int l, int t, int r, int b) {
//    super.onLayout(changed, l, t, r, b);
//    if (changed) {
//      int width = r - l;
//      int len = tabViews.size();
//      if (len < 4 || autoWidth) {
//        for (int i = 0; i < len; i++) {
//          LinearLayout.LayoutParams lp =
//              (LinearLayout.LayoutParams) tabViews.get(i).getLayoutParams();
//          lp.width = width / len;
//          tabViews.get(i).setLayoutParams(lp);
//        }
//      }
//    }
//  }

    public void setGravity(int gravity) {
        content.setGravity(gravity);
    }

    public void setTabViews(List<View> tabViews) {
        this.tabViews = tabViews;
        int len = tabViews.size();
        if (len < 4 || autoWidth) {
            content.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        } else {
            content.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }
        for (int i = 0; i < tabViews.size(); i++) {
            View v = tabViews.get(i);
            if (len < 4 || autoWidth) {
                content.addView(v, new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1));
            } else {
                content.addView(v);
            }
            v.setId(i);
            v.setOnClickListener(this);
            v.setOnTouchListener(this);
        }
        setPosition(position);
    }

    public void setAutoWidth() {
        this.autoWidth = true;
    }

    public int getPosition() {
        return position;
    }

    /**
     * 设置选中
     */
    public void setPosition(int position) {
        this.position = position;
        if (position < 0 || position > tabViews.size() - 1) {
            return;
        }
        View v = tabViews.get(position);
//    if (v == proView)
//      return;
        if (listener != null) {
            listener.state(v, position, tabBarState.SELECTED);
            if (proView != null && v != proView) {//增加&&v != proView 屏蔽上面代码 为了满足登录提示取消时能再次响应
                listener.state(proView, position, tabBarState.NORMAL);
            }
        }
        proView = v;
    }

    public void setStateListener(StateListener listener) {
        this.listener = listener;
    }

    public interface StateListener {
        void state(View v, int position, tabBarState state);
    }

    public void refrish() {
        setPosition(position);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // if(v == proView) return false;
        // switch (event.getAction()) {
        // case MotionEvent.ACTION_DOWN:
        // if(listener != null) listener.state(v, v.getId(), tabBarState.DOWN);
        // break;
        // case MotionEvent.ACTION_UP:
        // if(listener != null) listener.state(v, v.getId(), tabBarState.UP);
        // break;
        // }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - curTime < 100) {
            curTime = System.currentTimeMillis();
            return;
        }
        curTime = System.currentTimeMillis();
//    if (v == proView)
//      return;
        setPosition(v.getId());
    }
}
