package com.bofsoft.laio.customerservice.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bofsoft.laio.database.DBCacheHelper;

/**
 * 欢迎页面显示记录
 */
public class IntroDBAdapter {
    private DBCacheHelper helper;

    public IntroDBAdapter(Context context) {
        // helper = new DBCacheHelper(context);
        helper = DBCacheHelper.getInstance(context);
    }

    public int introBool() {
        SQLiteDatabase sdb = helper.getReadableDatabase();
        Cursor cursor = sdb.query("intro", new String[]{"_id"}, null, null, null, null, null);
        int a = cursor.getCount();
        cursor.close();
        sdb.close();
        return a;

    }

    public void insert(String introBool, String boolString) {
        SQLiteDatabase sdb = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(introBool, boolString);
        long s = sdb.insert("intro", null, cv);
        sdb.close();
    }
}
