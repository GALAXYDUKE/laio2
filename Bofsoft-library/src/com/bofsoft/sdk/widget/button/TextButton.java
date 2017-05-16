package com.bofsoft.sdk.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import com.bofsoft.sdk.config.BaseSysConfig;

public class TextButton extends BaseButton implements OnTouchListener {

    private String text;

    private int textSize;

    private int textNormalColor;

    private int textDownColor;

    private int bgNormalColor;

    private int bgDownColor;

    private TextView textView;

    @SuppressLint("ClickableViewAccessibility")
    public TextButton(Context context, int id, String text) {
        super(context, id);
        this.text = text;
        this.textSize = BaseSysConfig.actionBarTexButtonSize;
        this.textNormalColor = BaseSysConfig.actionBarTexButtonNormalColor;
        this.textDownColor = BaseSysConfig.actionBarTexButtonDownColor;
        this.bgNormalColor = BaseSysConfig.actionBarButtonNormalColor;
        this.bgDownColor = BaseSysConfig.actionBarButtonDownColor;
        drawView();
        this.setOnTouchListener(this);
    }

    @SuppressLint("ClickableViewAccessibility")
    public TextButton(Context context, int id, String text, Integer textSize,
                      Integer textNormalColor, Integer textDownColor, Integer bgNormalColor, Integer bgDownColor) {
        super(context, id);
        this.text = text;
        this.textSize = textSize == null ? BaseSysConfig.actionBarTexButtonSize : textSize;
        this.textNormalColor =
                textNormalColor == null ? BaseSysConfig.actionBarTexButtonNormalColor : textNormalColor;
        this.textDownColor =
                textDownColor == null ? BaseSysConfig.actionBarTexButtonDownColor : textDownColor;
        this.bgNormalColor =
                bgNormalColor == null ? BaseSysConfig.actionBarButtonNormalColor : bgNormalColor;
        this.bgDownColor = bgDownColor == null ? BaseSysConfig.actionBarButtonDownColor : bgDownColor;
        drawView();
        this.setOnTouchListener(this);
    }


    public TextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void drawView() {
        if (textView == null) {
            addView(textView = new TextView(getContext()), new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            setGravity(Gravity.CENTER);
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize);
        textView.setTextColor(this.textNormalColor);
        textView.setText(text);
        this.setBackgroundColor(this.bgNormalColor);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isAutoAttr())
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(this.bgDownColor);
                textView.setTextColor(this.textDownColor);
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(this.bgNormalColor);
                textView.setTextColor(this.textNormalColor);
                break;
        }
        return false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        drawView();
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
        drawView();
    }

    public int getTextNormalColor() {
        return textNormalColor;
    }

    public void setTextNormalColor(int textNormalColor) {
        this.textNormalColor = textNormalColor;
        drawView();
    }

    public int getTextDownColor() {
        return textDownColor;
    }

    public void setTextDownColor(int textDownColor) {
        this.textDownColor = textDownColor;
    }

    public int getBgNormalColor() {
        return bgNormalColor;
    }

    public void setBgNormalColor(int bgNormalColor) {
        this.bgNormalColor = bgNormalColor;
        drawView();
    }

    public int getBgDownColor() {
        return bgDownColor;
    }

    public void setBgDownColor(int bgDownColor) {
        this.bgDownColor = bgDownColor;
        drawView();
    }

    @Override
    public BaseButton clone() {
        return new TextButton(context, getId(), text, textSize, textNormalColor, textDownColor,
                bgNormalColor, bgDownColor);
    }

}
