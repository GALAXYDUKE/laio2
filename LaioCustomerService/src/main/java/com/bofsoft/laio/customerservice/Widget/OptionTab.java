package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.R;


public class OptionTab extends LinearLayout {
    private MyLog myLog = new MyLog(getClass());

    private TextView[] titleTabBtn;

    private Context context;
    public int curPosition = 0;// 当前选择的 序号
    // private int height = 0;//
    private int width = 0;//
    private int offset = 0;
    private boolean isCanClick = true;
    String[] mTitles = {"button0", "button1", "button2"};
    private int TitleCount = 3; // 按钮个数
    private float lineHeight = 5; // 线条宽度
    private float textSize = 14;

    private int Bg_Select = getResources().getColor(R.color.bg_white);
    private int Bg_UnSelect = getResources().getColor(R.color.bg_white);
    private int Bg_Touch = getResources().getColor(R.color.bg_gray_light);

    private int Text_Sel_Color = getResources().getColor(R.color.text_orange_red);
    private int Text_UnSel_Color = getResources().getColor(R.color.text_black);

    private int Line_Color = getResources().getColor(R.color.bg_orange_red);

    private LinearLayout llayoutTitle;
    private ImageView lineView;

    private OptionTabChangeListener tabChangeListener;

    /**
     * 选中的title改变回调
     *
     * @author admin
     */
    public interface OptionTabChangeListener {
        void onOptionTabChangeListener(View view, int position);
    }

    public OptionTab(Context context) {
        super(context);
        this.context = context;
        setTitle(mTitles);
    }

    public OptionTab(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        TypedArray type = context.obtainStyledAttributes(attrs, R.styleable.OptionTab);
        textSize = type.getDimension(R.styleable.OptionTab_tabTextSize, textSize);
        Text_Sel_Color = type.getColor(R.styleable.OptionTab_tabTextColorSel, Text_Sel_Color);
        Text_UnSel_Color = type.getColor(R.styleable.OptionTab_tabTextColorUnSel, Text_UnSel_Color);
        lineHeight = type.getDimension(R.styleable.OptionTab_lineHeight, lineHeight);
        Line_Color = type.getColor(R.styleable.OptionTab_lineColor, Line_Color);
        Bg_Select = type.getColor(R.styleable.OptionTab_tabBtnBackgroudSel, Bg_Select);
        Bg_UnSelect = type.getColor(R.styleable.OptionTab_tabBtnBackgroudUnSel, Bg_UnSelect);
        Bg_Touch = type.getColor(R.styleable.OptionTab_tabBtnBackgroudUnTouch, Bg_Touch);
        type.recycle();
        setTitle(mTitles);
    }

    /**
     * 设置是否可点击
     *
     * @param isCanClick
     */
    public void setIsCanClick(boolean isCanClick) {
        this.isCanClick = isCanClick;
    }

    // @Override
    // protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    // if (!isGet) {
    // width = MeasureSpec.getSize(widthMeasureSpec);
    // height = MeasureSpec.getSize(heightMeasureSpec);
    // // System.out.println("OptionTab>>>height= " + height + ", width= "
    // // + width);
    // }
    // }

    // @Override
    // protected void onLayout(boolean changed, int l, int t, int r, int b) {
    // super.onLayout(changed, l, t, r, b);
    //
    // if (!isSetLineWidth) {
    // isSetLineWidth = true;
    // android.view.ViewGroup.LayoutParams params = lineView.getLayoutParams();
    // params.width = width / TitleCount;
    // lineView.setLayoutParams(params);
    // }
    // }

    public void setTitle(String[] titles) {
        this.mTitles = titles;
        if (titles != null) {
            TitleCount = titles.length;
            titleTabBtn = new TextView[TitleCount];
            this.setOrientation(LinearLayout.VERTICAL);
            this.setGravity(Gravity.CENTER_VERTICAL);

            initLayout();
        }
    }

