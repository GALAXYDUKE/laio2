package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

public class OrdGrpDetailsListData extends BaseData {
    // 请求参数
    // GroupDM Integer 订单状态分组代码0为全部，其他为各分组代码
    // OrderType Integer 订单分类，0全部订单，1报名类订单，2培训类订单
    // ProType Integer 订单对应产品类型，0为全部
    // 招生类(OrderType=1)：1计时计程，2一费制
    // 培训类(OrderType=2)：1初学培训产品，2补训产品，3陪练产品；

    public List<OrdGrpDetailsData> OrderList; // 订单列表
    public boolean More;
}
