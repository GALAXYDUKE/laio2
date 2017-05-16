package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.OrderGrpListAdapter;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.dialog.ListPopWindows;
import com.bofsoft.laio.widget.CustomPullRefreshListView;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

/**
 * 订单分组详情列表
 *
 * @author yedong
 */
public class OrderGrpListActivity extends BaseVehicleActivity implements OnClickListener {

    public final static int Order_Type_Today = 1;
    public final static int Order_Type_Money_Back = 2;
    public final static int Order_Type_Wait_Pay = 3;
    public final static int Order_Type_Wait_Training = 4;
    public final static int Order_Type_Training = 5;
    public final static int Order_Type_Wait_Ensure = 6;
    public final static int Order_Type_Wait_Evaluate = 7;
    public final static int Order_Type_Finish = 8;
    public final static int ORDER_TYPE_RECEICE = 9;

    public final static int Request_Code_Details = 10;
    public final static String Filter_Key = "param_filter_key";

    public String MasterUUID = "";
    public int GroupDM = 1; // 订单状态分组代码，1今日订单，2退款中，3待付款，4待培训，5培训中，6待确认，7待评价，8已完成 , 9待受理

    public int OrderType = 0; // Integer 订单分类，0全部订单，1报名类订单，2培训类订单
    public int ProType = 0; // Integer 订单对应产品类型，0为全部
    // 招生类(OrderType=1)：1计时计程，2一费制
    // 培训类(OrderType=2)：1初学培训产品，2补训产品，3陪练产品；

    private OrderGrpListAdapter mAdapter;
    private CustomPullRefreshListView mListView;

    private List<String> mOrderType;
    private List<String> mTypeAll;
    private List<String> mTypeEnroll;
    private List<String> mTypeTrain;
    private String[] groupDMList = new String[]{"今日订单", "退款中", "待付款", "待培训", "待评价", "已完成"};
    private Integer[] groupDMIndex = new Integer[]{1, 2, 3, 4, 7, 8};
    private LinearLayout mLLayout_Filter;
    private LinearLayout mLLayout_One;
    private LinearLayout mLLayout_Two;
    private LinearLayout mLLayout_Three;
    private TextView mTxtTitleOne;
    private TextView mTxtTitleTwo;
    private TextView mTxtTitleThree;
    private View lineThree;

    private boolean isNeedFilter = true; // 是否需要筛选

