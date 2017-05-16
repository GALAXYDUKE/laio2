package com.bofsoft.laio.common;

import com.bofsoft.sdk.config.BaseSysConfig;

public class ConfigAll {

    // public static final String SDCard_Download_Cache_Path = Environment.getDownloadCacheDirectory()
    // .getAbsolutePath();
    // public static final String SDCard_Data_Path = Environment.getDataDirectory().getAbsolutePath();
    public static String APP_UPDATE_PATH = BaseSysConfig.appCachePath + "/update/";
    public static String APP_IMAGE_PATH = BaseSysConfig.appFilesPath + "/image/";
    public static String APP_IMAGE_CACHE_PATH = BaseSysConfig.appCachePath + "/image/";
    // public static String APP_PROPERTIES_PATH = APP_PATH + "/data.properties";
    public static String APP_CRASH_PATH = BaseSysConfig.appCachePath + "/crash/";
    public static String fileLogPath = BaseSysConfig.appFilesPath;

    public static boolean isLogining = false; // 是否正在重连
    public static int state; // socket 连接状态
    public static String SystemVersion = "";
    public static String SystemType = "android";
    public static boolean autoConnect = true; // 是否自动重连
    public static boolean alert3G = true;// 是否提示2G/3G提示

    public static String initKey = "556d7006-facc-40e9-a151-5ac1e7e2b630";
    public static int designWidth = 720;
    public static int designHeight = 1280;
    public static int screenWidth = 720;
    public static int screenHeight = 1280;

    // 登录时返回信息
    public static String Key = initKey;
    public static byte[] Session = new byte[16];
    public static String UserPhone = null;
    public static String UserERPName = null;
    public static String UserERPTrueName = null;
    public static String UserERPDanweiName = null;
    public static String UserERPDanwei = null;
    public static String UserUUID = null;
    public static int CoachType = 2; // 教练类型，1机构教练，2个体教练(默认值)

    // 登录信息
    public static String LoginInfo = null; // 登录成功后保存登录信息（连接异常后用此信息重连获取key）
    public static String GUID = null;
    public static boolean isbreadCastLogin = false;
    public static short CodeNum = ServerConfigall.CodeNum; // 客户端版本
    public static String headerName = "LAIO"; // 协议包前缀
    public static boolean isLogin = false;
    public static boolean isOrderJumpToIndex = false;//学员端订单跳转首页 2016/11/22
    public static int LoginType = 0; // 0-博软账户登录， 1-手机账号登录
    public static int ObjectType = 0; // 用户类型：0学员，1教练
    public static String UserToken = ""; // 推送消息的token
    public static String Username = null; // 登陆的用户名
    public static String MasterId = null; // 教练ID
    public static String MasterJiaxiao = null; // 驾校名称
    public static String MasterName = null;// 教练用户名
    public static String MasterShowName = null;// 教练显示的名称
    public static String BMapKey = "YaEDyWncEbtpqtATkKiqGXu0";// 百度地图的Key
    public static double defaultLng = 104.06; // 经度
    public static double defaultLat = 30.67; // 纬度
    public static String DefaultCityDM = "510100"; // 默认区域代码
    public static int LocationStatus = 0; // -1 -定位失败，0 -未定位，1 -定位成功
    public static String CityDM; // 选择的城市DM
    public static double Lng = 0; // 经度
    public static double Lat = 0; // 纬度
    public static int StatusRenZheng = 4;// 是否已经认证,1已认证, 2认证中（可以重新提交认证信息）, 3未认证(已删除),

    // 4认证失败（可以重新提交认证信息）
    public static int StatusEmail = 3;// 1已绑定, 2绑定中, 3未绑定
    public static String StatusRenZhengName;
    public static String StatusEmailName;
    public static String Email;
    public static String IDCardNum;
    public static String Name;
    public static int OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表
    public static final String Result_Key = "result_key";
    public static final String Param_Key = "param_key";
    public static final String Param_Key_Two = "param_key_two";
    public static final String Param_Key_Three = "param_key_three";

    public static String IDCardNumforAppoint = "";
    public static String PwdforAppoint = "";
    public static int forAppoint = 0;

    public static String getKey() {
        if (isLogin == true) {
            return Key;
        } else {
            return initKey;
        }
    }

    public static void setKey(String key) {
        Key = key;
    }

    public static byte[] getSession() {
        if (isLogin == true) {
            return Session;
        } else {
            Session = new byte[16];
            return Session;
        }
    }

    public static void setSession(byte[] session) {
        Session = session;
    }

    public static String getUserPhone() {
        return UserPhone;
    }

    public static void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public static String getUserERPName() {
        return UserERPName;
    }

    public static void setUserERPName(String userERPName) {
        UserERPName = userERPName;
    }

    public static String getUserERPDanwei() {
        return UserERPDanwei;
    }

    public static void setUserERPDanwei(String userERPDanwei) {
        UserERPDanwei = userERPDanwei;
    }

    public static String getUserERPTrueName() {
        return UserERPTrueName;
    }

    public static void setUserERPTrueName(String userERPTrueName) {
        UserERPTrueName = userERPTrueName;
    }

    public static String getUserERPDanweiName() {
        return UserERPDanweiName;
    }

    public static void setUserERPDanweiName(String userERPDanweiName) {
        UserERPDanweiName = userERPDanweiName;
    }

    public static String getUserUUID() {
        return UserUUID;
    }

    public static void setUserUUID(String userUUID) {
        UserUUID = userUUID;
    }

    public static int getCoachType() {
        return CoachType;
    }

    public static void setCoachType(int coachType) {
        CoachType = coachType;
    }

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean isLogin) {
        ConfigAll.isLogin = isLogin;
    }

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }


    /**
     * 重置所有参数
     */
    public static void reset() {
        isLogining = false; // 是否正在重连
        SystemVersion = ""; // 获取当前应用程序的版本号
        autoConnect = true; // 是否自动重连
        alert3G = true; // 是否提示2G/3G提示
        screenWidth = 720;
        screenHeight = 1280;

        // 登录时返回信息
        Key = initKey;
        Session = new byte[16];
        UserPhone = null;
        UserERPName = null;
        UserERPDanwei = null;
        UserERPTrueName = null;
        UserERPDanweiName = null;
        UserUUID = null;

        // 登录信息
        LoginInfo = null; // 登录成功后保存登录信息（连接异常后用此信息重连获取key）
        GUID = null;
        isbreadCastLogin = false;
        isLogin = false;
        isOrderJumpToIndex = false;

        Username = null; // 登陆的用户名
        MasterId = null; // 教练ID
        MasterJiaxiao = null; // 驾校名称
        MasterName = null; // 教练用户名
        MasterShowName = null; // 教练显示的名称
        LocationStatus = 0; // -1 -定位失败，0 -未定位，1 -定位成功
        CityDM = null; // 选择的城市DM
        Lng = 0; // 经度
        Lat = 0; // 纬度

        StatusRenZheng = 4; // 是否已经认证,1已认证, 2认证中（可以重新提交认证信息）, 3未认证(已删除), 4认证失败（可以重新提交认证信息）
        StatusEmail = 3; // 1已绑定, 2绑定中, 3未绑定
        StatusRenZhengName = null;
        StatusEmailName = null;
        Email = null;
        IDCardNum = null;
        Name = null;
        OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表
    }
}
