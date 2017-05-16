//
// JasonApplyPublistFillInfoData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/1 19:23:04.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu
 */
public class ApplyPublistFillInfoData {
    // 请求参数

    private String CityDM; // 城市代码
    private String CityName; // 城市名称
    private String DistrictDM; // 区域代码
    private String DistrictName; // 区域名称
    private String SchoolDM; // 驾校代码
    private String SchoolName; // 驾校名称
    private String Name; // 真实姓名
    private String IDCardNum; // 身份证号

    // 返回参数
    private int Code;
    private String Content;

    public String getApplyPublistFillInfoData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("CityDM", CityDM);
        js.put("CityName", CityName);
        js.put("DistrictDM", DistrictDM);
        js.put("DistrictName", DistrictName);
        js.put("SchoolDM", SchoolDM);
        js.put("SchoolName", SchoolName);
        js.put("Name", Name);
        js.put("IDCardNum", IDCardNum);

        return js.toString();
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

    public String getCityName() {
        return CityName;
    }

    public void setCityName(String cityName) {
        CityName = cityName;
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

    public void setIDCardNum(String iDCardNum) {
        IDCardNum = iDCardNum;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    // 方法
    public static ApplyPublistFillInfoData InitData(JSONObject js) throws JSONException {
        ApplyPublistFillInfoData tmp = new ApplyPublistFillInfoData();
        tmp.Code = js.getInt("Code");
        tmp.Content = js.getString("Content");

        return tmp;
    }

}
