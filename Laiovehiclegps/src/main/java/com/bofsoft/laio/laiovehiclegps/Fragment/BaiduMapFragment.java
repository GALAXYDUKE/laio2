package com.bofsoft.laio.laiovehiclegps.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.navisdk.adapter.BNOuterLogUtil;
import com.baidu.navisdk.adapter.BNOuterTTSPlayerCallback;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.laiovehiclegps.Activity.BNDemoGuideActivity;
import com.bofsoft.laio.laiovehiclegps.Adapter.ArrayAdapter;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoData;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoList;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPScarList;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.utils.Loading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 百度地图碎片
 * Created by Bofsoft on 2017/5/16.
 */

public class BaiduMapFragment extends Fragment implements View.OnClickListener,IResponseListener{
    MyLog mylog = new MyLog(BaiduMapFragment.class);
    /**
     * 导航相关
     */
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    private String mSDCardPath = null;
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";
    String authinfo = null;
    /**
     * 地图定位相关
     */
    public MapView mMapView;
    public BaiduMap mBaiduMap;
    // 定位
    public LocationClient mLocClient;
    public BaiduMapFragment.MyLocationListenner myListener = new BaiduMapFragment.MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    boolean isFirstLoc = true; // 是否首次定位
    // 定位结果
    LatLng curLocation;
    //    LatLng pt;
//    LatLng desLatLng;//转换成百度坐标
    String addrName = "";
    //是否开启了路况
    public boolean isTraffic = false;
    //是否正在追踪
    public boolean isTrack = false;
    //是否显示气泡
    public boolean isPopWindowShow = false;

    private GPSInfoData mMarkerTemp;
    private GPSInfoData mShowMarker;
    public List<Marker> mList;
    private InfoWindow mInfoWindow;

    /**
     * 控件相关
     */
    public RelativeLayout ll_search;
    public ImageView iv_search;
    public ImageView iv_msg;
    public TextView item_tips;

    //    public EditText et_search;
    public AutoCompleteTextView autotext;
    public ImageView iv_normal;//标准地图
    public ImageView iv_satellite;//卫星地图
    public ImageView iv_traffic;//开启or关闭路况
    public ImageView iv_right;//下一辆车
    public ImageView iv_left;//上一次辆车
    public ImageView iv_popwindow;//弹出车辆信息
    public ImageView iv_location;//定位当前位置
    public LinearLayout ll_distance;
    public TextView tv_distance;

    //    TextView tv_address;//气泡内 地址
    private ArrayAdapter<String> arrayAdapter;
    private String[] arr;
    private StringBuffer Devices = new StringBuffer();//设备号
    public static GPScarList gPScarList;
    private String[] devicesArr;
    Bitmap bitmap;
    public boolean hasPopAddress;
    public boolean hasRefresh;
    public String Deviceid;
    Map<String, String> map = new HashMap<String, String>();

    /**
     * 地图反解析
     */
//    public static GeoCoder mSearch = null; //

    public static int currentMarkerNum = 0;

    public int[] imgIds = {R.mipmap.marker_status1,
            R.mipmap.marker_status2,
            R.mipmap.marker_status3,
            R.mipmap.marker_status4};

    BitmapDescriptor bd_temp;

    public static GPSInfoList carListData = new GPSInfoList();
    public int changNum = 0;
    public boolean isMontori;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.fragment_baidumap,null,false);
        init(view);
        //        mSearch = GeoCoder.newInstance();
