package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 获取我的其他信息
 */
public class GetMyOtherInfoData {
    // 请求参数
    public int ObjectType; // 用户类型：0 学员 1 教练
    public String MasterUUID = ""; // 教练账号UUID，教练请求此参数内容可为空

    // 返回参数
    private String Introduce;

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String Introduce) {
        this.Introduce = Introduce;
    }

    // 方法
    public static GetMyOtherInfoData InitData(JSONObject js) throws JSONException {
        GetMyOtherInfoData tmp = new GetMyOtherInfoData();
        tmp.Introduce = js.isNull("Introduce") ? "" : js.getString("Introduce");

        return tmp;
    }

    public String getMyOtherInfoData() {
        JSONObject js = new JSONObject();
        try {
            js.put("ObjectType", ObjectType);
            js.put("MasterUUID", MasterUUID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

}
