package com.bofsoft.laio.customerservice.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.DataClass.RegisterSubmitData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.customerservice.Widget.Widget_Input;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author admin
 *
 */
public class ResetPasswordActivity extends BaseVehicleActivity implements OnClickListener {

  private RegisterSubmitData mSubmitData;
  private String Mobile = "";
  private int MobileType = 1; // 用户使用手机类型：1:Android;2：IOS；
  private int VerifyType = 2; // 验证码类型 : 1：注册，2：找回密码
  private int ObjectType = 1; // 用户类型： 0:学员 1:教练
  private String Password = "";
  private String VerifyCode = "";

  private Widget_Input mEdtConfirmPassword;
  private Widget_Input mEdtPassword;
  private Widget_Image_Text_Btn mBtnRegister;

  @Override
  public void messageBack(int code, String result) {
    switch (code) {
      case CommandCodeTS.CMD_RESETACCOUNTPWD_INTF:
        submitResult(result);
        break;
      default:
        super.messageBack(code, result);
        break;
    }

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState,true);
    setContentView(R.layout.activity_reset_password);
    initView();
  }

  public void initView() {
    mEdtPassword = (Widget_Input) findViewById(R.id.register_edtPassword);
    mEdtConfirmPassword = (Widget_Input) findViewById(R.id.register_edtConfirmPassword);
    mBtnRegister = (Widget_Image_Text_Btn) findViewById(R.id.Register_btnRegister);

    mBtnRegister.setOnClickListener(this);

    mSubmitData = new RegisterSubmitData();

    Intent intent = getIntent();
    Mobile = intent.getStringExtra("Mobile");
    MobileType = intent.getIntExtra("MobileType", MobileType);
    VerifyType = intent.getIntExtra("VerifyType", VerifyType);
    ObjectType = intent.getIntExtra("ObjectType", ObjectType);
    VerifyCode = intent.getStringExtra("VerifyCode");

  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.Register_btnRegister:
        Password = mEdtPassword.getText().toString().trim();
        String ConfirmPassword = mEdtConfirmPassword.getText().toString().trim();

        if (Password.equals("")) {
          showPrompt("密码不能为空");
          return;
        }
        if (ConfirmPassword.equals("")) {
          showPrompt("请输入确认密码");
          return;
        }
        if (Mobile.equals("")) {
          showPrompt("手机号为空");
          return;
        }
        if (VerifyCode.equals("")) {
          showPrompt("验证码为空");
          return;
        }
        if (!Func.isMobileNO(Mobile)) {
          showPrompt("手机号不正确");
          return;
        }
        if (!Password.equals(ConfirmPassword)) {
          showPrompt("密码不匹配");
          return;
        }
        CMD_ResetPassword();
        break;

      default:
        break;
    }
  }

  public void CMD_ResetPassword() {
    mSubmitData.setMobile(Mobile);
    mSubmitData.setPassword(Password);
    mSubmitData.setVerifyCode(VerifyCode);
    mSubmitData.setObjectType(ObjectType);
    String content = null;
    try {
      content = mSubmitData.getSubmitData();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (content == null || content.equals("")) {
      // mylog.e("the content is: " + content);
      return;
    }
    showWaitDialog(R.string.waittip_set_newpsd);
    DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_RESETACCOUNTPWD_INTF, content, this);
  }

  public void submitResult(String result) {
    closeWaitDialog();
    try {
      JSONObject jobj = new JSONObject(result);
      RegisterSubmitData data = RegisterSubmitData.InitData(jobj);
      if (data.getCode() == 0) {
        showPrompt(data.getContent());
      } else {
        showPrompt(data.getContent(), new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            ResetPasswordActivity.this.finish();
          }
        });
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected void setTitleFunc() {
    setTitle("设置密码");
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
