package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 培训状态
 *
 * @author yedong
 */
public class TrainStatusData extends BaseData {
    public int OrderId; // 订单Id
    public String Name; // 学员姓名
    public int RemainMin; // 剩余分钟数
    public String EndTime; // 培训结束时间
}
