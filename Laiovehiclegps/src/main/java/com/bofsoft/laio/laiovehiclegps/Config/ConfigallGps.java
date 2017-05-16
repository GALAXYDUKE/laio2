package com.bofsoft.laio.laiovehiclegps.Config;

import android.content.Context;

import com.baidu.location.BDLocation;
import com.bofsoft.laio.laiovehiclegps.Fragment.CarListFragment;
import com.bofsoft.laio.laiovehiclegps.Fragment.HomeFragment;

/**
 * Created by szw on 2017/3/1.
 */

public class ConfigallGps {
    public static String SystemVersion = "";
    public static String SystemType = "android";
    public static String initKey = "556d7006-facc-40e9-a151-5ac1e7e2b630";
    public static int designWidth = 720;
    public static int designHeight = 1280;
    public static int screenWidth = 720;
    public static int screenHeight = 1280;
    public static String GUID = null;
    public static boolean isbreadCastLogin = false;
    public static boolean fragmentMyUpdata =false;
    public static boolean addrpopupwindowUpdata = false;
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
    public static BDLocation bdLocation; // 百度定位信息
    public static double Lng = 0; // 经度
    public static double Lat = 0; // 纬度

    // **************************************************************************************
    // 认证的全局变量
//    public static AccountStatusInfoData accountStatus; // 获取账号状态信息
//    public static MyCollCoachUUIDListData myCollCoachUUIDList; // 已收藏教练UUID列表
//    public static TrainStatusData trainStatusData; // 培训状态
//    public static StuAuthInfoData stuAuthInfoData; // 学员填写资料(陪练时要求填写或者驾校认证)
//    public static ShareContentData ShareContentData = null; // 分享内容获取
//    public static ShareCodeAndInfo ShareCodeandInfo =new ShareCodeAndInfo();//分享链接
//    public static ShareIncomeInfo shareIncomeInfo;//分享收益
//    public static SetandGetDefaultCityInfo setandGetDefaultCityInfo=new SetandGetDefaultCityInfo();
    public static boolean isShareActivity;//是否 是点击分享页面 是：有邀请码 否：没有邀请码
    public static int StatusRenZheng = 4;// 是否已经认证,1已认证, 2认证中（可以重新提交认证信息）, 3未认证(已删除),
    // 4认证失败（可以重新提交认证信息）
    public static int StatusEmail = 3;// 1已绑定, 2绑定中, 3未绑定
    public static String StatusRenZhengName;
    public static String StatusEmailName;
    public static String Email;
    public static String IDCardNum;
    public static String Name;
    public static boolean shareredpoint;//我 分享模块的红点
//    public static List<CarTypeData> carTypeData = new ArrayList<CarTypeData>();
//    public static  List<CarModelData> carModelData = new ArrayList<CarModelData>();
    public static boolean UpdataCarModel;
    public static int OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表

    public static final String Result_Key = "result_key";
    public static final String Param_Key = "param_key";
    public static final String Param_Key_Two = "param_key_two";
    public static final String Param_Key_Three = "param_key_three";
//    public static List<CityData> data=new ArrayList<CityData>();

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
//    screenWidth = 720;
//    screenHeight = 1280;

        GUID = null;
        isbreadCastLogin = false;
        Username = null; // 登陆的用户名
        MasterId = null; // 教练ID
        MasterJiaxiao = null; // 驾校名称
        MasterName = null;// 教练用户名
        MasterShowName = null;// 教练显示的名称
        LocationStatus = 0; // -1 -定位失败，0 -未定位，1 -定位成功
        CityDM = null; // 选择的城市DM
        bdLocation = null; // 百度定位信息
        Lng = 0; // 经度
        Lat = 0; // 纬度

        // 认证的全局变量
//        accountStatus = null; // 获取账号状态信息
//        myCollCoachUUIDList = null; // 已收藏教练UUID列表
//        trainStatusData = null; // 培训状态
//        stuAuthInfoData = null; // 学员填写资料(陪练时要求填写或者驾校认证)
//        ShareContentData = null; // 分享内容获取
        StatusRenZheng = 4;// 是否已经认证,1已认证, 2认证中（可以重新提交认证信息）, 3未认证(已删除), 4认证失败（可以重新提交认证信息）
        StatusEmail = 3; // 1已绑定, 2绑定中, 3未绑定
        StatusRenZhengName = null;
        StatusEmailName = null;
        Email = null;
        IDCardNum = null;
        Name = null;
        OperateType = 0; // 是否可操作车管预约 ,0-不能操作, 1-批次列表,2-学员列表
        shareredpoint=false;
//        carTypeData.clear();
//        carModelData.clear();

        HomeFragment.gPScarList=null;
        HomeFragment.carListData=null;
        HomeFragment.currentMarkerNum=0;
        //CarListFragment.tempGpsInfoList=null;
    }
}
