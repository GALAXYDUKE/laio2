package com.bofsoft.laio.customerservice.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.Order.OrderDetailsActivity;
import com.bofsoft.laio.customerservice.Activity.Order.OrderGrpListActivity;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.Order.OrdGrpDetailsData;
import com.bofsoft.laio.customerservice.DataClass.Order.OrdGrpDetailsListData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.ResponseListener;
import com.bofsoft.laio.widget.pulltorefresh.PullToRefreshListView;


/**
 * 订单分组详情列表
 *
 * @author yedong
 */
public class OrderGrpListAdapter extends
        AbsPullListViewAdapter<OrdGrpDetailsData, OrderGrpListAdapter.ViewHolder> {

    private Handler mHandler;

    public OrderGrpListAdapter(Context context, PullToRefreshListView listView) {
        super(context, listView);
    }

    /**
     * 设置状态变化通知handle
     */
    public void setChangerHandle(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void onPullItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(mContext, OrderDetailsActivity.class);
        intent.putExtra(ConfigallCostomerService.Param_Key, getItem(position));
        // intent.putExtra(ConfigallTea.Param_Key, getItem(position).Id);
        // intent.putExtra(ConfigallTea.Param_Key_Two,
        // getItem(position).OrderType);
        // intent.putExtra(ConfigallTea.Param_Key_Three,
        // getItem(position).StatusAccepted);
        ((BaseVehicleActivity) mContext).startActivityForResult(intent,
                OrderGrpListActivity.Request_Code_Details);
    }

    @Override
    public View createItemView() {
        View view = mInflater.inflate(R.layout.activity_order_grp_list_item, null);
        return view;
    }

    @Override
    public ViewHolder setItemHolder(View convertView) {
        ViewHolder holder = new ViewHolder();
        holder.txtName = (TextView) convertView.findViewById(R.id.txtName);
        holder.txtState = (TextView) convertView.findViewById(R.id.txtState);
        holder.txtContent = (TextView) convertView.findViewById(R.id.txt_OrderContent);
        holder.txtTip = (TextView) convertView.findViewById(R.id.txt_tip);
        holder.btnOrderGroup = (Button) convertView.findViewById(R.id.btn_OrderGroup);
        holder.llayContactStu = (LinearLayout) convertView.findViewById(R.id.llay_ContactStu);
        return holder;
    }

    @Override
    public void setItemViewContent(ViewHolder holder, final OrdGrpDetailsData data, int position) {
        if (data != null) {
            holder.txtName.setText(data.Name);
            String state = "";
            // holder.txtTip.setVisibility(View.VISIBLE);
            // holder.llayContactStu.setVisibility(View.VISIBLE);
            switch (data.Status) {
                // 订单状态，-6客服介入，-5付款超时订单取消，-4超时未付款订单取消，-3买家取消，-2已退款订单，-1退款中，0待付款，1已付款待确认(培训)，2完成
                case -7:
                    state = "退款中";
                    break;
                case -6:
                    state = "退款中";
                    break;
                case -5:
                    state = "已取消";
                    break;
                case -4:
                    state = "已取消";
                    break;
                case -3:
                    state = "已取消";
                    break;
                case -2:
                    state = "已退款";
                    break;
                case -1:
                    state = "退款中";
                    break;
                case 0:
                    // holder.btnOrderGroup.setVisibility(View.GONE);
                    switch (data.StatusAccepted) {
                        case 0:
                            state = "未受理";
                            holder.txtTip.setVisibility(View.VISIBLE);
                            holder.llayContactStu.setVisibility(View.VISIBLE);
                            break;
                        case 1:
                            state = "已受理,待教练确认";
                            holder.txtTip.setVisibility(View.VISIBLE);
                            holder.llayContactStu.setVisibility(View.VISIBLE);
                            break;
                        case 2:
                            state = "待付款";
                            holder.txtTip.setVisibility(View.GONE);
                            holder.llayContactStu.setVisibility(View.GONE);
                            break;
                        default:
                            holder.txtTip.setVisibility(View.GONE);
                            holder.llayContactStu.setVisibility(View.GONE);
                            break;
                    }
                    break;
                case 1:
                    state = "待培训";
                    break;
                case 2:
                    state = "已完成";
                    break;
                default:
                    break;
            }
            //判断是否已评价
            if (data.Status == 2) {
                if (data.StatusAppraise == 0) {
                    state = "待评价";
                } else {
                    state = "已完成";
                }
            }
            holder.txtState.setText(state);
            holder.txtContent.setText(Html.fromHtml(data.View));
            holder.btnOrderGroup.setFocusable(false);
            holder.llayContactStu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.StatusAccepted == 0) {
                        acceptOrder(data);
                    } else {
                        goCall(data.BuyerTel);
                    }
                }
            });
        }
    }

    /**
     * 受理订单
     *
     * @param data
     */
    public void acceptOrder(final OrdGrpDetailsData data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("OrderId", data.Id);
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_CONFIRMORDER_COACH,
                jsonObject.toString(), new ResponseListener() {
                    @Override
                    public void messageBack(int code, String result) {
                        // TODO Auto-generated method stub
                        parseAcceptOrder(data.BuyerTel, result);
                    }
                });
    }

    /**
     * 解析受理结果
     *
     * @param phoneNo
     * @param result
     */
    public void parseAcceptOrder(final String phoneNo, String result) {
        BaseResponseStatusData statusData = JSON.parseObject(result, BaseResponseStatusData.class);
        if (statusData.getCode() == 1) {
            ((BaseVehicleActivity) mContext).showPrompt(statusData.getContent(),
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (mHandler != null) {
                                mHandler.sendEmptyMessage(0);
                            }
                            goCall(phoneNo);
                            doRefresh();
                        }
                    });
        } else {
            if (!TextUtils.isEmpty(statusData.getContent())) {
                ((BaseVehicleActivity) mContext).showPrompt(statusData.getContent());
            }
        }
    }

    /**
     * 拨打电话
     *
     * @param phone
     */
    public void goCall(String phone) {
        if (!TextUtils.isEmpty(phone)) {
            try {
                String phoneString = "tel:" + phone;
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phoneString));
                mContext.startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void doReslut(int code, String result) {
        OrdGrpDetailsListData data = JSON.parseObject(result, OrdGrpDetailsListData.class);
        addListData(data.OrderList, data.More);
    }

    public class ViewHolder {
        TextView txtName;
        TextView txtState;
        TextView txtContent;
        TextView txtTip;
        Button btnOrderGroup;
        LinearLayout llayContactStu;
    }

}
