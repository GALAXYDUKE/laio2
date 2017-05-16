package com.bofsoft.laio.customerservice.Common;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.Application.MyApplication;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.db.CityData;
import com.bofsoft.laio.customerservice.Database.PublicDBManager;
import com.bofsoft.laio.customerservice.Database.SqlUtils;
import com.bofsoft.laio.customerservice.Database.TableManager;

/**
 * 百度sdk定位 2014.10.25
 *
 * @author yedong
 */
public class BDLocationUtil {
    static MyLog mylog = new MyLog(BDLocationUtil.class);
    // 定位坐标系
    public final static String COOR_TYPE_GCJ = "gcj02";
    public final static String COOR_TYPE_BD09 = "bd09ll";
    public final static String COOR_TYPE_BD09LL = "bd09";

    // private final static int LOCATION_TIME_OUT = 5;

    private final static int CALLBACK_TYPE_LOCATION = 100;
    private final static int CALLBACK_TYPE_CITY = 101;

    private static boolean isTimeOut = true;
    private final static int TIME_OUT = 8 * 1000;

    private static LocationClient mLocationClient;
    private static LocationClientOption mLocClientOpt = new LocationClientOption();
    private static LocationMode mLocationMode = LocationMode.Hight_Accuracy;
    private static String mCoorType = COOR_TYPE_BD09LL;
    // private static String mCoorType = COOR_TYPE_GCJ;
    private static int mScanSpan = 3000;
    private static boolean mIsNeedAddr = true;

    private static LocationCallBack mLocationCallBack;
    private static CityCallBack mCityCallBack;

    public static class MyBDLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {

