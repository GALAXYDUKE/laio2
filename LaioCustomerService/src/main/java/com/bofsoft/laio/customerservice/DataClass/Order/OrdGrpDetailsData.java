package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 订单详情列表
 *
 * @author yedong
 */
public class OrdGrpDetailsData extends BaseData {
    public int Id; // Integer 订单Id
    public String Num; // String 订单号
    public String Name; // String 订单名称
    public String View; // String 订单内容
    public int GroupDM; // Integer 订单状态分组代码 0为全部，其他为各分组代码
    public int OrderType; // Integer 订单购买产品类型，0报名产品，1初学培训产品，2补训产品，3陪练产品；
    public int Status; // Integer 订单状态，-2已关闭，-1退款中，0待付款，1已付款待确认(培训)，2完成
    public int StatusTrain; // Integer
    // 培训状态，Status为1时返回，0没开始，1学员发起培训，2教练确认开始，3培训结束
    public int StatusAppraise; // Integer 评价状态，Status为2时返回，0未评，1学员已评；
    public String DeadTime; // String 订单过期时间，Status为0时返回
    public String ConfirmTime; // String 订单完成时间，status为2时返回

    // 新增字段 2014.10.15
    public int RefundStatus; // 是否存在申请退款，0不存在，1存在

    // 新增字段 2014.02.13
    public String BuyerTel; // 买家联系电话

    /**
     * 订单受理状态，在Status=0时有效<br/>
     * 0-未受理待卖家处理，<br/>
     * 1-已受理待学员完善培训时段（学时订单有效，OrderType为1，2，3）<br/>
     * 2-已受理待学员付款；
     */
    public int StatusAccepted;

}
