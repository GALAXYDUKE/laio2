package com.bofsoft.laio.customerservice.Activity.Money;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.NetworkUtil;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.LoginActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Button;
import com.bofsoft.laio.customerservice.Widget.Widget_Input;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetPayPasswdActivity extends BaseVehicleActivity implements OnClickListener {

    private TextView txtTitle;
    private Widget_Input edtPassword;
    private Widget_Button btnOK;
    private InputMethodManager imm;

    private String Password;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_SUBMITPAYPWD_INTF:
                parseResult(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_set_pay_password);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        edtPassword = (Widget_Input) findViewById(R.id.edtPassword);
        btnOK = (Widget_Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOK:
                Password = edtPassword.getText().toString().trim();
                if (Password.equals("")) {
                    showPrompt("提示", "请输入您要设置的支付密码");
                    return;
                }
                if (Password.length() < 6 || Password.length() > 16) {
                    showPrompt("请检查支付密码的位数由6-16位字符组成");
                    return;
                }
                boolean isAllNum = false; // 判断是否全是数字
                try {
                    Long.parseLong(Password); // 用Long解析输入密码，如果能转化则说明是纯数字
                } catch (NumberFormatException e) {
                    isAllNum = true;
                }
                if (!isAllNum) {
                    showPrompt("密码不能为纯数字");
                    return;
                }

                Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{0,}$");
                Matcher matcher = pattern.matcher(Password);
                if (matcher.matches()) {
                    showPrompt("提示", "支付密码不能输入中文汉字");
                    return;
                }

                if (ConfigallCostomerService.accountStatus == null) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.putExtra(ConfigallCostomerService.Param_Key, true);
                    startActivity(intent);
                    return;
                }
                if (ConfigallCostomerService.accountStatus.MobileIsVerify == 0) {
                    showPrompt("绑定手机后才能设置支付密码哦");
                    return;
                }
                if (NetworkUtil.isNetworkAvailable(SetPayPasswdActivity.this) == false) {
                    showPrompt("网络错误，请检查网络再登录！");
                    return;
                }
                setPayPassword();
                break;
            default:
                break;
        }

    }

    private void setPayPassword() {
        JSONObject js = new JSONObject();
        js.put("Password", Password);
        String content = js.toString();
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_SUBMITPAYPWD_INTF, content, this);
    }

    public void parseResult(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data.Code == 1) {
            showPrompt("提示", data.Content, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
        } else {
            showPrompt("温馨提示", data.Content);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("设置支付密码");
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
