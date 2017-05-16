package com.bofsoft.laio.customerservice.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class JasonWebView extends WebView {
    public Boolean isloadOver;

    public interface IJasonWebViewBack {
        int onLoadOverAndChangeSize(View v, int h);
    }

    IJasonWebViewBack JasonWebViewBack = null;

    public void setJasonWebViewBack(IJasonWebViewBack webViewBack) {
        JasonWebViewBack = webViewBack;
    }

    public JasonWebView(Context context) {
        super(context);
        setweb();
    }

    public JasonWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setweb();
    }

    public JasonWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setweb();
    }

    private void setweb() {
        isloadOver = false;
        super.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                isloadOver = true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isloadOver = false;
            }

        });
        // this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
        //
        // @Override
        // public void onLayoutChange(View v, int left, int top, int right,
        // int bottom, int oldLeft, int oldTop, int oldRight,
        // int oldBottom) {
        //
        // if ((isloadOver) && (JasonWebViewBack != null)) {
        // JasonWebViewBack.onLoadOverAndChangeSize(JasonWebView.this,
        // JasonWebView.this.getHeight());
        // }
        // }
        // });
    }

    @Override
    @Deprecated
    public void setWebViewClient(WebViewClient client) {
        // 不再支持该方法，已经被内部方法占用了
        try {
            throw new Throwable("not support！");
        } catch (Throwable e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if ((isloadOver) && (JasonWebViewBack != null)) {
            int hh = JasonWebViewBack.onLoadOverAndChangeSize(this, h);
            if (hh != h) {
                new AsyncTask<Integer, Integer, Integer>() {

                    @Override
                    protected Integer doInBackground(Integer... params) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return params[0];
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        super.onPostExecute(result);
                        ViewGroup.LayoutParams la =
                                JasonWebView.this.getLayoutParams();
                        la.height = result;
                        JasonWebView.this.setLayoutParams(la);
                    }

                }.execute(hh);
            }
        }
    }
}
