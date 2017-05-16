package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

public class BandingAlipayAccountData extends BaseData {
    public String AlipayAccount; // 余额转出到的支付宝账户
    public String AlipayTrueName; // 余额转出到的支付宝账户对应的真实姓名
    public int AlipayIsVerify; // 余额转出到的支付宝账户状态，1已验证，0已提交，-1未提交，-2审核失败
}
