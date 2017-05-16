package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.customerservice.DataClass.db.TestSubData;
import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 学员端_查看单个培训产品
 *
 * @author yedong
 */
public class TrainProDetailData extends BaseData {
    // 请求参数
    // ProType Integer 产品类型Id（必填），初学培训产品，补训产品，陪练产品；
    // CoachUserUUID String 教练用户Account.UUID
    // ProDate String 培训日期，如果时间为空，返回教练当前以后有数据的第一天的产品

    public int ProType; // Integer 产品类型，1初学培训产品，2补训产品，3陪练产品,4计时培训；
    public String CoachUUID; // String 教练Auth.UUID
    public String CoachUserUUID; // String 教练用户Account.UUID
    /**
     * 2015.02.05 已停用
     */
    public String TestSubId; // 培训科目Id，1科目一、2科目二、3科目三、4科目四；
    /**
     * 2015.02.05 新增 ,只返回id，需从本地数据库查询其它字段<br/>
     * TestSubList包含元素：<br/>
     * 名称 类型 解释<br/>
     * Id Integer 培训科目Id，1科目一、2科目二、3科目三、4科目四；
     */
    public List<TestSubData> TestSubList; // 可培训科目
    public String ProDate; // String 培训日期
    public String Intro; // String 产品详情
    public List<TrainProTimesData> TimesList; // List 可选时段列表
    public List<TrainProPriceData> PriceList; // List
    // 价格列表独立出来，以SpceId作为与TimesList进行对应
    public List<ProAddrData> AddrList; // 接送地址列表，同一批次的接送地址只有一组
    public List<ProVasData> VasList; // 提供的增值服务列表，同一批次的增值服务只有一组
}
