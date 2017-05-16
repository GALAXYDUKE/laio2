package com.bofsoft.laio.laiovehiclegps.Widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;


public class Widget_CountView extends TextView {

  public Widget_CountView(Context context, CharSequence text) {
    super(context);
    setText(text);
    SetCountText();
  }

  public Widget_CountView(Context context, AttributeSet attrs) {
    super(context, attrs);
    SetCountText();
  }

  public void SetCountText(String text) {
    setText(text);
    SetCountText();
  }

  public void SetCountText() {
    String text = getText().toString();
    if (text != "" && text != null && Integer.parseInt(text) != 0) {
      setGravity(Gravity.CENTER);
      setTextColor(Color.WHITE);
      setTextSize(11);
      getPaint().setFakeBoldText(true);
      setBackgroundResource(R.mipmap.news_background);
      setVisibility(View.VISIBLE);
    } else {
      setText("");
      setVisibility(View.GONE);
    }
  }
}
