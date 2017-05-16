package com.bofsoft.laio.customerservice.DataClass.Order;

import com.bofsoft.laio.data.BaseData;

/**
 * 设置默认的Erp账号
 *
 * @author yedong
 */
public class SettingDefaultErpAccountData extends BaseData {
    // 请求参数
    // ERPId Integer ERP记录Id
    public String UserPhone; // 用户手机号（如果没有就填写“”）
    public String UserERPName; // 用户ERP名称（如果没有就填写“”）
    public String UserERPDanwei; // 用户ERP单位（如果没有就填写“”）

}
