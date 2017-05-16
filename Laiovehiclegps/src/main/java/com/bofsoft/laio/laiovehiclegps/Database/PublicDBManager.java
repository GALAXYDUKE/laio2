package com.bofsoft.laio.laiovehiclegps.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.data.BaseData;
import com.bofsoft.laio.laiovehiclegps.Config.ConfigallGps;
import com.bofsoft.laio.laiovehiclegps.DataClass.CityData;
import com.bofsoft.laio.laiovehiclegps.DataClass.DBConvertUtil;
import com.bofsoft.laio.laiovehiclegps.DataClass.DistrictData;
import com.bofsoft.laio.laiovehiclegps.DataClass.SchoolData;
import com.bofsoft.laio.laiovehiclegps.DataClass.TableChkUpdateListData;
import com.bofsoft.laio.laiovehiclegps.DataClass.TableUpdateData;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.ResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 公共数据库操作类
 * 
 * @author admin
 *
 */
public class PublicDBManager {
  MyLog mylog = new MyLog(getClass());
  // private int TIME_OUT = 10 * 1000;

  private Stack<DBRequestParam<BaseData>> requestParamsStack =
      new Stack<DBRequestParam<BaseData>>(); // 保存的查询请求
  private DBRequestParam<BaseData> curRequestParam; // 当前的查询请求

  private boolean isNeedUpdateFlag = false;

  public static TableChkUpdateListData updateListData; // 表更新记录

  private static PublicDBHelper helper;
  private static PublicDBManager dbManager;

  private PublicDBManager(Context context) {
    helper = new PublicDBHelper(context);
  }

  public static PublicDBManager getInstance(Context context) {
    synchronized (PublicDBManager.class) {
      if (dbManager == null) {
        dbManager = new PublicDBManager(context);
      }
      return dbManager;
    }
  }

  /**
   * 添加一个请求，当前无请求则立即执行
   * 
   * @param param
   */
  private synchronized <T> void addRequest(DBRequestParam<T> requestParam) {
    mylog.e("=========数据库查询添加请求>>>>>requestParamsStack——size>>>" + requestParamsStack.size());
    // if (!requestParamsStack.empty() && curRequestParam == null) {
    // curRequestParam = requestParamsStack.pop();
    // handler.sendEmptyMessage(DBRequestParam.TWO_GETTING_UPDATE_LIST);
    // } else {
    // requestParamsStack.add((DBRequestParam<BaseData>) requestParam);
    // }
    if (curRequestParam == null) {
      curRequestParam = (DBRequestParam<BaseData>) requestParam;
      handler.sendEmptyMessage(DBRequestParam.TWO_GETTING_UPDATE_LIST);
    } else {
      requestParamsStack.add((DBRequestParam<BaseData>) requestParam);
    }
  }

  /**
   * 执行下一个请求
   */
  private synchronized void popRequest() {
    mylog.e("=========数据库查询下一个请求>>>>>requestParamsStack——size>>>" + requestParamsStack.size());
    if (!requestParamsStack.empty() && curRequestParam == null) {
      curRequestParam = requestParamsStack.pop();
      handler.sendEmptyMessage(DBRequestParam.TWO_GETTING_UPDATE_LIST);
    }
  }

  private <T> void CallBack() {
    if (curRequestParam != null) {
      List<BaseData> list =
              queryDataList(curRequestParam.getDataClass(),
                  curRequestParam.getTableNum(), curRequestParam.getSelection(),
                  curRequestParam.getSelectionArgs(), curRequestParam.getGroupBy(),
                  curRequestParam.getHaving(), curRequestParam.getOrderBy());

      if (curRequestParam.dbCallBack != null) {
        curRequestParam.dbCallBack.onCallBackList(list);
      } else {
//        mylog.e("=========数据库查询回调>>>>>DBCallBack = null，tableName="
//            + curRequestParam.getTableName());
      }

    } else {
//      mylog.e("=========数据库查询回调>>>>>curRequestParam = null,tableName="
//          + curRequestParam.getTableName());
    }
    curRequestParam = null;
    popRequest();
  }

