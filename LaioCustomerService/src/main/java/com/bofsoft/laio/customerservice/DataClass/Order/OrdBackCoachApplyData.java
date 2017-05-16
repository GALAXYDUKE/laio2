package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练端_申请返款
 *
 * @author yedong
 */
public class OrdBackCoachApplyData extends BaseData {

    // BackId Integer 返款记录Id
    // Reason String 申请原因描述

    public int Code; // 提交状态，1成功，0不成功
    public String Content; // 提交不成功，返回失败提示，提交成功，返回提示信息

}
