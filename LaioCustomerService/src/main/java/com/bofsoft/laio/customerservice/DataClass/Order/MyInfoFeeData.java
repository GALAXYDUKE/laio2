package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 我-教练获取我的教练信息（网络招生信息）- 班次费用
 *
 * @author admin
 */
public class MyInfoFeeData {
    // 请求参数

    // 返回参数

    private List<Info> InfoList = new ArrayList<Info>();

    // 方法

    public String getMyInfoFeeData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

    public static List<Info> initDataList(JSONObject js) throws JSONException {
        List<Info> tempList = new ArrayList<Info>();
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            tempList.add(Info.InitData(tmpinfo.getJSONObject(i)));
        }
        return tempList;
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private int FYId; // 费用id
        private String FYInfo; // 费用介绍

        public String getMyInfoFeeDataInfo() throws JSONException {
            JSONObject js = new JSONObject();
            js.put("FYId", FYId);
            js.put("FYInfo", FYInfo);

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.FYId = js.getInt("FYId");
            tmp.FYInfo = js.getString("FYInfo");

            return tmp;
        }

        public int getFYId() {
            return FYId;
        }

        public void setFYId(int fYId) {
            FYId = fYId;
        }

        public String getFYInfo() {
            return FYInfo;
        }

        public void setFYInfo(String fYInfo) {
            FYInfo = fYInfo;
        }

    }

}
