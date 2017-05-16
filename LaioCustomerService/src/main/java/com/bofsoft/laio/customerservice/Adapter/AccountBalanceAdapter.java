package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bofsoft.laio.customerservice.Activity.Money.AccountBalanceDetailsActivity;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.AccountTransListData;
import com.bofsoft.laio.customerservice.DataClass.Order.BalancePageData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.widget.pulltorefresh.PullToRefreshListView;

/**
 * 账户收支明细列表
 *
 * @author yedong
 */
public class AccountBalanceAdapter extends
        AbsPullListViewAdapter<AccountTransListData, AccountBalanceAdapter.ViewHolder> {

    public AccountBalanceAdapter(Context context, PullToRefreshListView listView) {
        super(context, listView);
    }

    @Override
    public void onPullItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, AccountBalanceDetailsActivity.class);
        intent.putExtra(ConfigallCostomerService.Param_Key, getItem(position));
        mContext.startActivity(intent);
    }

    @Override
    public View createItemView() {
        View view = mInflater.inflate(R.layout.activity_account_balance_item, null);
        return view;
    }

    @Override
    public ViewHolder setItemHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.txtTransType = (TextView) convertView.findViewById(R.id.txtTransType);
        holder.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
        holder.txtBalance = (TextView) convertView.findViewById(R.id.txtBalance);
        holder.txtAmount = (TextView) convertView.findViewById(R.id.txtAmount);

        return holder;
    }

    @Override
    public void setItemViewContent(ViewHolder holder, AccountTransListData data, int position) {
        if (data != null) {
            holder.txtTransType.setText(data.TransType);
            holder.txtTime.setText(data.Time);
            holder.txtBalance.setText(String.valueOf(data.Balance));
            if (data.Amount > 0) {
                holder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.text_green));
            } else if (data.Amount < 0) {
                holder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.text_red));
            } else {
                holder.txtAmount.setTextColor(mContext.getResources().getColor(R.color.text_black_light));
            }
            holder.txtAmount.setText(String.valueOf(data.Amount));
        }
    }

    @Override
    public void doReslut(int code, String result) {
        BalancePageData<AccountTransListData> data =
                JSON.parseObject(result, new TypeReference<BalancePageData<AccountTransListData>>() {
                });
        addListData(data.TransList, data.more);
    }

    public class ViewHolder {
        TextView txtTransType;
        TextView txtTime;
        TextView txtBalance;
        TextView txtAmount;
    }

}
