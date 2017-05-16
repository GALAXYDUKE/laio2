package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.R;


public class IndexTabBarView extends RelativeLayout {

    private Context context;

    private int resIdSelected;

    private int resIdNormal;

    private TextView titleTv;

    private int cnt = 0;

    private ImageView ico;

    private TextView cntTv;

    public IndexTabBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public IndexTabBarView(Context context, int resId) {
        super(context, null);
        this.context = context;
        init(resId);
    }

    public void setResource(int normalId, int selectedId, String title) {
        this.resIdNormal = normalId;
        this.resIdSelected = selectedId;
        this.titleTv.setText(title);
        setNormal();
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
        if (cnt <= 0) {
            cntTv.setVisibility(View.GONE);
        } else {
            cntTv.setVisibility(View.VISIBLE);
            if (cnt >= 1000) {
                cntTv.setText("  ");
            } else {
                cntTv.setText(cnt + "");
            }
        }
    }

    public void init(int resId) {
        LayoutInflater.from(context).inflate(resId, this, true);
        ico = (ImageView) findViewById(R.id.ico_iv);
        titleTv = (TextView) findViewById(R.id.title_tv);
        cntTv = (TextView) findViewById(R.id.cnt_tv);

        setCnt(0);
    }

    public void setSelected() {
        ico.setImageDrawable(getResources().getDrawable(resIdSelected));
        titleTv.setTextColor(getResources().getColor(R.color.textcolor_green));
    }

    public void setNormal() {
        ico.setImageDrawable(getResources().getDrawable(resIdNormal));
        titleTv.setTextColor(getResources().getColor(R.color.textcolor_gray));
    }
}
