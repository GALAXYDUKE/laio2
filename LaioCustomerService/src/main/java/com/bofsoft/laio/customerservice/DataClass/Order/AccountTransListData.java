package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 账户收支明细列表
 *
 * @author yedong
 */
public class AccountTransListData extends BaseData {
    public String TransNum; // 交易流水号
    public double Amount; // 发生额，正数表示收入，负数表示支出
    public double Balance; // 余额
    public String Time; // 日期
    public String TransType; // 交易类型，如支付、退款
    public int FlowId; //
}
