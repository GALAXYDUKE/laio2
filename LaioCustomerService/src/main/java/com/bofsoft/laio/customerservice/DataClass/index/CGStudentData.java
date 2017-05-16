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
public class CGStudentData {
    // 请求参数
    private int OperateType; // 命令接口获取到的值 是否可操作车管预约 0：不能操作 1：批次列表 2：学员列表
    private String BatchDate; // 时间批次
    private String CarType; // 分配车型
    private boolean ShowAll; // 是否显示全部学员，若为false则只显示已预约学员
    private String Condition; // 查询条件
    private int Page;
    private int PageNum;

    // 返回参数
    public List<Info> infoList = new ArrayList<Info>();
    public boolean more;
    public String Topmsg; // 顶部显示名额信息

    // 方法

    /**
     * @return 提交参数
     * @throws JSONException
     */
    public String getDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page = 1;
        PageNum = 10;
        js.put("OperateType", OperateType);
        js.put("BatchDate", BatchDate);
        js.put("CarType", CarType);
        js.put("ShowAll", ShowAll);
        js.put("Condition", Condition);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public String getNextDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page++;
        js.put("OperateType", OperateType);
        js.put("BatchDate", BatchDate);
        js.put("CarType", CarType);
        js.put("ShowAll", ShowAll);
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
        Topmsg = js.getString("Topmsg");
    }

    public static class Info {
        // 请求参数

        // 返回参数

        private int StudentId; // 学员ID
        private String Name; // 学员姓名
        private String Phone; // 学员电话
        private boolean isYuYue; // 是否预约

        // 方法
        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.StudentId = js.getInt("StudentId");
            tmp.Name = js.getString("Name");
            tmp.Phone = js.getString("Phone");
            tmp.isYuYue = js.getBoolean("isYuYue");

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

        public boolean isYuYue() {
            return isYuYue;
        }

        public void setYuYue(boolean isYuYue) {
            this.isYuYue = isYuYue;
        }

    }

    public int getOperateType() {
        return OperateType;
    }

    public void setOperateType(int operateType) {
        OperateType = operateType;
    }

    public String getBatchDate() {
        return BatchDate;
    }

    public void setBatchDate(String batchDate) {
        BatchDate = batchDate;
    }

    public String getCarType() {
        return CarType;
    }

    public void setCarType(String carType) {
        CarType = carType;
    }

    public boolean isShowAll() {
        return ShowAll;
    }

    public void setShowAll(boolean showAll) {
        ShowAll = showAll;
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

    public String getTopmsg() {
        return Topmsg;
    }

    public void setTopmsg(String topmsg) {
        Topmsg = topmsg;
    }

}
