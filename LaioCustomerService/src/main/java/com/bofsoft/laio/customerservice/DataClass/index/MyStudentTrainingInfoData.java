package com.bofsoft.laio.customerservice.DataClass.index;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 教练获取学员培训记录信息
 *
 * @author admin
 */
public class MyStudentTrainingInfoData {
    // 请求参数
    private int StudentId; // 学员id
    private int ObjectType; // 登陆对象，0学员，1教练

    private int Page;
    private int PageNum;

    // 返回参数
    public List<Info> infoList = new ArrayList<Info>();
    public boolean more;

    // 方法

    /**
     * @return 提交参数
     * @throws JSONException
     */
    public String getDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page = 1;
        PageNum = 10;
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public String getNextDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page++;
        js.put("StudentId", StudentId);
        js.put("ObjectType", ObjectType);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public void addDataInfoList(JSONObject js) throws JSONException {
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            infoList.add(Info.InitData(tmpinfo.getJSONObject(i)));
        }
        more = js.getBoolean("more");
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private String TrainingInfo; // 详细培训记录信息(每字段及内容后以<br>分割)

        // 方法
        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.TrainingInfo = js.getString("TrainingInfo");

            return tmp;
        }

        public String getSuggestListDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public String getTrainingInfo() {
            return TrainingInfo;
        }

        public void setTrainingInfo(String trainingInfo) {
            TrainingInfo = trainingInfo;
        }

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

    public int getPage() {
        return Page;
    }

    public void setPage(int page) {
        Page = page;
    }

    public int getPageNum() {
        return PageNum;
    }

    public void setPageNum(int pageNum) {
        PageNum = pageNum;
    }

    public List<Info> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<Info> infoList) {
        this.infoList = infoList;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

}
