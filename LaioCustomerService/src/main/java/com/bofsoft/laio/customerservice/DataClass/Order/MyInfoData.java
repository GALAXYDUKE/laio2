package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我-教练获取我的教练信息（网络招生信息）
 *
 * @author admin
 */
public class MyInfoData {
    // 请求参数

    // 返回参数

    private String MasterName; // 教练名字
    private String MasterJiaXiao; // 教练所属驾校
    private double MasterTuiJian; // 教练推荐指数
    private double MasterMaxTuiJian; // 教练推荐指数总分
    private int MasterCreditRank; // 教练的信用等级
    private String MasterInfo; // 教练简介信息
    private double EnrollmentAddrLng; // 招生地址的经度
    private double EnrollmentAddrLat; // 招生地址的纬度
    private String MasterPicURL; // 教练图片

    // 方法

    public String getMyInfoData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

    public static MyInfoData initData(JSONObject js) throws JSONException {
        MyInfoData data = new MyInfoData();
        data.MasterName = js.getString("MasterName");
        data.MasterJiaXiao = js.getString("MasterJiaXiao");
        data.MasterTuiJian = js.getDouble("MasterTuiJian");
        data.MasterMaxTuiJian = js.getDouble("MasterMaxTuiJian");
        data.MasterCreditRank = js.getInt("MasterCreditRank");
        data.MasterInfo = js.getString("MasterInfo");
        data.EnrollmentAddrLng = js.getDouble("EnrollmentAddrLng");
        data.EnrollmentAddrLat = js.getDouble("EnrollmentAddrLat");
        data.MasterPicURL = js.getString("MasterPicURL");
        return data;
    }

    public String getMasterName() {
        return MasterName;
    }

    public void setMasterName(String masterName) {
        MasterName = masterName;
    }

    public String getMasterJiaXiao() {
        return MasterJiaXiao;
    }

    public void setMasterJiaXiao(String masterJiaXiao) {
        MasterJiaXiao = masterJiaXiao;
    }

    public double getMasterTuiJian() {
        return MasterTuiJian;
    }

    public void setMasterTuiJian(double masterTuiJian) {
        MasterTuiJian = masterTuiJian;
    }

    public double getMasterMaxTuiJian() {
        return MasterMaxTuiJian;
    }

    public void setMasterMaxTuiJian(double masterMaxTuiJian) {
        MasterMaxTuiJian = masterMaxTuiJian;
    }

    public int getMasterCreditRank() {
        return MasterCreditRank;
    }

    public void setMasterCreditRank(int masterCreditRank) {
        MasterCreditRank = masterCreditRank;
    }

    public String getMasterInfo() {
        return MasterInfo;
    }

    public void setMasterInfo(String masterInfo) {
        MasterInfo = masterInfo;
    }

    public double getEnrollmentAddrLng() {
        return EnrollmentAddrLng;
    }

    public void setEnrollmentAddrLng(double enrollmentAddrLng) {
        EnrollmentAddrLng = enrollmentAddrLng;
    }

    public double getEnrollmentAddrLat() {
        return EnrollmentAddrLat;
    }

    public void setEnrollmentAddrLat(double enrollmentAddrLat) {
        EnrollmentAddrLat = enrollmentAddrLat;
    }

    public String getMasterPicURL() {
        return MasterPicURL;
    }

    public void setMasterPicURL(String masterPicURL) {
        MasterPicURL = masterPicURL;
    }

}
