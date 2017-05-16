package com.bofsoft.laio.laiovehiclegps.Activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.bofsoft.laio.common.ActivityMgr;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.BroadCastUtil;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.laiovehiclegps.Config.ConfigallGps;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Tools.DialogUtils;
import com.bofsoft.laio.laiovehiclegps.Tools.Utils;
import com.bofsoft.laio.laiovehiclegps.Widget.PromptDialog;
import com.bofsoft.laio.laiovehiclegps.Widget.WaitDialog;
import com.bofsoft.laio.receiver.SocketConnectReceiver;
import com.bofsoft.laio.service.DataCenter;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.widget.base.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by szw on 2017/2/16.
 */
public abstract class BaseVehicleActivity extends BaseActivity implements IResponseListener {
    protected MyLog mylog = new MyLog(this.getClass());
    protected final String mPageName = getClass().getSimpleName();

    public PromptDialog promptDialog;
    public WaitDialog mWaitDialog;
    // public SharedPreferencesHelper mSp;
    // long newTime = 0;
    // long startTime = 0;
    // long endTime = 0;
    // boolean htTag = false; // 是否在后台运行
    public int retryNum=0;

    protected SocketConnectReceiver socketConnectReceiver; // socket建连状态通知broadcast

    /**
     * 请求权限
     * @param id 请求授权的id 唯一标识即可
     * @param permission 请求的权限
     */
    protected void requestPermission(int id, String[] permission) {

        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            AppOpsManager appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
            int checkOp = appOpsManager.checkOp(AppOpsManager.OPSTR_FINE_LOCATION, Binder.getCallingUid(), getPackageName());
            if (checkOp == AppOpsManager.MODE_IGNORED) {
                // 权限被拒绝了 .
//                ActivityCompat.requestPermissions(BaseVehicleActivity.this, new String[]{permission}, id);
            }

            //检查是否拥有权限

//            int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            boolean needPermission = false;
            for (int i =0;i<permission.length;i++){
                int checkPermission = this.checkCallingOrSelfPermission(permission[i]);
                if (checkPermission != PackageManager.PERMISSION_GRANTED){
                    needPermission = true;
                }
            }
            //int checkPermission = this.checkCallingOrSelfPermission(permission);

            if (needPermission) {
                //弹出对话框接收权限
                ActivityCompat.requestPermissions(BaseVehicleActivity.this, permission, id);
                return;
            } else {
//                allowableRunnable.run();
            }
        } else {
//            allowableRunnable.run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        } else {
            Utils.showDialog(this, "需要授权必要的权限！","设置", "退出程序", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent =  new Intent(Settings.ACTION_MANAGE_ALL_APPLICATIONS_SETTINGS);
//                    startActivity(intent);
                    Intent localIntent = new Intent();
                    localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    localIntent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivity(localIntent);
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ConfigAll.reset();
                    sendBroadcast(new Intent(BroadCastNameManager.DESTORY));
                    ActivityMgr.finishActivity();
                    DataCenter.close("IndexActivity.killApp");
                    Func.stopDataCenter(getApplicationContext());
                    android.os.Process.killProcess(android.os.Process.myPid());
                    System.exit(0);
                }
            });
        }
    }



    @Override
    public void messageBack(int code, int lenght, int tcplenght) {}


    //需要显示 无网络图时，其他activity在此方法调用super  个别页面可能需要单独处理
    @Override
    public void messageBackFailed(int errorCode, String error) {
        Loading.close();
        switch (errorCode) {
            case ErrorCode.NETWORK_NOT_AVAILABLE:
//        showPrompt("当前无可用网络");
                if (error.equals("无法连接到网络,请检查网络设置")){
                    showReloadUi();//显示 无网络可用
                }else{
                    LoadingData(true,0);// 隐藏
                }
                break;
            case ErrorCode.ErrorCode_System:
            case ErrorCode.ErrorCode_DP_Connect_Failed:
                if (!TextUtils.isEmpty(error)) {
                    showPrompt(error);
                }
                break;
            case ErrorCode.ErrorCode_PassWord_Stutent:
            case ErrorCode.ErrorCode_PassWord_Teacher:
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
                ConfigAll.isLogining = false;
                break;
            case ErrorCode.E_NOT_LOGIN:
                if (!TextUtils.isEmpty(error)) {
                    //重新登录
                    //如果记录了登录信息就自动登录
                    //如果自动登录失败跳转到登录
                    //如果没有记录登录信息跳转到登录
//                    Member.autoLogin(this);
                }
                break;
            case CommandCodeTS.CMD_MOBILE_LOGIN:
                //登录失败去登录页面
                showPrompt(error, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        if (!(BaseStuActivity.this instanceof LoginActivity)) {
//                            Intent intent = new Intent(BaseStuActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                            dialog.dismiss();
//                            if (!(BaseStuActivity.this instanceof IndexActivity)) {
//                                finish();
//                            }
//                        }
                    }
                });
                break;
            case ErrorCode.Error_Code_Parse_Exception:
                mylog.e(error);
                break;
            default:
//                mylog.e(BaseStuActivity.this + ">>> errorCode= " + errorCode + ", error= " + error);
                break;
        }
    }

    //只要有显示 无网络图时，activity均需要调用super
    @Override
    public void messageBack(int code, String result) {
        // closeWaitDialog();
        LoadingData(true,0);
        switch (code) {
            // case CommandCode.SEND_SUCESS:
            // break;
            // case CommandCode.SEND_FAILURE:
            // showPrompt("错误提示", "数据发送失败，请稍候再试。", onClickListenerRetry);
            // break;
            case ErrorCode.NETWORK_ERROR:
                // Toast.makeText(BaseActivity.this, "网络错误，请稍候再试。",
                // Toast.LENGTH_LONG).show();
                break;
            case ErrorCode.SOCKET_CONNECT_SUCCESS:
                mylog.e("socket_connect_success！");
                break;
            case ErrorCode.SOCKET_CONNECT_FAILURE:
                mylog.e("socket_connect_failure！");
                ShowReconnDialog();
                break;
            case CommandCodeTS.CMD_GETACCOUNTSTATUS_INTF:
                parseAccountStatusInfo(result);
                break;
            case CommandCodeTS.CMD_STU_GETFAVORITETEAUUIDLIST_INTF:
                parseMyCollection(result);
                break;
            case CommandCodeTS.CMD_GETSTUBASICINFO_INTF:
//                StuAuthInfoData data = JSON.parseObject(result, StuAuthInfoData.class);
//                if (data != null) {
//                    ConfigallStu.stuAuthInfoData = data;
//                }
                break;
            default:
                break;
        }
    }

    private InputMethodManager imm;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState, haveActiobar);
        init(savedInstanceState);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            setTranslucentStatus(true);
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.tc_style2);//通知栏所需颜色
//        }
    }

    @TargetApi(19)
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.onCreate(savedInstanceState, false);
    }

    private void init(Bundle savedInstanceState) {
        ActivityMgr.addActivity(this);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // requestWindowFeature(Window.FEATURE_NO_TITLE); //无标题栏

        if (savedInstanceState != null) {
            ConfigAll.Key = savedInstanceState.getString("Key");
            ConfigAll.Session = savedInstanceState.getByteArray("Session");
            ConfigallGps.GUID = savedInstanceState.getString("GUID");
            ConfigAll.isLogin = savedInstanceState.getBoolean("isLogin");
            ConfigAll.CodeNum = savedInstanceState.getShort("CodeNum");
            ConfigallGps.Username = savedInstanceState.getString("Username");
            ConfigAll.UserUUID = savedInstanceState.getString("UserUUID");
            ConfigallGps.MasterId = savedInstanceState.getString("MasterId");
            ConfigallGps.MasterJiaxiao = savedInstanceState.getString("MasterJiaxiao");
            ConfigallGps.MasterName = savedInstanceState.getString("MasterName");
            ConfigallGps.MasterShowName = savedInstanceState.getString("MasterShowName");
            ConfigallGps.StatusRenZheng = savedInstanceState.getInt("StatusRenZheng");
            ConfigallGps.StatusEmail = savedInstanceState.getInt("StatusEmail");
            ConfigallGps.StatusRenZhengName = savedInstanceState.getString("StatusRenZhengName");
            ConfigallGps.StatusEmailName = savedInstanceState.getString("StatusEmailName");
            ConfigallGps.Email = savedInstanceState.getString("Email");
            ConfigallGps.IDCardNum = savedInstanceState.getString("IDCardNum");
            ConfigallGps.Name = savedInstanceState.getString("Name");
        }
//         myActivityInfo = new MyActivityInfo();
        // myActivityInfo.setHandler(handler);
        // myActivityInfo.setIsActive(true);
        mWaitDialog = new WaitDialog(this);
        // 获取屏幕分辨率
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        ConfigallGps.screenWidth = display.getWidth();
        ConfigallGps.screenHeight = display.getHeight();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastNameManager.Logout);
        intentFilter.addAction(BroadCastNameManager.Msg_List);
        intentFilter.addAction(BroadCastNameManager.Recv_Message);
        intentFilter.addAction(Intent.ACTION_USER_PRESENT);
        registerReceiver(baseReceiver, intentFilter);
        // 注册soket连接状态broadcast
        socketConnectReceiver = BroadCastUtil.registerSocketConnectBroadCast(this, socketHandler);
    }

    private Handler socketHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case ErrorCode.SOCKET_CONNECT_SUCCESS:
                    mylog.i("socket_connect_success！");
                    Loading.close();
                    retryNum=0;
                    // Toast.makeText(BaseStuActivity.this, "网络连接成功!", Toast.LENGTH_SHORT).show();
                    break;
                case ErrorCode.SOCKET_CONNECT_RETRY:
                    mylog.i("socket_connect_retry！");
