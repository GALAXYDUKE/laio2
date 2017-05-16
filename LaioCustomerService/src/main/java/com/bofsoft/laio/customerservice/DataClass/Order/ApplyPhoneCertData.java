//
// JasonApplyPhoneCertData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/9 17:44:53.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu ERP账号登录验证手机号
 */
public class ApplyPhoneCertData {
    // 请求参数
    private String Mobile; // 手机号
    private String VerifyCode; // 验证码
    private String SchoolNum; // 驾校编号（登录的）
    private String ERPUserName; // DP用户名（登录的）
    private String ERPPassword; // DP登录密码（登录的）

    // 返回参数
    private int Code;
    private String Content;

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public String getVerifyCode() {
        return VerifyCode;
    }

    public void setVerifyCode(String VerifyCode) {
        this.VerifyCode = VerifyCode;
    }

    public String getSchoolNum() {
        return SchoolNum;
    }

    public void setSchoolNum(String schoolNum) {
        SchoolNum = schoolNum;
    }

    public String getERPUserName() {
        return ERPUserName;
    }

    public void setERPUserName(String eRPUserName) {
        ERPUserName = eRPUserName;
    }

    public String getERPPassword() {
        return ERPPassword;
    }

    public void setERPPassword(String eRPPassword) {
        ERPPassword = eRPPassword;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    // 方法
    public static ApplyPhoneCertData InitData(JSONObject js) throws JSONException {
        ApplyPhoneCertData tmp = new ApplyPhoneCertData();
        tmp.Code = js.getInt("Code");
        tmp.Content = js.getString("Content");

        return tmp;
    }

    public String getApplyPhoneCertData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("Mobile", Mobile);
        js.put("VerifyCode", VerifyCode);
        js.put("SchoolNum", SchoolNum);
        js.put("ERPUserName", ERPUserName);
        js.put("ERPPassword", ERPPassword);

        return js.toString();
    }

}
