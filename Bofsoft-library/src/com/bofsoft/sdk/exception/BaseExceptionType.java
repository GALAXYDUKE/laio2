package com.bofsoft.sdk.exception;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("UseSparseArrays")
public class BaseExceptionType {

    private static Map<Integer, BaseExceptionType> map = new HashMap<Integer, BaseExceptionType>();

    private int id;

    private String name;

    static {
        addException(10000, "未知");
        addException(10001, "系统");
        addException(10002, "数据读写");
        addException(10003, "配置错误");
        addException(10004, "参数错误");
        addException(10005, "权限错误");
        addException(10006, "??");
        addException(10007, "网络异常");
        addException(10008, "未登录");
    }

    public static final int UNKNOW_VALUE = 10000;
    public static final int SYSTEM_VALUE = 10001;
    public static final int DB_VALUE = 10002;
    public static final int CONFIG_VALUE = 10003;
    public static final int PARAM_VALUE = 10004;
    public static final int PURVIEW_VALUE = 10005;
    public static final int VIP_LIMIT_VALUE = 10006;
    public static final int NET_VALUE = 10007;
    public static final int NO_LOGIN = 10008;

    public static void addException(int id, String name) {
        map.put(id, new BaseExceptionType(id, name));
    }

    public static String getName(int id) {
        return map.get(id).getName();
    }

    public BaseExceptionType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
