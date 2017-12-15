package com.xgn.vlv.client.base.adapter.viewpager;

import android.content.Context;
import android.view.View;

/**
 * ViewPagerHolder
 */
public interface ViewPagerHolder<T> {
    /**
     *  创建View
     * @param context
     * @return
     */
    View createView(Context context);

    /**
     * 绑定数据
     * @param context
     * @param position
     * @param data
     */
    void onBind(Context context,int position,T data);
}
