package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教练获取学员状态分类统计列表
 *
 * @author admin
 */
public class MyErpClassStudentData {
    // 请求参数

    // 返回参数

    private List<Info> InfoList = new ArrayList<Info>();

    // 方法

    public String getMyStudentClassData() throws JSONException {
        JSONObject js = new JSONObject();

        return js.toString();
    }

    public static List<Info> initDataList(JSONObject js) throws JSONException {
        List<Info> tempList = new ArrayList<Info>();
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            tempList.add(Info.InitData(tmpinfo.getJSONObject(i)));
        }
        return tempList;
    }

    public static class Info {
        // 请求参数

        // 返回参数
        private int StudentType; // 学员类型
        private String StudentTypeName; // 学员类型描述

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();
            js.put("StudentType", StudentType);
            js.put("StudentTypeName", StudentTypeName);

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.StudentType = js.getInt("StudentType");
            tmp.StudentTypeName = js.getString("StudentTypeName");

            return tmp;
        }

        public int getStudentType() {
            return StudentType;
        }

        public void setStudentType(int studentType) {
            StudentType = studentType;
        }

        public String getStudentTypeName() {
            return StudentTypeName;
        }

        public void setStudentTypeName(String studentTypeName) {
            StudentTypeName = studentTypeName;
        }

    }
}
