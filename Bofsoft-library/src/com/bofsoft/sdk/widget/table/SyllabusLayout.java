package com.bofsoft.sdk.widget.table;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.sdk.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 课表控件
 *
 * @author zxq
 */
@SuppressLint("SimpleDateFormat")
public class SyllabusLayout extends LinearLayout implements OnClickListener {

    private int row = 4;
    private int col = 8;

    private TableLayout tabl;
    private TextView monthTv;
    private LinearLayout leftLl;
    private LinearLayout rightLl;

    private TextView[] daysTv = new TextView[7];
    private String[] weeks = new String[]{"日\n周日", "日\n周一", "日\n周二", "日\n周三", "日\n周四", "日\n周五",
            "日\n周六"};
    private Date toDay;
    private Date checkFristDay;
    private final long day = 1000 * 60 * 60 * 24;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");

    // 时间背景颜色
    private int timeSingleBC = Color.rgb(104, 141, 213);
    private int timeDoubleBC = Color.rgb(81, 117, 186);
    private int timeTodayBC = Color.rgb(255, 153, 56);

    // 表格样式背景颜色
    private int normalSingleBC = Color.rgb(255, 255, 255);
    private int normalDoubleBC = Color.rgb(251, 250, 248);

    // 表格业务背景颜色
    private int otherPlanBC = Color.rgb(224, 236, 213);
    private int multiPersionBC = Color.rgb(104, 141, 212);
    private int onePersionBC = Color.rgb(255, 127, 0);
    private int twoPersionBC = Color.rgb(150, 12, 220);

    // 字体颜色
    private int tcColor1 = Color.rgb(255, 255, 255);
    private int tcColor2 = Color.rgb(101, 160, 68);

    // 选中表格背景颜色
    private int selectedBD = R.drawable.table_col_selected;

    private SyllabusAdapter adapter;
    private onDateChangeListener listener;

    public SyllabusLayout(Context context) {
        super(context, null);
        init();
    }

