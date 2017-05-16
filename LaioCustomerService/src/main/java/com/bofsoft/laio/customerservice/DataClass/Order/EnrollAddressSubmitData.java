package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 工作台-设置-招生地址设置-提交
 *
 * @author admin
 */
public class EnrollAddressSubmitData extends BaseData {

    private int Code; // 提交状态，1成功，0不成功
    private String Content; // 显示的消息
    private int AddrId; // 招生地址Id，提交成功才返回此字段

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

    public int getAddrId() {
        return AddrId;
    }

    public void setAddrId(int addrId) {
        AddrId = addrId;
    }

}
