package com.bofsoft.laio.customerservice.Common;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.bofsoft.laio.common.BroadCastNameManager;
import com.bofsoft.laio.customerservice.Config.Config;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;

import java.util.List;

/**
 * Created by szw on 2017/3/8.
 */

public class MyPushMessageReceiver extends PushMessageReceiver {


    public static final String USER_TOCKEN = "UserTocken";
    public int sum = 0;

    @Override
    public void onBind(Context context, int errorCode, String appid, String userId, String channelId,
                       String requestId) {
        Log.e("tag", "push onBind errorCode=" + errorCode + " appid=" + appid + " userId=" + userId
                + " channelId=" + channelId + " requestId=" + requestId);
        // 绑定成功，保存UserToken，可以有效的减少不必要的绑定请求
        if (errorCode == 0 && null != channelId) {
            // 保存UserToken
            ConfigallCostomerService.UserToken = channelId;
            Config.spHelper.putString(USER_TOCKEN, channelId);
        } else {
            if (sum < 5) {
                PushManager.startWork(context.getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,
                        PushmessageUitls.getMetaValue(context.getApplicationContext(), "api_key"));
                sum++;
            }
        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(Context context, String title, String description,
                                      String customContentString) {
        Log.e("tag", "push onNotificationClicked title=" + title + " description=" + description
                + " customContent=" + customContentString);
        context.sendBroadcast(new Intent(BroadCastNameManager.Msg_List));
    }

    @Override
    public void onNotificationArrived(Context context, String s, String s1, String s2) {

    }
}
