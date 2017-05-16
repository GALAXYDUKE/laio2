package com.bofsoft.laio.tcp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.bofsoft.laio.common.AtomicIntegerUtil;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.BroadCastUtil;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.ConstAll;
import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.common.NetworkUtil;
import com.bofsoft.laio.common.ServerConfigall;
import com.bofsoft.laio.data.login.LoginData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class Clientgps {
    protected MyLog mylog = new MyLog(this.getClass());

    private String IP = ServerConfigall.serveripgps;
    private int PORT = ServerConfigall.serverportgps;

    public static int state = ConstAll.STATE_CONNECT_FAILED;
    private int reconn_count = 0;

    private Socket socket = null;
    private OutputStream mOutStream = null;
    private InputStream mInStream = null;

    private Thread mConnThread = null;
    public static Thread mSendThread = null;
    public static Thread mRecThread = null;

    private Context context;
    private ISocketResponse respListener;
    private ArrayBlockingQueue<PacketItem> requestQueen = new ArrayBlockingQueue<PacketItem>(1000);
    private final Object ConnectLock = new Object();
    private static Clientgps clientgps = null;
    Handler handler = null;
    PacketItem lastItem;
    PacketItem loginItem;
    Boolean SelfLogin = false;

    public ConnectReceiver connectReceiver = new ConnectReceiver();
    public ReConnectReceiver reconnReceiver = new ReConnectReceiver();

    // 放入队列的包
    class PacketItem {
        private int id = AtomicIntegerUtil.getIncrementID(); // 队列ID 安全自增长方法
        long key; // 队列的Key，用于回查handler
        Packet packet; // 发送的包
        boolean always; // 是否循环发送直到成功，发短消息需要

        public int getId() {
            return id;
        }

        public long getKey() {
            return key;
        }

        public void setKey(long key) {
            this.key = key;
        }

        public Packet getPacket() {
            return packet;
        }

        public void setPacket(Packet packet) {
            this.packet = packet;
        }

        public boolean getAlways() {
            return always;
        }

        public void setAlways(boolean always) {
            this.always = always;
        }
    }

    public class ReConnectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null
                    && BroadCastNameManager.Socket_Reconnect.equalsIgnoreCase(intent.getAction())) {
                reconn("reconnent broadcast");

                if (intent.getAction().equals(BroadCastNameManager.DESTORY)) {
                    context.unregisterReceiver(this);
                }
            }
        }
    }

    public class ConnectReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(BroadCastNameManager.CONN_CHANGE)) {
                ConnectivityManager manager =
                        (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeInfo = manager.getActiveNetworkInfo();

                if (activeInfo != null && activeInfo.isConnected()) {
                    if (ConfigAll.autoConnect = true) {
                        open("ConnectReceiver");
                    }
                }

                if (activeInfo != null && !activeInfo.isConnected()) {
                    if (ConfigAll.autoConnect = true) {
                        close("ConnectReceiver.CONN_CHANGE");
                    }
                }

                if (activeInfo == null) {
                    Toast.makeText(context, "网络连接已经断开，请检查网络", Toast.LENGTH_LONG).show();
                }

                if (activeInfo != null && activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    if (ConfigAll.alert3G) {
//            Toast.makeText(context, "当前网络类型为2G/3G/4G，请注意网络流量哦", Toast.LENGTH_LONG).show();
                    }
                }

                if (activeInfo != null && activeInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    if (ConfigAll.alert3G) {
//            Toast.makeText(context, "当前网络类型为WIFI", Toast.LENGTH_LONG).show();
                    }
                }
            }

            if (intent.getAction().equals(BroadCastNameManager.DESTORY)) {
                context.unregisterReceiver(this);
            }
        }
    }

    public static Clientgps getInstance(Context context, ISocketResponse respListener) {
        if (clientgps == null)
            clientgps = new Clientgps(context, respListener);
        return clientgps;
    }

    private Clientgps(Context context, ISocketResponse respListener) {
        this.context = context;
        this.respListener = respListener;
        registerReceive();
    }

    /**
     * 注册网络连接及socket连接状态广播
     */
    public void registerReceive() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(BroadCastNameManager.CONN_CHANGE);
        filter.addAction(BroadCastNameManager.DESTORY);
        context.registerReceiver(connectReceiver, filter);

        IntentFilter reconnFilter = new IntentFilter();
        reconnFilter.addAction(BroadCastNameManager.Socket_Reconnect);
        reconnFilter.addAction(BroadCastNameManager.DESTORY);
        context.registerReceiver(reconnReceiver, reconnFilter);
    }

    /**
     * 取消网络连接及socket连接状态广播
     */
    public void unRegisterReceive() {
        if (context != null) {
            context.unregisterReceiver(connectReceiver);
            context.unregisterReceiver(reconnReceiver);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        close("Client.finalize");
        unRegisterReceive();
        super.finalize();
    }

    // 重试的时候发送最后一个包
    public int retry() {
        Handler handler = this.handler;
        long key = lastItem.getKey();
        Packet in = lastItem.getPacket();
        boolean always = lastItem.getAlways();
        return send(handler, key, in, always);
    }

    public int send(Handler handler, long key, Packet in, boolean always) {
        this.handler = handler;

        // 包队列
        PacketItem item = new PacketItem();
        item.setKey(key);
        item.setPacket(in);
        item.setAlways(always);

        // 最后一个包
        lastItem = item;

        // 如果是登录包则保存
        if (null == loginItem && in.getCommandid() == CommandCodeTS.CMD_MOBILE_LOGIN) {
            mylog.i("存储登录信息");
            loginItem = item;
            if (!SelfLogin) {
                requestQueen.clear(); // 手动登陆时,清理掉可能存在的用于自动登录的数据包
            }
        }

        // 如果是登出包则清理登录信息
        if (null != loginItem && in.getCommandid() == CommandCodeTS.CMD_MOBILE_LOGOUT) {
            mylog.i("清理登录信息");
            loginItem = null;
            SelfLogin = false;
        }

        if (SelfLogin && null != loginItem) {
            try {
                mylog.i("登录包放入队列中");
                requestQueen.put(loginItem);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (isNeedConn()) {
            reconn("send");
        }

        // 如果要持续发送到成功，则加入队列，并不返回错误消息，只有当send线程发送成功后才返回消息
        // 该方法适用于端消息聊天
        try {
            requestQueen.put(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return item.getId();
    }

    public void cancel(int reqId) {
        Iterator<PacketItem> mIterator = requestQueen.iterator();
        while (mIterator.hasNext()) {
            PacketItem item = mIterator.next();
            if (item.getId() == reqId) {
                mIterator.remove();
            }
        }
    }

    public boolean isNeedConn() {
        return !((state == ConstAll.STATE_CONNECT_SUCCESS)
                && (null != mSendThread && mSendThread.isAlive())
                && (null != mRecThread && mRecThread.isAlive()) || state == ConstAll.STATE_CONNECT_WAIT);
    }

    public void open(String fromwhere) {
        reconn(fromwhere);
    }

    public void open(String host, int port) {
        this.IP = host;
        this.PORT = port;
        reconn("open");
    }

    public synchronized void reconn(String fromwhere) {
        if (isNeedConn()) {
            IP = ServerConfigall.serveripgps;
            PORT = ServerConfigall.serverportgps;
            mConnThread = new Thread(new ConnRunnable(fromwhere));
            mConnThread.start();
        }
    }

    public void close(final String fromwhere) {
        try {
            mylog.i("关闭客户端连接，来源：" + fromwhere);
            try {
                try {
                    if (null != socket) {
                        socket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != mOutStream) {
                        mOutStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != mInStream) {
                        mInStream.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != mConnThread && mConnThread.isAlive()) {
                        mConnThread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != mSendThread && mSendThread.isAlive()) {
                        mSendThread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (null != mRecThread && mRecThread.isAlive()) {
                        mRecThread.interrupt();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } finally {
                socket = null;
                mOutStream = null;
                mInStream = null;
                mConnThread = null;
                mSendThread = null;
                mRecThread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ConnRunnable implements Runnable {
        String fromwhere;

        public ConnRunnable(String fromwhere) {
            super();
            this.fromwhere = fromwhere;
        }

        public void run() {
            synchronized (ConnectLock) {
                while (state != ConstAll.STATE_CONNECT_SUCCESS) {
                    try {
                        state = ConstAll.STATE_CONNECT_WAIT;
                        ConfigAll.state = ConstAll.STATE_CONNECT_WAIT;
                        mylog.i("开始建立连接，来源：" + fromwhere);

                        if (!NetworkUtil.isNetworkAvailable(context)) {
                            state = ConstAll.STATE_CONNECT_FAILED;
                            ConfigAll.state = ConstAll.STATE_CONNECT_FAILED;

                            // 没有可用网络的情况下通知UI层建连失败
                            mylog.i("Socket_Connect_Failure1 " + fromwhere);
                            BroadCastUtil.sendSocketConnectBroadCast(context,
                                    BroadCastNameManager.Socket_Connect_Failure);
                            break;
                        }

                        // socket连接 IP,PORT
                        socket = new Socket();
                        socket.connect(new InetSocketAddress(IP, PORT), ConstAll.CONN_TIMEOUT * 1000);
                        state = ConstAll.STATE_CONNECT_SUCCESS;
                        ConfigAll.state = ConstAll.STATE_CONNECT_SUCCESS;
                        reconn_count = 0;
                    } catch (Exception e) {
                        e.printStackTrace();
                        // state = ConstAll.STATE_CONNECT_FAILED;
                        // ConfigAll.state = ConstAll.STATE_CONNECT_FAILED;
                    }

                    // socket连接成功，获取输入输出流
                    if (state == ConstAll.STATE_CONNECT_SUCCESS) {
                        try {
                            mOutStream = socket.getOutputStream();
                            mInStream = socket.getInputStream();
                            mylog.i("创建输入输出流完成gps");

                            mSendThread = new Thread(new SendRunnable());
                            mRecThread = new Thread(new ReceiveRunnable());
                            mylog.i("创建收发线程完成gps");

                            mSendThread.start();
                            mRecThread.start();
                            mylog.i("启动收发线程完成gps");

                            // ConfigAll.state = ConstAll.STATE_CONNECT_SUCCESS;
                            BroadCastUtil.sendSocketConnectBroadCast(context,
                                    BroadCastNameManager.Socket_Connect_Success);
                        } catch (Exception e) {
                            e.printStackTrace();
                            continue;
                        }

                        break;
                    } else {
                        if (NetworkUtil.isNetworkAvailable(context) && reconn_count < ConstAll.RECONN_COUNT) {
                            // state = ConstAll.STATE_CONNECT_WAIT;

                            // 通知UI层正在自动重连
                            BroadCastUtil.sendSocketConnectBroadCast(context,
                                    BroadCastNameManager.socket_Connect_Retry);

                            reconn_count++;

                            // 存在网络，但是没有连接上服务器，等待一段时间重新连接
                            try {
                                int TIME_OUT = ConstAll.RECONN_WAIT_TIME * 1000;
                                Thread.sleep(TIME_OUT);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            state = ConstAll.STATE_CONNECT_FAILED;
                            ConfigAll.state = ConstAll.STATE_CONNECT_FAILED;
                            reconn_count = 0;

                            // 通知UI层建连失败
                            mylog.i("Socket_Connect_Failure2 " + fromwhere);
                            BroadCastUtil.sendSocketConnectBroadCast(context,
                                    BroadCastNameManager.Socket_Connect_Failure);
                            break;
                        }
                    }
                }
                mylog.i("Socket_Connect_Exit " + fromwhere);
            }
        }
    }

    private class SendRunnable implements Runnable {

        public SendRunnable() {
            super();
        }

        public void run() {
            PacketItem item = null; // 当前需要发送的包
            long key = 0;
            Packet packet;

            try {
                while (state == ConstAll.STATE_CONNECT_SUCCESS && null != mOutStream) {
                    if (ConfigAll.isLogining) {
                        Thread.sleep(1000);
                        continue;
                    }

                    item = requestQueen.take();

                    if (item != null) {
                        // 获取发送的窗口信息
                        key = item.getKey();

                        // 获取发送的数据包
                        packet = item.getPacket();
                        packet.pack();

                        if (packet.getCommandid() == CommandCodeTS.CMD_MOBILE_LOGIN) {
                            ConfigAll.isLogining = true;
                        }

                        // 发送数据
                        mOutStream.write(packet.getPacket());
                        mOutStream.flush();

                        // 清空Item
                        item = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // 发送的时候出现异常，说明socket被关闭了(服务器关闭)java.net.SocketException:
            }

            // 发送错误到DataCenter
            if (handler != null) {
                Message message = handler.obtainMessage();
                message.what = ErrorCode.NETWORK_ERROR;
                message.obj = key;
                message.sendToTarget();
            }

            // 把当前包放入队列中
            if (null != item) {
                try {
                    requestQueen.put(item);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void receiveData(ByteArrayOutputStream bytestream) {
        byte[] bytes = bytestream.toByteArray();
        try {
            if (bytes.length >= TcpHeader.getHeaderlen()) {
                TcpHeader header = Packet.unpackHeader(bytes);
                if (header != null) {
                    if (bytes.length >= header.length) {
                        byte[] contentBytes = Arrays.copyOfRange(bytes, 0, header.length);
                        byte[] endBytes = Arrays.copyOfRange(bytes, header.length, bytes.length);

                        int flag = 0;

                        if (loginItem != null && SelfLogin) {
                            if (header.index == loginItem.getPacket().getIndex()
                                    && header.commandid == CommandCodeTS.CMD_MOBILE_LOGIN) {
                                JSONObject js;
                                try {
                                    SelfLogin = false;
                                    flag = 1;

                                    // 解包
                                    Packet packet = new Packet();
                                    try {
                                        packet.unpack(contentBytes);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    String content = new String(packet.getPacket());

                                    js = new JSONObject(content);
                                    LoginData loginData = LoginData.InitData(js);
                                    ConfigAll.setKey(loginData.getKey());
                                    ConfigAll.setSession(loginData.getSession().getBytes());
                                    ConfigAll.setUserPhone(loginData.getUserPhone());
                                    ConfigAll.setUserERPName(loginData.getUserERPName());
                                    ConfigAll.setUserERPDanwei(loginData.getUserERPDanwei());
                                    ConfigAll.setUserUUID(loginData.getUserUUID());
                                    ConfigAll.setCoachType(loginData.getCoachType());
                                    ConfigAll.setLogin(true);
                                    ConfigAll.isLogining = false;
                                    mylog.i("自动重建登录成功");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        if (flag == 0 && null != respListener) {
                            respListener.onSocketResponse(contentBytes);
                        }

                        bytestream.reset();
                        if (endBytes.length > 0) {
                            mylog.e("出现意外数据[" + Arrays.toString(endBytes) + "]");
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final int bufSize = 4 * 1024; // 读取缓冲区大小,限制单次读取数据不超过4k
    private byte[] buf = new byte[bufSize];

    private class ReceiveRunnable implements Runnable {
        public void run() {
            int read = 0;
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            try {
                while (state == ConstAll.STATE_CONNECT_SUCCESS && null != mInStream) {
                    bytestream.reset();
                    // 先读包头
                    read = mInStream.read(buf, 0, TcpHeader.getHeaderlen());
                    if (-1 != read) {
                        // 解包头
                        bytestream.write(buf, 0, read);
                        TcpHeader header = Packet.unpackHeader(bytestream.toByteArray());
                        // 读包剩余部分
                        int leftSize = 0;
                        if (null != header) {
                            leftSize = header.length - TcpHeader.getHeaderlen();
                        }
                        while (leftSize > 0) {
                            if (leftSize <= bufSize) {
                                read = mInStream.read(buf, 0, leftSize);
                            } else {
                                read = mInStream.read(buf, 0, bufSize);
                            }
                            // 读取出现错误
                            if (-1 == read)
                                break;
                            bytestream.write(buf, 0, read);
                            leftSize -= read;
                        }
                        if (-1 != read) {
                            // 读取成功,处理数据
                            receiveData(bytestream);
                        } else {
                            // 读取出现错误
                            bytestream.close();
                            break;
                        }
                    } else {
                        // 读取出现错误
                        bytestream.close();
                        break;
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

            mylog.i("接收线程退出, read=" + read + " state=" + state + " isLogining=" + ConfigAll.isLogining);
            state = ConstAll.STATE_CONNECT_FAILED;
            close("ReceiveRunable");

            if (!ConfigAll.isLogining) {
                if (loginItem != null)
                    SelfLogin = true;

                if (null != handler) {
                    Message message = handler.obtainMessage();
                    message.what = ErrorCode.NETWORK_ERROR;
                    message.sendToTarget();
                }
            } else {
                ConfigAll.isLogining = false;
                loginItem = null;
            }
        }
    }
}
