package com.bofsoft.laio.customerservice.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.JasonLogin;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.customerservice.Widget.Widget_Input;
import com.bofsoft.laio.data.login.LoginData;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.config.BaseMember;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends BaseVehicleActivity implements View.OnClickListener {

    //    public Widget_Input wi_code;
    public Widget_Input wi_username;
    public Widget_Input wi_password;
//    public CheckBox checkProtocol_Vas;
    public TextView txtProtocol_Vas;
    public Widget_Image_Text_Btn login_btnLogin;
    private BaseMember.LoginEntry loginData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
//        wi_code = (Widget_Input) findViewById(R.id.wi_code);
        wi_username = (Widget_Input) findViewById(R.id.wi_username);
        wi_password = (Widget_Input) findViewById(R.id.wi_password);

//        wi_username.setText("13548134814");
//        wi_password.setText("qaz123");
//        checkProtocol_Vas = (CheckBox) findViewById(R.id.checkProtocol_Vas);
        txtProtocol_Vas = (TextView) findViewById(R.id.txtProtocol_Vas);
        login_btnLogin = (Widget_Image_Text_Btn) findViewById(R.id.login_btnLogin);

        txtProtocol_Vas.setOnClickListener(this);
        login_btnLogin.setOnClickListener(this);
        autoLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtProtocol_Vas:
//                Intent intent = new Intent(this, RegisterProtocolActivity.class);
//                intent.putExtra("URL", ServerConfigall.WebHost + "/agreement/register.html");
//                startActivity(intent);
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra(ConfigallCostomerService.Param_Key, wi_username.getText().toString());
                startActivity(intent);
                break;
            case R.id.login_btnLogin:
//                if (wi_code.getText().toString().equalsIgnoreCase("") == true) {
//                    showPrompt("提示", "请输入编号！");
//                    return;
//                }
                if (wi_username.getText().toString().equalsIgnoreCase("") == true) {
                    showPrompt("提示", "请输入您的用户名！");
                    return;
                }
                if (wi_password.getText().toString().equalsIgnoreCase("") == true) {
                    showPrompt("提示", "请输入您的密码！");
                    return;
                }
//                if (!checkProtocol_Vas.isChecked()) {
//                    showPrompt("提示", "请阅读并同意服务协议！");
//                    return;
//                }
//                new Thread(networkTask).start();
                loginIn();
                break;
            default:
                break;
        }

    }

    /**
     * 自动登录
     */
    public void autoLogin() {
        ConfigAll.setLogin(false);
        loginData = BaseMember.getDefaultLogin();
        // 没有记录登录信息
        if (loginData == null || loginData.getUserName().isEmpty() || loginData.getPassWord().isEmpty()) {
            return;
        }
        String tmpName = loginData.getUserName();
        wi_username.setText(tmpName);
        wi_password.setText(loginData.getPassWord());
//        new Thread(networkTask).start();
        //login(act, login.getUserName(), password.length() <= 0 ? login.getPassWord() : password, login.isSavePassWord(), login.isAutoLogin());
        loginIn();
    }

    private void loginIn() {
        Loading.show(LoginActivity.this, getResources().getString(R.string.waittip_logining));

        ConfigallCostomerService.UserToken = Config.spHelper.getString("UserTocken", "");
        JasonLogin login = new JasonLogin();
        login.setUsername(wi_username.getText().toString());
        login.setUserpassword(wi_password.getText().toString());
        login.setType("0");
        login.setAppType("8");
        login.setVer(Func.packageInfo(this,"versionName"));
//        login.setVer("2.5.2500");
        login.setSystemType("android");
        login.setSystemVersion(android.os.Build.VERSION.RELEASE);
        login.setUserToken(ConfigallCostomerService.UserToken);
        login.setGUID(ConfigallCostomerService.getGUID(this));
        login.setLoginType("");
        login.setDanwei("");
        String jsonStr = JSON.toJSONString(login);
        mylog.e(jsonStr);
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGIN, jsonStr, this);
    }

//    /**
//     * 网络操作相关的子线程
//     */
//    Runnable networkTask = new Runnable() {
//
//        @Override
//        public void run() {
//            // TODO
//            String myString = null;
//            try {
//                //myString = HtmlService.getHtml(ServerConfigall.gpsHost + wi_code.getText().toString());//外网www.bofsoft.com
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            // 在这里进行 http request.网络请求相关操作
//            Message msg = new Message();
//            Bundle data = new Bundle();
//            data.putString("value", myString);
//
//            msg.setData(data);
//            handler.sendMessage(msg);
//        }
//    };

    @Override
    protected void setTitleFunc() {
        setTitle("来噢客服");
    }

    @Override
    protected void setActionBarButtonFunc() {

    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_MOBILE_LOGIN:
                closeWaitDialog();
                autoLoginSet(wi_username.getText().toString(), wi_password.getText().toString(), true, true);
                JSONObject js = null;
                try {
                    js = new JSONObject(result);
                    LoginData loginData = LoginData.InitData(js);
                    ConfigAll.setKey(loginData.getKey());
                    ConfigAll.setSession(loginData.getSession().getBytes());
                    ConfigAll.setUserPhone(loginData.getUserPhone());
                    ConfigAll.setUserERPName(loginData.getUserERPName());
                    ConfigAll.setUserERPDanwei(loginData.getUserERPDanwei());
                    ConfigAll.setUserUUID(loginData.getUserUUID());
                    ConfigAll.setLogin(true);
                    ConfigAll.isLogining = false;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(LoginActivity.this, IndexActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
                break;
        }
    }

    public void autoLoginSet(String userName, String passWord, boolean autoLogin,
                             boolean savePassWord) {
        try {
            JSONArray inJsonArray = new JSONArray();
            JSONArray jsonArray = new JSONArray(BaseSysConfig.spHelper.getString("autoLogin", "[]"));
            BaseMember.LoginEntry newLe = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                BaseMember.LoginEntry le = BaseMember.LoginEntry.prase(jsonArray.getJSONObject(i));
                if (le == null)
                    continue;
                if (le.getUserName() != null && le.getUserName().equals(userName)) {
                    le.setUserName(userName);
                    le.setPassWord(savePassWord ? passWord : "");
                    le.setAutoLogin(autoLogin);
                    le.setSavePassWord(savePassWord);
                    le.setDefault(true);
                    newLe = le;
                } else {
                    le.setDefault(false);
                    inJsonArray.put(le.toJSONObject());
                }
            }
            if (newLe == null)
                inJsonArray.put(new BaseMember.LoginEntry(userName, savePassWord ? passWord : "", autoLogin, savePassWord, true)
                        .toJSONObject());
            else
                inJsonArray.put(newLe.toJSONObject());
            BaseSysConfig.spHelper.putString("autoLogin", inJsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent(BroadCastNameManager.DESTORY));
        DataCenter.close("LoginActivity.onBackPressed");
        Func.stopDataCenter(getApplicationContext());
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
    }
}
