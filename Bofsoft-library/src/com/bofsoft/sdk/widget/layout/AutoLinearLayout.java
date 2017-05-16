package com.bofsoft.sdk.widget.layout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint("ClickableViewAccessibility")
public class AutoLinearLayout extends LinearLayout implements OnClickListener, View.OnTouchListener {
    private int mWidth; // Layout控件的宽
    private int mHeight; // Layout控件的高
    private int r;
    private int l;
    private CheckState state = CheckState.RADIO;
    private OnChangeListener listener;
    private View checkedView;
    static final String TAG = "AutoLinearLayout";
    private int gap = 10;
    private boolean enable = true;
    private String toastMsg = null;
    private Map<Integer, Boolean> enableMap = new HashMap<Integer, Boolean>();
    private Map<Integer, String> enableToastMap = new HashMap<Integer, String>();

    // 固定列
    private int cellCnt = 0;
    // 每列宽度
    private int cWidth = 0;

    public void setGap(int gap) {
        this.gap = gap;
    }

    /**
     * 选择方式 单选，多选
     */
    public enum CheckState {
        RADIO, MULTI
    }

    /**
     * 未选中 选中 按下 抬起
     */
    public enum ChangeState {
        NORMAL, CHECKED, DOWN, UP
    }

    public AutoLinearLayout(Context context) {
        super(context);
    }

