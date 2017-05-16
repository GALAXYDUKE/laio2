package com.bofsoft.laio.laiovehiclegps.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.Config.Config;
import com.bofsoft.laio.laiovehiclegps.Widget.Widget_CountView;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.database.MsgAdaper;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

/**
 * Created by szw on 2017/2/21.
 */

public class SmsActivity extends BaseVehicleActivity{

    MsgAdaper msgAdapter;
    RelativeLayout sms_class_layTeacherMsg;
    RelativeLayout sms_class_layOrder;
    RelativeLayout sms_class_laySchoolMsg;
    RelativeLayout sms_class_layNoticeMsg;
    RelativeLayout sms_class_promotion;//优惠促销

    Widget_CountView sms_class_countViewTeacher;
    Widget_CountView sms_class_countViewOrder;
    Widget_CountView sms_class_countViewSchool;
    Widget_CountView sms_class_countViewNotice;
    Widget_CountView sms_class_countpromotion;

    TextView sms_class_txtLastTeacherMsg;
    TextView sms_class_txtLastOrderMsg;
    TextView sms_class_txtLastSchoolMsg;
    TextView sms_class_txtLastNoticeMsg;
    TextView sms_class_txtpromotion;

    public static int UnReadCount_All = 0; // 全部消息
    public static int UnReadCount_Msg = 0; // 教练学员互发消息
    public static int UnReadCount_Order = 0; // 订单消息
    public static int UnReadCount_Jxtz = 0; // 驾校通知
    public static int UnReadCount_Laio = 0; // 来噢消息
    public static int UnReadCount_Demand = 0; // 需求抢单
    public static int UnReadCount_Promotion=0;// 优惠促销


    @Override
    public void messageBack(int code, String result) {
//        Log.e("tag","result:"+result);
        switch (code) {
            case CommandCodeTS.CMD_GET_MSGLIST:

                break;
            default:
                super.messageBack(code, result);
                break;
        }

    }

