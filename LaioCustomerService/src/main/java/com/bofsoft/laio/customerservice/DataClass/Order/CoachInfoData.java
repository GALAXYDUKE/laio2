package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练、学员获取来噢教练信息(用默认Key加解密)
 *
 * @author yedong
 */
public class CoachInfoData extends BaseData {
    // 请求参数
    // ObjectType Integer 用户类型：0学员 1教练
    // MasterUUID String 教练账号UUID

    public String MasterName; // 教练名字
    public String MasterSchoolName; // 教练所属驾校
    public int MasterRecommendIndex;// 学员评价（原推荐指数）
    public double MasterCreditRank; // 教练信用等级，最大值为5

    public int RateSignupTotal; // 招生类评价总数
    public int RateTrainTotal; // 培训类评价总数
    public int RateTrainCivilTeach; // 文明教学平均分
    public int RateTrainBenefit; // 吃拿卡要平均分
    public int RateTrainTeachArea; // 教学场地平均分
    public int RateTrainTeachQuality; // 带教质量平均分
    public int RateTrainCarCondition; // 车辆车况平均分

    public String MasterPicURL; // 教练头像
    public String CityName; // 所属城市
    public String DistrictName; // 所属县市区
    public String MasterInfo; // 教练简介信息，包含所属驾校<br>招生地址<br>联系电话<br>准教车型
    public String CardInfo; // 准教证、驾驶证等信息，以<br>分隔(注：学员请求时，不会返回此信息)

    // 新增字段 2014.10.15（web端使用）
    public int AuthStatus; // 实名认证，1已实名认证，非1未实名认证
    public int PapersReady; // 是否五证齐全，1表示是，0表示否

    public int MasterCollected; // 已登录学员获取查看的教练是否已被收藏，0未收藏，1已收藏

    public String TrainCarType; // 准教车型c1
    public String TrainCarModel; // 培训车型 (桑塔纳)

    public String MasterAccount; // 教练账号
    public String MasterPhone; // 教练联系电话

}
