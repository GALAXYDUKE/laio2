package com.bofsoft.laio.customerservice.DataClass.index;

import com.bofsoft.laio.data.BaseData;

/**
 * 教练获取我的学员的分类下的学员列表data
 *
 * @author yedong
 */
public class MyStudentClassListInfoData extends BaseData {
    public int CACId; // 学员关联表Id
    public int StuDPId; // 学员在DP中的Id
    public String StuUUID; // 学员的账户UUID
    public String Name; // 学员姓名
    public String NickName; // 学员昵称
    public String Phone; // 学员电话
    public int HasVerify; // 是否已经注册Laio账号，为1时表示已注册，为0时可以进行邀请；
    public String IDCardNum;// 学员身份证号码

}
