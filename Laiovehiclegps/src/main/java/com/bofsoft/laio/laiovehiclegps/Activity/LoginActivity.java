package com.bofsoft.laio.laiovehiclegps.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.ServerConfigall;
import com.bofsoft.laio.data.login.LoginData;
import com.bofsoft.laio.laiovehiclegps.Config.Config;
import com.bofsoft.laio.laiovehiclegps.Config.ConfigallGps;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Tools.HtmlService;
import com.bofsoft.laio.laiovehiclegps.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.laiovehiclegps.Widget.Widget_Input;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.config.BaseMember;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.widget.base.BaseActivity;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;

import static com.bofsoft.laio.laiovehiclegps.R.id.txtProtocol_Vas02;
//import static com.bofsoft.laio.laiovehiclegps.R.id.view;

/**
 * Created by szw on 2017/2/21.
 */

public class LoginActivity extends BaseVehicleActivity implements View.OnClickListener, ServiceConnection {

    public Widget_Input wi_code;
    public Widget_Input wi_username;
    public Widget_Input wi_password;
    public CheckBox checkProtocol_Vas;
    public TextView txtProtocol_Vas02;
    public Widget_Image_Text_Btn login_btnLogin;
    private BaseMember.LoginEntry loginData = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        wi_code = (Widget_Input) findViewById(R.id.wi_code);
        wi_username = (Widget_Input) findViewById(R.id.wi_username);
        wi_password = (Widget_Input) findViewById(R.id.wi_password);
        checkProtocol_Vas = (CheckBox) findViewById(R.id.checkProtocol_Vas);
        txtProtocol_Vas02 = (TextView) findViewById(R.id.txtProtocol_Vas02);
        login_btnLogin = (Widget_Image_Text_Btn) findViewById(R.id.login_btnLogin);

