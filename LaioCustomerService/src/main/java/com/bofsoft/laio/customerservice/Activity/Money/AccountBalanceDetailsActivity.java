package com.bofsoft.laio.customerservice.Activity.Money;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.AccountTransDetailsData;
import com.bofsoft.laio.customerservice.DataClass.Order.AccountTransListData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

/**
 * 账户收支明细详情
 *
 * @author yedong
 */
public class AccountBalanceDetailsActivity extends BaseVehicleActivity implements OnClickListener {

    public AccountTransListData transListData;
    private TextView mTxtOrderNum;
    private TextView mTxtType;
    private TextView mTxtAmount;
    private TextView mTxtPayType;
    private TextView mTxtTime;
    private TextView mTxtBalance;
    private TextView mTxtRemark;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_GETACCOUNTASSETINOUTDETAIL_INTF:
                parseData(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_account_balance_details);
        Intent intent = getIntent();
        transListData = (AccountTransListData) intent.getSerializableExtra(ConfigallCostomerService.Param_Key);
        initView();
    }

    public void initView() {

        mTxtOrderNum = (TextView) findViewById(R.id.txtOrderNum);
        mTxtType = (TextView) findViewById(R.id.txtType);
        mTxtAmount = (TextView) findViewById(R.id.txtAmount);
        mTxtPayType = (TextView) findViewById(R.id.txtPayType);
        mTxtTime = (TextView) findViewById(R.id.txtTime);
        mTxtBalance = (TextView) findViewById(R.id.txtBalance);
        mTxtRemark = (TextView) findViewById(R.id.txtRemark);

        mTxtOrderNum.setOnClickListener(this);

        getData();
    }

    public void loadView(AccountTransDetailsData data) {
        if (data != null) {
            mTxtOrderNum.setText(data.OrderNum);
            mTxtType.setText(data.TransType);
            mTxtAmount.setText(String.valueOf(data.Amount));
            mTxtPayType.setText(data.PayType);
            mTxtTime.setText(data.Time);
            mTxtBalance.setText(String.valueOf(data.Balance));
            mTxtRemark.setText(data.Abstract);
        }
    }

    public void getData() {
        if (transListData == null) {
            return;
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TransNum", transListData.TransNum);
        jsonObject.put("FlowId", transListData.FlowId);

        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETACCOUNTASSETINOUTDETAIL_INTF,
                jsonObject.toString(), this);
    }

    public void parseData(String result) {
        closeWaitDialog();
        AccountTransDetailsData data = JSON.parseObject(result, AccountTransDetailsData.class);
        loadView(data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtOrderNum:

                break;
            default:
                break;
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("收支详情");
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
