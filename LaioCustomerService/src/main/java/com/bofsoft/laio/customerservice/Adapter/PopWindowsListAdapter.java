package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.R;

import java.util.List;

/**
 * @author admin
 */
public class PopWindowsListAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<String> mList;

    public PopWindowsListAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<String> getList() {
        return mList;
    }

    public void setList(List<String> mList) {
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
    public String getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.pop_windows_list_item, null);
            holder.textView = (TextView) convertView.findViewById(R.id.pop_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }

}
