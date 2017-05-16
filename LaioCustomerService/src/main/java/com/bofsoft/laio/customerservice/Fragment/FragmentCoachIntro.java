package com.bofsoft.laio.customerservice.Fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bofsoft.laio.common.CommandCodeTS;
import com.bofsoft.laio.common.ConfigAll;
import com.bofsoft.laio.customerservice.Activity.BaseFragment;
import com.bofsoft.laio.customerservice.Common.ImageLoaderUtil;
import com.bofsoft.laio.customerservice.Config.ConfigallCostomerService;
import com.bofsoft.laio.customerservice.DataClass.Order.CoachInfoData;
import com.bofsoft.laio.customerservice.DataClass.Order.GetMyOtherInfoData;
import com.bofsoft.laio.customerservice.R;
import com.bofsoft.laio.customerservice.Widget.WidgetStarLevel;
import com.bofsoft.laio.tcp.DataLoadTask;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 教练介绍
 *
 * @author yedong
 */
public class FragmentCoachIntro extends BaseFragment {

    // private MyLog mylog = new MyLog(getClass());
    // private JasonWebView webView;
    private CoachInfoData mCoachInfoData; // 教练信息
    private GetMyOtherInfoData mCoachIntroData; // 教练介绍
    private TextView mTxtSchoolName;
    private TextView mTxtCoachName;
    private TextView mTxtTrainCarType;
    private TextView mTxtTrainCarModel;
    // private TextView mIntro;
    private WidgetStarLevel mStarRecommand;
    private WidgetStarLevel mStarCredit;
    private ImageView mImgCoachPic;

    // private LinearLayout topView;
    // private View mOptionView;
    // public Handler sendBackMsg;
    private String MasterUUID;
    private CoachInfoCallBack coachInfoCallBack;

    @Override
    public void messageBack(int code, String result) {
        switch (code) {
            case CommandCodeTS.CMD_GETCOACHBASICINFO_INTF:
                parseCoachInfo(result);
                break;
            // case CommandCode.CMD_GET_COACH_INTRO_INFO:
            // parseCoachIntro(result);
            // break;
            default:
                break;
        }
    }

    public interface CoachInfoCallBack {
        void sendCity(String CityName, String DistrictName); // 用于
    }

