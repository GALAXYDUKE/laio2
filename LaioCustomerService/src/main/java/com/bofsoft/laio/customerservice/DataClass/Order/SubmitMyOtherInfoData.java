//
// JasonSubmitMyOtherInfoData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/9 16:50:20.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author 提交我的其他信息
 */
public class SubmitMyOtherInfoData {
    // 请求参数
    private String Introduce;

    // 返回参数
    private int Code;
    private String Content;

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String Introduce) {
        this.Introduce = Introduce;
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
    public static SubmitMyOtherInfoData InitData(JSONObject js) throws JSONException {
        SubmitMyOtherInfoData tmp = new SubmitMyOtherInfoData();
        tmp.Code = js.getInt("Code");
        tmp.Content = js.getString("Content");

        return tmp;
    }

    public String getSubmitMyOtherInfoData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("Introduce", Introduce);

        return js.toString();
    }

}
