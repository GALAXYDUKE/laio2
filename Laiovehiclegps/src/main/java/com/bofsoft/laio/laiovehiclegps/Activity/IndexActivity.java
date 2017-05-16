package com.bofsoft.laio.laiovehiclegps.Activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.ActivityMgr;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.ServerConfigall;
import com.bofsoft.laio.laiovehiclegps.Config.ConfigallGps;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoList;
import com.bofsoft.laio.laiovehiclegps.Fragment.CarListFragment;
import com.bofsoft.laio.laiovehiclegps.Fragment.CreditInvestigationFragment;
import com.bofsoft.laio.laiovehiclegps.Fragment.HomeFragment;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Tools.Order;
import com.bofsoft.laio.laiovehiclegps.Tools.Utils;
import com.bofsoft.laio.laiovehiclegps.Widget.IndexTabBarView;
import com.bofsoft.laio.laiovehiclegps.Widget.MyDrawerLayoutItem;
import com.bofsoft.laio.laiovehiclegps.Widget.MyToolBar;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.utils.Tel;
import com.bofsoft.sdk.widget.base.BaseFragment;
import com.bofsoft.sdk.widget.base.Event;
import com.bofsoft.sdk.widget.tabbar.BaseTabBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by szw on 2017/2/16.
 * 主页面
 */

public class IndexActivity extends BaseVehicleActivity implements View.OnClickListener, IResponseListener, MyToolBar.MyToolBarClickListener {

    public HomeFragment homeFragment;
    public CarListFragment carListFragment;

    public int TempNum = 0;
    private RadioButton radioButton1, radioButton2;
    private CreditInvestigationFragment ciFragment;
    private DrawerLayout drawerLayout;//附带测页的布局
    private RelativeLayout relativeLayout;//测页的布局
    private TextView txt;
    private MyDrawerLayoutItem myDrawerLayoutItem1, myDrawerLayoutItem2, myDrawerLayoutItem;//测页从上到下依次的布局,最后一个是退出登录
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, false);

        //requestPermission(1, Manifest.permission.ACCESS_FINE_LOCATION);
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(1, permissions);

