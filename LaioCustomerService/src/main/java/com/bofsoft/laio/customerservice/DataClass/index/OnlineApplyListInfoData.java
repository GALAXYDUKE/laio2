package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 线上报名学员列表data
 *
 * @author szw
 */
public class OnlineApplyListInfoData extends BaseData {
    public int RegfeeId;//返款记录Id
    public int Status;//状态，0培训中，1可返款(培训已完成)，2已返款
    public int ApplyForStatus;//教练申请提取尾款，-1申请失败(可重新申请)，0未申请，1已申请。
    //当Status=1 and ApplyForStatus!=1时，教练可申请返款。
    public String ApplyForCheckMsg;//教练申请返款审核失败意见，可在ApplyForStatus=-1时显示
    public String BuyerUUID;//学员账号UUID
    public String BuyerName;//学员姓名
    public String BuyerTel;//学员联系电话
}
