package com.bofsoft.sdk.utils;

public class VerityUtil {

    /**
     * 电话号码过滤
     *
     * @param tel
     * @return
     */
    public static String Tel(String tel) {
        if (tel == null)
            return tel;
        String vTel = tel.replaceAll(" ", "");
        if (vTel.length() > 11) {
            int len = vTel.length();
            vTel = vTel.substring(len - 11, len);
        }
        return vTel;
    }
}
