package com.xgn.vly.client.commonui.widget.refreshload;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * 支持下拉刷新的ScrollView
 * Created by tanghh on 2017/7/5.
 */
public class PullableScrollView extends ScrollView implements  Pullable{


    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean canPullDown() {
        //canScrollVertically(1)的值表示是否能向上滚动，true表示能滚动，false表示已经滚动到底部
        //canScrollVertically(-1)的值表示是否能向下滚动，true表示能滚动，false表示已经滚动到顶部
        if (!canScrollVertically(-1)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canPullUp() {
        return false;
    }
}
