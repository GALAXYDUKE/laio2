package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 招生产品预览，发布
 *
 * @author admin
 */
public class ProductEnrollViewPubData extends BaseData {
    // 请求参数
    // OpeType Integer 操作类型：
    // 0：预览，1：提交
    // RegType Integer 招生产品分类，0一费制，1计时计程；
    // UpToDate String 招生截止日期
    // VasList String 勾选的增值服务
    // VasList包含元素：
    // Id Integer 选中增值服务Id
    // Name String 增值服务名称

    private int Code; // Integer 提交状态，1成功，0不成功
    private String Content; // String 提交不成功，返回失败提示，提交成功，为预览操作时，返回产品预览信息

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
