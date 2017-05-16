package com.bofsoft.laio.myclass;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import com.bofsoft.laio.common.ErrorCode;
import com.bofsoft.laio.tcp.IResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

// 自定义的窗口类
public class MyActivityInfo {
    private long Key = 0;
    private boolean isActive = false;
    // mListener不能为static 若为static, 当上个请求还未返回就发起下个命令时 会出现两个命令回调同一个mListener的情况
    private IResponseListener mListener;

    public IResponseListener getmListener() {
        return mListener;
    }

    public void setmListener(IResponseListener mListener) {
        this.mListener = mListener;
    }

    public Handler getHandler() {
        return mHandler;
    }

    public long getKey() {
        return Key;
    }

    public void setKey(long key) {
        Key = key;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    // 此处不能将MHandler修改为static 原因见mListener定义部分
    @SuppressLint("HandlerLeak")
    class MHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            if (mListener != null) {
                switch (message.what) {
                    case ErrorCode.NETWORK_NOT_AVAILABLE:
                        mListener.messageBackFailed(message.what, "");
                        break;
                    case ErrorCode.SEND_SUCESS:
                    case ErrorCode.SEND_FAILURE:
                        try {
                            mListener.messageBack(message.what, String.valueOf(message.obj));
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }
                        break;
                    default:
                        if (message.obj instanceof String) {
                            try {
                                JSONObject jsonObject = new JSONObject((String) message.obj);
                                if (jsonObject.isNull("ErrorCode")) {
                                    // 没有报错误信息
                                    if (message.obj == null) {
                                        mListener.messageBack(message.what, message.arg1, message.arg2);
                                    } else {
                                        mListener.messageBack(message.what, (String) message.obj);
                                    }
                                } else {
                                    // 报错误信息
                                    int ErrorCode = jsonObject.getInt("ErrorCode");
                                    String Error = "";
                                    if (!jsonObject.isNull("Error")) {
                                        Error = jsonObject.getString("Error");
                                    }
                                    mListener.messageBackFailed(ErrorCode, Error);
                                }
                            } catch (JSONException e) {
                                String Error = "解析数据异常！,what= " + message.what + ", result= " + message.obj;
                                mListener.messageBackFailed(ErrorCode.Error_Code_Parse_Exception, Error);
                            }
                        }
                        break;
                }
            }
        }
    }

    public MHandler mHandler = new MHandler();
}
