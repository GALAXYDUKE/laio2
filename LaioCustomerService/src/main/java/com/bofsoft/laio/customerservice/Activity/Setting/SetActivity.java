package com.bofsoft.laio.customerservice.Activity.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Multi_Text_Button;
import com.bofsoft.sdk.widget.base.Event;

/**
 * 安全设置
 *
 * @author yedong
 */
public class SetActivity extends BaseVehicleActivity implements OnClickListener {

    private final int Request_Code_Banding_Change_Auth = 5;
    private final int Request_Code_Banding_Alipay = 6;

    private Widget_Multi_Text_Button btnBandingAlipay;
    private Widget_Multi_Text_Button btnModifyPassword;
//    private Widget_Multi_Text_Button btnChangeBinding;
    // private Widget_Multi_Text_Button btnBindingNewSchool;

//    private Widget_Multi_Text_Button btnSetPayPassword;
//    private Widget_Multi_Text_Button btnChangePayPassword;
//    private Widget_Multi_Text_Button btnForgetPayPassword;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_GETACCOUNTSTATUS_INTF:
                parseAccountStatusInfo(result);
                initView();
                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,true);
        setContentView(R.layout.activity_set);


        initView();
    }

    public void initView() {
        btnBandingAlipay = (Widget_Multi_Text_Button) findViewById(R.id.set_btnBandingAlipay);
//        btnChangeBinding = (Widget_Multi_Text_Button) findViewById(R.id.set_btnChangeBinding);
        // btnBindingNewSchool = (Widget_Multi_Text_Button)
        // findViewById(R.id.set_btnBindingNewSchool);
        btnModifyPassword = (Widget_Multi_Text_Button) findViewById(R.id.set_btnModifyPassword);

//        btnSetPayPassword = (Widget_Multi_Text_Button) findViewById(R.id.btn_setPayPassword);
//        btnChangePayPassword = (Widget_Multi_Text_Button) findViewById(R.id.btn_changePayPassword);
//        btnForgetPayPassword = (Widget_Multi_Text_Button) findViewById(R.id.btn_forgetPayPassword);

        btnBandingAlipay.setOnClickListener(this);
        btnModifyPassword.setOnClickListener(this);
//        btnChangeBinding.setOnClickListener(this);
        // btnBindingNewSchool.setOnClickListener(this);

//        btnSetPayPassword.setOnClickListener(this);
//        btnChangePayPassword.setOnClickListener(this);
//        btnForgetPayPassword.setOnClickListener(this);

//        if (ConfigallCostomerService.accountStatus != null) {
//            switch (ConfigallCostomerService.accountStatus.AlipayIsVerify) {
//                // 1已验证, 0已提交，-1未提交，-2审核失败
//                case 1:
//                    btnBandingAlipay.setTextRight("已验证");
//                    break;
//                case 0:
//                    btnBandingAlipay.setTextRight("已提交");
//                    break;
//                case -1:
//                    btnBandingAlipay.setTextRight("未提交");
//                    break;
//                case -2:
//                    btnBandingAlipay.setTextRight("审核失败");
//                    break;
//
//                default:
//                    break;
//            }
//        }
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.set_btnBandingAlipay:
                intent = new Intent(SetActivity.this, BandingAliPayAccountActivity.class);
                startActivityForResult(intent, Request_Code_Banding_Alipay);
                break;

            case R.id.set_btnModifyPassword:
                intent = new Intent(SetActivity.this, ModifyPasswordActivity.class);
                startActivity(intent);
                break;

//            case R.id.set_btnChangeBinding: // 更帮手机
//                intent = new Intent(SetActivity.this, BindingNewPhoneActivity.class);
//                startActivity(intent);
//                break;

            // case R.id.set_btnBindingNewSchool:
            // intent = new Intent(SetActivity.this,
            // BindingNewSchoolAuthActivity.class);
            // startActivityForResult(intent, Request_Code_Banding_Change_Auth);
            // break;

//            case R.id.btn_setPayPassword:
//                intent = new Intent(this, SetPayPasswdActivity.class);
//                startActivity(intent);
//                break;
//
//            case R.id.btn_changePayPassword:
//                intent = new Intent(this, ModifyPayPasswdActivity.class);
//                startActivity(intent);
//                break;
//
//            case R.id.btn_forgetPayPassword:
//                intent = new Intent(this, ForgetPayPasswdActivity.class);
//                startActivity(intent);
//                break;

            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Request_Code_Banding_Change_Auth:

                    break;
                case Request_Code_Banding_Alipay:
                    getAccountStatusInfo();
                    break;

                default:
                    break;
            }
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("安全设置");
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
