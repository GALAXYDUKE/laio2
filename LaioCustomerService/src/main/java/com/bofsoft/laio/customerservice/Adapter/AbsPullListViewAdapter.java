package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.ResponseListener;
import com.bofsoft.laio.widget.pulltorefresh.PullToRefreshBase;
import com.bofsoft.laio.widget.pulltorefresh.PullToRefreshListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T> item的数据bean
 * @param <H> item的holder
 * @author yedong 2014.09.15
 */
public abstract class AbsPullListViewAdapter<T, H> extends BaseAdapter {
    MyLog mylog = new MyLog(this.getClass());
    public int OffsetCount = 1;
    public int PAGE_START_NUM = 1;
    public final int PAGE_MAX_NUM = 10;
    public final int LOCATION_LIST_HEADER = 0x01;
    public final int LOCATION_LIST_FOOTER = 0x02;
    public final int mAddDataLocation = LOCATION_LIST_FOOTER;
    public boolean isLoading = false; // 是否加载到底部，已经加载到底部则不提示
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected PullToRefreshListView mPullListView;
    protected ListView mListView;
    protected List<T> mList = new ArrayList<T>();
    protected TextView emptyTextView;
    protected JSONObject requst;
    public int Page = PAGE_START_NUM;
    public int PageNum = PAGE_MAX_NUM;
    public boolean isMore = true; // 是否有更多页
    public short mCommandId;

    public AbsPullListViewAdapter(Context context) {
        mContext = context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public AbsPullListViewAdapter(Context context, PullToRefreshListView listView) {
        this(context);
        initView(listView);
    }

    public Handler handler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 0:
                    if (isLoading) {
                        mPullListView.onRefreshComplete();
                    }
                    break;
                case 1:
                    if (isLoading) {
                        mPullListView.onRefreshComplete();
                        Toast.makeText(mContext, "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }

    };

    public void initView(PullToRefreshListView listView) {
        if (null == listView) {
            throw new NullPointerException("Listview is null");
        }
        emptyTextView = new TextView(mContext);
        mPullListView = listView;
        mListView = mPullListView.getRefreshableView();
        mPullListView.setPullToRefreshOverScrollEnabled(false);
        mPullListView.setMode(PullToRefreshBase.Mode.BOTH);

        onSetEmptyView();

        // mPullListView.setOnRefreshListener(new
        // PullToRefreshBase.OnRefreshListener<ListView>() {
        // @Override
        // public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        // String label = DateUtils.formatDateTime(mContext,
        // System.currentTimeMillis(),
        // DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE |
        // DateUtils.FORMAT_ABBREV_ALL);
        //
        // // Update the LastUpdatedLabel
        // refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
        //
        // // Do work to refresh the list here.
        // doRefresh();
        // }
        // });

        // mPullListView.setOnLastItemVisibleListener(new
        // PullToRefreshBase.OnLastItemVisibleListener() {
        // @Override
        // public void onLastItemVisible() {
        // if(isMore){
        // loadNextData();
        // }else {
        // if(!isLoadEnd){
        // isLoadEnd = true;
        // Toast.makeText(mContext, "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
        // }
        // }
        //
        // }
        // });

        /**
         * PullToRefreshListView 设置其事件监听
         */
        mPullListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label =
                        DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                // Update the LastUpdatedLabel
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // Do work to refresh the list here.
                doRefresh();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                String label =
                        DateUtils.formatDateTime(mContext, System.currentTimeMillis(),
                                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
                                        | DateUtils.FORMAT_ABBREV_ALL);
                refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                // Update the LastUpdatedLabel
                // Do work to refresh the list here.
                // mylog.e("-----onPullUpToRefresh-----isMore|"+isMore+">>>>isLoading|"+isLoading);

                isLoading = true;
                if (isMore) {
                    handler.sendEmptyMessageDelayed(0, 3000);
                    loadNextData();
                } else {
                    // mPullListView.onRefreshComplete();
                    handler.sendEmptyMessageDelayed(1, 1000);
                }
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onPullItemClick(parent, view, position - mListView.getHeaderViewsCount(), id);
            }
        });

        // mPullListView.setOnItemClickListener(new
        // AdapterView.OnItemClickListener() {
        //
        // @Override
        // public void onItemClick(AdapterView<?> parent, View view, int
        // position, long id) {
        // onPullItemClick(parent,view,position-OffsetCount,id);
        // }
        // });

        /**
         * Add Sound Event Listener
         */
        // SoundPullEventListener<ListView> soundListener = new
        // SoundPullEventListener<ListView>(this);
        // soundListener.addSoundEvent(State.PULL_TO_REFRESH, R.raw.pull_event);
        // soundListener.addSoundEvent(State.RESET, R.raw.reset_sound);
        // soundListener.addSoundEvent(State.REFRESHING,
        // R.raw.refreshing_sound);
        // mPullRefreshListView.setOnPullEventListener(soundListener);

