package com.bofsoft.laio.customerservice.DataClass.Order;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Jason.xu
 */
public class UploadPicData {
    // 请求参数
    private String Type; // 证件类型
    private String Photo; // 照片，二进制转16进制

    // 返回参数
    private int Code; // 提交状态，1成功，0不成功
    private String Content; // 显示的消息

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

    // 方法
    public static UploadPicData InitData(JSONObject js) throws JSONException {
        UploadPicData tmp = new UploadPicData();
        tmp.Code = js.getInt("Code");
        tmp.Content = js.getString("Content");
        tmp.Type = js.getString("Type");

        return tmp;
    }

    public String getUploadPicData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("Type", Type);
        js.put("Photo", Photo);

        return js.toString();
    }

}
