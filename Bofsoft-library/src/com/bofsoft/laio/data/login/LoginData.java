package com.bofsoft.laio.data.login;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * 客户端登陆
 *
 * @author admin
 */
@SuppressWarnings("serial")
public class LoginData implements Serializable {
    // 请求参数
    private String Username;
    private String Userpassword;
    private String Type; // 0:学员 1:教练
    private String Danwei; // 当Type为教练的时候这个就是单位编号
    private String Ver; // 软件版本号
    private String SystemType; // 操作系统，android的话就发送android，iphone就发送ios
    private String SystemVersion; // 操作系统版本号
    private String UserToken; // 用户推送token，如果没获取到传“”
    private String GUID; // 客户端的唯一标识，程序第一次运行时产生保存在客户端，生成后不再重新生成
    private String PhoneManufacturers;
    private String PhoneModel;

    // 返回参数
    private String Key; // 加密key
    private String Session; // 通讯的session

    private String UserPhone; // 用户手机号（如果没有就填写“”）
    private String UserERPName; // 用户ERP名称（如果没有就填写“”）
    private String UserERPDanwei; // 用户ERP单位（如果没有就填写“”）
    private String UserERPTrueName; // 用户ERP用户姓名（如果没有就填写“”）（租车、GPS新增）
    private String UserERPDanweiName; // 用户ERP单位名称（如果没有就填写“”）（租车、GPS新增）
    private String UserUUID; // 用户唯一标示符，必须有

    // 新增2014.10.18 教练端使用
    private int CoachType; // 教练类型，1机构教练，2个体教练(默认值)

    // 方法

    /**
     * @return 客户端登陆参数
     * @throws JSONException
     */
    public String getLoginData() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("Username", Username);
        js.put("Userpassword", Userpassword);
        js.put("Type", Type);
        js.put("Danwei", Danwei);
        js.put("Ver", Ver);
        js.put("SystemType", SystemType);
        js.put("SystemVersion", SystemVersion);
        js.put("UserToken", UserToken);
        js.put("GUID", GUID);
        js.put("PhoneManufacturers", PhoneManufacturers);
        js.put("PhoneModel", PhoneModel);

        return js.toString();
    }

    public static LoginData parseLoginData(JSONObject js) {
        if (js == null) {
            return null;
        }
        LoginData data = new LoginData();
        try {
            data.setUsername(js.getString("Username"));
            data.setUserpassword(js.getString("Userpassword"));
            data.setType(js.getString("Type"));
            data.setDanwei(js.getString("Danwei"));
            data.setVer(js.getString("Ver"));
            data.setSystemType(js.getString("SystemType"));
            data.setSystemVersion(js.getString("SystemVersion"));
            data.setGUID(js.getString("GUID"));
            data.setPhoneManufacturers(js.getString("PhoneManufacturers"));
            data.setPhoneModel(js.getString("PhoneModel"));
        } catch (JSONException e) {
            e.printStackTrace();
            System.out.println(js.toString());
            return data;
        }
        return data;
    }

    /**
     * 解析登陆返回string
     *
     * @param js
     * @return
     * @throws JSONException
     */
    public static LoginData InitData(JSONObject js) throws JSONException {
        System.out.println("LoginData JSONObject=" + js.toString());
        LoginData tmp = new LoginData();
        tmp.Key = js.getString("Key");
        tmp.Session = js.getString("Session");
        tmp.UserPhone = js.getString("UserPhone");
        tmp.UserERPName = js.getString("UserERPName");
        tmp.UserERPDanwei = js.getString("UserERPDanwei");
        tmp.UserERPTrueName = js.getString("UserERPTrueName");
        tmp.UserERPDanweiName = js.getString("UserERPDanweiName");
        tmp.UserUUID = js.getString("UserUUID");
        tmp.CoachType = js.getInt("CoachType");
        return tmp;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getUserpassword() {
        return Userpassword;
    }

    public void setUserpassword(String userpassword) {
        Userpassword = userpassword;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getDanwei() {
        return Danwei;
    }

    public void setDanwei(String danwei) {
        Danwei = danwei;
    }

    public String getVer() {
        return Ver;
    }

    public void setVer(String ver) {
        Ver = ver;
    }

    public String getSystemType() {
        return SystemType;
    }

    public void setSystemType(String systemType) {
        SystemType = systemType;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String gUID) {
        GUID = gUID;
    }

    public String getPhoneManufacturers() {
        return PhoneManufacturers;
    }

    public void setPhoneManufacturers(String phoneManufacturers) {
        PhoneManufacturers = phoneManufacturers;
    }

    public String getPhoneModel() {
        return PhoneModel;
    }

    public void setPhoneModel(String phoneModel) {
        PhoneModel = phoneModel;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }

    public String getSession() {
        return Session;
    }

    public void setSession(String session) {
        Session = session;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }

    public String getUserERPName() {
        return UserERPName;
    }

    public void setUserERPName(String userERPName) {
        UserERPName = userERPName;
    }

    public String getUserERPDanwei() {
        return UserERPDanwei;
    }

    public void setUserERPDanwei(String userERPDanwei) {
        UserERPDanwei = userERPDanwei;
    }

    public String getUserERPTrueName() {
        return UserERPTrueName;
    }

    public void setUserERPTrueName(String userERPTrueName) {
        UserERPTrueName = userERPTrueName;
    }

    public String getUserERPDanweiName() {
        return UserERPDanweiName;
    }

    public void setUserERPDanweiName(String userERPDanweiName) {
        UserERPDanweiName = userERPDanweiName;
    }

    public String getUserUUID() {
        return UserUUID;
    }

    public void setUserUUID(String userUUID) {
        UserUUID = userUUID;
    }

    public int getCoachType() {
        return CoachType;
    }

    public void setCoachType(int coachType) {
        CoachType = coachType;
    }


}