  private Handler handler = new Handler(Looper.getMainLooper()) {
    public void handleMessage(android.os.Message msg) {
      switch (msg.what) {
        case DBRequestParam.One_PENDING:

          break;
        case DBRequestParam.TWO_GETTING_UPDATE_LIST:
          mylog.e("====>>>>>handler>>数据库更新>>>获取时间列表>>");
          getUpdateListInfo();
          break;
        case DBRequestParam.THREE_CHECK_IS_UPDATE:
          mylog.e("====>>>>>handler>>数据库更新>>>检查是否更新>>");
          CheckNeedUpdate(curRequestParam.getTableName());
          break;
        case DBRequestParam.FOUR_GETTING_UPDATE:
          if (isNeedUpdateFlag) {
            mylog.e("====>>>>>handler>>数据库更新>>>获取更新数据>>");
            UpdateTable(curRequestParam.getClass(), curRequestParam.getTableName());
          } else {
            handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
          }
          break;
        case DBRequestParam.FIVE_FINISH:
          mylog.e("====>>>>>handler>>数据库更新>>>数据返回>>");
          CallBack();
          break;

        default:
          break;
      }
    }
  };

  /**
   * 获取服务器数据库更新时间列表
   */
  private void getUpdateListInfo() {
    // ===========第一步
    if (updateListData != null) {
      handler.sendEmptyMessage(DBRequestParam.THREE_CHECK_IS_UPDATE);
      return;
    }

    JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("ObjectType", ConfigallGps.ObjectType);
      DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETBASEDATAUPDATETIMELIST_INTF,
          jsonObject.toString(), new ResponseListener() {

            @Override
            public void messageBack(int code, String result) {
              mylog.e("====>>>>>获取服务器数据库更新时间列表>>messageBack>>>commandid= 0x"
                  + Integer.toHexString(code));
              updateListData = JSON.parseObject(result, TableChkUpdateListData.class);
              handler.sendEmptyMessage(DBRequestParam.THREE_CHECK_IS_UPDATE);
            }

            @Override
            public void messageBackFailed(int errorCode, String error) {
              mylog.e("====>>>>>获取服务器数据库更新时间列表>>messageBackFailed>>>commandid= 0x"
                  + Integer.toHexString(errorCode));
              if (curRequestParam != null) {
                curRequestParam.setHasException(true);
                handler.sendEmptyMessage(DBRequestParam.THREE_CHECK_IS_UPDATE);
              }
            }
          });

    } catch (JSONException e) {
      e.printStackTrace();
      if (curRequestParam != null) {
        curRequestParam.setHasException(true);
        handler.sendEmptyMessage(DBRequestParam.THREE_CHECK_IS_UPDATE);
      }
    }
  }

  /**
   * 检查是否需要更新数据
   * 
   * @param tableName
   */
  private void CheckNeedUpdate(String tableName) {
    // ===========第二步
    isNeedUpdateFlag = true; // 默认需要更新

    if (!TableManager.isChkUpdate(tableName)) {
      isNeedUpdateFlag = false;
      mylog.e("=====>>>>>isNeedUpdate()====>>>isNeedUpdateFlag = >>>>" + isNeedUpdateFlag);
    } else {
      int tableNum = TableManager.getNumByName(tableName);
      TableUpdateData recordData =
          queryBySelection(TableUpdateData.class, TableManager.Laio_Table_UpdateTime,
              "ConfType = ?", new String[] {tableNum + ""});
      mylog.e("=====>>>>>isNeedUpdate====>>>recordData>>>>" + recordData);
      if (recordData == null) {
        // 本地没有更新记录
        isNeedUpdateFlag = true;
        mylog.e("=====>>>>>isNeedUpdate()====>>>isNeedUpdateFlag = >>>>" + isNeedUpdateFlag);
        mylog.e("=====>>>>>isNeedUpdate()====>>>本地没有更新记录 = >>>>" + isNeedUpdateFlag);
      } else {
        // 本地有更新记录
        mylog.e("=====>>>>>isNeedUpdate====>>>本地更新记录recordData>>>>" + recordData.toString());
        if (updateListData != null && updateListData.getInfo() != null && recordData != null) {
          for (int i = 0; i < updateListData.getInfo().size(); i++) {
            TableUpdateData data = updateListData.getInfo().get(i);
            // 查找对应类型表的服务器更新时间
            if (data != null && data.getConfType() == recordData.getConfType()) {
              mylog.e("=====>>>>>isNeedUpdate====>>>服务器更新记录Data>>>>" + data.toString());
              if (recordData.getUpdateTime() != null
                  && !recordData.getUpdateTime().equalsIgnoreCase("")
                  && recordData.getUpdateTime().equalsIgnoreCase(data.getUpdateTime())) {
                isNeedUpdateFlag = false;
                mylog
                    .e("=====>>>>>isNeedUpdate()====>>>isNeedUpdateFlag = >>>>" + isNeedUpdateFlag);
              }
              break;
            }
          }
        }
      }
    }
    handler.sendEmptyMessage(DBRequestParam.FOUR_GETTING_UPDATE);
  }

  /**
   * 更新表
   * 
   * @param cls
   * @param tableName
   */
  private <T> void UpdateTable(Class<T> cls, final String tableName) {
    int tableNum = TableManager.getNumByName(tableName);
    TableUpdateData recordData =
        queryBySelection(TableUpdateData.class, TableManager.Laio_Table_UpdateTime, "ConfType = ?",
            new String[] {tableNum + ""});

    final JSONObject jsonObject = new JSONObject();
    try {
      jsonObject.put("ConfType", tableNum);
      if (recordData != null && recordData.getUpdateTime() != null) {
        jsonObject.put("UpdateTime", recordData.getUpdateTime());
      } else {
        jsonObject.put("UpdateTime", "");
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    try {
//      new AsyncTask<Integer, Integer, Boolean>() {
//        @Override
//        protected Boolean doInBackground(Integer... params) {
//          if (Looper.myLooper() == null) {
//            Looper.prepare();
//          }
          DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETBASEDATALIST_INTF,
              jsonObject.toString(), new ResponseListener() {

                @Override
                public void messageBack(int code, String result) {
                  mylog.e(">>>>>>>>start>>>>>>time");
                  SQLiteDatabase sqLiteDatabase = helper.getWritableDatabase();
                  sqLiteDatabase.beginTransaction();
                  try {
                    // mylog.i("result=" + result);
                    JSONObject js = new JSONObject(result);
                    org.json.JSONArray jaArray = js.getJSONArray("info");
                    JSONObject jssJsonObject = null;
                    ContentValues values = new ContentValues();
                    int lenght = jaArray.length();
                    int nameLenght = 0;
                    for (int i = 0; i < lenght; i++) {
                      jssJsonObject = jaArray.getJSONObject(i);
                      nameLenght = jssJsonObject.names().length();
                      for (int j = 0; j < nameLenght; j++) {
                        String name = jssJsonObject.names().getString(j);
                        // String vString = jssJsonObject.getString(name);
                        values.put(name, jssJsonObject.getString(name));
                      }
                      replaceInto(sqLiteDatabase, tableName, "Id", values);
                    }
                    sqLiteDatabase.setTransactionSuccessful();

                    mylog.e(">>>>>>>>end>>>>>>time");

                    // TableUpdateListData<T> listData =
                    // JSON.parseObject(result,
                    // new
                    // TypeReference<TableUpdateListData<T>>() {
                    // });
                    // List<T> list = listData.getInfo();
                    // // mylog.i("更新状态list。。。" + list);
                    // if (list != null && list.size() > 0) {
                    // for (int i = 0; i < list.size(); i++) {
                    // replaceInto(tableName, "Id",
                    // DBConvertUtil.data2ContentValues(list.get(i)));
                    // mylog.e("time");
                    // }
                    // }
                    // 修改更新记录
                    if (!curRequestParam.isHasException()) {
                      // 查询过程中没有错误则更新本地更新记录
                      ContentValues updatevalues = new ContentValues();
                      updatevalues.put("Id", 0);
                      updatevalues.put("ConfType", js.getString("ConfType"));
                      updatevalues.put("UpdateTime", js.getString("UpdateTime"));
                      replaceInto(TableManager.Laio_Table_UpdateTime, "Id", updatevalues);
                    }

                  } catch (Exception e) {
                    // sqLiteDatabase.endTransaction();
                  } finally {
                    sqLiteDatabase.endTransaction();
                    if (sqLiteDatabase != null && sqLiteDatabase.isOpen()) {
                      sqLiteDatabase.close();
                    }
//                    Looper.myLooper().quit();
                    handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
                  }
                }

                @Override
                public void messageBackFailed(int errorCode, String error) {
                  super.messageBackFailed(errorCode, error);
                  curRequestParam.setHasException(true);
//                  Looper.myLooper().quit();
                  handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
                }
              });
//          Looper.loop();
//          return null;
//        }
//      }.execute(0);
    } catch (RuntimeException e) {
      e.printStackTrace();
      curRequestParam.setHasException(true);
      // Looper.myLooper().quit();
      handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
    }

    // DataLoadTask.getInstance().loadData(CommandCode.CMD_BAOBAN_CONFIG_BASE_INFO,
    // jsonObject.toString(),
    // new ResponseListener() {
    //
    // @Override
    // public void messageBack(int code, String result) {
    // mylog.i("result=" + result);
    // TableUpdateListData<T> listData = JSON.parseObject(result,
    // new TypeReference<TableUpdateListData<T>>() {
    // });
    // List<T> list = listData.getInfo();
    // // mylog.i("更新状态list。。。" + list);
    // if (list != null && list.size() > 0) {
    // for (int i = 0; i < list.size(); i++) {
    // replaceInto(tableName, "Id",
    // DBConvertUtil.data2ContentValues(list.get(i)));
    // }
    // }
    // // 修改更新记录
    // if (!curRequestParam.isHasException()) {
    // // 查询过程中没有错误则更新本地更新记录
    // ContentValues values = new ContentValues();
    // values.put("Id", listData.getConfType());
    // values.put("ConfType", listData.getConfType());
    // values.put("UpdateTime", listData.getUpdateTime());
    // replaceInto(TableManager.Laio_Table_UpdateTime, "Id", values);
    // }
    // handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
    // }
    //
    // @Override
    // public void messageBackFailed(int errorCode, String error) {
    // super.messageBackFailed(errorCode, error);
    // curRequestParam.setHasException(true);
    // handler.sendEmptyMessage(DBRequestParam.FIVE_FINISH);
    // }
    // });

  }

  /**
   * 根据城市id获取驾校列表(驾校查询单独处理)
   * 
   * @param cityData
   * @return
   */
  public void getSchoolsByCity(CityData cityData, DBCallBack<SchoolData> callBack) {
    if (cityData == null) {
      return;
    }
    String selection = null;
    if (cityData.getProid() == 0) {
      // 在查询直辖市的驾校时，直辖市不能直接通过Laio_school表中的DistrictDM相等来判断，要用直辖市地区代码的前三位来进行匹配
      selection = "DistrictDM like \"" + cityData.getDM().substring(0, 3) + "%\"";
    } else {
      selection = "DistrictDM = " + cityData.getDM();
    }
    queryList(SchoolData.class, TableManager.Laio_School, selection, null, null, null, "Num asc",
        callBack);
  }

  /**
   * 根据城市id获取区域列表(区域查询单独处理)
   * 
   * @param cityData
   * @return
   */
  public void getDistrictByCity(CityData cityData, DBCallBack<DistrictData> callBack) {
    if (cityData == null) {
      return;
    }
    String selection = null;
    if (cityData.getProid() == 0) {
      // 在查询直辖市的区域时，用直辖市地区代码的前三位来进行匹配，否则前4位，且要将城市本身排除
      selection =
          "DM like \"" + cityData.getDM().substring(0, 3) + "%\"" + " and DM != "
              + cityData.getDM();
    } else {
      selection =
          "DM like \"" + cityData.getDM().substring(0, 4) + "%\"" + " and DM != "
              + cityData.getDM();
    }
    queryList(DistrictData.class, TableManager.Laio_District, selection, null, null, null, null,
        callBack);
  }

  /**
   * 根据城市id获取区域列表(区域查询单独处理)
   *
   * @param cityData
   * @return
   */

  public <T> void queryById(Class<T> cls, int tableNum, int Id, String orderBy,
      DBCallBackImp<T> callBack) {
    T data = null;
    String tablename = TableManager.getNameByNum(tableNum);
    Cursor cursor = query(tablename, Id, orderBy);
    if (cursor.moveToFirst()) {
      data = DBConvertUtil.cursor2Data(cls, cursor);
      callBack.onCallBackData(data);
    }
  }

  public <T> void queryList(Class<T> cls, int tableNum, String selection, String[] selectionArgs,
      String groupBy, String having, String orderBy, DBCallBackImp<T> callBack) {
    addRequest(new DBRequestParam<T>(tableNum, cls, selection, selectionArgs, groupBy, having,
        orderBy, callBack));
  }

  public <T> void queryList(Class<T> cls, String tableName, String selection,
      String[] selectionArgs, String groupBy, String having, String orderBy,
      DBCallBackImp<T> callBack) {
    addRequest(new DBRequestParam<T>(tableName, cls, selection, selectionArgs, groupBy, having,
        orderBy, callBack));
  }

  public <T> void queryList(Class<T> cls, String tableName, String groupBy, String having,
      String orderBy, DBCallBackImp<T> callBack) {
    queryList(cls, tableName, null, null, groupBy, having, orderBy, callBack);
  }

  public <T> void queryList(Class<T> cls, String tableName, String selection,
      String[] selectionArgs, DBCallBackImp<T> callBack) {
    queryList(cls, tableName, selection, selectionArgs, null, null, null, callBack);
  }

  public <T> void queryList(Class<T> cls, int tableNum, String selection, String[] selectionArgs,
      DBCallBackImp<T> callBack) {
    queryList(cls, TableManager.getNameByNum(tableNum), selection, selectionArgs, null, null, null,
        callBack);
  }

  public <T> void queryList(Class<T> cls, String tableName, DBCallBackImp<T> callBack) {
    queryList(cls, tableName, null, null, null, null, null, callBack);
  }

  public <T> void queryList(Class<T> cls, int tableNum, DBCallBackImp<T> callBack) {
    queryList(cls, TableManager.getNameByNum(tableNum), null, null, null, null, null, callBack);
  }

  public void execSQL(String sql, Object[] bindArgs) {
    SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    sqliteDB.execSQL(sql, bindArgs);
  }

  public Cursor rawQuery(String sql, String[] selectionArgs) {
    SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    return sqliteDB.rawQuery(sql, selectionArgs);
  }

  private Cursor query(String table, String selection, String[] selectionArgs, String groupBy,
      String having, String orderBy) {
    SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    Cursor cursor = sqliteDB.query(table, null, selection, selectionArgs, groupBy, having, orderBy);
    return cursor;
  }

  private Cursor query(String tableName, int Id, String orderBy) {
    Cursor cursor = query(tableName, "Id = ?", new String[] {Id + ""}, null, null, orderBy);
    return cursor;
  }

  /**
   * 根据Id查询表
   * 
   * @param tableNum
   * @param Id
   * @param orderBy
   * @return
   */

  public <T> T queryById(Class<T> cls, int tableNum, int Id, String orderBy) {
    T data = null;
    String tablename = TableManager.getNameByNum(tableNum);
    Cursor cursor = query(tablename, Id, orderBy);
    if (cursor.moveToFirst()) {
      data = DBConvertUtil.cursor2Data(cls, cursor);
    }
    return data;
  }

  /**
   * 根据条件查询单条记录
   * 
   * @param cls
   * @param tableName
   * @param selection
   * @param selectionArgs
   * @param orderBy
   * @return
   */
  public <T> T queryBySelection(Class<T> cls, String tableName, String selection,
      String[] selectionArgs) {
    T data = null;
    Cursor cursor = query(tableName, selection, selectionArgs, null, null, null);
    if (cursor.moveToFirst()) {
      data = DBConvertUtil.cursor2Data(cls, cursor);
    }
    return data;
  }

  /**
   * 根据表编号查询表
   * 
   * @param cls
   * @param tableNum
   * @param selection
   * @param selectionArgs
   * @param groupBy
   * @param having
   * @param orderBy
   * @return
   */
  private <T> List<T> queryDataList(Class<T> cls, int tableNum, String selection,
      String[] selectionArgs, String groupBy, String having, String orderBy) {
    List<T> list = new ArrayList<T>();
    String tableName = TableManager.getNameByNum(tableNum);
    list = queryDataList(cls, tableName, selection, selectionArgs, groupBy, having, orderBy);
    return list;
  }

  /**
   * 根据表名查询表
   * 
   * @param cls
   * @param tableName
   * @param selection
   * @param selectionArgs
   * @param groupBy
   * @param having
   * @param orderBy
   * @return
   */
  private <T> List<T> queryDataList(Class<T> cls, String tableName, String selection,
      String[] selectionArgs, String groupBy, String having, String orderBy) {
    List<T> list = new ArrayList<T>();
    try {
      Cursor cursor = query(tableName, selection, selectionArgs, groupBy, having, orderBy);
      if (cursor != null) {
        while (cursor.moveToNext()) {
          T t = DBConvertUtil.cursor2Data(cls, cursor);
          if (t != null) {
            list.add(t);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      return list;
    }
    // mylog.i("查询的list="+list);
    return list;
  }

  /**
   * 替换或插入数据到指定表
   * 
   * @param tableName
   * @param nullColumnHack
   * @param initialValues
   * @return
   */
  private long replaceInto(SQLiteDatabase sqliteDB, String tableName, String nullColumnHack,
      ContentValues initialValues) {
    // SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    if (sqliteDB == null) {
      return 0;
    }
    return sqliteDB.replace(tableName, nullColumnHack, initialValues);
  }

  private long replaceInto(String tableName, String nullColumnHack, ContentValues initialValues) {
    SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    return sqliteDB.replace(tableName, nullColumnHack, initialValues);
  }

  /**
   * 替换或插入数据到指定编号表
   * 
   * @param tableNum
   * @param nullColumnHack
   * @param initialValues
   */
  public long replaceInto(SQLiteDatabase sqliteDB, int tableNum, String nullColumnHack,
      ContentValues initialValues) {
    if (sqliteDB == null) {
      return 0;
    }
    String tableName = TableManager.getNameByNum(tableNum);
    return replaceInto(sqliteDB, tableName, nullColumnHack, initialValues);
  }

  public int delete(int tableNum, String whereClause, String[] whereArgs) {
    String table = TableManager.getNameByNum(tableNum);
    return delete(table, whereClause, whereArgs);
  }

  public int delete(String tableName, String whereClause, String[] whereArgs) {
    SQLiteDatabase sqliteDB = helper.getWritableDatabase();
    return sqliteDB.delete(tableName, whereClause, whereArgs);
  }

}
