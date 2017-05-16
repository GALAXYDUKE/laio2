package com.bofsoft.laio.common;

public class ErrorCode {
    public static final int NETWORK_NOT_AVAILABLE = 1100000; // 网络不可用
    public static final int ErrorCode_System = 10100; // 系统级错误
    public static final int ErrorCode_DP_Connect_Failed = 10102; // 连接Driverplan服务器失败
    public static final int Error_Code_Parse_Exception = 10200;

    public static final short NETWORK_ERROR = 0x0001;// 网络错误
    public static final short SEND_SUCESS = 0x0002;// 发送成功
    public static final short SEND_FAILURE = 0x0003;// 发送失败
    public static final short NETWORK_3G = 0x0004;// 3G网络提示错误
    public static final short SERVICESTARTED = 0X0005;// 服务已启动
    public static final short SOCKET_CONNECT_SUCCESS = 0x0006;// 建立网络连接成功
    public static final short SOCKET_CONNECT_FAILURE = 0x0007;// 建立网络连接失败
    public static final short SOCKET_CONNECT_RETRY = 0x0008;// 建立网络连接失败
    public final static int ErrorCode_PassWord_Stutent = 10101; // 学员用户名或者密码错误
    public final static int ErrorCode_PassWord_Teacher = 10103; // 教练用户名或密码错误

    public static final int E_NOT_LOGIN = 10104; // 需要登录而未登录
}
