package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 培训产品时间段Data
 *
 * @author yedong
 */
public class TrainProTimesData extends BaseData implements Comparable<TrainProTimesData> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public int Id; // Integer 时间点ID，即产品Id
    public String GUID; // String
    // 投放批次GUID，在购买选择学时时，只有GUID相同的才能一起购买，且如果是多个学时学时必须连续。
    public String Name; // String 时间点，如：7:00~8:00
    public int SpecId; // Integer 培训产品规格ID，区分不同规格的产品，同一规格的产品价格体系一样，即可执行相同套餐价格。

    public int TimeSpace; // 间隔时长，单位分钟，默认为0

    @Override
    public int compareTo(TrainProTimesData another) {
        return Name.compareTo(another.Name);
    }
}
