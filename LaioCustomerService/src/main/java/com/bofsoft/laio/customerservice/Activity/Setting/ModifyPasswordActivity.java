package com.bofsoft.laio.customerservice.Activity.Setting;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.NetworkUtil;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Activity.LoginActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Button;
import com.bofsoft.laio.customerservice.Widget.Widget_Input;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

public class ModifyPasswordActivity extends BaseVehicleActivity {

    Widget_Input edtOldPassword;
    Widget_Input edtNewPassword;
    Widget_Input edtReNewPassword;
    Widget_Button btnOK;
    private InputMethodManager imm;

    private String oldPsd; // 旧密码
    private String newPsd; // 新密码
    private String confirmNewPsd; // 确认新密码

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_SUBMITMODIFYLOGINPWD_INTF:
                parseResult(result);
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,true);
        setContentView(R.layout.activity_modify_password);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        edtOldPassword = (Widget_Input) findViewById(R.id.edtOldPassword);
        edtNewPassword = (Widget_Input) findViewById(R.id.edtNewPassword);
        edtReNewPassword = (Widget_Input) findViewById(R.id.edtReNewPassword);
        btnOK = (Widget_Button) findViewById(R.id.btnOK);
        btnOK.setOnClickListener(onClickListener);
    }

    private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnOK:
                    oldPsd = edtOldPassword.getText().toString();
                    newPsd = edtNewPassword.getText().toString();
                    confirmNewPsd = edtReNewPassword.getText().toString();
                    if ("".equals(oldPsd)) {
                        showPrompt("错误提示", "请输入您的旧密码！");
                        return;
                    }
                    if ("".equals(newPsd)) {
                        showPrompt("错误提示", "请输入您的新密码！");
                        return;
                    }
                    if ("".equals(confirmNewPsd)) {
                        showPrompt("错误提示", "请再次输入您的新密码！");
                        return;
                    } else if (newPsd.equals(confirmNewPsd) == false) {
                        showPrompt("错误提示", "您两次输入的密码不相同，请再次输入！");
                        return;
                    }
                    if (newPsd.length() < 6 || newPsd.length() > 20) {
                        showPrompt("密码位数为6-20位字符");
                        return;
                    }
                    boolean isAllNum = false; // 判断是否全是数字
                    try {
                        Long.parseLong(newPsd); // 用Long解析输入密码，如果能转化则说明是纯数字
                    } catch (NumberFormatException e) {
                        isAllNum = true;
                    }
                    if (!isAllNum) {
                        showPrompt("密码不能为纯数字");
                        return;
                    }

                    if (NetworkUtil.isNetworkAvailable(ModifyPasswordActivity.this) == false) {
                        showPrompt("错误提示", "网络异常，请稍后再试");
                        return;
                    } else {
                        submitModifyPassword();
                    }
                    break;
                default:
                    break;
            }

        }
    };

    private void submitModifyPassword() {
        JSONObject js = new JSONObject();
        try {
            js.put("OldPassword", edtOldPassword.getText().toString());
            js.put("NewPassword", edtNewPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = js.toString();

        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_SUBMITMODIFYLOGINPWD_INTF, content, this);

    }

    public void parseResult(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data.Code == 1) {
            showPrompt("温馨提示", "密码修改成功，请重新登录！", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Send_MOBILE_LOGOUT();
                    DataCenter.close("ModifyPasswordActivity.parseResult");
                    Intent intent = new Intent(BroadCastNameManager.Logout);
                    sendBroadcast(intent);
                    ConfigAll.setLogin(false);
                    intent = new Intent(ModifyPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            showPrompt("温馨提示", data.Content);
        }
    }

    private void Send_MOBILE_LOGOUT() {
        JSONObject js = new JSONObject();
        try {
            js.put("Username", ConfigallCostomerService.Username);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String content = js.toString();
        mylog.e("send JSON：" + content);

        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGOUT, content, this);
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
        setTitle("修改登录密码");
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
