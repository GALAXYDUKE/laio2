package com.bofsoft.sdk.widget.base;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.bofsoft.sdk.widget.base.BaseActivity.ACTIONBAR_STYLE;
import com.bofsoft.sdk.widget.button.BaseButton;

import java.util.ArrayList;
import java.util.List;


@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment {

    private BaseActivity _act;
    private View _view;
    private List<View> _leftButtons = new ArrayList<View>();
    private List<View> _rightButtons = new ArrayList<View>();
    private View _titleView;
    private View _titleText;
    @SuppressWarnings("unused")
    private String _titleStr;
    private CharSequence _titleSeqence;
    private Integer _titleSize;
    private Integer _titleColor;
    private int _titleType = -1;
    private boolean fillTitle = false;
    private boolean changeActionbar = true;
    private CallBack ac = new CallBack() {

        @Override
        public void callBack(int id, View v, Event e) {
            actionBarButtonCallBack(id, v, e);
        }
    };

    public void onCreate(Bundle savedInstanceState, boolean haveActionbar, boolean changeActionbar) {
        super.onCreate(savedInstanceState);
        this.changeActionbar = changeActionbar;
        init(haveActionbar);
    }

    public void onCreate(Bundle savedInstanceState, boolean haveActionbar) {
        super.onCreate(savedInstanceState);
        this.changeActionbar = true;
        init(haveActionbar);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.changeActionbar = true;
        init(true);
    }

    public void init(boolean haveActiobar) {
        _act = (BaseActivity) getActivity();
        //不改变头部
        if (!changeActionbar) return;
       // _act.setCallBack(ac);
        _act.init(haveActiobar);
        clearTitle();
        clearActionButton();
       // _act.setTitleFill(fillTitle);
        if (_view == null) {
            setTitleFunc();
            setActionBarButtonFunc();
        } else {
            if (_titleText != null)
                if (_titleType == 1)
                    _act.setTitle(_titleSeqence);
                else if (_titleType == 2)
                    _act.setTitle(_titleSeqence, _titleSize, _titleColor);
                else if (_titleType == 3)
                    _act.setTitle(_titleSeqence);
                else if (_titleType == 4)
                    _act.setTitle(_titleSeqence, _titleSize, _titleColor);
            if (_titleView != null)
                _act.setTitle(_titleView, _titleView.getLayoutParams());
            for (int i = 0; i < _leftButtons.size(); i++) {
                View v = _leftButtons.get(i);
                if (v instanceof BaseButton) {
                    //_act.addLeftButton(v.getId(), (BaseButton) v);
                } else {
                   // _act.addLeftView(v.getId(), v);
                }
            }
            for (int i = 0; i < _rightButtons.size(); i++) {
                View v = _rightButtons.get(i);
                if (v instanceof BaseButton) {
                   // _act.addRightButton(v.getId(), (BaseButton) v);
                } else {
                  //  _act.addRightView(v.getId(), v);
                }
            }
        }
    }

    /**
     * @param tf true 撑满头部 false 头部居中
     */
  /*  public void setTitleFill(boolean tf) {
        fillTitle = tf;
        _act.setTitleFill(tf);
    }
*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return _view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (_view != null && _view.getParent() != null) {
            ((ViewGroup) _view.getParent()).removeView(_view);
        }
    }

    public void setContentView(int layoutResID) {
        if (this._view != null)
            return;
        this.setContentView(LayoutInflater.from(_act).inflate(layoutResID, null));
    }

    public void setContentView(View view) {
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        _view = view;
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
        _act.setActionBarBackgroundColor(color);
    }

    public void setActionBarBackgroundResource(int resid) {
        _act.setActionBarBackgroundResource(resid);
    }

    public View setTitle(CharSequence title) {
        _titleType = 1;
        _titleSeqence = title;
        return _titleText = _act.setTitle(title, null, null);
    }

    public View setTitle(CharSequence title, Integer titleSize, Integer titleColor) {
        _titleType = 2;
        _titleSeqence = title;
        _titleSize = titleSize;
        _titleColor = titleColor;
        return _titleText = _act.setTitle(title, titleSize, titleColor);
    }

    public View setTitle(String title) {
        _titleType = 3;
        _titleSeqence = title;
        return _titleText = _act.setTitle(title, null, null);
    }

    public View setTitle(String title, Integer titleSize, Integer titleColor) {
        _titleType = 4;
        _titleSeqence = title;
        _titleSize = titleSize;
        _titleColor = titleColor;
        return _titleText = _act.setTitle(title, titleSize, titleColor);
    }

    public View setTitle(View v) {
        return _titleView = _act.setTitle(v, null);
    }

    public View setTitle(View v, LayoutParams lp) {
        return _titleView = _act.setTitle(v, lp);
    }

    public void clearTitle() {
        _act.clearTitle();
    }

    public View addLeftButton(BaseButton btn) {
        return addLeftButton(-1, btn);
    }

    public View addLeftButton(int id, BaseButton btn) {
        View v = _act.addLeftButton(id, btn);
        _leftButtons.add(v);
        return v;
    }

   public View addRightButton(BaseButton btn) {
        return addRightButton(-1, btn);
    }

    public View addRightButton(int id, BaseButton btn) {
        View v = _act.addRightButton(id, btn);
        _rightButtons.add(v);
        return v;
    }

    public View addLeftView(View v) {
        return addLeftView(-1, v);
    }

    public View addLeftView(int id, View v) {
        View _v = _act.addLeftView(v);
        _leftButtons.add(_v);
        return _v;
    }

    public View addRightView(View v) {
        return addRightView(-1, v);
    }

    public View addRightView(int id, View v) {
        View _v = _act.addRightView(id, v);
        _rightButtons.add(_v);
        return _v;
    }

    public void removeActionBarButton(View btn) {
        _act.removeActionBarButton(btn);
    }

    public void clearActionButton() {
        _act.clearActionButton();
    }

    /**
     * 设置ActionBar与Body的布局模式
     */
   public void setActionBarStyle(ACTIONBAR_STYLE style) {
        _act.setActionBarStyle(style);
    }

    public void hideActionBar() {
       _act.hideActionBar();
    }

    public void showActionBar() {
        _act.showActionBar();
    }

    public Resources getResource() {
        return _act.getResources();
    }

    public View getView() {
        return _view;
    }

/*    public View getHead() {
        return _act.getHead();
    }

    public View getBody() {
        return _act.getBody();
    }*/

    public int getDpi() {
        return _act.getDpi();
    }

    public int getScreenWidth() {
        return _act.getScreenWidth();
    }

    public int getScreenHeight() {
        return _act.getScreenHeight();
    }

    public int getHeadWidth() {
        return _act.getHeadWidth();
    }

    public int getHeadHeight() {
        return _act.getHeadHeight();
    }

    public void LoadingData(boolean isStopAnim, int strtype) {
        //_act.LoadingData(isStopAnim, strtype);
    }

   /* public void showReloadUi() {
        _act.showReloadUi();
    }

    public TextView getReloadTextView() {
        return _act.getReloadTextView();
    }*/

}
