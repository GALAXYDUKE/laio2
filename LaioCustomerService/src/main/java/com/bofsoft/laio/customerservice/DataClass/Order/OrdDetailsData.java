package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 订单详情
 *
 * @author yedong
 */
public class OrdDetailsData extends BaseData {
    // 请求参数
    // OrderId Integer 订单Id
    public int Id; // 订单Id
    public String Num; // 订单号
    // 2014.10.30新增
    public String CustomerCode; // 订单消费码，默认为空（学员请求时才返回）
    public String Name; // 订单名称
    public float DealSum; // 订单成交金额
    public String View; // 订单详情
    public String ViewOrder; // 订购信息
    public String ViewPro; // 订购产品信息
    public String ProPicUrl; // 产品图片路径Url
    public String CoachTel; // 卖家电话
    public String BuyerTel; // 买家电话
    public String OrderTime; // 下单时间
    // 新增字段 2014.10.15
    public String CoachUserUUID; // 教练用户UUID（用于来噢消息发送）
    public String CoachName; // 教练姓名（用于来噢消息发送）
    public String BuyerUUID; // 买家用户UUID（用于来噢消息发送）
    public String BuyerName; // 买家姓名（用于来噢消息发送）

    public int OrderType; // 订单购买产品类型，0报名产品，1初学培训产品，2补训产品，3陪练产品,4-ICcard支付；
    public int Status; // 订单状态，-6退款客户介入处理中,-5付款超时订单取消，-4超时未付款订单取消，-3买家取消，
    // -2已退款订单，-1退款中，0待付款，1已付款待确认(培训)，2完成
    public int StatusTrain; // 培训状态，Status为1时返回，0没开始，1未到场可标记违约，2教练确认开始处于培训中，3培训结束

    /**
     * ProType != 4 and Status=1，StatusTrain为0、1时进行判断，0可以操作申请退款，1可以操作取消<br/>
     * ProType = 4 and Status = 2,时进行判断，0可以操作申请退款，1可以操作取消；
     */
    public int CanCancel;
    public int PayDeposit; // OrderType 为0，Status
    // 为0时有效，对支付类型进行判断，-1全款支付，0支付定金，1支付尾款
    public int TrainRemainMin; // 培训剩余时间，StatusTrain为2时返回
    public int StatusAppraise; // 评价状态，Status为2时返回，0未评，1学员已评；

    // 新增字段 2014.10.15 增加字段返回取消订单扣费金额和申请退款金额字段
    public double CancelFine; // 取消订单，违约扣款金额，Status为-3时返回
    public int RefundStatus; // 是否存在申请退款，0不存在，1存在
    public double RefundSum; // 申请退款金额

    public String DeadTime; // 订单过期时间，Status为0时返回
    public String ConfirmTime; // 订单完成时间，status为2时返回

    public int SendMsg; // 学员、教练间是否可以发送Laio消息，0不能发送消息，1可以发送消息;

    public int StatusAccepted; // 订单受理状态，在Status=0时有效，0未受理待卖家处理，1已受理待学员完善培训时段（学时订单有效，OrderType为1，2，3），2已受理待学员付款；
    public String StartTime; // 培训订单返回，购买培训开始时间
    public String EndTime; // 培训订单返回，购买培训结束时间
    public String StartAddr; // 上车地址
    public String DistrictMC; // 培训区域
    public String TrainSite; // 训练场名称
    public String SiteAddr; // 训练场地址
    public int Amount; // 学时数
    public int TestSubId; // 培训科目id
    public int TrainType;
    public String ModOrder;//服务人是否有修改招生订单金额权限：1是，0否；（教练请求时才返回
    public Double DepositFee;//	Double	定金金额
    public int DepositStatus;//	Integer	定金付款状态，0待付款，1已支付
    public Double FinalFee;//	Double	尾款金额
    public int FinalStatus;//	Integer	尾款付款状态，-2待付定金，-1资料准备中，0待付款，1已支付


    // DepositFee Double Web返回，定金金额
    // DepositStatus Integer Web返回，定金付款状态，0待付款，1已支付
    // FinalFee Double Web返回，尾款金额
    // FinalStatus Integer Web返回，尾款付款状态，-2待付定金，-1资料准备中，0待付款，1已支付
    // RefundRequestTime String Web返回，申请退款订单，申请退款时间
    // TimeList List Web返回，学时List
    // AddrList List Web返回，招生地址List
    // VasList List Web返回，增值服务List

}
