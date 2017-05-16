package com.bofsoft.laio.laiovehiclegps.Database;

import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 公共数据库表映射及操作</br> (1, Laio_AdmitCarType)</br> (2, Laio_CarModel)</br> (3, Laio_CarType)</br> (4,
 * Laio_City)</br> (5, Laio_City_Gps)</br> (6, Laio_Class_Date)</br> (7, Laio_Class_Time)</br> (8,
 * Laio_Class_Train)</br> (9, Laio_Class_Type)</br> (10, Laio_District)</br> (11,
 * Laio_Enroll_Reson)</br> (12, Laio_Holiday)</br> (13, Laio_School)</br> (14, Laio_Site)</br> (15,
 * Laio_TestSub)</br> (16, Laio_TestSub_List)</br> (17, Laio_Train_Site);
 * 
 * @author admin
 *
 */
public class TableManager {

  // 定义laio.db3数据库中的表名称
  public static final String Laio_AdmitCarType = "laio_admitcartype"; // 1 教练准教车型
  public static final String Laio_CarModel = "laio_carmodel"; // 2 教练车型
  public static final String Laio_CarType = "laio_cartype"; // 3 准驾车型
  public static final String Laio_City = "laio_city"; // 4 全国地级市列表
  public static final String Laio_City_Gps = "laio_city_gps"; // 5 地城市对应经纬度
  public static final String Laio_Class_Date = "laio_class_date"; // 6 日期班次
  public static final String Laio_Class_Time = "laio_class_time"; // 7 培训时段
  public static final String Laio_Class_Train = "laio_class_train"; // 8 培训班次（可自定义）
  public static final String Laio_Class_Type = "laio_class_type"; // 9 培训类型
  public static final String Laio_District = "laio_district"; // 10 行政区划
  public static final String Laio_Enroll_Reason = "laio_enroll_reason"; // 11 报考原因
  public static final String Laio_Holiday = "laio_holiday"; // 12 节假日
  public static final String Laio_School = "laio_school"; // 13 全国驾校列表
  public static final String Laio_Site = "laio_site"; // 14 考场信息列表
  public static final String Laio_TestSub = "laio_testsub"; // 15 培训科目
  public static final String Laio_IdType = "laio_idtype"; // 16 身份证明有效证件类型
  public static final String Laio_Train_Site = "laio_train_site"; // 17 训练场地信息
  public static final String Laio_Class_Reg = "laio_class_reg"; // 18 招生班次基础信息
  public static final String Laio_StuStatus = "laio_stustatus"; // 19 学员状态基础表
  public static final String Laio_TestSubInfo = "laio_testsub_info"; // 20科目对应培训项目
  public static final String Laio_Module = "laio_module";//21 判断哪些模块是否显示
//  public static final String Laio_TestSub_List = "laio_testsub_list"; // 16 培训科目对应培训项目

  public static final String Laio_Table_UpdateTime = "laio_table_updatetime"; // 公共数据表更新记录

  // 定义laio.db3数据库中的表名称
  public static final int Type_AdmitCarType = 1; // 1 教练准教车型
  public static final int Type_CarModel = 2; // 2 教练车型
  public static final int Type_CarType = 3; // 3 准驾车型
  public static final int Type_City = 4; // 4 全国地级市列表
  public static final int Type_City_Gps = 5; // 5 地城市对应经纬度
  public static final int Type_Class_Date = 6; // 6 日期班次
  public static final int Type_Class_Time = 7; // 7 培训时段
  public static final int Type_Class_Train = 8; // 8 培训班次（可自定义）
  public static final int Type_Class_Type = 9; // 9 培训类型
  public static final int Type_District = 10; // 10 行政区划
  public static final int Type_Enroll_Reson = 11; // 11 报考原因
  public static final int Type_Holiday = 12; // 12 节假日
  public static final int Type_School = 13; // 13 全国驾校列表
  public static final int Type_Site = 14; // 14 考场信息列表
  public static final int Type_TestSub = 15; // 15 培训科目
  public static final int Type_IdType = 16; // 16 招生班次基础信息
  public static final int Type_Train_Site = 17; // 17 训练场地信息
  public static final int Type_Class_Reg = 18; // 18 招生班次基础信息
  public static final int Type_StuStatus = 19; // 19 学员状态基础表
  public static final int Type_TestSubInfo = 20; // 20科目对应培训项目
  public static final int Type_Module = 21;// 21根据版本判断哪些模块是否显示
//  public static final int Type_TestSub_List = 16; // 16 培训科目对应培训项目

  private static SparseArray<String> table = new SparseArray<String>();
  private static Map<String, Boolean> NeedUpdateTable = new HashMap<String, Boolean>();
  static {
    table.put(1, Laio_AdmitCarType);
    table.put(2, Laio_CarModel);
    table.put(3, Laio_CarType);
    table.put(4, Laio_City);
    table.put(5, Laio_City_Gps);
    table.put(6, Laio_Class_Date);
    table.put(7, Laio_Class_Time);
    table.put(8, Laio_Class_Train);
    table.put(9, Laio_Class_Type);
    table.put(10, Laio_District);
    table.put(11, Laio_Enroll_Reason);
    table.put(12, Laio_Holiday);
    table.put(13, Laio_School);
    table.put(14, Laio_Site);
    table.put(15, Laio_TestSub);
//    table.put(16, Laio_TestSub_List);
    table.put(16, Laio_IdType);
    table.put(17, Laio_Train_Site);
    table.put(18, Laio_Class_Reg);
    table.put(19, Laio_StuStatus);
    table.put(20, Laio_TestSubInfo);
    table.put(21, Laio_Module);

    // 需要更新的表
    NeedUpdateTable.put(Laio_CarType, true);
    NeedUpdateTable.put(Laio_City, true);
    NeedUpdateTable.put(Laio_City_Gps, true);
    NeedUpdateTable.put(Laio_District, true);
    NeedUpdateTable.put(Laio_Holiday, true);
    NeedUpdateTable.put(Laio_School, true);
    NeedUpdateTable.put(Laio_Train_Site, true);
    NeedUpdateTable.put(Laio_Class_Reg, true);
    NeedUpdateTable.put(Laio_StuStatus, true);
    NeedUpdateTable.put(Laio_TestSubInfo, true);
    NeedUpdateTable.put(Laio_Module, true);

  }

  /**
   * 根据表名获取表编号
   * 
   * @param tableName
   * @return
   */
  public static Integer getNumByName(String tableName) {
    return table.keyAt(table.indexOfValue(tableName));
  }

  /**
   * 根据表编号获取表名
   * 
   * @param tableNum
   * @return
   */
  public static String getNameByNum(int tableNum) {
    return table.get(tableNum);
  }

  /**
   * @param tableName
   * @param update 服务器的更新时间
   * @return
   */
  public static boolean isChkUpdate(String tableName) {
    boolean flag = false;
    Boolean isChk = NeedUpdateTable.get(tableName);
    if (isChk != null && isChk == true) {
      flag = true;
    }
    return flag;

  }

}
