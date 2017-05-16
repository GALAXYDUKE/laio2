package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

public class OnlineApplyDetailData extends BaseData {
    public int RegType;//招生产品分类，0一人一车一费制，1一人一车先学后付，2多人一车一费制
    public int Status;//状态，0培训中，1可返款(培训已完成)，2已返款。培训费余额与尾款金额在申请返款成功时一起返给教练。
    public Double RegFee;//已付首付款
    public int TrainPayCount;//已付费培训次数
    public Double TrainFee;//已付培训费
    public Double TrainBalance;//培训费余额
    public Double FinalFee;//尾款金额
}
