package com.bofsoft.laio.customerservice.Activity.Order;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.customerservice.Activity.BaseFragment;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.IndexActivity;
import com.bofsoft.laio.customerservice.Common.ImageLoaderUtil;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.Order.OrderDetailRsp;
import com.bofsoft.laio.customerservice.Fragment.FragmentProductDetail;
import com.bofsoft.laio.customerservice.Fragment.FragmentServiceProtocol;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Tools.DialogUtils;
import com.bofsoft.laio.customerservice.Widget.JasonWebView;
import com.bofsoft.laio.customerservice.Widget.OptionTab;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.scrollview.ScrollViewContainer;
import com.bofsoft.sdk.scrollview.ScrollViewOver;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.utils.Tel;
import com.bofsoft.sdk.utils.Utils;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单详情
 *
 * @author yedong
 */
public class OrderDetailsActivity extends BaseVehicleActivity implements OptionTab.OptionTabChangeListener {

    public final int Request_Code_OrderCancle = 5;
    public final int Request_Code_OrderMoneyBack = 6;
    public final int Request_Code_PayOrder = 10;
    public final int Request_Code_Evaluate = 11;

    public final int ORDER_CUSTOMER_IN_HANDLE = -7; // -7退款客户介入处理中
    public final int ORDER_CUSTOMER_IN = -6; // -6退款客户介入处理中
    public final int ORDER_STATUS_PAY_TIMEOUT = -5; // -5-付款超时订单取消
    public final int ORDER_STATUS_TIMEOUT_NO_PAY = -4; // -4-超时未付款订单取消
    public final int ORDER_STATUS_CANCLE = -3; // -3-买家取消
    public final int ORDER_STATUS_MONEY_BACKED = -2; // -2-已退款订单
    public final int ORDER_STATUS_MONEY_BACKING = -1; // -1-退款中
    public final int ORDER_STATUS_WAITTING_PAY = 0; // 0-待付款
    public final int ORDER_STATUS_WAITTING_CONFIRM = 1; // 1-已付款待确认(培训)
    public final int ORDER_STATUS_FINISH = 2; // 2-完成

    public final int TRAINING_STATUS_NO_START = 0; // 0-没开始
    public final int TRAINING_STATUS_LATE = 1; // 1-未到场可标记违约
    public final int TRAINING_STATUS_TRAINING = 2; // 2-教练确认开始处于培训中
    public final int TRAINING_STATUS_FINISH = 3; // 3-培训结束

    private TextView mTxtOrderNum;
    private TextView mTxtOrderTime;
    //  private LinearLayout mLlayout_OrderStatus; // 订单状态
//  private TextView mTxtOrderStatus; // 订单状态
//  private TextView mTxtRemainTime; // 剩余时间
    private RelativeLayout mLlayout_CustomerCode;
    private TextView mTxtCustomerCode;
    private TextView tv_Qrcode;
    private ImageView mImgCoachPic;
    private TextView mTxtOrderPrice;
    private TextView mTxtProInfo;
    private JasonWebView mWebOrderContent;
    private OptionTab mOptionTab;
    private OptionTab mOptionTabTop;
    private LinearLayout mLLayout_DeadTime;
    private TextView mTxtDeadTime;
//    private Button mBtnBottomOne;
//    private Button mBtnBottomTwo;
//    private Button mBtnBottomFullpay;
//    private Button mBtnCancel;
//    private LinearLayout mLLayBottom;
    private TextView orderStyleTv;
    private LinearLayout ll_contact;
    private TextView tv_name, tv_call;

    private ScrollViewOver topScrollView;
    // private ScrollViewOver downScrollView;
    private ScrollViewContainer scrollViewContainer;

    public int OrderId; // 订单Id
    public int OrderType = 0; // Integer 订单购买产品类型，0报名产品，1初学培训产品，2补训产品，3陪练产品；
    private OrderDetailRsp orderDetailRsp;
    private boolean isStateChange = false;

    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
    private FragmentManager mFragmentManager = getSupportFragmentManager();
    private String[] tabTitles = {"产品简介", "产品内容", "退费规则", "学员保障"};
    // private String[] tabTitles = { "教练介绍", "产品简介", "产品内容", "退费规则", "学员保障" };

    private int ReqType = 1; // 请求方式：0-产品发布时请求, 1-通过产品或订单Id
    private int Type = 1; // 请求类型，0产品，1订单

    /**
     * 订单受理状态，在Status=0时有效<br/>
     * 0-未受理待卖家处理，<br/>
     * 1-已受理待学员完善培训时段（学时订单有效，OrderType为1，2，3）<br/>
     * 2-已受理待学员付款；
     */
    public int StatusAccepted = 0;

    private boolean logOrder = true; // true 从订单列表进入返回上一页 ， false 其他途径进入返回到订单页

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_ORDERVIEW:
                parseOrderDetails(result);
                break;
            case CommandCodeTS.CMD_ORDERREFUNDREQUEST_STU:
                parseMoneyBackInfo(result);
                break;
            case CommandCodeTS.CMD_ORDERAFFIRM_STU:
                parseConfirmPay(result);
                break;

