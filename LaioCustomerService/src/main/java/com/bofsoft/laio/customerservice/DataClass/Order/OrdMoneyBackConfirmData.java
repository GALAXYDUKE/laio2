package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练端_学员退款申请确认
 *
 * @author yedong
 */
public class OrdMoneyBackConfirmData extends BaseData {
    // OrderId Integer 当前支付的订单Id
    // Pass Integer 是否通过，0不通过，1通过
    // Reason String 不通过时必须填写取消原因

    public int Code; // 提交状态，1成功，0不成功
    public String Content; // 提交不成功，返回失败提示，提交成功，为预览操作时，返回订单预览信息

}
