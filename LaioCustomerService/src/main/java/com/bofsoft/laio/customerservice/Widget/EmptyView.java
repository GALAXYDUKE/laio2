package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.R;

/**
 * Created by szw on 2016/11/23.
 */
public class EmptyView extends RelativeLayout {
    private TextView mTxtEmptyTip;

    public EmptyView(Context context) {
        super(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTxtEmptyTip = (TextView) findViewById(R.id.tv_nodata);
    }

    /**
     * 设置提示信息
     *
     * @param tip
     */
    public void setEmptyTip(String tip) {
        mTxtEmptyTip.setText(tip);
    }

    /**
     * 设置提示信息
     *
     * @param resId
     */
    public void setEmptyTip(int resId) {
        mTxtEmptyTip.setText(resId);
    }
}
