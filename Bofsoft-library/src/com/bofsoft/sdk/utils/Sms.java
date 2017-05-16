package com.bofsoft.sdk.utils;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;

import java.util.List;

public class Sms {

    private static Sms self;

    private Sms() {

    }

    public static Sms getInstence() {
        if (self == null)
            self = new Sms();
        return self;
    }

    /**
     * OK 发送成功 ERROR_GENERIC_FAILURE 错误 ERROR_RADIO_OFF ERROR_NULL_PDU DELIVERED 收信人成功接收
     */
    public enum SENT_STATE {
        OK, ERROR_GENERIC_FAILURE, ERROR_RADIO_OFF, ERROR_NULL_PDU, DELIVERED
    }

    public static final String SENT_SMS_ACTION = "SENT_SMS_ACTION";
    public static final String DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";

    /**
     * 调用系统短信界面
     *
     * @param act
     * @param number
     * @param msg
     */
    public void send(Activity act, String number, String msg) {
        Intent intent = null;
        if (number != null) {
            Uri uri = Uri.parse("smsto:" + number);
            intent = new Intent(Intent.ACTION_SENDTO, uri);
        } else {
            intent = new Intent(Intent.ACTION_SENDTO);
        }
        intent.putExtra("sms_body", msg == null ? "" : msg);
        act.startActivity(intent);
    }

    /**
     * 自动发送
     *
     * @param act
     * @param number
     * @param msg
     */
    public void autoSend(Activity act, String number, String msg) {
        this.autoSend(act, number, msg, null);
    }

    /**
     * 自动发送
     *
     * @param act
     * @param number
     * @param msg
     * @param handler
     */
    public void autoSend(Activity act, String number, String msg, SentHandler handler) {
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentPI = PendingIntent.getBroadcast(act, 0, new Intent(SENT_SMS_ACTION), 0);
        PendingIntent deliverPI =
                PendingIntent.getBroadcast(act, 0, new Intent(DELIVERED_SMS_ACTION), 0);
        if (handler != null) {
            act.registerReceiver(new SmsSentBroadcastReceiver(handler), new IntentFilter(SENT_SMS_ACTION));
            act.registerReceiver(new SmsDeliveredBroadcastReceiver(handler), new IntentFilter(
                    DELIVERED_SMS_ACTION));
        }
        List<String> divideContents = smsManager.divideMessage(msg);
        for (String text : divideContents) {
            smsManager.sendTextMessage(number, null, text, sentPI, deliverPI);
        }
    }


    class SmsSentBroadcastReceiver extends BroadcastReceiver {

        private SentHandler handler;

        public SmsSentBroadcastReceiver(SentHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    handler.back(SENT_STATE.OK);
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    handler.back(SENT_STATE.ERROR_GENERIC_FAILURE);
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    handler.back(SENT_STATE.ERROR_RADIO_OFF);
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    handler.back(SENT_STATE.ERROR_NULL_PDU);
                    break;
            }
        }
    }

    class SmsDeliveredBroadcastReceiver extends BroadcastReceiver {

        private SentHandler handler;

        public SmsDeliveredBroadcastReceiver(SentHandler handler) {
            this.handler = handler;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            handler.back(SENT_STATE.DELIVERED);
        }
    }

    public interface SentHandler {
        void back(SENT_STATE state);
    }
}
