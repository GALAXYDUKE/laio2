//
// JasonApplyImageStateList.java
//
// Created by Jason.xu Auto Source Tool on 2014/8/9 9:45:00.
// Copyright (c) 2014年 Jason.xu. All rights reserved.
//
package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason.xu 获取证件图片状态列表
 */
public class ApplyImageStateList extends BaseData {
    // 请求参数
    private int ObjectType;

    // 返回参数
    private List<ApplyImageStateInfo> info = new ArrayList<ApplyImageStateInfo>();

    public int getObjectType() {
        return ObjectType;
    }

    public void setObjectType(int ObjectType) {
        this.ObjectType = ObjectType;
    }

    public List<ApplyImageStateInfo> getInfo() {
        return info;
    }

    public void setInfo(List<ApplyImageStateInfo> info) {
        this.info = info;
    }

    // 方法
    public static ApplyImageStateList InitData(JSONObject js) throws JSONException {
        ApplyImageStateList tmp = new ApplyImageStateList();
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            tmp.info.add(ApplyImageStateInfo.InitData(tmpinfo.getJSONObject(i)));
        }

        return tmp;
    }

    public String getApplyImageStateListData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("ObjectType", ObjectType);

        return js.toString();
    }

    /**
     * @author Jason.xu 获取证件图片状态列表
     */
    public static class ApplyImageStateInfo extends BaseData {

        // 返回参数
        public String Type; // 图片类型，数据字表示证件类型：0头像，1身份证，2教练证，3营运证，4行驶证，5驾驶证，6车辆照片；
        // 字母表示正背面：A正面，B背面。
        // 比如1A表示身份证正面，1B表示身份证背面，只有一面就当是正面
        public int Status; // 图片状态，1有，0无
        public String UpdateTime; // 图片更新时间2014-01-01 12:01:01

        public int Check; // 审核状态，1已审核，0已确认提交，-1审核失败
        public String CheckMsg; // 审核意见，失败时显示

        // 方法
        public static ApplyImageStateInfo InitData(JSONObject js) throws JSONException {
            ApplyImageStateInfo tmp = new ApplyImageStateInfo();
            tmp.Type = js.getString("Type");
            tmp.Status = js.getInt("Status");
            tmp.UpdateTime = js.getString("UpdateTime");
            tmp.Check = js.getInt("Check");
            tmp.CheckMsg = js.getString("CheckMsg");

            return tmp;
        }

        public String getinfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

    }

}
