package com.bofsoft.laio.common;

public class ServerConfigall {
    //  public static String serverip = "laio.bofsoft.com"; // 测试服务器
//  public static String serverip = "m.laio.cn"; // 外网
//  public static String serverip = "10.0.0.137"; // 测试服务器
//  public static String serverip = "10.0.0.235"; // 周老师测试服务器
    public static String serverip = "10.0.0.28"; // 测试服务器
    //    public static int serverport = 48888;
    public static int serverport = 58888;//来噢的端口
//    public static int serverport = 48888;//租车服务的端口
    public static String serveripgps = "10.0.0.28"; // 测试服务器
    public static int serverportgps = 38888;
    //public static String gpsHost = "http://10.0.0.28:8070/gpshost/";//测试环境
    public static String gpsHost = "http://gps.laio.cn:8070/gpshost/";//正式环境
    public static String WebHost = "http://" + serverip;//+ ":8010"
    public static String gpsWebHost = "http://www.laio.cn";
    //  public static String WebHost = "http://www.laio.cn";//外网webhost
    public static final boolean IS_DEBUG_VER = false; // 是否是调试版本
    public static short CodeNum = CodeNumConst.CODENUM_DEFAULT; // 客户端版本
    public static boolean MyLog_is_w = true;
    public static boolean Mylog_is_file = true;
}