        txtProtocol_Vas02.setOnClickListener(this);
        login_btnLogin.setOnClickListener(this);
//        new Thread(networkTask).start();
        autoLogin();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtProtocol_Vas02:
                Intent intent = new Intent(this, RegisterProtocolActivity.class);
                intent.putExtra("URL", ServerConfigall.WebHost + "/agreement/register.html");
                startActivity(intent);
                break;
            case R.id.login_btnLogin:
                if (wi_code.getText().toString().equalsIgnoreCase("") == true) {
                    showPrompt("提示", "请输入编号！");
                    return;
                }
                if (wi_username.getText().toString().equalsIgnoreCase("") == true) {
                    showPrompt("提示", "请输入您的用户名！");
                    return;
                }
                if (wi_password.getText().toString().equalsIgnoreCase("") == true) {
                    showPrompt("提示", "请输入您的密码！");
                    return;
                }
                if (!checkProtocol_Vas.isChecked()) {
                    showPrompt("提示", "请阅读并同意服务协议！");
                    return;
                }
                Loading.show(LoginActivity.this, getResources().getString(R.string.waittip_logining));
                new Thread(networkTask).start();
//                loginIn();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 0) {
                login_btnLogin.setEnabled(true);
                wi_code.setEnabled(true);
                wi_username.setEnabled(true);
                wi_password.setEnabled(true);
                loginIn();
            } else {
                closeWaitDialog();
                Toast.makeText(LoginActivity.this, "网络连接错误。。。", Toast.LENGTH_LONG).show();
            }
        }
    };

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
        String[] tmpStr = tmpName.split("\\$");
        if (tmpStr.length != 2) {
            return;
        }
        wi_code.setText(tmpStr[0]);
        wi_username.setText(tmpStr[1]);
        wi_password.setText(loginData.getPassWord());
        new Thread(networkTask).start();
        //login(act, login.getUserName(), password.length() <= 0 ? login.getPassWord() : password, login.isSavePassWord(), login.isAutoLogin());
    }

    private void loginIn() {
        ConfigallGps.UserToken = Config.spHelper.getString("UserTocken", "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Username", wi_username.getText().toString());
            jsonObject.put("Userpassword", wi_password.getText());
            jsonObject.put("Type", "");
            jsonObject.put("Danwei", wi_code.getText().toString());
            jsonObject.put("Ver", Func.packageInfo(this, "versionName"));
            jsonObject.put("SystemType", "android");
            jsonObject.put("SystemVersion", android.os.Build.VERSION.RELEASE);
            jsonObject.put("UserToken", ConfigallGps.UserToken);
            jsonObject.put("GUID", ConfigallGps.getGUID(this));
            jsonObject.put("LoginType", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGIN, jsonObject.toString(), this);
    }

    /**
     * 网络操作相关的子线程
     */
    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            // TODO
            String myString = null;
            try {
                String danwei = URLEncoder.encode(wi_code.getText().toString(),"UTF-8");
                myString = HtmlService.getHtml(ServerConfigall.gpsHost + danwei);//外网www.bofsoft.com
            } catch (Exception e) {
                e.printStackTrace();
            }

            String val = myString;
            if (val == null || val.length() == 0) {
                //IP没获取到
                // 在这里进行 http request.网络请求相关操作
                Message msg = new Message();
                msg.arg1 = -1;
                handler.sendMessage(msg);
            } else {
                String[] temp = val.split("\\$");
                if (temp.length == 2) {
                    String[] temp0 = temp[0].split(":");
                    String[] temp1 = temp[1].split(":");
                    ServerConfigall.serveripgps = temp0[0];//GPS IP
                    ServerConfigall.serverportgps = Integer.parseInt(temp0[1]);//GPS 端口
                    ServerConfigall.serverip = temp1[0];//IP
                    ServerConfigall.serverport = Integer.parseInt(temp1[1]);//端口
                    if(DataCenter.getInstance() == null) {
                        Func.bindDataCenter(getApplicationContext(), LoginActivity.this);
                    }else{
                        DataCenter.client.reconn("Login");
                        DataCenter.clientgps.reconn("Login");
//                        DataCenter.close("Login");
//                        try {
//                            int i = 0;
//                            while (!DataCenter.client.isNeedConn() && !DataCenter.clientgps.isNeedConn()){
//                                i++;
//                                Thread.sleep(100);
//                                if (i > 300)
//                                    break;
//                            }
//
//                            DataCenter.getInstance().open("Login");
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                    }
                    int i = 0;
                    //30秒，如果还没连接上，就跳出
                    while (i < 300) {
                        if (DataCenter.getInstance() == null) {
                            i++;
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Message msg = new Message();
                            msg.arg1 = 0;
                            handler.sendMessage(msg);
                            return;
                        }
                    }
                    Message msg = new Message();
                    msg.arg1 = -1;
                    handler.sendMessage(msg);
                } else {
                    //ip没获取到
                    Message msg = new Message();
                    msg.arg1 = -1;
                    handler.sendMessage(msg);
                }
            }


        }
    };

    @Override
    protected void setTitleFunc() {
        setTitle("来噢租车管理",24,Color.BLACK);
    }

    @Override
    protected void setActionBarButtonFunc() {

    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }



    //登陆服务器所需接口
    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_MOBILE_LOGIN:
                closeWaitDialog();
                autoLoginSet(wi_code.getText().toString() + "$" + wi_username.getText().toString(), wi_password.getText().toString(), true, true);
                JSONObject js = null;
                try {
                    js = new JSONObject(result);
                    LoginData loginData = LoginData.InitData(js);
                    ConfigAll.setKey(loginData.getKey());
                    ConfigAll.setSession(loginData.getSession().getBytes());
                    ConfigAll.setUserPhone(loginData.getUserPhone());
                    ConfigAll.setUserERPName(loginData.getUserERPName());
                    ConfigAll.setUserERPDanwei(loginData.getUserERPDanwei());
                    ConfigAll.setUserERPTrueName(loginData.getUserERPTrueName());
                    ConfigAll.setUserERPDanweiName(loginData.getUserERPDanweiName());
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
    public void onServiceConnected(ComponentName name, IBinder service) {

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

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
