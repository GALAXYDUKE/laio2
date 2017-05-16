package com.bofsoft.sdk.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.ArrayList;
import java.util.List;

public class NetworkState {

    private static NetworkState self;

    private NetworkState() {

    }

    public static NetworkState getInstence() {
        if (self == null)
            self = new NetworkState();
        return self;
    }

    /**
     * 枚举网络状态 NET_UNKNOWN：未知网络 NET_NO：没有网络 NET_WIFI：wifi NET_2G：2g网络 NET_3G：3g网络 NET_4G：4g网络
     */
    public enum NET_ATATE {
        NET_UNKNOWN, NET_NO, NET_WIFI, NET_2G, NET_3G, NET_4G
    }

    /**
     * 当前网络状态
     */
    public static NET_ATATE STATE = NET_ATATE.NET_UNKNOWN;

    /**
     * 监听网络状态改变
     */
    private List<NetEventListener> netListeners = new ArrayList<NetworkState.NetEventListener>();

    /**
     * 设置网络状态监听
     */
    public void setNetEventListener(NetEventListener listener) {
        if (!netListeners.contains(listener)) {
            netListeners.add(listener);
        }
    }

    /**
     * 关闭网络状态监听
     */
    public void removeNetEventListener(NetEventListener listener) {
        if (netListeners.contains(listener)) {
            netListeners.remove(listener);
        }
    }

    public interface NetEventListener {
        void NET_ATATE(NET_ATATE state);
    }

    /**
     * 判断当前是否网络连接
     *
     * @param context
     * @return
     */
    public NET_ATATE isConnected(Context context) {
        NET_ATATE stateCode = NET_ATATE.NET_NO;
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni != null && ni.isConnectedOrConnecting()) {
            switch (ni.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    stateCode = NET_ATATE.NET_WIFI;
                    if (STATE == NET_ATATE.NET_WIFI)
                        return stateCode;
                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    switch (ni.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2
                        case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
                        case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            stateCode = NET_ATATE.NET_2G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            stateCode = NET_ATATE.NET_3G;
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            stateCode = NET_ATATE.NET_4G;
                            break;
                        default:
                            stateCode = NET_ATATE.NET_UNKNOWN;
                    }
                    break;
                default:
                    stateCode = NET_ATATE.NET_UNKNOWN;
            }
        }
        STATE = stateCode;
        for (NetEventListener listener : netListeners) {
            listener.NET_ATATE(stateCode);
        }
        return stateCode;
    }
}
