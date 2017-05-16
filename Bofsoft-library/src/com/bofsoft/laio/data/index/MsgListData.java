package com.bofsoft.laio.data.index;

import android.content.Context;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.bofsoft.laio.data.BaseData;

/**
 * Created by szw on 2017/3/9.
 */

public class MsgListData extends BaseData implements OnGetGeoCoderResultListener {
    Context context;

    public Long rowId;
    public String FromM;//	String
    public int MsgID;//	Integer
    public String Msg;//	String
    public String Time;//	String
    public int Type;//	Integer
    public String Key;//	String
    public String ShowName;//	String

    public void SearchAddress(Long rowId, Context context) {
        if (mSearch == null) {
            mSearch = GeoCoder.newInstance();
            mSearch.setOnGetGeoCodeResultListener(this);
        }

        this.rowId = rowId;
        this.context = context;
        double Lat = 0.0d;
        double Lng = 0.0d;
        String[] temp = Key.split("&");
        if (temp.length >= 4) {
            String[] tempLat = temp[2].split("=");
            String[] tempLng = temp[3].split("=");
            if (tempLat.length >= 2) {
                Lat = Double.parseDouble(tempLat[1]);
            }
            if (tempLng.length >= 2) {
                Lng = Double.parseDouble(tempLng[1]);
            }
        }
        LatLng llNew = new LatLng(Lat, Lng);
        mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(llNew));//llNew从Msg分离出来的
    }

    public static GeoCoder mSearch = null; //

    @Override
    public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

    }

    @Override
    public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
//        Log.e("tag", "rowId:" + rowId + " ,address:" + reverseGeoCodeResult.getAddress());
//        Msg = Msg + "\r\n位置：" + reverseGeoCodeResult.getAddress();
//        MsgAdaper.getInstance(context).update(rowId, MsgID, FromM, Msg, Time,
//                Type, Key, ShowName, 0, ConfigAll.UserUUID, Func.formattime(new Date(), 1),
//                1, FromM);
    }
}
