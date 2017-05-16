package com.bofsoft.laio.laiovehiclegps.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.Activity.IndexActivity;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoData;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Widget.LoadMoreRecyclerView;

import java.util.List;

/**
 * Created by szw on 2017/2/20.
 */

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {

    private final static int sum = 10;
    private List<GPSInfoData> mValues;
    private boolean mIsStagger;
    private IndexActivity indexActivity;

    public MyItemRecyclerViewAdapter(List<GPSInfoData> items) {
        mValues = items;
    }

    public void switchMode(boolean mIsStagger) {
        this.mIsStagger = mIsStagger;
    }

    public void setData(List<GPSInfoData> datas) {
        mValues = datas;
    }

    public void addDatas(List<GPSInfoData> datas, int page) {
        int start = page * sum;
        for (int i = start; i < datas.size(); i++) {
            mValues.add(i, datas.get(i));
        }
//        mValues.addAll(datas);
    }

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        indexActivity = (IndexActivity) parent.getContext();
        if (viewType == LoadMoreRecyclerView.TYPE_STAGGER) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item_staggel, parent, false);
            return new StaggerViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.fragment_item, parent, false);
            view.setOnClickListener(this);
            return new ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mIsStagger) {
            StaggerViewHolder staggerViewHolder = (StaggerViewHolder) holder;
            staggerViewHolder.iconView.setVisibility(View.VISIBLE);
            staggerViewHolder.mContentView.setText(mValues.get(position).License);
        } else {
            ViewHolder mHolder = (ViewHolder) holder;
            mHolder.mItem = mValues.get(position);
//            mHolder.mView.setTag(mValues.get(position));
            mHolder.mView.setTag(mValues.get(position).Deviceid);
            mHolder.tv_item_carlicense.setText(mValues.get(position).License);
            Log.e("tag", "mValues.get(position).License:" + mValues.get(position).License + " :" + position);
            if (mValues.get(position).Status == 0) {
                mHolder.iv_item_state.setImageResource(R.mipmap.marker_status1);
                mHolder.tv_status.setText("正常");
            } else {
                mHolder.iv_item_state.setImageResource(R.mipmap.marker_status3);
                mHolder.tv_status.setText(mValues.get(position).StatusContent + "");
            }
            mHolder.tv_equipmentTime.setText(mValues.get(position).Datetime);
            mHolder.tv_speed.setText(mValues.get(position).Speed + "km/h");
            mHolder.tv_latlong.setText(mValues.get(position).Longitude + "," + mValues.get(position).Latitude + " 方向：" + mValues.get(position).getDirectionStr());//方向根据返回的值改变
            mHolder.tv_address.setText("位        置：   " + (mValues.get(position).Address == null ? "" : mValues.get(position).Address));
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (GPSInfoData) v.getTag());
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
        public View mView;
        public RelativeLayout rl_item_top;
        public ImageView iv_item_state;
        public TextView tv_item_carlicense;
        public ImageView iv_arrow;
        public RelativeLayout rl_moreInfo;
        public TextView tv_equipmentTime;
        public TextView tv_status;
        public TextView tv_speed;
        public TextView tv_latlong;
        public TextView tv_address;

        public GPSInfoData mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            rl_item_top = (RelativeLayout) view.findViewById(R.id.rl_item_top);
            iv_item_state = (ImageView) view.findViewById(R.id.iv_item_state);
            tv_item_carlicense = (TextView) view.findViewById(R.id.tv_item_carlicense);
            iv_arrow = (ImageView) view.findViewById(R.id.iv_arrow);
            rl_moreInfo = (RelativeLayout) view.findViewById(R.id.rl_moreInfo);
            tv_equipmentTime = (TextView) view.findViewById(R.id.tv_equipmentTime);
            tv_status = (TextView) view.findViewById(R.id.tv_status);
            tv_speed = (TextView) view.findViewById(R.id.tv_speed);
            tv_latlong = (TextView) view.findViewById(R.id.tv_latlong);
            tv_address = (TextView) view.findViewById(R.id.tv_address);

            rl_item_top.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (rl_moreInfo.getVisibility() == View.VISIBLE) {
                        rl_moreInfo.setVisibility(View.GONE);
                        iv_arrow.setImageResource(R.mipmap.arrow_right);
                    } else {
                        rl_moreInfo.setVisibility(View.VISIBLE);
                        iv_arrow.setImageResource(R.mipmap.arrow_down);
                    }
                }
            });

            rl_moreInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    indexActivity.gotoHomefragment((String) mView.getTag());//跳转首页 并传入数据
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tv_item_carlicense.getText() + "'";
        }
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, GPSInfoData data);
    }
}
