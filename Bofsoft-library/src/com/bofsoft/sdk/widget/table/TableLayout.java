package com.bofsoft.sdk.widget.table;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bofsoft.sdk.widget.table.TableColLayout.onDimensionBackListener;

import java.util.ArrayList;
import java.util.List;

public class TableLayout extends LinearLayout implements onDimensionBackListener {

    private int lineWidth = 1;
    private int lineColor;
    private int groupBg;
    private int row = 0;
    private int col = 0;
    private List<TableRowLayout> rowViews = new ArrayList<TableRowLayout>();

    private onTableClickListener listener;

    public TableLayout(Context context) {
        super(context, null);
        init();
    }

    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        this.setOrientation(LinearLayout.VERTICAL);
        setLineWidth(lineWidth);
        setLineColor(Color.rgb(229, 229, 231));
        setBackground(Color.rgb(255, 255, 255));
    }

    /**
     * 设置表格线宽度
     *
     * @param width
     */
    public void setLineWidth(int width) {
        this.lineWidth = width;
        this.setPadding(0, 0, lineWidth, lineWidth);
        for (TableRowLayout tr : rowViews) {
            tr.setLineWidth(lineWidth);
        }
    }

    /**
     * 设置表格线颜色
     *
     * @param color
     */
    public void setLineColor(int color) {
        this.lineColor = color;
        this.setBackgroundColor(lineColor);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setBackground(int color) {
        this.groupBg = color;
        for (TableRowLayout tr : rowViews) {
            tr.setBackground(groupBg);
        }
    }

    public void setRow(int row) {
        while (this.row < row) {
            addRow();
        }
    }

    public void addRow() {
        TableRowLayout tr = new TableRowLayout(getContext(), row);
        tr.setLineWidth(lineWidth);
        tr.setBackground(groupBg);
        tr.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        tr.setOnTableClickListener(listener);
        this.addView(tr);
        rowViews.add(tr);
        for (TableRowLayout layout : rowViews) {
            layout.setCol(this.col);
        }
        row++;
    }

    public void setCol(int col) {
        while (this.col < col) {
            addCol();
        }
    }

    public void addCol() {
        col++;
        for (TableRowLayout layout : rowViews) {
            layout.setCol(this.col);
        }
    }

    public int getRow() {
        return this.row;
    }

    public int getCol() {
        return this.col;
    }

    /**
     * 设置行高
     *
     * @param row
     * @param height -1:自适应高度 other绝对高度
     */
    public void setRowHeight(int row, int height) {
        if (row < 0 || row >= this.row) {
            Log.e("", "row out of " + this.row);
            return;
        }
        LayoutParams lp;
        for (TableColLayout tc : rowViews.get(row).getCols()) {
            lp = (LayoutParams) tc.getLayoutParams();
            if (height == -1) {
                tc.setAutoHeight(false);
                lp.height = LayoutParams.MATCH_PARENT;
            } else {
                lp.height = height;
            }
        }
    }

    /**
     * 设置列宽
     *
     * @param col
     * @param width -1:自适应宽度 other:绝对宽度
     */
    public void setColWidth(int col, int width) {
        if (col < 0 || col >= this.col) {
            Log.e("", "col out of " + this.col);
            return;
        }
        curCol = col;
        LayoutParams lp;
        for (TableRowLayout tr : rowViews) {
            TableColLayout tc = tr.getCols().get(col);
            lp = (LayoutParams) tc.getLayoutParams();
            lp.weight = 0;
            if (width == -1) {
                tc.setOnDimensionBackListener(this);
                lp.width = LayoutParams.WRAP_CONTENT;
            } else {
                tc.setOnDimensionBackListener(null);
                lp.width = width;
            }
        }
    }

    private int curCol = 0;
    private int mWidth = 0;

    @Override
    public void size(int l, int t, int r, int b) {
        if ((r - l) > mWidth) {
            mWidth = r - l;
            LayoutParams lp;
            for (TableRowLayout tr : rowViews) {
                TableColLayout tc = tr.getCols().get(curCol);
                lp = (LayoutParams) tc.getLayoutParams();
                lp.width = mWidth;
                for (int i = 0; i < tc.getChildCount(); i++) {
                    RelativeLayout.LayoutParams cLp =
                            (android.widget.RelativeLayout.LayoutParams) tc.getChildAt(i).getLayoutParams();
                    cLp.width = LayoutParams.MATCH_PARENT;
                    cLp.height = LayoutParams.WRAP_CONTENT;
                    cLp.addRule(RelativeLayout.CENTER_IN_PARENT);
                }
            }
        }
    }

    /**
     * 获取某一格
     *
     * @param row 行
     * @param col 列
     * @return
     */
    public TableColLayout getColView(int row, int col) {
        if (row < 0 || row >= this.row) {
            Log.e("", "row out of " + this.row);
            return null;
        } else if (col < 0 || col > this.col) {
            Log.e("", "col out of " + this.col);
            return null;
        }
        return rowViews.get(row).getCols().get(col);
    }

    /**
     * 获取某一行所有格
     *
     * @param row 行
     * @return
     */
    public List<TableColLayout> getRows(int row) {
        if (row < 0 || row >= this.row) {
            Log.e("", "row out of " + this.row);
            return null;
        }
        return rowViews.get(row).getCols();
    }

    /**
     * 获取某一列所有格
     *
     * @param col 列
     * @return
     */
    public List<TableColLayout> getCols(int col) {
        if (col < 0 || col >= this.col) {
            Log.e("", "col out of " + this.col);
            return null;
        }
        List<TableColLayout> cols = new ArrayList<TableColLayout>();
        for (TableRowLayout tr : rowViews) {
            cols.add(tr.getCols().get(col));
        }
        return cols;
    }

    public void setOnTableClickListener(onTableClickListener listener) {
        this.listener = listener;
        for (TableRowLayout tr : rowViews) {
            tr.setOnTableClickListener(listener);
        }
    }

}
