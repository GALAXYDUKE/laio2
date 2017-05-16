package com.bofsoft.sdk.widget.tabbar;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.bofsoft.sdk.R;
import com.bofsoft.sdk.widget.tabbar.BaseTabBar.StateListener;

import java.util.ArrayList;
import java.util.List;

public class SimpleTabBar extends BaseTabBar implements StateListener {

    private SelectedListener listener;

    private int titleColerNormal = getResources().getColor(R.color.tabbar_normal_color);

    private int titleColerDown = getResources().getColor(R.color.tabbar_down_color);

    private Activity act;

    private String[] titles = {};

    private View[] contents = {};

    private View proV;

    public SimpleTabBar(Context context) {
        super(context, null);
        this.act = (Activity) context;
        setStateListener(this);
    }

    public SimpleTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.act = (Activity) context;
        setStateListener(this);
    }

    public String[] geTitles() {
        return titles;
    }

    /**
     * @param titles
     */
    public void setTitles(String[] titles) {
        this.titles = titles;
        List<View> list = new ArrayList<View>();
        int padding = getResources().getDimensionPixelOffset(R.dimen.DP_10);
        for (String s : titles) {
            View v = act.getLayoutInflater().inflate(R.layout.tabbar_simple_item, null);
            TextView t = (TextView) v.findViewById(R.id.tab_title);
            t.setPadding(padding, padding, padding, padding);
            t.setTextColor(titleColerNormal);
            t.setSingleLine(true);
            t.setText(s);
            list.add(v);
        }
        setTabViews(list);
    }

    @Override
    public void state(View v, int position, tabBarState state) {
        if (v == null)
            return;
        TextView t = (TextView) v.findViewById(R.id.tab_title);
        switch (state) {
            case DOWN:
                t.setTextColor(titleColerDown);
                break;
            case UP:
                t.setTextColor(titleColerNormal);
                break;
            case SELECTED:
                t.setTextColor(titleColerDown);
                if (listener != null)
                    listener.selected(v, position);
                if (contents.length > 0) {
                    View cv = contents[position];
                    cv.setVisibility(View.VISIBLE);
                    if (proV != null)
                        proV.setVisibility(View.GONE);
                    proV = cv;
                }
                break;
            case NORMAL:
                t.setTextColor(titleColerNormal);
                break;
        }
    }

    public void setSelectedListener(SelectedListener listener) {
        this.listener = listener;
    }

    public interface SelectedListener {
        void selected(View v, int position);
    }

    public int getTitleColerNormal() {
        return titleColerNormal;
    }

    public void setTitleColerNormal(int titleColerNormal) {
        this.titleColerNormal = titleColerNormal;
    }

    public int getTitleColerDown() {
        return titleColerDown;
    }

    public void setTitleColerDown(int titleColerDown) {
        this.titleColerDown = titleColerDown;
    }

    public View[] getContents() {
        return contents;
    }

    public void setContents(View[] contents) {
        this.contents = contents;
        for (View v : contents) {
            v.setVisibility(View.GONE);
        }
        refrish();
    }
}
