package com.bofsoft.laio.customerservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.DataClass.RegisterGetAuthCodeData;
import com.bofsoft.laio.customerservice.DataClass.RegisterSubmitData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.customerservice.Widget.Widget_Input;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author admin
 */
public class ForgetPasswordActivity extends BaseVehicleActivity implements OnClickListener {
    private Widget_Input mEdtPhone;
    private Widget_Input mEdtAuthCode;
    private TextView mBtnGetCode;
    private Widget_Image_Text_Btn mBtnAuth;

    private RegisterGetAuthCodeData mGetCodeData;
    private String Mobile = "";
    private int MobileType = 1; // 用户使用手机类型：1:Android;2：IOS；
    private int VerifyType = 2; // 验证码类型 : 1：注册，2：找回密码
    private int ObjectType = 0; // 用户类型： 0:学员 1:教练

    private String VerifyCode;

    private int mTime = 120;

    // private boolean mIsContinue = true;

    @Override
    public void messageBack(int code, String result) {
        Loading.close();
        switch (code) {
            case CommandCodeTS.CMD_GETVERIFYCODE_INTF:
                getCodeResult(result);
                break;
            case CommandCodeTS.CMD_GETVERIFYCODEISVALID_INTF:
                parseCheckCode(result);
                break;

            default:
                super.messageBack(code, result);
                break;
        }
    }

    public void messageBackFailed(int errorCode, String error) {
        Loading.close();
        switch (errorCode) {
            case CommandCodeTS.CMD_GETVERIFYCODE_INTF:
                mBtnGetCode.setClickable(true);
                break;
            default:
                super.messageBackFailed(errorCode, error);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_forget_password);
        Intent intent = getIntent();
        if (intent.hasExtra(ConfigallCostomerService.Param_Key)) {
            Mobile = intent.getStringExtra(ConfigallCostomerService.Param_Key);
        }
        initView();
    }

    public void initView() {
        mEdtPhone = (Widget_Input) findViewById(R.id.forget_edtPhone);
        mBtnGetCode = (TextView) findViewById(R.id.btnGetCode);
        mEdtAuthCode = (Widget_Input) findViewById(R.id.forget_edtAuthCode);
        mBtnAuth = (Widget_Image_Text_Btn) findViewById(R.id.forget_btnAuth);

        mBtnGetCode.setOnClickListener(this);
        mBtnAuth.setOnClickListener(this);

        mEdtPhone.setText(Mobile);

        mGetCodeData = new RegisterGetAuthCodeData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetCode:
                Mobile = mEdtPhone.getText().toString().trim();
                if (Mobile.length() != 11) {
                    showPrompt("请输入11位数的手机号码");
                    return;
                }
                if (!Func.isMobileNO(Mobile)) {
                    showPrompt("输入了错误的手机号");
                    return;
                }
                CMD_getCode();
                break;
            case R.id.forget_btnAuth:
                Mobile = mEdtPhone.getText().toString().trim();
                VerifyCode = mEdtAuthCode.getText().toString().trim();
                if (Mobile.equals("")) {
                    showPrompt("请输入手机号");
                    return;
                }
                if (VerifyCode.equals("")) {
                    showPrompt("请输入验证码");
                    return;
                }

                if (!Func.isMobileNO(Mobile)) {
                    showPrompt("输入了错误的手机号");
                    return;
                }

                int code = Integer.valueOf(VerifyCode);
                checkAuthCodeCorrect(Mobile, code, VerifyType);
                break;
            default:
                break;
        }
    }

    /**
     * 检查验证码是否正确
     *
     * @param Mobile     手机号
     * @param VerifyCode 验证码
     * @param VerifyType 验证码类型，1-注册账号，2-重置账号登录密码，<br/>
     *                   3-重置账号支付密码，4-未验证手机号短信验证码， 5-已验证手机号短信验证码，<br/>
     *                   6-ERP账号验证手机号码，7-绑定手机号，8-更换绑定手机号
     */
    public void checkAuthCodeCorrect(String Mobile, int VerifyCode, int VerifyType) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Mobile", Mobile);
            jsonObject.put("VerifyCode", VerifyCode);
            jsonObject.put("VerifyType", VerifyType);
            jsonObject.put("ObjectType", ConfigallCostomerService.ObjectType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog(R.string.waittip_retrieve_psd);
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETVERIFYCODEISVALID_INTF,
                jsonObject.toString(), this);

    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mTime > 0) {
                // if (mTime > 0 && mIsContinue) {
                mTime--;
                mBtnGetCode.setText("(" + mTime + "s)重新获取");
                mBtnGetCode.setClickable(false);
                mHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                mBtnGetCode.setClickable(true);
                mTime = 120;
                mBtnGetCode.setText("获取验证码");
            }
        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // mIsContinue = false;
        mHandler.removeMessages(0);
    }

    public void CMD_getCode() {
        mGetCodeData.setMobile(Mobile);
        mGetCodeData.setMobileType(MobileType);
        mGetCodeData.setVerifyType(VerifyType);
        mGetCodeData.setObjectType(ObjectType);
        String content = null;
        try {
            content = mGetCodeData.getAuthCodeData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (content == null || content.equals("")) {
            // mylog.e("the content is: " + content);
            return;
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETVERIFYCODE_INTF, content, this);
        mBtnGetCode.setClickable(false);
        Loading.show(this);
    }

    RegisterSubmitData data;

    public void getCodeResult(String result) {
        closeWaitDialog();
        try {
            JSONObject jobj = new JSONObject(result);
            data = RegisterSubmitData.InitData(jobj);
            if (data.getCode() == 0) {
                mBtnGetCode.setClickable(true);
            } else {
                mHandler.sendEmptyMessage(0);
            }
            showPrompt(data.getContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseCheckCode(String result) {
        closeWaitDialog();
        BaseResponseStatusData data = JSON.parseObject(result, BaseResponseStatusData.class);
        if (data != null) {
            if (data.Code == 1) {
                // mIsContinue = false;
                Intent intent = new Intent(ForgetPasswordActivity.this, ResetPasswordActivity.class);
                intent.putExtra("Mobile", Mobile);
                intent.putExtra("MobileType", MobileType);
                intent.putExtra("VerifyType", VerifyType);
                intent.putExtra("ObjectType", ObjectType);
                intent.putExtra("VerifyCode", VerifyCode);
                startActivity(intent);
                this.finish();

            } else {
                showPrompt(data.Content);
            }
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("找回密码");
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
