package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 找教练招生产品查找list item
 *
 * @author yedong
 */
public class FindTeacherData extends BaseData {

    public int ProductID; // Integer 产品Id，招生类产品直接返回i商品的Id
    public String CoachUUID; // String 教练Auth.UUID
    public String CoachUserUUID; // String 教练用户Account.UUID
    public String CoachName; // String 教练名字
    public String CoachPhone; // String 联系电话
    public String CoachHeadUrl; // String 教练头像路径Url
    public double CoachCredit; // Integer 信用评价 2014.10.15由int修改为double
    public int CoachLevel; // Integer 推荐指数
    public String CoachInfo; // String
    // 教练简介信息，包含口碑<br>所属驾校<br>招生地址<br>联系电话<br>准教车型<br>初领日期<br>到期日期

    // 新增字段 2014.10.15(web端使用)
    public int ClassType; // 招生产品收费模式，0一费制，1计时计程
    /**
     * 产品名称
     */
    public String ProName; // 产品名称
    public String ProDeadTime; // 产品截至日期
    public int AuthStatus; // 是否通过实名认证，0未认证，1已认证
    public int AllPapersReady; // 五证齐全，0不齐，1齐全
    public String Addr; // 招生产品返回招生地址，培训产品返回训练场地
    public String School; // 驾校名称

    public int DealNo; // 成交量
    public int EvaluateNo; // 评价数量
    public double ProPrice; // 价格，培训产品返回最低价
    public double ProPriceMax; // 价格，培训产品返回最高价与ProPrice构成价格区间
    public String CarPicUrl; // 教练车图片路径Url
    public String CarType; // 报考车型
    public String CarModel; // 教练车型，学时产品返回(网页端使用)

    // public String FindContent; //查询条件，对查找接口进行匹配过滤，为空时不做处理
    public String Distance; // 距离学员所在位置距离，招生产品是与招生点的距离，培训产品是与训练场的距离，返回内容如：1.20公里或500米

    public String SaleTag; // 促销信息标签，不为空时展示
    public String SaleContent; // 促销内容描述，不为空时展示

    public int ProType; // 产品类型，0招生类产品，1初学培训产品，2补训产品，3陪练产品，4计时培训；

    public String ProShortName; // 产品名称简称，列表中显示
    public String ProContent; // 产品简介，优先显示SaleContent，当SaleContent为空时显示ProContent（仅招生产品显示）
    public double FirstPayAmount; // 首付金额，当金额大于0时才显示（仅招生产品显示）

}
