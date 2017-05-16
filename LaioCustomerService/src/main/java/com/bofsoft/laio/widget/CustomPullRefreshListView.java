package com.bofsoft.laio.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.bofsoft.laio.widget.pulltorefresh.PullToRefreshListView;

public class CustomPullRefreshListView extends PullToRefreshListView {

    public CustomPullRefreshListView(Context context) {
        super(context);
    }

    public CustomPullRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomPullRefreshListView(Context context,
                                     Mode mode) {
        super(context, mode);
    }

    public CustomPullRefreshListView(Context context,
                                     Mode mode,
                                     AnimationStyle style) {
        super(context, mode, style);

    }

}
