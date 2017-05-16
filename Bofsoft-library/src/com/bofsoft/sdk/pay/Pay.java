package com.bofsoft.sdk.pay;

import android.app.Activity;
import android.content.Intent;

import com.bofsoft.sdk.pay.PayProtos.PayInfoBuf;

public abstract class Pay {

    private Activity activity;

    private OnResultCallbackListener onResultCallbackListener;

    public Pay(Activity activity) {
        this.activity = activity;
    }

    public abstract void pay(PayInfoBuf info);

    /*支付宝、微信不需要使用此方法**/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    public interface OnResultCallbackListener {
        void back(String result);
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public OnResultCallbackListener getOnResultCallbackListener() {
        return onResultCallbackListener;
    }

    public void setOnResultCallbackListener(OnResultCallbackListener onResultCallbackListener) {
        this.onResultCallbackListener = onResultCallbackListener;
    }
}
