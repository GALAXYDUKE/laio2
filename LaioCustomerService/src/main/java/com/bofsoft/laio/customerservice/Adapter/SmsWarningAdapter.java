package com.bofsoft.laio.customerservice.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.LoadMoreRecyclerView;

import java.util.List;
import java.util.Map;

/**
 * Created by szw on 2017/3/8.
 */

public class SmsWarningAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final static int sum = 10;
    private List<Map<String, Object>> mValues;
    private boolean mIsStagger;
//    private IndexActivity indexActivity;

    public SmsWarningAdapter(List<Map<String, Object>> items) {
        mValues = items;
    }

    public void switchMode(boolean mIsStagger) {
        this.mIsStagger = mIsStagger;
    }

    public void setData(List<Map<String, Object>> datas) {
        mValues = datas;
    }

    public void addDatas(List<Map<String, Object>> datas, int time) {
        int start = time * sum;
        for (int i = start; i < datas.size(); i++) {
            mValues.add(i, datas.get(i));
        }
//        mValues.addAll(datas);
    }

    private SmsWarningAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(SmsWarningAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        indexActivity = (IndexActivity) parent.getContext();
        if (viewType == LoadMoreRecyclerView.TYPE_STAGGER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_staggel, parent, false);
            return new SmsWarningAdapter.StaggerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_smswarning_item, parent, false);
            view.setOnClickListener(this);
            return new SmsWarningAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mIsStagger) {
            SmsWarningAdapter.StaggerViewHolder staggerViewHolder = (SmsWarningAdapter.StaggerViewHolder) holder;
            staggerViewHolder.iconView.setVisibility(View.VISIBLE);
//            staggerViewHolder.mContentView.setText(mValues.get(position).License);
        } else {
            SmsWarningAdapter.ViewHolder mHolder = (SmsWarningAdapter.ViewHolder) holder;
            mHolder.mItem = mValues.get(position);
            mHolder.mView.setTag(mValues.get(position));
            mHolder.tv_warningLicense.setText((String) mValues.get(position).get("ShowName"));
            mHolder.tv_warningMsg.setText((String) mValues.get(position).get("Msg"));
            mHolder.tv_warningDate.setText((String) mValues.get(position).get("Time"));
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Map<String, Object>) v.getTag());
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class StaggerViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public View iconView;
        public TextView mContentView;

        public StaggerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            iconView = itemView.findViewById(R.id.icon);
            mContentView = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        public final TextView tv_warningLicense;
        public final TextView tv_warningMsg;
        public final TextView tv_warningDate;

        public Map<String, Object> mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            tv_warningLicense = (TextView) view.findViewById(R.id.tv_warningLicense);
            tv_warningMsg = (TextView) view.findViewById(R.id.tv_warningMsg);
            tv_warningDate = (TextView) view.findViewById(R.id.tv_warningDate);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_warningLicense.getText() + "'";
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, Map<String, Object> data);
    }
}