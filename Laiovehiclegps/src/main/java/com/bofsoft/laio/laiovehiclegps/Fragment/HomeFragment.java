package com.bofsoft.laio.laiovehiclegps.Fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
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
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.laiovehiclegps.Activity.BNDemoGuideActivity;
import com.bofsoft.laio.laiovehiclegps.Activity.SmsActivity;
import com.bofsoft.laio.laiovehiclegps.Adapter.ArrayAdapter;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoData;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPSInfoList;
import com.bofsoft.laio.laiovehiclegps.DataClass.GPScarList;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.laiovehiclegps.Tools.Order;
import com.bofsoft.laio.laiovehiclegps.Widget.IndexTabBarView;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;
import com.bofsoft.sdk.utils.Loading;
import com.bofsoft.sdk.widget.base.BaseFragment;
import com.bofsoft.sdk.widget.base.Event;
import com.bofsoft.sdk.widget.tabbar.BaseTabBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 车辆监控
 * Created by szw on 2017/2/16.
 */

public class HomeFragment extends BaseFragment implements BaseTabBar.StateListener{

    public BaseTabBar tabbar;
   public List<View> tabs = new ArrayList<View>();
   public BaseFragment[] fragments = new BaseFragment[2];
    public boolean isFirstIn = true;
    public int curIndex = 0;
    private FrameLayout frameLayout;
    private FragmentManager fm;

    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, false);
        if (getView() != null)
            return;
        setContentView(R.layout.fragment_home);
        init();
    }



    private void init() {
        tabbar = (BaseTabBar) getView().findViewById(R.id.tabbar);
        frameLayout = (FrameLayout) getView().findViewById(R.id.hf_FrameLayout);

       tabbar.setAutoWidth();
       tabbar.setGravity(Gravity.BOTTOM);
       tabbar.setStateListener(this);
       tabbar.setAutoWidth();

    initTabs();
    }

   private void initTabs() {
        tabs.add(new IndexTabBarView(getActivity().getApplicationContext(), R.layout.index_tab_layout));
        tabs.add(new IndexTabBarView(getActivity().getApplicationContext(), R.layout.index_tab_layout));

        ((IndexTabBarView) tabs.get(0)).setResource(R.mipmap.index_statemonitor,
                R.mipmap.index_statemonitor_focus, "车辆监控");
        ((IndexTabBarView) tabs.get(1)).setResource(R.mipmap.index_carlist,
                R.mipmap.index_carlist_focus, "车辆列表");

        tabbar.setPosition(0);
        tabbar.setTabViews(tabs);

    }

    public void setFragment(int position, Class<? extends BaseFragment> fClass) {
        if (position < 0 || position > 2)//
            return;
//        curIndex = position;
        BaseFragment fragment = fragments[position];
        if (fragment == null) {
            try {
                fragment = fClass.newInstance();
                fragments[position] = fragment;
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
//        if(isFinishing()) return;
//        getSupportFragmentManager().beginTransaction().replace(R.id.index_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        if (!fragment.isAdded()) {
            if (isFirstIn) {
                isFirstIn = false;
               // getActivity().getSupportFragmentManager().beginTransaction().add(R.id.index_content, fragment, fragment.getClass().getName()).commit();
            } else {
               // getActivity().getSupportFragmentManager().beginTransaction().hide(fragments[curIndex]).add(R.id.index_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            getActivity().getSupportFragmentManager().beginTransaction().hide(fragments[curIndex]).show(fragment).commit();
        }
//        .setCustomAnimations(
//                R.animator.fragment_slide_right_in, R.animator.fragment_slide_left_out,
//                R.animator.fragment_slide_left_in, R.animator.fragment_slide_right_out)
        curIndex = position;
        if (curIndex == 0) {
            hideActionBar();//隐藏工具栏
        }
        if (curIndex == 1) {
            hideActionBar();
            //showActionBar();
        }
    }

   //底部栏切换
    @Override
    public void state(View v, int position, BaseTabBar.tabBarState state) {
        if (state == BaseTabBar.tabBarState.SELECTED) {
            switch (position) {
                case 0:
//                    isLogin = false;
                    //setFragment(position, HomeFragment.class);

                    break;
                case 1:
//                    if (isLogin = Member.tipLogin(IndexActivity.this))
//                        return;
                    setFragment(position, CarListFragment.class);
                    break;
            }
            ((IndexTabBarView) v).setSelected();
        } else {
//            if (!isLogin)
            ((IndexTabBarView) v).setNormal();
        }
    }



    @Override
    protected void setTitleFunc() {
        setTitle("来噢租车管理",24, Color.BLACK);
    }

    @Override
    protected void setActionBarButtonFunc() {
        showActionBar();
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }

}
