package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 产品地址
 *
 * @author yedong
 */
public class ProAddrData extends BaseData {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int Id; // Integer 地址Id
    // public String Name; //String 招生点名称
    public String Addr; // Sring 招生点地址
    public String Lat; // String 所在纬度
    public String Lng; // String 所在经度

    public String GUID; // String 投放批次GUID(培训)

}
