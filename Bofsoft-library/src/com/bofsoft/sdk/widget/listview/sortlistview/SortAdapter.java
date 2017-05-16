package com.bofsoft.sdk.widget.listview.sortlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;

import java.util.List;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    @SuppressWarnings("rawtypes")
    private List<SortModel> list = null;
    private ISortAdapter<?> isa;

    @SuppressWarnings("rawtypes")
    public SortAdapter(Context mContext, List<SortModel> dataList, ISortAdapter isa) {
        this.list = dataList;
        this.isa = isa;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    @SuppressWarnings("rawtypes")
    public void updateListView(List<SortModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @SuppressWarnings("rawtypes")
    public List<SortModel> getData() {
        return list;
    }

    public int getCount() {
        return this.list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @SuppressWarnings("unchecked")
    public View getView(final int position, View view, ViewGroup arg2) {
        return isa.getView(position, view, arg2, isFirstShow(position), list.get(position));
    }

    public interface ISortAdapter<T> {
        View getView(final int position, View view, ViewGroup arg2, boolean isFirst, SortModel<T> data);
    }

    public boolean isFirstShow(int position) {
        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        return position == getPositionForSection(section);
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getKey().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @SuppressLint("DefaultLocale")
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getKey();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    @SuppressLint("DefaultLocale")
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