        mPullListView.setAdapter(this);
    }

    /**
     * 没有数据时提示信息
     *
     * @param tip
     */
    protected void setEmptyView(String tip) {
        emptyTextView.setGravity(Gravity.CENTER);
        emptyTextView.setTextSize(14);
        emptyTextView.setVisibility(View.GONE);
        emptyTextView.setText(tip);
        mPullListView.setEmptyView(emptyTextView);
    }

    /**
     * 设置空列表是tips回调
     */
    public void onSetEmptyView() {
        setEmptyView(mContext.getString(R.string.no_data_tip));
    }

    /**
     * 刷新时回调任务,重载super方法
     */
    public void doRefresh() {
        Page = PAGE_START_NUM;
        if (mList != null) {
            mList.clear();
        }
        if (requst != null) {
            try {
                requst.put("Page", Page);
                loadData(requst, mCommandId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void loadData(JSONObject requst, short commandId) {
        if (requst != null) {
            this.requst = requst;
            this.mCommandId = commandId;
            Page = PAGE_START_NUM = requst.optInt("Page", PAGE_START_NUM);
            PageNum = requst.optInt("PageNum", PAGE_MAX_NUM);
            // Page = PAGE_START_NUM;
            // PageNum = PAGE_MAX_NUM;
            if (mList != null) {
                mList.clear();
            }
            try {
                requst.put("Page", Page);
                requst.put("PageNum", PageNum);
            } catch (JSONException e) {
                e.printStackTrace();
                return;
            }
            mPullListView.demo(); // 进入页面加载动画
            requstData();
        }
    }

    /**
     * 重写了一个loadData()的方法，加载数据
     */
    public void loadData(String request, short commandId) {
        this.mCommandId = commandId;
        try {
            JSONObject jsonObject = new JSONObject(request);
            loadData(jsonObject, commandId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // requstData();
    }

    /**
     * 加载下一组数据
     */
    public void loadNextData() {
        try {
            if (requst != null) {
                Page++;
                requst.put("Page", Page);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }
        requstData();
    }

    public void requstData() {

        DataLoadTask.getInstance().loadData(this.mCommandId, requst.toString(), new ResponseListener() {
            @Override
            public void messageBack(int code, int lenght, int tcplenght) {

            }

            @Override
            public void messageBack(int code, String result) {
                isLoading = false;
                doReslut(code, result);
                mPullListView.onRefreshComplete();
            }

            @Override
            public void messageBackFailed(int errorCode, String error) {
                isLoading = false;
                switch (errorCode) {
                    case ErrorCode.ErrorCode_System:
                    case ErrorCode.ErrorCode_DP_Connect_Failed:
                        if (!TextUtils.isEmpty(error)) {
                            ((BaseVehicleActivity) mContext).showPrompt(error);
                        }
                        break;
                    case ErrorCode.Error_Code_Parse_Exception:
                        mylog.e(error);
                        break;
                    default:
                        mylog.e(AbsPullListViewAdapter.this + ">>> errorCode= " + errorCode + ", error= "
                                + error);
                        break;
                }
                mPullListView.onRefreshComplete();
            }
        });
    }

    /**
     * 处理数据请求结果<br/>
     * 通过 {@code addListData(List<? extends T> list,boolean isMore)} 插入数据
     *
     * @param code
     * @param result
     */
    public abstract void doReslut(int code, String result);

    /**
     * 将请求结果添加到List
     *
     * @param mList  添加的数据
     * @param isMore 是否有更多页
     */
    public void addListData(List<? extends T> list, boolean isMore) {
        this.isMore = isMore;
        if (list == null) {
            this.isMore = false;
        } else {
            if (mList == null) {
                mList = new ArrayList<T>();
            }
            // this.isMore = isMore;
            switch (mAddDataLocation) {
                case LOCATION_LIST_FOOTER:
                    mList.addAll(list);
                    break;

                case LOCATION_LIST_HEADER:
                    mList.addAll(0, list);
                    break;
                default:
                    break;
            }
            this.notifyDataSetChanged();

            if (this.isMore) {
                // mylog.i("有数据---------加载数据--------->>>>");
            } else {
                if (Page > PAGE_START_NUM) {
//          Toast.makeText(mContext, "没有更多数据了哦!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    /**
     * 加载List item 布局
     *
     * @param contentView
     */
    public abstract View createItemView();

    /**
     * setting List item holder
     *
     * @param convertView
     * @param holder
     */
    public abstract H setItemHolder(View convertView);

    /**
     * setting item content and Event
     *
     * @param holder
     * @param data
     */
    public abstract void setItemViewContent(H holder, T data, int position);

    public void onPullItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (mList == null) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        H holder = null;
        if (convertView == null) {
            convertView = createItemView();
            holder = setItemHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (H) convertView.getTag();
        }
        setItemViewContent(holder, mList.get(position), position);
        return convertView;
    }

    public void setItem(int position, T t) {
        if (position >= 0 && position <= mList.size() - 1) {
            mList.set(position, t);
        }
    }

}
