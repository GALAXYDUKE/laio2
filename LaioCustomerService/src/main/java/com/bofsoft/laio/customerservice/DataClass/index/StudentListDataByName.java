package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练获取来噢学员列表(招生类)
 *
 * @author yedong
 */
public class StudentListDataByName extends BaseData {
    public int OrderId; // 订单Id
    public String OrderNum; // 订单号
    public String OrderTime; // 订单时间
    public int OrderType; // 订单购买产品类型，0报名产品，1初学培训产品，2补训产品，3陪练产品
}
