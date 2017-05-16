package com.bofsoft.laio.laiovehiclegps.DataClass;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.bofsoft.laio.data.login.LoginData;
import com.bofsoft.laio.laiovehiclegps.Config.Config;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConfigallTea implements Serializable {

    public static String SystemVersion = "";// 获取当前应用程序的版本号
    public static String SystemType = "android";
    public static String initKey = "556d7006-facc-40e9-a151-5ac1e7e2b630";
    public static int designWidth = 720;
    public static int designHeight = 1280;
    public static int screenWidth = 720;
    public static int screenHeight = 1280;
    public static String GUID = null; // 客户端的唯一标识，程序第一次运行时产生保存在客户端，生成后不再重新生成
    public static int ObjectType = 1; // 用户类型：0学员，1教练
    public static LoginData loginData;

    public static String UserToken = ""; // 推送消息的token
    public static String Username = null; // 登陆的用户名
    public static String BMapKey = "YaEDyWncEbtpqtATkKiqGXu0";// 百度地图的Key
    public static double defaultLng = 104.06; // 经度
    public static double defaultLat = 30.67; // 纬度

    public static String DefaultCityDM = "510100"; // 默认城市代码
    public static boolean isLocation = false; // 是否成功定位
    public static int LocationStatus = 0; // -1 -定位失败，0 -未定位，1 -定位成功
    public static String CityDM; // 选择的城市DM
    public static BDLocation bdLocation; // 百度定位信息
    public static double Lng = 0; // 经度
    public static double Lat = 0; // 纬度
    // **************************************************************************************
//    public static AccountStatusInfoData accountStatus; // 获取账号状态信息
//    public static ApplyPulishAuthStateData authState = new ApplyPulishAuthStateData(); // 网络商品服务认证状态(教练)
//    public static BindingSchoolStatusData schoolStatusData; // 获取绑定的ERP账号信息 (驾校)
//    public static TeacherGetRecruitAndTraining teaGetRecruitAndTraining;//获取招生产品或培训产品可发布产品类型
//    public static SetandGetDefaultCityInfo setandGetDefaultCityInfo = new SetandGetDefaultCityInfo();
//    public static ShareContentData ShareContentData = null; // 分享内容获取
//    public static int[] TypeforR = {-1, -1, -1}; //招生产品可发布类型初始值
//    public static int[] TypeforT = {-1, -1};//培训产品可发布类型初始值
//    public static CoachInfoData coachInfoData; // 教练信息
//    public static TrainStatusData trainStatusData;
    public static String BannerInfo = ""; // 首页标题栏信息
    public static int OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表
    public static boolean isExamination = false;//false 我的学员模块 true 考场模拟模块
    public static boolean isInvitationAward = false;//false 我的学员模块 true 老学员邀请奖励

    public static final String Result_Key = "result_key";
    public static final String Param_Key = "param_key";
    public static final String Param_Key_Two = "param_key_two";
    public static final String Param_Key_Three = "param_key_three";
    public static final String Param_Key_four = "param_key_four";

    public static String getUsername() {
        return Username;
    }

    public static void setUsername(String username) {
        Username = username;
    }

    public static String getGUID(Context context) {
        GUID = Config.spHelper.getString("GUID", null);
        if (GUID == null) {
            GUID = java.util.UUID.randomUUID().toString();
            Config.spHelper.putString("GUID", GUID);
        }
        return GUID;
    }

    public static void setGUID(String guid, Context context) {
        Config.spHelper.putString("GUID", guid);
        GUID = guid;
    }

    /**
     * 重置所有参数
     */
    public static void reset() {
        SystemVersion = "";// 获取当前应用程序的版本号
        GUID = null; // 客户端的唯一标识，程序第一次运行时产生保存在客户端，生成后不再重新生成
        ObjectType = 1; // 用户类型：0学员，1教练
        Username = null; // 登陆的用户名
        isLocation = false; // 是否成功定位
        LocationStatus = 0; // -1 -定位失败，0 -未定位，1 -定位成功
        CityDM = null; // 选择的城市DM
        bdLocation = null; // 百度定位信息
        Lng = 0; // 经度
        Lat = 0; // 纬度
//        accountStatus = null; // 获取账号状态信息
//        authState = null; // 网络商品服务认证状态(教练)
//        schoolStatusData = null; // 获取绑定的ERP账号信息 (驾校)
//        coachInfoData = null; // 教练信息
//        trainStatusData = null;
        BannerInfo = ""; // 首页标题栏信息
        OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表
        loginData = null;
//        teaGetRecruitAndTraining = null;
//        ShareContentData = null; // 分享内容获取
//        TypeforR[0] = -1;
//        TypeforR[1] = -1;
//        TypeforR[2] = -1;
//        TypeforT[0] = -1;
//        TypeforT[1] = -1;
    }

}
