package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练、学员获取来噢认证学员信息
 *
 * @author yedong
 */
public class StuAuthInfoData extends BaseData {
    // 请求参数
    // StuUUID String 学员账号UUID ，当学员获取学员信息时此值可为空
    public String StuName; // 学员名字
    public String StuIDCardNum; // 学员证件号码
    public String Mobile; // 联系电话
}