//        mSearch.setOnGetGeoCodeResultListener(this);
        BNOuterLogUtil.setLogSwitcher(true);// 打开log开关
        if (initDirs()) {
            initNavi();
        }
        return view;

    }

    public void setMsgCnt(int cnt) {
        if (item_tips != null) {
            if (cnt > 0) {
                item_tips.setVisibility(View.VISIBLE);
            } else {
                item_tips.setVisibility(View.GONE);
            }
            if (cnt > 99) {
                item_tips.setText("...");
            } else {
                item_tips.setText(cnt + "");
            }
        }
    }

    private void init(View v) {
        findView(v);
    }

    private void findView(View v) {
        mMapView = (MapView) v.findViewById(R.id.bmapView);
        ll_search = (RelativeLayout) v.findViewById(R.id.ll_search);
        iv_search = (ImageView) v.findViewById(R.id.iv_search);
        item_tips = (TextView) v.findViewById(R.id.item_tips);
//        et_search = (EditText) getView().findViewById(R.id.et_search);
        autotext = (AutoCompleteTextView) v.findViewById(R.id.autotext);

        iv_normal = (ImageView) v.findViewById(R.id.iv_normal);
        iv_satellite = (ImageView) v.findViewById(R.id.iv_satellite);
        iv_traffic = (ImageView) v.findViewById(R.id.iv_traffic);

        iv_right = (ImageView) v.findViewById(R.id.iv_right);
        iv_left = (ImageView) v.findViewById(R.id.iv_left);
        iv_popwindow = (ImageView) v.findViewById(R.id.iv_popwindow);
        iv_location = (ImageView) v.findViewById(R.id.iv_location);

        ll_distance = (LinearLayout) v.findViewById(R.id.ll_distance);
        tv_distance = (TextView) v.findViewById(R.id.tv_distance);

        iv_search.setOnClickListener(this);
        iv_normal.setOnClickListener(this);
        iv_satellite.setOnClickListener(this);
        iv_traffic.setOnClickListener(this);
        iv_right.setOnClickListener(this);
        iv_left.setOnClickListener(this);
        iv_popwindow.setOnClickListener(this);
        iv_location.setOnClickListener(this);

        autotext.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < carListData.InfoList.size(); i++) {
                    GPSInfoData tmpData = carListData.InfoList.get(i);
                    if (autotext.getText().toString().equals(tmpData.License)) {
                        mBaiduMap.hideInfoWindow();//隐藏气泡
                        currentMarkerNum = i;
                        showLocation(tmpData);
                        break;
                    }
                }
            }
        });

        mBaiduMap = mMapView.getMap();

        clearOverlay();
        openLocation();

        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                //地图点击事件
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                //地图poi点击事件
                return false;
            }
        });

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //地图marker点击事件
                showLocation(getGPSInfoDataByMake(marker));
                return true;
            }
        });

