package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 产品增值服务
 *
 * @author yedong
 */
public class ProVasData extends BaseData {

    public int Id; // Integer 增值服务Id
    public String GUID; // String 投放批次GUID(培训)
    public int VasType; // Integer 增值服务计费方式，0单个学时，1单个订单(培训)
    public String Name; // String 增值服务名称
    public String Content; // String 增值服务内容描述
    public float Price; // Float 增值服务价格

}
