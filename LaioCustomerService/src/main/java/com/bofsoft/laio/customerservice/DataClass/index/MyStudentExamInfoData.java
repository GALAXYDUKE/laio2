package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教练获取学员考试记录信息
 *
 * @author admin
 */
public class MyStudentExamInfoData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    // 返回参数

    private List<Info> InfoList = new ArrayList<Info>();

    // 方法

    public String getMyStudentExamInfoData() throws JSONException {
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

        private String TestDate; // 考试日期
        private String TestSub; // 考试科目
        private String Grade; // 考试成绩

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();
            js.put("TestDate", TestDate);
            js.put("TestSub", TestSub);
            js.put("Grade", Grade);

            return js.toString();
        }

        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.TestDate = js.getString("TestDate");
            tmp.TestSub = js.getString("TestSub");
            tmp.Grade = js.getString("Grade");

            return tmp;
        }

        public String getTestDate() {
            return TestDate;
        }

        public void setTestDate(String testDate) {
            TestDate = testDate;
        }

        public String getTestSub() {
            return TestSub;
        }

        public void setTestSub(String testSub) {
            TestSub = testSub;
        }

        public String getGrade() {
            return Grade;
        }

        public void setGrade(String grade) {
            Grade = grade;
        }

    }
}
