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
public class TrainXueShiQueryData {
    // 请求参数
    private String Condition; // 查询条件
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
        js.put("Condition", Condition);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public String getNextDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page++;
        js.put("Condition", Condition);
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

        private int StudentId; // 学员ID
        private String Name; // 学员姓名
        private String Phone; // 学员电话
        private String ReachMarkInfo; // 达标信息

        // 方法
        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.StudentId = js.getInt("StudentId");
            tmp.Name = js.getString("Name");
            tmp.Phone = js.getString("Phone");
            tmp.ReachMarkInfo = js.getString("ReachMarkInfo");

            return tmp;
        }

        public String getDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public int getStudentId() {
            return StudentId;
        }

        public void setStudentId(int studentId) {
            StudentId = studentId;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public String getPhone() {
            return Phone;
        }

        public void setPhone(String phone) {
            Phone = phone;
        }

        public String getReachMarkInfo() {
            return ReachMarkInfo;
        }

        public void setReachMarkInfo(String reachMarkInfo) {
            ReachMarkInfo = reachMarkInfo;
        }

    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
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
