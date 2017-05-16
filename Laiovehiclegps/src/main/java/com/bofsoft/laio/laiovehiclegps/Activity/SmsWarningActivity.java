package com.bofsoft.laio.laiovehiclegps.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.database.DBCacheHelper;
import com.bofsoft.laio.database.MsgAdaper;
import com.bofsoft.laio.laiovehiclegps.Adapter.SmsWarningAdapter;
import com.bofsoft.laio.laiovehiclegps.Config.Config;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Widget.LoadMoreRecyclerView;
import com.bofsoft.sdk.widget.base.Event;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by szw on 2017/3/8.
 */

public class SmsWarningActivity extends BaseVehicleActivity{
    MsgAdaper msgAdaper;
    String FromM = "0";
    String ShowName = "";
    int Type; // 1：业务消息(教练)/驾校通知(学员) 2：系统公告 3：教练信息 4：学员信息 10：驾校通知 11订单消息
    //	ListView sms_content_listview;
    private List<Map<String, Object>> mData;
    public static  int mapsize=0;
    public static final int num=10;//每次加载的数量
    public static int Times=0;//加载次数
    public boolean isMore=false;//是否有更多数据
    Date showTime = Func.strToDate("0000-00-00 00:00");// 显示的时间

    private SmsWarningAdapter smsWarningAdapter;
    private LoadMoreRecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_smswarning);
        Intent intent = getIntent();
//        FromM = intent.getStringExtra("FromM");
        ShowName = intent.getStringExtra("ShowName");
        Type = intent.getIntExtra("Type", 0);
        if (0 == Type) {
            mylog.e("Type[0] is incorrect!");
        }
        setTitle(ShowName);
        // 更新已读状态，发送已读广播
        msgAdaper = MsgAdaper.getInstance(getApplicationContext());

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BroadCastNameManager.Recv_Message);
        registerReceiver(broadcastReceiver, intentFilter);

        mData = new ArrayList<Map<String, Object>>();
        mData.removeAll(mData);
        getDataFromDB(0,Times);

        recyclerView = (LoadMoreRecyclerView)findViewById(R.id.recyclerview_list);
        recyclerView.setHasFixedSize(true);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                tempGpsInfoList.InfoList.clear();
