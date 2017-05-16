package com.bofsoft.sdk.config;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public class BaseMember {

    /**
     * 获取自动登录用户信息
     *
     * @return
     */
    public static LoginEntry getDefaultLogin() {
        List<LoginEntry> list = getAutoLogins();
        for (int i = list.size() - 1; i >= 0; i--) {
            if (list.get(i).isDefault)
                return list.get(i);
        }
        return null;
    }

    /**
     * 获取所有的登录信息
     *
     * @return
     */
    public static List<LoginEntry> getAutoLogins() {
        JSONArray jsonArray;
        List<LoginEntry> list = new ArrayList<BaseMember.LoginEntry>();
        try {
            jsonArray = new JSONArray(BaseSysConfig.spHelper.getString("autoLogin", "[]"));
            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(LoginEntry.prase(jsonArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 退出自动登录，将不记住密码
     *
     * @param userName
     */
    public static void autoLoginOut(String userName) {
        autoLoginSet(userName, "", false, false);
    }

    /**
     * 保存自动登录信息
     *
     * @param userName
     * @param passWord
     * @param autoLogin
     * @param savePassWord
     */
    @SuppressLint("NewApi")
    public static void autoLoginSet(String userName, String passWord, boolean autoLogin,
                                    boolean savePassWord) {
        try {
            JSONArray inJsonArray = new JSONArray();
            JSONArray jsonArray = new JSONArray(BaseSysConfig.spHelper.getString("autoLogin", "[]"));
            LoginEntry newLe = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                LoginEntry le = LoginEntry.prase(jsonArray.getJSONObject(i));
                if (le == null)
                    continue;
                if (le.getUserName() != null && le.getUserName().equals(userName)) {
                    le.setUserName(userName);
                    le.setPassWord(savePassWord ? passWord : "");
                    le.setAutoLogin(autoLogin);
                    le.setSavePassWord(savePassWord);
                    le.setDefault(true);
                    newLe = le;
                } else {
                    le.setDefault(false);
                    inJsonArray.put(le.toJSONObject());
                }
            }
            if (newLe == null)
                inJsonArray.put(new LoginEntry(userName, savePassWord ? passWord : "", autoLogin, savePassWord, true)
                        .toJSONObject());
            else
                inJsonArray.put(newLe.toJSONObject());
            BaseSysConfig.spHelper.putString("autoLogin", inJsonArray.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除所有登录记录信息
     */
    @SuppressLint("NewApi")
    public static void autoLoginClear() {
        BaseSysConfig.spHelper.putString("autoLogin", "[]");
    }

    public static class LoginEntry {
        private String userName = "";
        private String passWord = "";
        private boolean autoLogin = false;
        private boolean savePassWord = false;
        private boolean isDefault = false;

        public LoginEntry() {
            super();
        }

        public LoginEntry(String userName, String passWord, boolean autoLogin, boolean savePassWord,
                          boolean isDefault) {
            super();
            this.userName = userName;
            this.passWord = passWord;
            this.autoLogin = autoLogin;
            this.savePassWord = savePassWord;
            this.isDefault = isDefault;
        }

        public JSONObject toJSONObject() {
            JSONObject jo = new JSONObject();
            try {
                jo.put("userName", userName);
                jo.put("passWord", passWord);
                jo.put("autoLogin", autoLogin);
                jo.put("savePassWord", savePassWord);
                jo.put("isDefault", isDefault);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jo;
        }

        public static LoginEntry prase(JSONObject obj) {
            return new LoginEntry(obj.optString("userName"), obj.optString("passWord"),
                    obj.optBoolean("autoLogin"), obj.optBoolean("savePassWord"), obj.optBoolean("isDefault"));
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName == null ? "" : userName;
        }

        public String getPassWord() {
            return passWord;
        }

        public void setPassWord(String passWord) {
            this.passWord = passWord == null ? "" : passWord;
        }

        public boolean isAutoLogin() {
            return autoLogin;
        }

        public void setAutoLogin(boolean autoLogin) {
            this.autoLogin = autoLogin;
        }

        public boolean isSavePassWord() {
            return savePassWord;
        }

        public void setSavePassWord(boolean savePassWord) {
            this.savePassWord = savePassWord;
        }

        public boolean isDefault() {
            return isDefault;
        }

        public void setDefault(boolean isDefault) {
            this.isDefault = isDefault;
        }
    }
}
