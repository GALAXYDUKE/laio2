package com.bofsoft.laio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bofsoft.laio.common.CrashHandler;
import com.bofsoft.laio.data.index.SchoolNoticeData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 驾校通知消息操作
 *
 * @author admin
 */
public class MsgJxtzAdapter {
    private volatile static MsgJxtzAdapter mAdaper = null;

    private DBCacheHelper helper;
    private SQLiteDatabase db = null;

    private MsgJxtzAdapter(Context context) {
        helper = DBCacheHelper.getInstance(context); // 创建或打开数据库
    }

    public static MsgJxtzAdapter getInstance(Context context) {
        if (mAdaper == null) {
            synchronized (MsgJxtzAdapter.class) {
                if (mAdaper == null) {
                    mAdaper = new MsgJxtzAdapter(context);
                }
            }
        }
        mAdaper.open();
        return mAdaper;
    }

    public boolean isOpen() {
        if (db != null) {
            return db.isOpen();
        }
        return false;
    }

    /**
     * 创建或打开数据库
     */
    public void open() {
        try {
            if (db != null) {
                if (!db.isOpen()) {
                    db = helper.getWritableDatabase(); // 打开
                }
            } else {
                db = helper.getWritableDatabase(); // 创建
            }
        } catch (Exception e) {
            e.printStackTrace();
            CrashHandler.getInstance().reportError(e);
        }
    }

    /**
     * 关闭数据库
     */
    public void close() {
        if (db == null) {
            return;
        }
        try {
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        db = null;
    }

    public int getUnReadCount() {
        open();
        int a = 0;
        try {
            Cursor cursor = db.rawQuery("select * from jxtzmsg where isread=0", null);
            a = cursor.getCount();
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return a;
    }

    public void insert(String title, String msg, String puddate, String isread, String msgid,
                       String isget) {
        open();
        ContentValues cv = new ContentValues();
        cv.put("title", title);
        cv.put("msg", msg);
        cv.put("puddate", puddate);
        cv.put("isread", isread);
        cv.put("msgid", msgid);
        cv.put("isget", isget);
        db.insert("jxtzmsg", null, cv);
    }

    // 插入详细信息岛数据库中
    public void insertMsg(String msg) {
        open();
        db.execSQL("insert into jxtzmsg(msg) values(?)", new Object[]{msg});
    }

    // ===>>修改跟新方法
    // 加入修改字段（消息内容）msg
    // 2014年6月6日15:05:04
    public int update(long id, String msg) {
        open();
        ContentValues values = new ContentValues();
        values.put("isread", "1");
        values.put("msg", msg);
        values.put("isget", "1");
        int a = db.update("jxtzmsg", values, "_id=?", new String[]{String.valueOf(id)});
        return a;
    }

    public String Text() {
        open();
        Cursor cursor = db.rawQuery("select * from jxtzmsg order by _id desc", null);
        String a = "";
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            a = cursor.getString(cursor.getColumnIndex("title"));
        }
        cursor.close();
        return a;
    }

    public List<SchoolNoticeData> getList(int Times) {
        open();
        List<SchoolNoticeData> list = new ArrayList<SchoolNoticeData>();
        Cursor cursor = db.rawQuery("select * from jxtzmsg order by _id desc limit " + (Times * 10) + ",10", null);
        while (cursor.moveToNext()) {
            SchoolNoticeData data = new SchoolNoticeData();
            data._id = cursor.getInt(cursor.getColumnIndex("_id"));
            data.title = cursor.getString(cursor.getColumnIndex("title"));
            data.msg = cursor.getString(cursor.getColumnIndex("msg"));
            data.puddate = cursor.getString(cursor.getColumnIndex("puddate"));
            data.isread = cursor.getInt(cursor.getColumnIndex("isread"));
            data.msgid = cursor.getInt(cursor.getColumnIndex("msgid"));
            data.isget = cursor.getInt(cursor.getColumnIndex("isget"));
            list.add(data);
        }
        cursor.close();
        return list;
    }

    public String UpdateMsg(int id) {
        Cursor cursor = db.rawQuery("select * from jxtzmsg where _id=" + id, null);
        SchoolNoticeData data = new SchoolNoticeData();
        while (cursor.moveToNext()) {
            data.msg = cursor.getString(cursor.getColumnIndex("msg"));
        }
        cursor.close();
        return data.msg;
    }

    public List<Map<String, Object>> getCursor() {
        open();
        List<Map<String, Object>> mData = new ArrayList<Map<String, Object>>();
        Cursor cursor = db.rawQuery("select * from jxtzmsg order by _id desc", null);
        Map<String, Object> map;
        while (cursor.moveToNext()) {
            map = new HashMap<String, Object>();
            map.put("id", cursor.getInt(0));
            map.put("title", cursor.getString(1));
            map.put("msg", cursor.getString(2));
            map.put("time", cursor.getString(3));
            map.put("isread", cursor.getString(4));
            map.put("msgid", cursor.getString(5));
            map.put("isget", cursor.getString(6));
            mData.add(map);
        }
        cursor.close();
        return mData;
    }

    public int delete(int id) {
        open();
        int a = db.delete("jxtzmsg", "_id=?", new String[]{String.valueOf(id)});
        return a;
    }

    // 查询指定id的详细信息
    public String selectMsg(int id) {
        open();
        String jxtzmsg = "";
        Cursor cursor = db.rawQuery("select * from jxtzmsg  where _id=?", new String[]{id + ""});
        while (cursor.moveToNext()) {
            jxtzmsg = cursor.getString(2);
        }
        cursor.close();
        return jxtzmsg;
    }

}
