package com.bofsoft.laio.customerservice.DataClass;

import com.bofsoft.laio.data.BaseData;

public class BaseResponseStatusData extends BaseData {

    public int Code; // 提交状态，1成功，0不成功
    public String Content; // 显示的消息

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
