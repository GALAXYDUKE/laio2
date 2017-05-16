package com.bofsoft.sdk.widget.browser;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NSWebViewClient extends WebViewClient {

    ProgressBar mProgressBar;

    private NSUrlInterface urli = null;

    public NSWebViewClient(ProgressBar mProgressBar) {
        super();
        this.mProgressBar = mProgressBar;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (urli != null)
            return urli.onLoadding(view, url);
        return false;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if (urli != null)
            urli.onFilished(view, url);
        mProgressBar.setVisibility(View.GONE);
        super.onPageFinished(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (urli != null)
            urli.onStarted(view, url, favicon);
        mProgressBar.setVisibility(View.VISIBLE);
        super.onPageStarted(view, url, favicon);
    }

    public void setUrlInterface(NSUrlInterface urli) {
        this.urli = urli;
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                   SslError error) {
        // 忽略证书的错误继续Load页面内容
        handler.proceed();
        // handler.cancel(); // Android默认的处理方式
        // handleMessage(Message msg); // 进行其他处理
        // super.onReceivedSslError(view, handler, error);
    }
}
