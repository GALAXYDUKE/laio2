package com.bofsoft.laio.customerservice.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.Adapter.PopWindowsOptAdapter;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.CustomListView;

import java.util.List;

public class OptionPopWindows extends PopupWindow {
    private Context context;
    private ItemClickCallback callback;
    private LayoutInflater layoutInflater;
    private List<String> items;

    public OptionPopWindows() {
        super();
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public OptionPopWindows(int width, int height) {
        super(width, height);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(View contentView, int width, int height) {
        super(contentView, width, height);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(View contentView) {
        super(contentView);
        // TODO Auto-generated constructor stub
    }

    public OptionPopWindows(Context context, List<String> items) {
        this(context);
        this.items = items;
    }

    public void showPopWindows(View parent) {

        View contentView = layoutInflater.inflate(R.layout.pop_windows_option, null);
        CustomListView popListView = (CustomListView) contentView.findViewById(R.id.pop_listView);
        TextView cancle = (TextView) contentView.findViewById(R.id.pop_cancle);
        PopWindowsOptAdapter adapter = new PopWindowsOptAdapter(context);
        adapter.setList(items);
        popListView.setAdapter(adapter);

        // // 创建一个PopuWidow对象
        // PopupWindow popupWindow = new PopupWindow(contentView,
        // LayoutParams.MATCH_PARENT,
        // LayoutParams.WRAP_CONTENT);
        this.setContentView(contentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        // this.setBackgroundDrawable(new BitmapDrawable());
        this.showAtLocation(parent, Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
        popListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback == null) {
                    throw new RuntimeException(
                            "please implements the callback methods before use SettingPopWindows!");
                } else {
                    callback.onItemClickCallback(OptionPopWindows.this, parent, view, position, id);
                }
            }
        });

        cancle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionPopWindows.this.dismiss();
            }
        });
    }

    public void setItemCallBack(ItemClickCallback callback) {
        this.callback = callback;
    }

    public interface ItemClickCallback {
        void onItemClickCallback(PopupWindow popupWindow, AdapterView<?> parent, View view,
                                 int position, long id);
    }
}
