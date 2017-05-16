package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.StudentEnrollActivity;
import com.bofsoft.laio.customerservice.Common.ImageLoaderUtil;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.OrderList;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.EmptyView;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;
import com.bofsoft.sdk.widget.listview.pulllistview.PageListView;
import com.bofsoft.sdk.widget.listview.pulllistview.ZrcListView;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by szw on 2016/12/5.
 */
public class OrderGrpTransferActivity extends BaseVehicleActivity implements ZrcListView.OnItemClickListener, PageListView.onCallBackListener {

    private final int Request_Code_Receive_Order = 20;
    private EmptyView mIEmptyView;
    private PageListView listView;
    //private OrderGrpTransferAdapter orderGrpTransferAdapter;
    private OrderList data = new OrderList();
    private OrderAdapter<OrderList.Order> adapter;
    private int page = 1;
    private boolean hasMore = false;
    private boolean canRefresh = false;
    private int targetPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.orderlistfragment);
        init();
    }

    private void init() {
        mIEmptyView = (EmptyView) getView().findViewById(R.id.inc_empty_view);
        listView = (PageListView) findViewById(R.id.paglistview);
        listView.setEmptyView(mIEmptyView);
        adapter = new OrderAdapter<OrderList.Order>(this, R.layout.order_list_item, data.getOrderList());
        listView.setDivider(null);
        listView.setDividerHeight((int) getResources().getDimension(R.dimen.app_gap));
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnCallBackListener(this);
        data.getOrderList().clear();
        adapter.notifyDataSetChanged();
//        getOrderList(1);
    }

    @Override
    public void onResume() {
        super.onResume();
        dofresh();
    }

    public void dofresh() {
        data.getOrderList().clear();
        page = 1;
        hasMore = true;
        getOrderList(1);
    }

    public void refresh(boolean canRefresh) {
        this.canRefresh = canRefresh;
    }


    private void getOrderList(int page) {
        if (page < 1) {
            page = 1;
        }
        this.page = page;
        try {
            JSONObject jo = new JSONObject();
            jo.put("GroupDM", 0);
            jo.put("OrderType", 1);
            jo.put("ProType", 0);
            jo.put("Page", page);
            jo.put("PageNum", 10);
            mylog.e(jo.toString());
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ORDERLIST_STU, jo.toString(),
                    this);
        } catch (Exception e) {

        }
    }

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_ORDERLIST_STU:
                listView.success();
                OrderList mdata = JSON.parseObject(result, OrderList.class);
                if (page == 1) {
                    data.getOrderList().clear();
                }
                data.getOrderList().addAll(mdata.getOrderList());
                hasMore = mdata.isMore();
                data.setMore(mdata.isMore());
                data.setTotalCount(mdata.getTotalCount());
                adapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("我的订单");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                finish();
                break;
        }
    }

    @Override
    public void onItemClick(ZrcListView parent, View view, int position, long id) {
        targetPosition = position;
        OrderList.Order item = data.getOrderList().get(position);
        Intent intent = new Intent(OrderGrpTransferActivity.this, OrderDetailsActivity.class);

        intent.putExtra(ConfigallCostomerService.Param_Key, item.getId());
        intent.putExtra(ConfigallCostomerService.Param_Key_Two, item.getOrderType());
        intent.putExtra(ConfigallCostomerService.Param_Key_Three, item.getStatusAccepted());
        startActivityForResult(intent, 0);
    }

    @Override
    public void back(PageListView.RlState s) {
        if (s == PageListView.RlState.REFRESH) {
            page = 1;
            hasMore = true;
            getOrderList(1);
        } else if (s == PageListView.RlState.MORE) {
            if (hasMore) {
                getOrderList(page + 1);
            } else {
                listView.failed();
            }
        }
    }

    class HandlerShow {
        TextView order_num_tv;
        TextView order_state_tv;
        ImageView head_iv;
        TextView name_tv;
        TextView tv_1;
        TextView tv_2;
    }


    private class OrderAdapter<T> extends ArrayAdapter<T> implements View.OnClickListener {

        private int resId;
        private LayoutInflater mInflater;

        public OrderAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
            this.resId = resource;
            this.mInflater = LayoutInflater.from(context);
        }

        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            OrderList.Order item = data.getOrderList().get(position);

            Intent intent = new Intent(OrderGrpTransferActivity.this, StudentEnrollActivity.class);
            intent.putExtra(ConfigallCostomerService.Param_Key, JSON.toJSONString(item));
            startActivity(intent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            OrderList.Order item = data.getOrderList().get(position);
            HandlerShow handler;
            if (convertView == null) {
                convertView = mInflater.inflate(resId, null);
                handler = new HandlerShow();
                handler.order_num_tv = (TextView) convertView.findViewById(R.id.order_num_tv);
                handler.order_state_tv = (TextView) convertView.findViewById(R.id.order_state_tv);
                handler.head_iv = (ImageView) convertView.findViewById(R.id.head_iv);
                handler.name_tv = (TextView) convertView.findViewById(R.id.name_tv);
                handler.tv_1 = (TextView) convertView.findViewById(R.id.tv_1);
                handler.tv_2 = (TextView) convertView.findViewById(R.id.tv_2);
                convertView.setTag(handler);
                handler.order_state_tv.setOnClickListener(this);
            } else {
                handler = (HandlerShow) convertView.getTag();
            }
            if (item.getInviteCanRegister() == 1) {
                handler.order_state_tv.setTag(position);
                handler.order_state_tv.setVisibility(View.VISIBLE);
            } else {
                handler.order_state_tv.setVisibility(View.GONE);
            }
            handler.order_num_tv.setText("订单编号 " + item.getNum());
            ImageLoaderUtil.displaySmallSizeImage(item.getProPicUrl(), handler.head_iv, R.mipmap.icon_default_headstu);
            handler.name_tv.setText(item.getCoachName() + "  报名  " + item.getCarType());
            handler.tv_1.setText(getTrainType(item.getTrainType()));
            handler.tv_2.setText("下单时间：" + item.getOrderTime());
            return convertView;
        }
    }

    private String getTrainType(int type) {
        switch (type) {
            case 0:
                return "多人一车一费制";
            case 1:
                return "一人一车一费制";
            case 2:
                return "体验学车";
            case 3:
                return "自营模式";
        }
        return "";
    }
}
