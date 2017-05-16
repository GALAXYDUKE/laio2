package com.bofsoft.laio.customerservice.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.ActivityMgr;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Activity.Money.AccountManagerActivity;
import com.bofsoft.laio.customerservice.Activity.Order.OrderGrpTransferActivity;
import com.bofsoft.laio.customerservice.Activity.Setting.SetActivity;
import com.bofsoft.laio.customerservice.Activity.erweima.ShareActivity;
import com.bofsoft.laio.customerservice.Common.ImageLoaderUtil;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Tools.Utils;
import com.bofsoft.laio.data.BaseData;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.widget.base.Event;
import com.bofsoft.sdk.widget.imageview.RoundImageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by szw on 2017/2/16.
 */

public class IndexActivity extends BaseVehicleActivity implements View.OnClickListener, IResponseListener {
    private TextView tv_username;
    private TextView tv_school;
    private TextView tv_yue;
    private RelativeLayout rl_exit;
    private RelativeLayout rl_eweima;
    private RelativeLayout rl_dingdan;
    private RelativeLayout rl_anquan;
    private RoundImageView iv_headpic;

    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, true);

        //requestPermission(1, Manifest.permission.ACCESS_FINE_LOCATION);
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(1, permissions);

//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_index);
        init();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GET_MYINFO, null, this);
    }

    private void init() {
        tv_username = (TextView) this.findViewById(R.id.tv_username);
        tv_school = (TextView) this.findViewById(R.id.tv_userschool);
        tv_yue = (TextView) this.findViewById(R.id.tv_yue);
        rl_anquan = (RelativeLayout) this.findViewById(R.id.rl_anquan);
        rl_dingdan = (RelativeLayout) this.findViewById(R.id.rl_dingdan);
        rl_eweima = (RelativeLayout) this.findViewById(R.id.rl_erweima);
        rl_exit = (RelativeLayout) this.findViewById(R.id.rl_exit);
        iv_headpic = (RoundImageView) this.findViewById(R.id.iv_headpic);

        rl_anquan.setOnClickListener(this);
        rl_dingdan.setOnClickListener(this);
        rl_eweima.setOnClickListener(this);
        rl_exit.setOnClickListener(this);

//        tv_username.setText(ConfigallCostomerService.getUsername());
        tv_school.setText("来噢客服");
        tv_yue.setOnClickListener(this);
        getAccountStatusInfo();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(1, permissions);
    }

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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_anquan: {
                Intent i = new Intent(IndexActivity.this, SetActivity.class);
                startActivity(i);
            }
            break;
            case R.id.rl_erweima: {
                Intent i = new Intent(IndexActivity.this, ShareActivity.class);
                startActivity(i);
            }
            break;
            case R.id.rl_dingdan: {
                Intent i = new Intent(IndexActivity.this, OrderGrpTransferActivity.class);
                startActivity(i);
            }
            break;
            case R.id.rl_exit: {
                Utils.showDialog(this, "你是否要退出登录？", "确认", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            }
            break;
            case R.id.tv_yue: {
                Intent i = new Intent(IndexActivity.this, AccountManagerActivity.class);
                startActivity(i);
            }
            break;
        }
    }

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_GET_MYINFO:
                try {
                    MyInfoData myInfoData = JSON.parseObject(result, MyInfoData.class);
                    if (myInfoData != null) {
                        if (!myInfoData.StuName.isEmpty())
                            tv_username.setText(myInfoData.StuName);
                        else
                            tv_username.setText(ConfigAll.getUserPhone());
                        if (myInfoData.StuPicURL != null) {//!TextUtils.isEmpty(myInfoData.StuPicURL)
                            ImageLoaderUtil.displayImage(myInfoData.StuPicURL, iv_headpic, R.mipmap.icon_default_headstu);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }


    private void logOut() {
        // 退出自动登录
        Config.spHelper.putString("password", "");
        // 发送退出登录请求
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Username", ConfigallCostomerService.Username);//ConfigAll.getUserERPDanwei()
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGOUT, jsonObject.toString(), this);
        // 重置一些数据
        ConfigAll.reset();
        autoLoginClear();
        Func.stopDataCenter(this.getApplicationContext());
        // 返回首页
//            Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.setClass(this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }

    private void autoLoginClear() {
        BaseSysConfig.spHelper.putString("autoLogin", "[]");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//&&(curIndex==0||curIndex==4)
            CloseAPP();
            return true;
        }
//    if (keyCode == KeyEvent.KEYCODE_BACK&&(curIndex==1||curIndex==3)){
//      setFragment(0, HomeNewFragment.class);
//      tabbar.setVisibility(View.VISIBLE);
//      return true;
//    }
        return false;
    }

    long mBeginTime = 0;
    long mEndTime = 0;

    /**
     * 关闭App
     */
    public void CloseAPP() {
        mEndTime = System.currentTimeMillis();
        if (mEndTime - mBeginTime <= 2000) {
            killApp();
        } else {
            mBeginTime = mEndTime;
            Toast.makeText(this, "再按一次将退出来噢客服", Toast.LENGTH_SHORT).show();
        }
    }

    public void killApp() {
        mylog.e("-------------killApp---------");
//        FlashActivity.msgPush = false;

        ConfigAll.reset();
//        ConfigallStu.reset();

        sendBroadcast(new Intent(BroadCastNameManager.DESTORY));
        ActivityMgr.finishActivity();
        DataCenter.close("LoginActivity.killApp");
        Func.stopDataCenter(getApplicationContext());

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }

    public static class MyInfoData extends BaseData {
        public String StuName;//	String	学员姓名
        public String StuPicURL;//	String	学员头像URL
        public String CarTypeMC;//	String	报考车型名称
        public Double Balance;//	Double	账户余额
        public int TicketCount;//	Integer	来噢劵可用数量

    }
}
