//
// JasonApplyGetPublistFillInfoData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/9 14:10:04.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu 网络商品服务_获取认证信息
 */
public class ApplyGetPublistFillInfoData {
    // 请求参数

    // 返回参数
    private String CityDM; // 城市代码
    private String CityName; // 城市名称
    private String DistrictDM; // 区域代码
    private String DistrictName; // 区域名称
    private String SchoolDM; // 驾校代码
    private String SchoolName; // 驾校名称
    private String Name; // 教练姓名
    private String IDCardNum; // 教练证件号码
    private String TeachCarType; // 教练证准教车型
    private String TeachCardNum; // 教练证证件编号
    private String TeachDueDate; // 教练证到期日期
    private String YYZNum; // 营运证编号
    private String YYZCarLicense; // 营运证车牌号
    private String YYZJYLicense; // 营运证经营许可证编号
    private String YYZCarType; // 营运证车型车型
    private String XSZVehicleType; // 行驶证车型类型
    private String XSZModel; // 行驶证品牌型号
    private String XSZSendDate; // 行驶证发证日期

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
    }

    public String getCityDM() {
        return CityDM;
    }

    public void setCityDM(String cityDM) {
        CityDM = cityDM;
    }

    public String getDistrictDM() {
        return DistrictDM;
    }

    public void setDistrictDM(String districtDM) {
        DistrictDM = districtDM;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public String getSchoolDM() {
        return SchoolDM;
    }

    public void setSchoolDM(String schoolDM) {
        SchoolDM = schoolDM;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String schoolName) {
        SchoolName = schoolName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIDCardNum() {
        return IDCardNum;
    }

    public void setIDCardNum(String IDCardNum) {
        this.IDCardNum = IDCardNum;
    }

    public String getTeachCarType() {
        return TeachCarType;
    }

    public void setTeachCarType(String TeachCarType) {
        this.TeachCarType = TeachCarType;
    }

    public String getTeachCardNum() {
        return TeachCardNum;
    }

    public void setTeachCardNum(String TeachCardNum) {
        this.TeachCardNum = TeachCardNum;
    }

    public String getTeachDueDate() {
        return TeachDueDate;
    }

    public void setTeachDueDate(String TeachDueDate) {
        this.TeachDueDate = TeachDueDate;
    }

    public String getYYZNum() {
        return YYZNum;
    }

    public void setYYZNum(String YYZNum) {
        this.YYZNum = YYZNum;
    }

    public String getYYZCarLicense() {
        return YYZCarLicense;
    }

    public void setYYZCarLicense(String YYZCarLicense) {
        this.YYZCarLicense = YYZCarLicense;
    }

    public String getYYZJYLicense() {
        return YYZJYLicense;
    }

    public void setYYZJYLicense(String YYZJYLicense) {
        this.YYZJYLicense = YYZJYLicense;
    }

    public String getYYZCarType() {
        return YYZCarType;
    }

    public void setYYZCarType(String YYZCarType) {
        this.YYZCarType = YYZCarType;
    }

    public String getXSZVehicleType() {
        return XSZVehicleType;
    }

    public void setXSZVehicleType(String XSZVehicleType) {
        this.XSZVehicleType = XSZVehicleType;
    }

    public String getXSZModel() {
        return XSZModel;
    }

    public void setXSZModel(String XSZModel) {
        this.XSZModel = XSZModel;
    }

    public String getXSZSendDate() {
        return XSZSendDate;
    }

    public void setXSZSendDate(String XSZSendDate) {
        this.XSZSendDate = XSZSendDate;
    }

    // 方法
    public static ApplyGetPublistFillInfoData InitData(JSONObject js) throws JSONException {
        ApplyGetPublistFillInfoData tmp = new ApplyGetPublistFillInfoData();
        tmp.CityDM = js.isNull("CityDM") ? "" : js.getString("CityDM");
        tmp.CityName = js.isNull("CityName") ? "" : js.getString("CityName");
        tmp.DistrictDM = js.isNull("DistrictDM") ? "" : js.getString("DistrictDM");
        tmp.DistrictName = js.isNull("DistrictName") ? "" : js.getString("DistrictName");
        tmp.SchoolDM = js.isNull("SchoolDM") ? "" : js.getString("SchoolDM");
        tmp.SchoolName = js.isNull("SchoolName") ? "" : js.getString("SchoolName");
        tmp.Name = js.isNull("Name") ? "" : js.getString("Name");
        tmp.IDCardNum = js.isNull("IDCardNum") ? "" : js.getString("IDCardNum");
        tmp.TeachCarType = js.isNull("TeachCarType") ? "" : js.getString("TeachCarType");
        tmp.TeachCardNum = js.isNull("TeachCardNum") ? "" : js.getString("TeachCardNum");
        tmp.TeachDueDate = js.isNull("TeachDueDate") ? "" : js.getString("TeachDueDate");
        tmp.YYZNum = js.isNull("YYZNum") ? "" : js.getString("YYZNum");
        tmp.YYZCarLicense = js.isNull("YYZCarLicense") ? "" : js.getString("YYZCarLicense");
        tmp.YYZJYLicense = js.isNull("YYZJYLicense") ? "" : js.getString("YYZJYLicense");
        tmp.YYZCarType = js.isNull("YYZCarType") ? "" : js.getString("YYZCarType");
        tmp.XSZVehicleType = js.isNull("XSZVehicleType") ? "" : js.getString("XSZVehicleType");
        tmp.XSZModel = js.isNull("XSZModel") ? "" : js.getString("XSZModel");
        tmp.XSZSendDate = js.isNull("XSZSendDate") ? "" : js.getString("XSZSendDate");

        return tmp;
    }

    public String getApplyGetPublistFillInfoData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

}
