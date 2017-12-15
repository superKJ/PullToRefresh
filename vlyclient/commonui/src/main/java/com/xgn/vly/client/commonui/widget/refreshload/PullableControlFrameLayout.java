package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * 可下拉的FrameLayout,需要手动控制能否下拉
 * Created by tanghh on 2017/7/8.
 */
public class PullableControlFrameLayout extends FrameLayout implements  Pullable{

    private boolean isCanPullDown;

    public PullableControlFrameLayout(Context context) {
        super(context);
    }

    public PullableControlFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableControlFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean canPullDown() {
//        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
//        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
//        if (!canScrollVertically(-1)) {
//            return true;
//        }
//        return false;
        return isCanPullDown;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }


    public boolean isCanPullDown() {
        return isCanPullDown;
    }

    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }
}
