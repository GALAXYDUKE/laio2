package com.bofsoft.laio.laiovehiclegps.Database;

import java.util.List;

/**
 * 数据库查询结果回调
 * 
 * @author yedong
 *
 * @param <T>
 */
public class DBCallBack<T> implements DBCallBackImp<T> {

  /**
   * 返回查询的
   * 
   * @param data
   */
  @Override
  public void onCallBackData(T data) {

  }

  /**
   * 返回查询的List<T>
   * 
   * @param data
   */
  @Override
  public void onCallBackList(List<T> list) {

  }


}
