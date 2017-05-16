package com.bofsoft.laio.customerservice.Activity.Setting;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.Setting.BandingAlipayAccountData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONObject;

/**
 * 绑定支付宝账号
 *
 * @author admin
 */
public class BandingAliPayAccountActivity extends BaseVehicleActivity implements OnClickListener {

    private EditText mEdtAliAccount;
    private EditText mEdtAliName;
    private Widget_Image_Text_Btn mBtnOk;

    // private String AlipayAccount; // 绑定的账户
    // private String AlipayTrueName; // 账户姓名

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_SUBMITAPLIPAYACCOUNTINFO_INTF:
                parseSubmitResult(result);
                break;
            case CommandCodeTS.CMD_GETAPLIPAYACCOUNTINFO_INTF:
                parseBandingResult(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,true);
        setContentView(R.layout.activity_banding_alipay_account);
        initView();
    }

    public void initView() {
        mEdtAliAccount = (EditText) findViewById(R.id.edtOutAmount);
        mEdtAliName = (EditText) findViewById(R.id.edtAliName);
        mBtnOk = (Widget_Image_Text_Btn) findViewById(R.id.btn_Out);
        mBtnOk.setOnClickListener(this);

        getAlipayBandingInfo();
    }

    public void loadView(BandingAlipayAccountData data) {
        if (data != null) {
            mEdtAliAccount.setText(data.AlipayAccount);
            mEdtAliName.setText(data.AlipayTrueName);
            if (data.AlipayIsVerify == 1) {
                // 1已验证, 0已提交，-1未提交，-2审核失败
                mEdtAliAccount.setEnabled(false);
                mEdtAliName.setEnabled(false);
                mBtnOk.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_Out:
                String AlipayAccount = mEdtAliAccount.getText().toString().trim();
                String AlipayTrueName = mEdtAliName.getText().toString().trim();
                if (AlipayAccount.equals("")) {
                    showPrompt("请输入您的支付宝账号");
                    return;
                }
                if (AlipayTrueName.equals("")) {
                    showPrompt("请输入您的支付宝账号的姓名");
                    return;
                }
                submitBanding(AlipayAccount, AlipayTrueName);
                break;

            default:
                break;
        }
    }

    public void submitBanding(String AlipayAccount, String AlipayTrueName) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("AlipayAccount", AlipayAccount);
            jsonObject.put("AlipayTrueName", AlipayTrueName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        showWaitDialog(getString(R.string.data_submiting));
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_SUBMITAPLIPAYACCOUNTINFO_INTF,
                jsonObject.toString(), this);
    }

    public void parseSubmitResult(String result) {
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
                    BandingAliPayAccountActivity.this.finish();
                }
            });
        }
    }

    public void getAlipayBandingInfo() {
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETAPLIPAYACCOUNTINFO_INTF, null, this);
    }

    public void parseBandingResult(String result) {
        closeWaitDialog();
        BandingAlipayAccountData data = JSON.parseObject(result, BandingAlipayAccountData.class);

        loadView(data);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("绑定支付宝");
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
