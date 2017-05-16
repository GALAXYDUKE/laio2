package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 账户收支明细详情
 *
 * @author yedong
 */
public class AccountTransDetailsData extends BaseData {
    public String TransNum; // 交易流水号
    public double Amount; // 发生额，正数表示收入，负数表示支出
    public double Balance; // 余额
    public String Time; // 日期
    public String TransType; // 交易类型，如支付、退款
    public String PayType; // 支付方式
    public String Abstract; // 备注
    public String OrderNum; // 订单号，通过订单号查询订单
}
