package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 教练获取学员基本信息
 *
 * @author admin
 */
public class MyStudentInfoData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    // 返回参数

    private String StudentInfo; // 学员信息（如学员姓名：张三，以<br>分割）
    private String StuPicURL; // 学员照片URL
    private String username; // 学员Laio发送消息用户名
    private String showname; // 学员Laio发送消息显示名称

    // 新增2014.11.21
    private String useruuid; // 学员Laio发送消息用户uuid

    // 方法

    public String getMyStudentInfoData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
        return js.toString();
    }

    public static MyStudentInfoData initData(JSONObject js) throws JSONException {
        MyStudentInfoData data = new MyStudentInfoData();
        data.StudentInfo = js.getString("StudentInfo");
        data.StuPicURL = js.getString("StuPicURL");
        data.username = js.getString("username");
        data.showname = js.getString("showname");
        data.useruuid = js.getString("useruuid");
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

    public String getStuPicURL() {
        return StuPicURL;
    }

    public void setStuPicURL(String stuPicURL) {
        StuPicURL = stuPicURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

}
