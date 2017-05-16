package com.bofsoft.laio.laiovehiclegps.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;

/**
 * 主页测页布局，自定义的每行Item布局
 * Created by Bofsoft on 2017/5/16.
 */

public class MyDrawerLayoutItem extends LinearLayout {
    private TextView textView;
    private ImageView imageView;
    private LinearLayout linearLayout;

    public MyDrawerLayoutItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.widget_mydrawerlayout_item, this);
        init();
    }

    private void init() {
        findView();
    }

    private void findView() {
        textView = (TextView) findViewById(R.id.myDrawerLayoutItem_Function);
        imageView = (ImageView) findViewById(R.id.myDrawerLayoutItem_Image);
    }

    //自定义文字
    public void setTextView(String function) {
        textView.setText(function);
    }

    //自定义图片
    public void setImageView(int resId) {
        imageView.setImageResource(resId);
    }
}