            case CommandCodeTS.CMD_TRAINFINISH_STU: // 培训完成
                closeWaitDialog();
                JSONObject jsonObject;
                try {
                    jsonObject = new JSONObject(result);
                    // String Code = jsonObject.getString("Code");
                    String Content = jsonObject.getString("Content");
//                    mBtnBottomOne.setVisibility(View.GONE);
                    showPrompt(Content);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case CommandCodeTS.CMD_ORDERCANCELFORDP_STU:
                parseDpTimeTrainOrderCancle(result, CommandCodeTS.CMD_ORDERCANCELFORDP_STU);
                break;
            case CommandCodeTS.CMD_SITE_CANCELCHARGESORDER:
                parseDpTimeTrainOrderCancle(result, CommandCodeTS.CMD_SITE_CANCELCHARGESORDER);
                break;
            default:
                break;
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_order_details);
        Intent intent = getIntent();
        OrderId = intent.getIntExtra(ConfigallCostomerService.Param_Key, OrderId);
        OrderType = intent.getIntExtra(ConfigallCostomerService.Param_Key_Two, OrderType);
        StatusAccepted = intent.getIntExtra(ConfigallCostomerService.Param_Key_Three, StatusAccepted);
        logOrder = intent.getBooleanExtra("logOrder", true);
        initView();
    }

    public void initView() {

        mTxtOrderNum = (TextView) findViewById(R.id.txtOrderNum);
        mTxtOrderTime = (TextView) findViewById(R.id.txt_order_time);

//    mLlayout_OrderStatus = (LinearLayout) findViewById(R.id.layoutOrderStatus);
//    mTxtOrderStatus = (TextView) findViewById(R.id.txtOrderStatus);
//    mTxtRemainTime = (TextView) findViewById(R.id.txtRemainTime);
        orderStyleTv = (TextView) findViewById(R.id.order_style_tv);

        mLlayout_CustomerCode = (RelativeLayout) findViewById(R.id.llayout_CustomerCode);
        mTxtCustomerCode = (TextView) findViewById(R.id.txtCustomerCode);
        tv_Qrcode = (TextView) findViewById(R.id.tv_Qrcode);
        mImgCoachPic = (ImageView) findViewById(R.id.imgCoachPic);
        mTxtOrderPrice = (TextView) findViewById(R.id.txtOrderPrice);

        mTxtProInfo = (TextView) findViewById(R.id.webView_ProInfo);

        mWebOrderContent = (JasonWebView) findViewById(R.id.web_OrderContent);

        mLLayout_DeadTime = (LinearLayout) findViewById(R.id.llayout_DeadTime);
        mTxtDeadTime = (TextView) findViewById(R.id.txtDeadTime);

//        mLLayBottom = (LinearLayout) findViewById(R.id.llay_bottom);
//        mBtnCancel = (Button) findViewById(R.id.btnCancel);
//        mBtnBottomOne = (Button) findViewById(R.id.btn_bottom_one);
//        mBtnBottomTwo = (Button) findViewById(R.id.btn_bottom_two);
//        mBtnBottomFullpay = (Button) findViewById(R.id.btn_bottom_Fullpay);
        ll_contact = (LinearLayout) findViewById(R.id.ll_contact);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_call = (TextView) findViewById(R.id.tv_call);

        mOptionTab = (OptionTab) findViewById(R.id.optiontab);
        mOptionTab.setOptionTabChangeListener(this);

        mOptionTabTop = (OptionTab) findViewById(R.id.optiontab_top);
        mOptionTabTop.setOptionTabChangeListener(this);

        mOptionTab.setIsCanClick(true);
        mOptionTabTop.setIsCanClick(true);

        topScrollView = (ScrollViewOver) findViewById(R.id.top_scrollview);
        topScrollView.autoHeight();
        // downScrollView = (ScrollViewOver) findViewById(R.id.down_scrollview);
        scrollViewContainer = (ScrollViewContainer) findViewById(R.id.scrollViewContainer);
        scrollViewContainer.setOnScrollListener(new ScrollViewContainer.OnScrollListener() {

            @Override
            public void isTop(boolean isTop) {
                if (orderDetailRsp != null && orderDetailRsp.getOrderType() != 4) {
                    if (isTop)
                        mOptionTabTop.setVisibility(View.GONE);
                    else
                        mOptionTabTop.setVisibility(View.VISIBLE);
                }
            }
        });

        initFragmentAndOptionTab();
        getOrderDetails();
    }

    private void initFragmentAndOptionTab() {
        mFragmentList.clear();
        // FragmentCoachIntro one = new FragmentCoachIntro();
        FragmentProductDetail two = new FragmentProductDetail();
        FragmentServiceProtocol three = new FragmentServiceProtocol();
        FragmentServiceProtocol four = new FragmentServiceProtocol();
        FragmentServiceProtocol five = new FragmentServiceProtocol();
        // mFragmentList.add(one);
        mFragmentList.add(two);
        mFragmentList.add(three);
        mFragmentList.add(four);
        mFragmentList.add(five);
        mOptionTab.setTitle(tabTitles);
        mOptionTabTop.setTitle(tabTitles);
    }

    @Override
    public void onOptionTabChangeListener(View view, int position) {
        try {
            switch (view.getId()) {
                case R.id.option_tab:
                    mOptionTabTop.setSelectionWithoutListener(position);
                    break;
                case R.id.option_tab_top:
                    mOptionTab.setSelectionWithoutListener(position);
                    break;
                default:
                    break;
            }

            BaseFragment fragment = mFragmentList.get(position);
            mFragmentManager.beginTransaction().replace(R.id.llayout_content, fragment).commit();
            switchFragment(fragment, position);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 切换fragment加载数据
     *
     * @param position
     */
    private void switchFragment(BaseFragment fragment, int position) {
        switch (position) {
            case 0:
                // if (fragment instanceof FragmentCoachIntro && orderData != null) {
                // ((FragmentCoachIntro) fragment).loadData(orderData.CoachUserUUID);
                // }
                // break;
                // case 1:
                if (fragment instanceof FragmentProductDetail && orderDetailRsp != null) { // 产品简介
                    ((FragmentProductDetail) fragment).loadData(orderDetailRsp.getViewPro());
                }
                break;
            case 1: // 产品内容
                loadServiceProtocol(fragment, 0);
                break;
            case 2: // 退费规则
                loadServiceProtocol(fragment, 1);
                break;
            case 3: // 学员保障
                loadServiceProtocol(fragment, 2);
                break;
            default:
                break;
        }
    }

    /**
     * 加载相关协议内容
     *
     * @param fragment
     * @param protocolType 0-产品内容，1-退费规则，2-学员保障
     */
    private void loadServiceProtocol(BaseFragment fragment, int protocolType) {
        if (fragment instanceof FragmentServiceProtocol && orderDetailRsp != null)
            if (orderDetailRsp.getOrderType() == 0) {// 招生
                ((FragmentServiceProtocol) fragment).loadData(ReqType, protocolType, Type, OrderId);
            } else {
                ((FragmentServiceProtocol) fragment).loadData(ReqType, protocolType, Type, OrderId,
                        OrderType);
            }
    }

    private void refreshView() {
        if (orderDetailRsp != null) {
            mOptionTab.setIsCanClick(true);
            mOptionTabTop.setIsCanClick(true);
            mTxtOrderNum.setText(orderDetailRsp.getNum());
            mTxtOrderTime.setText(orderDetailRsp.getOrderTime());
//      mLlayout_OrderStatus.setVisibility(View.GONE);
            if (orderDetailRsp.getOrderType() == 6) {
                mLlayout_CustomerCode.setVisibility(View.GONE);
            } else {
                mLlayout_CustomerCode.setVisibility(View.VISIBLE);
            }
            mTxtCustomerCode.setText(orderDetailRsp.getCustomerCode());
//      Log.e("tag", "orderDetailRsp.getCoachName():"+orderDetailRsp.getCoachName());
//      Log.e("tag", "orderDetailRsp.getCoachTel():"+orderDetailRsp.getCoachTel());
            if (orderDetailRsp.getCoachTel() == null || orderDetailRsp.getCoachTel().equals("")) {
                ll_contact.setVisibility(View.GONE);
            } else {
                ll_contact.setVisibility(View.VISIBLE);
                if (orderDetailRsp.getCoachTel().equals("400 8804 789")) {
                    tv_name.setText("来噢客服");
                } else {
                    tv_name.setText("教练：" + orderDetailRsp.getCoachName());
                }
                final String phonenumber = orderDetailRsp.getCoachTel();
                tv_call.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Tel.getInstence().dial(OrderDetailsActivity.this, phonenumber);
                    }
                });
            }
            String state = "";
            switch (orderDetailRsp.getStatus()) {
                case 2:
                    if (orderDetailRsp.getStatusAppraise() == 0)
                        state = "待评价";
                    else
                        state = "已完成";
                    break;
                case 1:
                    state = "待培训";
                    break;
                case 0:
                    state = "待付款";
                    break;
                case -1:
                case -6:
                case -7:
                    state = "退款中";
                    break;
                case -2:
                    state = "已退款";
                    break;
                case -3:
                case -4:
                case -5:
                    state = "已取消";
                    break;
                default:
                    state = "未知";
                    break;
            }
            orderStyleTv.setText(state);


            ImageLoaderUtil.displaySmallSizeImage(orderDetailRsp.getProPicUrl(), mImgCoachPic,
                    R.mipmap.icon_default_small);
            mTxtOrderPrice.setText(getString(R.string.account, orderDetailRsp.getDealSum()));
            mTxtProInfo.setText(orderDetailRsp.getName());
            mWebOrderContent.loadDataWithBaseURL("", orderDetailRsp.getViewOrder(), "text/html", "utf-8",
                    "");
            switch (orderDetailRsp.getOrderType()) {
                // 0报名产品，1初学培训产品，2补训产品，3陪练产品,4计时培训；
                case 0:
                    mLlayout_CustomerCode.setVisibility(View.GONE); // 报名产品 去掉订单消费码
//                    initEnrollOrderView();
                    mOptionTab.setSelection(0);
                    break;
                case 1:
                case 2:
                case 3:
                case 6:
//                    initTrainOrderView();
                    mOptionTab.setSelection(0);
                    break;
                case 4:
                case 5:
                    mLlayout_CustomerCode.setVisibility(View.GONE);
                    mOptionTab.setVisibility(View.GONE);
                    mOptionTabTop.setVisibility(View.GONE);
//                    initTrainOrderView();
                    mOptionTab.setSelection(0);
                    break;
            }
        }
    }

    /**
     * 获取订单详情
     */
    public void getOrderDetails() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OrderId", OrderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ORDERVIEW, jsonObject.toString(), this);
    }

    /**
     * 解析订单详情
     *
     * @param result
     */
    public void parseOrderDetails(String result) {
        Loading.close();
        orderDetailRsp = JSON.parseObject(result, OrderDetailRsp.class);
//        orderDetailRsp = (OrderDetailRsp) Tools.merge(result, OrderDetailRsp.newBuilder());
        refreshView();
    }

    /**
     * 初始化报名订单View
     */
