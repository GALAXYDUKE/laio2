package com.bofsoft.laio.customerservice.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.MyLog;
import com.bofsoft.laio.customerservice.Activity.BaseFragment;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.JasonScrollView;
import com.bofsoft.laio.customerservice.Widget.JasonWebView;
import com.bofsoft.laio.tcp.DataLoadTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 培训产品详情fragment
 *
 * @author Administrator
 */
public class FragmentProductDetail extends BaseFragment {
    MyLog myLog = new MyLog(getClass());
    public JasonWebView webView;
    private View mOptionView;
    private String mProDetail;
    // private Handler sendBackMsg;
    // private boolean isCallBack = false;
    private boolean isLoadingData = false; // 是否正在加载数据中

    @Override
    public void messageBack(int code, String result) {
        super.messageBack(code, result);
        switch (code) {
            case CommandCodeTS.CMD_ONECOACHTRAINPROVIEW_STU: // //学员端_查看某教练某类可购买产品按日期产品详情预览
                parseProductDetailsByDay(result);
                break;
            default:
                break;
        }

    }

    @Override
    public void messageBack(int code, int lenght, int tcplenght) {

    }

    @Override
    public void messageBackFailed(int errorCode, String error) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getActivity()).inflate(R.layout.activity_pro_intro, container, false);
        webView = (JasonWebView) view.findViewById(R.id.weibo);
        webView.setJasonWebViewBack(new JasonWebView.IJasonWebViewBack() {
            @Override
            public int onLoadOverAndChangeSize(View v, int h) {
                int parentH = JasonScrollView.getScrollParentH(mOptionView);
                int opHeight = 0;
                if (mOptionView != null) {
                    opHeight = mOptionView.getHeight();
                }
                int height = parentH - opHeight;

                myLog.i("----------parentH|" + parentH + ">>>opHeight|" + opHeight);
                // if (height < h && sendBackMsg != null) {
                // sendBackMsg.sendEmptyMessage(1);
                // } else {
                // if (isCallBack && webView.isloadOver && sendBackMsg != null)
                // {
                // sendBackMsg.sendEmptyMessage(1);
                // }
                // }
                // isCallBack = true;
                return h < height ? height : h;
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        loadView();
    }

    /**
     * 获取教练数据
     *
     * @param productDetails 产品详情
     */
    public void loadData(String productDetails) {
        this.mProDetail = productDetails;
        loadView();
    }

    /**
     * 加载培训产品详情
     *
     * @param proType
     * @param coachUserUUID
     * @param proDate
     * @param orderId
     */
    public void loadTrainData(int proType, String coachUserUUID, String proDate, int orderId) {
        if (mProDetail == null) {
            getProductDetailsByDay(proType, coachUserUUID, proDate, orderId);
        } else {
            loadView();
        }
    }

    private void loadView() {
        if (!TextUtils.isEmpty(mProDetail) && webView != null) {
            webView.loadDataWithBaseURL("", mProDetail, "text/html", "utf-8", "");
        }
    }

    /**
     * 获取某教练某类可购买产品按日期产品详情预览
     */
    public void getProductDetailsByDay(int proType, String coachUserUUID, String proDate, int orderId) {
        if (isLoadingData) {
            return;
        }
        isLoadingData = true;
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ProType", proType);
            jsonObject.put("CoachUserUUID", coachUserUUID);
            jsonObject.put("ProDate", proDate);
            jsonObject.put("OrderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_ONECOACHTRAINPROVIEW_STU,
                jsonObject.toString(), this);

    }

    /**
     * 解析某教练某类可购买产品按日期产品详情预览
     *
     * @param result
     */
    private void parseProductDetailsByDay(String result) {
        isLoadingData = false;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(result);
            mProDetail = jsonObject.getString("Intro");
            loadView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        // if (!hidden) {
        // if (sendBackMsg != null)
        // sendBackMsg.sendEmptyMessage(8);
        // }
    }

    /**
     * 接受从外面发送过来的回滚(option)控件
     *
     * @param view
     */
    public void setScrollBackView(View view) {
        mOptionView = view;
    }

    @Override
    public void onDestroyView() {
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

}
