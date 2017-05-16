package com.bofsoft.laio.customerservice.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Button;


public class ConfirmDialog extends Dialog {
    Context context;
    TextView textTitle;
    TextView textContent;
    Widget_Button btnOK;
    Widget_Button btnCancel;
    String title;
    String content;
    static View.OnClickListener onClickListener;

    public ConfirmDialog(Context context, String title, String content,
                         View.OnClickListener onClickListener) {
        super(context, R.style.PromoptDialog);
        this.context = context;
        this.title = title;
        this.content = content;
        ConfirmDialog.onClickListener = onClickListener;
    }

    public ConfirmDialog(Context context, String title, String content) {
        super(context, R.style.PromoptDialog);
        this.context = context;
        this.title = title;
        this.content = content;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirm);

        textTitle = (TextView) findViewById(R.id.confirm_txtTitle);
        textContent = (TextView) findViewById(R.id.confirm_txtContent);
        btnOK = (Widget_Button) findViewById(R.id.confirm_btnOK);
        btnCancel = (Widget_Button) findViewById(R.id.confirm_btnCancel);

        textTitle.setText(title);
        textContent.setText(Html.fromHtml(content));
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.this.dismiss();
                if (onClickListener != null)
                    onClickListener.onClick(v);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmDialog.this.dismiss();
                if (onClickListener != null)
                    onClickListener.onClick(v);
            }
        });
    }
}