    public SyllabusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @SuppressWarnings("deprecation")
    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.syllabus_layout, this, true);
        tabl = (TableLayout) findViewById(R.id.tab_ll);
        monthTv = (TextView) findViewById(R.id.month_tv);
        leftLl = (LinearLayout) findViewById(R.id.left_ll);
        rightLl = (LinearLayout) findViewById(R.id.right_ll);
        leftLl.setOnClickListener(this);
        rightLl.setOnClickListener(this);

        tabl.setRow(row);
        tabl.setCol(col);
        tabl.setRowHeight(0, -1);
        tabl.setColWidth(0, -1);

        setTimeFrame(1, 0, "上\n午");
        setTimeFrame(2, 0, "下\n午");
        setTimeFrame(3, 0, "晚");

        setNormalBackgroundColor();
        initDaysTextView();
        toDay = new Date();
        checkFristDay = getWeekFirstDay(toDay.getDay());
        setDaysData();
    }

    public void setAdapter(SyllabusAdapter adapter) {
        this.adapter = adapter;
        notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        if (adapter == null)
            return;
        View v;
        TableColLayout tcl;
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                tcl = tabl.getColView(i, j);
                if (tcl.getChildCount() <= 0) {
                    v = null;
                } else {
                    v = tcl.getChildAt(0);
                }
                View rv = adapter.getView(i, j, v);
                if (v == null && rv != null) {
                    RelativeLayout.LayoutParams lp =
                            new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT);
                    lp.addRule(RelativeLayout.CENTER_IN_PARENT);
                    tcl.addView(rv, lp);
                }
            }
        }
    }

    /**
     * 设置表格列间隔颜色
     */
    private void setNormalBackgroundColor() {
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                setColBackgroundColor(i, j, 1);
            }
        }
    }

    /**
     * 设置表格颜色
     *
     * @param row 行
     * @param col 列
     * @param biz 1 默认背景色，2 多人一车 3 一人一车 4 一人一车 5 选中
     */
    @SuppressLint("NewApi")
    public void setColBackgroundColor(int row, int col, int biz) {
        View v = tabl.getColView(row, col);
        RelativeLayout layout = ((RelativeLayout) v);
        TextView t = null;
        if (layout.getChildCount() > 0) {
            t = (TextView) layout.getChildAt(0);
            if (biz == -1)
                t.setTextColor(Color.BLACK);
            else
                t.setTextColor(tcColor1);
        }
        if (biz == -1) {
            v.setBackgroundColor(otherPlanBC);
        } else if (biz <= 1) {
            if (col % 2 == 0) {
                v.setBackgroundColor(normalDoubleBC);
            } else {
                v.setBackgroundColor(normalSingleBC);
            }
        } else if (biz <= 2) {
            v.setBackgroundColor(multiPersionBC);
        } else if (biz <= 3) {
            v.setBackgroundColor(onePersionBC);
        } else if (biz <= 4) {
            v.setBackgroundColor(twoPersionBC);
        } else if (biz <= 5) {
            if (t != null)
                t.setTextColor(tcColor1);
            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                v.setBackgroundDrawable(getResources().getDrawable(selectedBD));
            } else {
                v.setBackground(getResources().getDrawable(selectedBD));
            }
        }
    }

    /**
     * 设置上下晚时间
     *
     * @param row
     * @param col
     * @param text
     */
    private void setTimeFrame(int row, int col, String text) {
        TextView t = new TextView(getContext());
        t.setText(text);
        t.setTextColor(Color.rgb(200, 200, 200));
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        t.setGravity(Gravity.CENTER);
        tabl.getColView(row, col).addView(t);
    }

    /**
     * 设置日期显示
     */
    private void initDaysTextView() {
        RelativeLayout.LayoutParams lp =
                new android.widget.RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                        LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        for (int i = 0; i < daysTv.length; i++) {
            TextView t = new TextView(getContext());
            t.setTextColor(tcColor1);
            t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            t.setGravity(Gravity.CENTER);
            t.setLayoutParams(lp);
            tabl.getColView(0, i + 1).addView(t);
            daysTv[i] = t;
        }
    }

    private Date getWeekFirstDay(int toDay) {
        // 星期天
        if (toDay == 0) {
            return new Date(System.currentTimeMillis() - day * 6);
        } else {
            return new Date(System.currentTimeMillis() - day * (toDay - 1));
        }
    }

    @SuppressWarnings("deprecation")
    private void setDaysData() {
        Date d = null;
        monthTv.setText(df.format(checkFristDay));
        tabl.getColView(0, 0).setBackgroundColor(timeSingleBC);
        for (int i = 0; i < daysTv.length; i++) {
            d = new Date(checkFristDay.getTime() + i * day);
            daysTv[i].setText((d.getDate()) + weeks[d.getDay()]);
            // 设置背景颜色
            // 今天是一种颜色
            if (((d.getTime() / day) - (toDay.getTime() / day)) == 0) {
                tabl.getColView(0, i + 1).setBackgroundColor(timeTodayBC);
            } else {
                if (i % 2 == 0)
                    tabl.getColView(0, i + 1).setBackgroundColor(timeSingleBC);
                else
                    tabl.getColView(0, i + 1).setBackgroundColor(timeDoubleBC);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_ll) {
            checkFristDay = new Date(checkFristDay.getTime() - 7 * day);
            setDaysData();
            if (listener != null)
                listener.date(checkFristDay, new Date(checkFristDay.getTime() + day * 6));
        } else if (v.getId() == R.id.right_ll) {
            checkFristDay = new Date(checkFristDay.getTime() + 7 * day);
            setDaysData();
            if (listener != null)
                listener.date(checkFristDay, new Date(checkFristDay.getTime() + day * 6));
        }
    }

    public Date getToDay() {
        return toDay;
    }

    public interface SyllabusAdapter {
        View getView(int row, int col, View v);
    }

    public interface onDateChangeListener {
        void date(Date begin, Date end);
    }

    public void setOnDateChangeListener(onDateChangeListener listener) {
        this.listener = listener;
        refrish();
    }

    public void refrish() {
        if (listener != null)
            listener.date(checkFristDay, new Date(checkFristDay.getTime() + day * 6));
    }

    public void setOnTableClickListener(onTableClickListener listener) {
        tabl.setOnTableClickListener(listener);
    }
}