//    public void initEnrollOrderView() {
//        switch (orderDetailRsp.getStatus()) {
//            // 订单状态，-6客服介入,-5付款超时订单取消，-4超时未付款订单取消，-3买家取消，
//            // -2已退款订单，-1退款中，0待付款，1已付款待确认(培训)，2完成
//            case ORDER_CUSTOMER_IN_HANDLE: // -7退款客户介入处理中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_CUSTOMER_IN: // -6退款客户介入处理中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_STATUS_PAY_TIMEOUT: // -5-付款超时订单取消
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_STATUS_TIMEOUT_NO_PAY: // -4-超时未付款订单取消
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_STATUS_CANCLE: // -3-买家取消
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_STATUS_MONEY_BACKED: // -2-已退款订单
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                break;
//            case ORDER_STATUS_MONEY_BACKING: // -1-退款中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                break;
//
//            // OrderType 为0，Status 为0时有效，对支付类型进行判断，0支付订金，1支付尾款
//            case ORDER_STATUS_WAITTING_PAY: // 0-待付款
//                mLLayBottom.setVisibility(View.VISIBLE);
//                switch (orderDetailRsp.getStatusAccepted()) {
//                    case 0:
//                        // mBtnCancel.setVisibility(View.VISIBLE);
//                        // mBtnCancel.setOnClickListener(new OnClickListener() {
//                        // @Override
//                        // public void onClick(View v) {
//                        // showCancleOrder();
//                        // }
//                        // });
//                        //
//                        // mBtnBottomTwo.setVisibility(View.GONE);
//                        // mBtnBottomOne.setVisibility(View.VISIBLE);
//                        // mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                        // @Override
//                        // public void onClick(View v) {
//                        // ensureOrder();
//                        // }
//                        // });
//                        // break;
//                    case 2:
//                        mBtnCancel.setVisibility(View.VISIBLE);
//                        mBtnCancel.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                showCancleOrder();
//                            }
//                        });
//
//                        mBtnBottomTwo.setVisibility(View.GONE);
//                        mBtnBottomOne.setVisibility(View.VISIBLE);
//                        mBtnBottomFullpay.setVisibility(View.VISIBLE);
//                        if (orderDetailRsp.getPayDeposit() == 0) { // 支付订金
//                            mBtnBottomOne.setText(getString(R.string.pay_order_deposit));
//                            mLLayout_DeadTime.setVisibility(View.VISIBLE);
//                            mTxtDeadTime.setText(orderDetailRsp.getDeadTime());
//                        } else if (orderDetailRsp.getPayDeposit() == 1) { // 支付尾款
//                            mBtnBottomFullpay.setVisibility(View.GONE);
//                            mBtnBottomOne.setText(getString(R.string.pay_order_remaining));
//                            mLLayout_DeadTime.setVisibility(View.GONE);
//                        } else {
//                            mBtnBottomFullpay.setVisibility(View.GONE);
//                            mBtnBottomOne.setText(getString(R.string.pay_order_full_payment));
//                            mTxtDeadTime.setText(orderDetailRsp.getDeadTime());
//                        }
//                        mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                goPay(orderDetailRsp.getOrderType(), orderDetailRsp.getPayDeposit());
//                            }
//                        });
//                        mBtnBottomFullpay.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                goPay(orderDetailRsp.getOrderType(), 1);
//                            }
//                        });
//                        break;
//                    default:
//                        break;
//                }
//
//
//                break;
//            case ORDER_STATUS_WAITTING_CONFIRM: // 1-已付款待确认(培训)
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                mLLayout_DeadTime.setVisibility(View.GONE);
//
//                mBtnCancel.setVisibility(View.VISIBLE);
//                mBtnCancel.setText("退款");
//                mBtnCancel.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        applyMoneyBack();
//                    }
//                });
//
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.VISIBLE);
//                mBtnBottomOne.setText("确认付款");
//                mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        showConfirmPayDialog();
//
//                        // 确认之前先判断是否 设置过来噢支付密码
//                        // if (ConfigallStu.accountStatus != null) {
//                        // if (ConfigallStu.accountStatus.PayPwdIsSet == 0) {
//                        // AlertDialog.Builder builder = new AlertDialog.Builder(
//                        // OrderDetailsActivity.this);
//                        // builder.setMessage("未设置来噢支付密码")
//                        // .setPositiveButton("去设置",
//                        // new DialogInterface.OnClickListener() {
//                        // public void onClick(DialogInterface dialog, int id) {
//                        // Intent intent = new Intent(
//                        // OrderDetailsActivity.this,
//                        // SetPayPasswdActivity.class);
//                        // startActivity(intent);
//                        // }
//                        // })
//                        // .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                        // public void onClick(DialogInterface dialog, int id) {
//                        // dialog.cancel();
//                        // }
//                        // });
//                        // builder.show();
//                        // } else {
//                        // showConfirmPayDialog();
//                        // }
//                        // }
//                    }
//                });
//
//                break;
//
//            case ORDER_STATUS_FINISH: // 2-完成
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                mBtnCancel.setVisibility(View.GONE);
//                mBtnBottomOne.setText("评价");
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomFullpay.setVisibility(View.GONE);
//                switch (orderDetailRsp.getStatusAppraise()) {
//                    case 0:
//                        mBtnBottomOne.setText("评价");
//                        mBtnBottomOne.setVisibility(View.VISIBLE);
//                        mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                goEvaluate();
//                            }
//                        });
//                        break;
//                    case 1:
//                        mLLayBottom.setVisibility(View.GONE);
//                        mBtnBottomOne.setVisibility(View.GONE);
//                        break;
//
//                    default:
//                        break;
//                }
//
//                break;
//            default:
//                break;
//        }
//    }