    public void setOptionTabChangeListener(OptionTabChangeListener listener) {
        this.tabChangeListener = listener;
    }

    public void setSelection(int position) {

        setLineAnimation(this.curPosition, position);
        this.curPosition = position; // 更新位置
        // 设置按钮颜色
        for (int i = 0; i < TitleCount; i++) {
            if (i == position) {
                titleTabBtn[i].setTextColor(Text_Sel_Color);
                titleTabBtn[i].setBackgroundColor(Bg_Select);
            } else {
                titleTabBtn[i].setTextColor(Text_UnSel_Color);
                titleTabBtn[i].setBackgroundColor(Bg_UnSelect);
            }
        }

        if (tabChangeListener != null) {
            tabChangeListener.onOptionTabChangeListener(this, position);
        } else {
            myLog.e("please set the TitleTabChangeListener before use Widget_TitleTab!");
        }
    }

    public void setSelectionWithoutListener(int position) {

        setLineAnimation(this.curPosition, position);
        this.curPosition = position; // 更新位置
        // 设置按钮颜色
        for (int i = 0; i < TitleCount; i++) {
            if (i == position) {
                titleTabBtn[i].setTextColor(Text_Sel_Color);
                titleTabBtn[i].setBackgroundColor(Bg_Select);
            } else {
                titleTabBtn[i].setTextColor(Text_UnSel_Color);
                titleTabBtn[i].setBackgroundColor(Bg_UnSelect);
            }
        }
    }

