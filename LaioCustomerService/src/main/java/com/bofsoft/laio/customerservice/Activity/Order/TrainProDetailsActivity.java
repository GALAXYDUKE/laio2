package com.bofsoft.laio.customerservice.Activity.Order;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseFragment;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsAddrAdapter;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsTimeAdapter;
import com.bofsoft.laio.customerservice.Adapter.TrainDetailsVasAdapter;
import com.bofsoft.laio.customerservice.Common.ImageLoaderUtil;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.Order.FindTeacherData;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProDetailData;
import com.bofsoft.laio.customerservice.DataClass.Order.TrainProTimesData;
import com.bofsoft.laio.customerservice.Fragment.FragmentCoachIntro;
import com.bofsoft.laio.customerservice.Fragment.FragmentProductDetail;
import com.bofsoft.laio.customerservice.Fragment.FragmentServiceProtocol;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.CustomListView;
import com.bofsoft.laio.customerservice.Widget.OptionTab;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.scrollview.ScrollViewContainer;
import com.bofsoft.sdk.scrollview.ScrollViewOver;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 培训产品详情界面
 */
public class TrainProDetailsActivity extends BaseVehicleActivity implements View.OnClickListener,
        TrainDetailsTimeAdapter.TrainProTimeChangerListener, OptionTab.OptionTabChangeListener {
    public static final int REQUEST_CODE_CHOICE_TIME = 5; // 选择时间段
    // public static final int REQUEST_CODE_CHOICE_TESTSUB = 6; // 选择科目
    private ImageView imgCarPic;// 教练照片
    private TextView tvProName; // 培训内容
    private TextView tvPrice; // 价格
    private TextView tvTrainPrice; // 侧滑价格
    private TextView tvSaleInfo; // 优惠信息
    private TextView tvTrainTime; // 培训时间
    private TextView tvPurchaseNum; // 购买人数
    private TextView tvTrainArea; // 培训区域（四川成都）
    private TextView tvStudentEvaluate; // 学员评价
    private TextView tvEvaluateName; // 教练电话
    private TextView tvEvaluateContent; // 评价内容
    private TextView tvJiaoXue;
    private TextView tvKaYao;
    private TextView tvChangdi;
    private TextView tvZhiLiang;
    private TextView tvZhuangKuang;

    private Button btnConfirmBuy; // 确认购买
    private DrawerLayout mDrawerLayout = null;

    private ImageView imgCarPicSide;
    private LinearLayout layoutTestSub;
    private LinearLayout layoutTrainTime;
    private LinearLayout layoutVasService;
    private LinearLayout layoutAddress;

    private TextView tvTrainChoiced; // 侧滑产品名称
    // private TextView tvTestsub; // 科目选择状态
    private TextView tvTimeState; // 时段选择状态
    private TextView tvServiceState; // 增值服务选择状态
    private TextView tvAddressState; // 地址选择状态
    private CustomListView mListViewVas;
    private CustomListView mListTranferAddr;
    private OptionTab mOptionTab;
    private OptionTab mOptionTabTop;

    private ScrollViewOver topScrollviewOver;
    private ScrollViewContainer scrollViewContainer;

    private TrainDetailsTimeAdapter mTimeAdapter;
    private TrainDetailsVasAdapter mProVasAdapter;
    private TrainDetailsAddrAdapter mTrainAddrAdapter;

    private FragmentManager fragmentManager = getSupportFragmentManager();
    /**
     * ProDate不为空时有效， 0返回ProDate当天，-1返回ProDate前一天，1返回ProDate后一天学时产品
     */
    private int DateType = 0;
    // private int ProtocolType = 0; // 协议类型，0服务标准，1退费规则
    private int Type = 2; // 查看评价产品类型，1招生类产品，2培训类产品；
    private int ProType = 1; // 获取产品详情时产品类型

    private int ReqType = 1; // 请求方式：0-产品发布时请求, 1-通过产品或订单Id
    private int RequestType = 0; // 请求类型，0产品，1订单

    private FindTeacherData findData;
    private TrainProDetailData detailsData;
    private String ProDate = "";

    private JSONObject subObj;
    private int OpeType = 1; // 0：预览，1：提交
    private int TestSubId = 0; // （选择其他日期培训时段使用）培训科目Id，默认值为0不区分科目，1科目一、2科目二、3科目三、4科目四；

    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();
    private String[] tabTitles = new String[]{"教练介绍", "产品简介", "产品内容", "退费规则", "学员保障"};

    private boolean isBackChoicedTime = false; // 是否选择时间段（false需要刷新页面）
    private int OrderId;

    /**
     * 回调教练介绍，获取省份城市
     */
    FragmentCoachIntro.CoachInfoCallBack cityInfoCallBack = new FragmentCoachIntro.CoachInfoCallBack() {
        public void sendCity(String CityName, String DistrictName) {
            if (CityName.length() >= 3 && DistrictName.length() >= 3) {
                tvTrainArea.setText(CityName.substring(0, 2) + DistrictName.substring(0, 2));
                return;
            }
            if (CityName.length() == 0 && DistrictName.length() >= 3) {
                tvTrainArea.setText(DistrictName);
                return;
            }
            if (CityName.length() >= 3 && DistrictName.length() == 0) {
                tvTrainArea.setText(CityName);
                return;
            }
        }
    };

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_ONEDAYONECOACHTRAINPRO_STU: // 返回产品详情
                parseProductDetails(result);
                break;
            // case CommandCode.CMD_GET_TRAIN_PRO_DETAILS_TIME: //
            // //学员端_查看某教练某类可购买产品按日期产品详情预览
            // sendProDetailToFragment(result);
            // break;
            case CommandCodeTS.CMD_RENEWTRAINTIMES_STU: // 学员端_更改订单与教练协商后的培训时段信息
                parseSubmitTimeEnsure(result);
                break;
            case CommandCodeTS.CMD_GETTEAEVALUATIONLIST_INTF: // 获取教练的评价信息
                parseCoachEva(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_train_detail);
        Intent intent = getIntent();
        OrderId = intent.getIntExtra("orderId", 0);
        ProType = intent.getIntExtra(ConfigallCostomerService.Param_Key, 0);
        findData = (FindTeacherData) intent.getSerializableExtra(ConfigallCostomerService.Param_Key_Two);
        ProDate = intent.getStringExtra(ConfigallCostomerService.Param_Key_Three);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.closeDrawer(Gravity.RIGHT);
        imgCarPic = (ImageView) findViewById(R.id.img_toach_photo); // 教练照片

        LayoutParams linearParams = (LayoutParams) imgCarPic.getLayoutParams(); // 取控件ImageView当前的布局参数
        linearParams.width = ConfigallCostomerService.screenWidth; // 当控件的宽度为手机屏幕的宽度
        linearParams.height = ConfigallCostomerService.screenWidth * 3 / 4; // 当控件的高强与手机屏幕的宽度4:3
        imgCarPic.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件ImageView

        tvProName = (TextView) findViewById(R.id.tv_train_content); // 培训内容
        tvProName.setOnClickListener(this);
        tvPrice = (TextView) findViewById(R.id.tv_price); // 产品价格
        tvSaleInfo = (TextView) findViewById(R.id.tv_SaleInfo); // 产品价格
        tvTrainTime = (TextView) findViewById(R.id.tv_train_time); // 培训时间
        tvPurchaseNum = (TextView) findViewById(R.id.tv_purchase_number); // 购买人数
        tvTrainArea = (TextView) findViewById(R.id.tv_train_area); // 培训区域
        tvStudentEvaluate = (TextView) findViewById(R.id.tv_student_evaluate); // 学员评价
        tvStudentEvaluate.setOnClickListener(this);

        tvJiaoXue = (TextView) findViewById(R.id.tv_jiaoxue);
        tvKaYao = (TextView) findViewById(R.id.tv_kayao);
        tvChangdi = (TextView) findViewById(R.id.tv_changdi);
        tvZhiLiang = (TextView) findViewById(R.id.tv_zhiliang);
        tvZhuangKuang = (TextView) findViewById(R.id.tv_zhuangkuang);

        tvEvaluateName = (TextView) findViewById(R.id.tv_evaluate_name); // 评价人员姓名
        tvEvaluateContent = (TextView) findViewById(R.id.tv_evaluate_content); // 学员评价内容

        btnConfirmBuy = (Button) findViewById(R.id.btn_confirm_buy); // 确认购买按钮
        btnConfirmBuy.setOnClickListener(this);

        imgCarPicSide = (ImageView) findViewById(R.id.img_coach_head);
        tvTrainPrice = (TextView) findViewById(R.id.tv_train_price); // 侧滑产品价格
        tvTrainChoiced = (TextView) findViewById(R.id.tv_train_choice); // 侧滑产品名称
        layoutTestSub = (LinearLayout) findViewById(R.id.layout_Testsub);
        layoutTrainTime = (LinearLayout) findViewById(R.id.layout_choice_time1);
        layoutVasService = (LinearLayout) findViewById(R.id.layout_choice_time2);
        layoutAddress = (LinearLayout) findViewById(R.id.layout_choice_time3);
        layoutTestSub.setOnClickListener(this);
        layoutTestSub.setVisibility(View.GONE);
        layoutTrainTime.setOnClickListener(this);
        layoutVasService.setOnClickListener(this);
        layoutAddress.setOnClickListener(this);

        tvTimeState = (TextView) findViewById(R.id.tv_time_state);
        if (ProDate != null) {
            tvTimeState.setText(ProDate);
        }
        tvServiceState = (TextView) findViewById(R.id.tv_service_state);
        tvAddressState = (TextView) findViewById(R.id.tv_address_state);

        mTimeAdapter = new TrainDetailsTimeAdapter(this);
        mListViewVas = (CustomListView) findViewById(R.id.GridVas);
        mProVasAdapter = new TrainDetailsVasAdapter(this);
        mListViewVas.setAdapter(mProVasAdapter);

        mListTranferAddr = (CustomListView) findViewById(R.id.list_TransferAddr);
        mTrainAddrAdapter = new TrainDetailsAddrAdapter(this);
        mListTranferAddr.setAdapter(mTrainAddrAdapter);

        mOptionTab = (OptionTab) findViewById(R.id.option_tab);
        mOptionTab.setOptionTabChangeListener(this);
        mOptionTab.setTitle(tabTitles);

        mOptionTabTop = (OptionTab) findViewById(R.id.option_tab_top);
        mOptionTabTop.setOptionTabChangeListener(this);
        mOptionTabTop.setTitle(tabTitles);

        topScrollviewOver = (ScrollViewOver) findViewById(R.id.top_scrollview);
        topScrollviewOver.autoHeight();
        scrollViewContainer = (ScrollViewContainer) findViewById(R.id.scrollViewContainer);
        scrollViewContainer.setOnScrollListener(new ScrollViewContainer.OnScrollListener() {

            @Override
            public void isTop(boolean isTop) {
                if (isTop)
                    mOptionTabTop.setVisibility(View.GONE);
                else {
                    if (findData != null && findData.ProType != 4) {
                        mOptionTabTop.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (TextUtils.isEmpty(findData.SaleContent)) {
            if (TextUtils.isEmpty(findData.ProContent)) {
                tvSaleInfo.setVisibility(View.GONE);
            } else {
                tvSaleInfo.setVisibility(View.VISIBLE);
                tvSaleInfo.setText(Html.fromHtml(findData.ProContent));
            }
        } else {
            tvSaleInfo.setVisibility(View.VISIBLE);
            tvSaleInfo.setText(Html.fromHtml(findData.SaleContent)); // 设置优惠信息
        }

        tvProName.setText(findData.CoachName + "  " + findData.ProName); // 设置培训内容
        if (findData.ProPrice < findData.ProPriceMax) { // 设置产品价格区间
            String price =
                    getString(R.string.account, findData.ProPrice) + " - "
                            + getString(R.string.account, findData.ProPriceMax);
            tvPrice.setText(price);
            tvTrainPrice.setText(price);
        } else {
            tvPrice.setText(getString(R.string.account, findData.ProPrice));
            tvTrainPrice.setText(getString(R.string.account, findData.ProPrice));
        }

        tvTrainTime.setText("培训时间：" + ProDate); // 设置培训时间
        tvPurchaseNum.setText(findData.DealNo + "人已购买"); // 设置成交量
        tvStudentEvaluate.setText("学员评价(" + findData.EvaluateNo + ")"); // 设置学员评价数
        tvTrainChoiced.setText(findData.ProName);

        if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mDrawerLayout.openDrawer(Gravity.RIGHT);
            btnConfirmBuy.setText("确认提交");
        }

        initFragmentAndOptionTab();
        getProductDetails(ProDate); // 获取产品详情
    }

    /**
     * 获取教练评价信息
     */
    private void CMD_getCoachEva() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("MasterUUID", findData.CoachUserUUID);
            jsonObject.put("Type", Type);
            jsonObject.put("ObjectType", ConfigallCostomerService.ObjectType);
            jsonObject.put("Page", 1);
            jsonObject.put("PageNum", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETTEAEVALUATIONLIST_INTF,
                jsonObject.toString(), this);
    }

    /**
     * 获取产品详情
     */
    public void getProductDetails(String proDate) {
        if (findData != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ProType", ProType);
                jsonObject.put("CoachUserUUID", findData.CoachUserUUID);
                jsonObject.put("ProDate", proDate);
                jsonObject.put("DateType", DateType);
                jsonObject.put("OrderId", OrderId);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            showWaitDialog();
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ONEDAYONECOACHTRAINPRO_STU,
                    jsonObject.toString(), this);
        }
    }

    /**
     * 解析产品详情
     *
     * @param result
     */
    public void parseProductDetails(String result) {
        closeWaitDialog();
        try {
            detailsData = JSON.parseObject(result, TrainProDetailData.class);
            if (detailsData != null) {
                ProDate = detailsData.ProDate;
                if (!isBackChoicedTime) {
                    loadView();
                } else {
                    mTimeAdapter.setList(detailsData.TimesList);
                    mProVasAdapter.setList(detailsData.VasList);
                    mTrainAddrAdapter.setList(detailsData.AddrList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            mylog.e(e.getMessage());
        }
    }

    /**
     * 加载页面
     */
    public void loadView() {
        if (detailsData != null) {
            if (findData != null) {
                ImageLoaderUtil.displayBigSizeImage(findData.CarPicUrl, imgCarPic);
                ImageLoaderUtil.displaySmallSizeImage(findData.CarPicUrl, imgCarPicSide);
            }
            CMD_getCoachEva(); // 获取教练评价
            mTimeAdapter.setList(detailsData.TimesList);
            mProVasAdapter.setList(detailsData.VasList);
            mTrainAddrAdapter.setList(detailsData.AddrList);

            try {
                if (!TextUtils.isEmpty(detailsData.TestSubId)) {
                    TestSubId = Integer.valueOf(detailsData.TestSubId);
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            if (findData.ProType == 4) { // 计时培训
                mOptionTab.setSelection(1); // 默认选择第二个Tab
                mOptionTab.setVisibility(View.GONE);
                mOptionTabTop.setVisibility(View.GONE);
            } else {
                mOptionTab.setSelection(0); // 默认选择第一个Tab
            }
        }
    }

    /**
     * 初始化fragment
     */
    public void initFragmentAndOptionTab() {
        if (findData != null) {
            FragmentCoachIntro one = new FragmentCoachIntro();
            one.setCityBack(cityInfoCallBack);
            FragmentProductDetail two = new FragmentProductDetail();
            FragmentServiceProtocol three = new FragmentServiceProtocol();
            FragmentServiceProtocol four = new FragmentServiceProtocol();
            FragmentServiceProtocol five = new FragmentServiceProtocol();
            mFragmentList.add(one);
            mFragmentList.add(two);
            mFragmentList.add(three);
            mFragmentList.add(four);
            mFragmentList.add(five);
            mOptionTab.setTitle(tabTitles);
            mOptionTabTop.setTitle(tabTitles);
        }

    }

    /**
     * 按钮的点击事件
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_train_content: // 培训内容
                mOptionTab.setSelection(1);
                scrollViewContainer.toDown();
                break;

            case R.id.btn_confirm_buy: // 进入购买界面
                if (!mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                    btnConfirmBuy.setText("确认提交");
                    return;
                }
                if (mTimeAdapter.getCheckSize() < 1) {
                    showPrompt("请先选择培训时段，再确认提交！");
                    return;
                } else {
                    submitTimeEnsure();
                }
                break;
            case R.id.layout_choice_time1: // 选择时段
                startChoiceTimeActivity();
                break;

            default:
                break;
        }
    }

    /**
     * 跳转到选择时间段的activity界面中去
     */
    public void startChoiceTimeActivity() {
        if (detailsData != null) {
            Intent intent = new Intent(this, ChoiceTimeActivity.class);
            intent.putExtra("OrderId", OrderId);
            intent.putExtra("TestSubId", TestSubId);
            intent.putExtra("UUID", findData.CoachUserUUID);
            intent.putExtra("detailsData", detailsData);
            intent.putExtra(ConfigallCostomerService.Param_Key, (Serializable) mTimeAdapter.getCheckList());
            intent.putExtra(ConfigallCostomerService.Param_Key_Two, (Serializable) mProVasAdapter.getCheckList());
            intent.putExtra(ConfigallCostomerService.Param_Key_Three, (Serializable) mTrainAddrAdapter.getCheckList());
            intent.putExtra("ProType", ProType);
            intent.putExtra("Guid", mTimeAdapter.checkTimeGUID);
            startActivityForResult(intent, REQUEST_CODE_CHOICE_TIME);
        }
    }

    /**
     * 购买产品提交预览(培训时段修改)
     */
    public void submitTimeEnsure() {
        if (mTimeAdapter.getCheckSize() < 1) {
            showToastShortTime("请选择培训时间");
            return;
        }
        subObj = new JSONObject();
        JSONArray TimesList = new JSONArray();
        try {
            subObj.put("OrderId", OrderId);
            subObj.put("OpeType", OpeType);
            List<TrainProTimesData> timeCheckList = mTimeAdapter.getCheckListData();
            for (int i = 0; i < timeCheckList.size(); i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("Id", timeCheckList.get(i).Id);
                jsonObject.put("Name", timeCheckList.get(i).Name);
                TimesList.put(jsonObject);
            }
            subObj.put("TimesList", TimesList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_RENEWTRAINTIMES_STU, subObj.toString(),
                this);
    }

    /**
     * 解析培训时段确认
     *
     * @param result
     */
    public void parseSubmitTimeEnsure(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data != null) {
            switch (data.Code) {
                case 0:
                    showPrompt("提示", data.Content);
                    break;
                case 1:
                    showPrompt(data.getContent(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            setResult(RESULT_OK);
                            TrainProDetailsActivity.this.finish();
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CHOICE_TIME:
                    if (data != null) {
                        handleTimeActivityResult(data);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 处理页面返回
     *
     * @param data
     */
    private void handleTimeActivityResult(Intent data) {
        String newProData = data.getStringExtra("ProData");
        if (newProData != null) {
            if (!detailsData.ProDate.equals(newProData)) {
                detailsData = (TrainProDetailData) data.getSerializableExtra("detailsData");
                ProDate = newProData;
                isBackChoicedTime = true;
                if (detailsData != null) {
                    mTimeAdapter.setList(detailsData.TimesList);
                    mProVasAdapter.setList(detailsData.VasList);
                    mTrainAddrAdapter.setList(detailsData.AddrList);
                }
            }
            List<Boolean> timeCheckList = (List<Boolean>) data.getSerializableExtra("timeList");
            List<Boolean> vasCheckList = (List<Boolean>) data.getSerializableExtra("vasList");
            List<Boolean> addCheckList = (List<Boolean>) data.getSerializableExtra("addList");
            String checkGuid = data.getStringExtra("Guid");

            int mProVasNum = data.getIntExtra("mProVasNum", 0);
            if (vasCheckList.size() > 0) {
                tvServiceState.setText("");
                mListViewVas.setVisibility(View.VISIBLE);
            }
            if (mProVasNum == 0) {
                tvServiceState.setText("无");
                mListViewVas.setVisibility(View.VISIBLE);
            }

            if (addCheckList.size() != 0) {
                mListTranferAddr.setVisibility(View.VISIBLE);
                tvAddressState.setText("");
            }

            tvTrainTime.setText("培训时间：" + ProDate);
            if (timeCheckList.size() != 0) {
                mTimeAdapter.setCheckedList(timeCheckList);
                mTimeAdapter.checkTimeGUID = checkGuid;
                mProVasAdapter.setCheckList(vasCheckList, checkGuid);
                mTrainAddrAdapter.setCheckList(addCheckList, checkGuid);
                tvTimeState.setText(getSelectTimesString(mTimeAdapter.getCheckListData()));
            }
        }
    }

    /**
     * 获取选择的培训时间范围
     *
     * @param timeCheckList
     * @return
     */
    private String getSelectTimesString(List<TrainProTimesData> timeCheckList) {
        String timeString = "请选择培训时间";
        if (timeCheckList != null && timeCheckList.size() > 0) {
            if (timeCheckList.size() == 1) { // 只选中一个时间段情况
                timeString = timeCheckList.get(0).Name;
            } else { // 两个时间段及其以上
                String startTime = timeCheckList.get(0).Name;
                String lastTime = timeCheckList.get(timeCheckList.size() - 1).Name;
                startTime = startTime.substring(0, startTime.indexOf("~")).trim();
                lastTime = lastTime.substring(lastTime.indexOf("~") + 1).trim();
                timeString = startTime + "~" + lastTime;
            }
        }
        if (timeCheckList != null && timeCheckList.size() > 0) {
            timeString = ProDate + "  " + timeString;
        }
        return timeString;
    }

    /**
     * 解析教练评价
     *
     * @param json
     */
    private void parseCoachEva(String json) {
        closeWaitDialog();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int RateTotal = jsonObject.getInt("RateTotal");
            if (RateTotal != 0) {
                JSONArray jsonArray = jsonObject.getJSONArray("info");
                JSONObject jsonObject2 = jsonArray.getJSONObject(0);

                int jiaoxiao = jsonObject2.getInt("TrainCivilTeach");
                int kayao = jsonObject2.getInt("TrainBenefit");
                int changdi = jsonObject2.getInt("TrainTeachArea");
                int zhiliang = jsonObject2.getInt("TrainTeachQuality");
                int zhuangkuang = jsonObject2.getInt("TrainCarCondition");

                if (jiaoxiao < 4) {
                    tvJiaoXue.setCompoundDrawables(null, null, null, null);
                }
                if (kayao < 4) {
                    tvKaYao.setCompoundDrawables(null, null, null, null);
                }
                if (changdi < 4) {
                    tvChangdi.setCompoundDrawables(null, null, null, null);
                }
                if (zhiliang < 4) {
                    tvZhiLiang.setCompoundDrawables(null, null, null, null);
                }
                if (zhuangkuang < 4) {
                    tvZhuangKuang.setCompoundDrawables(null, null, null, null);
                }

                tvJiaoXue.setText(jsonObject2.getInt("TrainCivilTeach") + "");
                tvKaYao.setText(jsonObject2.getInt("TrainBenefit") + "");
                tvChangdi.setText(jsonObject2.getInt("TrainTeachArea") + "");
                tvZhiLiang.setText(jsonObject2.getInt("TrainTeachQuality") + "");
                tvZhuangKuang.setText(jsonObject2.getInt("TrainCarCondition") + "");
                tvEvaluateName.setText(jsonObject2.getString("StuName"));
                tvEvaluateContent.setText(jsonObject2.getString("Comment"));

                tvEvaluateName.setText(jsonObject2.getString("StuName"));
                tvEvaluateContent.setText(jsonObject2.getString("Comment"));
            } else {
                tvJiaoXue.setText(0 + "");
                tvKaYao.setText(0 + "");
                tvChangdi.setText(0 + "");
                tvZhiLiang.setText(0 + "");
                tvZhuangKuang.setText(0 + "");

                tvJiaoXue.setCompoundDrawables(null, null, null, null);
                tvKaYao.setCompoundDrawables(null, null, null, null);
                tvChangdi.setCompoundDrawables(null, null, null, null);
                tvZhiLiang.setCompoundDrawables(null, null, null, null);
                tvZhuangKuang.setCompoundDrawables(null, null, null, null);

                tvJiaoXue.setPadding(5, 5, 5, 5);
                tvKaYao.setPadding(5, 5, 5, 5);
                tvChangdi.setPadding(5, 5, 5, 5);
                tvZhiLiang.setPadding(5, 5, 5, 5);
                tvZhuangKuang.setPadding(5, 5, 5, 5);
                tvEvaluateName.setVisibility(View.GONE);
                tvEvaluateContent.setText("暂无评价信息");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户点击后退 要判断是否DrawerRight 是否是打开状态
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                mDrawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onTimeChangerListener(List<Boolean> checkList, String timeGuid) {
        mProVasAdapter.setGuid(timeGuid);
        mTrainAddrAdapter.setGuid(timeGuid);
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
            fragmentManager.beginTransaction().replace(R.id.layout_container, fragment).commit();
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
                if (fragment instanceof FragmentCoachIntro) {
                    ((FragmentCoachIntro) fragment).loadData(findData.CoachUserUUID);
                }
                break;
            case 1:
                if (fragment instanceof FragmentProductDetail) { // 产品简介
                    ((FragmentProductDetail) fragment).loadTrainData(ProType, findData.CoachUserUUID,
                            ProDate, OrderId);
                }
                break;
            case 2:
                if (fragment instanceof FragmentServiceProtocol && detailsData != null
                        && detailsData.TimesList.size() > 0) { // 产品内容
                    ((FragmentServiceProtocol) fragment).loadData(ReqType, 0, RequestType,
                            detailsData.TimesList.get(0).Id, detailsData.ProType);
                }
                break;
            case 3:
                if (fragment instanceof FragmentServiceProtocol && detailsData != null
                        && detailsData.TimesList.size() > 0) { // 退费规则
                    ((FragmentServiceProtocol) fragment).loadData(ReqType, 1, RequestType,
                            detailsData.TimesList.get(0).Id, detailsData.ProType);
                }
                break;
            case 4:
                if (fragment instanceof FragmentServiceProtocol && detailsData != null
                        && detailsData.TimesList.size() > 0) { // 学员保障
                    ((FragmentServiceProtocol) fragment).loadData(ReqType, 2, RequestType,
                            detailsData.TimesList.get(0).Id, detailsData.ProType);
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("培训产品详情");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }

}
