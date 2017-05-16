package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.DataClass.index.EnrollCarTypeData;
import com.bofsoft.laio.customerservice.R;

import java.util.List;

public class EnrollCarTypeAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<EnrollCarTypeData> mList;

    public EnrollCarTypeAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<EnrollCarTypeData> getmList() {
        return mList;
    }

    public void setmList(List<EnrollCarTypeData> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        }
        return mList.size();
    }

    @Override
    public EnrollCarTypeData getItem(int position) {
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
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_text_single_list_item, null);
            holder.txtMC = (TextView) convertView.findViewById(R.id.txt_name_single_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        EnrollCarTypeData data = mList.get(position);
        holder.txtMC.setText(data.CarType);
        return convertView;
    }

    class ViewHolder {
        TextView txtMC;
    }

}