    private void initLayout() {
        // 添加Titles
        if (llayoutTitle == null) {
            llayoutTitle = new LinearLayout(context);
            LayoutParams paramsTitle = new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);
            llayoutTitle.setOrientation(LinearLayout.HORIZONTAL);
            this.addView(llayoutTitle, 0, paramsTitle);
        } else {
            llayoutTitle.removeAllViews();
        }
        // 添加底部指示线条
        if (lineView == null) {
            lineView = new ImageView(context);
            lineView.setBackgroundColor(Line_Color);
            LayoutParams paramsLine = new LayoutParams(100, (int) lineHeight);
            this.addViewInLayout(lineView, 1, paramsLine);

            lineView.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    lineView.getViewTreeObserver().removeOnPreDrawListener(this);
                    width = OptionTab.this.getMeasuredWidth();
                    android.view.ViewGroup.LayoutParams params = lineView.getLayoutParams();
                    params.width = width / TitleCount;
                    lineView.setLayoutParams(params);
                    return true;
                }
            });
        }
        for (int i = 0; i < TitleCount; i++) {
            TextView text_btn = new TextView(context);
            text_btn.setText(mTitles[i]);
            text_btn.setTextSize(textSize);
            text_btn.setTextColor(Text_UnSel_Color);
            text_btn.setGravity(Gravity.CENTER);
            titleTabBtn[i] = text_btn;
            initTextLayout(text_btn, i);
        }
    }

    private void initTextLayout(TextView text_btn, int position) {
        // 动态布局
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.weight = 1.0f;
        // 设置宽度
        text_btn.setLayoutParams(lp);
        text_btn.setTag(position);

        if (position == curPosition) {
            text_btn.setTextColor(Text_Sel_Color);
            text_btn.setBackgroundColor(Bg_Select);
        } else {
            text_btn.setTextColor(Text_UnSel_Color);
            text_btn.setBackgroundColor(Bg_UnSelect);
        }

        // 按钮事件
        text_btn.setOnTouchListener(onTouchListener);
        text_btn.setOnClickListener(onClickListener);
        llayoutTitle.addView(text_btn);
    }

    /**
     * 线条的偏移量
     *
     * @param toPosition
     * @return
     */
    private void initLineOffset() {
        int lineWidth = lineView.getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        offset = (width / 3 - lineWidth) / 2;// 计算偏移量
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        lineView.setImageMatrix(matrix);// 设置动画初始位置
    }

    /**
     * 设置线条移动
     */
    private void setLineAnimation(int formPosition, int toPosition) {
        // int form = offset + width / TitleCount * formPosition;
        // int to = offset + width / TitleCount * toPosition;
        int form = offset + lineView.getWidth() * formPosition;
        int to = offset + lineView.getWidth() * toPosition;
        TranslateAnimation anim = new TranslateAnimation(form, to, 0, 0);
        anim.setFillAfter(true);
        anim.setDuration(300);
        lineView.startAnimation(anim);
    }

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            int index = (Integer) v.getTag();
            if (isCanClick && index != curPosition) {
                setSelection(index);
            }
        }
    };

    private OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                v.setBackgroundColor(Bg_Touch);
            } else if (event.getAction() == MotionEvent.ACTION_UP
                    || event.getAction() == MotionEvent.ACTION_CANCEL) {
                int index = (Integer) v.getTag();
                v.setBackgroundColor(Bg_UnSelect);
                if (index != curPosition) {
                    // 如果点击相同按钮不输入点击事件
                    performClick();
                }
            }
            return false;
        }
    };

    // private int BT_SELECTED = getResources().getColor(R.color.bg_gray_light);
    // private int BT_NOT_SELECTED = getResources().getColor(R.color.bg_white);
    // private int BT_TOUCH = getResources().getColor(R.color.bg_gray_light);

    private void setTextColor() {
        int count = llayoutTitle.getChildCount();
        for (int i = 0; i < count; i++) {
            if (i == curPosition) {
                ((TextView) llayoutTitle.getChildAt(i)).setTextSize(Text_Sel_Color);
            } else {
                ((TextView) llayoutTitle.getChildAt(i)).setTextSize(Text_UnSel_Color);
            }
        }
    }

    private void setTabBtnBgColor() {
        int count = llayoutTitle.getChildCount();
        for (int i = 0; i < count; i++) {
            if (i == curPosition) {
                llayoutTitle.getChildAt(i).setBackgroundColor(Bg_Select);
            } else {
                llayoutTitle.getChildAt(i).setBackgroundColor(Bg_UnSelect);
            }
        }
    }

    /**
     * 设置底部横线高度
     *
     * @param lineheight
     */
    public void setLineHeight(int lineheight) {
        if (lineView != null) {
            this.lineHeight = lineheight;
            android.view.ViewGroup.LayoutParams params = lineView.getLayoutParams();
            params.height = lineheight;
            lineView.setLayoutParams(params);
        }
    }

    /**
     * 设置底部线条颜色
     *
     * @param textColor
     */
    public void setLineColor(int textColor) {
        this.Line_Color = textColor;
        lineView.setBackgroundColor(Line_Color);
    }

    public void setTextSize(float size) {
        textSize = size;
        int count = llayoutTitle.getChildCount();
        for (int i = 0; i < count; i++) {
            ((TextView) llayoutTitle.getChildAt(i)).setTextSize(size);
        }
    }

    /**
     * 设置选择Tab文字颜色
     *
     * @param textColor
     */
    public void setTextColorSel(int textColor) {
        this.Text_Sel_Color = textColor;
        setTextColor();
    }

    /**
     * 设置未选择Tab文字颜色
     *
     * @param textColor
     */
    public void setTextColorUnSel(int textColor) {
        this.Text_UnSel_Color = textColor;
        setTextColor();
    }

    /**
     * 选中的Tab背景颜色
     *
     * @param textColor
     */
    public void setBtnBgColorSel(int textColor) {
        this.Bg_Select = textColor;
        setTabBtnBgColor();
    }

    /**
     * 未选中的Tab背景颜色
     *
     * @param textColor
     */
    public void setBtnBgColorUnSel(int textColor) {
        this.Bg_UnSelect = textColor;
        setTabBtnBgColor();
    }


}
