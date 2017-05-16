package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.DataClass.Order.ProTimeData;
import com.bofsoft.laio.customerservice.R;

import java.util.List;


public class TrainProDataAdapter extends BaseAdapter {

    private List<ProTimeData> list;
    private ProTimeData proTimeData;
    private Context context;

    private LayoutInflater inflater;

    public TrainProDataAdapter(List<ProTimeData> list, Context context) {
        super();
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.layout_trainpro_prodata_choice, null);

            viewHolder = new ViewHolder();
            viewHolder.ProDate = (TextView) convertView.findViewById(R.id.tv_prodata);
            viewHolder.ProTimes = (TextView) convertView.findViewById(R.id.tv_protimes);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        proTimeData = list.get(position);
        viewHolder.ProDate.setText(proTimeData.ProDate);
        viewHolder.ProTimes.setText("可预约" + proTimeData.ProTimes + "学时");
        return convertView;
    }

    ViewHolder viewHolder;

    class ViewHolder {
        TextView ProDate;
        TextView ProTimes;
    }

}
