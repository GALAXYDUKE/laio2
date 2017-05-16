package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 学时销售情况data
 *
 * @author yedong
 */
public class SaleStatusData extends BaseData {
    public int ProId; // 产品Id
    public String Times; // 时段
    public int Status; // Integer 产品状态，-2已过期，-1已下架，0待审核， 1可购买，2待付款，3已售出
}
