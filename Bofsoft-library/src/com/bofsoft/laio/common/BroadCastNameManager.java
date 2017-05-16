package com.bofsoft.laio.common;

public class BroadCastNameManager {

    /**
     * 从服务器接受到消息
     */
    public static String Recv_Message = "com.bofsoft.laio.recvmessage";

    /**
     * 通知读取消息
     */
    public static String Read_Message = "com.bofsoft.laio.readmessage";
    /**
     * 退出登录
     */
    public static String Logout = "com.bofsoft.laio.logout";

    /**
     * app退出
     */
    public static String DESTORY = "com.bofsoft.liao.des";

    /**
     * 通知去服务器获取消息
     */
    public static String Msg_List = "com.bofsoft.laio.msgList";

    /**
     * 通知client重连socket
     */
    public static String Socket_Reconnect = "com.bofsoft.laio.socket.reconnect";
    /**
     * socket建连成功
     */
    public static String Socket_Connect_Success = "com.bofsoft.laio.socket.connect.success";
    /**
     * socket建连失败
     */
    public static String Socket_Connect_Failure = "com.bofsoft.laio.socket.connect.failure";
    /**
     * socket自动重连中
     */
    public static String socket_Connect_Retry = "com.bofsoft.laio.socket.connect.retry";
    /**
     * Android Tag, Don't Modify the Value
     */
    public static String CONN_CHANGE = "android.net.conn.CONNECTIVITY_CHANGE";

    /**
     * 从服务器接受到消息
     */
    public static String Recv_GPS = "com.bofsoft.laio.recvgps";
}
