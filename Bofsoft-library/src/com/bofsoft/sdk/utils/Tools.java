package com.bofsoft.sdk.utils;

import android.content.Context;
import android.util.TypedValue;

import com.google.protobuf.GeneratedMessage;
import com.googlecode.protobuf.format.JsonFormat;

public class Tools {
    /**
     * protobuf转json
     *
     * @param message
     * @return
     */
    public static String printToString(com.google.protobuf.Message message) {
        return JsonFormat.printToString(message);
    }

    /**
     * json转protobuf
     *
     * @param result
     * @param clazz
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static GeneratedMessage merge(String result,
                                         com.google.protobuf.GeneratedMessage.Builder builer) {
        try {
            JsonFormat.merge(result.replaceAll("\\\\/", "/"), builer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (GeneratedMessage) builer.build();
    }

    public static int dp2px(Context context, int dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context
                .getResources().getDisplayMetrics()));
    }

    /**
     * 是否省级城市
     *
     * @param city
     * @return
     */
    public static boolean isProvinceForCityName(String city) {
        return city.contains("广西")
                || city.contains("内蒙古")
                || city.contains("西藏")
                || city.contains("宁夏")
                || city.contains("新疆")
                || city.contains("北京")
                || city.contains("天津")
                || city.contains("上海")
                || city.contains("重庆")
                || city.contains("香港")
                || city.contains("澳门");
    }

    /**
     * 是否省级城市
     *
     * @param postCode
     * @return
     */
    public static boolean isProviceForCityNum(String postCode) {
        switch (postCode.substring(0, 2)) {
            case "45":  //广西
            case "15":  //内蒙古
            case "54":  //西藏
            case "64":  //宁夏
            case "65":  //新疆
            case "11":  //北京
            case "12":  //天津
            case "31":  //上海
            case "50":  //重庆
            case "81":  //香港
            case "82":  //澳门
                return true;
        }
        return false;
    }
}
