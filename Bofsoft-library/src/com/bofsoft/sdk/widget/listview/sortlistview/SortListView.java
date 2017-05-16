package com.bofsoft.sdk.widget.listview.sortlistview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.sdk.R;
import com.bofsoft.sdk.widget.listview.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.bofsoft.sdk.widget.listview.sortlistview.SortAdapter.ISortAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SortListView extends RelativeLayout {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private OnItemClickListener listener;
    private String noData = "暂无数据";
    private TextView msg;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    @SuppressWarnings("rawtypes")
    private List<SortModel> dataList = new ArrayList<SortModel>();

    private View v;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    @SuppressWarnings("rawtypes")
    private PinyinComparator pinyinComparator;

    @SuppressLint("InflateParams")
    public SortListView(Context context) {
        super(context, null);
        v = LayoutInflater.from(context).inflate(R.layout.sort_listview, null);
        addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initViews();
    }

    @SuppressLint("InflateParams")
    public SortListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        v = LayoutInflater.from(context).inflate(R.layout.sort_listview, null);
        addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        initViews();
    }

    @SuppressWarnings("rawtypes")
    private void initViews() {
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        sideBar = (SideBar) v.findViewById(R.id.sidrbar);
        dialog = (TextView) v.findViewById(R.id.dialog);
        sortListView = (ListView) v.findViewById(R.id.country_lvcountry);
        mClearEditText = (ClearEditText) v.findViewById(R.id.filter_edit);
        msg = (TextView) v.findViewById(R.id.msg);
        msg.setText(noData);
        sideBar.setTextView(dialog);
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });

        // 根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void setNoData(String msg) {
        this.noData = msg == null ? "" : msg;
        this.msg.setText(this.noData);
    }

    @SuppressWarnings("rawtypes")
    public SortModel getItem(int position) {
        return (SortModel) adapter.getItem(position);
    }

    public void setChecked(int position, boolean isChecked) {
        SortModel<?> sm = (SortModel<?>) adapter.getItem(position);
        sm.setChecked(isChecked);
        adapter.notifyDataSetChanged();
    }

    @SuppressWarnings("rawtypes")
    public List<SortModel> getData() {
        return this.adapter.getData();
    }

    // 设置数据源
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void setData(List dataList, SortKeyCallback callback) {

        List<SortModel> list = new ArrayList<SortModel>();
        for (Object d : dataList) {
            String en = callback.sortENKey(d);
            String cn = callback.sortCNKey(d);
            SortModel sm = new SortModel();
            sm.setCNKey(cn);
            sm.setENKey(en);
            sm.setData(d);
            list.add(sm);
        }
        this.dataList.clear();
        this.dataList.addAll(filledData(list));
        // 根据a-z进行排序源数据
        Collections.sort(this.dataList, pinyinComparator);
        adapter.notifyDataSetChanged();
        if (dataList.size() <= 0) {
            msg.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.INVISIBLE);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
        sortListView.setOnItemClickListener(this.listener);
    }

    @SuppressWarnings("rawtypes")
    public void setAdapter(ISortAdapter adapter) {
        this.adapter = new SortAdapter(getContext(), dataList, adapter);
        sortListView.setAdapter(this.adapter);
    }

    public interface SortKeyCallback<T> {
        String sortCNKey(T obj);

        String sortENKey(T obj);
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @param list
     * @return
     */
    @SuppressLint("DefaultLocale")
    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<SortModel> filledData(List<SortModel> list) {
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for (SortModel s : list) {
            SortModel sortModel = new SortModel();
            sortModel.setCNKey(s.getCNKey());
            sortModel.setENKey(s.getENKey());
            // 汉字转换成拼音
            String pinyin = "";
            if (sortModel.getCNKey() != null && sortModel.getCNKey().length() > 0) {
                pinyin = s.getCNKey();
            } else {
                pinyin = characterParser.getSelling(s.getENKey());
            }
            String sortString = "";
            if (pinyin != null && pinyin.length() > 0) {
                sortString = pinyin.substring(0, 1).toUpperCase();
            }

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setKey(sortString.toUpperCase());
            } else {
                sortModel.setKey("#");
            }
            sortModel.setData(s.getData());
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    @SuppressLint("DefaultLocale")
    @SuppressWarnings({"rawtypes", "unchecked"})
    private void filterData(String filterStr) {
        List<SortModel> filterDateList = new ArrayList<SortModel>();
        filterStr = filterStr.trim();
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = dataList;
        } else {
            filterDateList.clear();
            for (SortModel sortModel : dataList) {
                String en = sortModel.getENKey();
                String cn = sortModel.getCNKey();
                if (cn != null && cn.indexOf(filterStr) != -1) {
                    filterDateList.add(sortModel);
                } else if (en != null && (en.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(en).toUpperCase().startsWith(filterStr.toUpperCase().toString()))) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() <= 0) {
            msg.setVisibility(View.VISIBLE);
        } else {
            msg.setVisibility(View.INVISIBLE);
        }
    }
}
