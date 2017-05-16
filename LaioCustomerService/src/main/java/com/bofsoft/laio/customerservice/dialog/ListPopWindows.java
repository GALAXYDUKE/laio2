package com.bofsoft.laio.customerservice.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;

import com.bofsoft.laio.customerservice.Adapter.PopWindowsListAdapter;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.CustomListView;

import java.util.List;


public class ListPopWindows extends PopupWindow {
    private Context context;
    private ItemClickCallback callback;
    private LayoutInflater layoutInflater;
    private List<String> items;

    public ListPopWindows(Context context) {
        super(context);
        this.context = context;
        this.layoutInflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public ListPopWindows(Context context, List<String> items) {
        this(context);
        this.items = items;
    }

    public void setList(List<String> items) {
        this.items = items;
    }

    public void showPopWindows(View location) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        View contentView = layoutInflater.inflate(R.layout.pop_windows_list, null);
        CustomListView popListView = (CustomListView) contentView.findViewById(R.id.pop_listView);
        PopWindowsListAdapter adapter = new PopWindowsListAdapter(context);
        adapter.setList(items);
        popListView.setAdapter(adapter);

        int width = wm.getDefaultDisplay().getWidth();

        // // 创建一个PopuWidow对象
        // PopupWindow popupWindow = new PopupWindow(contentView,
        // LayoutParams.MATCH_PARENT,
        // LayoutParams.WRAP_CONTENT);
        this.setContentView(contentView);
        this.setWidth(width / 3);
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);

        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        // this.setBackgroundDrawable(new BitmapDrawable());
        this.showAsDropDown(location);
        popListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (callback == null) {
                    throw new RuntimeException(
                            "please implements the callback methods before use SettingPopWindows!");
                } else {
                    callback.onItemClickCallback(ListPopWindows.this, parent, view, position, id);
                }
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
