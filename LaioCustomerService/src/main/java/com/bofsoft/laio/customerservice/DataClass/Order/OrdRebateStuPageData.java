package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 教练端_可申请返点学员列表
 *
 * @author yedong
 */
public class OrdRebateStuPageData extends BaseData {

    // Stage Integer 申请返款阶段，1科目一，2科目二，3科目三
    // Status Integer 状态，0全部，1可申请返款，2返款申请中，3已返款
    // Page Integer 页码
    // PageNum Integer 每页多少条

    public List<OrdRebateStuData> StuList; // 返点学员列表
    public boolean More; // 是否还有数据
}
