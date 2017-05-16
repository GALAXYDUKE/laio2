package com.bofsoft.sdk.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.bofsoft.sdk.R;

@SuppressLint("InflateParams")
public class NsPopupwindow extends PopupWindow {

    private View root;
    @SuppressWarnings("unused")
    private View background;
    private RelativeLayout content;
    @SuppressWarnings("unused")
    private Context act;

    public NsPopupwindow(View v, Integer width, Integer height, boolean focusable) {
        super(v, width, height, focusable);
    }

    public static NsPopupwindow getInstence(Context act, View v) {
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(-1, -1);
        return getInstence(act, v, lp);
    }

    public static NsPopupwindow getInstence(Context act, View v, RelativeLayout.LayoutParams lp) {
        View popWindowView = LayoutInflater.from(act).inflate(R.layout.ns_popupwindow, null);
        final NsPopupwindow np =
                new NsPopupwindow(popWindowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        np.act = act;
        np.setOutsideTouchable(true);
        np.setFocusable(true);
        np.background = popWindowView.findViewById(R.id.background);
        np.content = (RelativeLayout) popWindowView.findViewById(R.id.content);
        np.content.addView(v, lp);
        np.root = np.content;
        v.setOnClickListener(np.onclickListener);
        np.content.setOnClickListener(np.onclickListener);
        return np;
    }

    private OnClickListener onclickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.content) {
                close();
            }
        }
    };

    public void close() {
        if (isShowing()) {
            dismiss();
        }
    }

    public void show() {
        if (root != null && !((Activity) act).isFinishing()) {
            showAtLocation(root, 1, 0, 0);
        }
    }
}
