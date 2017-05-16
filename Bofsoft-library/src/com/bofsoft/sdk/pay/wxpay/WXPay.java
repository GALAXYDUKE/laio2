package com.bofsoft.sdk.pay.wxpay;

import android.app.Activity;

import com.bofsoft.sdk.pay.Pay;
import com.bofsoft.sdk.pay.PayProtos.PayInfoBuf;
import com.bofsoft.sdk.pay.wxpay.utils.Constants;
import com.bofsoft.sdk.pay.wxpay.utils.MD5;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Random;

public class WXPay extends Pay {

    private static final String TAG = WXPay.class.getSimpleName();

    private IWXAPI wx = null;

    PayReq req;

    public WXPay(Activity activity) {
        super(activity);
        wx = WXAPIFactory.createWXAPI(getActivity(), null);
        wx.registerApp(Constants.APP_ID);
    }

    @Override
    public void pay(PayInfoBuf payInfo) {
        req.appId = Constants.APP_ID;
        req.partnerId = Constants.MCH_ID;
        req.prepayId = payInfo.getPrepayId();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        req.sign = payInfo.getSign();
        wx.sendReq(req);
    }

    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }
}
