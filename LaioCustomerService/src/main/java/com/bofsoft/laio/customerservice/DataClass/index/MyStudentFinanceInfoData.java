package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 教练获取学员财务信息
 *
 * @author admin
 */
public class MyStudentFinanceInfoData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    // 返回参数

    private String StudentInfo; // 财务信息（如：实缴费用：¥5000元，以<br>分割），充值退费记录

    // 方法

    public String getMyStudentFinanceInfoData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
        return js.toString();
    }

    public static MyStudentFinanceInfoData initData(JSONObject js) throws JSONException {
        MyStudentFinanceInfoData data = new MyStudentFinanceInfoData();
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
