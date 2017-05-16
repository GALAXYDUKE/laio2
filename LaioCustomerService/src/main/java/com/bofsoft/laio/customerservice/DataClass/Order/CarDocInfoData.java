package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练获取来噢教练车档案信息
 *
 * @author yedong
 */
public class CarDocInfoData extends BaseData {

    public String CarLicense; // 车牌号
    public String CarInfo; // 准教证、驾驶证等信息，以<br>分隔(注：学员请求时，不会返回此信息)

}
