package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我-教练获取我的教练信息（网络招生信息）- 训练场地
 *
 * @author admin
 */
public class MyInfoPlaceData {
    // 请求参数

    // 返回参数

    private String MasterChangdiInfo; // 训练场地详细信息

    // 方法

    public String getMyInfoPlaceData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

    public static MyInfoPlaceData initData(JSONObject js) throws JSONException {
        MyInfoPlaceData data = new MyInfoPlaceData();
        data.MasterChangdiInfo = js.getString("MasterChangdiInfo");
        return data;
    }

    public String getMasterChangdiInfo() {
        return MasterChangdiInfo;
    }

    public void setMasterChangdiInfo(String masterChangdiInfo) {
        MasterChangdiInfo = masterChangdiInfo;
    }

}
