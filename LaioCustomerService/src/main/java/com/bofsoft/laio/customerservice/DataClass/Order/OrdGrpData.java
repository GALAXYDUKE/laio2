package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

public class OrdGrpData extends BaseData {
    public int GroupDM; // Integer 工作台返回：1今日订单，2退款中，3待付款，4待培训, 6待确认付款，7待评价；
    // 我的订单返回所有订单分组，代码为：2退款中，3待付款，4待培训，5培训中，6待确认，7待评价，8已完成
    public int Quantity; // Integer 订单数量
}
