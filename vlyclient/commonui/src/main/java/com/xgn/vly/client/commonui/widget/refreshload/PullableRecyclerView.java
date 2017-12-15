package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgn.vlv.client.base.adapter.recyclerview.HeaderAndFooterWrapper;
import com.xgn.vly.client.commonui.R;

/**
 * 可下拉的RecyclerView
 * Created by tanghh on 2016/7/15.
 */
public class PullableRecyclerView extends RecyclerView implements Pullable {

    public static final int INIT = 0;
    public static final int LOADING = 1;
    public static final int NO_MORE_DATA = 2;
    private OnLoadListener mOnLoadListener;
    private View mLoadmoreView;
    private ImageView mLoadingView;
    private TextView mStateTextView;
    private int state = INIT;
    private boolean canLoad = true;
    private boolean autoLoad = true;
    private boolean hasMoreData = false;
    private AnimationDrawable mLoadAnim;
    private HeaderAndFooterWrapper footerWrapper;

    /**
     * 底部view实现的接口
     */
    private ILoadFooter loadFooter;

    /**
     * 底部view
     */
    private View footerView;

    public PullableRecyclerView(Context context) {
        super(context);
        init();
    }

    public PullableRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullableRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    public boolean canPullDown() {
//        LinearLayoutManager lm = (LinearLayoutManager) getLayoutManager();
//        //如果没有item 则可以下拉刷新
//        if (lm.getItemCount() == 0) {
//            return true;
//        }
//        //如果滑动到底部了，可以下拉刷新
//        else if (lm.findViewByPosition(lm.findFirstVisibleItemPosition()).getTop() == 0 && lm.findFirstVisibleItemPosition() == 0) {
//            return true;
//        }
//        //其他情况不可以下拉刷新
        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
        if (!canScrollVertically(-1)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
//        LinearLayoutManager lm = (LinearLayoutManager) getLayoutManager();
//        //如果没有item则 不可以上拉加载
//        if(lm.getItemCount() == 0){
//            return false;
//        }
//        //如果有item 并且滑动到最底部了，则可以上拉加载
//        else if (lm.findViewByPosition(lm.findLastVisibleItemPosition()).getBottom() <= (getHeight())
//                && lm.findLastVisibleItemPosition() == (lm.getItemCount() - 1)) {
//            return true;
//        }
//        //其他情况不可以上拉加载
        return false;
    }


    /**
     * 重写setAdapter，实际设置的是包装后的adapter
     *
     * @param adapter
     */
    @Override
    public void setAdapter(Adapter adapter) {
        footerWrapper = new HeaderAndFooterWrapper(adapter);
        if (footerView != null) {
            footerWrapper.addFootView(footerView);
        }
        super.setAdapter(footerWrapper);
    }

    /**
     * 初始化
     */
    private void init() {
        VlyLoadFooter temp = new VlyLoadFooter(getContext(), VlyLoadFooter.LayoutParamsType.RECYCLERVIEW);
        footerView = temp;
        loadFooter = temp;
        setHasMoreData(false);
    }

    /**
     * 是否开启自动加载
     *
     * @param enable true启用，false禁用
     */
    public void enableAutoLoad(boolean enable) {
        autoLoad = enable;
    }

    /**
     * 是否显示加载更多
     *
     * @param v true显示，false不显示
     */
    public void setLoadmoreVisible(boolean v) {
        if (v) {
            if (footerWrapper.getFootersCount() == 0 && footerView != null) {
                footerWrapper.addFootView(footerView);
            }
        } else {
            footerWrapper.getmFootViews().clear();
        }
        footerWrapper.notifyDataSetChanged();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                // 按下的时候禁止自动加载
//                canLoad = false;
//                break;
            case MotionEvent.ACTION_UP:
                // 松开手判断是否自动加载
                canLoad = true;
                checkLoad();
                break;
        }
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 在滚动中判断是否满足自动加载条件
        checkLoad();
    }

    /**
     * 判断是否满足自动加载条件
     */
    private void checkLoad() {
        if (reachBottom() && mOnLoadListener != null && state != LOADING
                && canLoad && autoLoad && hasMoreData)
            load();
    }

    /**
     * 加载更多数据
     */
    private void load() {
        changeState(LOADING);
        mOnLoadListener.onLoad(this);
    }

    /**
     * 改变状态
     *
     * @param state
     */
    private void changeState(int state) {
        this.state = state;
        switch (state) {
            case INIT:
                loadFooter.change2Init();
                break;

            case LOADING:
                loadFooter.change2Loading();
                break;

            case NO_MORE_DATA:
                loadFooter.change2NoMoreData();
                break;
        }
    }

    /**
     * 完成加载
     */
    public void finishLoading() {
        changeState(INIT);
    }

    public HeaderAndFooterWrapper getFooterWrapper() {
        return footerWrapper;
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    /**
     * 判断recyclerview是否滚动到底了
     *
     * @return footerview可见时返回true，否则返回false
     */
    private boolean reachBottom() {
        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
//        if (!canScrollVertically(1)) {
//            return true;
//        }
        try {
            LinearLayoutManager manager = (LinearLayoutManager) getLayoutManager();
            //获取最后一个完全显示的ItemPosition
            int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
            int totalItemCount = manager.getItemCount();

            // 判断是否滚动到底部
            if (lastVisibleItem == (totalItemCount - 1)) {
                //加载更多功能的代码
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否有更多数据
     *
     * @return
     */
    public boolean isHasMoreData() {
        return hasMoreData;
    }

    /**
     * 设置是否有更多数据
     *
     * @param hasMoreData
     */
    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (!hasMoreData) {
            changeState(NO_MORE_DATA);
        } else {
            changeState(INIT);
        }
    }


    public void notifyDataSetChanged() {
        footerWrapper.notifyDataSetChanged();
    }

    /**
     * 加载监听器
     */
    public interface OnLoadListener {
        void onLoad(PullableRecyclerView pullableRecyclerView);
    }

}
