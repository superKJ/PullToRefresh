package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.HeaderViewListAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 支持 下拉刷新、上拉加载的ExpandableListView
 *
 * @author tanghh
 */
public class PullableExpandableListView extends ExpandableListView implements Pullable {
    public static final int INIT = 0;
    public static final int LOADING = 1;
    public static final int NO_MORE_DATA = 2;
    private OnLoadListener mOnLoadListener;
    private int state = INIT;
    private boolean canLoad = true;
    private boolean autoLoad = true;
    private boolean hasMoreData = true;

    /**
     * 底部view实现的接口
     */
    private ILoadFooter loadFooter;

    /**
     * 底部view
     */
    private View footerView;

    public PullableExpandableListView(Context context) {
        super(context);
        init(context);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        VlyLoadFooter temp = new VlyLoadFooter(getContext(),VlyLoadFooter.LayoutParamsType.LISTVIEW);
        footerView = temp;
        loadFooter = temp;
        addFooterView(footerView, null, false);
    }

    /**
     * 是否启用自动加载
     */
    public void enableAutoLoad(boolean enable) {
        autoLoad = enable;
    }

    /**
     * 设置加载更多 显示/隐藏
     */
    public void setLoadmoreVisible(boolean v) {
        if (v) {
            if (getFooterViewsCount() == 0) {
                addFooterView(footerView, null, false);
            }
        } else {
            removeFooterView(footerView);
        }
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent ev) {
//        switch (ev.getActionMasked()) {
//            case MotionEvent.ACTION_DOWN:
//                canLoad = false;
//                break;
//            case MotionEvent.ACTION_UP:
//                canLoad = true;
//                checkLoad();
//                break;
//        }
//        return super.onTouchEvent(ev);
//    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        checkLoad();
    }

    /**
     * 检查是否能加载
     */
    private void checkLoad() {
        if (reachBottom() && mOnLoadListener != null && state != LOADING
                && canLoad && autoLoad && hasMoreData)
            load();
    }

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
     * 结束加载
     */
    public void finishLoading() {
        changeState(INIT);
    }

    @Override
    public boolean canPullDown() {
        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
        if (!canScrollVertically(-1)) {
            return true;
        }
        return false;
//        if (getCount() == 0) {
//            return true;
//        } else if (getFirstVisiblePosition() == 0
//                && getChildAt(0).getTop() >= 0) {
//            return true;
//        } else
//            return false;
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.mOnLoadListener = listener;
    }

    /**
     * 判断是否滑动到底部
     */
    private boolean reachBottom() {
//        if (getCount() == 0) {
//            return true;
//        } else if (getLastVisiblePosition() == (getCount() - 1)) {
//            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
//                    && getChildAt(
//                    getLastVisiblePosition()
//                            - getFirstVisiblePosition()).getTop() < getMeasuredHeight() && !canPullDown())
//                return true;
//        }
//        return false;
        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
        if (!canScrollVertically(1)) {
            return true;
        }
        return false;
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        if (!hasMoreData) {
            changeState(NO_MORE_DATA);
        } else {
            changeState(INIT);
        }
    }

    public void notifyDataSetChanged() {
        if (getAdapter() instanceof HeaderViewListAdapter) {
            ListAdapter tempAdapter = ((HeaderViewListAdapter) getAdapter()).getWrappedAdapter();
            ((BaseAdapter) tempAdapter).notifyDataSetChanged();
        } else {
            ((BaseExpandableListAdapter) getAdapter()).notifyDataSetChanged();
        }
    }

    public interface OnLoadListener {
        void onLoad(PullableExpandableListView pullableListView);
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
