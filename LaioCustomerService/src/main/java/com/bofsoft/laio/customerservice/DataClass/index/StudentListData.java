package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练获取来噢学员列表(招生类)
 *
 * @author yedong
 */
public class StudentListData extends BaseData {
    // 请求参数
    // Content String 可搜索学员姓名、联系电话，若为空则为查询全部
    // Page Integer 页码
    // PageNum Integer 每页多少条

    public Integer OrderID; // 订单Id
    public String OrderNum; // 订单号，通过订单号查询学员订单
    public String Name; // 学员姓名
    public String Phone; // 学员联系电话
    public String TeachingType; // 招生产品分类，一费制、计时计程
    public String IDCardNum; // 学员证件号码
    public boolean more; // 是否还有数据
}
