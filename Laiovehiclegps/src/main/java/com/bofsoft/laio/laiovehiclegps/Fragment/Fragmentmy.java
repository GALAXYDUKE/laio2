package com.bofsoft.laio.laiovehiclegps.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.ServerConfigall;
import com.bofsoft.laio.laiovehiclegps.Activity.AboutUsActivity;
import com.bofsoft.laio.laiovehiclegps.Activity.LoginActivity;
import com.bofsoft.laio.laiovehiclegps.Config.ConfigallGps;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Tools.Utils;
import com.bofsoft.laio.laiovehiclegps.Widget.Widget_Image_Text_Btn;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.utils.Tel;
import com.bofsoft.sdk.widget.base.BaseFragment;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by szw on 2017/2/21.
 */

public class Fragmentmy extends BaseFragment implements IResponseListener{

    RelativeLayout rl_AboutLaio;
    RelativeLayout rl_ContactLaio;
    Widget_Image_Text_Btn wttb_Loginout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState,true);
        if (getView() == null) {
            setContentView(R.layout.fragment_my);
            initView();
        }
    }

    private void initView() {
        rl_AboutLaio= (RelativeLayout) getView().findViewById(R.id.rl_AboutLaio);
        rl_ContactLaio= (RelativeLayout) getView().findViewById(R.id.rl_ContactLaio);
        wttb_Loginout= (Widget_Image_Text_Btn) getView().findViewById(R.id.wttb_Loginout);

        rl_AboutLaio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AboutUsActivity.class);
                intent.putExtra("URL", ServerConfigall.gpsWebHost + "/co/aboutusmobile_zc.html");
                intent.putExtra("AboutUs", true);
                startActivity(intent);
            }
        });

        rl_ContactLaio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialog(getActivity(), "是否拨打电话？4008804789", "拨打", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tel.getInstence().dial(getActivity(), "4008804789");
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
        });

        wttb_Loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut(1);
            }
        });

    }

    private void logOut(int i) {
        if(ConfigallGps.Username != null) {
                    // 退出自动登录
//                    autoLoginOut(ConfigallStu.Username);
//                    Config.spHelper.putString("password", "");
                    // 发送退出登录请求
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Username",  ConfigallGps.Username);//ConfigAll.getUserERPDanwei()
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGOUT,jsonObject.toString(),this);
        }
        // 重置一些数据
        ConfigAll.reset();
        ConfigallGps.reset();
        if (i==1) {
            autoLoginClear();
            Func.stopDataCenter(getActivity().getApplicationContext());
            // 返回首页
//            Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_LONG).show();
            Intent intent=new Intent();
            intent.setClass(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
    }

    public static void autoLoginClear() {
        BaseSysConfig.spHelper.putString("autoLogin", "[]");
    }

    @Override
    protected void setTitleFunc() {

    }

    @Override
    protected void setActionBarButtonFunc() {

    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }

    @Override
    public void messageBack(int code, String result) {

    }

    @Override
    public void messageBack(int code, int lenght, int tcplenght) {

    }

    @Override
    public void messageBackFailed(int errorCode, String error) {

    }
}
