package com.bofsoft.sdk;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.sdk.widget.browser.NSBrowser;
import com.bofsoft.sdk.widget.popupwindow.NsPopupwindow;

@SuppressLint("NewApi")
public class BrowserPopupwindow {

    private Context act;
    private NsPopupwindow disPopwindow;
    private NSBrowser browser;
    //  private TextView text;
    private ScrollView scroll;
    private RelativeLayout htmlContet;

    private static BrowserPopupwindow self;

    public static BrowserPopupwindow getInstence(Context act) {
        if (self != null) {
            if (self.act != act)
                self = null;
        }
        if (self == null) {
            self = new BrowserPopupwindow();
            self.act = act;
        }
        return self;
    }

    public BrowserPopupwindow show() {
        if (disPopwindow == null) {
            int gap = act.getResources().getDimensionPixelOffset(R.dimen.DP_10);

            LinearLayout contet = new LinearLayout(act);
            htmlContet = new RelativeLayout(act);
//      text = new TextView(act);
//      text.setPadding(gap, gap, gap, gap);
            scroll = new ScrollView(act);
            if (Config.SDK < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//        contet.setBackgroundDrawable(act.getResources().getDrawable(
//            R.drawable.group_gray_border_gray_body));
            } else {
//        contet.setBackground(act.getResources().getDrawable(
//            R.drawable.group_gray_border_gray_body));
            }
            contet.setOrientation(LinearLayout.VERTICAL);

//      htmlContet.addView(text, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
//          android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            scroll.addView(htmlContet, new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                    android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
            contet.addView(scroll, new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
            TextView btn = new TextView(act);
            btn.setText("知道啦");
            btn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            btn.setTextColor(act.getResources().getColor(R.color.RGB_FFF));
            btn.setGravity(Gravity.CENTER);
            btn.setPadding(gap, gap, gap, gap);
            if (Config.SDK < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//        btn.setBackgroundDrawable(act.getResources().getDrawable(
//            R.drawable.selector_orange_border));
            } else {
//        btn.setBackground(act.getResources().getDrawable(
//            R.drawable.selector_orange_border));
            }
            btn.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (self != null)
                        self.close();
                }
            });
            LayoutParams btnLp =
                    new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btnLp.topMargin = gap;
            btnLp.bottomMargin = gap;
            btnLp.leftMargin = gap;
            btnLp.rightMargin = gap;
            contet.addView(btn, btnLp);

            RelativeLayout.LayoutParams lp =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
            lp.topMargin = Config.actionBarHeight + gap;
            lp.bottomMargin = gap;
            lp.leftMargin = gap * 2;
            lp.rightMargin = gap * 2;
            disPopwindow = NsPopupwindow.getInstence(act, contet, lp);
        }
        if (browser != null && browser.getParent() != null) {
            htmlContet.removeView(browser);
        }
        browser = new NSBrowser(act);
        browser.setAutoScroll(false);
        browser.setVisibility(View.GONE);
//    text.setVisibility(View.GONE);
        htmlContet.addView(browser, new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
        disPopwindow.show();
        return this;
    }

    public void loadHtml(String html) {
        html = "<!DOCTYPE html>"
                + " <html>"
                + "    <head>"
                + "        <meta charset=&quot;utf-8&quot;>"
                + "        <meta name=&quot;viewport&quot; content=&quot;width=device-width; height=device-height; user-scalable=no; maximum-scale=1.0; initial-scale=1.0;&quot;>"
                + "        <title></title>"
                + "    </head>"
                + "    <body>"
                + html
                + "    </body>"
                + "</html>";

        scroll.scrollTo(0, 0);
        browser.loadHtml(html);
//    text.setText(Html.fromHtml(html));
        browser.setVisibility(View.VISIBLE);
//    text.setVisibility(View.GONE);
    }

    public void loadUrl(String url) {
        browser.loadUrl(url);
        browser.setVisibility(View.VISIBLE);
//    text.setVisibility(View.GONE);
    }

    public void loadDataWithBaseUrl(String baseUrl, String data, String failUrl) {
        browser.loadDataWithBaseUrl(baseUrl, data, failUrl);
        browser.setVisibility(View.VISIBLE);
//    text.setVisibility(View.GONE);
    }

    public interface CallBackLinstener {
        void back();
    }

    public void close() {
        if (disPopwindow != null)
            disPopwindow.close();
    }

}
