package com.bofsoft.laio.customerservice.DataClass;


import android.content.ContentValues;
import android.database.Cursor;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * 数据库操作（查询，插入）数据类型转换工具
 *
 * @author admin
 */
public class DBConvertUtil {

    /**
     * cursor获取data对象
     *
     * @param cls
     * @param cursor
     * @return
     */
    public static <T> T cursor2Data(Class<T> cls, Cursor cursor) {
        T obj = null;
        if (cursor == null) {
            return obj;
        }
        JSONObject jObject = new JSONObject();
        int count = cursor.getColumnCount();
        try {
            for (int i = 0; i < count; i++) {
                // 低版本API11以下不支持cursor.geType(),故全getString();
                jObject.put(cursor.getColumnName(i), cursor.getString(i));
            }
            obj = JSON.parseObject(jObject.toString(), cls);
        } catch (JSONException e) {
            e.printStackTrace();
            return obj;
        }
        return obj;
    }

    /**
     * @param t
     * @return
     */
    public static <T> ContentValues data2ContentValues(T t) {
        ContentValues values = null;
        JSONObject jOjb = null;
        try {
            jOjb = new JSONObject(JSON.toJSONString(t));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jOjb != null) {
            values = new ContentValues();
            Iterator<String> iterator = jOjb.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                values.put(key, jOjb.optString(key));
            }
        }
        // System.out.println("DBConvertUtil>>>"+jOjb.toString());
        return values;

    }

    /**
     * json转ContentValues
     *
     * @param jsonObject
     * @return
     */
    public static ContentValues json2ContentValues(JSONObject jsonObject) {
        ContentValues values = null;
        if (jsonObject != null) {
            values = new ContentValues();
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String key = iterator.next();
                values.put(key, jsonObject.optString(key));
            }
        }
        return values;
    }


}
