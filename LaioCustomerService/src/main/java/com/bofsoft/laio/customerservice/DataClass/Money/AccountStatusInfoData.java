package com.bofsoft.laio.customerservice.DataClass.Money;

import com.bofsoft.laio.data.BaseData;

public class AccountStatusInfoData extends BaseData {
    public int MobileIsVerify; // 手机号账号是否已验证，0未验证，1已验证
    public String Mobile = ""; // 手机号账号
    public int PayPwdIsSet; // 是否已设置支付密码，0未设置，1已设置

    public int AlipayIsVerify; // 余额转出到的支付宝账户状态，1已验证，0已提交，-1未提交，-2审核失败
}
