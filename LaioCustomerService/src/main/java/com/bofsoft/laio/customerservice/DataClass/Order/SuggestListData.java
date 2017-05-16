package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取教练反馈列表
 *
 * @author admin
 */
public class SuggestListData {
    // 请求参数
    private int MsgType; // 1，投诉，2咨询，3建议
    private int Page;
    private int PageNum;

    // 返回参数
    public List<Info> SuggestListDataList = new ArrayList<Info>();
    public boolean more;

    // 方法

    /**
     * @return 提交反馈参数
     * @throws JSONException
     */
    public String getSuggestListDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page = 1;
        PageNum = 10;
        js.put("MsgType", MsgType);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public String getNextSuggestListDataInfoList() throws JSONException {
        JSONObject js = new JSONObject();
        Page++;
        js.put("MsgType", MsgType);
        js.put("Page", Page);
        js.put("PageNum", PageNum);

        return js.toString();
    }

    public void addSuggestListDataInfoList(JSONObject js) throws JSONException {
        JSONArray tmpinfo = js.getJSONArray("info");
        for (int i = 0; i < tmpinfo.length(); i++) {
            SuggestListDataList.add(Info.InitData(tmpinfo.getJSONObject(i)));
        }
        more = js.getBoolean("more");
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
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

    public List<Info> getSuggestListDataList() {
        return SuggestListDataList;
    }

    public void setSuggestListDataList(List<Info> suggestListDataList) {
        SuggestListDataList = suggestListDataList;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public static class Info {
        // 请求参数

        // 返回参数
        private String Content; // 投诉、咨询、建议
        private String Status; // 状态，处理中、已处理
        private String StatusResult; // 状态结果

        // 方法
        public static Info InitData(JSONObject js) throws JSONException {
            Info tmp = new Info();
            tmp.Content = js.getString("Content");
            tmp.Status = js.getString("Status");
            tmp.StatusResult = js.getString("StatusResult");

            return tmp;
        }

        public String getSuggestListDataInfo() throws JSONException {
            JSONObject js = new JSONObject();

            return js.toString();
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }

        public String getStatus() {
            return Status;
        }

        public void setStatus(String status) {
            Status = status;
        }

        public String getStatusResult() {
            return StatusResult;
        }

        public void setStatusResult(String statusResult) {
            StatusResult = statusResult;
        }

    }

}