            switch (location.getLocType()) {
                // 定位成功
                case BDLocation.TypeGpsLocation:
                case BDLocation.TypeCacheLocation:
                case BDLocation.TypeOffLineLocation:
                case BDLocation.TypeNetWorkLocation:
                    // 保存定位信息
                    ConfigallCostomerService.LocationStatus = 1;
                    ConfigallCostomerService.bdLocation = location;

                    break;
                // 定位失败情况
                case BDLocation.TypeNone:
                case BDLocation.TypeCriteriaException:
                case BDLocation.TypeNetWorkException:
                case BDLocation.TypeOffLineLocationFail:
                case BDLocation.TypeOffLineLocationNetworkFail:
                case BDLocation.TypeServerError:
                    ConfigallCostomerService.LocationStatus = -1;
                    mylog.i("BDLocationUtil>>定位失败>>LocType>>" + location.getLocType());
                    break;
                default:
                    break;
            }

        }
    }

    public interface LocationCallBack {
        void onCallBack(BDLocation location);
    }

    public interface CityCallBack {
        /**
         * @param cityData 定位的城市
         */
        void onCityCallBack(CityData cityData);
    }

    public static Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case CALLBACK_TYPE_LOCATION:
                    if (isTimeOut) {
                        mLocationCallBack.onCallBack(null);
                    }
                    break;
                case CALLBACK_TYPE_CITY:
                    if (isTimeOut) {
                        mCityCallBack.onCityCallBack(null);
                    }
                    break;

                default:
                    break;
            }
        }

    };

    /**
     * 初始化定位
     */
    public static void initLocation(Context context) {
        mLocationClient = ((MyApplication) context.getApplicationContext()).mLocationClient;
        mLocClientOpt.setLocationMode(mLocationMode);// 设置定位模式
        mLocClientOpt.setCoorType(mCoorType); // 返回的定位结果是百度经纬度，默认值gcj02
        mLocClientOpt.setScanSpan(mScanSpan); // 设置发起定位请求的间隔时间为5000ms
        mLocClientOpt.setIsNeedAddress(mIsNeedAddr);
        // mLocClientOpt.setTimeOut(TIME_OUT);
        mLocationClient.setLocOption(mLocClientOpt);
    }

    /**
     * 发起百度定位请求
     *
     * @param context
     * @param callBack
     */
    public static void requestLocation(Context context, final LocationCallBack callBack) {
        mLocationCallBack = callBack;

        initLocation(context);
        isTimeOut = true;
        handler.sendEmptyMessageDelayed(CALLBACK_TYPE_LOCATION, TIME_OUT);
        mLocationClient.registerLocationListener(new MyBDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                super.onReceiveLocation(location);
                if (ConfigallCostomerService.LocationStatus == 1) {
                    isTimeOut = false;
                    callBack.onCallBack(location);
                }
            }
        });

        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    /**
     * 发起百度定位请求
     *
     * @param context
     * @param callBack
     */
    public static void requestLocation(Context context, final BDLocationUtil.CityCallBack callBack) {
        mCityCallBack = callBack;

        initLocation(context);
        isTimeOut = true;
        handler.sendEmptyMessageDelayed(CALLBACK_TYPE_CITY, TIME_OUT);
        mLocationClient.registerLocationListener(new MyBDLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation location) {
                super.onReceiveLocation(location);
                if (ConfigallCostomerService.LocationStatus == 1) {
                    printLocationInfo(location);
                    isTimeOut = false;
                    callBack.onCityCallBack(getCity(location));
                }
            }
        });

        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
    }

    public static void StopLocation() {
        if (mLocationClient != null && mLocationClient.isStarted()) {
            mLocationClient.stop();
        }
    }

    /**
     * 根据百度经纬度获取地区(本地数据库查找)
     *
     * @param location 百度定位信息
     * @return 定位城市DM，定位结果为查找到城市信息，返回默认城市DM（成都）
     */
    public static String parseLocation(BDLocation location) {
        String cityDM = ConfigallCostomerService.DefaultCityDM;
        Cursor cursor;
//    String[] selectionArg =
//        {String.valueOf(latitude), String.valueOf(longitude), String.valueOf(latitude),
//            String.valueOf(longitude)};
        if (location == null || location.getCity() == null) {
            String[] selectionArgdefault = {"成都市", "成都市"};
            cursor =
                    PublicDBManager.getInstance(MyApplication.context).rawQuery(SqlUtils.DistrictSql1,
                            selectionArgdefault);
        } else {
            String[] selectionArg = {location.getCity(), location.getCity()};
            cursor =
                    PublicDBManager.getInstance(MyApplication.context).rawQuery(SqlUtils.DistrictSql1,
                            selectionArg);
        }
        if (cursor.moveToFirst()) {
            cityDM = cursor.getString(cursor.getColumnIndex("DM"));
//      Log.e("tag", "cityDM:"+cityDM);
        }
        if (cursor != null) {
            cursor.close();
        }
        return cityDM;

    }

    public static CityData getCity() {
        if (!TextUtils.isEmpty(ConfigallCostomerService.CityDM)) {
            return getCityByCityDM(ConfigallCostomerService.CityDM);
        } else if (ConfigallCostomerService.bdLocation != null) {
            return getCity(ConfigallCostomerService.bdLocation);
        } else {
            return null;
        }
    }

    /**
     * 获取默认的城市
     *
     * @return
     */
    public static CityData getDefaultCity() {
        return getCityByCityDM(ConfigallCostomerService.DefaultCityDM);
    }

    /**
     * 根据地区代码获取城市
     *
     * @param districtDM
     * @return
     */

    public static CityData getCityByCityDM(String districtDM) {
        String[] cityArg = {districtDM};
        CityData cityData =
                PublicDBManager.getInstance(MyApplication.context).queryBySelection(CityData.class,
                        TableManager.Laio_City, "DM = ?", cityArg);
        mylog.i("BDLocationUtil>>CityData=" + cityData);
        return cityData;
    }

    /**
     * 根据百度定位结果获取城市
     *
     * @param location
     * @return
     */
    public static CityData getCity(BDLocation location) {
        return getCityByCityDM(parseLocation(location));
    }

    /**
     * 打印定位结果信息
     *
     * @param location
     */
    public static void printLocationInfo(BDLocation location) {
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("\nerror code : ");
        sb.append(location.getLocType());
        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());
        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());
        sb.append("\nradius : ");
        sb.append(location.getRadius());
        sb.append("\n省份");
        sb.append(location.getProvince());
        sb.append("\n城市");
        sb.append(location.getCity() + ",code= " + location.getCityCode());
        sb.append("\n区域");
        sb.append(location.getDistrict());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());
            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("\ndirection : ");
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            sb.append(location.getDirection());
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());
            // 运营商信息
            sb.append("\noperationers : ");
            sb.append(location.getOperators());
        }
        logMsg(sb.toString());
        Log.i("BaiduLocationApiDem", sb.toString());
    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public static void logMsg(String str) {
        mylog.i("定位结果：" + str);
    }

}
