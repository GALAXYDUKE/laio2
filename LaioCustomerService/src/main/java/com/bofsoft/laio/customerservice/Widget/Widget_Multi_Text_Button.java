package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.R;

public class Widget_Multi_Text_Button extends RelativeLayout {
    Context context;
    CharSequence text_left = "";
    CharSequence text_right = "";
    int textColor = -1;
    int textColor_left = -1;
    int textColor_right = -1;
    float textSize = 14;
    float textSize_left = 14;
    float textSize_right = 14;
    boolean tagVisiable = false;
    boolean bottomLineVisiable = false;

    TextView txtLeft;
    TextView txtRight;
    ImageView imgTag;
    View bottomLine;

    public Widget_Multi_Text_Button(Context context) {
        super(context);
        // 按钮变色
        this.context = context;
        this.setOnTouchListener(Func.onTouchListener);

        init();
    }

    public Widget_Multi_Text_Button(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.Widget_Multi_Text_Button);
        text_left = typedArray.getString(R.styleable.Widget_Multi_Text_Button_textLeft);
        text_right = typedArray.getString(R.styleable.Widget_Multi_Text_Button_textRight);

        textColor = typedArray.getColor(R.styleable.Widget_Multi_Text_Button_textColorAll, textColor);
        textColor_left =
                typedArray.getColor(R.styleable.Widget_Multi_Text_Button_textColorLeft, textColor_left);
        textColor_right =
                typedArray.getColor(R.styleable.Widget_Multi_Text_Button_textColorRight, textColor_right);
        textSize = typedArray.getFloat(R.styleable.Widget_Multi_Text_Button_textSizeAll, textSize_left);
        textSize_left =
                typedArray.getFloat(R.styleable.Widget_Multi_Text_Button_textSizeLeft, textSize_left);
        textSize_right =
                typedArray.getFloat(R.styleable.Widget_Multi_Text_Button_textSizeRight, textSize_right);
        tagVisiable =
                typedArray.getBoolean(R.styleable.Widget_Multi_Text_Button_tagVisiable, tagVisiable);
        bottomLineVisiable =
                typedArray.getBoolean(R.styleable.Widget_Multi_Text_Button_bottomLineVisiable,
                        bottomLineVisiable);

        this.setOnTouchListener(Func.onTouchListener);
        init();

        typedArray.recycle();
    }

    private void init() {
        LayoutInflater.from(context).inflate(R.layout.widget_multi_text_button, this, true);
        txtLeft = (TextView) findViewById(R.id.multi_text_leftText);
        txtRight = (TextView) findViewById(R.id.multi_text_rightText);
        imgTag = (ImageView) findViewById(R.id.img_tag);
        bottomLine = findViewById(R.id.bottom_line);

        txtLeft.setText(text_left);
        txtRight.setText(text_right);
        txtLeft.setTextSize(textSize_left);
        txtRight.setTextSize(textSize_right);

        if (textColor != -1) {
            txtLeft.setTextColor(textColor);
            txtRight.setTextColor(textColor);
        }
        if (textColor_left != -1) {
            txtLeft.setTextColor(textColor_left);
        }
        if (textColor_right != -1) {
            txtRight.setTextColor(textColor_right);
        }
        if (tagVisiable) {
            imgTag.setVisibility(VISIBLE);
        } else {
            imgTag.setVisibility(GONE);
        }
        if (bottomLineVisiable) {
            bottomLine.setVisibility(VISIBLE);
        } else {
            bottomLine.setVisibility(GONE);
        }
    }

    public void setTextLeft(CharSequence text) {
        text_left = text;
        txtLeft.setText(text);
    }

    public void setTextRight(CharSequence text) {
        text_right = text;
        txtRight.setText(text);
    }

    public CharSequence getTextRight() {
        return txtRight.getText();
    }

    public void setTextColorLeft(int color) {
        textColor_left = color;
        txtLeft.setTextColor(color);
    }

    public void setTextColorRight(int color) {
        textColor_right = color;
        txtRight.setTextColor(color);
    }

    public void setTextColor(int color) {
        textColor_left = color;
        textColor_right = color;
        txtLeft.setTextColor(color);
        txtRight.setTextColor(color);
    }

    public void setTextSizeLeft(float size) {
        textSize_left = size;
        txtLeft.setTextSize(size);
    }

    public void setTextSizeRight(float size) {
        textSize_right = size;
        txtRight.setTextSize(size);
    }

    public void setTextSize(float size) {
        textSize_left = size;
        textSize_right = size;
        txtLeft.setTextSize(size);
        txtRight.setTextSize(size);
    }

    public void setTextRightVisiblity(int visibility) {
        txtRight.setVisibility(visibility);
    }

    public void setTagVisiblity(boolean visiable) {
        tagVisiable = visiable;
        if (visiable) {
            imgTag.setVisibility(VISIBLE);
        } else {
            imgTag.setVisibility(GONE);
        }
    }

}
