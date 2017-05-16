package com.bofsoft.laio.common;

public class ConstAll {
    // TCP相关
    public static final int STATE_CONNECT_SUCCESS = 1; // 连接成功
    public static final int STATE_CONNECT_FAILED = 0; // 连接失败
    public static final int STATE_CONNECT_WAIT = 2; // 等待连接

    public static final int RECONN_COUNT = 3; // 有网络时重连次数
    public static final int CONN_TIMEOUT = 10; // 建连接超时时间 秒
    public static final int RECONN_WAIT_TIME = 5; // 重连等待时间 秒

    // 请求返回结果
    public static final int RET_FAIL = 0; // 失败
    public static final int RET_SUCC = 1; // 成功
    // 请求模式
    public static final int REQ_TYPE_PREVIEW = 0; // 预览
    public static final int REQ_TYPE_SUBMIT = 1; // 提交

    // 产品类型
    public static final int PRO_TYPE_NONE = -1; // 未设置
    public static final int PRO_TYPE_SINGLE = 0; // 一人一车一费制
    public static final int PRO_TYPE_TRY = 1; // 一人一车先学后付
    public static final int PRO_TYPE_MULTI = 2; // 多人一车一费制
    public static final int PRO_TYPE_TWO = 3; // 二人一车一费制

    public static final String PRO_DISCPT_NONE = "未设置";
    public static final String PRO_DISCPT_SINGLE = "一人一车一费制";
    public static final String PRO_DISCPT_TWO = "自营模式";
    public static final String PRO_DISCPT_TRY = "一人一车先学后付";
    public static final String PRO_DISCPT_MULTI = "多人一车一费制";

    // 培训模式
    public static final int TRAIN_TYPE_MULTI = 0; // 多人一车
    public static final int TRAIN_TYPE_SINGLE = 1; // 一人一车
    public static final int TRAIN_TYPE_TRY = 2; // 体验

    public static final String TRAIN_DISCPT_MULTI = "多人一车"; // 多人一车
    public static final String TRAIN_DISCPT_SINGLE = "一人一车"; // 一人一车

    // 科目
    public static final String SUB_DISCPT_4 = "科目四"; // 科目四
    public static final String SUB_DISCPT_3 = "科目三"; // 科目三
    public static final String SUB_DISCPT_2 = "科目二"; // 科目二
    public static final String SUB_DISCPT_1 = "科目一"; // 科目一

    public static final int SUB_TYPE_4 = 4; // 科目四
    public static final int SUB_TYPE_3 = 3; // 科目三
    public static final int SUB_TYPE_2 = 2; // 科目二
    public static final int SUB_TYPE_1 = 1; // 科目一
    public static final int SUB_TYPE_0 = 0; // 科目无

    // 产品类型
    public static final int RT_REG = 0; // 招生产品
    public static final int RT_TRAIN = 1; // 初学培训产品
    public static final int RT_TRAIN_AGAIN = 2; // 补训产品
    public static final int RT_TRAIN_AFTER = 3; // 陪练产品
    public static final int RT_TRAIN_TIMECOUNT = 4; // 计时培训产品
    public static final int RT_TEST_APPOINT = 5; // 考场预约

    // 需求状态
    public static final int RST_ALL = -9; // 所有状态
    public static final int RST_FAIL = -2; // 抢单失败
    public static final int RST_CANCEL = -1; // 已取消
    public static final int RST_WAIT = 0; // 待抢单
    public static final int RST_BID = 1; // 抢单中
    public static final int RST_FINI = 2; // 已完成

    // 订单相关
    public static final int OT_FINI = 8; // 已完成
    public static final int OT_REFUND = 2; // 退款中
    public static final int OT_EVALUATE = 7; // 待评价
    public static final int OT_TRAINING = 4; // 待培训
    public static final int OT_PAY = 3; // 待付款

    public static final String OT_TAG_FINI = "已完成";
    public static final String OT_TAG_REFUND = "退款中";
    public static final String OT_TAG_EVALUATE = "待评价";
    public static final String OT_TAG_TRAINING = "待培训";
    public static final String OT_TAG_PAY = "待付款";

    // 参数默认值
    public static final int VALUE_DEF_INT = 0; // 默认值0
    public static final String VALUE_DEF_STR = ""; // 默认值空

    // 教练列表筛选
    public static final String COACH_FILTER_ALL = "综合";
    public static final String COACH_FILTER_RECENTLY = "离我最近";
    public static final String COACH_FILTER_PRICE = "价格";
    public static final String COACH_FILTER_AMOUNT = "成交量";

}
