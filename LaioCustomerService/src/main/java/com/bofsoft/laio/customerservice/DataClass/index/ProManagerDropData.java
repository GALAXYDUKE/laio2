package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 产品管理下架返回Data
 *
 * @author admin
 */
public class ProManagerDropData extends BaseData {
    // 请求参数
    // ProType Integer 产品类型，0招生类产品，1培训类产品；
    // ProId Integer 产品Id

    private int Code; // Integer 提交状态，1成功，0不成功
    private int ProType; // Integer 产品类型，0招生类产品，1培训类产品；
    private int ProId; // Integer 产品Id
    private String Content; // String 提交不成功，返回失败提示

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public int getProType() {
        return ProType;
    }

    public void setProType(int proType) {
        ProType = proType;
    }

    public int getProId() {
        return ProId;
    }

    public void setProId(int proId) {
        ProId = proId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
