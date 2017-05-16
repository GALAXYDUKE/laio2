package com.bofsoft.laio.laiovehiclegps.Tools;

import android.app.Activity;

import com.bofsoft.laio.laiovehiclegps.Config.ExceptionType;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.laio.tcp.IResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {

  public static int ORDER_NEW_CNT = 0;

  // 请求码
  private int code;
  // 提示消息
  private String content;

  private int cnt;

  public static void getNewCnt(Activity act) {
    if (!ConfigAll.isLogin) {
      ORDER_NEW_CNT = 0;
      return;
    }
    try {
      DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_FUTUREORDERCOUNT, null,
          (IResponseListener) act);
    } catch (Exception e) {
      Utils.rFailed(act, CommandCodeTS.CMD_FUTUREORDERCOUNT,
          ExceptionType.getName(ExceptionType.SYSTEM_VALUE));
    }
  }


  public static Order prase(String json) {
    Order m = null;
    try {
      m = prase(new JSONObject(json));
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return m;
  }

  public static Order prase(JSONObject jo) {
    Order m = new Order();
    try {
      if (jo.has("OrderCount"))
        m.setCnt(jo.getInt("OrderCount"));
    } catch (Exception e) {
      e.printStackTrace();
    }
    return m;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getCnt() {
    return cnt;
  }

  public void setCnt(int cnt) {
    this.cnt = cnt;
  }
}
