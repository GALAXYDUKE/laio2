package com.bofsoft.sdk;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bofsoft.laio.customerservice.R;

public class LvView extends LinearLayout {

    private int lv = 0;

    // 多少级一个级别
    private int mul = 5;

    private int[] resId = {R.mipmap.icon_heart2, R.mipmap.lv2, R.mipmap.lv3, R.mipmap.lv4};

    private int gap = 5;

    public LvView(Context context) {
        super(context, null);
    }

    public LvView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setResId(int[] resId) {
        this.resId = resId;
    }

    public void setLv(int lv) {
        removeAllViews();
        this.lv = lv;

        if (lv <= 0) {
//      ImageView iv = new ImageView(getContext());
//      iv.setImageResource(R.drawable.logo1);
//      addView(iv);
//      return;
        } else {
            int rLv = lv / mul;
            int rNum = lv % mul;
            if (rLv > resId.length - 1) {
                rLv = resId.length - 1;
                rNum = mul;
            }
            if (lv > 0 && rNum == 0) {
                rLv--;
                rNum = mul;
            }

            int rid = resId[rLv];
            for (int i = 0; i < rNum; i++) {
                ImageView iv = new ImageView(getContext());
                iv.setImageResource(rid);
                addView(iv, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
            }
            setGap(gap);
        }
    }

    public void setGap(int gap) {
        this.gap = gap;
        int count = getChildCount();
        for (int j = 1; j < count; j++) {
            final View cv = getChildAt(j);
            LinearLayout.LayoutParams lp = (LayoutParams) cv.getLayoutParams();
            lp.setMargins(gap, 0, 0, 0);
            cv.setLayoutParams(lp);
        }
    }
}
