package com.bofsoft.laio.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.util.LongSparseArray;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.data.index.MsgListData;
import com.bofsoft.laio.database.MsgAdaper;
import com.bofsoft.laio.database.MsgJxtzAdapter;
import com.bofsoft.laio.myclass.MyActivityInfo;
import com.bofsoft.laio.tcp.Client;
import com.bofsoft.laio.tcp.Clientgps;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.laio.tcp.ISocketResponse;
import com.bofsoft.laio.tcp.Packet;
import com.bofsoft.laio.tcp.TcpHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

public class DataCenter extends Service {
    protected MyLog mylog = new MyLog(this.getClass());
    private static DataCenter dataCenter;
    private IBinder binder = new DataCenter.LocalBinder();
    // Activity的哈希表
    static volatile LongSparseArray<MyActivityInfo> activityList = new LongSparseArray<MyActivityInfo>();
    // Socket类实例
    public static Client client = null;
    public static Clientgps clientgps = null;
    static LongSparseArray<Integer> msgSendList = new LongSparseArray<Integer>();
    private final String GPSPageName = "com.bofsoft.laio.laiovehiclegps";
    private static boolean isGPSPackage = false;
    public MsgListData msgListData;


    // 静态变量
    public static DataCenter getInstance() {
        return dataCenter;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }


    // 定义内容类继承Binder
    public class LocalBinder extends Binder {
        // 返回本地服务
        public DataCenter getService() {
            return DataCenter.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isGPSPackage = Func.getPackageName(this).equalsIgnoreCase(GPSPageName);
        dataCenter = this;
        client = Client.getInstance(getApplicationContext(), socketListener);
        client.open("DataCenter.onCreate");
        if (isGPSPackage) {
            clientgps = Clientgps.getInstance(getApplicationContext(), socketListener);
            clientgps.open("DataCenter.onCreate");
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    // 添加到list中
    public void addActivityList(MyActivityInfo myAcivityInfo) {
        long key = myAcivityInfo.getKey();
        activityList.put(key, myAcivityInfo);
    }

    // 获取Activity
    public static MyActivityInfo getActivity(long key) {
        return activityList.get(key);
    }

    // 发送消息主函数 对发送的内容进行加密并将String content转化为byte[] content
    public void sendContent(short commandid, String content, Integer msg_id,
                            IResponseListener listener) {
        MyActivityInfo myActivityInfo = new MyActivityInfo();
        long key;
        byte[] contentbyte = null;
        Packet packet = new Packet();
        int index;

        if (content != null) {
            contentbyte = content.getBytes();
        }

        mylog.i("发送的数据：0x" + Integer.toHexString(commandid) + "|" + content);
        index = packet.initPack(commandid, contentbyte);
        myActivityInfo.setmListener(listener);

        // 计算哈希值
        key = index * 0x10000 + commandid;
        myActivityInfo.setKey(key);

        // 添加到Handle列表
        addActivityList(myActivityInfo);

        // 发送,传入 handler是处理发送成功的消息
        if (handler == null)
            return;
        if (client == null)
            return;

        client.send(handler, key, packet, false);

        // 如果传输了短消息编号的就把短消息编号保存起来
        if (msg_id != -1) {
            msgSendList.put(key, msg_id);
        }
    }

    // 发送消息主函数 对发送的内容进行加密并将String content转化为byte[] content
    // 发送到GPS
    public void sendContentgps(short commandid, String content, Integer msg_id,
                               IResponseListener listener) {
        MyActivityInfo myActivityInfo = new MyActivityInfo();
        long key;
        byte[] contentbyte = null;
        Packet packet = new Packet();
        int index;

        if (content != null) {
            contentbyte = content.getBytes();
        }
        index = packet.initPack(commandid, contentbyte);

        myActivityInfo.setmListener(listener);

        // 计算哈希值
        key = index * 0x10000 + commandid;
        myActivityInfo.setKey(key);

        // 添加到Handle列表
        addActivityList(myActivityInfo);

        // 发送,传入 handler是处理发送成功的消息
        if (handler == null)
            return;
        if (clientgps == null)
            return;

        clientgps.send(handler, key, packet, false);

        // 如果传输了短消息编号的就把短消息编号保存起来
        if (msg_id != -1) {
            msgSendList.put(key, msg_id);
        }
    }

    public static void close(String fromwhere) {
        if (client != null)
            client.close(fromwhere);
        if (isGPSPackage && clientgps != null)
            clientgps.close(fromwhere);
    }

    public void open(String fromwhere) {
        client.open(fromwhere);
        if (isGPSPackage)
            clientgps.open(fromwhere);
    }

    public void retry() {
        client.retry();
        if (isGPSPackage)
            clientgps.retry();
    }

    static class MHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            Handler handler = null;
            switch (message.what) {
                case 1000000000:
                case 1000000001:
                case ErrorCode.NETWORK_ERROR:
                    // 网络错误中的connect错误，由于还没有调用send函数，没有记录对应的key和handle，只能在表中查找活动的handler
                    for (int i = 0; i < activityList.size(); i++) {
                        MyActivityInfo myActivityInfo = activityList.get(i);
                        if (null != myActivityInfo && myActivityInfo.getIsActive()) {
                            handler = myActivityInfo.getHandler();
                        }
                    }
                    if (null != handler) {
                        Message msg = handler.obtainMessage();
                        msg.what = message.what;
                        msg.sendToTarget();
                    }
                    break;
                case ErrorCode.SEND_SUCESS:
                case ErrorCode.SEND_FAILURE:
                    long key = (Long) message.obj;
                    short commandid = (short) (key % 0x10000);
                    switch (commandid) {
                        case CommandCodeTS.CMD_CLIENT_SENDMSG:
                            // 针对短消息发送的单独处理
                            MsgAdaper.getInstance(dataCenter.getApplicationContext()).exeSQL(
                                    "update MsgList set isSend=1 where _id=?",
                                    new String[]{Integer.toString(msgSendList.get(key))});
                            break;
                        default:
                            break;
                    }

                    MyActivityInfo myActivityInfo = getActivity(key);

                    // 向发送窗口发送消息
                    if (null != myActivityInfo) {
                        handler = myActivityInfo.getHandler();
                        Message msg = handler.obtainMessage();
                        msg.what = message.what;
                        msg.obj = Long.toString(key);
                        msg.sendToTarget();
                    }
                    break;
            }
        }
    }

