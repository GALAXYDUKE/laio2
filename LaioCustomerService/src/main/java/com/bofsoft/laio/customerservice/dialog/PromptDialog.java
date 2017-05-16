package com.bofsoft.laio.customerservice.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;

import com.bofsoft.laio.customerservice.R;

public class PromptDialog {
    String title;
    String content;
    Context context;
    OnClickListener onClickListener;
    AlertDialog dialog;

    public PromptDialog(Context context, String title, String content, OnClickListener onClickListener) {
        this.title = title;
        this.content = content;
        this.context = context;
        this.onClickListener = onClickListener;
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title).setMessage(content).setIcon(R.drawable.ic_launcher)
                    .setCancelable(false).setPositiveButton("知道了", onClickListener).create();
            dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        if (dialog != null) {
            return dialog.isShowing();
        } else {
            return false;
        }
    }

    public void close() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
