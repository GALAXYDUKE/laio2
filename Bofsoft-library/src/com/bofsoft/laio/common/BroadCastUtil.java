package com.bofsoft.laio.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import com.bofsoft.laio.receiver.SocketConnectReceiver;

/**
 * 广播管理类
 *
 * @author yedong
 */
public class BroadCastUtil {

    /**
     * @param context
     * @param action  action is {@link BroadCastNameManager#Socket_Connect_Success} or
     *                {@link BroadCastNameManager#Socket_Connect_Failure}
     */
    public static void sendSocketConnectBroadCast(Context context, String action) {
        if (context == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setAction(action);
        context.sendBroadcast(intent);
    }

    /**
     * 注册socket连接状态广播
     *
     * @param context
     * @param handler 通知handler
     * @return
     */
    public static SocketConnectReceiver registerSocketConnectBroadCast(Context context,
                                                                       Handler handler) {
        if (context == null) {
            return null;
        }
        SocketConnectReceiver receiver = new SocketConnectReceiver();
        IntentFilter socketConnectFilter = new IntentFilter();
        socketConnectFilter.addAction(BroadCastNameManager.Socket_Connect_Success);
        socketConnectFilter.addAction(BroadCastNameManager.socket_Connect_Retry);
        socketConnectFilter.addAction(BroadCastNameManager.Socket_Connect_Failure);
        receiver.setHandler(handler);
        context.registerReceiver(receiver, socketConnectFilter);
        return receiver;
    }

    /**
     * 取消socket连接状态广播
     *
     * @param context
     * @param receiver 取消的receiver
     */
    public static void unRegisterSocketConnectBroadCast(Context context, BroadcastReceiver receiver) {
        if (context == null) {
            return;
        }
        context.unregisterReceiver(receiver);
    }
}
