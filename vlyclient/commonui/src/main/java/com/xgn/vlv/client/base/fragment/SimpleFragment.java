package com.xgn.vlv.client.base.fragment;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.umeng.analytics.MobclickAgent;
import com.xgn.vlv.client.base.activity.SimpleFragmentActivity;

/**
 * Fragment基类
 * Created by tanghh on 2017/7/18.
 */
public abstract class SimpleFragment extends Fragment {

    /**
     * STATE_SAVE_IS_HIDDEN
     */
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    /**
     * 宿主Activity对象
     */
    protected Activity mActivity;

    /**
     * 根view
     */
    protected View rootView;

    /**
     * 保存传递过来的参数的action对象
     */
    protected SimpleFragmentActivity.Action action;


    /**
     * 当该fragment被附加到activity时被回调， 此方法只会被调用一次
     *
     * @param context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //获取宿主Activity
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            //防止fragment出现重叠
            boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            if (isSupportHidden) {
                ft.hide(this);
            } else {
                ft.show(this);
            }
            ft.commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 创建view时回调
     *
     * @param inflater           布局填充器
     * @param container          容器
     * @param savedInstanceState 保存实例状态
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //填充布局view
        rootView = inflater.inflate(getLayoutId(), null);
        //获取action
        action = (SimpleFragmentActivity.Action) getArguments().getSerializable(SimpleFragmentActivity.KEY_ACTION);
        //初始化view
        initView(savedInstanceState);
        //初始化事件
        initEvent();
        //初始化数据
        initData(savedInstanceState);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(action.fragmentClassName);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(action.fragmentClassName);
    }

    /**
     * 获取fragment布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 初始化事件
     */
    protected abstract void initEvent();

    /**
     * 初始化数据
     */
    protected abstract void initData(Bundle savedInstanceState);

    /**
     * 寻找view
     *
     * @param viewId view的id
     * @param <T>
     * @return
     */
    protected <T extends View> T findView(int viewId) {
        return (T) rootView.findViewById(viewId);
    }

    /**
     * 从parent中寻找view
     *
     * @param parent parent
     * @param viewId view的id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewForParent(View parent, int viewId) {
        return (T) parent.findViewById(viewId);
    }

    /**
     * 显示软键盘
     *
     * @param view 输入框
     */
    public void showInputKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 隐藏软键盘
     */
    public void hideInputKeyboard() {
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(getCurrentFocus()
//                                .getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);

        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(mActivity.getWindow().getDecorView().getWindowToken(), 0);
    }

}
