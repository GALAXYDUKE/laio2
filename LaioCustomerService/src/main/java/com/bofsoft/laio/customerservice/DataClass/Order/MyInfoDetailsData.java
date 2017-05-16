package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我-教练获取我的教练信息（网络招生信息）- 教练详细信息
 *
 * @author admin
 */
public class MyInfoDetailsData {
    // 请求参数

    // 返回参数

    private String MasterInfo; // 教练详细信息

    // 方法

    public String getMyInfoDetailsData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

    public static MyInfoDetailsData initData(JSONObject js) throws JSONException {
        MyInfoDetailsData data = new MyInfoDetailsData();
        data.MasterInfo = js.getString("MasterInfo");
        return data;
    }

    public String getMasterInfo() {
        return MasterInfo;
    }

    public void setMasterInfo(String masterInfo) {
        MasterInfo = masterInfo;
    }

}
