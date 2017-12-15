package com.xgn.vlv.client.base;

import com.umeng.analytics.MobclickAgent;
import com.xgn.vly.client.commonui.dialog.DialogMaker;
import com.xgn.vly.client.commonui.dialog.LoadingDialog;
import com.xgn.vly.client.commonui.view.ToolbarTool;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * 基础Activity
 * Created by Administrator on 2016/12/14.
 */

public class BaseActivity extends FragmentActivity {

    public ToolbarTool mToolbarTool;
    private ViewGroup mRootView;
    /**
     * 网络加载Dialog
     */
    public Dialog loadingDialog;
    /**
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 将打开的 Activity 收集起来，当注销时全部 finish 掉
        ActivityCollector.addActivity(this);
        if (null == mToolbarTool) {
            mToolbarTool = new ToolbarTool(this);
        }
        loadingDialog = new LoadingDialog(this);
    }

    public View getActionBarView() {
        if (null != mToolbarTool) {
            return mToolbarTool.getHeadView();
        }
        return null;
    }

    protected void hideLine(boolean hide) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.hideLine(hide);
    }

    protected void setActionBarBackgroundColor(int res) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setBackgroundColor(res);
    }

    protected void initToolbar(ViewGroup rootView, ToolbarTool.OnBackClickListener listener) {
        if (null == mToolbarTool)
            return;
        mRootView = rootView;
        ToolbarTool.OnBackClickListener backClickListener = null;
        if (null == listener) {
            backClickListener = new ToolbarTool.OnBackClickListener() {
                @Override
                public void onBackBtnPressed() {
                    onBackPressed();
                }
            };
        } else {
            backClickListener = listener;
        }
        mToolbarTool.initToolbar(backClickListener, rootView);
    }

    public void hideBackView() {
        if (null == mToolbarTool)
            return;
        mToolbarTool.hideBackView();
    }

    public void showBackView() {
        if (null == mToolbarTool)
            return;
        mToolbarTool.showBackView();
    }

    public void setBackIcon(int resid) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setBackIcon(resid);
    }

    public void setBackText(String str) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setBackText(str);
    }

    public void setRightIconListener(View.OnClickListener listener) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setRightIconListener(listener);
    }

    public void setRightTextListener(View.OnClickListener listener) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setRightTextListener(listener);
    }

    public void setRightIcon(int resid) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setRightIcon(resid);
    }

    public void setRightTextRightIcon(int resid) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setBackTextImage(resid);
    }

    public void setRightText(String text) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setRightText(text);
    }

    public void setRightText(@StringRes int resId) {
        setRightText(getString(resId));
    }

    public void hideRight() {
        if (null == mToolbarTool)
            return;
        mToolbarTool.hideRight();
    }

    public void hideRightIcon() {
        if (null == mToolbarTool)
            return;
        mToolbarTool.hideRightIcon();
    }

    /**
     * 隐藏右边的文字
     */
    public void hideRightText() {
        if (null == mToolbarTool)
            return;
        mToolbarTool.hideRightText();
    }

    public void setRightTextColor(int color) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setRightTextColor(color);
    }

    public void setRightTextColor(ColorStateList colorStateList) {
        if (null != mToolbarTool) {
            mToolbarTool.setRightTextColor(colorStateList);
        }
    }

    public void setTitle(String title) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setTitle(title);
    }

    public void setTitle(@StringRes int resId) {
        setTitle(getString(resId));
    }

    public void setTitleIcon(int res) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setTitleIcon(res);
    }

    public void setTitleListener(View.OnClickListener clickListener) {
        if (null == mToolbarTool)
            return;
        mToolbarTool.setTitleListener(clickListener);
    }

    public void onBackPressed() {
        super.onBackPressed();
        this.showKeyboard(false);
    }

    /**
     * 显示或隐藏输入法
     *
     * @param isShow
     */
    protected void showKeyboard(boolean isShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (isShow) {
            if (getCurrentFocus() == null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            } else {
                imm.showSoftInput(getCurrentFocus(), 0);
            }
        } else {
            if (getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void showProgress(final boolean show) {
        if (show) {
            DialogMaker.showProgressDialog(this, null, false);
        } else {
            DialogMaker.dismissProgressDialog();
        }
    }

    /**
     * 可以取消
     *
     * @param show
     * @param cancelable 转圈是否可以取消
     */
    public void showProgress(final boolean show, final boolean cancelable) {
        if (show) {
            DialogMaker.showProgressDialog(this, null, cancelable);
        } else {
            DialogMaker.dismissProgressDialog();
        }
    }

    /**
     * 是否显示标题箭头
     *
     * @param show
     */
    public void showTitleArrow(final boolean show) {
        if (null != mToolbarTool) {
            mToolbarTool.showTitleArrow(show);
        }

    }

    /**
     * 是否收起
     *
     * @param isPackUp true:收起
     */
    public void isArrowPackUp(boolean isPackUp) {
        if (null != mToolbarTool) {
            mToolbarTool.isArrowPackUp(isPackUp);
        }
    }

    /**
     * 设置StatusBar
     */
    protected void setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4及以上才支持
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    /**
     * 设置StatusBar,并且加背景颜色
     *
     * @param statusBar
     * @param color
     */
    protected void setStatusBar(View statusBar, int color) {
        if (statusBar != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4及以上才支持
                Window w = getWindow();
                w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                //status bar height
                int actionBarHeight = 0;
                int statusBarHeight = getStatusBarHeight();
                //action bar height
                statusBar.getLayoutParams().height = actionBarHeight + statusBarHeight;
                statusBar.setBackgroundColor(color);
            }
        }
    }

    /**
     * 获取statusBar的高度
     *
     * @return
     */
    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    protected void onDestroy() {
        if (null != mToolbarTool && null != mRootView) {
            mToolbarTool.destoryToolBar(mRootView);
        }
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        //TODO 加一些埋点
//        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //TODO 加一些埋点
//        MobclickAgent.onResume(this);
    }

    public void toActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void showLoadingDialog() {
        if (loadingDialog != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }
}
