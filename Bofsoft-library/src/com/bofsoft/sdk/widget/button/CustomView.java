package com.bofsoft.sdk.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;

public class CustomView extends BaseButton {

    private View v;

    public CustomView(Context context, View v) {
        super(context, 0);
        this.v = v;
        drawView();
    }

    @SuppressLint("NewApi")
    private void drawView() {
        addView(v, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }

    public View getView() {
        return v;
    }

    @Override
    public BaseButton clone() {
        return null;
    }

}