//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_index);

        init();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastNameManager.CONN_CHANGE);
        intentFilter.addAction(BroadCastNameManager.Recv_Message);
        intentFilter.addAction(BroadCastNameManager.Recv_GPS);
        intentFilter.addAction(BroadCastNameManager.Read_Message);
        registerReceiver(broadcastReceiver, intentFilter);

        Send_GetUnreadSMSList();
    }

    private void init() {
        findView();
        setOnClickListener();
        setMyDrawerLayoutItem();
        radioButtonChecked();
    }

    private void radioButtonChecked() {
        if (radioButton1.isChecked()){
            ft = fm.beginTransaction();
            homeFragment = new HomeFragment();
            ft.replace(R.id.index_frameLayout, homeFragment);
            ft.commit();
        }
    }

    private void setOnClickListener() {
        radioButton1.setOnClickListener(this);
        radioButton2.setOnClickListener(this);
        myDrawerLayoutItem1.setOnClickListener(this);
        myDrawerLayoutItem2.setOnClickListener(this);
        myDrawerLayoutItem.setOnClickListener(this);
        txt.setOnClickListener(this);
    }

    private void findView() {
        radioButton1 = (RadioButton) findViewById(R.id.radioBtn1);
        radioButton2 = (RadioButton) findViewById(R.id.radioBtn2);
        txt = (TextView) findViewById(R.id.name);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        relativeLayout = (RelativeLayout) findViewById(R.id.drawerRelativaLayout);
        myDrawerLayoutItem1 = (MyDrawerLayoutItem) findViewById(R.id.myDrawerLayoutItem1);
        myDrawerLayoutItem2 = (MyDrawerLayoutItem) findViewById(R.id.myDrawerLayoutItem2);
        myDrawerLayoutItem = (MyDrawerLayoutItem) findViewById(R.id.myDrawerLayoutItemBottom);
        fm = getSupportFragmentManager();
    }

    public void setMyDrawerLayoutItem() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        myDrawerLayoutItem1.setTextView("关于来噢");
        myDrawerLayoutItem1.setImageView(R.mipmap.about_laio);
        myDrawerLayoutItem2.setTextView("联系来噢");
        myDrawerLayoutItem2.setImageView(R.mipmap.connect_laio);
        myDrawerLayoutItem.setTextView("退出登录");
        myDrawerLayoutItem.setImageView(R.mipmap.quit);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_SETTINGS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE};
        requestPermission(1, permissions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUnReadCnt();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (null == intent)
                return;
            if (intent.getAction().equals(BroadCastNameManager.CONN_CHANGE)) {
                ConnectivityManager manager = (ConnectivityManager) context
                        .getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo gprs = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifi = manager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (!gprs.isConnected() && !wifi.isConnected()) {
                    // network closed
                } else {  // 判断网络是否为 打开
//    			Log.e("tag", gprs.isConnected()+":gprs+wifi:"+wifi.isConnected());
                    Func.startDataCenter(getApplicationContext()); //启动DataCenter服务
                }
            }
            if (intent.getAction().equals(BroadCastNameManager.Recv_Message)
                    || intent.getAction().equals(BroadCastNameManager.Read_Message)) {
                // 设置未读消息标志
                setUnReadCnt();
            }
            if (intent.getAction().equals(BroadCastNameManager.Recv_GPS)) {
                String result = intent.getStringExtra("content");
                Log.e("tag", "index result:" + result);
                GPSInfoList gpsInfoList = JSON.parseObject(result, GPSInfoList.class);
                if (gpsInfoList.InfoList == null || gpsInfoList.InfoList.size() == 0) {

                } else {
                    if (gpsInfoList.InfoList.get(0).Deviceid.length() != 0) {
                        HomeFragment.carListData.InfoList.set(HomeFragment.currentMarkerNum, gpsInfoList.InfoList.get(0));
                    }
                    homeFragment = (HomeFragment) getSupportFragmentManager().getFragments().get(0);
                    homeFragment.changePop();
                }
            }
        }
    };

    public void gotoHomefragment(String gpsDeviceid) {//int markerNum
        //   tabbar.setPosition(0);//跳转车辆监控
        homeFragment = (HomeFragment) getSupportFragmentManager().getFragments().get(0);
        homeFragment.carListFragmentshowPop(gpsDeviceid);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radioBtn1:
                ft = fm.beginTransaction();
                homeFragment = new HomeFragment();
                ft.replace(R.id.index_frameLayout, homeFragment);
                ft.commit();
                break;
            case R.id.radioBtn2:
                ft = fm.beginTransaction();
                ciFragment = new CreditInvestigationFragment();
                ft.replace(R.id.index_frameLayout, ciFragment);
                ft.commit();
                break;
            case R.id.myDrawerLayoutItem1:
                Intent intent = new Intent(this, AboutUsActivity.class);
                intent.putExtra("URL", ServerConfigall.gpsWebHost + "/co/aboutusmobile_zc.html");
                intent.putExtra("AboutUs", true);
                startActivity(intent);
                break;
            case R.id.myDrawerLayoutItem2:
                Utils.showDialog(this, "是否拨打电话？4008804789", "拨打", "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Tel.getInstence().dial(IndexActivity.this, "4008804789");
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                break;
            case R.id.myDrawerLayoutItemBottom:
                logOut(1);
                break;
        }
    }

    private void logOut(int i) {
        if (ConfigallGps.Username != null) {
            // 退出自动登录
//                    autoLoginOut(ConfigallStu.Username);
//                    Config.spHelper.putString("password", "");
            // 发送退出登录请求
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Username", ConfigallGps.Username);//ConfigAll.getUserERPDanwei()
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_MOBILE_LOGOUT, jsonObject.toString(), this);
        }
        // 重置一些数据
        ConfigAll.reset();
        ConfigallGps.reset();
        if (i == 1) {
            autoLoginClear();
            Func.stopDataCenter(this.getApplicationContext());
            // 返回首页
//            Toast.makeText(getActivity(), "注销成功", Toast.LENGTH_LONG).show();
            Intent intent = new Intent();
            intent.setClass(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
        }
    }

    public static void autoLoginClear() {
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
            Toast.makeText(this, "再按一次将退出来噢学车", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String gpsDeviceid = intent.getStringExtra("Deviceid");
        if (gpsDeviceid.length() > 0) {
            homeFragment = (HomeFragment) getSupportFragmentManager().getFragments().get(0);
//            homeFragment.carListFragmentshowPop(gpsDeviceid);
            homeFragment.reFresh(gpsDeviceid);
        }

    }

    @Override
    public void onLeftClick() {
        drawerLayout.openDrawer(relativeLayout);
    }

    @Override
    public void onRightClick() {
        Intent intent = new Intent(this, SmsActivity.class);
        startActivity(intent);
    }
    @Override
    protected void setTitleFunc() {
        setTitle("来噢租车管理", 24, Color.BLACK);
    }

    // 设置未读消息标志
    private void setUnReadCnt() {
        // 先更新数据
        // Msg.getUnReadCnt(this);
        SmsActivity.getMsgCount(this);
        Order.getNewCnt(this);
        // 首页左下角图标上显示的是抢单消息之外的全部消息
//        if (tabs.size() > 0) {
//            if (SmsActivity.UnReadCount_All - SmsActivity.UnReadCount_Demand > 0) {
//                ((IndexTabBarView) tabs.get(0)).setCnt(SmsActivity.UnReadCount_All
//                        - SmsActivity.UnReadCount_Demand);
//            } else {
//                ((IndexTabBarView) tabs.get(0)).setCnt(0);
//            }
//        }
        // 首页右上角图标上显示的是抢单消息之外的全部消息
//        if (fragments[0] != null) {
//            if (SmsActivity.UnReadCount_All - SmsActivity.UnReadCount_Demand > 0) {
//                ((HomeFragment) fragments[0]).setMsgCnt(SmsActivity.UnReadCount_All
//                        - SmsActivity.UnReadCount_Demand);
//            } else {
//                ((HomeFragment) fragments[0]).setMsgCnt(0);
//            }
//        }
    }

    @Override
    protected void setActionBarButtonFunc() {
        View viewL = getLayoutInflater().inflate(R.layout.actionbar_leftview, null);
        addLeftView(viewL);
        View viewR = getLayoutInflater().inflate(R.layout.actionbar_rightview, null);
        addLeftView(viewR);
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }

}
