package com.bofsoft.laio.customerservice.DataClass.Order;

import java.io.Serializable;

/**
 * @author 网络商品服务_获取认证状态(教练)
 */

/**
 * @author yedong
 */
@SuppressWarnings("serial")
public class ApplyPulishAuthStateData implements Serializable {
    public final static int AUTH_STATUS_AUTHING = 2; // 2-认证中
    public final static int AUTH_STATUS_HAS_AUTH = 1; // 1-已认证
    public final static int AUTH_STATUS_WAIT_SUB = 0; // 0-确认提交
    public final static int AUTH_STATUS_NOT_AUTH = -1; // -1-未认证(待提交)
    public final static int AUTH_STATUS_FAILURE = -2; // -2-认证失败

    // 请求参数

    // 返回参数
    public int ERPAccountStatus = 0; // ERP帐号绑定，1已绑定，0未绑定
    public int EmailStatus = -1; // 电子邮箱是否已绑定，1已绑定，0绑定中，-1未绑定（暂无)

    /*
     * 注：状态为1、2时不能修改“商家入住”中的信息，其它状态都可修改，修改了任何“商家入住”信息， 状态就会变成-1，点击“确认提交”后状态变成0确认提交
     */
    public int AuthStatus = -1; // 认证状态，2-认证中,1-已认证，0-确认提交，-1-未认证(待提交)，-2-认证失败（待提交）
    public String ERPAccountStatusName = "未绑定"; // ERP帐号绑定状态，已绑定、未绑定等
    public String EmailStatusName = "未绑定"; // 电子邮箱是否已绑定状态，已绑定、绑定中、未绑定等（暂无）
    public String AuthStatusName = "未认证"; // 认证信息状态，已认证、认证中、未认证、认证失败等
    public String TeachCarType; // 准教车型DM，C1、C2等
    public String TeachCarModel; //
    public String DistrictDM; // String 区域DM
    public String DistrictName; // String 区域名称
    public int ShowDPModule = 1;// 是否显示与DP有关的模块，0否，1是


    // 2014.10.24新增
    public String AuthDetail = ""; // 商家入住认证意见，失败时才显示

    public int getERPAccountStatus() {
        return ERPAccountStatus;
    }

    public void setERPAccountStatus(int eRPAccountStatus) {
        ERPAccountStatus = eRPAccountStatus;
    }

    public int getEmailStatus() {
        return EmailStatus;
    }

    public void setEmailStatus(int emailStatus) {
        EmailStatus = emailStatus;
    }

    public int getAuthStatus() {
        return AuthStatus;
    }

    public void setAuthStatus(int authStatus) {
        AuthStatus = authStatus;
    }

    public String getERPAccountStatusName() {
        return ERPAccountStatusName;
    }

    public void setERPAccountStatusName(String eRPAccountStatusName) {
        ERPAccountStatusName = eRPAccountStatusName;
    }

    public String getEmailStatusName() {
        return EmailStatusName;
    }

    public void setEmailStatusName(String emailStatusName) {
        EmailStatusName = emailStatusName;
    }

    public String getAuthStatusName() {
        return AuthStatusName;
    }

    public void setAuthStatusName(String authStatusName) {
        AuthStatusName = authStatusName;
    }

    public String getTeachCarType() {
        return TeachCarType;
    }

    public void setTeachCarType(String teachCarType) {
        TeachCarType = teachCarType;
    }

    public String getTeachCarModel() {
        return TeachCarModel;
    }

    public void setTeachCarModel(String teachCarModel) {
        TeachCarModel = teachCarModel;
    }

    public String getDistrictDM() {
        return DistrictDM;
    }

    public void setDistrictDM(String districtDM) {
        DistrictDM = districtDM;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public void setDistrictName(String districtName) {
        DistrictName = districtName;
    }

    public int getShowDPModule() {
        return ShowDPModule;
    }

    public void setShowDPModule(int showDPModule) {
        ShowDPModule = showDPModule;
    }

    @Override
    public String toString() {
        return "ApplyPulishAuthStateData [ERPAccountStatus=" + ERPAccountStatus + ", EmailStatus="
                + EmailStatus + ", AuthStatus=" + AuthStatus + ", ERPAccountStatusName="
                + ERPAccountStatusName + ", EmailStatusName=" + EmailStatusName + ", AuthStatusName="
                + AuthStatusName + ", TeachCarType=" + TeachCarType + ", TeachCarModel=" + TeachCarModel
                + ", DistrictDM=" + DistrictDM + ", DistrictName=" + DistrictName + ", ShowDPModule=" + ShowDPModule + "]";
    }

}
