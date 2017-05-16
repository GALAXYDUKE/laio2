package com.bofsoft.sdk.pay;

import android.app.Activity;
import android.util.Log;

import com.bofsoft.sdk.pay.alipay.AilPay;
import com.bofsoft.sdk.pay.wxpay.WXPay;

public class PayFactory {

    private final static String TAG = PayFactory.class.getSimpleName();

    /**
     * 获取支付实例
     *
     * @param type 支付方式
     * @return
     */
    public static Pay createNow(Activity activity, PayType type) {
        Pay pay = null;
        if (type == PayType.AliPay) {
            pay = new AilPay(activity);
        } else if (type == PayType.WxPay) {
            pay = new WXPay(activity);
        } else {
            Log.e(TAG, "支付方式不存在");
        }
        return pay;
    }

    enum PayType {
        AliPay,
        WxPay
    }
}
