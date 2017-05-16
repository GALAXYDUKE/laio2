package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 教练获取学员业务流程信息
 *
 * @author admin
 */
public class MyStudentFlowInfoData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    // 返回参数

    private String StudentInfo; // 业务流程信息（如：报名时间：2013-01-01，以<br>分割）

    // 方法

    public String getMyStudentFlowInfoData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
        return js.toString();
    }

    public static MyStudentFlowInfoData initData(JSONObject js) throws JSONException {
        MyStudentFlowInfoData data = new MyStudentFlowInfoData();
        data.StudentInfo = js.getString("StudentInfo");

        return data;
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

    public String getStudentInfo() {
        return StudentInfo;
    }

    public void setStudentInfo(String studentInfo) {
        StudentInfo = studentInfo;
    }

}
