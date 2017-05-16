package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.R;


public class Widget_Button extends Button {
    Drawable inSrc;
    Drawable outSrc;

    public Widget_Button(Context context) {
        super(context);
        // 按钮变色
        this.setOnTouchListener(Func.onTouchListener);
    }

    public Widget_Button(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Widget_Button);
        inSrc = typedArray.getDrawable(R.styleable.Widget_Button_bInPic);
        outSrc = typedArray.getDrawable(R.styleable.Widget_Button_bOutPic);

        if (outSrc != null)
            this.setBackgroundDrawable(outSrc);
        if (inSrc == null || outSrc == null) {
            // 按钮变色
            this.setOnTouchListener(Func.onTouchListener);
        } else {
            this.setOnTouchListener(onTouchListener);
        }
    }

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                Widget_Button.this.setBackgroundDrawable(inSrc);
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                Widget_Button.this.setBackgroundDrawable(outSrc);
            }
            return false;
        }

    };

}