    public AutoLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        this.r = r;
        this.l = l;
        foo();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int cellWidthSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);// 让自控件自己按需撑开

        int cellHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);// 让自控件自己按需撑开

        int w = MeasureSpec.getSize(widthMeasureSpec);

        mWidth = w;

        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            try {
                childView.measure(cellWidthSpec, cellHeightSpec);
            } catch (NullPointerException e) {
                // 这个异常不影响展示
            }
        }
        foo();
        setMeasuredDimension(resolveSize(mWidth, widthMeasureSpec),
                resolveSize(mHeight, heightMeasureSpec));
    }

    public void setCellNum(int cnt) {
        this.cellCnt = cnt;
    }

    private void foo() {
        if (mWidth == 0) mWidth = r - l;// 宽度是跟随父容器而定的

        // 自身控件的padding
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();

        // 控件自身可以被用来显示自控件的宽度
        int mDisplayWidth = mWidth - paddingLeft - paddingRight;

        // 自控件的margin
        int cellMarginLeft = 0;
        int cellMarginRight = 0;
        int cellMarginTop = 0;
        int cellMarginBottom = 0;

        int x = 0;// 子控件的默认左上角坐标x
        int y = 0;// 子控件的默认左上角坐标y

        int totalWidth = 0;// 计算每一行随着自控件的增加而变化的宽度
        int count = getChildCount();

        int cellWidth = 0;// 子控件的宽，包含padding
        int cellHeight = 0;// 自控件的高，包含padding

        int left = 0;// 子控件左上角的横坐标
        int top = 0;// 子控件左上角的纵坐标

        LayoutParams lp;

        if (cWidth <= 0 && cellCnt > 0) {
            cWidth = (int) ((mDisplayWidth - gap * (cellCnt - 1)) / cellCnt - 0.5);
        }


        for (int j = 0; j < count; j++) {
            final View childView = getChildAt(j);
            childView.setOnClickListener(this);
            childView.setOnTouchListener(this);
            childView.setId(j);

            // 获取子控件child的宽高
            cellWidth = childView.getMeasuredWidth();// 测量自控件内容的宽度(这个时候padding有被计算进内)
            cellHeight = childView.getMeasuredHeight();// 测量自控件内容的高度(这个时候padding有被计算进内)

            lp = (LayoutParams) childView.getLayoutParams();
            cellMarginLeft = lp.leftMargin;
            cellMarginRight = lp.rightMargin;
            cellMarginTop = lp.topMargin;
            cellMarginBottom = lp.bottomMargin;

            if (cWidth > 0) {
                cellWidth = cWidth;
            }

            // 动态计算子控件在一行里面占据的宽度
            // 预判，先加上下一个要展示的子控件，计算这一行够不够放
            totalWidth += cellMarginLeft + cellWidth + cellMarginRight;

            if (totalWidth >= mDisplayWidth) {// 不够放的时候需要换行
                y += cellMarginTop + cellHeight + cellMarginBottom + gap;// 新增一行
                totalWidth = cellMarginLeft + cellWidth + cellMarginRight;// 这时候这一行的宽度为这个子控件的宽度
                x = 0;// 重置
            }

            // 计算顶点横坐标
            left = x + cellMarginLeft + paddingLeft;

            // 计算顶点纵坐标
            top = y + cellMarginTop + paddingTop;

            // 绘制自控件
            childView.layout(left, top, left + cellWidth, top + cellHeight);

            // 计算下一个横坐标
            x += cellMarginLeft + cellWidth + cellMarginRight;

            x += gap;
            totalWidth += gap;
        }
        mHeight = paddingTop + y + cellMarginTop + cellHeight + cellMarginBottom + paddingBottom;
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        child.setId(getChildCount() - 1);
    }

    public void setChecked(View v) {
        if (v == null)
            return;
        listener.change(v, v.getId(), ChangeState.CHECKED);
        v.setTag(1);
        if (state == CheckState.RADIO) {
            if (checkedView != null && v.getId() != checkedView.getId()) {
                listener.change(checkedView, checkedView.getId(), ChangeState.NORMAL);
                checkedView.setTag(0);
            }
            checkedView = v;
        } else {
            if (checkedView != null) {
                listener.change(checkedView, checkedView.getId(), ChangeState.NORMAL);
                checkedView.setTag(0);
                checkedView = null;
            }
        }
    }

    public void setChecked(int position) {
        int count = getChildCount();
        if (count < 0 || position >= count)
            return;
        setChecked(getChildAt(position));
    }

    public void cleanChecked(int position) {
        View v = getChildAt(position);
        listener.change(v, v.getId(), ChangeState.NORMAL);
        v.setTag(0);
    }

    public void cleanChecked() {
        checkedView = null;
        int cnt = getChildCount();
        for (int i = 0; i < cnt; i++) {
            cleanChecked(i);
        }
    }

    public void setEnable(boolean enable, String toastMsg) {
        this.enable = enable;
        this.toastMsg = toastMsg;
    }

    public void setEnable(int position, boolean enable, String toastString) {
        enableMap.put(position, enable);
        enableToastMap.put(position, toastString);
    }

    public void cleanEnable() {
        this.enable = true;
        this.toastMsg = null;
        this.enableMap.clear();
        this.enableToastMap.clear();
    }

    @Override
    public void onClick(View v) {
        if (!enable) {
            if (toastMsg != null) {
                Toast.makeText(getContext(), toastMsg, Toast.LENGTH_SHORT).show();
            }
            return;
        }
        checked(v);
    }

    private void checked(View v) {
        if (listener == null || state == null)
            return;
        int t = v.getTag() == null ? 0 : (Integer) v.getTag();
        int position = v.getId();
        Boolean enable = enableMap.get(position);
        String toastStr = enableToastMap.get(position);
        if (enable != null && !enable) {
            if (toastStr != null)
                Toast.makeText(getContext(), toastStr, Toast.LENGTH_SHORT).show();
            return;
        }

        if (state == CheckState.MULTI) {
            if (t == 0) {
                listener.change(v, position, ChangeState.CHECKED);
                v.setTag(1);
            } else {
                listener.change(v, position, ChangeState.NORMAL);
                v.setTag(0);
            }
            checkedView = v;
        } else {
            if (checkedView != v) {
                listener.change(v, position, ChangeState.CHECKED);
                v.setTag(1);
                if (checkedView != null) {
                    listener.change(checkedView, checkedView.getId(), ChangeState.NORMAL);
                    checkedView.setTag(0);
                }
                checkedView = v;
//      } else {
//        listener.change(v, position, ChangeState.NORMAL);
//        v.setTag(0);
//        checkedView = null;
            }
        }
    }

    /**
     * 获取被点种的索引
     *
     * @return
     */
    public List<Integer> getSelected() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v.getTag() != null && Integer.valueOf(v.getTag().toString()) == 1) {
                list.add(v.getId());
            }
        }
        return list;
    }

    /**
     * 获取未选中的
     *
     * @return
     */
    public List<Integer> getNormal() {
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v.getTag() == null && Integer.valueOf(v.getTag().toString()) == 0) {
                list.add(v.getId());
            }
        }
        return list;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (listener == null)
            return false;
        int id = v.getId();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                listener.change(v, id, ChangeState.DOWN);
                break;
            case MotionEvent.ACTION_UP:
                listener.change(v, id, ChangeState.UP);
                break;
        }
        return false;
    }

    public void setState(CheckState state) {
        this.state = state;
        foo();
    }

    public void setOnChangeListener(OnChangeListener listener) {
        this.listener = listener;
        foo();
    }

    public interface OnChangeListener {
        void change(View v, int position, ChangeState state);
    }
}
