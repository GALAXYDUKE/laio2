package com.bofsoft.laio.laiovehiclegps.DataClass;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本更新
 *
 * @author admin
 */
public class CheckVersionUpdate {
    // 请求参数
    private String SoftVersion;
    private String SystemType;
    private String SystemVersion;
    private String PhoneManufacturers;
    private String PhoneModel;
    private String GUID;
    private int AppType; // 0教练App，1学员App

    // 返回参数
    private Integer IsNeetUpgrade; // 是否需要升级，0表示不需要，1表示建议升级（弹出提示框：有新版本，是否需要升级），2表示强制升级
    private String Content; // 升级内容描述
    private String MD5; // 当前升级APK的MD5值
    private String UpgradeURL; // APK下载的url

    public String getSoftVersion() {
        return SoftVersion;
    }

    public void setSoftVersion(String softVersion) {
        SoftVersion = softVersion;
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

    public String getGUID() {
        return GUID;
    }

    public void setGUID(String gUID) {
        GUID = gUID;
    }

    public int getAppType() {
        return AppType;
    }

    public void setAppType(int appType) {
        AppType = appType;
    }

    public Integer getIsNeetUpgrade() {
        return IsNeetUpgrade;
    }

    public void setIsNeetUpgrade(Integer isNeetUpgrade) {
        IsNeetUpgrade = isNeetUpgrade;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String mD5) {
        MD5 = mD5;
    }

    public String getUpgradeURL() {
        return UpgradeURL;
    }

    public void setUpgradeURL(String upgradeURL) {
        UpgradeURL = upgradeURL;
    }

    // 方法
    /**
     * @return 检查更新请求参数
     * @throws JSONException
     */
    public String getCheckVersionUpgrade() throws JSONException {
        JSONObject js = new JSONObject();
        js.put("SoftVersion", SoftVersion);
        js.put("SystemType", SystemType);
        js.put("SystemVersion", SystemVersion);
        js.put("PhoneManufacturers", PhoneManufacturers);
        js.put("PhoneModel", PhoneModel);
        js.put("GUID", GUID);
        js.put("AppType", AppType);

        return js.toString();
    }

    public static CheckVersionUpdate InitData(JSONObject js) throws JSONException {
        CheckVersionUpdate tmp = new CheckVersionUpdate();
        tmp.IsNeetUpgrade = js.getInt("IsNeetUpgrade");
        tmp.Content = js.getString("Content");
        tmp.MD5 = js.getString("MD5");
        tmp.UpgradeURL = js.getString("UpgradeURL");

        return tmp;
    }

}