    @Override
    public void messageBackFailed(int errorCode, String error) {
        super.messageBackFailed(errorCode, error);
//        Log.e("tag","errorCode:"+errorCode+" error:"+error);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_sms);
        // db = new MsgAdaper(this);
        msgAdapter = MsgAdaper.getInstance(getApplicationContext());
        sms_class_layTeacherMsg = (RelativeLayout) findViewById(R.id.sms_class_layTeacherMsg);
        sms_class_layOrder = (RelativeLayout) findViewById(R.id.sms_class_layOrder);
        sms_class_laySchoolMsg = (RelativeLayout) findViewById(R.id.sms_class_laySchoolMsg);
        sms_class_layNoticeMsg = (RelativeLayout) findViewById(R.id.sms_class_layNoticeMsg);
        sms_class_promotion=(RelativeLayout) findViewById(R.id.sms_class_promotion);

        sms_class_countViewTeacher = (Widget_CountView) findViewById(R.id.sms_class_countViewTeacher);
        sms_class_countViewOrder = (Widget_CountView) findViewById(R.id.sms_class_countViewOrder);
        sms_class_countViewSchool = (Widget_CountView) findViewById(R.id.sms_class_countViewSchool);
        sms_class_countViewNotice = (Widget_CountView) findViewById(R.id.sms_class_countViewNotice);
        sms_class_countpromotion = (Widget_CountView) findViewById(R.id.sms_class_countpromotion);

        sms_class_txtLastTeacherMsg = (TextView) findViewById(R.id.sms_class_txtLastTeacherMsg);
        sms_class_txtLastOrderMsg = (TextView) findViewById(R.id.sms_class_txtLastOrderMsg);
        sms_class_txtLastSchoolMsg = (TextView) findViewById(R.id.sms_class_txtLastSchoolMsg);
        sms_class_txtLastNoticeMsg = (TextView) findViewById(R.id.sms_class_txtLastNoticeMsg);
        sms_class_txtpromotion = (TextView) findViewById(R.id.sms_class_txtpromotion);

        sms_class_layTeacherMsg.setOnClickListener(onClickListener);
        sms_class_layOrder.setOnClickListener(onClickListener);
        sms_class_laySchoolMsg.setOnClickListener(onClickListener);
        sms_class_layNoticeMsg.setOnClickListener(onClickListener);
        sms_class_promotion.setOnClickListener(onClickListener);

        sms_class_layTeacherMsg.setOnTouchListener(Func.onTouchListener);
        sms_class_layOrder.setOnTouchListener(Func.onTouchListener);
        sms_class_laySchoolMsg.setOnTouchListener(Func.onTouchListener);
        sms_class_layNoticeMsg.setOnTouchListener(Func.onTouchListener);
        sms_class_promotion.setOnTouchListener(Func.onTouchListener);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastNameManager.Recv_Message);
        intentFilter.addAction(BroadCastNameManager.Read_Message);
        registerReceiver(broadcastReceiver, intentFilter);
//        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GET_MSGLIST, null, this);
//        Log.e("tag","1013");

    }
    @Override
    protected void onResume() {
        super.onResume();
        setCountView();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @SuppressWarnings("resource")
    public static void getMsgCount(Context context) {
        if (!ConfigAll.isLogin || null == ConfigAll.UserUUID) {
            // 全部消息
            UnReadCount_All = 0;
            // 教练学员互发消息
            UnReadCount_Msg = 0;
            // 订单消息
            UnReadCount_Order = 0;
            // 驾校通知
            UnReadCount_Jxtz = 0;
            // 来噢消息
            UnReadCount_Laio = 0;
            // 需求抢单
            UnReadCount_Demand = 0;
            // 优惠促销
            UnReadCount_Promotion =0;
            return;
        }
        Cursor cursor=null;
        // 教练学员互发消息
        try{
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_COACHMSG)});
            cursor.moveToFirst();
            UnReadCount_Msg = cursor.getInt(0);
            // 订单消息
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_MYORDER)});
            cursor.moveToFirst();
            UnReadCount_Order = cursor.getInt(0);
            // 优惠促销
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_PROMOTION)});
            cursor.moveToFirst();
            UnReadCount_Promotion = cursor.getInt(0);
            // 驾校通知
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_JXYW)});
            cursor.moveToFirst();
            UnReadCount_Jxtz = cursor.getInt(0);
            // 来噢消息
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_LAIOMSG)});
            cursor.moveToFirst();
            UnReadCount_Laio = cursor.getInt(0);
            // 需求抢单
            cursor =
                    MsgAdaper.getInstance(context).rawQuery(
                            "select count(*) from MsgList where ToM=? and type=? and isRead=0",
                            new String[] {ConfigAll.UserUUID, String.valueOf(CommandCodeTS.CMD_MSGTYPE_STEPAHEAD)});
            cursor.moveToFirst();
            UnReadCount_Demand = cursor.getInt(0);

            // 全部消息
            UnReadCount_All = UnReadCount_Msg + UnReadCount_Order + UnReadCount_Jxtz + UnReadCount_Laio+UnReadCount_Promotion;
        }finally{
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @SuppressWarnings("resource")
    public void setCountView() {
        if (ConfigAll.UserUUID != null) {
            // 发送人的类型： 1：驾校通知, 2：系统公告,3：教练信息. 4：学员信息 11：订单消息
            Cursor cursor=null;
            try{
                msgAdapter = MsgAdaper.getInstance(getApplicationContext());
                // 教练信息总数
                cursor =
                        msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=? and isRead=0",
                                new String[] {ConfigAll.UserUUID, "3"});
                cursor.moveToFirst();
                sms_class_countViewTeacher.SetCountText(cursor.getString(0));
                // 最后一条消息
                cursor =
                        msgAdapter.rawQuery("select * from MsgList where ToM=? and type=? order by _id desc",
                                new String[] {ConfigAll.UserUUID, "3"});
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    sms_class_txtLastTeacherMsg.setText(cursor.getString(cursor.getColumnIndex("ShowName"))+" "+cursor.getString(cursor.getColumnIndex("Msg")));
                }

                // 订单信息总数
                cursor =
                        msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=? and isRead=0",
                                new String[] {ConfigAll.UserUUID, "11"});
                cursor.moveToFirst();
                sms_class_countViewOrder.SetCountText(cursor.getString(0));
                // 最后一条消息
                cursor =
                        msgAdapter.rawQuery("select * from MsgList where ToM=? and type=? order by _id desc",
                                new String[] {ConfigAll.UserUUID, "11"});
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    sms_class_txtLastOrderMsg.setText(cursor.getString(cursor.getColumnIndex("Msg")));
                }

                // 驾校通知总数
                cursor =
                        msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=? and isRead=0",
                                new String[] {ConfigAll.UserUUID, "1"});
                cursor.moveToFirst();
                sms_class_countViewSchool.SetCountText(cursor.getString(0));
                // 最后一条消息
                cursor =
                        msgAdapter.rawQuery("select * from MsgList where ToM=? and type=? order by Time desc",
                                new String[] {ConfigAll.UserUUID, "1"});
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    sms_class_txtLastSchoolMsg.setText(cursor.getString(cursor.getColumnIndex("Msg")));
                }

                // 系统公告总数
                cursor =
                        msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=? and isRead=0",
                                new String[] {ConfigAll.UserUUID, "2"});
                cursor.moveToFirst();
                sms_class_countViewNotice.SetCountText(cursor.getString(0));
                // 最后一条消息
                cursor =
                        msgAdapter.rawQuery("select * from MsgList where ToM=? and type=? order by Time desc",
                                new String[] {ConfigAll.UserUUID, "2"});
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    sms_class_txtLastNoticeMsg.setText(cursor.getString(cursor.getColumnIndex("Msg")));
                }
                // 优惠促销总数
                cursor =
                        msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=? and isRead=0",
                                new String[] {ConfigAll.UserUUID, "13"});
                cursor.moveToFirst();
                sms_class_countpromotion.SetCountText(cursor.getString(0));
                // 最后一条消息
                cursor =
                        msgAdapter.rawQuery("select * from MsgList where ToM=? and type=? order by Time desc",
                                new String[] {ConfigAll.UserUUID, "13"});
                cursor.moveToFirst();
                if (cursor.getCount() != 0) {
                    sms_class_txtpromotion.setText(cursor.getString(cursor.getColumnIndex("Msg")));
                }
            }
            finally{
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
    }

    private int getMsgCount(String Type) {
        msgAdapter = MsgAdaper.getInstance(getApplicationContext());
        try {
            Cursor cursor =
                    msgAdapter.rawQuery("select count(*) from MsgList where ToM=? and type=?", new String[] {
                            ConfigAll.UserUUID, Type});
            if (cursor.moveToFirst()) {
                return cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.sms_class_layTeacherMsg:
                    intent = new Intent(getApplicationContext(), SmsWarningActivity.class);
                    intent.putExtra("Type", 3);
                    intent.putExtra("ShowName", "报警消息");
                    startActivity(intent);
                    break;
                case R.id.sms_class_layOrder:
//                    intent = new Intent(getApplicationContext(), SmsOrderListActivity.class);
//                    intent.putExtra("Type", CommandCodeTS.CMD_MSGTYPE_MYORDER);
//                    intent.putExtra("ShowName", "订单消息");
//                    startActivity(intent);
                    break;
                case R.id.sms_class_laySchoolMsg:
                    // if (getMsgCount("1") > 0) {
                    intent = new Intent(getApplicationContext(), SmsWarningActivity.class);
                    intent.putExtra("Type", CommandCodeTS.CMD_MSGTYPE_JXYW);
                    intent.putExtra("ShowName", "业务消息");
                    startActivity(intent);
                    // }
                    break;
                case R.id.sms_class_layNoticeMsg:
                    // if (getMsgCount("2") > 0) {
                    intent = new Intent(getApplicationContext(), SmsWarningActivity.class);
                    intent.putExtra("Type", CommandCodeTS.CMD_MSGTYPE_LAIOMSG);
                    intent.putExtra("ShowName", "系统公告");
                    startActivity(intent);
                    // }
                    break;
                case R.id.sms_class_promotion:
//                    intent = new Intent(getApplicationContext(), SmsPromotionActivity.class);
//                    intent.putExtra("Type", 13);
//                    intent.putExtra("ShowName", "优惠促销");
//                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    };

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(BroadCastNameManager.Recv_Message)
                    || intent.getAction().equalsIgnoreCase(BroadCastNameManager.Read_Message)) {
                setCountView();
            }
        }
    };

    @Override
    protected void setTitleFunc() {
        setTitle("通知消息");
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
