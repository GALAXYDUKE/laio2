package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练端_请求返款记录
 *
 * @author yedong
 */
public class OrdBackApplyRecordData extends BaseData {

    // BackId Integer 返款记录Id

    public int Code; // 提交状态，1成功，0不成功
    public String Content; // 提交不成功，返回失败提示，提交成功，以Html格式返回返款申请记录

}
