package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 自定义是否滑动切换的ViewPager
 * Created by Boqin on 2016/11/17.
 * Modified by Boqin
 *
 * @Version
 */
public class ViewPagerCustomScroll extends ViewPager {

    private boolean isCanScroll = false;

    public ViewPagerCustomScroll(Context context) {
        super(context);
        init();
    }

    public ViewPagerCustomScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 设置是否可活动切换，默认不可滑动切换
     * @param isCanScroll true:可滑动
     * @description: Created by Boqin on 2016/11/17 18:07
     */
    public void setScanScroll(boolean isCanScroll){
        this.isCanScroll = isCanScroll;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return super.onTouchEvent(arg0);
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        if (isCanScroll) {
            return super.onInterceptTouchEvent(arg0);
        } else {
            return false;
        }
    }

    private void init() {

    }

}
