package com.bofsoft.sdk.widget.popupwindow;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bofsoft.sdk.R;


@SuppressLint("InflateParams")
public class OptionPopupWindow extends PopupWindow {

    private View root;
    private LinearLayout contentLl;
    private TextView cleanTv;
    private View background;
    private View content;
    private Activity act;
    private static OptionPopupWindow pw = null;

    public OptionPopupWindow(View v, Integer width, Integer height, boolean focusable) {
        super(v, width, height, focusable);

    }

    public static void show(Activity act, String[] values, final onSetChangeItemListener listener) {
        if (pw != null && pw.isShowing() || values == null || values.length <= 0)
            return;
        LayoutInflater inflate = act.getLayoutInflater();
        View popWindowView = inflate.inflate(R.layout.bofsoft_popupwindow_select, null);
        pw =
                new OptionPopupWindow(popWindowView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                        true);
        pw.setOutsideTouchable(true);
        pw.setFocusable(true);
        pw.content = popWindowView.findViewById(R.id.content);
        pw.background = popWindowView.findViewById(R.id.background);
        pw.act = act;
        // pw.setAnimationStyle(R.style.photo_from_down);
        Animation aset = AnimationUtils.loadAnimation(act, R.anim.photo_enter);
        pw.content.startAnimation(aset);
        Animation aalpha = AnimationUtils.loadAnimation(act, R.anim.photo_alipha_enter);
        pw.background.startAnimation(aalpha);

        pw.root = ((ViewGroup) act.findViewById(android.R.id.content)).getChildAt(0);
        popWindowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWithAnim();
            }
        });

        pw.contentLl = (LinearLayout) popWindowView.findViewById(R.id.pop_content);
        pw.cleanTv = (TextView) popWindowView.findViewById(R.id.pop_cancle_btn);
        if (values.length > 1) {
            for (int i = 0; i < values.length - 1; i++) {
                View v = inflate.inflate(R.layout.bofsoft_popupwindow_select_item, null);
                v.setId(i);
                TextView title = (TextView) v.findViewById(R.id.pop_tv);
                title.setText(values[i]);
                pw.contentLl.addView(v);
                if (i < values.length - 2) {
                    View line = inflate.inflate(R.layout.bofsoft_popupwindow_select_line, null);
                    pw.contentLl.addView(line);
                }
                v.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        listener.onClick(v, v.getId());
                        // close();
                    }
                });
            }
        }
        pw.cleanTv.setText(values[values.length - 1]);
        pw.cleanTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                closeWithAnim();
            }
        });
        showPW();
    }

    public static void close() {
        close(500);
    }

    public static void close(int delay) {
        if (pw != null && pw.isShowing()) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    pw.dismiss();
                    pw = null;
                }
            }, delay);
        }
    }

    public static void closeWithAnim() {
        if (pw != null && pw.isShowing()) {
            Animation aalpha = AnimationUtils.loadAnimation(pw.act, R.anim.photo_alipha_exit);
            pw.background.startAnimation(aalpha);
            Animation aset = AnimationUtils.loadAnimation(pw.act, R.anim.photo_exit);
            pw.content.startAnimation(aset);
            aset.setAnimationListener(new AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    pw.dismiss();
                    pw = null;
                }
            });
        }
    }

    private static void showPW() {
        if (pw != null && pw.root != null) {
            Log.e("", pw.root.getHeight() + "");
            pw.showAtLocation(pw.root, 1, 0, 0);
        }
    }

    public interface onSetChangeItemListener {
        void onClick(View v, int position);
    }
}