//                tempGpsInfoList.InfoList.addAll(HomeFragment.carListData.InfoList);
                swipeRefreshLayout.setRefreshing(false);
                mData.removeAll(mData);
                Times = 0;
                getDataFromDB(0, Times);
                smsWarningAdapter.setData(mData);
                recyclerView.setAutoLoadMoreEnable(isMore);
                smsWarningAdapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        smsWarningAdapter = new SmsWarningAdapter(mData);
        recyclerView.setAdapter(smsWarningAdapter);
        recyclerView.setAutoLoadMoreEnable(isMore);
        recyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        getDataFromDB(0, Times);
                        smsWarningAdapter.addDatas(mData,Times);
                        recyclerView.notifyMoreFinish(isMore);
                    }
                }, 1000);
            }
        });
        smsWarningAdapter.setOnItemClickListener(new SmsWarningAdapter.OnRecyclerViewItemClickListener () {
            @Override
            public void onItemClick(View view, Map<String, Object> data) {
                String[] temp=data.get("Key").toString().split("&");

                String Deviceid="";
                if (temp.length>1){
                    String[] temp1=temp[1].split("=");
                    if (temp1.length>1){
                        Deviceid=temp1[1];
                    }else{
                        return;
                    }
                }else{
                    return;
                }
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setClass(SmsWarningActivity.this,IndexActivity.class);
                intent.putExtra("Deviceid",Deviceid);
                startActivity(intent);
                finish();
            }
        });
        smsWarningAdapter.notifyDataSetChanged();

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Times=0;
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 重新从数据库读取消息
     *
     * @param getType
     *            0 其他 1 发送 2 发送成功
     */
    private void getDataFromDB(int getType,int times) {
        showTime = Func.strToDate("0000-00-00 00:00");
//		mData.removeAll(mData);
        Map<String, Object> map;
        Cursor cursor = null;
        msgAdaper = MsgAdaper.getInstance(getApplicationContext());
        // 1：业务消息(教练)/驾校通知(学员) 2：系统公告 3：教练信息 4：学员信息 10：驾校通知 11订单消息
        switch (Type) {
            case CommandCodeTS.CMD_MSGTYPE_JXYW:
            case CommandCodeTS.CMD_MSGTYPE_LAIOMSG:
                // 订单消息
                cursor = msgAdaper.rawQuery("select * from "
                                + DBCacheHelper.tableMsg
                                + " where ToM like ? and Type=? order by _id desc limit ?,?",
                        new String[] { ConfigAll.UserUUID, String.valueOf(Type),String.valueOf(times*num),String.valueOf(num)});
                break;

            case 3:
                // 订单消息
                cursor = msgAdaper.rawQuery("select * from "
                                + DBCacheHelper.tableMsg
                                + " where ToM like ? and Type=? order by _id desc limit ?,?",
                        new String[] { ConfigAll.UserUUID, String.valueOf(Type),String.valueOf(times*num),String.valueOf(num)});
                break;
        }

        try {
            while (cursor.moveToNext()) {
                map = new HashMap<String, Object>();
                map.put("Time", cursor.getString(cursor.getColumnIndex("Time"))); // 服务器的时间
                map.put("MsgID", cursor.getInt(cursor.getColumnIndex("MsgID"))); // messageID
                map.put("Msg", cursor.getString(cursor.getColumnIndex("Msg"))); // 消息内容
                map.put("LocalTime",
                        cursor.getString(cursor.getColumnIndex("LocalTime")));// 本地时间
                map.put("FromM",
                        cursor.getString(cursor.getColumnIndex("FromM")));// 发送者
                map.put("ToM", cursor.getString(cursor.getColumnIndex("ToM")));// 接收者
                map.put("Type", cursor.getInt(cursor.getColumnIndex("Type")));// 类型
                map.put("isSend",
                        cursor.getInt(cursor.getColumnIndex("isSend")));// 是否发送成功
                map.put("ShowName",
                        cursor.getString(cursor.getColumnIndex("ShowName")));// 标题
                map.put("Key", cursor.getString(cursor.getColumnIndex("Key")));// 订单处用与现实订单标号，其他的需要触发动作的值，具体值根据Type类型确定
                if (cursor.getInt(cursor.getColumnIndex("Type")) == 3) {
                    if ((Func.strToDate(
                            cursor.getString(cursor.getColumnIndex("Time")))
                            .getTime() - showTime.getTime()) / 1000 / 60 > 5) {
                        map.put("show", true);
                        showTime = Func.strToDate(cursor.getString(cursor
                                .getColumnIndex("Time")));
                    } else {
                        map.put("show", false);
                    }
                } else if (cursor.getInt(cursor.getColumnIndex("Type")) == 5) {
                    if ((Func
                            .strToDate(
                                    cursor.getString(cursor
                                            .getColumnIndex("LocalTime")))
                            .getTime() - showTime.getTime()) / 1000 / 60 > 5) {
                        map.put("show", true);
                        showTime = Func.strToDate(cursor.getString(cursor
                                .getColumnIndex("LocalTime")));
                    } else {
                        map.put("show", false);
                    }
                } else
                    map.put("show", true);

                mData.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapsize=mData.size();
        isMore = mapsize >= (times + 1) * num;

        // 更新已读状态，发送已读广播
        if (Type == CommandCodeTS.CMD_MSGTYPE_COACHMSG) {
            msgAdaper
                    .exeSQL("update "
                                    + DBCacheHelper.tableMsg
                                    + " set isRead=1 where FromM like ? and ToM like ? and Type=?",
                            new String[] { FromM, ConfigAll.UserUUID,
                                    String.valueOf(Type) });
        } else {
            msgAdaper.exeSQL("update " + DBCacheHelper.tableMsg
                            + " set isRead=1 where ToM like ? and Type=?",
                    new String[] { ConfigAll.UserUUID, String.valueOf(Type) });
        }

        if (cursor != null) {
            cursor.close();
        }
        Times++;
        Intent i = new Intent(BroadCastNameManager.Read_Message);
        sendBroadcast(i);
    }
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(
                    BroadCastNameManager.Recv_Message)) {
//				getDataFromDB(0);
                smsWarningAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    protected void setTitleFunc() {

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