//        addOverlay(null);
//        GPS_Monitor(true);
        Loading.show(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DanweiNum", ConfigAll.getUserERPDanwei());//ConfigAll.getUserERPDanwei()
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.GPS_GETCARLIST, jsonObject.toString(), this);
    }

    private GPSInfoData getGPSInfoDataByMake(Marker marker) {
        String License = marker.getExtraInfo().getString("License");
        for (GPSInfoData info : carListData.InfoList) {
            if (License.equalsIgnoreCase(info.getLicense())) {
                return info;
            }
        }
        return null;
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initNavi() {

        BNOuterTTSPlayerCallback ttsCallback = null;

        BaiduNaviManager.getInstance().init(getActivity(), mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getActivity(), authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            public void initSuccess() {//百度导航引擎初始化成功
                initSetting();
            }

            public void initStart() {//百度导航引擎初始化开始
            }

            public void initFailed() {
                Toast.makeText(getActivity(), "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }

        }, null, ttsHandler, ttsPlayStateListener);

    }

    private void initSetting() {
        // 设置是否双屏显示
        BNaviSettingManager.setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        // 设置导航播报模式
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // 是否开启路况
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
    }

    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
//                    showToastMsg("Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
//                    showToastMsg("Handler : TTS play end");
                    break;
                }
                default:
                    break;
            }
        }
    };

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
//            showToastMsg("TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
//            showToastMsg("TTSPlayStateListener : TTS play start");
        }
    };

    public void showToastMsg(final String msg) {
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * //显示气泡
     */
    private void showLocation(final GPSInfoData gpsInfoData) {
        int i = 0;
        if (gpsInfoData == null) {
            return;
        }
        //设置到maker到最上面去
        if(mList != null) {
            for (i = 0; i < mList.size(); i++) {
                Marker marker = mList.get(i);
                if (marker.getExtraInfo().getString("License").equalsIgnoreCase(gpsInfoData.License)) {
                    marker.setToTop();
                    break;
                }
            }
        }
//        // 创建InfoWindow展示的view
//        if (marker == null) {
//            return;
//        }
//        for (i = 0; i < mList.size(); i++) {
//            if (marker == mList.get(i)) {
//                currentMarkerNum = i;
//                break;
//            }
//        }

//        latitude = carListData.InfoList.get(currentMarkerNum).Latitude;
//        longitude = carListData.InfoList.get(currentMarkerNum).Longitude;

//        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        LatLng tmpLL = new LatLng(gpsInfoData.getLatitude(), gpsInfoData.getLongitude());
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(tmpLL);
        final LatLng pt = converter.convert();

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.map_popwindow, null); //自定义气泡形状
        ImageView iv_close = (ImageView) view.findViewById(R.id.iv_close);
        TextView tv_CarLicense = (TextView) view.findViewById(R.id.tv_CarLicense);

        LinearLayout ll_Navigation = (LinearLayout) view.findViewById(R.id.ll_Navigation);

        LinearLayout ll_Track = (LinearLayout) view.findViewById(R.id.ll_Track);
        final ImageView iv_Track = (ImageView) view.findViewById(R.id.iv_Track);
        final TextView tv_Track = (TextView) view.findViewById(R.id.tv_Track);

        TextView tv_equipmentTime = (TextView) view.findViewById(R.id.tv_equipmentTime);
        TextView tv_status = (TextView) view.findViewById(R.id.tv_status);
        TextView tv_speed = (TextView) view.findViewById(R.id.tv_speed);
        TextView tv_latlong = (TextView) view.findViewById(R.id.tv_latlong);
        TextView tv_address = (TextView) view.findViewById(R.id.tv_address);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.hideInfoWindow();//隐藏气泡
                isPopWindowShow = false;
                iv_normal.setVisibility(View.VISIBLE);
                iv_satellite.setVisibility(View.VISIBLE);
                iv_traffic.setVisibility(View.VISIBLE);
            }
        });
        tv_CarLicense.setText(carListData.InfoList.get(currentMarkerNum).License);
        ll_Navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //导航
                isTrack = false;
                iv_Track.setImageResource(R.mipmap.popwindow_track_on);
                tv_Track.setText("追踪");
                GPS_Monitor(false);
                ll_distance.setVisibility(View.GONE);
                mMarkerTemp = null;
                routeplanToNavi(BNRoutePlanNode.CoordinateType.BD09LL);
            }
        });
        if (mMarkerTemp != null && mMarkerTemp.getLicense().equalsIgnoreCase(gpsInfoData.getLicense())) {
            if (ll_distance.getVisibility() == View.VISIBLE) {
                isTrack = true;
                iv_Track.setImageResource(R.mipmap.popwindow_track_off);
                tv_Track.setText("追踪中");
            }
        } else {
            if (ll_distance.getVisibility() == View.VISIBLE) {
                GPS_Monitor(false);
                ll_distance.setVisibility(View.GONE);
                mMarkerTemp = null;
            }
        }

        ll_Track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //追踪
                if (isTrack) {
                    isTrack = false;
                    iv_Track.setImageResource(R.mipmap.popwindow_track_on);
                    tv_Track.setText("追踪");
                    GPS_Monitor(false);
                    ll_distance.setVisibility(View.GONE);
                    mMarkerTemp = null;
                } else {
                    isTrack = true;
                    iv_Track.setImageResource(R.mipmap.popwindow_track_off);
                    tv_Track.setText("追踪中");
                    GPS_Monitor(true);
                    ll_distance.setVisibility(View.VISIBLE);
                    tv_distance.setText(getDistance(curLocation, pt));
                    mMarkerTemp = gpsInfoData;
                }
                showLocation(gpsInfoData);
            }
        });

        tv_equipmentTime.setText(carListData.InfoList.get(currentMarkerNum).Datetime);
        if (carListData.InfoList.get(currentMarkerNum).Status == 0) {
            tv_status.setText("正常");
        } else {
            tv_status.setText(carListData.InfoList.get(currentMarkerNum).StatusContent);
        }
        tv_speed.setText(carListData.InfoList.get(currentMarkerNum).Speed + "km/h");
        tv_latlong.setText(gpsInfoData.getLongitude() + "," + gpsInfoData.getLatitude() + " 方向:" + carListData.InfoList.get(currentMarkerNum).getDirectionStr());
        hasPopAddress = true;
