package com.bofsoft.laio.laiovehiclegps.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;


public class Widget_Input extends LinearLayout {
  public static String INPUT_TYPE_TEXT_PASSWORD = "textPassword";
  public static String INPUT_TYPE_TEXT_EMAILADDRESS = "textEmailAddress";
  public static String INPUT_TYPE_TEXT_PHONE = "textPhone";
  public static String INPUT_TYPE_TEXT_MULTILINE = "textMultiline";
  public static String INPUT_TYPE_TEXT_NUMBER = "number";

  TextView txtLable;
  ImageView imgIcon;
  EditText edtContent;
  Drawable inPic = null;
  Drawable outPic = null;
  Drawable backgound = null;
  String inputType = null;
  CharSequence text = null;
  int textColor = 0;
  int textSize = 0;
  int maxLength = 50;
  static OnClickListener outTbOnClickListener;

  public Widget_Input(Context context, AttributeSet attrs) {
    super(context, attrs);

    LayoutInflater.from(context).inflate(R.layout.widget_input, this, true);

    // 获取元件属性
    TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Widget_Input);
    CharSequence label = typedArray.getText(R.styleable.Widget_Input_lable);
    inPic = typedArray.getDrawable(R.styleable.Widget_Input_inPic);
    outPic = typedArray.getDrawable(R.styleable.Widget_Input_outPic);
    backgound = typedArray.getDrawable(R.styleable.Widget_Input_ibackground);
    inputType = typedArray.getString(R.styleable.Widget_Input_inputType);
    text = typedArray.getText(R.styleable.Widget_Input_itext);
    textColor = typedArray.getColor(R.styleable.Widget_Input_itextColor, Color.GRAY);
    textSize = typedArray.getInt(R.styleable.Widget_Input_itextSize, 14);
    maxLength = typedArray.getInt(R.styleable.Widget_Input_inputMaxLength, maxLength);

    typedArray.recycle();

    txtLable = (TextView) findViewById(R.id.widget_input_txtLabel);
    imgIcon = (ImageView) findViewById(R.id.widget_input_imgIcon);
    edtContent = (EditText) findViewById(R.id.widget_input_edtContent);

    edtContent.setHint(text);
    edtContent.setTextColor(textColor);
    edtContent.setTextSize(textSize);

    if (inputType != null) {
      if (inputType.equalsIgnoreCase("textPassword") == true) {
        edtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
      } else if (inputType.equalsIgnoreCase("textEmailAddress") == true) {
        edtContent.setInputType(InputType.TYPE_CLASS_TEXT
            | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
      } else if (inputType.equalsIgnoreCase("textPhone") == true) {
        edtContent.setInputType(InputType.TYPE_CLASS_PHONE);
      } else if (inputType.equalsIgnoreCase("textMultiline") == true) {
        edtContent.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
      } else if (inputType.equalsIgnoreCase("number") == true) {
        edtContent.setInputType(InputType.TYPE_CLASS_NUMBER);
      }
    } else {
      edtContent.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    if (label == null || label == "") {
      txtLable.setVisibility(View.GONE);
      // txtLable.setText("测试");
    } else {
      txtLable.setText(label.toString());
    }
    if (outPic == null || inPic == null) {
      imgIcon.setVisibility(View.GONE);
      LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      // lp.addRule(RIGHT_OF, R.id.widget_input_txtLabel);
      // lp.addRule(ALIGN_PARENT_RIGHT);
      lp.setMargins(10, 0, 10, 0);
      edtContent.setLayoutParams(lp);
    } else {
      imgIcon.setImageDrawable(outPic);
    }
    this.setBackgroundResource(R.drawable.bg_input_border_out);
    edtContent.setOnFocusChangeListener(onFocusChangeListener);
    if (backgound != null) {
      edtContent.setBackgroundDrawable(backgound);
    }
    // this.setOnClickListener(new OnClickListener() {
    //
    // @Override
    // public void onClick(View v) {
    // edtContent.requestFocus();
    // }
    // });

    edtContent.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View v) {
        edtContent.requestFocus();
        // outTbOnClickListener.onClick(Widget_Input.this);
      }
    });

    setMaxLenght(maxLength);
  }

  @Override
  public void setOnClickListener(OnClickListener call) {
    Widget_Input.outTbOnClickListener = call;
  }

  OnFocusChangeListener onFocusChangeListener = new OnFocusChangeListener() {

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
      if (hasFocus == true) {
        imgIcon.setImageDrawable(inPic);
        Widget_Input.this.setBackgroundResource(R.drawable.bg_input_border_in);
      } else {
        imgIcon.setImageDrawable(outPic);
        Widget_Input.this.setBackgroundResource(R.drawable.bg_input_border_out);
      }
    }
  };

  public Editable getText() {
    return edtContent.getText();
  }

  public void setText(CharSequence text) {
    edtContent.setText(text);
  }

  public void setTextSize(int size) {
    edtContent.setTextSize(size);
  }

  public void setTextColor(int color) {
    edtContent.setTextColor(color);
  }

  public void setEnabled(boolean enabled) {
    edtContent.setEnabled(enabled);
  }

  public void setBackgroundResource() {
    Widget_Input.this.setBackgroundResource(R.drawable.bg_input_border_out);
  }

  public void setImagIcon() {
    imgIcon.setImageDrawable(outPic);
  }

  public void setOnFocusChangeListener() {
    edtContent.setOnFocusChangeListener(null);
  }

  public void setHint(String str) {
    edtContent.setHint(str);
  }

  /**
   * 设置最大输入字符数
   * 
   * @param length
   */
  public void setMaxLenght(int length) {
    if (edtContent != null && length > 0) {
      maxLength = length;
      edtContent.setFilters(new InputFilter[] {new InputFilter.LengthFilter(length)});
    }
  }
}
