package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教练获取学员达标记录信息
 *
 * @author admin
 */
public class MyStudentReachMarkData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    // 返回参数

    private List<Info> InfoList = new ArrayList<Info>();

    // 方法

    public String getMyStudentExamReachMarkData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
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

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getObjectType() {
        return ObjectType;
    }

    public void setObjectType(int objectType) {
        ObjectType = objectType;
    }

    public List<Info> getInfoList() {
        return InfoList;
    }

    public void setInfoList(List<Info> infoList) {
        InfoList = infoList;
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private String ReachMarkInfo; // 学员培训达标记录

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.ReachMarkInfo = js.getString("ReachMarkInfo");
            return tmp;
        }

        public String getReachMarkInfo() {
            return ReachMarkInfo;
        }

        public void setReachMarkInfo(String reachMarkInfo) {
            ReachMarkInfo = reachMarkInfo;
        }
    }
}
