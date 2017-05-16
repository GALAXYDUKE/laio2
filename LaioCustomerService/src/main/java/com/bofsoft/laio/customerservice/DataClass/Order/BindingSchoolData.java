//
// JasonApplyBindingErpData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/6 9:44:30.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu ERP账号_申请绑定(教练)
 */
public class BindingSchoolData {
    // 请求参数
    private String SchoolNum; // 驾校编号
    private String UserName; // 用户名
    private String Password; // 登录密码

    // 返回参数
    private int Code;
    private String Content;

    public String getSchoolNum() {
        return SchoolNum;
    }

    public void setSchoolNum(String SchoolNum) {
        this.SchoolNum = SchoolNum;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
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
    public static BindingSchoolData InitData(JSONObject js) throws JSONException {
        BindingSchoolData tmp = new BindingSchoolData();
        tmp.Code = js.getInt("Code");
        tmp.Content = js.getString("Content");

        return tmp;
    }

    public String getApplyErpBindingData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("SchoolNum", SchoolNum);
        js.put("UserName", UserName);
        js.put("Password", Password);

        return js.toString();
    }

}
