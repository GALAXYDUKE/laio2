package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.DataClass.Order.OrdGrpData;
import com.bofsoft.laio.customerservice.DataClass.Order.OrdGrpListData;
import com.bofsoft.laio.customerservice.R;


/**
 * Created by szw on 2016/12/5.
 */
public class OrderGrpTransferAdapter extends BaseAdapter {

    Context context;
    OrdGrpListData data;
    private LayoutInflater inflater;

    public OrderGrpTransferAdapter(Context context, OrdGrpListData data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (data == null) {
            return 0;
        } else {
            return data.GroupList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return data.GroupList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    ViewHolder viewHolder;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.ordergrptransfer_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_GroupDM = (TextView) convertView.findViewById(R.id.tv_GroupDM);
            viewHolder.tv_Quantity = (TextView) convertView.findViewById(R.id.tv_Quantity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        OrdGrpData ordGrpData = data.GroupList.get(position);
        viewHolder.tv_GroupDM.setText(getGroupName(ordGrpData.GroupDM));
        viewHolder.tv_Quantity.setText(ordGrpData.Quantity + "");
        return convertView;
    }

    class ViewHolder {
        TextView tv_GroupDM;
        TextView tv_Quantity;
    }

    public String getGroupName(int dm) {
        String name = "";
        //2退款中，3待付款，4待培训，5培训中，6待确认付款，8已完成，9待受理
        switch (dm) {
            case 2:
                name = "退款中";
                break;
            case 3:
                name = "待付款";
                break;
            case 4:
                name = "待培训";
                break;
            case 5:
                name = "培训中";
                break;
            case 6:
                name = "待确认付款";
                break;
            case 7:
                name = "待评价";
                break;
            case 8:
                name = "已完成";
                break;
            case 9:
                name = "待受理";
                break;
            default:
                break;
        }
        return name;
    }
}
