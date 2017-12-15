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

/**
 * Created by tanghh on 2017/6/20.
 * <p>
 * Fragment基类，支持懒加载
 * <p>
 * 1、Viewpager + Fragment情况下，fragment的生命周期因Viewpager的缓存机制而失去了具体意义
 * 该抽象类自定义新的回调方法，当fragment可见状态改变时会触发的回调方法，和 Fragment 第一次可见时会回调的方法
 *
 * @see #onFragmentVisibleChange(boolean)
 * @see #onFragmentFirstVisible()
 */
public abstract class LazyBaseFragment extends Fragment {

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
     * isFragmentVisible
     */
    private boolean isFragmentVisible;

    /**
     * isFirstVisible
     */
    private boolean isFirstVisible;

    /**
     * 是否复用view
     */
    private boolean isReuseView;

    /**
     * view是否创建完成
     */
    private boolean isViewCreated;

    /**
     * setUserVisibleHint
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (!isViewCreated) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }


    /**
     * onViewCreated
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        isViewCreated = true;
        if (getUserVisibleHint()) {
            if (isFirstVisible) {
                onFragmentFirstVisible();
                isFirstVisible = false;
            }
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
        }
        super.onViewCreated(view, savedInstanceState);
    }


    /**
     * onDestroy
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        initVariable();
    }

    /**
     * 初始化变量
     */
    private void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = false;
        isViewCreated = false;
    }

    /**
     * 设置是否使用 view 的复用，默认关闭
     * view 的复用是指，ViewPager 在销毁和重建 Fragment 时会不断调用 onCreateView() -> onDestroyView()
     * 之间的生命函数，这样可能会出现重复创建 view 的情况，导致界面上显示多个相同的 Fragment
     * view 的复用其实就是指保存第一次创建的 view，后面再 onCreateView() 时直接返回第一次创建的 view
     * 注意：需要在viewpager的adapter中重写destroyItem方法并去掉super.destroyItem方法，即不销毁item！！！
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }


    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     * <p>
     * 可在该回调方法里进行一些ui显示与隐藏，比如加载框的显示和隐藏
     *
     * @param isVisible true  不可见 -> 可见
     *                  false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可在这里进行加载数据，保证只在第一次打开Fragment时才会加载数据，
     */
    protected void onFragmentFirstVisible() {

    }

    /**
     * 判断fragment是否可见
     *
     * @return
     */
    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

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

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
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

    /**
     * onSaveInstanceState
     *
     * @param outState
     */
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
        if (rootView == null || isReuseView == false) {
            //填充布局view
            rootView = inflater.inflate(getLayoutId(), null);
            //初始化view
            initView(savedInstanceState);
            //初始化事件
            initEvent();
            //初始化数据
            initData(savedInstanceState);
        }
        return rootView;
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