    public void setCityBack(CoachInfoCallBack cityInfoCallBack) {
        this.coachInfoCallBack = cityInfoCallBack;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getActivity())
                        .inflate(R.layout.frangment_coach_intro, container, false);
        initView(view);
        return view;
    }

    public void initView(View view) {
        // topView = (LinearLayout) view.findViewById(R.id.topView);
        mTxtSchoolName = (TextView) view.findViewById(R.id.txtSchoolName);
        mTxtCoachName = (TextView) view.findViewById(R.id.txtCoachName);
        mTxtTrainCarType = (TextView) view.findViewById(R.id.txtTrainCarType);
        mTxtTrainCarModel = (TextView) view.findViewById(R.id.txtTrainCarModel);
        mStarRecommand = (WidgetStarLevel) view.findViewById(R.id.star_recommand);
        mStarCredit = (WidgetStarLevel) view.findViewById(R.id.star_credit);

        mImgCoachPic = (ImageView) view.findViewById(R.id.imgCoachPic);
        // mIntro = (TextView) view.findViewById(R.id.myIntro);

        // webView = (JasonWebView) view.findViewById(R.id.webview);
        // webView.getSettings().setDefaultFontSize(14);

        if (mCoachInfoData != null && mCoachIntroData != null) {
            loadView();
        } else {
            if (mCoachInfoData == null) {
                getCoachInfo();
            }
        }
        // webView.setJasonWebViewBack(new IJasonWebViewBack() {
        // @Override
        // public int onLoadOverAndChangeSize(View v, int h) {
        // int parentH = JasonScrollView.getScrollParentH(mOptionView);
        // int opHeight = 0;
        // if (mOptionView != null) {
        // opHeight = mOptionView.getHeight();
        // }
        // // int height = parentH - opHeight - topView.getHeight() -
        // mIntro.getHeight();
        // int height = parentH - opHeight - topView.getHeight();
        // if (webView.isloadOver) {
        // if (sendBackMsg != null)
        // sendBackMsg.sendEmptyMessage(0);
        // }
        // return h < height ? height : h;
        // }
        // });
    }

    /**
     * 刷新界面
     */
    private void loadView() {
        if (mCoachInfoData != null) {
            mTxtSchoolName.setText(mCoachInfoData.MasterSchoolName);
            mTxtCoachName.setText(mCoachInfoData.MasterName);
            mTxtTrainCarType.setText(mCoachInfoData.TrainCarType);
            mTxtTrainCarModel.setText(mCoachInfoData.TrainCarModel);
            mStarRecommand.setRecommendLevel(mCoachInfoData.MasterRecommendIndex);
            mStarCredit.setCreditLevel(mCoachInfoData.MasterCreditRank);
            ImageLoaderUtil.displayImage(mCoachInfoData.MasterPicURL, mImgCoachPic,
                    R.mipmap.icon_default_head);
        }

        if (coachInfoCallBack != null) {
            coachInfoCallBack.sendCity(mCoachInfoData.CityName, mCoachInfoData.DistrictName);
        }

        // if (webView != null) {
        // if (mCoachIntroData != null) {
        // if (!TextUtils.isEmpty(mCoachIntroData.getIntroduce())) {
        // webView.loadDataWithBaseURL("", mCoachIntroData.getIntroduce(),
        // "text/html",
        // "UTF-8", "");
        // } else {
        // webView.loadUrl("file:///android_asset/index.html");
        // }
        // }
        // }

    }

    /**
     * 获取教练数据
     *
     * @param masterUUID
     */
    public void loadData(String masterUUID) {
        this.MasterUUID = masterUUID;
        if (mCoachInfoData != null) {
            loadView();
        } else {
            getCoachInfo();
        }
    }

    /**
     * 获取教练信息
     */
    private void getCoachInfo() {
        if (TextUtils.isEmpty(MasterUUID)) {
            return;
        }
        JSONObject js = new JSONObject();
        try {
            js.put("ObjectType", ConfigallCostomerService.ObjectType);
            js.put("MasterUUID", ConfigAll.UserUUID);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        DataLoadTask.getInstance().loadData(CommandCodeTS.CMD_GETCOACHBASICINFO_INTF, js.toString(),
                this);
    }

    /**
     * 解析教练信息
     *
     * @param result
     */
    private void parseCoachInfo(String result) {
        mCoachInfoData = JSON.parseObject(result, CoachInfoData.class);
        if (mCoachInfoData != null) {
            loadView();
        }
    }

    // /**
    // * 获取教练自我介绍
    // */
    // public void getCoachIntro() {
    // GetMyOtherInfoData data = new GetMyOtherInfoData();
    // data.ObjectType = ConfigallTea.ObjectType;
    // data.MasterUUID = "";
    // DataLoadTask.getInstance().loadData(CommandCode.CMD_GET_COACH_INTRO_INFO,
    // data.getMyOtherInfoData(), this);
    // }
    //
    // /**
    // * 解析教练自我介绍
    // *
    // * @param result
    // */
    // public void parseCoachIntro(String result) {
    // try {
    // JSONObject jobj = new JSONObject(result);
    // mCoachIntroData = GetMyOtherInfoData.InitData(jobj);
    // if (mCoachIntroData != null) {
    // loadView();
    // }
    // } catch (JSONException e) {
    // e.printStackTrace();
    // }
    // }

    // /**
    // * 接受从外面发送过来的option控件
    // *
    // * @param view
    // */
    // public void setScrollBackView(View view) {
    // mOptionView = view;
    // }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        // if (webView != null) {
        // ViewGroup parent = (ViewGroup) webView.getParent();
        // if (parent != null) {
        // parent.removeView(webView);
        // }
        super.onDestroyView();
        // webView.removeAllViews();
        // webView.destroy();
        // } else {
        // super.onDestroyView();
        // }
    }

}
