package com.xgn.vly.client.commonui.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lenghuo on 2017/3/6 17:34
 * Modified by xxx
 */

public class ExpandViewpager extends ViewPager {
    public ExpandViewpager(Context context) {
        super(context);
    }

    public ExpandViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = 0;
        //获取child的高度
        if (getChildCount()!=0) {
            View child = findViewWithTag(getCurrentItem());
            child.measure(widthMeasureSpec,
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            int h = child.getMeasuredHeight();
            if (h > height) //采用最大的view的高度。
                height = h;
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height,
                MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
