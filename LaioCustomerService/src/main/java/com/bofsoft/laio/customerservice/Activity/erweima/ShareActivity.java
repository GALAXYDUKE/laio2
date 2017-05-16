package com.bofsoft.laio.customerservice.Activity.erweima;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.customerservice.Activity.BaseVehicleActivity;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.ShareContentData;
import com.bofsoft.laio.customerservice.DataClass.erweima.ShareCodeAndInfo;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.Widget_Button;
import com.bofsoft.laio.tcp.DataLoadTask;
import com.bofsoft.sdk.widget.base.Event;

import org.json.JSONException;
import org.json.JSONObject;

import m.framework.ui.widget.asyncview.AsyncImageView;

//import android.util.Log;

public class ShareActivity extends BaseVehicleActivity {
    private Widget_Button btn_share;
    private ShareContentData mShareContentData;
    private AsyncImageView asyncImageView;

    //实例化，保证点击邀请好友过快而sharecodeandinfo没有实例化而造成的nullpointe
    public ShareCodeAndInfo sharecodeandinfo = new ShareCodeAndInfo();
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, true);
        setContentView(R.layout.activity_shareapp);

        ConfigallCostomerService.shareredpoint = true;
        sp = getSharedPreferences("ShowRedPoint", Context.MODE_PRIVATE);
        editor = sp.edit();
        editor.putBoolean("ShowRedPoint", true);
        editor.commit();

        init();
    }

    private class MonitorWebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//			Intent i=new Intent(getApplicationContext(),WebViewActivity.class);
//			i.putExtra("url",url);
//			startActivity(i);

            return true;
        }
    }

    private void init() {
        super.onResume();
        JSONObject jsonObject = new JSONObject();
        try {
//			  jsonObject.put("CityDM", ConfigallCostomerService.setandGetDefaultCityInfo.CityDM);
            jsonObject.put("CityDM", "510100");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_SHARECODEANDINFO,
                jsonObject.toString(), this);
        asyncImageView = (AsyncImageView) findViewById(R.id.erweima_pic);
        btn_share = (Widget_Button) findViewById(R.id.btn_share);
        btn_share.setEnabled(false);
        btn_share.setBackgroundResource(R.mipmap.button_send_unclick);
        btn_share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareActivity.this.showShare(sharecodeandinfo.shareinfo, "", "");
                ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                Uri copyUri = Uri.parse(sharecodeandinfo.inviteurl);
                ClipData clipData = ClipData.newUri(getContentResolver(),"URi",copyUri);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(ShareActivity.this, "复制成功，可以发给朋友们了。", Toast.LENGTH_LONG).show();
            }
        });
    }
//
//    @Override
//    protected void onDestroy() {
//        // TODO Auto-generated method stub
//        if (webview != null) {
//            ViewGroup parent = (ViewGroup) webview.getParent();
//            if (parent != null) {
//                parent.removeView(parent);
//            }
//            super.onDestroy();
//            webview.removeAllViews();
//            webview.destroy();
//        } else {
//            super.onDestroy();
//        }
//    }

    /**
     * 解析分享文件内容
     *
     * @param result
     */
    public void parseShareContent(String result) {
        mShareContentData = JSON.parseObject(result, ShareContentData.class);
        ConfigallCostomerService.ShareContentData = mShareContentData;
        ShareActivity.this.showShare(sharecodeandinfo.shareinfo, "", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void setTitleFunc() {
        setTitle("我的二维码");
    }

    @Override
    protected void setActionBarButtonFunc() {
        addLeftButton(Config.abBack.clone());
    }

    @Override
    protected void actionBarButtonCallBack(int id, View v, Event e) {
        switch (id) {
            case 0:
                finish();
                break;
        }
    }

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_SHARECODEANDINFO://获取我的优惠码及分享信息
                ConfigallCostomerService.isShareActivity = true;
                sharecodeandinfo = JSON.parseObject(result, ShareCodeAndInfo.class);
//			Log.e("tag", "sharecodeandinfo:"+sharecodeandinfo.shareinfo);
                if (sharecodeandinfo != null) {
                    ConfigallCostomerService.ShareCodeandInfo = sharecodeandinfo;
                    asyncImageView.execute(sharecodeandinfo.qrcodeurl);
//				webview.loadUrl("http://www.baidu.com");
                    btn_share.setEnabled(true);
                    btn_share.setBackgroundResource(R.mipmap.button_send);
                } else {
                    btn_share.setEnabled(false);
                    btn_share.setBackgroundResource(R.mipmap.button_send_unclick);
                }

                break;
            default:
                super.messageBack(code, result);
                break;
        }
    }
}
