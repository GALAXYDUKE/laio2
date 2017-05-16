package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练端_可申请返点学员
 *
 * @author yedong
 */
public class OrdRebateStuData extends BaseData {

    public int BackId; // 返款记录Id
    public int Stage; // 申请返款阶段，1科目一，2科目二，3科目三
    public int Status; // 状态，1可申请返款，2返款申请中，3已返款
    public String Name; // 学员姓名
    public String CarType; // 报考车型
    public String Mobile; // 联系电话
    public String BackTime; // Status为1时返回订单付款时间，为2时返回发起申请时间，为3时返回通过申请时间
    public int HasRequest; // 是否已经申请过返款，0没有，1有申请记录，为1时可调用0x2963接口查看申请记录

}
