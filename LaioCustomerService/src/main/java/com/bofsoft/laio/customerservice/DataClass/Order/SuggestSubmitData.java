package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 教练反馈提交（1，投诉，2咨询，3建议）
 *
 * @author admin
 */
public class SuggestSubmitData {
    // 请求参数
    private int MsgType; // 1，投诉，2咨询，3建议
    private String Msg; // 提交的内容

    // 返回参数
    private int Code; // 提交状态，1成功，0不成功
    private String Content; // 显示的消息

    // 方法

    /**
     * @return 提交反馈参数
     * @throws JSONException
     */
    public String getSuggestSubmitData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("MsgType", MsgType);
        js.put("Msg", Msg);

        return js.toString();
    }

    public static SuggestSubmitData initData(JSONObject js) throws JSONException {
        SuggestSubmitData data = new SuggestSubmitData();
        data.Code = js.getInt("Code");
        data.Content = js.getString("Content");
        return data;
    }

    public int getMsgType() {
        return MsgType;
    }

    public void setMsgType(int msgType) {
        MsgType = msgType;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String msg) {
        Msg = msg;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

}
