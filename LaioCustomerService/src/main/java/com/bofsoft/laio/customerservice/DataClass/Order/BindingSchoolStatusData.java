package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * @author 获取绑定的ERP账号信息 (教练)
 */
public class BindingSchoolStatusData extends BaseData {

    public int BindStatus; // 绑定状态，1已绑定，0未绑定，-1需要重新绑定
    public String SchoolNum; // 驾校编号
    public String SchoolName;// 驾校名称
    public String UserName; // 用户名

}
