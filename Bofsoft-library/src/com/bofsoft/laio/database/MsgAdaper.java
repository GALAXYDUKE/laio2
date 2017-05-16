package com.bofsoft.laio.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.common.CrashHandler;
import com.bofsoft.laio.common.MyLog;

/**
 * 消息操作类
 *
 * @author admin
 */
public class MsgAdaper {
    private static MyLog myLog = new MyLog(MsgAdaper.class);
    private volatile static MsgAdaper mAdaper = null;

    private DBCacheHelper helper;
    private SQLiteDatabase db = null;

    private MsgAdaper(Context context) {
        helper = DBCacheHelper.getInstance(context); // 创建或打开数据库
    }

    public static MsgAdaper getInstance(Context context) {
        if (mAdaper == null) {
            synchronized (MsgAdaper.class) {
                if (mAdaper == null) {
                    mAdaper = new MsgAdaper(context);
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

    public Cursor list() {
        open();
        return db.query(DBCacheHelper.tableMsg, new String[]{DBCacheHelper.FIELD_ID,
                DBCacheHelper.FIELD_FROM_M, DBCacheHelper.FIELD_MSG, DBCacheHelper.FIELD_TIME,
                DBCacheHelper.FIELD_TYPE, DBCacheHelper.FIELD_KEY, DBCacheHelper.FIELD_SHOWNAME,
                DBCacheHelper.FIELD_IS_READ, DBCacheHelper.FIELD_TO_M, DBCacheHelper.FIELD_LOCALTIME,
                DBCacheHelper.FIELD_IS_SEND, DBCacheHelper.FIELD_TAG_NAME}, null, null, null, null, null);
    }

    /**
     * @param msgId
     * @param fromM     谁发送的，存放User
     * @param msg       发送的消息内容
     * @param time      发送时间
     * @param type      发送人的类型：1：驾校通知2：系统公告3：教练信息4：学员信息
     * @param showName  用于在界面上展示的用户名
     * @param isRead    是否已读
     * @param toM       接收者
     * @param localTime 本地时间
     * @param isSend    是否发送成功
     * @param tagName
     * @return
     */
    public long insert(int msgId, String fromM, String msg, String time, Integer type, String key,
                       String showName, Integer isRead, String toM, String localTime, Integer isSend, String tagName) {
        ContentValues values = new ContentValues();
        values.put(DBCacheHelper.FIELD_MSG_ID, msgId);
        values.put(DBCacheHelper.FIELD_FROM_M, fromM);
        values.put(DBCacheHelper.FIELD_MSG, msg);
        values.put(DBCacheHelper.FIELD_TIME, time);
        values.put(DBCacheHelper.FIELD_TYPE, type);
        values.put(DBCacheHelper.FIELD_KEY, key);
        values.put(DBCacheHelper.FIELD_SHOWNAME, showName);
        values.put(DBCacheHelper.FIELD_IS_READ, isRead);
        values.put(DBCacheHelper.FIELD_TO_M, toM);
        values.put(DBCacheHelper.FIELD_LOCALTIME, localTime);
        values.put(DBCacheHelper.FIELD_IS_SEND, isSend);
        values.put(DBCacheHelper.FIELD_TAG_NAME, tagName);

        open();
        long rowId = db.insert(DBCacheHelper.tableMsg, null, values);
        return rowId;
    }

    /**
     * @param msgId
     * @param fromM     谁发送的，存放User
     * @param msg       发送的消息内容
     * @param time      发送时间
     * @param type      发送人的类型：1：驾校通知2：系统公告3：教练信息4：学员信息
     * @param showName  用于在界面上展示的用户名
     * @param isRead    是否已读
     * @param toM       接收者
     * @param localTime 本地时间
     * @param isSend    是否发送成功
     * @param tagName
     * @return
     */
    public long replace(int msgId, String fromM, String msg, String time, int type, String key,
                        String showName, int isRead, String toM, String localTime, int isSend, String tagName) {
        open();
        String repQuerySql =
                "select * from " + DBCacheHelper.tableMsg + " where " + DBCacheHelper.FIELD_MSG_ID + "="
                        + msgId;
        Cursor cursor = db.rawQuery(repQuerySql, null);
        // 已接收则不插入
        if (cursor.getCount() > 0) {
            myLog.i("---------此消息已接收");
            return 0;
        }
        ContentValues values = new ContentValues();
        values.put(DBCacheHelper.FIELD_ID, msgId);
        values.put(DBCacheHelper.FIELD_MSG_ID, msgId);
        values.put(DBCacheHelper.FIELD_FROM_M, fromM);
        values.put(DBCacheHelper.FIELD_MSG, msg);
        values.put(DBCacheHelper.FIELD_TIME, time);
        values.put(DBCacheHelper.FIELD_TYPE, type);
        values.put(DBCacheHelper.FIELD_KEY, key);
        values.put(DBCacheHelper.FIELD_SHOWNAME, showName);
        values.put(DBCacheHelper.FIELD_IS_READ, isRead);
        values.put(DBCacheHelper.FIELD_TO_M, toM);
        values.put(DBCacheHelper.FIELD_LOCALTIME, localTime);
        values.put(DBCacheHelper.FIELD_IS_SEND, isSend);
        values.put(DBCacheHelper.FIELD_TAG_NAME, tagName);

        return db.replace(DBCacheHelper.tableMsg, null, values);
    }

    public boolean delete(long id) {
        open();
        boolean isSuccess =
                db.delete(DBCacheHelper.tableMsg, DBCacheHelper.FIELD_ID + "=" + id, null) > 0;
        return isSuccess;
    }

    public Cursor get(long id) {
        open();
        Cursor cursor =
                db.query(DBCacheHelper.tableMsg, new String[]{DBCacheHelper.FIELD_ID,
                        DBCacheHelper.FIELD_FROM_M, DBCacheHelper.FIELD_MSG, DBCacheHelper.FIELD_TIME,
                        DBCacheHelper.FIELD_TYPE, DBCacheHelper.FIELD_KEY, DBCacheHelper.FIELD_SHOWNAME,
                        DBCacheHelper.FIELD_IS_READ, DBCacheHelper.FIELD_TO_M, DBCacheHelper.FIELD_LOCALTIME,
                        DBCacheHelper.FIELD_IS_SEND, DBCacheHelper.FIELD_TAG_NAME}, DBCacheHelper.FIELD_ID
                        + "=" + id, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public int getUnReadCount() {
        if (ConfigAll.UserUUID == null)
            return 0;
        Cursor cursor =
                rawQuery("select count(*) from " + DBCacheHelper.tableMsg + " where ToM=? and isRead=0",
                        new String[]{ConfigAll.UserUUID});
        if (cursor == null) {
            return 0;
        }
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public Cursor rawQuery(String sql, String[] selectionArgs) {
        open();
        Cursor cursor = db.rawQuery(sql, selectionArgs);
        return cursor;
    }

    public void exeSQL(String sql, String[] selectionArgs) {
        open();
        db.execSQL(sql, selectionArgs);
    }

    /**
     * @param msgId
     * @param fromM     谁发送的，存放User
     * @param msg       发送的消息内容
     * @param time      发送时间
     * @param type      发送人的类型：1：驾校通知2：系统公告3：教练信息4：学员信息
     * @param showName  用于在界面上展示的用户名
     * @param isRead    是否已读
     * @param toM       接收者
     * @param localTime 本地时间
     * @param isSend    是否发送成功
     * @param tagName
     * @return
     */
    public boolean update(long id, int msgId, String fromM, String msg, String time, Integer type,
                          String key, String showname, Integer isField, String toM, String localTime, Integer isSend,
                          String tagName) {
        ContentValues values = new ContentValues();
        values.put(DBCacheHelper.FIELD_MSG_ID, msgId);
        values.put(DBCacheHelper.FIELD_FROM_M, fromM);
        values.put(DBCacheHelper.FIELD_MSG, msg);
        values.put(DBCacheHelper.FIELD_TIME, time);
        values.put(DBCacheHelper.FIELD_TYPE, type);
        values.put(DBCacheHelper.FIELD_KEY, key);
        values.put(DBCacheHelper.FIELD_SHOWNAME, showname);
        values.put(DBCacheHelper.FIELD_IS_READ, isField);
        values.put(DBCacheHelper.FIELD_TO_M, toM);
        values.put(DBCacheHelper.FIELD_LOCALTIME, localTime);
        values.put(DBCacheHelper.FIELD_IS_SEND, isSend);
        values.put(DBCacheHelper.FIELD_TAG_NAME, tagName);

        open();
        return db.update(DBCacheHelper.tableMsg, values, DBCacheHelper.FIELD_ID + "=" + id, null) > 0;
    }

    // 使用不当可能导致用全新连接去取回来的值始终是0哦 建议不要用
    public Integer select_last_id() {
        open();
        Cursor cursor = rawQuery("select last_insert_rowid() from " + DBCacheHelper.tableMsg, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        int rowId = cursor.getInt(0);
        return rowId;
    }
}
