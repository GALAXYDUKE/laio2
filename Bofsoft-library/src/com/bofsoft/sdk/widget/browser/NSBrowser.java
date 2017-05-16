package com.bofsoft.sdk.widget.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.sdk.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("JavascriptInterface")
public class NSBrowser extends RelativeLayout {

    private View v;
    private BaseBrowser wv;
    private ProgressBar pb;
    private NSWebViewClient wclicnt = null;

    private List<String> backTitleList = new ArrayList<String>();
    private List<String> forWardTitleList = new ArrayList<String>();
    public static String UrlforExamination = "";
    public static boolean isFinish = false;//该布尔值 服务 教练端考场模拟
    public static boolean isOutofTime = false;//该布尔值 服务 学员端教练端自主约考 网站超时
    public Context context;
    public String areadm = "";
    public static boolean backfrommap;//是否从考场地图返回
    public static boolean gobackTwiceformap;

    // 回调
    private onTitleBackListener titleListener;

    public NSBrowser(Context context) {
        super(context, null);
        this.context = context;
        initView();
    }

    public NSBrowser(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "NewApi"})
    public void initView() {
        v = inflate(getContext(), R.layout.ns_browser, null);
        addView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        pb = (ProgressBar) v.findViewById(R.id.mProgress);
        wv = (BaseBrowser) v.findViewById(R.id.web);
        wclicnt = new NSWebViewClient(pb);
        wclicnt.setUrlInterface(new NSUrlInterface(getContext()));
        wv.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");
        wv.getSettings().setJavaScriptEnabled(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setUseWideViewPort(true);
        wv.getSettings().setDefaultTextEncodingName("GB2312");
        wv.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        wv.getSettings().setAppCacheEnabled(false); // 打开缓存
        wv.getSettings().setDatabaseEnabled(true);
        wv.getSettings().setDomStorageEnabled(true);

        String ua = wv.getSettings().getUserAgentString();
        wv.getSettings().setUserAgentString(ua + " LaioAdr/2.5");

        wv.setWebViewClient(wclicnt);
        wv.addJavascriptInterface(new NSJavascriptInterface(getContext()),
                "call");
        wv.setWebViewClient(new MyWebViewClient());
        wv.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                NSInterface.getInstence().setTitle(getContext(), title);
                if (titleListener != null)
                    titleListener.title(title);
                backTitleList.add(title);
            }
        });
    }

    public class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            isFinish = false;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
//			Log.e("tag","Url:"+url);$(\"form#login1 input[name='username']\").val(\"#1#\");$(\"form#login1 input[name='password']\").val(\"#2#\");
//			view.loadUrl("javascript:$(\"form#login1 input[name='username']\").val('566');$(\"form#login1 input[name='password']\").val('655');");
            UrlforExamination = url;
            isFinish = true;
            isOutofTime = false;
            if (ConfigAll.forAppoint == 1) {
                view.loadUrl("javascript:$(\"form#login1 input[name='username']\").val(\"" + ConfigAll.IDCardNumforAppoint + "\");$(\"form#login1 input[name='password']\").val(\"" + ConfigAll.PwdforAppoint + "\");");
            }

            if (url.contains("/dpsite/sitemap.html")) {//保存考场的areadm    ||url.contains("/dpsite/testsub.html")
                view.loadUrl("javascript:window.local_obj.showSource("
                        + "document.getElementById('areadm').value);");
            }
            if (!url.contains("/dpsite/sitelist.html?")) {
                gobackTwiceformap = false;
            }
            if (backfrommap) {//从考场地图返回 改变citydm
                backfrommap = false;
                String tmpUrl[] = url.split("\\?");
                String loadUrl = tmpUrl[0] + "?areadm=" + areadm;
                view.loadUrl(loadUrl);
                gobackTwiceformap = true;
            }
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            view.loadUrl("file:///android_asset/index.html");
        }

        // 当load有ssl层的https页面时，如果这个网站的安全证书在Android无法得到认证，WebView就会变成一个空白页，
        // 而并不会像PC浏览器中那样跳出一个风险提示框
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

    private void removeCookie(Context context) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    public void setJavascriptInterface(NSJavascriptInterface itf) {
        wv.addJavascriptInterface(itf, "call");
    }

    public void setUrlInterface(NSUrlInterface urli) {
        wclicnt.setUrlInterface(urli);
    }

    public void loadDataWithBaseUrl(String baseUrl, String data, String failUrl) {
        wv.loadDataWithBaseURL(baseUrl, data, "text/html", "GB2312", failUrl);
    }

    public void loadHtml(String html) {
        wv.loadData(html, "text/html; charset=UTF-8", null);
    }

    public void loadUrl(String url) {
        wv.loadUrl(url);
    }

    public void clearCache(boolean flag) {
        wv.clearCache(flag);
        removeCookie(context);
    }

    public void setAutoScroll(boolean autoScroll) {
        wv.setAutoScroll(autoScroll);
    }

    public boolean canGoBack() {
        return wv.canGoBack();
    }

    public boolean canGoForward() {
        return wv.canGoForward();
    }

    public boolean goBack() {
        if (wv.canGoBack()) {
            wv.goBack();
            int titleSize = backTitleList.size();
            if (titleSize >= 2) {
                String title = backTitleList.get(titleSize - 1);
                forWardTitleList.add(title);
                backTitleList.remove(titleSize - 1);
                title = backTitleList.get(titleSize - 2);
                NSInterface.getInstence().setTitle(getContext(), title);
                if (titleListener != null)
                    titleListener.title(title);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean goForward() {
        if (wv.canGoForward()) {
            wv.goForward();
            int forWardSize = forWardTitleList.size();
            if (forWardSize >= 1) {
                String title = forWardTitleList.get(forWardSize - 1);
                NSInterface.getInstence().setTitle(getContext(), title);
                if (titleListener != null)
                    titleListener.title(title);
                backTitleList.add(title);
                forWardTitleList.remove(forWardSize - 1);
            }
            return true;
        } else {
            return false;
        }
    }

    public void reload() {
        wv.reload();
    }

    /**
     * 标题回调
     */
    public interface onTitleBackListener {
        void title(String title);
    }

    public void setOnTitleBackListener(onTitleBackListener listener) {
        this.titleListener = listener;
    }

    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void showSource(String html) {
            areadm = html;
            backfrommap = true;
        }
    }
}
