package com.bofsoft.laio.laiovehiclegps.Activity;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.CrashHandler;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.laiovehiclegps.Common.PushmessageUitls;
import com.bofsoft.laio.laiovehiclegps.DataClass.CheckVersionUpdate;
import com.bofsoft.laio.laiovehiclegps.DataClass.ConfigallTea;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;


/**
 * Created by szw on 2017/2/16.
 */

public class FlashActivity extends BaseVehicleActivity implements ServiceConnection {

    private final int DOWNLOAD_FINISH = 100;
    private final int DOWNLOAD_PROGRESS = 101;
    private final int DOWNLOAD_FAILURE = 102;

    public static boolean msgPush;
    private ProgressDialog progressDialog;
    private CheckVersionUpdate checkVersionUpdate;
    private String softVersion;
    private String apkPath;
    public static boolean autoLogin;
    public static int width;
    public static int height;

    private TextView tv_timer;
    private MyCountDownTimer mc;

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        Log.e("tag", "检查版本信息" + result);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, haveActiobar);
        setContentView(R.layout.activity_flash);
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
        Func.bindDataCenter(getApplicationContext(), (ServiceConnection) this);
        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                PushmessageUitls.getMetaValue(getApplicationContext(), "api_key"));
    }

    private void settingUmengAnalytics() {
        CrashHandler.getInstance().uploadErrorLog();
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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        checkVersion();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        checkVersion();
    }


    private void checkVersion() {
        mylog.i("------------检查版本------------");
        CheckVersionUpdate update = new CheckVersionUpdate();
        update.setSoftVersion(softVersion);
        update.setSystemType(ConfigallTea.SystemType);
        update.setSystemVersion(android.os.Build.VERSION.RELEASE);
        update.setPhoneManufacturers(android.os.Build.MANUFACTURER);
        update.setPhoneModel(android.os.Build.MODEL);
        update.setGUID(ConfigallTea.getGUID(this));
        update.setAppType(0);

        String content = null;
        try {
            content = update.getCheckVersionUpgrade();
            if (content == null || content.equals("")) {
                mylog.e("send JSON：" + "is empty!");
                return;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_CHECK_VER, content, this);
//    saveFile(content,"content.txt");
        // myHandler.sendEmptyMessageDelayed(Chk_Version_TimeOut, 6 * 1000);
    }

    public static void saveFile(String str, String filename) {
        String filePath = null;
        boolean hasSDCard = Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if (hasSDCard) { // SD卡根目录的hello.text
            filePath = Environment.getExternalStorageDirectory().toString() + File.separator + filename;
        } else  // 系统下载缓存根目录的hello.text
            filePath = Environment.getDownloadCacheDirectory().toString() + File.separator + filename;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(str.getBytes());
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