//    /**
//     * 初始化培训订单View
//     */
//    public void initTrainOrderView() {
//        mBtnBottomFullpay.setVisibility(View.GONE);
//        switch (orderDetailRsp.getStatus()) {
//            // 订单状态，-5付款超时订单取消，-4超时未付款订单取消，-3买家取消，
//            // -2已退款订单，-1退款中，0待付款，1已付款待确认(培训)，2完成
//            case ORDER_CUSTOMER_IN_HANDLE: // -7退款客户介入处理中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//            case ORDER_CUSTOMER_IN: // -6退款客户介入处理中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_PAY_TIMEOUT: // -5-付款超时订单取消
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_TIMEOUT_NO_PAY: // -4-超时未付款订单取消
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_CANCLE: // -3-买家取消
//                mLlayout_CustomerCode.setVisibility(View.GONE);
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_MONEY_BACKED: // -2-已退款订单
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_MONEY_BACKING: // -1-退款中
//                mLLayBottom.setVisibility(View.GONE);
//                mBtnBottomOne.setVisibility(View.GONE);
//                mBtnBottomTwo.setVisibility(View.GONE);
//                break;
//
//            case ORDER_STATUS_WAITTING_PAY: // 0-待付款
//                mLLayout_DeadTime.setVisibility(View.VISIBLE);
//                mTxtDeadTime.setText(orderDetailRsp.getDeadTime());
//
//                if (orderDetailRsp.getOrderType() == 4) {
//                    mBtnCancel.setVisibility(View.VISIBLE);
//                    mBtnCancel.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            showCancleOrder();
//                        }
//                    });
//                    mBtnBottomOne.setVisibility(View.VISIBLE);
//                    mBtnBottomOne.setText("立即支付");
//                    mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            goPay(orderDetailRsp.getOrderType(), orderDetailRsp.getStatusAccepted());
//                        }
//                    });
//                    mBtnBottomTwo.setVisibility(View.GONE);
//                } else {
//                    switch (orderDetailRsp.getStatusAccepted()) {
//                        case 0:
//                        case 1:
//                            mBtnCancel.setVisibility(View.VISIBLE);
//                            mBtnCancel.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showCancleOrder();
//                                }
//                            });
//                            mBtnBottomOne.setVisibility(View.GONE);
//                            mBtnBottomTwo.setVisibility(View.GONE);
//                            break;
//                        case 2:
//                            mBtnCancel.setVisibility(View.VISIBLE);
//                            mBtnCancel.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showCancleOrder();
//                                }
//                            });
//                            mBtnBottomOne.setVisibility(View.VISIBLE);
//                            mBtnBottomOne.setText("立即支付");
//                            mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    goPay(orderDetailRsp.getOrderType(), orderDetailRsp.getStatusAccepted());
//                                }
//                            });
//                            mBtnBottomTwo.setVisibility(View.GONE);
//                            break;
//                    }
//                }
//                break;
//
//            case ORDER_STATUS_WAITTING_CONFIRM: // 1-已付款待确认(培训)
//                mLLayout_DeadTime.setVisibility(View.GONE);
//                // 培训状态
//                if (orderDetailRsp.getOrderType() != 4 && orderDetailRsp.getOrderType() != 5) {
//                    switch (orderDetailRsp.getStatusTrain()) {
//                        case TRAINING_STATUS_NO_START: // 0-没开始
//                            if (orderDetailRsp.getCanCancel() == 0) {
//                                mBtnBottomOne.setVisibility(View.GONE);
//                                mBtnBottomTwo.setVisibility(View.GONE);
//                                mBtnBottomTwo.setVisibility(View.GONE);
//
//                                mBtnCancel.setVisibility(View.VISIBLE);
//                                mBtnCancel.setText("退款");
//                                mBtnCancel.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        applyMoneyBack();
//                                    }
//                                });
//                                return;
//                            } else if (orderDetailRsp.getCanCancel() == 1) {
//                                mBtnBottomTwo.setVisibility(View.GONE);
//                                mBtnBottomOne.setVisibility(View.GONE);
//                                mBtnCancel.setVisibility(View.VISIBLE);
//                                mBtnCancel.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        // applyMoneyBack();
//                                        showCancleOrder();
//                                    }
//                                });
//                            } else if (orderDetailRsp.getCanCancel() == -1) {
//                                // 不能取消订单
//                                mLLayBottom.setVisibility(View.GONE);
//                            }
//
//                            break;
//                        case TRAINING_STATUS_LATE: // 1-未到场可标记违约
//                            if (orderDetailRsp.getCanCancel() == 0) {
//                                mBtnBottomOne.setVisibility(View.GONE);
//                                mBtnBottomTwo.setVisibility(View.GONE);
//
//
//                                // 有支付，才退款
//                                if (orderDetailRsp.getDealSum() > 0) {
//
//                                    mBtnCancel.setVisibility(View.VISIBLE);
//                                    mBtnCancel.setText("退款");
//                                    mBtnCancel.setOnClickListener(new OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            applyMoneyBack();
//                                        }
//                                    });
//                                } else {
//                                    mLLayBottom.setVisibility(View.GONE);
//                                    mBtnCancel.setVisibility(View.GONE);
//                                }
//                                return;
//                            } else if (orderDetailRsp.getCanCancel() == 1) {
//                                mBtnBottomTwo.setVisibility(View.GONE);
//                                mBtnBottomOne.setVisibility(View.GONE);
//
//                                mBtnCancel.setVisibility(View.VISIBLE);
//                                mBtnCancel.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        showCancleOrder();
//                                    }
//                                });
//                            } else if (orderDetailRsp.getCanCancel() == -1) {
//                                // 不能取消订单
//                                mLLayBottom.setVisibility(View.GONE);
//                            }
//
//                            break;
//                        case TRAINING_STATUS_TRAINING: // 2-教练确认开始处于培训中
//                            mylog.e("--------orderData.TrainRemainMin---------->>>> "
//                                    + orderDetailRsp.getTrainRemainMin());
////              mLlayout_OrderStatus.setVisibility(View.VISIBLE);
////              mTxtOrderStatus.setText("培训中");
////              mTxtRemainTime.setText(orderDetailRsp.getTrainRemainMin() + "分钟");
//                            mBtnCancel.setVisibility(View.GONE);
//                            mBtnBottomTwo.setVisibility(View.GONE);
//                            mBtnBottomOne.setVisibility(View.VISIBLE);
//                            mBtnBottomOne.setText("结束培训");
//                            mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (orderDetailRsp.getTrainRemainMin() <= 30) {
//                                        Builder builder = new Builder(OrderDetailsActivity.this);
//                                        builder.setTitle("提示");
//                                        builder.setMessage("您是否要结束该次培训？");
//                                        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                        builder.setPositiveButton("结束", new DialogInterface.OnClickListener() {
//
//                                            @Override
//                                            public void onClick(DialogInterface dialog, int which) {
//                                                finishTrain();
//                                                dialog.dismiss();
//                                            }
//                                        });
//                                        builder.show();
//                                    } else {
//                                        showPrompt("培训开始30分钟后才能结束培训");
//                                    }
//                                }
//                            });
//                            break;
//                        case TRAINING_STATUS_FINISH: // 3-培训结束
//                            mBtnCancel.setVisibility(View.VISIBLE);
//                            mBtnCancel.setText("退款");
//                            mBtnCancel.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    applyMoneyBack();
//                                }
//                            });
//
//                            mBtnBottomOne.setVisibility(View.GONE);
//                            mBtnBottomTwo.setVisibility(View.VISIBLE);
//                            mBtnBottomTwo.setText("确认付款");
//                            mBtnBottomTwo.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    showConfirmPayDialog();
//                                }
//                            });
//                            break;
//                        default:
//                            break;
//                    }
//                } else if (orderDetailRsp.getOrderType() == 5 || orderDetailRsp.getOrderType() == 4) {
//                    if (orderDetailRsp.getCanCancel() == 1) {
//                        mBtnCancel.setVisibility(View.GONE);
//                        mBtnBottomOne.setText("取消预约");
//                        mBtnBottomOne.setVisibility(View.VISIBLE);
//                        mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                if (orderDetailRsp.getOrderType() == 4) {
//                                    dpTimeTrainOrderCancle(0, CommandCodeTS.CMD_ORDERCANCELFORDP_STU);
//                                } else if (orderDetailRsp.getOrderType() == 5) {
//                                    dpTimeTrainOrderCancle(0, CommandCodeTS.CMD_SITE_CANCELCHARGESORDER);
//                                }
//                            }
//                        });
//                    } else {
//                        mLLayBottom.setVisibility(View.GONE);
//                        mBtnBottomTwo.setVisibility(View.GONE);
//                        mBtnBottomOne.setVisibility(View.GONE);
//                        mBtnCancel.setVisibility(View.GONE);
//                    }
//                }
//                break;
//
//            case ORDER_STATUS_FINISH: // 2-完成
//                if (orderDetailRsp.getOrderType() == 4) {
//                    mLlayout_CustomerCode.setVisibility(View.GONE);
//                    mBtnCancel.setVisibility(View.GONE);
//                    mBtnBottomTwo.setVisibility(View.GONE);
//                    switch (orderDetailRsp.getCanCancel()) {
//                        case 0:
//                            if (orderDetailRsp.getStatusAppraise() == 0) {
//                                mBtnBottomOne.setText("评价");
//                                mBtnBottomOne.setVisibility(View.VISIBLE);
//                                mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        goEvaluate();
//                                    }
//                                });
//                            } else {
//                                mLLayBottom.setVisibility(View.GONE);
//                                mBtnBottomOne.setVisibility(View.GONE);
//                            }
//
//                            break;
//                        case 1:
//                            mBtnBottomOne.setText("取消预约");
//                            mBtnBottomOne.setVisibility(View.VISIBLE);
//                            mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (orderDetailRsp.getOrderType() == 4) {
//                                        dpTimeTrainOrderCancle(0, CommandCodeTS.CMD_ORDERCANCELFORDP_STU);
//                                    } else if (orderDetailRsp.getOrderType() == 5) {
//                                        dpTimeTrainOrderCancle(0, CommandCodeTS.CMD_SITE_CANCELCHARGESORDER);
//                                    }
//                                }
//                            });
//                            if (orderDetailRsp.getStatusAppraise() == 0) {
//                                mBtnBottomTwo.setText("评价");
//                                mBtnBottomTwo.setVisibility(View.VISIBLE);
//                                mBtnBottomTwo.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        goEvaluate();
//                                    }
//                                });
//                            } else {
//                                mBtnBottomTwo.setVisibility(View.GONE);
//                            }
//                            break;
//                        default:
//                            break;
//                    }
//                } else {
//                    mLlayout_CustomerCode.setVisibility(View.GONE);
//                    mBtnCancel.setVisibility(View.GONE);
//                    mBtnBottomTwo.setVisibility(View.GONE);
//                    switch (orderDetailRsp.getStatusAppraise()) {
//                        case 0:
//                            if (orderDetailRsp.getCanCancel() == 1) {
//                                mBtnCancel.setVisibility(View.VISIBLE);
//                                mBtnCancel.setOnClickListener(new OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                        showCancleOrder();
//                                    }
//                                });
//                            }
//                            mBtnBottomOne.setText("评价");
//                            mBtnBottomOne.setVisibility(View.VISIBLE);
//                            mBtnBottomOne.setOnClickListener(new OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    goEvaluate();
//                                }
//                            });
//                            break;
//                        case 1:
//                            mLLayBottom.setVisibility(View.GONE);
//                            mBtnBottomOne.setVisibility(View.GONE);
//                            break;
//                        default:
//                            break;
//                    }
//                }
//                break;
//
//            default:
//                break;
//        }
//    }

    /**
     * 去支付
     *
     * @param orderType 订单类型
     * @param status    orderType=0 时：0-定金，1-尾款；orderType=1,2,3时：1-确认订单 ,2-全款，orderType=4时：计时培训
     */
    public void goPay(int orderType, int status) {
        if (orderDetailRsp == null) {
            return;
        }
        if (orderType == 0) {
            pay(status);
        } else if (orderType == 4) { //
            pay(orderType);
        } else {
            pay(status);
        }
    }

    /**
     * 支付
     *
     * @param status
     */
    public void pay(int status) {
//        if (orderDetailRsp != null) {
//            Intent intent = new Intent(this, PayActivity.class);
//            intent.putExtra(PayActivity.ORDER_ID, orderDetailRsp.getId());
//            intent.putExtra(PayActivity.ORDER_Num, String.valueOf(orderDetailRsp.getNum()));
//            intent.putExtra(PayActivity.PRO_TYPE, orderDetailRsp.getOrderType());
//            if (status == 0 && orderDetailRsp.getOrderType() == 0 && orderDetailRsp.getStatus() == 0
//                    && orderDetailRsp.getPayDeposit() == 0) {
//                intent.putExtra(PayActivity.ORDER_PAY_TYPE, PayActivity.PAY_TYPE_DEPOSIT);
//            } else {
//                intent.putExtra(PayActivity.ORDER_PAY_TYPE, PayActivity.PAY_TYPE_ALL);
//            }
//            intent.putExtra("PayType",status);
//            startActivityForResult(intent, Request_Code_PayOrder);
//        }
    }

    /**
     * 显示取消订单确认dialog
     */
    public void showCancleOrder() {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailsActivity.this);
        builder.setTitle(getString(R.string.tip));
        builder.setMessage("您是否要取消该订单")
                .setPositiveButton("确认取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        orderCancle();
                    }
                }).setNegativeButton("点错了", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 点击确认后，弹出确认输入来噢支付密码的dialog
     */
    public void showConfirmPayDialog() {
//        DialogUtils.showDialog(this, getString(R.string.tip), getString(R.string.order_ensure_pay_tip),
//                getString(R.string.cancel), null, getString(R.string.ok),
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        confirmPay("");
//                    }
//                });
    }

    /**
     * 查看申请退款记录
     */
    public void viewBackApplyRecord() {
//        if (orderDetailRsp != null) {
//            Intent intent = new Intent(OrderDetailsActivity.this, OrderBackApplyRecordActivity.class);
//            intent.putExtra(ConfigallStu.Param_Key, orderDetailRsp.getId());
//            startActivity(intent);
//        }
    }

    /**
     * 确认付款
     */
    public void confirmPay(String payPassWord) {
        if (orderDetailRsp != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("OrderId", orderDetailRsp.getId()); // OrderId 当前支付的订单Id
                jsonObject.put("PayPassWord", payPassWord); // PayPassWord 支付密码
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showWaitDialog();
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ORDERAFFIRM_STU, jsonObject.toString(),
                    this);
        }
    }

    /**
     * 解析确认支付结果
     *
     * @param result
     */
    public void parseConfirmPay(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data != null) {
            switch (data.Code) {
                case 1:
                    showPrompt("确认支付成功");
                    isStateChange = true;
                    getOrderDetails();
                    break;
                case 0:
                    showPrompt(data.Content);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 申请退款
     */
    public void applyMoneyBack() {
        if (orderDetailRsp != null) {
            getMoneyBackInfo(orderDetailRsp.getId());
        }
    }

    /**
     * 订单取消
     */
    public void orderCancle() {
//        if (orderDetailRsp != null) {
//            Intent intent = new Intent(this, OrderCancleActivity.class);
//            intent.putExtra(ConfigallStu.Param_Key, orderDetailRsp.getId());
//            intent.putExtra("orderType", OrderType);
//            startActivityForResult(intent, Request_Code_OrderCancle);
//        }
    }

    /**
     * 学员端_取消DP计时培训订单
     *
     * @param OpeType 操作类型，0首次提交，1再次确认提交(当需要确认时传1)
     */
    public void dpTimeTrainOrderCancle(final int OpeType, final short commandid) {
        // OpeType Integer 操作类型，0首次提交，1再次确认提交(当需要确认时传1)
        // OrderId Integer 当前取消的订单Id

        Utils.showDialog(this, "您是否要取消该订单", "点错了", "确认取消", null, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (orderDetailRsp != null) {
                    try {
                        JSONObject json = new JSONObject();
                        if (commandid == CommandCodeTS.CMD_ORDERCANCELFORDP_STU) {
                            json.put("OpeType", OpeType);
                        }
                        json.put("OrderId", orderDetailRsp.getId());
                        showWaitDialog();
                        DataLoadTask.getInstance().loadData(commandid, json.toString(), OrderDetailsActivity.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /**
     * 取消DP计时培训订单<br/>
     * 1成功，0不成功，-1返回提示信息需要再次提交进行确认，<br/>
     * -2返回提示信息没有后续操作，为-1时需要重新调用此接口，OpeType参数传1
     */
    public void parseDpTimeTrainOrderCancle(String result, final short commandid) {
        // 1成功，0不成功，-1返回提示信息需要再次提交进行确认，-2返回提示信息没有后续操作，为-1时需要重新调用此接口，OpeType参数传1；
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data != null) {
            switch (data.Code) {
                case 1:
                    showPrompt("取消学车预约成功", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getOrderDetails();
                        }
                    });
                    isStateChange = true;
                    break;
                case 0:
                    showPrompt(data.Content);
                    break;
                case -1:
                    DialogUtils.showDialog(this, getString(R.string.tip), data.Content, false, "确定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dpTimeTrainOrderCancle(1, commandid);
                                }
                            }, "取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                    break;
                case -2:
                    showPrompt(data.Content);
                    break;

                default:
                    break;
            }
        }
    }

    /**
     * 获取订单退款金额及原因
     *
     * @param orderId
     */
    public void getMoneyBackInfo(int orderId) {
        // OrderId Integer 当前支付的订单Id
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("OrderId", orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ORDERREFUNDREQUEST_STU,
                jsonObject.toString(), this);

    }

    /**
     * 解析退款信息
     *
     * @param result
     */
    public void parseMoneyBackInfo(String result) {
        closeWaitDialog();
//        OrdMoneyBackInfoData data = JSON.parseObject(result, OrdMoneyBackInfoData.class);
//        if (data != null && orderDetailRsp != null) {
//            Intent intent = new Intent(this, OrderMoneyBackApplyActivity.class);
//            intent.putExtra(ConfigallStu.Param_Key, orderDetailRsp.getId());
//            intent.putExtra(ConfigallStu.Param_Key_Two, data);
//            startActivityForResult(intent, Request_Code_OrderMoneyBack);
//        }
    }

    public void parseTrain(String result) {
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        switch (data.Code) {
            case 0:
                showPrompt(data.Content);
                break;
            case 1:
                showPrompt(data.Content, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        OrderDetailsActivity.this.finish();
                    }
                });
                break;

            default:
                break;
        }
    }

    /**
     * 结束培训
     */
    public void finishTrain() {
        if (orderDetailRsp != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("OrderId", orderDetailRsp.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showWaitDialog();
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_TRAINFINISH_STU, jsonObject.toString(),
                    this);
        }
    }

    /**
     * 去评价
     */
    public void goEvaluate() {
//        if (orderDetailRsp != null) {
//            Intent intent = new Intent(this, CoachEvaluateActivity.class);
//            intent.putExtra("orderId", orderDetailRsp.getId());
//            startActivityForResult(intent, Request_Code_Evaluate);
//        }
    }

    /**
     * 返回结果
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Request_Code_OrderCancle:
                if (resultCode == RESULT_OK) {
                    getOrderDetails();
                    isStateChange = true;
                }
                break;
            case Request_Code_OrderMoneyBack:
                if (resultCode == RESULT_OK) {
                    getOrderDetails();
                    isStateChange = true;
                }
                break;
            case Request_Code_Evaluate:
                if (resultCode == RESULT_OK) {
                    getOrderDetails();
                    isStateChange = true;
                }
                break;
            case Request_Code_PayOrder:
                if (resultCode == RESULT_OK) {
                    getOrderDetails();
                    isStateChange = true;
                }
                break;

            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        if (mWebOrderContent != null) {
            ViewGroup parent = (ViewGroup) mWebOrderContent.getParent();
            if (parent != null) {
                parent.removeView(parent);
            }
            super.onDestroy();
            mWebOrderContent.removeAllViews();
            mWebOrderContent.destroy();
        } else {
            super.onDestroy();
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("订单详情");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    /**
     * 监听back键被按下
     */
    @Override
    public void onBackPressed() {
//    super.onBackPressed();
        if (logOrder) {
            if (isStateChange) {
                setResult(RESULT_OK);
            } else {
                setResult(RESULT_CANCELED);
            }
            this.finish();
        } else {
            ConfigAll.isOrderJumpToIndex = true;
            Intent i = new Intent(OrderDetailsActivity.this, IndexActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.putExtra("index", 3);
            startActivity(i);
        }
        this.finish();
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                if (logOrder) {
                    if (isStateChange) {
                        setResult(RESULT_OK);
                    } else {
                        setResult(RESULT_CANCELED);
                    }
                    this.finish();
                } else {
                    ConfigAll.isOrderJumpToIndex = true;
                    Intent i = new Intent(OrderDetailsActivity.this, IndexActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    i.putExtra("index", 3);
                    startActivity(i);
                }
                break;
        }
    }

}
