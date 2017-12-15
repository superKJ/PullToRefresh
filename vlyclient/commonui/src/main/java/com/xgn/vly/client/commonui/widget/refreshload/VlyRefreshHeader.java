package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;

/**
 * vly样式的 刷新头布局
 *
 * @author tanghh
 */
public class VlyRefreshHeader extends FrameLayout implements IRefreshHeader {


    /**
     * 下拉箭头的转180°动画
     */
    private RotateAnimation rotateAnimation;

    /**
     * 均匀旋转动画
     */
    private RotateAnimation refreshingAnimation;


    /**
     * 到达释放刷新的距离
     */
    private int pullMaxDist;

    /**
     * 下拉刷新布局
     */
    private View mLlPullRefresh;

    /**
     * 刷新中布局
     */
    private View mLlRefreshing;


    /**
     * 刷新结果布局
     */
    private View mLlRefreshResult;

    /**
     * 下拉刷新 提示文字
     */
    private TextView mTvPullRefresh;

    /**
     * 刷新结果 提示文字
     */
    private TextView mTvRefreshResult;

    /**
     * 下拉刷新 图标
     */
    private ImageView mIvPullRefresh;

    /**
     * 刷新结果 图标
     */
    private ImageView mIvRefreshResult;

    /**
     * 刷新中的 图标
     */
    private ImageView mIvRefreshing;

    public VlyRefreshHeader(Context context) {
        super(context);
        init();
    }

    public VlyRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化view
        View.inflate(getContext(), R.layout.refresh_head_vly, this);
        mLlPullRefresh = findViewById(R.id.ll_pull_refresh);
        mLlRefreshing = findViewById(R.id.ll_refreshing);
        mLlRefreshResult = findViewById(R.id.ll_refresh_result);
        mTvPullRefresh = (TextView)findViewById(R.id.tv_pull_refresh);
        mTvRefreshResult = (TextView)findViewById(R.id.tv_refresh_result);
        mIvPullRefresh = (ImageView)findViewById(R.id.iv_pull_refresh);
        mIvRefreshing = (ImageView)findViewById(R.id.iv_refreshing);
        mIvRefreshResult = (ImageView)findViewById(R.id.iv_refresh_result);
        // 初始化动画
        rotateAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                getContext(), R.anim.reverse_anim);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                getContext(), R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        rotateAnimation.setInterpolator(lir);
        refreshingAnimation.setInterpolator(lir);
    }


    @Override
    public void change2Init() {
        mLlPullRefresh.setVisibility(View.VISIBLE);
        mLlRefreshing.setVisibility(View.GONE);
        mLlRefreshResult.setVisibility(View.GONE);
        // 下拉布局初始状态
        mTvPullRefresh.setText(R.string.pull_to_refresh);
        mIvPullRefresh.startAnimation((RotateAnimation) AnimationUtils
                .loadAnimation(getContext(), R.anim.reverse_anim2));
    }

    @Override
    public void change2ReleaseToRefresh() {
        mLlPullRefresh.setVisibility(View.VISIBLE);
        mLlRefreshing.setVisibility(View.GONE);
        mLlRefreshResult.setVisibility(View.GONE);
        // 释放刷新状态
        mTvPullRefresh.setText(R.string.release_to_refresh);
        mIvPullRefresh.startAnimation(rotateAnimation);

    }

    @Override
    public void change2Refreshing() {
        mLlPullRefresh.setVisibility(View.GONE);
        mLlRefreshing.setVisibility(View.VISIBLE);
        mLlRefreshResult.setVisibility(View.GONE);
        // 正在刷新状态
        mIvPullRefresh.clearAnimation();
        mIvRefreshing.startAnimation(refreshingAnimation);
    }

    @Override
    public void change2Done() {

    }

    @Override
    public void onRefreshSuccess() {
        mLlPullRefresh.setVisibility(View.GONE);
        mLlRefreshing.setVisibility(View.GONE);
        mLlRefreshResult.setVisibility(View.VISIBLE);
        mIvRefreshing.clearAnimation();
        mTvRefreshResult.setText(R.string.refresh_succeed);
        mIvRefreshResult
                .setBackgroundResource(R.drawable.refresh_succeed);
    }

    @Override
    public void onRefreshFail() {
        mLlPullRefresh.setVisibility(View.GONE);
        mLlRefreshing.setVisibility(View.GONE);
        mLlRefreshResult.setVisibility(View.VISIBLE);
        mIvRefreshing.clearAnimation();
        mTvRefreshResult.setText(R.string.refresh_fail);
        mIvRefreshResult
                .setBackgroundResource(R.drawable.refresh_failed);

    }

    @Override
    public void onPullDown(float pullDownY) {

    }

    @Override
    public float getRefreshDist() {
        pullMaxDist = findViewById(R.id.rl_head).getMeasuredHeight();
        return pullMaxDist;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                getContext().getResources().getDisplayMetrics());

    }


}
