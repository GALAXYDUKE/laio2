package com.bofsoft.laio.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bofsoft.laio.common.CrashHandler;
import com.bofsoft.laio.common.MyLog;

public class DBCacheHelper extends SQLiteOpenHelper {

    private static MyLog myLog = new MyLog(DBCacheHelper.class);
    private volatile static DBCacheHelper mInstance = null;

    public static final String databaseName = "laioclient_t.db";
    public static final int databaseVersion = 9; // 20150701 tableMsg 增加Key字段

    public static final String tableMsg = "MsgList";
    public static final String tableImage = "laio_imagecache"; // 图片缓存记录
    public static final String tableUpdate = "laio_tableupdate"; // 数据表更新记录

    public static final String FIELD_ID = "_id";
    public static final String FIELD_MSG_ID = "MsgID";
    public static final String FIELD_FROM_M = "FromM";// 谁发送的，存放User
    public static final String FIELD_MSG = "Msg";// 发送的消息内容
    public static final String FIELD_TIME = "Time";// 发送时间
    public static final String FIELD_TYPE = "Type";// 发送人的类型：1：驾校通知2：系统公告3：教练信息4：学员信息 11：订单消息 12：抢单消息
    public static final String FIELD_KEY = "Key";// 需要触发动作的值，具体值根据Type类型确定 Key=OrderId or
    // Key=DemandUUID
    public static final String FIELD_SHOWNAME = "ShowName";// 用于在界面上展示的用户名
    public static final String FIELD_IS_READ = "isRead";// 是否已读
    public static final String FIELD_TO_M = "ToM";// 接收者
    public static final String FIELD_LOCALTIME = "LocalTime";// 本地时间
    public static final String FIELD_IS_SEND = "isSend";// 是否发送成功
    public static final String FIELD_TAG_NAME = "TagName";

    // 跟新表的操作
    // 升级数据库，从第一版本升级到第二版本。主要是添加isLoadFromServ(是否已经从服务器读取msg内容,本地使用)，msg内容的id，
    // 通过这个id去获取详细信息，服务器返回，升级数据库的时候，isLoadFromServ回初始化成0，msgid初始化成0,升级之前的版本号是2，
    // 升级的主要操作是加入两个字段（msgid（详细信息的id和消息是否一经获取））

    // 创建或打开数据库
    private DBCacheHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public static DBCacheHelper getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBCacheHelper.class) {
                if (mInstance == null) {
                    mInstance = new DBCacheHelper(context);
                }
            }
        }
        return mInstance;
    }

    // 当数据库被创建时触发创建表
    @Override
    public void onCreate(SQLiteDatabase db) {
        myLog.e("数据库onCreate");
        initDatabase(db);
    }

    /**
     * 初始化数据库表
     *
     * @param db
     */
    private void initDatabase(SQLiteDatabase db) {
        // 通知
        String sqlMsg =
                "create table if not exists " + tableMsg + "(" + FIELD_ID + " INTEGER PRIMARY KEY,"
                        + FIELD_MSG_ID + " INTEGER," + FIELD_FROM_M + " TEXT," + FIELD_MSG + " TEXT,"
                        + FIELD_TIME + " TEXT," + FIELD_TYPE + " INTEGER," + FIELD_KEY + " TEXT,"
                        + FIELD_SHOWNAME + " TEXT," + FIELD_IS_READ + " INTEGER," + FIELD_TO_M + " TEXT,"
                        + FIELD_LOCALTIME + " TEXT," + FIELD_IS_SEND + " INTEGER," + FIELD_TAG_NAME + " TEXT);";

        String sqlWelcome =
                "create table if not exists intro (_id INTEGER PRIMARY KEY, introbool TEXT);";

        // 驾校通知
        String sqlSchoolTzMsg =
                "create table if not exists jxtzmsg (_id INTEGER PRIMARY KEY, "
                        + "title TEXT,msg TEXT,puddate TEXT,isread TEXT,msgid INTEGER,isget INTEGER);";

        // 图片缓存表
        String sqlImageCache =
                "CREATE TABLE if not exists " + tableImage
                        + " ( Id INTEGER PRIMARY KEY,  UUID char(36) NOT NULL, "
                        + "ImgType varchar(20) NOT NULL, ImgName varchar(100) NOT NULL, "
                        + "UpdateTime varchar(30) NOT NULL);";

        // 数据表更新记录
        String sqlTableUpdate =
                "CREATE TABLE if not exists " + tableUpdate + " ( Id INTEGER PRIMARY KEY, "
                        + "ConfType INTEGER NOT NULL, TableName varchar(100) NOT NULL, "
                        + "UpdateTime DATETIME NOT NULL);";

        db.execSQL(sqlMsg);
        db.execSQL(sqlWelcome);
        db.execSQL(sqlSchoolTzMsg);
        db.execSQL(sqlImageCache);
        db.execSQL(sqlTableUpdate);
    }

    // 升级数据库时触发
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        myLog.e("---onUpgrade------oldVersion|" + oldVersion + "-----newVersion|" + newVersion);
        switch (newVersion) {
            case 6:
                String insrtmsgid = "alter table jxtzmsg add msgid INTEGER default 0";
                String insertisget = "alter table jxtzmsg add isget INTEGER default 0";
                try {
                    db.execSQL(insrtmsgid);
                    db.execSQL(insertisget);
                } catch (SQLException e) {
                    e.printStackTrace();
                    CrashHandler.getInstance().reportError(e);
                }
                break;

            case 7:
                // 2015.02.03 某些情况造成没创建 图片缓存表和更新表导致no such table 异常,所以初始化一次
                initDatabase(db);
                break;
            case 8: // 教练消息表更新，新增MsgId字段（避免插入重复消息）
            case 9: // 教练消息表更新，新增Key字段（标示消息具体内容或动作）
                String deleteMsgTable = "DROP TABLE IF EXISTS " + tableMsg;
                db.execSQL(deleteMsgTable);
                initDatabase(db);
                break;

            default:
                break;

        }

    }

}
