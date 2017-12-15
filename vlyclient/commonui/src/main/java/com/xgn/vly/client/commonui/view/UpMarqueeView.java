package com.xgn.vly.client.commonui.view;

import com.xgn.vly.client.commonui.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ViewFlipper;

/**
 * 滚动消息View
 * Created by Boqin on 2017/6/15.
 * Modified by Boqin
 *
 * @Version
 */
public class UpMarqueeView extends ViewFlipper{

    private Context mContext;
    /** 设置动画间隔 */
    private boolean isSetAnimDuration = false;
    /** 区域切换间隔时间 */
    private int interval = 2000;
    /** 动画时间 */
    private int animDuration = 500;

    /** 点击事件监听 */
    private OnItemClickListener mOnItemClickListener;

    /** 适配器 */
    private BaseAdapter mBaseAdapter;

    private DataSetObserver mDataSetObserver = new DataSetObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            updateMarquee(mBaseAdapter);
        }
    };

    public UpMarqueeView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public UpMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    /**
     * 初始化操作
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration) {
            animIn.setDuration(animDuration);
        }
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration) {
            animOut.setDuration(animDuration);
        }
        setOutAnimation(animOut);
    }

    /**
     * 设置适配器
     * @param adapter 适配器
     */
    public void setAdapter(BaseAdapter adapter){
        if (adapter==null) {
            return;
        }
        mBaseAdapter = adapter;
//        updateMarquee(adapter);
        mBaseAdapter.registerDataSetObserver(mDataSetObserver);
    }

    /**
     * 更新子View
     */
    private void updateMarquee(BaseAdapter adapter) {
        stopFlipping();
        removeAllViews();
        for (int i = 0; i < adapter.getCount(); i++) {
            final int position = i;
            View view = adapter.getView(i, null, this);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener!=null) {
                        mOnItemClickListener.onItemClick(v, position);
                    }
                }
            });
            addView(view);
        }
        if (getChildCount()!=0) {
            startFlipping();
        }

    }

    /**
     * 列表更新事件监听
     * @description: Created by Boqin on 2017/6/15 16:41
     */
    public interface OnItemClickListener{
        /**
         * 点击事件
         * @param v 当前View
         * @param position 当前位置
         */
        void onItemClick(View v, int position);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startFlipping();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopFlipping();
    }

    @Override
    protected void onVisibilityChanged(
            @NonNull
                    View changedView,
                    int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility==GONE||visibility==INVISIBLE) {
            stopFlipping();
        }else {
            startFlipping();
        }
    }
}
