package com.bofsoft.laio.customerservice.DataClass;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * Created by jason.xu on 2017/3/24.
 */

public class JasonLogin {
    //String	登录用户名
    private String Username;
    //String	用户密码
    private String Userpassword;
    //0:学员 1:教练 2:机构 3:ERP教练(登录成功后变成1)
    private String Type;
    //	String	当Type为教练的时候这个就是单位编号
    private String Danwei;
    //	String	登录验证类型，1密码，2验证码
    private String LoginType;
    //	String	软件版本号
    private String Ver;
    //	String	客户端类型，0来噢学车，1来噢教练，8来噢客服
    private String AppType;
    //	String	操作系统，android的话就发送android，iphone就发送ios
    private String SystemType;
    //	String	操作系统版本号
    private String SystemVersion;
    //String	用户推送token，如果没获取到传“”
    private String UserToken;
    //String	客户端的唯一标识，程序第一次运行时产生保存在客户端，生成后不再重新生成
    private String GUID;

    @JSONField(name = "Username")
    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    @JSONField(name = "Userpassword")
    public String getUserpassword() {
        return Userpassword;
    }

    public void setUserpassword(String userpassword) {
        Userpassword = userpassword;
    }

    @JSONField(name = "Type")
    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    @JSONField(name = "Danwei")
    public String getDanwei() {
        return Danwei;
    }

    public void setDanwei(String danwei) {
        Danwei = danwei;
    }

    @JSONField(name = "LoginType")
    public String getLoginType() {
        return LoginType;
    }

    public void setLoginType(String loginType) {
        LoginType = loginType;
    }

    @JSONField(name = "Ver")
    public String getVer() {
        return Ver;
    }

    public void setVer(String ver) {
        Ver = ver;
    }

    @JSONField(name = "AppType")
    public String getAppType() {
        return AppType;
    }

    public void setAppType(String appType) {
        AppType = appType;
    }

    @JSONField(name = "SystemType")
    public String getSystemType() {
        return SystemType;
    }

    public void setSystemType(String systemType) {
        SystemType = systemType;
    }

    @JSONField(name = "SystemVersion")
    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    @JSONField(name = "UserToken")
    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    @JSONField(name = "GUID")
    public String getGUID() {
        return GUID;
    }

    public void setGUID(String GUID) {
        this.GUID = GUID;
    }
}
