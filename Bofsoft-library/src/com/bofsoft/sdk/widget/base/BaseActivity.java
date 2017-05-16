package com.bofsoft.sdk.widget.base;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bofsoft.laio.common.ActivityMgr;
import com.bofsoft.laio.common.ConstAll;
import com.bofsoft.laio.common.ScreenObserver;
import com.bofsoft.laio.common.ScreenObserver.ScreenStateListener;
import com.bofsoft.laio.tcp.Client;
import com.bofsoft.sdk.R;
import com.bofsoft.sdk.config.BaseSysConfig;
import com.bofsoft.sdk.widget.button.BaseButton;
import com.bofsoft.sdk.widget.button.CustomView;
import com.bofsoft.sdk.widget.loadfailed.DurianLoadingNew;

import java.util.List;

@SuppressLint("RtlHardcoded")
public abstract class BaseActivity extends FragmentActivity {

    private RelativeLayout _mainView;
    private LinearLayout _headView;
    private RelativeLayout _bodyView;
    private LinearLayout _leftBtnLayout;
    private LinearLayout _rightBtnLayout;
    private RelativeLayout _titleLayout;
    private View _view;
    private TextView _titleText;
    private boolean _haveActionbar = true;
    public static boolean Home_Power = false;
    public static boolean isActive = true;
    private CallBack fragmentCallBack;
    private ScreenObserver mScreenObserver;
    private CallBack selfCallBack = new CallBack() {

        @Override
        public void callBack(int id, View v, Event e) {
            actionBarButtonCallBack(id, v, e);
        }
    };
    private DurianLoadingNew _dl_loading;

