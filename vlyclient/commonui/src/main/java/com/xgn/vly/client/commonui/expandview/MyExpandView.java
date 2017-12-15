package com.xgn.vly.client.commonui.expandview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xgn.vly.client.commonui.R;

/**
 * Created by XG on 2017/7/12.
 * author by liuchao
 */

public class MyExpandView extends RelativeLayout {
    private Context context;
    private View rootView;
    private RelativeLayout menuLayout;
    private View menuLayoutChild;
    private RelativeLayout menuExpandLayout;
    private View menuExpandLayoutChild;
    private boolean isOpen;

    public MyExpandView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public MyExpandView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public MyExpandView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        rootView = LayoutInflater.from(context).inflate(R.layout.expand_item_layout, this);
        menuLayout = (RelativeLayout) rootView.findViewById(R.id.menu_layout);
        menuExpandLayout = (RelativeLayout) rootView.findViewById(R.id.menu_expand_layout);
    }

    public void initExpandView(View menuLayoutItem, View menuExpandLayoutItem) {
        menuLayoutChild = menuLayoutItem;
        menuExpandLayoutChild = menuExpandLayoutItem;
        //
        RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        menuLayoutChild.setLayoutParams(lp1);
        menuLayout.addView(menuLayoutChild);
        //
        RelativeLayout.LayoutParams lp2 = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        menuExpandLayoutChild.setLayoutParams(lp2);
        menuExpandLayout.addView(menuExpandLayoutChild);
        //
        RelativeLayout.LayoutParams lp3 = (RelativeLayout.LayoutParams) menuExpandLayout.getLayoutParams();
        menuLayout.measure(0, 0);
        //获取控件宽高
        final int height = menuLayout.getMeasuredHeight();
        lp3.setMargins(0, height, 0, 0);
        menuExpandLayout.setLayoutParams(lp3);
        //
        menuExpandLayout.setVisibility(View.GONE);
    }

    /**
     * 带动画的展开
     */
    public void memuExpandAnimShow() {
        if (isOpen) {
            return;
        }
        //测量控件宽高
        menuExpandLayout.measure(0, 0);
        //获取控件宽高
        final int height = menuExpandLayout.getMeasuredHeight();

        menuExpandLayout.setVisibility(View.VISIBLE);
        menuExpandLayoutChild.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(0, height);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                menuExpandLayout.getLayoutParams().height = value;
                menuExpandLayout.setLayoutParams(menuExpandLayout.getLayoutParams());
            }
        });
        animator.start();
        isOpen = true;
    }

    /**
     * 带动画的收起
     */
    public void memuExpandAnimclose() {
        if (!isOpen) {
            return;
        }
        //测量控件宽高
        menuExpandLayout.measure(0, 0);
        //获取控件宽高
        final int height = menuExpandLayout.getMeasuredHeight();

        menuExpandLayout.setVisibility(View.VISIBLE);
        menuExpandLayoutChild.setVisibility(View.INVISIBLE);
        ValueAnimator animator = ValueAnimator.ofInt(height, 0);
        animator.setDuration(500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (Integer) animation.getAnimatedValue();
                menuExpandLayout.getLayoutParams().height = value;
                menuExpandLayout.setLayoutParams(menuExpandLayout.getLayoutParams());
            }
        });
        animator.start();
        isOpen = false;
    }

    /**
     * 不带动画的展开
     */
    public void memuExpandShow() {
        if (isOpen) {
            return;
        }
        //测量控件宽高
        menuExpandLayout.measure(0, 0);
        //获取控件宽高
        final int height = menuExpandLayout.getMeasuredHeight();
        menuExpandLayout.setVisibility(View.VISIBLE);
        menuExpandLayoutChild.setVisibility(View.VISIBLE);
        menuExpandLayout.getLayoutParams().height = height;
        menuExpandLayout.setLayoutParams(menuExpandLayout.getLayoutParams());
        isOpen = true;
    }

    /**
     * 不带动画的收起
     */
    public void memuExpandclose() {
        if (!isOpen) {
            return;
        }
        menuExpandLayout.setVisibility(View.VISIBLE);
        menuExpandLayoutChild.setVisibility(View.INVISIBLE);
        menuExpandLayout.getLayoutParams().height = 0;
        menuExpandLayout.setLayoutParams(menuExpandLayout.getLayoutParams());
        isOpen = false;
    }

    /**
     * 设置是否展开
     *
     * @param open
     */
    public void setOpen(boolean open) {

        if (open) {
            memuExpandShow();
        } else {
            memuExpandclose();
        }
    }
}
