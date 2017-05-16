package com.bofsoft.laio.customerservice.Activity;

import android.support.v4.app.Fragment;

import com.bofsoft.laio.tcp.IResponseListener;

// import com.umeng.analytics.MobclickAgent;

public class BaseFragment extends Fragment implements IResponseListener {

    private final String mPageName = getClass().getSimpleName();

    @Override
    public void messageBack(int code, String result) {

    }

    @Override
    public void messageBack(int code, int lenght, int tcplenght) {

    }

    @Override
    public void messageBackFailed(int errorCode, String error) {
        if (getActivity() != null) {
            try {
                ((BaseVehicleActivity) getActivity()).messageBackFailed(errorCode, error);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        // MobclickAgent.onPageStart(mPageName);
    }

    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        // MobclickAgent.onPageEnd(mPageName);
    }

}
