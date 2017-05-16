package com.bofsoft.laio.laiovehiclegps.Tools;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bofsoft.laio.laiovehiclegps.R;


public class DialogUtils {
  static boolean isShowIcon = false;
  static boolean isCancelable = true;
  static Dialog dialog;
  static Dialog waitDialog;
  static Dialog customDialog;

  public static void showCancleDialog(Context context, CharSequence title, CharSequence content) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      if (isShowIcon) {
        builder.setIcon(R.mipmap.ic_launcher);
      }
      // builder.setCancelable(isCancelable);
      builder.setCancelable(false);
      builder.setTitle(title).setMessage(Html.fromHtml(content.toString()))
          .setPositiveButton("知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              dialog.dismiss();
            }
          });

      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showCancleDialog(Context context, CharSequence title, CharSequence content,
      CharSequence posButton, DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      if (isShowIcon) {
        builder.setIcon(R.drawable.ic_launcher);
      }
      // builder.setCancelable(isCancelable);
      builder.setCancelable(false);
      builder.setTitle(title).setMessage(Html.fromHtml(content.toString()))
          .setPositiveButton(posButton, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showCancleDialog(Context context, int titleId, int contentId, int posButtonId,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      if (isShowIcon) {
        builder.setIcon(R.drawable.ic_launcher);
      }
      // builder.setCancelable(isCancelable);
      builder.setCancelable(false);
      builder.setTitle(titleId).setMessage(contentId).setPositiveButton(posButtonId, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showCancleDialog(Context context, Drawable icon, CharSequence title,
      CharSequence content, CharSequence posButton, boolean cancelable,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(icon).setTitle(title).setMessage(Html.fromHtml(content.toString()))
          .setPositiveButton(posButton, listener).setCancelable(cancelable);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showCancleDialog(Context context, int iconId, int titleId, int contentId,
      int posButtonId, boolean cancelable, DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(iconId).setTitle(titleId).setMessage(contentId)
          .setPositiveButton(posButtonId, listener).setCancelable(cancelable);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showDialog(Context context, CharSequence title, CharSequence content,
      CharSequence negButton, DialogInterface.OnClickListener neglistener, CharSequence posButton,
      DialogInterface.OnClickListener poslistener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      if (isShowIcon) {
        builder.setIcon(R.drawable.ic_launcher);
      }
      // builder.setCancelable(isCancelable);
      builder.setCancelable(false);
      builder.setTitle(title).setMessage(Html.fromHtml(content.toString()))
          .setPositiveButton(posButton, poslistener).setNegativeButton(negButton, neglistener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void showDialog(Context context, int titleId, int contentId, int negButtonId,
      DialogInterface.OnClickListener neglistener, int posButtonId,
      DialogInterface.OnClickListener poslistener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      if (isShowIcon) {
        builder.setIcon(R.drawable.ic_launcher);
      }
      // builder.setCancelable(isCancelable);
      builder.setCancelable(false);
      builder.setTitle(titleId).setMessage(contentId).setPositiveButton(posButtonId, poslistener)
          .setNegativeButton(negButtonId, neglistener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showDialog(Context context, CharSequence title, CharSequence content,
      boolean cancelable, CharSequence negButton, DialogInterface.OnClickListener neglistener,
      CharSequence posButton, DialogInterface.OnClickListener poslistener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(title).setMessage(Html.fromHtml(content.toString()))
          .setPositiveButton(posButton, poslistener).setNegativeButton(negButton, neglistener)
          .setCancelable(cancelable);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showDialog(Context context, int iconId, int titleId, int contentId,
      boolean cancelable, int negButtonId, DialogInterface.OnClickListener neglistener,
      int posButtonId, DialogInterface.OnClickListener poslistener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(iconId).setTitle(titleId).setMessage(contentId)
          .setPositiveButton(posButtonId, poslistener).setNegativeButton(negButtonId, neglistener)
          .setCancelable(cancelable);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, int iconId, int titleId, int itemsId,
      boolean cancelable, DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(iconId).setTitle(titleId).setCancelable(cancelable)
          .setItems(itemsId, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, int titleId, int items, boolean cancelable,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(titleId).setCancelable(cancelable).setItems(items, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, int iconId, int titleId, int items,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(iconId).setTitle(titleId).setItems(items, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, CharSequence title, CharSequence[] items,
      boolean cancelable, DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(title).setItems(items, listener).setCancelable(cancelable);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, CharSequence title, int itemsId,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(title).setItems(itemsId, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, CharSequence title, CharSequence[] items,
      DialogInterface.OnClickListener listener, DialogInterface.OnCancelListener cancelListener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(title).setItems(items, listener);
      dialog = builder.create();
      if (cancelListener != null) {
        dialog.setOnCancelListener(cancelListener);
      }
      dialog.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, int iconId, CharSequence title,
      CharSequence[] items, DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setIcon(iconId).setTitle(title).setItems(items, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showItemDialog(Context context, CharSequence title, CharSequence[] items,
      DialogInterface.OnClickListener listener) {

    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (dialog != null && dialog.isShowing()) {
        dialog.dismiss();
        dialog = null;
      }
      builder.setTitle(title).setItems(items, listener);
      dialog = builder.create();
      dialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showWaitingDialog(Context context, CharSequence content, boolean cancelable) {
    try {
      if (waitDialog != null) {
        if (waitDialog.isShowing()) {
          waitDialog.dismiss();
        }
      }
      waitDialog = new Dialog(context, R.style.WaitDialog);
      waitDialog.setContentView(R.layout.dialog_wait);
      waitDialog.setCancelable(cancelable);
      waitDialog.show();

      TextView waitDialogDismiss = (TextView) waitDialog.findViewById(R.id.waitDialogDismiss);
      waitDialogDismiss.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          waitDialog.dismiss();
        }
      });

      TextView txtContent = (TextView) waitDialog.findViewById(R.id.waitTv);
      txtContent.setText(Html.fromHtml(content.toString()));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showWaitingDialog(Context context, CharSequence content) {
    // if (waitDialog == null) {
    // waitDialog = new Dialog(MyStudentApplication.context, R.style.WaitDialog);
    // waitDialog.setContentView(R.layout.dialog_wait);
    // }
    //
    // if( !waitDialog.isShowing() ){
    // waitDialog.setCancelable(isCancelable);
    // waitDialog.show();
    // }else {
    // waitDialog.setCancelable(isCancelable);
    // }
    //
    // TextView waitDialogDismiss = (TextView) waitDialog.findViewById(R.id.waitDialogDismiss);
    // waitDialogDismiss.setOnClickListener(new View.OnClickListener() {
    // @Override
    // public void onClick(View v) {
    // waitDialog.dismiss();
    // }
    // });
    //
    // TextView txtContent = (TextView) waitDialog.findViewById(R.id.waitTv);
    // txtContent.setText(content);

    try {
      if (waitDialog != null) {
        if (waitDialog.isShowing()) {
          waitDialog.dismiss();
        }
      }
      waitDialog = new Dialog(context, R.style.WaitDialog);
      waitDialog.setContentView(R.layout.dialog_wait);
      waitDialog.setCancelable(isCancelable);
      waitDialog.show();

      TextView waitDialogDismiss = (TextView) waitDialog.findViewById(R.id.waitDialogDismiss);
      waitDialogDismiss.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          waitDialog.dismiss();
        }
      });

      TextView txtContent = (TextView) waitDialog.findViewById(R.id.waitTv);
      txtContent.setText(Html.fromHtml(content.toString()));
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  public static void showWaitingDialog(Context context, int contentId) {
    try {
      if (waitDialog != null) {
        if (waitDialog.isShowing()) {
          waitDialog.dismiss();
        }
      }
      waitDialog = new Dialog(context, R.style.WaitDialog);
      waitDialog.setContentView(R.layout.dialog_wait);
      waitDialog.setCancelable(isCancelable);
      waitDialog.show();

      TextView waitDialogDismiss = (TextView) waitDialog.findViewById(R.id.waitDialogDismiss);
      waitDialogDismiss.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          waitDialog.dismiss();
        }
      });

      TextView txtContent = (TextView) waitDialog.findViewById(R.id.waitTv);
      txtContent.setText(contentId);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * 显示自定义dialog
   * 
   * @param context
   * @param title
   * @param view
   * @param negButton
   * @param neglistener
   * @param posButton
   * @param poslistener
   */
  public static void showCustomViewDialog(Context context, CharSequence title, View view,
      CharSequence negButton, DialogInterface.OnClickListener neglistener, CharSequence posButton,
      DialogInterface.OnClickListener poslistener) {
    showCustomViewDialog(context, title, view, isCancelable, negButton, neglistener, posButton,
        poslistener);
  }

  public static void showCustomViewDialog(Context context, CharSequence title, View view,
      boolean isCancelable, CharSequence negButton, DialogInterface.OnClickListener neglistener,
      CharSequence posButton, DialogInterface.OnClickListener poslistener) {
    try {
      AlertDialog.Builder builder = new AlertDialog.Builder(context);
      if (customDialog != null && customDialog.isShowing()) {
        customDialog.dismiss();
        customDialog = null;
      }
      builder.setTitle(title).setView(view).setPositiveButton(posButton, poslistener)
          .setNegativeButton(negButton, neglistener).setCancelable(isCancelable);
      customDialog = builder.create();
      customDialog.show();
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showPopWindows(PopupWindow popupWindow, View contentView, View parent) {

    try {
      popupWindow.setContentView(contentView);
      popupWindow.setWidth(LayoutParams.MATCH_PARENT);
      popupWindow.setHeight(LayoutParams.MATCH_PARENT);
      popupWindow.setFocusable(true);
      popupWindow.setOutsideTouchable(true);

      ColorDrawable dw = new ColorDrawable(0xb0000000);
      popupWindow.setBackgroundDrawable(dw);
      // this.setBackgroundDrawable(new BitmapDrawable());
      popupWindow.showAtLocation(parent, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void showDropPopWindows(PopupWindow popupWindow, View contentView, View Location) {
    try {
      popupWindow.setContentView(contentView);
      popupWindow.setWidth(LayoutParams.WRAP_CONTENT);
      popupWindow.setHeight(LayoutParams.WRAP_CONTENT);
      popupWindow.setFocusable(true);
      popupWindow.setOutsideTouchable(true);

      ColorDrawable dw = new ColorDrawable(0xb0000000);
      popupWindow.setBackgroundDrawable(dw);
      // this.setBackgroundDrawable(new BitmapDrawable());
      popupWindow.showAsDropDown(Location);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public static void closeDialog() {
    if (dialog != null && dialog.isShowing()) {
      dialog.dismiss();
      dialog = null;
    }
  }

  public static void closeWaitDialog() {
    if (waitDialog != null && waitDialog.isShowing()) {
      waitDialog.dismiss();
      waitDialog = null;
    }
  }

  public static void closeCustomDialog() {
    if (customDialog != null && customDialog.isShowing()) {
      customDialog.dismiss();
      customDialog = null;
    }
  }

}
