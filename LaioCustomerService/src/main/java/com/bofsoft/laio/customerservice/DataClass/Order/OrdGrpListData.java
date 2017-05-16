package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import java.util.ArrayList;
import java.util.List;

public class OrdGrpListData extends BaseData {
    // 请求参数
    // Model Integer 获取模块，0工作台，1我的订单
    public List<OrdGrpData> GroupList = new ArrayList<>();
}
