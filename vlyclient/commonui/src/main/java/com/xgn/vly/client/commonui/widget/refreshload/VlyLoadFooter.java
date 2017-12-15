package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.xgn.vly.client.commonui.R;

/**
 * vly样式的 底部自动加载布局
 * Created by tanghh on 2017/6/29.
 */
public class VlyLoadFooter extends FrameLayout implements ILoadFooter {


    /**
     * 加载进度条
     */
    private ImageView mIvLoading;

    /**
     * 加载中布局
     */
    private View mRlLoading;

    /**
     * 没有更多数据布局
     */
    private View mRlNoMoreData;

    /**
     * 当前加载状态
     */
    private TextView mTvState;

    /**
     * 均匀旋转动画
     */
    private RotateAnimation refreshingAnimation;

    public VlyLoadFooter(Context context, LayoutParamsType type) {
        super(context);
        init(type);
    }


    /**
     * 初始化
     */
    private void init(LayoutParamsType type) {
        switch (type) {
            case LISTVIEW:
                setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
                break;
            case RECYCLERVIEW:
                setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
                break;
        }
        LayoutInflater.from(getContext()).inflate(R.layout.load_more_vly, this);
        mIvLoading = (ImageView) findViewById(R.id.iv_loading);
        mRlLoading = findViewById(R.id.rl_loading);
        mRlNoMoreData = findViewById(R.id.rl_no_more_data);
        mTvState= (TextView) findViewById(R.id.tv_state);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(
                getContext(), R.anim.rotating);
        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
    }


    @Override
    public void change2Init() {
        mTvState.setText(getContext().getString(R.string.up_pull_load));
        mIvLoading.clearAnimation();
        mRlLoading.setVisibility(View.VISIBLE);
        mRlNoMoreData.setVisibility(View.GONE);
    }

    @Override
    public void change2Loading() {
        mTvState.setText(getContext().getString(R.string.is_loading));
        mRlLoading.setVisibility(View.VISIBLE);
        mRlNoMoreData.setVisibility(View.GONE);
        mIvLoading.startAnimation(refreshingAnimation);
    }

    @Override
    public void change2NoMoreData() {
        mTvState.setText(getContext().getString(R.string.up_pull_load));
        mIvLoading.clearAnimation();
        mRlLoading.setVisibility(View.GONE);
        mRlNoMoreData.setVisibility(View.VISIBLE);
    }


    public static enum LayoutParamsType {
        LISTVIEW,
        RECYCLERVIEW
    }
}
