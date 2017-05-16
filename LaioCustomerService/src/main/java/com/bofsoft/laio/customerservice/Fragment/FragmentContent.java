package com.bofsoft.laio.customerservice.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.JasonWebView;
import com.bofsoft.sdk.widget.base.BaseFragment;
import com.bofsoft.sdk.widget.base.Event;


/**
 * 自定义加载内容
 *
 * @author yedong
 */
public class FragmentContent extends BaseFragment {

    private JasonWebView webView;
    private String mContent; // 加载的内容

    // private boolean isCallBack = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getActivity()).inflate(R.layout.fragment_webview_content, container,
                        false);
        initView(view);
        return view;
    }

    /**
     * 获取培训订单协议
     *
     * @param reqType
     * @param protocolType
     * @param type
     * @param id
     * @param classType
     */
    public void loadData(String content) {
        this.mContent = content;
        loadView(mContent);
    }

    public void initView(View view) {
        webView = (JasonWebView) view.findViewById(R.id.webview);
        webView.getSettings().setDefaultFontSize(14);
        if (mContent != null) {
            loadView(mContent);
        }

        // webView.setJasonWebViewBack(new IJasonWebViewBack() {
        // @Override
        // public int onLoadOverAndChangeSize(View v, int h) {
        // int parentH = JasonScrollView.getScrollParentH(mOptionView);
        // int opHeight = 0;
        // if (mOptionView != null) {
        // opHeight = mOptionView.getHeight();
        // }
        // int height = parentH - opHeight;
        // if (height < h && mHandler != null) {
        // mHandler.sendEmptyMessage(0);
        // }else{
        // if (isCallBack) {
        // if (webView.isloadOver) {
        // if (mHandler != null)
        // mHandler.sendEmptyMessage(0);
        // }
        // }
        // }
        // isCallBack = true;
        // return h < height ? height : h;
        // }
        // });

    }

    /**
     * 刷新界面
     */
    public void loadView(String data) {
        if (webView == null)
            return;
        if (data != null) {
            webView.loadDataWithBaseURL("", data, "text/html", "UTF-8", "");
        }
        // else {
        // webView.loadUrl("file:///android_asset/index.html");
        // }

        // webView.setWebViewClient(new WebViewClient() {
        // @SuppressLint("NewApi")
        // @Override
        // public void onPageFinished(WebView view, String url) {
        // webView.addOnLayoutChangeListener(mChangeListener);
        // }
        // });

    }

    // /**
    // * 接受从外面发送过来的option控件
    // *
    // * @param view
    // */
    // public void sendView(View view) {
    // this.mOptionView = view;
    // }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        if (webView != null) {
            ViewGroup parent = (ViewGroup) webView.getParent();
            if (parent != null) {
                parent.removeView(webView);
            }
            super.onDestroyView();
            webView.removeAllViews();
            webView.destroy();
        } else {
            super.onDestroyView();
        }
    }

    @Override
    protected void setTitleFunc() {

    }

    @Override
    protected void setActionBarButtonFunc() {

    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {

    }
}