//        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(pt));
        if (gpsInfoData.getAddress() == null || gpsInfoData.getAddress().length() == 0) {
            HomeFragment.LoadAddHelper loadAddHelper = new HomeFragment.LoadAddHelper(gpsInfoData);
            loadAddHelper.loadReverseGeoCode();
        }
        tv_address.setText("位        置：   " + (carListData.InfoList.get(currentMarkerNum).getAddress() == null ?
                "" : carListData.InfoList.get(currentMarkerNum).getAddress()));

        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(pt);//.zoom(18)
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

        // 创建InfoWindow
        mInfoWindow = new InfoWindow(view, pt, -30);
        iv_normal.setVisibility(View.GONE);
        iv_satellite.setVisibility(View.GONE);
        iv_traffic.setVisibility(View.GONE);
        mBaiduMap.showInfoWindow(mInfoWindow); //显示气泡
        isPopWindowShow = true;
        mShowMarker = gpsInfoData;
    }

    private void GPS_Monitor(boolean onorOff) {
        ll_distance.setVisibility(View.GONE);
        JSONObject jsonObject = new JSONObject();
        try {
            if (onorOff) {
                jsonObject.put("Devices", carListData.InfoList.get(currentMarkerNum).Deviceid);//123456789012345
            } else {
                jsonObject.put("Devices", "");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.GPS_GETGPSDATA_MONITOR, jsonObject.toString(), this);
    }

    /**
     * 进入导航传入数据
     */
    private void routeplanToNavi(BNRoutePlanNode.CoordinateType coType) {
        BNRoutePlanNode sNode = null;//起始位置
        BNRoutePlanNode eNode = null;//目标位置
//        BNRoutePlanNode mNode = null;//途经点
        if (curLocation == null) {
            showToastMsg("手机尚未定位，不能跟踪！请到开阔地方去。");
            return;
        }
        sNode = new BNRoutePlanNode(curLocation.longitude, curLocation.latitude, addrName, null, coType);
//        mNode = new BNRoutePlanNode(104.070277,30.600337, "高新地铁站", null, coType);
        eNode = new BNRoutePlanNode(carListData.InfoList.get(currentMarkerNum).Longitude, carListData.InfoList.get(currentMarkerNum).Latitude, carListData.InfoList.get(currentMarkerNum).getAddress(), null, coType);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
//            list.add(mNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(getActivity(), list, 1, true, new BaiduMapFragment.DemoRoutePlanListener(sNode));
        }
    }

    //    //地址解析
