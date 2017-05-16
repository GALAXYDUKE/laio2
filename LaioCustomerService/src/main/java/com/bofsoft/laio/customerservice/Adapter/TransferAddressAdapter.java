package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.DataClass.Order.TransferAddrData;
import com.bofsoft.laio.customerservice.R;

import java.util.List;

/**
 * 接送地址列表
 *
 * @author admin
 */
public class TransferAddressAdapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<TransferAddrData> mList;

    public TransferAddressAdapter(Context context) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public List<TransferAddrData> getmList() {
        return mList;
    }

    public void setmList(List<TransferAddrData> mList) {
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
    public TransferAddrData getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.activity_transfer_address_list_item, null);
            holder.textName = (TextView) convertView.findViewById(R.id.txt_Name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TransferAddrData data = mList.get(position);
        holder.textName.setText(data.getAddr());
        return convertView;
    }

    class ViewHolder {
        TextView textName;
    }

}
