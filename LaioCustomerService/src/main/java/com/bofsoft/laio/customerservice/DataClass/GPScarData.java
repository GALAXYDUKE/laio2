package com.bofsoft.laio.customerservice.DataClass;

import com.bofsoft.laio.data.BaseData;

/**
 * Created by szw on 2017/3/1.
 */

public class GPScarData extends BaseData {
    public String License;//车牌号
    public String Deviceid;//设备号

    public String getDeviceid() {
        return Deviceid;
    }

    public void setDeviceid(String deviceid) {
        Deviceid = deviceid;
    }

    public String DueMsg;//该字段为空，才使用
}
