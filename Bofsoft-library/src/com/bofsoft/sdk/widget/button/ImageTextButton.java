package com.bofsoft.sdk.widget.button;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.sdk.R;
import com.bofsoft.sdk.config.BaseSysConfig;

@SuppressLint({"RtlHardcoded", "InflateParams"})
public class ImageTextButton extends BaseButton implements OnTouchListener {

    private String text;

    private int textSize;

    private Integer icoResId;

    private int textNormalColor;

    private int textDownColor;

    private int bgNormalColor;

    private int bgDownColor;

    private TextView textView;

    private ImageView icoIv;

    private TextView cntTv;

    private LinearLayout content;

    private int gap = 0;

    private int gravity = 0;

    private ImageAlign imageAlign = ImageAlign.LEFT;

    public enum ImageAlign {
        LEFT, RIGHT
    }

    public String getText() {
        return textView.getText().toString();
    }

    @SuppressLint("ClickableViewAccessibility")
    public ImageTextButton(Context context, int id, String text, Integer imageResId) {
        super(context, id);
        this.text = text;
        this.icoResId = imageResId;
        this.textSize = BaseSysConfig.actionBarTexButtonSize;
        this.textNormalColor = BaseSysConfig.actionBarTexButtonNormalColor;
        this.textDownColor = BaseSysConfig.actionBarTexButtonDownColor;
        this.bgNormalColor = BaseSysConfig.actionBarButtonNormalColor;
        this.bgDownColor = BaseSysConfig.actionBarButtonDownColor;
        this.setOnTouchListener(this);
        init();
    }

    @SuppressLint("ClickableViewAccessibility")
    public ImageTextButton(Context context, int id, String text, Integer imageResId,
                           Integer textSize, Integer textNormalColor, Integer textDownColor, Integer bgNormalColor,
                           Integer bgDownColor) {
        super(context, id);
        this.text = text;
        this.icoResId = imageResId;
        this.textSize = textSize == null ? BaseSysConfig.actionBarTexButtonSize : textSize;
        this.textNormalColor =
                textNormalColor == null ? BaseSysConfig.actionBarTexButtonNormalColor : textNormalColor;
        this.textDownColor =
                textDownColor == null ? BaseSysConfig.actionBarTexButtonDownColor : textDownColor;
        this.bgNormalColor =
                bgNormalColor == null ? BaseSysConfig.actionBarButtonNormalColor : bgNormalColor;
        this.bgDownColor = bgDownColor == null ? BaseSysConfig.actionBarButtonDownColor : bgDownColor;
        this.setOnTouchListener(this);
        init();
    }

    public ImageTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        if (content == null) {
            content = new LinearLayout(getContext());
            LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(content, lp);
        }
        this.setBackgroundColor(this.bgNormalColor);
        setImage(this.icoResId);
        setTitle(this.text);
        gap = getResources().getDimensionPixelOffset(R.dimen.DP_5);
        setGap(gap);
        this.setContentGravity(Gravity.CENTER_VERTICAL);
    }

    public void setTitle(String title) {
        if (content == null || title == null)
            return;
        if (textView == null) {
            textView = new TextView(getContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.textSize);
            textView.setTextColor(this.textNormalColor);
            textView.setSingleLine(true);
            textView.setMaxEms(8);
            content.addView(textView, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        textView.setText(title);
    }

    public void setImage(Integer resId) {
        if (content == null || resId == null)
            return;
        if (icoIv == null) {
            icoIv = new ImageView(getContext());
            content.addView(icoIv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }
        icoIv.setImageResource(resId);
    }

    public void setCnt(int cnt) {
        if (cntTv == null) {
            View v = LayoutInflater.from(getContext()).inflate(R.layout.msg_cnt_layout, null);
            cntTv = (TextView) v.findViewById(R.id.cnt_tv);
            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
            addView(v, lp);
        }
        if (cnt <= 0) {
            cntTv.setVisibility(View.GONE);
        } else {
            cntTv.setVisibility(View.VISIBLE);
            cntTv.setText(cnt + "");
        }
    }

    public void setContentGravity(int gravity) {
        if (content == null)
            return;
        this.gravity = gravity;
        content.setGravity(gravity);
    }

    public void setImageAlign(ImageAlign align) {
        if (textView == null || icoIv == null || content.getChildCount() < 2)
            return;
        this.imageAlign = align;
        if (align == ImageAlign.RIGHT) {
            ((ViewGroup) icoIv.getParent()).removeView(icoIv);
            content.addView(icoIv);
        } else {
            if (content.getChildAt(0) != icoIv) {
                ((ViewGroup) icoIv.getParent()).removeView(icoIv);
                content.addView(icoIv, 0);
            }
        }
        setGap(gap);
    }

    public void setGap(int gap) {
        if (textView == null || icoIv == null || content.getChildCount() < 2)
            return;
        this.gap = gap;
        for (int i = 0; i < content.getChildCount(); i++) {
            View v = content.getChildAt(i);
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) v.getLayoutParams();
            if (i == 0) {
                lp.setMargins(0, 0, 0, 0);
            } else {
                lp.setMargins(gap, 0, 0, 0);
            }
            v.setLayoutParams(lp);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (!isAutoAttr())
            return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.setBackgroundColor(this.bgDownColor);
                if (textView != null)
                    textView.setTextColor(this.textDownColor);
                break;
            case MotionEvent.ACTION_UP:
                this.setBackgroundColor(this.bgNormalColor);
                if (textView != null)
                    textView.setTextColor(this.textNormalColor);
                break;
        }
        return false;
    }

    @Override
    public ImageTextButton clone() {
        ImageTextButton but =
                new ImageTextButton(context, getId(), text, icoResId, textSize, textNormalColor,
                        textDownColor, bgNormalColor, bgDownColor);
        but.setGap(gap);
        but.setContentGravity(gravity);
        but.setImageAlign(imageAlign);
        return but;
    }
}
