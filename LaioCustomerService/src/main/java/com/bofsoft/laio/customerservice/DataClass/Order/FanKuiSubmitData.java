package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

public class FanKuiSubmitData {
    // 请求参数
    private String SoftVersion;
    private String SystemType; // 操作系统，1=android，2=ios
    private String SystemVersion; // 操作系统版本号
    private String PhoneManufacturers; // 手机厂商
    private String PhoneModel; // 手机型号
    private int ObjectType; // 0学员，1教练
    private String Content; // 反馈内容（内容长度大于等于10个字符，小于等于500个字符）

    // 返回参数

    private int Code; // 提交状态，1成功，0不成功
    private String MsgContent; // 显示的消息

    // 方法

    /**
     * @return 提交反馈参数
     * @throws JSONException
     */
    public String getFanKuiSubmitData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("SoftVersion", SoftVersion);
        js.put("SystemType", SystemType);
        js.put("SystemVersion", SystemVersion);
        js.put("PhoneManufacturers", PhoneManufacturers);
        js.put("PhoneModel", PhoneModel);
        js.put("ObjectType", ObjectType);
        js.put("Content", Content);

        return js.toString();
    }

    public static FanKuiSubmitData initData(JSONObject js) throws JSONException {
        FanKuiSubmitData data = new FanKuiSubmitData();
        data.Code = js.getInt("Code");
        data.MsgContent = js.getString("Content");
        return data;
    }

    public String getSoftVersion() {
        return SoftVersion;
    }

    public void setSoftVersion(String softVersion) {
        SoftVersion = softVersion;
    }

    public String getSystemType() {
        return SystemType;
    }

    public void setSystemType(String systemType) {
        SystemType = systemType;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public String getPhoneManufacturers() {
        return PhoneManufacturers;
    }

    public void setPhoneManufacturers(String phoneManufacturers) {
        PhoneManufacturers = phoneManufacturers;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public int getObjectType() {
        return ObjectType;
    }

    public void setObjectType(int objectType) {
        ObjectType = objectType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

}
