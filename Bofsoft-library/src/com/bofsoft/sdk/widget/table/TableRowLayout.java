package com.bofsoft.sdk.widget.table;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class TableRowLayout extends LinearLayout implements OnClickListener {

    private int rowInex = 0;

    private int col = 0;

    private int lineWidth = 0;

    private int bgColor = 0;

    private onTableClickListener listener;

    private List<TableColLayout> cols = new ArrayList<TableColLayout>();

    public TableRowLayout(Context context, int rowIndex) {
        super(context, null);
        this.rowInex = rowIndex;
        setOrientation(HORIZONTAL);
    }

    public TableRowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
    }

    /**
     * 设置表格线宽度
     *
     * @param width
     */
    public void setLineWidth(int width) {
        this.lineWidth = width;
        for (TableColLayout tc : cols) {
            setColLineWidth(tc);
        }
    }

    /**
     * 设置表格背景颜色
     *
     * @param color
     */
    public void setBackground(int color) {
        this.bgColor = color;
        for (TableColLayout tc : cols) {
            setColBackground(tc);
        }
    }

    /**
     * 设置表格线宽度
     *
     * @param layout
     */
    private void setColLineWidth(TableColLayout layout) {
        LayoutParams lp = (LayoutParams) layout.getLayoutParams();
        lp.setMargins(lineWidth, lineWidth, 0, 0);
    }

    /**
     * 设置表格背景颜色
     *
     * @param layout
     */
    private void setColBackground(TableColLayout layout) {
        layout.setBackgroundColor(bgColor);
    }

    public void setCol(int i) {
        while (col < i) {
            addCol();
        }
    }

    public void addCol() {
        TableColLayout layout = new TableColLayout(getContext());
        layout.setId(rowInex * 10000 + col);
        layout.setLayoutParams(new LayoutParams(0, LayoutParams.MATCH_PARENT, 1));
        layout.setOnClickListener(this);
        setColLineWidth(layout);
        setColBackground(layout);
        this.addView(layout);
        cols.add(layout);
        col++;
    }

    public void setOnTableClickListener(onTableClickListener listener) {
        this.listener = listener;
    }

    public List<TableColLayout> getCols() {
        return cols;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.click(v.getId() / 10000, v.getId() % 10000, v);
        }
    }
}
