package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

import java.util.List;

/**
 * 学员端_查看单个报名产品
 *
 * @author yedong
 */
public class ShareContentData extends BaseData {
    // 请求参数
    // MobileType Integer 来源类型： 1：Android 2：IOS 3：Web
    // ObjectType Integer 用户类型：0:学员 1:教练

    public List<ShareInfo> software; // 软件分享信息列表
    public String enrolltitle;//	String	报名学车分享标题
    public List<ShareInfo> enroll; // 报名学车分享信息列表
    public List<ShareInfo> appoint; // 预约学车分享信息列表
    public List<ShareInfo> retrain; // 我要补训分享信息列表
    public List<ShareInfo> train; // 找陪练分享信息列表

}
