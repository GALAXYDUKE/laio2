package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * @author yedong
 */
public class ProOrderSubResultData extends BaseData {
    public int Code; // Integer 提交状态，1成功，0不成功
    public int OrderId; // Integer 提交成功返回订单Id，预览返回为空
    public String Content; // String 提交不成功，返回失败提示，提交成功，为预览操作时，返回订单预览信息
}
