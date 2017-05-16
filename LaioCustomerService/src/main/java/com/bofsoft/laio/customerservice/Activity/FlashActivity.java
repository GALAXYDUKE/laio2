package com.bofsoft.laio.customerservice.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CrashHandler;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.customerservice.Common.PushmessageUitls;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.sdk.widget.base.Event;


/**
 * Created by szw on 2017/2/16.
 */

public class FlashActivity extends BaseVehicleActivity implements ServiceConnection {

    private TextView tv_timer;
    private MyCountDownTimer mc;

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, haveActiobar);
        setContentView(R.layout.activity_flash);
        Func.bindDataCenter(getApplicationContext(), this);
        init();
    }

    private void init() {
        tv_timer = (TextView) findViewById(R.id.tv_timer);
        tv_timer.setVisibility(View.VISIBLE);
        mc = new MyCountDownTimer(4000, 1000);
        mc.start();
        tv_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mc.cancel();
                Intent localIntent = new Intent(FlashActivity.this, LoginActivity.class);
                startActivity(localIntent);
                finish();
            }
        });
        settingUmengAnalytics();
//        Func.bindDataCenter(getApplicationContext(), (ServiceConnection) this);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                PushmessageUitls.getMetaValue(getApplicationContext(), "api_key"));
    }

    private void settingUmengAnalytics() {
        CrashHandler.getInstance().uploadErrorLog();
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        mylog.e("--------service start-------");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }

    class MyCountDownTimer extends CountDownTimer {
        /**
         * @param millisInFuture    表示以毫秒为单位 倒计时的总数
         *                          <p>
         *                          例如 millisInFuture=1000 表示1秒
         * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
         *                          <p>
         *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            Intent localIntent = new Intent(FlashActivity.this, LoginActivity.class);
            startActivity(localIntent);
            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_timer.setText("跳过(" + (millisUntilFinished / 1000 - 1) + "s)");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mc != null) {
            mc.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        sendBroadcast(new Intent(BroadCastNameManager.DESTORY));
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();
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
}
