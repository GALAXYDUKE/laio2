//
// JasonApplyGetPicData.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/6 9:57:29.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu 获取证件图片
 */
public class ApplyGetPicData {
    // 请求参数
    /**
     * 图片类型，数据字表示证件类型：0头像，1身份证，2教练证，3营运证， 字母表示正背面：A正面，B背面。比如1A表示身份证正面，1B表示身份证背面，只有一面就当是正面
     */
    private String Type;
    /**
     * 服务器照片更新时间2014-01-01 12:01:01，如果时间与服务器存储的时间相同， 则不返回图片数据，如果本地无对应图片传空字符串””
     */
    private String PhotoUpdateTime;

    // 返回参数
    private int Code;
    private String TypeResult;
    private String Content; // 显示的消息，当Code=1时Content返回的照片的十六进制数据
    /**
     * 照片更新时间2014-01-01 12:01:01 注：提交Code=1（成功）时会返回此字段，本地缓存提交过的图片并存储此字段，
     * 从服务器获取图片时可传递此参数，如果时间与服务器存储的时间一致，则返回空（不返回图片数据）
     */
    private String ResultPhotoUpdateTime;

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPhotoUpdateTime() {
        return PhotoUpdateTime;
    }

    public void setPhotoUpdateTime(String PhotoUpdateTime) {
        this.PhotoUpdateTime = PhotoUpdateTime;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getTypeResult() {
        return TypeResult;
    }

    public void setTypeResult(String typeResult) {
        TypeResult = typeResult;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    public String getResultPhotoUpdateTime() {
        return ResultPhotoUpdateTime;
    }

    public void setResultPhotoUpdateTime(String PhotoUpdateTime) {
        this.ResultPhotoUpdateTime = PhotoUpdateTime;
    }

    // 方法
    public static ApplyGetPicData InitData(JSONObject js) throws JSONException {
        ApplyGetPicData tmp = new ApplyGetPicData();
        tmp.Code = js.getInt("Code");
        tmp.TypeResult = js.getString("Type");
        tmp.Content = js.getString("Content");
        tmp.ResultPhotoUpdateTime = js.getString("PhotoUpdateTime");

        return tmp;
    }

    public String getApplyGetPicData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("Type", Type);
        js.put("PhotoUpdateTime", PhotoUpdateTime);

        return js.toString();
    }

}