    private boolean isChange = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_grp_list);
        Intent intent = getIntent();
        GroupDM = intent.getIntExtra(ConfigallCostomerService.Param_Key, GroupDM);
        // OrderType = intent.getIntExtra(ConfigallTea.Param_Key_Two, OrderType);
        ProType = intent.getIntExtra(ConfigallCostomerService.Param_Key_Three, ProType);
        isNeedFilter = intent.getBooleanExtra(Filter_Key, isNeedFilter);

        initStatus();
        initView();
    }

    public void initView() {

        mLLayout_Filter = (LinearLayout) findViewById(R.id.llayout_filter);
        mLLayout_One = (LinearLayout) findViewById(R.id.llayout_One);
        mLLayout_Two = (LinearLayout) findViewById(R.id.llayout_Two);
        mLLayout_Three = (LinearLayout) findViewById(R.id.llayout_Three);
        mTxtTitleOne = (TextView) findViewById(R.id.txtTitleOne);
        mTxtTitleTwo = (TextView) findViewById(R.id.txtTitleTwo);
        mTxtTitleThree = (TextView) findViewById(R.id.txtTitleThree);

        mLLayout_Filter.setVisibility(View.GONE);


        if (isNeedFilter) {
            initStatus();

            mLLayout_One.setOnClickListener(this);
            mLLayout_Two.setOnClickListener(this);
            mLLayout_Three.setOnClickListener(this);
            mLLayout_One.setTag(0);
            mLLayout_Two.setTag(1);
            mLLayout_Three.setTag(2);

            mTxtTitleOne.setText(mOrderType.get(0));
            mTxtTitleTwo.setText(mTypeEnroll.get(0));
            for (int i = 0; i < groupDMIndex.length; i++) {
                if (GroupDM == groupDMIndex[i]) {
                    mTxtTitleThree.setText(groupDMList[i]);
                    break;
                }
            }
        } else {
            mLLayout_Filter.setVisibility(View.GONE);
        }

        mListView = (CustomPullRefreshListView) findViewById(R.id.pull_listView);
        mAdapter = new OrderGrpListAdapter(this, mListView);

        mAdapter.setChangerHandle(new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                isChange = true;
            }
        });
        CMD_getData();
    }

    public void initStatus() {
        mOrderType = Arrays.asList("全部订单", "招生订单", "培训订单");
        mTypeAll = Arrays.asList("全部类型");
        mTypeEnroll = Arrays.asList("全部类型", "一人一车一费制", "一人一车先学后付", "多人一车一费制");
        mTypeTrain = Arrays.asList("全部类型", "初学培训", "陪练");
    }

    public void CMD_getData() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("GroupDM", GroupDM);
            jsonObject.put("OrderType", OrderType);
            jsonObject.put("ProType", ProType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAdapter.loadData(jsonObject, CommandCodeTS.CMD_ORDERLIST_COACH);
    }

    public void showSelectStatusPopWindow(View parent) {
        ListPopWindows listPop = new ListPopWindows(this);
        final int index = (Integer) parent.getTag();
        if (index == 0) {
            listPop.setList(mOrderType);
        } else if (index == 1) {
            if (OrderType == 0) {
                listPop.setList(mTypeAll);
            } else if (OrderType == 1) {
                listPop.setList(mTypeEnroll);
            } else if (OrderType == 2) {
                listPop.setList(mTypeTrain);
            }
        } else if (index == 2) {
            listPop.setList(Arrays.asList(groupDMList));
        }

        listPop.setItemCallBack(new ListPopWindows.ItemClickCallback() {
            @Override
            public void onItemClickCallback(PopupWindow popupWindow, AdapterView<?> parent, View view,
                                            int position, long id) {
                if (index == 0) {
                    // 订单类型
                    mTxtTitleOne.setText(mOrderType.get(position));
                    OrderType = position;
                    ProType = 0;
                    switch (OrderType) {
                        case 0:
                            mTxtTitleTwo.setText(mTypeEnroll.get(0));
                            mLLayout_Two.setClickable(false);
                            break;
                        case 1:
                            mTxtTitleTwo.setText(mTypeTrain.get(0));
                            mLLayout_Two.setClickable(true);
                            ProType = -1;
                            break;
                        case 2:
                            mTxtTitleTwo.setText(mTypeTrain.get(ProType));
                            mLLayout_Two.setClickable(true);
                            break;
                    }
                } else if (index == 1) {
                    // 产品类型
                    if (OrderType == 0) {
                        mTxtTitleTwo.setText(mTypeAll.get(position));
                        ProType = position;
                    } else if (OrderType == 1) {
                        mTxtTitleTwo.setText(mTypeEnroll.get(position));
                        ProType = position - 1;
                    } else if (OrderType == 2) {
                        mTxtTitleTwo.setText(mTypeTrain.get(position));
                        switch (position) {
                            case 0:
                                ProType = 0;
                                break;
                            case 1:
                                ProType = 1;
                                break;
                            case 2:
                                ProType = 3;
                                break;
                            default:
                                ProType = position;
                                break;
                        }
                    }
                } else if (index == 2) {
                    // 订单状态
                    mTxtTitleThree.setText(groupDMList[position]);
                    GroupDM = groupDMIndex[position];
                }
                popupWindow.dismiss();
                CMD_getData();
            }
        });
        listPop.showPopWindows(parent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.llayout_One:
                showSelectStatusPopWindow(mLLayout_One);
                break;
            case R.id.llayout_Two:
                showSelectStatusPopWindow(mLLayout_Two);
                break;
            case R.id.llayout_Three:
                showSelectStatusPopWindow(mLLayout_Three);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setfinish();
    }

    public void setfinish() {
        if (isChange) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            isChange = true;
            switch (requestCode) {
                case Request_Code_Details:
                    // CMD_getData();
                    mAdapter.doRefresh();
                    break;
                default:
                    break;
            }
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
}
