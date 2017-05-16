package com.bofsoft.laio.customerservice.Activity.Money;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONObject;

/**
 * 余额转出
 *
 * @author admin
 */
public class BalanceOutActivity extends BaseVehicleActivity implements OnClickListener {

    private EditText mEdtOutAccount;
    private EditText mEdtPayPasswd;
    private Widget_Image_Text_Btn mBtnOut;

    private double TransAmount; // 转出金额
    private String Password = ""; // 支付密码
    private double LimitMinAmount = 0.00;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_SUBMITACCOUNTBALANCETRANSFER_INTF:
                submitResult(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_balance_out);
        Intent intent = getIntent();
        TransAmount = intent.getDoubleExtra("BalanceOut", 0);
        initView();
    }

    public void initView() {
        mEdtOutAccount = (EditText) findViewById(R.id.edtOutAmount);
        mEdtPayPasswd = (EditText) findViewById(R.id.edtPayPasswd);
        mBtnOut = (Widget_Image_Text_Btn) findViewById(R.id.btn_Out);
        mEdtOutAccount.setText("" + TransAmount);
        mEdtOutAccount.setFocusable(false);
        if (TransAmount <= LimitMinAmount) {
            mBtnOut.setBackgroundResource(R.mipmap.button_send_unclick);
            mBtnOut.setEnabled(false);
        } else {
            mBtnOut.setEnabled(true);
            mBtnOut.setBackgroundResource(R.mipmap.button_send);
        }
        mBtnOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Out:
//        String amount = mEdtOutAccount.getText().toString().trim();
                // Password = mEdtPayPasswd.getText().toString().trim();

//        if (amount.equals("")) {
//          showPrompt("请输入您要转出的金额");
//          return;
//        }
                // if (Password.equals("")) {
                // showPrompt("请输入您的支付密码");
                // return;
                // }
                try {
//          TransAmount = Double.valueOf(amount);
                    submitBalanceOut();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    showPrompt("请输入正确的金额");
                }
                break;

            default:
                break;
        }
    }

    public void submitBalanceOut() {
        // Password String 支付密码
        // TransAmount Double 转出金额
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("TransAmount", TransAmount);
            jsonObject.put("Password", Password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_SUBMITACCOUNTBALANCETRANSFER_INTF,
                jsonObject.toString(), this);
    }

    public void submitResult(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data.getCode() == 0) {
            showPrompt(data.getContent());
        } else {
            showPrompt(data.getContent(), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    setResult(RESULT_OK);
                    BalanceOutActivity.this.finish();
                }
            });
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("余额转出");
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