//                    Toast.makeText(BaseStuActivity.this, "网络连接失败! 正在重连中...", Toast.LENGTH_LONG).show();
                    break;
                case ErrorCode.SOCKET_CONNECT_FAILURE:
                    mylog.i("socket_connect_failure！");
                    Loading.close();
                    // Toast.makeText(BaseStuActivity.this, "网络连接失败! 请检查网络！", Toast.LENGTH_LONG).show();
//          ShowReconnDialog();
                    if (retryNum<3){
//                        NetworkUtil.socketReconnect(BaseStuActivity.this);
                        retryNum++;
                    }
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 窗口列表删除本窗口
        ActivityMgr.deleteActivity(this);

        if (baseReceiver != null) {
            unregisterReceiver(baseReceiver);
            baseReceiver = null;
        }
        if (socketConnectReceiver != null) {
            BroadCastUtil.unRegisterSocketConnectBroadCast(this, socketConnectReceiver);
        }
        if (bundle != null)
            bundle.clear();
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        // htTag = isAppOnForeground();
        // mylog.v("isAppOnForeground()===>>" + isAppOnForeground());
        // newTime = System.currentTimeMillis();
        closeWaitDialog();
        super.onStop();
    }

    public void showToastShortTime(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    public void showToastLongTime(String content) {
        Toast.makeText(this, content, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示提示dialog
     *
     * @param content
     */
    public void showPrompt(String content) {
//        if (promptDialog != null) {
//            if (promptDialog.isShowing()) {
//                promptDialog.close();
//            }
//            promptDialog = null;
//        }
//        promptDialog = new PromptDialog(this, "提示", content, null);

        DialogUtils.showCancleDialog(this, getString(R.string.tip), content);
    }

    public void showPrompt(String title, String content) {
        DialogUtils.showCancleDialog(this, title, content);
    }

    public void showPrompt(String content, DialogInterface.OnClickListener onClickListener) {
        DialogUtils.showCancleDialog(this, getString(R.string.tip), content, "知道了", onClickListener);
    }

    public void showPrompt(String title, String content,
                           DialogInterface.OnClickListener onClickListener) {
        DialogUtils.showCancleDialog(this, title, content, "知道了", onClickListener);
    }

    /**
     * 显示加载dialog
     */

    public void showWaitDialog() {
        Loading.show(this);
    }

    public void showWaitDialog(String text) {
        Loading.show(this, text);
    }

    public void showWaitDialog(int resId) {
        showWaitDialog(getString(resId));
    }

    public void closeWaitDialog() {
        Loading.close();
    }

    /**
     * 显示重连网络提示dialog
     */
    public void ShowReconnDialog() {
//        DialogUtils.showDialog(this, getString(R.string.tip),
//                getString(R.string.network_connect_failure), false, "是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        NetworkUtil.socketReconnect(BaseStuActivity.this);
//                    }
//                }, "否", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                        if (BaseStuActivity.this instanceof FlashActivity) {
//                            finish();
//                        }
//                    }
//                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putCharSequence("Key", ConfigAll.Key);
        outState.putByteArray("Session", ConfigAll.Session);
        outState.putCharSequence("GUID", ConfigallGps.GUID);
        outState.putShort("CodeNum", ConfigAll.CodeNum);
        outState.putBoolean("isLogin", ConfigAll.isLogin);
        outState.putCharSequence("Username", ConfigallGps.Username);
        outState.putCharSequence("UserUUID", ConfigAll.UserUUID);
        outState.putCharSequence("MasterId", ConfigallGps.MasterId);
        outState.putCharSequence("MasterJiaxiao", ConfigallGps.MasterJiaxiao);
        outState.putCharSequence("MasterName", ConfigallGps.MasterName);
        outState.putCharSequence("MasterShowName", ConfigallGps.MasterShowName);
        outState.putInt("StatusRenZheng", ConfigallGps.StatusRenZheng);
        outState.putInt("StatusEmail", ConfigallGps.StatusEmail);
        outState.putCharSequence("StatusRenZhengName", ConfigallGps.StatusRenZhengName);
        outState.putCharSequence("StatusEmailName", ConfigallGps.StatusEmailName);
        outState.putCharSequence("Email", ConfigallGps.Email);
        outState.putCharSequence("IDCardNum", ConfigallGps.IDCardNum);
        outState.putCharSequence("Name", ConfigallGps.Name);
        bundle = outState;
        super.onSaveInstanceState(outState);
    }

    public BroadcastReceiver baseReceiver = new BroadcastReceiver() {

        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastNameManager.Logout)) {
                finish();
            }
            if (intent.getAction().equals(BroadCastNameManager.Msg_List)) {
                Send_GetUnreadSMSList();
            }
            // 消息通知
            if (intent.getAction() == BroadCastNameManager.Recv_Message) {
                try {
                    Bundle bundle = intent.getExtras();
                    if (null != bundle) {
                        int Type = bundle.getInt("Type");
                        final String Key = bundle.getString("Key");

                        switch (Type) {
                            case CommandCodeTS.CMD_MSGTYPE_STEPAHEAD: // 抢单消息 有教练抢单 到需求抢单列表
                                break;
                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    };

    @SuppressLint("NewApi")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (this.getCurrentFocus() != null) {
                if (this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 检查验证码是否正确
     *
     * @param Mobile 手机号
     * @param VerifyCode 验证码
     * @param VerifyType 验证码类型，1-注册账号，2-重置账号登录密码，<br/>
     *        3-重置账号支付密码，4-未验证手机号短信验证码， 5-已验证手机号短信验证码，<br/>
     *        6-ERP账号验证手机号码，7-绑定手机号，8-更换绑定手机号
//     * @param ObjectType 用户类型：0:学员 1:教练
     */
    public void checkAuthCodeCorrect(String Mobile, int VerifyCode, int VerifyType, String dialogTip) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Mobile", Mobile);
            jsonObject.put("VerifyCode", VerifyCode);
            jsonObject.put("VerifyType", VerifyType);
//            jsonObject.put("ObjectType", ConfigallStu.ObjectType);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog(dialogTip);
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETVERIFYCODEISVALID_INTF,
                jsonObject.toString(), this);

    }

    /**
     * 检查登录密码是否正确0x2125
     *
     * @param Password 登录密码
     */
    public void checkLoginPasswordCorrect(String Password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETLOGINPWDISVALID_INTF,
                jsonObject.toString(), this);
    }

    /**
     * 检查支付密码是否正确0x213F1
     *
     * @param Password 支付密码
     */
    public void checkPayPasswordCorrect(String Password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        showWaitDialog();
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETPAYPWDISVALID_INTF,
                jsonObject.toString(), this);
    }

    /**
     * 获取账户认证状态0x2141
     */
    public void getAccountStatusInfo() {
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETACCOUNTSTATUS_INTF, null, this);
    }

    public void parseAccountStatusInfo(String result) {
//        AccountStatusInfoData data = JSON.parseObject(result, AccountStatusInfoData.class);
//        ConfigallStu.accountStatus = data;
    }

    /**
     * 获取已收藏教练UUID列表0x1545
     */
    public void getMyCollection() {
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_STU_GETFAVORITETEAUUIDLIST_INTF, null,
                this);
    }

    /**
     * 解析已收藏的教练UUID列表
     *
     * @param result
     */
    public void parseMyCollection(String result) {
//        MyCollCoachUUIDListData data =
//                JSON.parseObject(result, new TypeReference<MyCollCoachUUIDListData>() {});
//        ConfigallStu.myCollCoachUUIDList = data;
    }

    /**
     * 是否收藏 coachUUID
     *
     * @param coachUUID
     * @return
     */
    public boolean isCollection(String coachUUID) {
        boolean isCollection = false;
//        if (ConfigallStu.myCollCoachUUIDList != null && ConfigallStu.myCollCoachUUIDList.info != null) {
//            if (coachUUID != null) {
//                for (int i = 0; i < ConfigallStu.myCollCoachUUIDList.info.size(); i++) {
//                    if (coachUUID.equalsIgnoreCase(ConfigallStu.myCollCoachUUIDList.info.get(i).MasterUUID)) {
//                        isCollection = true;
//                        break;
//                    }
//                }
//
//            }
//        } else {
//
//        }
        return isCollection;
    }

    /**
     * 移除缓存中收藏的教练
     *
     * @param MasterUUID
     */
    public void removeCoachCollection(String MasterUUID) {
//        if (ConfigallStu.myCollCoachUUIDList.info != null && MasterUUID != null) {
//            for (int i = 0; i < ConfigallStu.myCollCoachUUIDList.info.size(); i++) {
//                if (MasterUUID.equalsIgnoreCase(ConfigallStu.myCollCoachUUIDList.info.get(i).MasterUUID)) {
//                    ConfigallStu.myCollCoachUUIDList.info.remove(i);
//                    break;
//                }
//            }
//        }
    }

    /**
     * 基于百度推送服务 获取未读消息列表0x1013
     */
    protected void Send_GetUnreadSMSList() {
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GET_MSGLIST, null, this);
    }

    /**
     * 分享app
     */
    public void showShare(String shareContent,String PicUrl,String title) {
//        final OnekeyShare oks = new OnekeyShare();
//        String[] content;
//        // 分享时Notification的图标和文字
//        //oks.setNotification(R.drawable.icon_share_success, getString(R.string.app_name));
//        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//        if (title==""){
//            oks.setTitle("来噢学车-我学车我做主");
//        }else{
//            oks.setTitle(title);
//        }
//
//        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//        //屏蔽这里是因为QQ分享 有出现别的软件的广告 未找到原因之前 改为QQ存文本分享
//        if (ConfigallStu.ShareCodeandInfo.inviteurl!=null&&ConfigallStu.isShareActivity==true) {
//            oks.setTitleUrl(ConfigallStu.ShareCodeandInfo.inviteurl);
//        }else if(shareContent.contains("http:")){
//            content=shareContent.split("http:");
//            if (content.length>=2){
//                oks.setTitleUrl("http:"+content[1]);
//            }else{
//                oks.setTitleUrl("www.laio.com.cn");
//            }
//        }else{
//            oks.setTitleUrl("www.laio.com.cn");
//        }
//        // text是分享文本，所有平台都需要这个字段
//        if (shareContent != null || "".equals(shareContent)) {
//            oks.setText(shareContent);
//        } else {
//            oks.setText("小伙伴们，学车神器来了~随时随地，想学就学。来噢学车，点此猛戳下载：http://www.laio.cn/app");
//        }
//        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        final String sharePath = cn.sharesdk.framework.utils.R.getCachePath(this, null);
//        new AssetsWriteToSD(this, "ic_launcher.png", sharePath);
//
//        // url仅在微信（包括好友和朋友圈）中使用
//        if (ConfigallStu.ShareCodeandInfo.inviteurl!=null) {
//            oks.setUrl(ConfigallStu.ShareCodeandInfo.inviteurl);
//        }else if(shareContent.contains("http:")){
//            content=shareContent.split("http:");
//            if (content.length>=2){
//                oks.setUrl("http:" + content[1]);
//            }else{
//                oks.setUrl("www.laio.com.cn");
//            }
//        }else{
//            oks.setUrl("www.laio.com.cn");
//        }
//        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//        oks.setComment("小伙伴们，学车神器来了~随时随地，想学就学。来噢学车，点此猛戳下载~");
//        // site是分享此内容的网站名称，仅在QQ空间使用
//        oks.setSite(getString(R.string.app_name));
//        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//        // oks.setSiteUrl("http://sharesdk.cn");
//        oks.setSiteUrl("http://www.laio.cn/");
//        oks.disableSSOWhenAuthorize();
//        if (PicUrl==""){
//            oks.setImagePath(sharePath+"ic_launcher.png");
//        }else{
//            oks.setImageUrl(PicUrl);
//        }
//
//        //分享Listener
//        oks.setOnShareButtonClickListener(new OnShareButtonClickListener() {
//            @Override
//            public void onClick(View v, List<Object> checkPlatforms) {
//                if (checkPlatforms.size() <= 0) return;
//                if (checkPlatforms.get(0) instanceof WechatMoments) {
//                    oks.setTitle("在来噢平台报名学车要省好几百块，推荐你来报名！");
//                }
//                if (checkPlatforms.get(0) instanceof ShortMessage) {
//                    //短信分享
//                    oks.setImagePath(null);//为短信是不要图片，确保不发彩信
//                }
//                if (checkPlatforms.get(0) instanceof SinaWeibo) {
//                    if (ConfigallStu.ShareCodeandInfo.inviteurl != null && ConfigallStu.isShareActivity == true) {
//                        oks.setTitleUrl(ConfigallStu.ShareCodeandInfo.inviteurl);
//                    } else {
//                        oks.setTitleUrl("www.laio.com.cn");
//                    }
//                    oks.setImagePath(sharePath + "ic_launcher.png");// 确保SDcard下面存在此张图片
//                }
//            }
//        });
//
//        // 启动分享GUI
//        oks.show(this);
    }

}