    private MHandler handler = new MHandler();

    // socket返回的数据处理
    private ISocketResponse socketListener = new ISocketResponse() {
        @Override
        public void onSocketResponse(byte[] data) {
            // 数据总长度小于或等于头长度直接返回
            int dataLen = data.length;
            if (dataLen <= TcpHeader.getHeaderlen()) {
                mylog.e("数据总长度小于或等于头长度直接返回");
                retry();
                return;
            }

            // 包头名不正确直接返回
            String headerName = new String(data, 0, 4);
            if (!headerName.equalsIgnoreCase(ConfigAll.headerName)) {
                mylog.e("包头名不正确直接返回");
                retry();
                return;
            }

            // 包长度不正确直接返回
            int length = Func.byteArrayToInt(data, 8);
            if (dataLen != length) {
                mylog.e("包长度不正确直接返回");
                retry();
                return;
            }

            // 解包
            Packet packet = new Packet();
            try {
                packet.unpack(data);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 解密数据内容
            // 获取commandid和index
            TcpHeader tcpHeader = packet.getTcpHeader();
            Short commandid = tcpHeader.getCommendid();
            String content = new String(packet.getPacket());
            String printContent = content;
            if (printContent.length() > 1024) {
                printContent = printContent.substring(0, 1024) + "……";
            }
            mylog.i("收到的数据为：0x" + Integer.toHexString(commandid) + "|" + printContent);
            // 针对短消息的单独处理
            switch (commandid) {
                case CommandCodeTS.CMD_SERVER_SENDMSG:
                    // 先根据MsgID确定是否已有该消息，若已有，则过；若无，则保存，并通知收到消息
                    try {
                        // 解析数据
                        JSONObject js = new JSONObject(content);
                        int MsgID = js.getInt("MsgID");
                        String FromM = js.getString("FromM");
                        String Msg = js.getString("Msg");
                        String Time = js.getString("Time");
                        Integer Type = js.getInt("Type");
                        String Key = js.getString("Key");
                        String ShowName = js.getString("ShowName");

                        // 保存未读消息
                        if (ConfigAll.UserUUID != null) {
                            long rowId =
                                    MsgAdaper.getInstance(getApplicationContext()).replace(MsgID, FromM, Msg, Time,
                                            Type, Key, ShowName, 0, ConfigAll.UserUUID, Func.formattime(new Date(), 1),
                                            1, FromM);
                            if (rowId > 0) {
                                // 发送广播
                                Intent intent = new Intent(BroadCastNameManager.Recv_Message);
                                Bundle bundle = new Bundle();
                                bundle.putInt("Type", Type);
                                bundle.putString("Key", Key);
                                intent.putExtras(bundle);
                                sendBroadcast(intent);
                            } else {
                                mylog.w("消息入库失败：" + js.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommandCodeTS.CMD_GET_MSGLIST:
                    try {
                        // 解析数据
                        JSONObject jsonObjs = new JSONObject(content);
                        JSONArray jsonArray = jsonObjs.getJSONArray("MsgList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            int MsgID = js.getInt("MsgID");
                            String FromM = js.getString("FromM");
                            String Msg = js.getString("Msg");
                            String Time = js.getString("Time");
                            Integer Type = js.getInt("Type");
                            String Key = js.getString("Key");
                            String ShowName = js.getString("ShowName");
                            // 保存未读消息
                            long rowId =
                                    MsgAdaper.getInstance(getApplicationContext()).replace(MsgID, FromM, Msg, Time,
                                            Type, Key, ShowName, 0, ConfigAll.UserUUID, Func.formattime(new Date(), 1),
                                            1, FromM);
                            if (rowId > 0) {
                                if (Key.contains("Latitude")) {
                                    msgListData = JSON.parseObject(js.toString(), MsgListData.class);
                                    msgListData.SearchAddress(rowId, getApplicationContext());
                                }
                                Intent intent = new Intent(BroadCastNameManager.Recv_Message); // 定义广播
                                Bundle bundle = new Bundle();
                                bundle.putInt("Type", Type);
                                bundle.putString("Key", Key);
                                intent.putExtras(bundle);
                                sendBroadcast(intent);
                            } else {
                                mylog.w("消息入库失败：" + js.toString());
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommandCodeTS.CMD_GET_NOTICELIST: // 驾校通知
                    try {
                        // 解析数据
                        JSONObject jsonObjs = new JSONObject(content);
                        JSONArray jsonArray = jsonObjs.getJSONArray("MsgList");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject js = jsonArray.getJSONObject(i);
                            String Title = js.getString("Title");
                            String Msg = js.getString("Msg");
                            String PudDate = js.getString("PudDate");
                            Integer MsgID = js.getInt("MsgID");

                            // 保存未读消息
                            MsgJxtzAdapter.getInstance(getApplicationContext()).insert(Title, Msg, PudDate,
                                    0 + "", MsgID + "", 0 + "");
                        }
                        Intent intent = new Intent(BroadCastNameManager.Recv_Message); // 定义广播
                        sendBroadcast(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case CommandCodeTS.GPS_GETGPSDATA_MONITOR:
                    Intent intent = new Intent(BroadCastNameManager.Recv_GPS); // 定义广播
                    intent.putExtra("content", content);
                    sendBroadcast(intent);
                    break;
                default:
                    break;
            }

            int index = tcpHeader.getIndex();
            long key = index * 0x10000 + commandid;
            MyActivityInfo myActivityInfo = getActivity(key);

            if (null != myActivityInfo) {
                Handler handler = myActivityInfo.getHandler();
                Message message = handler.obtainMessage();
                message.what = tcpHeader.getCommendid();
                message.obj = content;
                message.sendToTarget();
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        MsgAdaper.getInstance(getApplicationContext()).close();
        MsgJxtzAdapter.getInstance(getApplicationContext()).close();
        this.sendBroadcast(new Intent(BroadCastNameManager.DESTORY));
        client.close("DataCenter.onDestroy");
        if (isGPSPackage)
            clientgps.close("DataCenter.onDestroy");
    }

    @Override
    protected void finalize() throws Throwable {
        this.stopSelf();
        super.finalize();
    }
}
