package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 产品服务协议
 *
 * @author admin
 */
public class ProductRuleUrlData extends BaseData {
    // 请求参数
    // ProType Integer 产品类型，0招生类产品，1培训类产品；

    private String Url; // String 规则连接

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

}
