package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

import java.util.ArrayList;
import java.util.List;

public class OnlineApplyListData extends BaseData {
    public List<OnlineApplyListInfoData> StuList = new ArrayList<OnlineApplyListInfoData>();
    public boolean more;
}
