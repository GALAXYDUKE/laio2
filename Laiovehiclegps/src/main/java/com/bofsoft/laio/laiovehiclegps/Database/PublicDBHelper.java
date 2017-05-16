package com.bofsoft.laio.laiovehiclegps.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.laiovehiclegps.Config.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 公共数据库操作类
 * 
 * @author yedong
 *
 */
public class PublicDBHelper extends SQLiteOpenHelper {
  MyLog mylog = new MyLog(getClass());
  public static final String DATABASE_NAME = "laio.db3";

  private static final int DB_VERSION =3;
  private static String DB_PATH = "";

  private Context context;
  
  public static final String NEW_TB_NAME = "laio_module";  
  public static final String ID = "_id";  
  public static final String CODENUM = "CodeNum";  
  public static final String MODULCODE = "ModuleCode";  
  public static final String ISSHOW = "IsShow";  
  public static final String ISDEL = "IsDel";

  public PublicDBHelper(Context context) {
    this(context, DB_VERSION);
  }

  public PublicDBHelper(Context context, int version) {
    this(context, DATABASE_NAME, version);
  }

  public PublicDBHelper(Context context, String name, int version) {
    super(context, name, null, version);
    this.context = context;
    DB_PATH = context.getDatabasePath(DATABASE_NAME).getAbsolutePath();
    createDataBase();
  }

  /**
   * create the database and copy data
   */
  public void createDataBase() {
    boolean dbExist = checkDBExist();
    if (dbExist) {
      // 数据库已存在，do nothing.
      mylog.i("PublicDBHelper>>>数据库已存在");
    } else {
      mylog.i("PublicDBHelper>>>拷贝数据库");
      // 创建数据库
      File file = new File(DB_PATH);
      if (!file.exists()) {
        file.getParentFile().mkdirs();
      }

      // create a empty database
      SQLiteDatabase sqtliteDb = SQLiteDatabase.openOrCreateDatabase(file, null);
      if (sqtliteDb != null) {
        sqtliteDb.close();
      }
      // 复制asseets中的db文件到DB_PATH下
      copyDataBase();
    }
  }

  // 检查数据库是否有效
  private boolean checkDBExist() {
    mylog.i("PublicDBHelper>>>检查数据库checkDBExist");
//    if (Config.CITY_DATA==null){//如果为空，证明没有进过首页 数据库没有创建
//      return false;
//    }
    File file = new File(DB_PATH);
    if (!file.exists()) {
      return false;
    }
    SQLiteDatabase checkDB = null;
    try {
      checkDB = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    } catch (SQLiteException e) {
      // database does't exist yet.
      e.printStackTrace();
      return false;
    }
    if (checkDB != null) {
      checkDB.close();
    }
    return checkDB != null;
  }

  /**
   * Copies your database from your local assets-folder to the just created empty database in the
   * system folder, from where it can be accessed and handled. This is done by transfering
   * bytestream.
   * */
  private void copyDataBase() {
    mylog.i("PublicDBHelper>>>拷贝数据库copyDataBase");
    try {
      InputStream myInput = context.getAssets().open(DATABASE_NAME);
      FileOutputStream myOutput = new FileOutputStream(DB_PATH);

      byte[] buffer = new byte[2048];
      int length;
      while ((length = myInput.read(buffer)) > 0) {
        myOutput.write(buffer, 0, length);
      }
      // Close the streams
      myOutput.flush();
      myOutput.close();
      myInput.close();
    } catch (Exception e) {
      e.printStackTrace();
      File file = new File(DB_PATH);
      if (file.exists()) {
        file.delete();
      }
    }
  }

  /**
   * 该函数是在第一次创建的时候执行， 实际上是第一次得到SQLiteDatabase对象的时候才会调用这个方法
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    mylog.i("PublicDBHelper>>>创建数据库onCreate");
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    mylog.i("PublicDBHelper>>>升级数据库onUpgrade-----oldVersion|" + oldVersion + "-----newVersion|"
        + newVersion);
    switch (newVersion) {
      case 2: // 添加表laio_stustatus，laio_testsub_info
    	  context.getDatabasePath(DB_PATH).delete();
    	  createDataBase();
    	  break;
      case 3: // 添加表laio_module
    	  db.execSQL("CREATE TABLE IF NOT EXISTS " + NEW_TB_NAME + " (" + ID
                  + " INTEGER PRIMARY KEY," + CODENUM + " SMALLINT(6) NOT NULL,"+
    			  MODULCODE +" CHAR(6) NOT NULL,"+ ISSHOW +"  TINYINT(3) NOT NULL DEFAULT ('1'), "
                  + ISDEL +"  TINYINT(3) NOT NULL DEFAULT ('0')"+")");
        break;
      default:
        break;
    }
  }

}
