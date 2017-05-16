package com.bofsoft.laio.customerservice.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.Activity.BaseFragment;
import com.bofsoft.laio.customerservice.DataClass.BaseResponseStatusData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.JasonScrollView;
import com.bofsoft.laio.customerservice.Widget.JasonWebView;
import com.bofsoft.laio.tcp.DataLoadTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 产品协议：服务标准 退费规则 学员保障
 *
 * @author yedong
 */
public class FragmentServiceProtocol extends BaseFragment {
    MyLog myLog = new MyLog(getClass());
    private JasonWebView mWebView;
    private View mOptionView;

    private int ReqType; // Integer 请求方式：0 产品发布时请求 ,1 通过产品或订单Id
    private int ProtocolType; // Integer 协议类型，0服务标准，1退费规则

    // 产品或订单Id：
    private int Type; // Integer 请求类型，0产品，1订单
    private int Id; // Integer 产品或订单ID
    private int ClassType = -1; // 产品Id时(培训需此参数)：0为返回全部，1初学培训，2补训，3陪练；

    private BaseResponseStatusData mProtocalData;

    // public Handler mHandler;
    // private boolean isCallBack = false;

    public FragmentServiceProtocol() {
        super();
    }

    /**
     * @param reqType      请求方式：0 产品发布时请求 ,1 通过产品或订单Id
     * @param protocolType Integer 协议类型，0服务标准，1退费规则
     * @param type         请求类型，0产品，1订单
     * @param id           Integer 产品或订单ID
     */
    @SuppressLint("ValidFragment")
    public FragmentServiceProtocol(int reqType, int protocolType, int type, int id) {
        super();
        ReqType = reqType;
        ProtocolType = protocolType;
        Type = type;
        Id = id;
    }

    /**
     * @param reqType      请求方式：0 产品发布时请求 ,1 通过产品或订单Id
     * @param protocolType Integer 协议类型，0服务标准，1退费规则
     * @param type         请求类型，0产品，1订单
     * @param id           Integer 产品或订单ID
     * @param classType    产品Id时(培训需此参数)：0为返回全部，1初学培训，2补训，3陪练；
     */
    @SuppressLint("ValidFragment")
    public FragmentServiceProtocol(int reqType, int protocolType, int type, int id, int classType) {
        super();
        ReqType = reqType;
        ProtocolType = protocolType;
        Type = type;
        Id = id;
        ClassType = classType;
    }

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_PROTOCOL_TRAIN:
                parseProtocolData(result);
                break;
            case CommandCodeTS.CMD_PROTOCOL_REG:
                parseProtocolData(result);
                break;

            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getActivity()).inflate(R.layout.fragment_service_protocol, container,
                        false);
        initView(view);
        return view;
    }

    /**
     * 获取招生订单协议
     *
     * @param reqType      请求方式：0 产品发布时请求 ,1 通过产品或订单Id
     * @param protocolType Integer 协议类型，0服务标准，1退费规则
     * @param type         请求类型，0产品，1订单
     * @param id           Integer 产品或订单ID
     */
    public void loadData(int reqType, int protocolType, int type, int id) {
        this.ReqType = reqType;
        this.ProtocolType = protocolType;
        this.Type = type;
        this.Id = id;

        if (mProtocalData == null) {
            getProtocalData();
        } else {
            loadView();
        }
    }

    /**
     * 获取培训订单协议
     *
     * @param reqType      请求方式：0 产品发布时请求 ,1 通过产品或订单Id
     * @param protocolType Integer 协议类型，0服务标准，1退费规则
     * @param type         请求类型，0产品，1订单
     * @param id           Integer 产品或订单ID
     * @param classType    产品Id时(培训需此参数)：0为返回全部，1初学培训，2补训，3陪练；
     */
    public void loadData(int reqType, int protocolType, int type, int id, int classType) {
        this.ReqType = reqType;
        this.ProtocolType = protocolType;
        this.Type = type;
        this.Id = id;
        this.ClassType = classType;

        if (mProtocalData == null) {
            getProtocalData();
        } else {
            loadView();
        }
    }

    public void initView(View view) {
        mWebView = (JasonWebView) view.findViewById(R.id.webview);
        mWebView.requestFocus();
        mWebView.getSettings().setDefaultFontSize(14);
        mWebView.setJasonWebViewBack(new JasonWebView.IJasonWebViewBack() {
            @Override
            public int onLoadOverAndChangeSize(View v, int h) {
                int parentH = JasonScrollView.getScrollParentH(mOptionView);
                int opHeight = 0;
                if (mOptionView != null) {
                    opHeight = mOptionView.getHeight();
                }
                int height = parentH - opHeight;
                myLog.i("-------------parentH|" + parentH + "-----opHeight|" + opHeight);
                // if (height < h && mHandler != null) {
                // mHandler.sendEmptyMessage(0);
                // } else {
                // if (isCallBack) {
                // if (mWebView.isloadOver) {
                // if (mHandler != null)
                // mHandler.sendEmptyMessage(0);
                // }
                // }
                // }
                // isCallBack = true;
                return h < height ? height : h;
            }
        });

        loadView();
    }

    /**
     * 刷新界面
     */
    private void loadView() {
        if (mProtocalData != null) {
            if (mProtocalData.Code == 1 && !TextUtils.isEmpty(mProtocalData.Content)) {
                mWebView.loadDataWithBaseURL("", mProtocalData.Content, "text/html", "UTF-8", "");
            }
            // else {
            // mWebView.loadUrl("file:///android_asset/index.html");
            // }
        }
        // else {
        // mWebView.loadUrl("file:///android_asset/index.html");
        // }
    }

    /**
     * 获取协议data
     */
    private void getProtocalData() {
        JSONObject jsonObject = new JSONObject();
        short commandid = 0;
        try {
            jsonObject.put("ReqType", ReqType);
            jsonObject.put("ProtocolType", ProtocolType);
            JSONArray FindList = new JSONArray();
            JSONObject FindListObj = new JSONObject();

            FindListObj.put("Type", Type);
            FindListObj.put("Id", Id);
            if (ClassType != -1) {
                FindListObj.put("ClassType", ClassType);
                commandid = CommandCodeTS.CMD_PROTOCOL_TRAIN;
            } else {
                commandid = CommandCodeTS.CMD_PROTOCOL_REG;
            }
            FindList.put(FindListObj);
            jsonObject.put("FindList", FindList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(commandid, jsonObject.toString(), this);
    }

    private void parseProtocolData(String result) {
        mProtocalData = JSON.parseObject(result, BaseResponseStatusData.class);
        loadView();
    }

    /**
     * 接受回滚定位option控件
     *
     * @param view
     */
    public void setScrollBackView(View view) {
        this.mOptionView = view;
    }

    @Override
    public void onDestroyView() {
        if (mWebView != null) {
            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            super.onDestroyView();
            mWebView.removeAllViews();
            mWebView.destroy();
        } else {
            super.onDestroyView();
        }
    }

}