    /**
     * @param savedInstanceState
     * @param haveActiobar       是否加载actionbar
     */
    protected void onCreate(Bundle savedInstanceState, boolean haveActiobar) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) { //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }
        ActivityMgr.addActivity(this);
        init(haveActiobar);
        setTitleFunc();
        setActionBarButtonFunc();
        initAction();
        mScreenObserver = new ScreenObserver(this);
        mScreenObserver.requestScreenStateUpdate(new ScreenStateListener() {
            @Override
            public void onScreenOn() {
                doSomethingOnScreenOn();
            }

            @Override
            public void onScreenOff() {
                doSomethingOnScreenOff();
            }
        });
    }

    private void doSomethingOnScreenOn() {

    }

    private void doSomethingOnScreenOff() {
        if (isActive) {
            Client.state = ConstAll.STATE_CONNECT_FAILED;
            if (Client.mRecThread != null) {
                Client.mRecThread.interrupt();
                Client.mRecThread = null;
            }
            if (Client.mSendThread != null) {
                Client.mSendThread.interrupt();
                Client.mSendThread = null;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 默认为false 这样旧代码就不用单独处理 但新写的必须用 onCreate(savedInstanceState, true)
        this.onCreate(savedInstanceState, false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isActive) {
            //app 从后台唤醒，进入前台
            isActive = true;
            Client.state = ConstAll.STATE_CONNECT_FAILED;
            if (Client.mRecThread != null) {
                Client.mRecThread.interrupt();
                Client.mRecThread = null;
            }
            if (Client.mSendThread != null) {
                Client.mSendThread.interrupt();
                Client.mSendThread = null;
            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Home_Power = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Home_Power = false;

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isAppOnForeground()) {
            //app 进入后台  
            isActive = false;//全局变量isActive = false 记录当前已经进入后台
        }
    }

    /**
     * 程序是否在前台运行
     *
     * @return
     */
    public boolean isAppOnForeground() {

        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        String packageName = getApplicationContext().getPackageName();

        List<RunningAppProcessInfo> appProcesses = activityManager
                .getRunningAppProcesses();
        if (appProcesses == null)
            return false;

        for (RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(packageName)
                    && appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止监听screen状态
        if (mScreenObserver != null) {
            mScreenObserver.stopScreenStateUpdate();
        }
        if (_dl_loading != null) {
            _dl_loading.release();
        }

    }

    @Override
    public void setContentView(int layoutResID) {
        this.setContentView(LayoutInflater.from(this).inflate(layoutResID, null));
    }

    @Override
    public void setContentView(View view) {
        _view = view;
        if (_bodyView.getChildCount() > 0) {
            _bodyView.removeAllViews();
        }
        _bodyView
                .addView(_view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setContentView(View view, LayoutParams params) {
        _view = view;
        if (_bodyView.getChildCount() > 0) {
            _bodyView.removeAllViews();
        }
        _bodyView
                .addView(_view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void finish() {
        super.finish();
        if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5)
            overridePendingTransition(BaseSysConfig.activityAnimLeftInResId,
                    BaseSysConfig.activityAnimRightOutResId);
    }

    /**
     * 动作
     */
    @SuppressWarnings("deprecation")
    private void initAction() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) > 5)
            overridePendingTransition(BaseSysConfig.activityAnimRightInResId,
                    BaseSysConfig.activityAnimLeftOutResId);
    }

    public void init(boolean haveActiobar) {
        this._haveActionbar = haveActiobar;
        if (_headView != null) {
            if (_haveActionbar) {
                _headView.setVisibility(View.VISIBLE);
            } else {
                _headView.setVisibility(View.GONE);
            }
        }
        initView();
        initHead();
        initBody();
    }

    /**
     * 初使页面
     */
    @SuppressWarnings("ResourceType")
    private void initView() {
        if (_mainView != null)
            return;
//    _mainView = new RelativeLayout(this);
        _mainView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.mainview, null);
        _mainView.setId(1);
        super.setContentView(_mainView, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
    }

    /**
     * 初使头部
     */
    @SuppressWarnings("ResourceType")
    private void initHead() {
        if (_headView != null || !_haveActionbar)
            return;
        _headView = new LinearLayout(this);
        _headView.setId(2);
        _headView.setOrientation(LinearLayout.HORIZONTAL);
        if (BaseSysConfig.actionBarDrawableResources == 0) {
            setActionBarBackgroundColor(BaseSysConfig.actionBarColor);
        } else {
            setActionBarBackgroundResource(BaseSysConfig.actionBarDrawableResources);
        }
        _headView.addView(_leftBtnLayout = new LinearLayout(this), new LinearLayout.LayoutParams(0,
                LayoutParams.MATCH_PARENT, 1));
        _headView.addView(_titleLayout = new RelativeLayout(this), new LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        _headView.addView(_rightBtnLayout = new LinearLayout(this), new LinearLayout.LayoutParams(0,
                LayoutParams.MATCH_PARENT, 1));
        _mainView.addView(_headView, new LayoutParams(LayoutParams.MATCH_PARENT,
                BaseSysConfig.actionBarHeight));
        _leftBtnLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        _leftBtnLayout.setOrientation(LinearLayout.HORIZONTAL);
        _titleLayout.setGravity(Gravity.CENTER);
        _rightBtnLayout.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        _rightBtnLayout.setOrientation(LinearLayout.HORIZONTAL);
    }

    /**
     * 初使内容
     */
    @SuppressWarnings("ResourceType")
    private void initBody() {
        if (_bodyView != null)
            return;
        _mainView.addView(_bodyView = new RelativeLayout(this), new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        _bodyView.setId(4);
        RelativeLayout.LayoutParams lp =
                (android.widget.RelativeLayout.LayoutParams) _bodyView.getLayoutParams();
        lp.addRule(RelativeLayout.BELOW, 2);
        _bodyView.setLayoutParams(lp);

        _dl_loading = (DurianLoadingNew) LayoutInflater.from(this).inflate(R.layout.durainloadingnew, null);
        _mainView.addView(_dl_loading);
        _dl_loading.setId(3);
        lp = (android.widget.RelativeLayout.LayoutParams) _dl_loading.getLayoutParams();
        lp.addRule(RelativeLayout.BELOW, 2);
        _dl_loading.setLayoutParams(lp);
        _dl_loading.showLoadUi(true, 0);
    }

    /**
     * @param tf true 撑满头部 false 头部居中
     */
    public void setTitleFill(boolean tf) {
        if (_leftBtnLayout == null || _rightBtnLayout == null || _titleLayout == null)
            return;
        if (tf) {
            LinearLayout.LayoutParams lp;
            lp = (LinearLayout.LayoutParams) _leftBtnLayout.getLayoutParams();
            lp.weight = 0;
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp = (LinearLayout.LayoutParams) _titleLayout.getLayoutParams();
            lp.weight = 1;
            lp.width = 0;
            lp = (LinearLayout.LayoutParams) _rightBtnLayout.getLayoutParams();
            lp.weight = 0;
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        } else {
            LinearLayout.LayoutParams lp;
            lp = (LinearLayout.LayoutParams) _leftBtnLayout.getLayoutParams();
            lp.weight = 1;
            lp.width = 0;
            lp = (LinearLayout.LayoutParams) _titleLayout.getLayoutParams();
            lp.weight = 1;
            lp.width = LinearLayout.LayoutParams.WRAP_CONTENT;
            lp = (LinearLayout.LayoutParams) _rightBtnLayout.getLayoutParams();
            lp.weight = 1;
            lp.width = 0;
        }
    }

    /**
     * 设置标题回调方法
     */
    protected abstract void setTitleFunc();

    /**
     * 设置标题栏按钮方法
     */
    protected abstract void setActionBarButtonFunc();

    /**
     * 标题栏点击回调方法
     */
    protected abstract void actionBarButtonCallBack(int id, View v, Event e);

    public void setActionBarBackgroundColor(int color) {
        if (_headView != null)
            _headView.setBackgroundColor(Color.WHITE);
    }

    public void setActionBarBackgroundResource(int resid) {
        if (_headView != null)
            _headView.setBackgroundResource(resid);
    }

    public void setTitle(CharSequence title) {
        this.setTitle(title, null, null);
    }

    public View setTitle(CharSequence title, Integer titleSize, Integer titleColor) {
        if (!_haveActionbar)
            return _titleText;
        if (_titleText == null) {
            _titleLayout.removeAllViews();
            _titleText = new TextView(this);
            _titleText.setSingleLine(true);
        }
        if (_titleText.getParent() == null)
            _titleLayout.addView(_titleText, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        if (title != null)
            if (title.length() > 10) {
                title = title.subSequence(0, 10) + "...";
            }
        _titleText.setText(title);
        if (titleSize == null)
            _titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, BaseSysConfig.actionBarTitleSize);
        else
            _titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        if (titleColor == null)
            _titleText.setTextColor(BaseSysConfig.actionBarTitleColor);
        else
            _titleText.setTextColor(titleColor);
        return _titleText;
    }

    public View setTitle(String title) {
        return this.setTitle(title, null, null);
    }

    public View setTitle(String title, Integer titleSize, Integer titleColor) {
        if (!_haveActionbar)
            return _titleText;
        if (_titleText == null) {
            if (_titleLayout != null) {
                _titleLayout.removeAllViews();
            }
            _titleText = new TextView(this);
            _titleText.setSingleLine(true);
        }
        if (_titleText.getParent() == null)
            _titleLayout.addView(_titleText, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
        if (title != null)
            if (title.length() > 7) {
                title = title.subSequence(0, 7) + "...";
            }
        _titleText.setText(title);
        if (titleSize == null)
            _titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, BaseSysConfig.actionBarTitleSize);
        else
            _titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleSize);
        if (titleColor == null)
            _titleText.setTextColor(BaseSysConfig.actionBarTitleColor);
        else
            _titleText.setTextColor(titleColor);
        return _titleText;
    }

    public View setTitle(View v) {
        return this.setTitle(v, null);
    }

    public View setTitle(View v, LayoutParams lp) {
        if (!_haveActionbar)
            return v;
        _titleLayout.removeAllViews();
        if (lp != null)
            _titleLayout.addView(v, lp);
        else
            _titleLayout.addView(v);
        return v;
    }

    public void clearTitle() {
        if (!_haveActionbar)
            return;
        if (_titleLayout != null)
            _titleLayout.removeAllViews();
    }

    public View addLeftButton(BaseButton btn) {
        return addLeftButton(-1, btn);
    }

    public View addLeftButton(int id, BaseButton btn) {
        if (btn.getParent() == null && _haveActionbar) {
            if (id != -1)
                btn.setId(id);
            _leftBtnLayout.addView(btn);
            setTouchEvent(btn);
        }
        return btn;
    }

    public View addRightButton(BaseButton btn) {
        return addRightButton(-1, btn);
    }

    public View addRightButton(int id, BaseButton btn) {
        if (btn.getParent() == null && _haveActionbar) {
            if (id != -1)
                btn.setId(id);
            _rightBtnLayout.addView(btn, 0);
            setTouchEvent(btn);
        }
        return btn;
    }

    public View addLeftView(View v) {
        return addLeftView(-1, v);
    }

    public View addLeftView(int id, View v) {
        CustomView cv = null;
        if (v.getParent() == null && _haveActionbar) {
            cv = new CustomView(this, v);
            if (id != -1)
                cv.setId(id);
            _leftBtnLayout.addView(cv);
            setTouchEvent(v);
        }
        return cv;
    }

    public View addRightView(View v) {
        return addRightView(-1, v);
    }

    public View addRightView(int id, View v) {
        CustomView cv = null;
        if (v.getParent() == null && _haveActionbar) {
            cv = new CustomView(this, v);
            if (id != -1)
                cv.setId(id);
            _rightBtnLayout.addView(cv, 0);
            setTouchEvent(v);
        }
        return cv;
    }

    private void setTouchEvent(View v) {
        v.setOnClickListener(btnClickListener);
    }

    private OnClickListener btnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            selfCallBack.callBack(v.getId(), v, Event.CLICK);
            if (fragmentCallBack != null)
                fragmentCallBack.callBack(v.getId(), v, Event.CLICK);
        }
    };

    public void removeActionBarButton(View btn) {
        if (btn.getParent() != null)
            ((LinearLayout) btn.getParent()).removeView(btn);
    }

    public void clearActionButton() {
        if (!_haveActionbar)
            return;
        if (_leftBtnLayout != null)
            _leftBtnLayout.removeAllViews();
        if (_rightBtnLayout != null)
            _rightBtnLayout.removeAllViews();
    }

    public enum ACTIONBAR_STYLE {
        RELATIVE, LINEAR
    }

    /**
     * 设置ActionBar与Body的布局模式
     */
    public void setActionBarStyle(ACTIONBAR_STYLE style) {
        RelativeLayout.LayoutParams lp =
                (android.widget.RelativeLayout.LayoutParams) _bodyView.getLayoutParams();
        if (style == ACTIONBAR_STYLE.RELATIVE)
            lp.addRule(RelativeLayout.BELOW, 0);
        else
            lp.addRule(RelativeLayout.BELOW, 2);
        _bodyView.setLayoutParams(lp);
    }

    public void hideActionBar() {
       if (!_haveActionbar)
            return;
        _headView.setVisibility(View.GONE);
    }

    public void showActionBar() {
        if (!_haveActionbar)
           return;
       _headView.setVisibility(View.VISIBLE);
  }

    public View getRoot() {
        return _mainView;
    }

    public View getView() {
        return _view;
    }

    public View getHead() {
        return _headView;
    }

    public View getBody() {
        return _bodyView;
    }

    public int getDpi() {
        return BaseSysConfig.getDpi(this);
    }

    public int getScreenWidth() {
        return BaseSysConfig.getWidth(this);
    }

    public int getScreenHeight() {
        return BaseSysConfig.getHeight(this);
    }

    public int getHeadWidth() {
        return BaseSysConfig.getWidth(this);
    }

    public int getHeadHeight() {
        return BaseSysConfig.actionBarHeight;
    }

    public void setCallBack(CallBack callback) {
        this.fragmentCallBack = callback;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        FragmentManager fm = getSupportFragmentManager();
        int index = requestCode >> 16;
        if (index != 0) {
            index--;
            if (fm.getFragments() == null || index < 0
                    || index >= fm.getFragments().size()) {
//        Log.w(TAG, "Activity result fragment index out of range: 0x"
//                + Integer.toHexString(requestCode));
                return;
            }
            Fragment frag = fm.getFragments().get(index);
            if (frag == null) {
//        Log.w(TAG, "Activity result no fragment exists for index: 0x"
//                + Integer.toHexString(requestCode));
            } else {
                handleResult(frag, requestCode, resultCode, data);
            }
            return;
        }

    }

    /**
     * 递归调用，对所有子Fragement生效
     *
     * @param frag
     * @param requestCode
     * @param resultCode
     * @param data
     */
    private void handleResult(Fragment frag, int requestCode, int resultCode,
                              Intent data) {
        frag.onActivityResult(requestCode & 0xffff, resultCode, data);
        List<Fragment> frags = frag.getChildFragmentManager().getFragments();
        if (frags != null) {
            for (Fragment f : frags) {
                if (f != null)
                    handleResult(f, requestCode, resultCode, data);
            }
        }
    }

    public void LoadingData(boolean isStopAnim, int strtype) {
        if (_dl_loading != null) {
            _dl_loading.showLoadUi(isStopAnim, strtype);
        }
        if (isStopAnim) {
            _bodyView.setVisibility(View.VISIBLE);
        } else {
            _bodyView.setVisibility(View.GONE);
        }
    }

    public void showReloadUi() {
        if (_dl_loading != null) {
            _dl_loading.showReloadUi();
            _bodyView.setVisibility(View.GONE);
        }
    }

    public TextView getReloadTextView() {
        if (_dl_loading != null) {
            return _dl_loading.getTextView();
        } else {
            return null;
        }
    }
}
