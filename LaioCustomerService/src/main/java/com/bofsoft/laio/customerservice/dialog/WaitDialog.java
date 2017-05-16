package com.bofsoft.laio.customerservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.R;

public class WaitDialog extends Dialog implements View.OnClickListener {
    MyLog myLog = new MyLog(WaitDialog.class);
    Context context;
    TextView textTitle;
    TextView textContent;
    TextView btnOK;
    String title;
    String content;
    private TextView tv;

    public WaitDialog(Context context) {
        super(context, R.style.WaitDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_wait);
        TextView waitDialogDismiss = (TextView) findViewById(R.id.waitDialogDismiss);
        tv = (TextView) findViewById(R.id.waitTv);
        waitDialogDismiss.setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        this.dismiss();
        myLog.i("waitDialog.dismiss()");
    }

    public void show(String text) {
        this.show();
        tv = (TextView) this.getWindow().findViewById(R.id.waitTv);
        tv.setText(text);
    }
}
