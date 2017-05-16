package com.bofsoft.laio.laiovehiclegps.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bofsoft.laio.laiovehiclegps.Config.Config;
import com.bofsoft.laio.common.Func;
import com.bofsoft.laio.laiovehiclegps.R;
import com.bofsoft.sdk.widget.base.Event;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by szw on 2017/3/1.
 */

public class RegisterProtocolActivity extends BaseVehicleActivity{
    private WebView webView;
    private String URL;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_protocol);

        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");

        init();
    }

    private void init() {
        // btnTitileBack = (Widget_Image_Text_Btn) findViewById(R.id.title_back);
        webView = (WebView) findViewById(R.id.webview);

        webView.getSettings().setJavaScriptEnabled(true); // 设置JavaScript脚本
        webView.getSettings().setBuiltInZoomControls(true);// 会出现放大缩小的按钮
        webView.getSettings().setSupportZoom(true);
        WebChromeClient wvcc = new WebChromeClient() { // WebChromeClient是辅助WebView处理Javascript的对话框，网站图标，网站title，加载进度等
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                setTitle(title);
            }
        };
        webView.setWebChromeClient(wvcc);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                final String json = "版本号：" + Func.packageInfo(RegisterProtocolActivity.this, "versionName");
                webView.loadUrl("javascript:document.getElementById('systemver').innerHTML='" + json + "'");
                super.onPageFinished(view, url);
            }

        });
        checkWebViewUrl(webView, URL);
    }


    private void checkWebViewUrl(final WebView webView, final String url) {
        if (url == null || url.equals("")) {
            return;
        }
        new AsyncTask<String, Void, Integer>() {

            @Override
            protected Integer doInBackground(String... params) {
                int responseCode = -1;
                try {
                    java.net.URL url = new URL(params[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    responseCode = connection.getResponseCode();
                } catch (Exception e) {
                    mylog.e("Loading webView error:" + e.getMessage());
                }
                return responseCode;
            }

            @Override
            protected void onPostExecute(Integer result) {
                if (result != 200) {
                    webView.loadUrl("file:///android_asset/index.html");
                } else {
                    webView.loadUrl(url);
                }
            }
        }.execute(url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            finish();
        }
    }

    @Override
    protected void setTitleFunc() {
        setTitle("服务协议");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    finish();
                }
                break;
        }
    }
}