//    @Override
//    public void onGetGeoCodeResult(GeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//
//        String strInfo = String.format("纬度：%f 经度：%f",
//                result.getLocation().latitude, result.getLocation().longitude);
////        Toast.makeText(getActivity(), strInfo, Toast.LENGTH_LONG).show();
//    }
//
//    //经纬度反解析
//    @Override
//    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
//        if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
//                    .show();
//            return;
//        }
//        if (hasPopAddress) {
//            hasPopAddress = false;
//            tv_address.setText("位        置：   " + (result.getAddress() == null ? "" : result.getAddress()));
//            return;
//        }
//
//        if (isMontori) {//追踪时 改变 位置
//            isMontori = false;
//            carListData.InfoList.get(currentMarkerNum).setAddress(result.getAddress());
//            tv_address.setText("位        置：   " + (result.getAddress() == null ? "" : result.getAddress()));
//            return;
//        }
//        for (int i = 0; i <= carListData.InfoList.size() - 1; i++) {
//            if (carListData.InfoList.get(i).getAddress() == null || carListData.InfoList.get(i).getAddress() == "") {
//                carListData.InfoList.get(i).setAddress(result.getAddress());
//
//                break;
//            }
//        }
////        recGeoCoderAddress = result.getAddress();
////        tv_address.setText("位        置：   "+recGeoCoderAddress);
////        Toast.makeText(getActivity(), result.getAddress(),
////                Toast.LENGTH_LONG).show();
//
//    }

    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {
            /*
             * 设置途径点以及resetEndNode会回调该接口
			 */
//            for (Activity ac : activityList) {
//
//                if (ac.getClass().getName().endsWith("BNDemoGuideActivity")) {
//
//                    return;
//                }
//            }
            Intent intent = new Intent(getActivity(), BNDemoGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(getActivity(), "算路失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void addOverlay(GPSInfoData gpsInfoData) {//添加marker 需要经纬度 车牌号
        LatLng llA = new LatLng(gpsInfoData.Latitude, gpsInfoData.Longitude);
        // 将GPS设备采集的原始GPS坐标转换成百度坐标
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        // sourceLatLng待转换坐标
        converter.coord(llA);
//        desLatLng = converter.convert();
        LatLng tmpLL = converter.convert();


        View v_temp = LayoutInflater.from(getActivity()).inflate(R.layout.map_marker, null);//加载自定义的布局
        TextView tv_temp = (TextView) v_temp.findViewById(R.id.tv_marker);//获取自定义布局中的textview
        ImageView img_temp = (ImageView) v_temp.findViewById(R.id.iv_marker);//获取自定义布局中的imageview
        tv_temp.setText(gpsInfoData.License);//设置要显示的文本
        if (gpsInfoData.Status == 0) {
            img_temp.setImageResource(imgIds[0]);//设置marker的图标
            bitmap = BitmapFactory.decodeResource(getResources(), imgIds[0]);
        } else {
            img_temp.setImageResource(imgIds[2]);//设置marker的图标
            bitmap = BitmapFactory.decodeResource(getResources(), imgIds[2]);
        }
        Matrix matrix = new Matrix();
        img_temp.setScaleType(ImageView.ScaleType.MATRIX);   //required
        matrix.postScale(1, 1);
        //获得ImageView中Image的真实宽高，
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        matrix.postRotate((float) gpsInfoData.Direction, (float) dw / 2, (float) dh / 2);//旋转角度，旋转中心
        img_temp.setImageMatrix(matrix);
        bd_temp = BitmapDescriptorFactory.fromView(v_temp);//用到了这个实例化方法来把自定义布局实现到marker中。
        MarkerOptions oo = new MarkerOptions().position(tmpLL).icon(bd_temp).anchor(0.5f, 1.0f).zIndex(15);
        Marker mMarkerA = (Marker) (mBaiduMap.addOverlay(oo));
        Bundle bundle = new Bundle();
        bundle.putString("License", gpsInfoData.getLicense());
        mMarkerA.setExtraInfo(bundle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_search:
                break;
            /*case R.id.iv_msg:
                Intent intent = new Intent();
                intent.setClass(getActivity(), SmsActivity.class);
                startActivity(intent);
                break;*/
            case R.id.iv_normal:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                iv_normal.setImageResource(R.mipmap.index_normal_on);
                iv_satellite.setImageResource(R.mipmap.index_satellite_off);
                break;
            case R.id.iv_satellite:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                iv_satellite.setImageResource(R.mipmap.index_satellite_on);
                iv_normal.setImageResource(R.mipmap.index_normal_off);
                break;
            case R.id.iv_traffic:
                if (isTraffic) {
                    isTraffic = false;
                    mBaiduMap.setTrafficEnabled(false);
                    iv_traffic.setImageResource(R.mipmap.index_traffic_off);
                } else {
                    isTraffic = true;
                    mBaiduMap.setTrafficEnabled(true);
                    iv_traffic.setImageResource(R.mipmap.index_traffic_on);
                }
                break;
            case R.id.iv_right:
                if (mList == null || mList.size() == 0) {
                    return;
                }
                currentMarkerNum++;
                if (currentMarkerNum > mList.size() - 1) {
                    currentMarkerNum = 0;
                }
                mBaiduMap.hideInfoWindow();//隐藏气泡
                isPopWindowShow = false;
                iv_normal.setVisibility(View.VISIBLE);
                iv_satellite.setVisibility(View.VISIBLE);
                iv_traffic.setVisibility(View.VISIBLE);
                showLocation(carListData.InfoList.get(currentMarkerNum));
                break;
            case R.id.iv_left:
                if (mList == null || mList.size() == 0) {
                    return;
                }
                currentMarkerNum--;
                if (currentMarkerNum < 0) {
                    currentMarkerNum = mList.size() - 1;
                }
                mBaiduMap.hideInfoWindow();//隐藏气泡
                isPopWindowShow = false;
                iv_normal.setVisibility(View.VISIBLE);
                iv_satellite.setVisibility(View.VISIBLE);
                iv_traffic.setVisibility(View.VISIBLE);
                showLocation(carListData.InfoList.get(currentMarkerNum));
                break;
            case R.id.iv_popwindow:
                if (mList == null || mList.size() == 0) {
                    return;
                } else {
                    mBaiduMap.hideInfoWindow();//隐藏气泡
                    iv_normal.setVisibility(View.VISIBLE);
                    iv_satellite.setVisibility(View.VISIBLE);
                    iv_traffic.setVisibility(View.VISIBLE);
                    if (isPopWindowShow == false) {
                        showLocation(carListData.InfoList.get(currentMarkerNum));
                    } else {
                        isPopWindowShow = false;
                    }
                }
                break;
            case R.id.iv_location:
                isFirstLoc = true;
                openLocation();
                break;
        }
    }

    public void carListFragmentshowPop(String gpsDeviceid) {
        for (int i = 0; i < carListData.InfoList.size(); i++) {
            if (carListData.InfoList.get(i).Deviceid.equals(gpsDeviceid)) {
                currentMarkerNum = i;
                break;
            }
        }
        if (mList == null || mList.size() == 0) {
            return;
        } else {
            mBaiduMap.hideInfoWindow();//隐藏气泡
            iv_normal.setVisibility(View.VISIBLE);
            iv_satellite.setVisibility(View.VISIBLE);
            iv_traffic.setVisibility(View.VISIBLE);
            showLocation(carListData.InfoList.get(currentMarkerNum));
            isPopWindowShow = true;
        }
    }

    public static void changePop() {
        mBaiduMap.hideInfoWindow();//隐藏气泡
        isMontori = true;
        carListData.InfoList.get(currentMarkerNum).setLicense(map.get(carListData.InfoList.get(currentMarkerNum).Deviceid));
        LatLng llNew = new LatLng(carListData.InfoList.get(currentMarkerNum).getLatitude(), carListData.InfoList.get(currentMarkerNum).getLongitude());
        // 反Geo搜索
//        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(llNew));
        HomeFragment.LoadAddHelper loadAddHelper = new HomeFragment.LoadAddHelper(carListData.InfoList.get(currentMarkerNum));
        loadAddHelper.loadReverseGeoCode();
        CoordinateConverter converter = new CoordinateConverter();
        converter.from(CoordinateConverter.CoordType.GPS);
        converter.coord(llNew);
        llNew = converter.convert();

        mList.get(currentMarkerNum).setPosition(llNew);
        if (changNum == 5) {
            MapStatus.Builder builder = new MapStatus.Builder();
            builder.target(llNew).zoom(15);
            mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            changNum = 0;
        }
        showLocation(carListData.InfoList.get(currentMarkerNum));
        tv_distance.setText(getDistance(curLocation, llNew));
        changNum++;
    }

    /**
     * 开启定位
     */
    public void openLocation() {
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(getActivity());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(15000);
        option.setIsNeedAddress(true);
        mLocClient.setLocOption(option);
        mLocClient.start();

        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
        mBaiduMap
                .setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
    }
    /**
     * 清楚地址所有标注
     */
    public void clearOverlay() {
        if (mMapView != null) {
            mBaiduMap.clear();
        }
    }


    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData =
                    new MyLocationData.Builder().accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                            .direction(location.getDirection()).latitude(location.getLatitude()).longitude(location.getLongitude())
                            .build();
            mBaiduMap.setMyLocationData(locData);
            addrName = location.getAddrStr();

            curLocation = new LatLng(location.getLatitude(), location.getLongitude());
            if (isFirstLoc) {
                isFirstLoc = false;

                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(curLocation).zoom(15);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    @Override
    public void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        if (mMapView != null)
            mMapView.onDestroy();
//        if (mSearch != null)
//            mSearch.destroy();
        super.onDestroy();
        if (bd_temp != null)
            bd_temp.recycle();
    }


    @Override
    public void onResume() {

        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
        super.onPause();
    }

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.GPS_GETCARLIST:
                Log.e("tag", "CARLIST:" + result);
                if (gPScarList != null) {
                    gPScarList = null;
                }
                gPScarList = JSON.parseObject(result, GPScarList.class);
                Devices.delete(0, Devices.length());
                for (int i = 0; i < gPScarList.CarList.size(); i++) {
                    if (i == 0) {
                        Devices.append(gPScarList.CarList.get(i).Deviceid);
                    } else {
                        Devices.append("," + gPScarList.CarList.get(i).Deviceid);
                    }
                }

                JSONObject jsonObject = new JSONObject();
                try {
                    Log.e("tag", "Devices.toString():" + Devices.toString());
                    jsonObject.put("Devices", Devices.toString());//Devices.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                DataLoadTask.getInstance().loadData(CommandCodeTS.GPS_GETGPSDATA, jsonObject.toString(), this);
                break;
            case CommandCodeTS.GPS_GETGPSDATA:
                Log.e("tag", "GETGPSDATA:" + result);
                Loading.close();
                if (!ConfigAll.isLogin)
                    return;
                if (carListData != null) {
                    carListData = null;
                }
                if (map != null) {
                    map = null;
                }
                carListData = JSON.parseObject(result, GPSInfoList.class);
                map = new HashMap<String, String>();
                if (carListData.InfoList == null) {
                    return;
                }
                devicesArr = new String[gPScarList.CarList.size()];
                for (int i = 0; i <= gPScarList.CarList.size() - 1; i++) {
                    map.put(gPScarList.CarList.get(i).getDeviceid(), gPScarList.CarList.get(i).License);
                    devicesArr[i] = gPScarList.CarList.get(i).getDeviceid();
                }
                for (int i = 0; i <= carListData.InfoList.size() - 1; i++) {
                    //将接口得到的数据与IndexActivity.gPScarList.CarList 组合
                    if (Arrays.asList(devicesArr).contains(carListData.InfoList.get(i).Deviceid)) {
                        carListData.InfoList.get(i).setLicense(map.get(carListData.InfoList.get(i).Deviceid));
                    }

                    LatLng ptCenter = new LatLng(carListData.InfoList.get(i).getLatitude(), carListData.InfoList.get(i).getLongitude());
                    // 反Geo搜索
                    //mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(ptCenter));
                    HomeFragment.LoadAddHelper loadAddHelper = new HomeFragment.LoadAddHelper(carListData.InfoList.get(i));
                    loadAddHelper.loadReverseGeoCode();
                    //添加marker
                    addOverlay(carListData.InfoList.get(i));
                }
                mList = mBaiduMap.getMarkersInBounds(mBaiduMap.getMapStatusLimit());//得到marker列表
                arr = new String[mList.size()];
                for (int i = 0; i <= carListData.InfoList.size() - 1; i++) {
                    arr[i] = carListData.InfoList.get(i).License;
                }
                arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arr);
                autotext.setAdapter(arrayAdapter);

                if (hasRefresh) {
                    carListFragmentshowPop(Deviceid);
                    hasRefresh = false;
                }

                break;
            case CommandCodeTS.GPS_GETGPSDATA_MONITOR:
                Log.e("tag", "MONITOR:" + result);
                break;
        }

    }

    @Override
    public void messageBack(int code, int lenght, int tcplenght) {
    }

    @Override
    public void messageBackFailed(int errorCode, String error) {
        Log.e("tag", "error:" + error);
    }

    /**
     * 计算两点之间距离
     *
     * @param start
     * @param end
     * @return 米
     */
    public String getDistance(LatLng start, LatLng end) {

        //两点间距离 m，如果想要km的话，结果/1000
        double d = DistanceUtil.getDistance(start, end);
        if (d < 0) {
            return "无法定位。";
        }
        if (d < 1000)
            return "人车相距" + (int) d + "米";
        else
            return "人车相距" + String.format("%.2f", d / 1000) + "公里";
    }

    public void reFresh(String gpsDeviceid) {
        Deviceid = gpsDeviceid;
        hasRefresh = true;
        mBaiduMap.hideInfoWindow();//隐藏气泡
        isFirstLoc = true;
        isPopWindowShow = false;
        clearOverlay();
        Loading.show(getActivity());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("DanweiNum", ConfigAll.getUserERPDanwei());//ConfigAll.getUserERPDanwei()
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.GPS_GETCARLIST, jsonObject.toString(), this);
    }

    public class LoadAddHelper implements OnGetGeoCoderResultListener {
        GPSInfoData gpsInfoData;

        public LoadAddHelper(GPSInfoData gpsInfoData) {
            this.gpsInfoData = gpsInfoData;
        }

        public void loadReverseGeoCode() {
            GeoCoder search = GeoCoder.newInstance();
            search.setOnGetGeoCodeResultListener(this);
            LatLng tmpLL = new LatLng(gpsInfoData.getLatitude(), gpsInfoData.getLongitude());
            // 将GPS设备采集的原始GPS坐标转换成百度坐标
            CoordinateConverter converter = new CoordinateConverter();
            converter.from(CoordinateConverter.CoordType.GPS);
            converter.coord(tmpLL);
            tmpLL = converter.convert();
            search.reverseGeoCode(new ReverseGeoCodeOption().location(tmpLL));
        }

        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        //经纬度反解析
        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                Toast.makeText(getActivity(), "抱歉，未能找到结果", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            mylog.i(result.getAddress());
            gpsInfoData.setAddress(result.getAddress());
            if (mShowMarker != null && gpsInfoData.Deviceid.equalsIgnoreCase(mShowMarker.Deviceid)) {
                showLocation(gpsInfoData);
            }
        }
    }
}
