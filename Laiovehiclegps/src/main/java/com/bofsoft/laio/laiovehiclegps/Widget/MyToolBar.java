package com.bofsoft.laio.laiovehiclegps.Widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;

/**
 * 自定义工具栏
 * Created by Bofsoft on 2017/5/15.
 */

public class MyToolBar extends RelativeLayout implements View.OnClickListener {
    private ImageView leftImg, rightImg;//工具栏的左右图片
    private TextView txt_title, txt_line;//工具栏中间的主题,工具栏和下面布局的分隔线
    private MyToolBar myToolBar;//自定义工具栏

    public MyToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (context instanceof  MyToolBarClickListener) {
            myToolBarClickListener = (MyToolBarClickListener) context;
        }
        LayoutInflater.from(context).inflate(R.layout.widget_mytoolbar, this);
        init();
    }

    public void init() {
        findView();
    }

    private void findView() {
        leftImg = (ImageView) findViewById(R.id.toorBar_leftImageView);
        txt_title = (TextView) findViewById(R.id.toorBar_title);
        rightImg = (ImageView) findViewById(R.id.toorBar_rightImageView);
        txt_line = (TextView) findViewById(R.id.toorBar_line);

        rightImg.setOnClickListener(this);
        leftImg.setOnClickListener(this);
    }

    //设置左图片
    public void setLeftImg(int resId) {
        leftImg.setImageResource(resId);
    }

    //设置右图片
    public void setRightImg(int resId) {
        rightImg.setImageResource(resId);
    }

    //设置主题
    public void setTitle(String title) {
        txt_title.setText(title);
    }

    //设置是否显示分隔线,默认不显示
    public void setLine(boolean flag) {
        if (flag == true) {
            txt_line.setVisibility(View.VISIBLE);
        }
    }//设置是否显示左图片，默认显示

    public void setLeftImgVisibility(boolean flag) {
        if (flag == false) {
            txt_line.setVisibility(View.GONE);
        }
    }//设置是否显示右图片，默认显示

    public void setRightImgVisibility(boolean flag) {
        if (flag == false) {
            txt_line.setVisibility(View.GONE);
        }
    }

    public interface MyToolBarClickListener{
        void onLeftClick();
        void onRightClick();
}

    MyToolBarClickListener myToolBarClickListener = null;

    //工具栏左侧和右侧图片点击事件
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.toorBar_leftImageView){
            myToolBarClickListener.onLeftClick();
        }else if (v.getId()==R.id.toorBar_rightImageView){
            myToolBarClickListener.onRightClick();
        }
    }
}
