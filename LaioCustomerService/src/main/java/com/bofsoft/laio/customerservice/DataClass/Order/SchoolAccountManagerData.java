package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 驾校账号管理
 *
 * @author yedong
 */
public class SchoolAccountManagerData extends BaseData {
    public int ERPId; // ERP记录Id
    public String SchoolNum; // 驾校编号
    public String SchoolName; // 驾校名称
    public String ERPUserName; // ERP用户名
    public int BindStatus; // ERP账号状态，1已绑定，0登录失败，需要重新绑定
    public int IsDefault; // 是否为默认账号

}
