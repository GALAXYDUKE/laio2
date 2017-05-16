package com.bofsoft.laio.laiovehiclegps.Tools;

import android.app.Activity;
import android.content.DialogInterface.OnClickListener;

import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.laio.tcp.IResponseListener;

public class Utils {

  public static void showDialog(Activity act, String content) {
    String title = act.getString(R.string.tip);
    DialogUtils.showCancleDialog(act, title, content);
  }

  public static void showDialog(Activity act, String content, String btn,
      OnClickListener neglistener) {
    String title = act.getString(R.string.tip);
    DialogUtils.showCancleDialog(act, title, content, btn, neglistener);
  }

  public static void showDialog(Activity act, String content,
      OnClickListener neglistener) {
    String title = act.getString(R.string.tip);
    DialogUtils.showCancleDialog(act, title, content, "知道了", neglistener);
  }

  public static void showDialog(Activity act, String content, String negString, String posString, OnClickListener negListener, OnClickListener posListener) {
    DialogUtils.showDialog(act, act.getString(R.string.tip), content, negString, negListener, posString, posListener);
  }
  
  public static void closeDialog() {
    DialogUtils.closeDialog();
  }

  public static void showWaitDialog(Activity act, String content, boolean cancelable) {
    DialogUtils.showWaitingDialog(act, content, cancelable);
  }

  public static void CloseWaitDialog() {
    DialogUtils.closeWaitDialog();
  }

  public static void messageBack(Activity act, int transId, String result) {
    if (act instanceof IResponseListener) {
      messageBack((IResponseListener) act, transId, result);
    }
  }

  public static void messageBack(IResponseListener req, int transId, String result) {
    req.messageBack(transId, result);
  }

  public static void rFailed(Activity act, int transId, String msg) {
    if (act instanceof IResponseListener) {
      rFailed((IResponseListener) act, transId, msg);
    }
  }

  public static void rFailed(IResponseListener req, int transId, String msg) {
    req.messageBackFailed(transId, msg);
  }

}
