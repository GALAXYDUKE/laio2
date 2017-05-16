package com.bofsoft.sdk.widget.listview.pulllistview;

import android.content.Context;
import android.util.AttributeSet;

public class PageListView extends ZrcListView {

    private onCallBackListener listener;
    private boolean hasTop = true;
    private boolean hasBottom = true;

    public PageListView(Context context, boolean hasTop, boolean hasBottem) {
        super(context);
        this.hasTop = hasTop;
        this.hasBottom = hasBottem;
    }

    public PageListView(Context context) {
        super(context, null);
        init();
    }

    public PageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public boolean isRefreshing(){
        return  isRefreshing;
    }

    public boolean isLoadingMore(){
        return isLoadingMore;
    }

    private void init() {
        if (hasTop) {
            // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
            SimpleHeader header = new SimpleHeader(getContext());
            header.setTextColor(0xff0066aa);
            header.setCircleColor(0xff33bbee);
            setHeadable(header);
        }
        if (hasBottom) {
            // 设置加载更多的样式（可选）
            SimpleFooter footer = new SimpleFooter(getContext());
            footer.setCircleColor(0xff33bbee);
            setFootable(footer);
        }

        // 下拉刷新事件回调（可选）
        setOnRefreshStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                if (listener != null)
                    listener.back(RlState.REFRESH);
            }
        });

        // 加载更多事件回调（可选）
        setOnLoadMoreStartListener(new OnStartListener() {
            @Override
            public void onStart() {
                if (listener != null)
                    listener.back(RlState.MORE);
            }
        });
        if (hasBottom) startLoadMore();            //开启加载更多
    }

    public void success() {
        success("刷新成功");
    }

    public void success(String text) {

        // 刷新
        if (isRefreshing) {
            setRefreshSuccess(text);    //刷新完成
            if (hasBottom) startLoadMore();            //开启加载更多
        }
        // 加载更多
        if (isLoadingMore) {
            setLoadMoreSuccess();        //加载更多完成
        }
    }

    public void failed() {
        failed("加载失败");
    }

    public void failed(String text) {
        // 刷新
        if (isRefreshing) {
            setRefreshFail(text);        //刷新失败
        }
        // 加载更多
        if (isLoadingMore) {
            stopLoadMore();                //加载更多失败
        }
    }

    public enum RlState {
        REFRESH, MORE
    }

    public void setOnCallBackListener(onCallBackListener listener) {
        this.listener = listener;
    }

    public interface onCallBackListener {
        void back(RlState s);
    }
}
