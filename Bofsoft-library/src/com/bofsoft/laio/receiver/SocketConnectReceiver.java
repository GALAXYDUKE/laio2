package com.bofsoft.laio.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.common.MyLog;

/**
 * Socket建连成功失败广播
 */
public class SocketConnectReceiver extends BroadcastReceiver {
    private MyLog myLog = new MyLog(getClass());
    private Handler handler;

    public SocketConnectReceiver() {
        super();
    }

    public SocketConnectReceiver(Handler handler) {
        super();
        this.handler = handler;
    }

    public Handler getHandler() {
        return handler;
    }

    /**
     * 设置页面回调handle
     *
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收广播
        if (null == intent || null == handler) {
            return;
        }
        myLog.i("broadcast receive: " + intent.getAction());
        if (BroadCastNameManager.Socket_Connect_Success.equalsIgnoreCase(intent.getAction())) {
            handler.sendEmptyMessage(ErrorCode.SOCKET_CONNECT_SUCCESS);
        }
        if (BroadCastNameManager.socket_Connect_Retry.equalsIgnoreCase(intent.getAction())) {
            handler.sendEmptyMessage(ErrorCode.SOCKET_CONNECT_RETRY);
        }
        if (BroadCastNameManager.Socket_Connect_Failure.equalsIgnoreCase(intent.getAction())) {
            handler.sendEmptyMessage(ErrorCode.SOCKET_CONNECT_FAILURE);
        }
    }
}
